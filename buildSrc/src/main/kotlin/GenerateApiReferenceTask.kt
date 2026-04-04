import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

/**
 * Generates RIKKAUI_REFERENCE.md from KDocs in component source files.
 *
 * Parses every public @Composable function, extracts its KDoc and parameter
 * signature, and writes a flat Markdown API reference that AI agents and
 * developers can read without IDE access or JAR extraction.
 */
abstract class GenerateApiReferenceTask : DefaultTask() {

    @get:InputDirectory
    abstract val componentsUiDir: DirectoryProperty

    @get:InputDirectory
    abstract val foundationDir: DirectoryProperty

    @get:InputFile
    abstract val registrySourceFile: RegularFileProperty

    @get:OutputFile
    abstract val outputFile: RegularFileProperty

    // Parsed from ComponentRegistry.kt
    private data class RegistryEntry(
        val id: String,
        val rawName: String,
        val rawDescription: String,
        val category: String,
    )

    // A single public composable function
    private data class ComposableInfo(
        val name: String,
        val description: String,
        val params: List<ParamInfo>,
        val rawSignature: String,
    )

    private data class ParamInfo(
        val name: String,
        val type: String,
        val default: String?,
        val description: String,
    )

    private fun parseRegistry(): List<RegistryEntry> {
        val source = registrySourceFile.get().asFile.readText()
        val pattern = Regex(
            """ComponentEntry\(\s*""" +
                """id\s*=\s*"([^"]+)"\s*,\s*""" +
                """rawName\s*=\s*"([^"]+)"\s*,\s*""" +
                """rawDescription\s*=\s*"([^"]+)"\s*,\s*""" +
                """.*?category\s*=\s*ComponentCategory\.(\w+)""",
            RegexOption.DOT_MATCHES_ALL,
        )
        return pattern.findAll(source).map { m ->
            RegistryEntry(m.groupValues[1], m.groupValues[2], m.groupValues[3], m.groupValues[4])
        }.toList()
    }

    private val idToDirOverrides = mapOf(
        "dropdown-menu" to "dropdown",
        "scroll-area" to "scrollarea",
        "hover-card" to "hovercard",
        "toggle-group" to "togglegroup",
        "alert-dialog" to "alertdialog",
        "context-menu" to "contextmenu",
        "navigation-bar" to "navigationbar",
        "top-app-bar" to "topappbar",
    )

    private fun idToDirName(id: String): String =
        idToDirOverrides[id] ?: id.replace("-", "")

    /**
     * Extracts all public @Composable functions from a Kotlin source file,
     * along with their KDoc and parameter signatures.
     */
    private fun extractComposables(file: File): List<ComposableInfo> {
        val text = file.readText()
        val results = mutableListOf<ComposableInfo>()

        // Match @Composable fun Name( and then manually find the balanced closing paren
        val composablePattern = Regex(
            """@Composable\s+fun\s+(\w+)\s*\(""",
        )

        for (match in composablePattern.findAll(text)) {
            val funcName = match.groupValues[1]
            val parenStart = match.range.last // index of '('

            // Skip private/internal functions: check for visibility on preceding lines
            val matchStart = match.range.first
            val lineStart = text.lastIndexOf('\n', matchStart).coerceAtLeast(0)
            val prefix = text.substring(lineStart, matchStart)
            if (prefix.contains("private") || prefix.contains("internal")) continue

            // Skip functions inside classes/objects (companion object, etc.)
            // by checking if the brace depth at this position is > 0
            if (braceDepthAt(text, matchStart) > 0) continue

            // Find balanced closing paren
            val rawParams = extractBalancedParens(text, parenStart) ?: continue

            // Find KDoc immediately before @Composable (only whitespace/annotations between)
            val rawKdoc = findPrecedingKdoc(text, matchStart)

            val description = parseKdocDescription(rawKdoc)
            val kdocParams = parseKdocParams(rawKdoc)
            val params = parseSignatureParams(rawParams, kdocParams)

            val cleanSig = cleanSignature(funcName, rawParams)

            results.add(ComposableInfo(funcName, description, params, cleanSig))
        }

        return results
    }

