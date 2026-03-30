import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class GenerateRegistryJsonTask : DefaultTask() {
    @get:InputDirectory
    abstract val componentsUiDir: DirectoryProperty

    @get:InputFile
    abstract val registrySourceFile: RegularFileProperty

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @get:Input
    abstract val foundationVersion: Property<String>

    private data class ComponentMeta(
        val id: String,
        val rawName: String,
        val rawDescription: String,
        val category: String,
    )

    // Maps registry ID → filesystem dir name
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

    // Reverse: dir name → registry ID
    private val dirToIdOverrides by lazy {
        idToDirOverrides.entries.associate { (k, v) -> v to k }
    }

    private fun idToDirName(id: String): String =
        idToDirOverrides[id] ?: id.replace("-", "")

    private fun dirNameToId(dirName: String): String =
        dirToIdOverrides[dirName] ?: dirName

    private fun escapeJson(s: String): String =
        s.replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t")

    private fun parseRegistry(): List<ComponentMeta> {
        val source = registrySourceFile.get().asFile.readText()
        val entryPattern = Regex(
            """ComponentEntry\(\s*""" +
                """id\s*=\s*"([^"]+)"\s*,\s*""" +
                """rawName\s*=\s*"([^"]+)"\s*,\s*""" +
                """rawDescription\s*=\s*"([^"]+)"\s*,\s*""" +
                """.*?category\s*=\s*ComponentCategory\.(\w+)""",
            RegexOption.DOT_MATCHES_ALL,
        )
        return entryPattern.findAll(source).map { match ->
            ComponentMeta(
                id = match.groupValues[1],
                rawName = match.groupValues[2],
                rawDescription = match.groupValues[3],
                category = match.groupValues[4],
            )
        }.toList()
    }

    /**
     * Scans .kt files in a directory for cross-component imports.
     * Returns a set of directory names that this component depends on.
     */
    private fun detectDependencies(dir: File, ownDirName: String): Set<String> {
        val importPattern = Regex("""import zed\.rainxch\.rikkaui\.components\.ui\.(\w+)\.""")
        val deps = mutableSetOf<String>()

        dir.listFiles()
            ?.filter { it.extension == "kt" }
            ?.forEach { file ->
                file.readLines().forEach { line ->
                    val match = importPattern.find(line)
                    if (match != null) {
                        val depDir = match.groupValues[1]
                        if (depDir != ownDirName) {
                            deps.add(depDir)
                        }
                    }
                }
            }

        return deps
    }

    @TaskAction
    fun generate() {
        val version = foundationVersion.get()
        val rDir = outputDir.get().asFile
        rDir.deleteRecursively()
        rDir.mkdirs()

        val registry = parseRegistry()
        val uiDir = componentsUiDir.get().asFile

        // Build set of all known dir names (registered + unregistered)
        val allDirs = uiDir.listFiles()
            ?.filter { it.isDirectory }
            ?.map { it.name }
            ?.toSet()
            ?: emptySet()

        val indexEntries = mutableListOf<String>()

        registry.forEach { meta ->
            val dirName = idToDirName(meta.id)
            val dir = uiDir.resolve(dirName)
            if (!dir.isDirectory) {
                logger.warn("Registry entry '${meta.id}' has no matching directory: $dirName")
                return@forEach
            }

            val ktFiles = dir.listFiles()
                ?.filter { it.extension == "kt" }
                ?.sortedBy { it.name }
                ?: emptyList()
            if (ktFiles.isEmpty()) return@forEach

            // Auto-detect cross-component dependencies
            val depDirNames = detectDependencies(dir, dirName)
            val depIds = depDirNames.map { dirNameToId(it) }.sorted()

            val filesJson = ktFiles.joinToString(",\n") { file ->
                val content = file.readText()
                """    {
      "name": "${file.name}",
      "path": "ui/$dirName/${file.name}",
      "type": "registry:component",
      "content": "${escapeJson(content)}"
    }"""
            }

            val depsJson = if (depIds.isNotEmpty()) {
                depIds.joinToString(",\n") { """    "$it"""" }
            } else ""

            val componentJson = """{
  "name": "${meta.id}",
  "type": "registry:component",
  "title": "${meta.rawName}",
  "description": "${escapeJson(meta.rawDescription)}",
  "category": "${meta.category}",
  "version": "$version",
  "registryDependencies": [
$depsJson
  ],
  "gradleDependencies": [
    "dev.rikkaui:foundation:$version"
  ],
  "files": [
$filesJson
  ]
}
"""
            rDir.resolve("${meta.id}.json").writeText(componentJson)

            val filesMetaJson = ktFiles.joinToString(",\n") { file ->
                """      {
        "name": "${file.name}",
        "path": "ui/$dirName/${file.name}",
        "type": "registry:component"
      }"""
            }
            indexEntries.add("""  {
    "name": "${meta.id}",
    "type": "registry:component",
    "title": "${meta.rawName}",
    "description": "${escapeJson(meta.rawDescription)}",
    "category": "${meta.category}",
    "registryDependencies": [${depIds.joinToString(", ") { "\"$it\"" }}],
    "files": [
$filesMetaJson
    ]
  }""")
        }

        // Handle directories not in the registry (icon, indication, popup-animation, etc.)
        val registeredDirs = registry.map { idToDirName(it.id) }.toSet()
        val unregisteredDirs = uiDir.listFiles()
            ?.filter { it.isDirectory && it.name !in registeredDirs }
            ?.sortedBy { it.name }
            ?: emptyList()

        unregisteredDirs.forEach { dir ->
            val ktFiles = dir.listFiles()
                ?.filter { it.extension == "kt" }
                ?.sortedBy { it.name }
                ?: emptyList()
            if (ktFiles.isEmpty()) return@forEach

            val id = dirNameToId(dir.name)
            val title = dir.name.replaceFirstChar { it.uppercase() }

            val depDirNames = detectDependencies(dir, dir.name)
            val depIds = depDirNames.map { dirNameToId(it) }.sorted()

            val filesJson = ktFiles.joinToString(",\n") { file ->
                val content = file.readText()
                """    {
      "name": "${file.name}",
      "path": "ui/${dir.name}/${file.name}",
      "type": "registry:utility",
      "content": "${escapeJson(content)}"
    }"""
            }

            val depsJson = if (depIds.isNotEmpty()) {
                depIds.joinToString(",\n") { """    "$it"""" }
            } else ""

            val componentJson = """{
  "name": "$id",
  "type": "registry:utility",
  "title": "$title",
  "version": "$version",
  "registryDependencies": [
$depsJson
  ],
  "gradleDependencies": [
    "dev.rikkaui:foundation:$version"
  ],
  "files": [
$filesJson
  ]
}
"""
            rDir.resolve("$id.json").writeText(componentJson)
        }

        // Handle standalone files (e.g. PopupAnimation.kt)
        val standaloneFiles = uiDir.listFiles()
            ?.filter { it.isFile && it.extension == "kt" }
            ?.sortedBy { it.name }
            ?: emptyList()

        standaloneFiles.forEach { file ->
            val id = file.nameWithoutExtension
                .replace(Regex("([a-z])([A-Z])"), "$1-$2")
                .lowercase()
            val title = file.nameWithoutExtension
            val content = file.readText()

            val componentJson = """{
  "name": "$id",
  "type": "registry:utility",
  "title": "$title",
  "version": "$version",
  "registryDependencies": [],
  "gradleDependencies": [
    "dev.rikkaui:foundation:$version"
  ],
  "files": [
    {
      "name": "${file.name}",
      "path": "ui/${file.name}",
      "type": "registry:utility",
      "content": "${escapeJson(content)}"
    }
  ]
}
"""
            rDir.resolve("$id.json").writeText(componentJson)
        }

        // Write index.json
        val indexJson = """{
  "name": "rikkaui",
  "homepage": "https://rikkaui.dev",
  "version": "$version",
  "items": [
${indexEntries.joinToString(",\n")}
  ]
}
"""
        rDir.resolve("index.json").writeText(indexJson)
    }
}