    /**
     * Returns the brace nesting depth at a given position in the text.
     * Depth > 0 means the position is inside a class/object/function body.
     */
    private fun braceDepthAt(text: String, position: Int): Int {
        var depth = 0
        var inString = false
        var inLineComment = false
        var inBlockComment = false
        var i = 0
        while (i < position && i < text.length) {
            val ch = text[i]
            val next = if (i + 1 < text.length) text[i + 1] else '\u0000'
            when {
                inLineComment -> { if (ch == '\n') inLineComment = false }
                inBlockComment -> { if (ch == '*' && next == '/') { inBlockComment = false; i++ } }
                ch == '/' && next == '/' -> inLineComment = true
                ch == '/' && next == '*' -> { inBlockComment = true; i++ }
                ch == '"' -> inString = !inString
                inString -> {}
                ch == '{' -> depth++
                ch == '}' -> depth--
            }
            i++
        }
        return depth
    }

    /**
     * Extracts the content between balanced parentheses starting at the given index.
     */
    private fun extractBalancedParens(text: String, openIndex: Int): String? {
        var depth = 0
        var i = openIndex
        while (i < text.length) {
            when (text[i]) {
                '(' -> depth++
                ')' -> {
                    depth--
                    if (depth == 0) return text.substring(openIndex + 1, i)
                }
            }
            i++
        }
        return null
    }

    /**
     * Finds the KDoc comment immediately preceding a function declaration.
     * Only matches if there's nothing but whitespace and annotations between
     * the KDoc closing and the @Composable annotation.
     */
    private fun findPrecedingKdoc(text: String, composableStart: Int): String {
        // Walk backwards from @Composable, skipping whitespace
        val beforeComposable = text.substring(0, composableStart).trimEnd()
        // The last significant thing before @Composable should be */ for a KDoc
        if (!beforeComposable.endsWith("*/")) return ""

        val kdocEnd = beforeComposable.length
        val kdocStart = beforeComposable.lastIndexOf("/**")
        if (kdocStart < 0) return ""

        return beforeComposable.substring(kdocStart + 3, kdocEnd - 2)
    }

    private fun parseKdocDescription(kdoc: String): String {
        if (kdoc.isBlank()) return ""
        val lines = kdoc.lines()
            .map { it.trim().removePrefix("*").trim() }
            .dropWhile { it.isBlank() }

        val descLines = mutableListOf<String>()
        for (line in lines) {
            if (line.startsWith("@")) break
            if (line.isBlank() && descLines.isNotEmpty()) break
            descLines.add(line)
        }
        return descLines.joinToString(" ").trim()
    }

    private fun parseKdocParams(kdoc: String): Map<String, String> {
        if (kdoc.isBlank()) return emptyMap()
        val paramPattern = Regex("""@param\s+(\w+)\s+(.+?)(?=@param|@return|@sample|@see|$)""", RegexOption.DOT_MATCHES_ALL)
        return paramPattern.findAll(kdoc).associate { m ->
            val name = m.groupValues[1]
            val desc = m.groupValues[2]
                .lines()
                .joinToString(" ") { it.trim().removePrefix("*").trim() }
                .trim()
                .removeSuffix("*/")
                .trim()
            name to desc
        }
    }

    private fun parseSignatureParams(rawParams: String, kdocParams: Map<String, String>): List<ParamInfo> {
        val params = mutableListOf<ParamInfo>()

        // Split on top-level commas (not inside generics or lambdas)
        val paramStrings = splitParams(rawParams)

        for (paramStr in paramStrings) {
            val trimmed = paramStr.trim()
            if (trimmed.isBlank()) continue
            // Skip annotations-only lines
            if (trimmed.startsWith("@") && !trimmed.contains(":")) continue

            // Remove leading annotations
            val noAnnotations = trimmed.replace(Regex("""@\w+(\([^)]*\))?\s*"""), "").trim()
            if (noAnnotations.isBlank()) continue

            // Parse name: Type = default
            val colonIdx = noAnnotations.indexOf(':')
            if (colonIdx < 0) continue

            val name = noAnnotations.substring(0, colonIdx).trim()
            val rest = noAnnotations.substring(colonIdx + 1).trim()

            // Split type and default on top-level '='
            val (type, default) = splitTypeAndDefault(rest)

            val desc = kdocParams[name] ?: ""
            params.add(ParamInfo(name, type.trim(), default?.trim(), desc))
        }

        return params
    }

    /**
     * Splits parameter string on commas, respecting nesting of <>, (), {}.
     * Tracks each bracket type independently so `(() -> Unit)?` doesn't
     * cause negative depth from `?` or mismatched brackets.
     */
    private fun splitParams(raw: String): List<String> {
        val result = mutableListOf<String>()
        var parenDepth = 0
        var angleDepth = 0
        var braceDepth = 0
        var current = StringBuilder()

        for (ch in raw) {
            when (ch) {
                '(' -> { parenDepth++; current.append(ch) }
                ')' -> { parenDepth = (parenDepth - 1).coerceAtLeast(0); current.append(ch) }
                '<' -> { angleDepth++; current.append(ch) }
                '>' -> { angleDepth = (angleDepth - 1).coerceAtLeast(0); current.append(ch) }
                '{' -> { braceDepth++; current.append(ch) }
                '}' -> { braceDepth = (braceDepth - 1).coerceAtLeast(0); current.append(ch) }
                ',' -> {
                    if (parenDepth == 0 && angleDepth == 0 && braceDepth == 0) {
                        result.add(current.toString())
                        current = StringBuilder()
                    } else {
                        current.append(ch)
                    }
                }
                else -> current.append(ch)
            }
        }
        if (current.isNotBlank()) result.add(current.toString())
        return result
    }

    /**
     * Splits "Type = default" on the first top-level '='.
     */
    private fun splitTypeAndDefault(rest: String): Pair<String, String?> {
        var parenDepth = 0
        var angleDepth = 0
        var braceDepth = 0
        for (i in rest.indices) {
            when (rest[i]) {
                '(' -> parenDepth++
                ')' -> parenDepth = (parenDepth - 1).coerceAtLeast(0)
                '<' -> angleDepth++
                '>' -> angleDepth = (angleDepth - 1).coerceAtLeast(0)
                '{' -> braceDepth++
                '}' -> braceDepth = (braceDepth - 1).coerceAtLeast(0)
                '=' -> {
                    if (parenDepth == 0 && angleDepth == 0 && braceDepth == 0) {
                        return rest.substring(0, i).trim() to rest.substring(i + 1).trim()
                    }
                }
            }
        }
        return rest.trim() to null
    }

    private fun cleanSignature(name: String, rawParams: String): String {
        val params = parseSignatureParams(rawParams, emptyMap())
        val paramLines = params.map { p ->
            val defaultPart = if (p.default != null) " = ${p.default}" else ""
            "    ${p.name}: ${p.type}$defaultPart"
        }
        return "$name(\n${paramLines.joinToString(",\n")}\n)"
    }

    @TaskAction
    fun generate() {
        val registry = parseRegistry()
        val uiDir = componentsUiDir.get().asFile
        val out = StringBuilder()

        out.appendLine("# RikkaUI API Reference")
        out.appendLine()
        out.appendLine("> Auto-generated from source KDocs. Do not edit manually.")
        out.appendLine("> Run `./gradlew generateApiReference` to regenerate.")
        out.appendLine()
        out.appendLine("---")
        out.appendLine()

        // Group by category
        val categories = registry.groupBy { it.category }
        val categoryOrder = listOf("Layout", "Forms", "DataDisplay", "Feedback", "Overlays", "Navigation")

        for (category in categoryOrder) {
            val entries = categories[category] ?: continue

            out.appendLine("## $category")
            out.appendLine()

            for (entry in entries) {
                val dirName = idToDirName(entry.id)
                val dir = uiDir.resolve(dirName)
                if (!dir.isDirectory) continue

                val ktFiles = dir.listFiles()
                    ?.filter { it.extension == "kt" }
                    ?.sortedBy { it.name }
                    ?: continue

                val allComposables = ktFiles.flatMap { extractComposables(it) }
                if (allComposables.isEmpty()) continue

                out.appendLine("### ${entry.rawName}")
                out.appendLine()
                out.appendLine("${entry.rawDescription}")
                out.appendLine()

                for (comp in allComposables) {
                    if (allComposables.size > 1) {
                        out.appendLine("#### `${comp.name}`")
                    }

                    if (comp.description.isNotBlank()) {
                        out.appendLine()
                        out.appendLine(comp.description)
                    }

                    out.appendLine()
                    out.appendLine("```kotlin")
                    out.appendLine(comp.rawSignature)
                    out.appendLine("```")
                    out.appendLine()

                    if (comp.params.isNotEmpty()) {
                        out.appendLine("| Parameter | Type | Default | Description |")
                        out.appendLine("|-----------|------|---------|-------------|")
                        for (p in comp.params) {
                            val default = p.default ?: "required"
                            val desc = p.description.ifBlank { "—" }
                            out.appendLine("| `${p.name}` | `${p.type}` | `$default` | $desc |")
                        }
                        out.appendLine()
                    }
                }

                out.appendLine("---")
                out.appendLine()
            }
        }

        // Foundation modifiers section
        out.appendLine("## Foundation Modifiers")
        out.appendLine()

        val modifierDir = foundationDir.get().asFile.resolve("modifier")
        if (modifierDir.isDirectory) {
            val modifierFiles = modifierDir.listFiles()
                ?.filter { it.extension == "kt" }
                ?.sortedBy { it.name }
                ?: emptyList()

            for (file in modifierFiles) {
                val composables = extractComposables(file)
                // Also extract extension functions on Modifier
                val modifierFuncs = extractModifierExtensions(file)

                if (composables.isEmpty() && modifierFuncs.isEmpty()) continue

                out.appendLine("### ${file.nameWithoutExtension}")
                out.appendLine()

                for (comp in composables) {
                    out.appendLine("#### `${comp.name}`")
                    if (comp.description.isNotBlank()) {
                        out.appendLine()
                        out.appendLine(comp.description)
                    }
                    out.appendLine()
                    out.appendLine("```kotlin")
                    out.appendLine(comp.rawSignature)
                    out.appendLine("```")
                    out.appendLine()
                    if (comp.params.isNotEmpty()) {
                        out.appendLine("| Parameter | Type | Default | Description |")
                        out.appendLine("|-----------|------|---------|-------------|")
                        for (p in comp.params) {
                            val default = p.default ?: "required"
                            val desc = p.description.ifBlank { "—" }
                            out.appendLine("| `${p.name}` | `${p.type}` | `$default` | $desc |")
                        }
                        out.appendLine()
                    }
                }

                for (func in modifierFuncs) {
                    out.appendLine("#### `${func.name}`")
                    if (func.description.isNotBlank()) {
                        out.appendLine()
                        out.appendLine(func.description)
                    }
                    out.appendLine()
                    out.appendLine("```kotlin")
                    out.appendLine(func.rawSignature)
                    out.appendLine("```")
                    out.appendLine()
                    if (func.params.isNotEmpty()) {
                        out.appendLine("| Parameter | Type | Default | Description |")
                        out.appendLine("|-----------|------|---------|-------------|")
                        for (p in func.params) {
                            val default = p.default ?: "required"
                            val desc = p.description.ifBlank { "—" }
                            out.appendLine("| `${p.name}` | `${p.type}` | `$default` | $desc |")
                        }
                        out.appendLine()
                    }
                }

                out.appendLine("---")
                out.appendLine()
            }
        }

        outputFile.get().asFile.writeText(out.toString())
        logger.lifecycle("Generated API reference: ${outputFile.get().asFile.absolutePath}")
    }

    /**
     * Extracts Modifier extension functions (non-@Composable) with KDocs.
     */
    private fun extractModifierExtensions(file: File): List<ComposableInfo> {
        val text = file.readText()
        val results = mutableListOf<ComposableInfo>()

        val pattern = Regex("""fun\s+Modifier\.(\w+)\s*\(""")

        for (match in pattern.findAll(text)) {
            val funcName = match.groupValues[1]
            val parenStart = match.range.last

            val matchStart = match.range.first
            val lineStart = text.lastIndexOf('\n', matchStart).coerceAtLeast(0)
            val prefix = text.substring(lineStart, matchStart)
            if (prefix.contains("private") || prefix.contains("internal")) continue

            // Skip if this is also a @Composable function (already handled)
            val beforeFunc = text.substring(0, matchStart).trimEnd()
            if (beforeFunc.endsWith("@Composable")) continue

            val rawParams = extractBalancedParens(text, parenStart) ?: continue
            val rawKdoc = findPrecedingKdoc(text, matchStart)

            val description = parseKdocDescription(rawKdoc)
            val kdocParams = parseKdocParams(rawKdoc)
            val params = parseSignatureParams(rawParams, kdocParams)
            val cleanSig = "Modifier.${cleanSignature(funcName, rawParams)}"

            results.add(ComposableInfo("Modifier.$funcName", description, params, cleanSig))
        }

        return results
    }
}
