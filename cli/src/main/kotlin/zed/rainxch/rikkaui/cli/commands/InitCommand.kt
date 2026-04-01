package zed.rainxch.rikkaui.cli.commands

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.options.option
import zed.rainxch.rikkaui.cli.model.RikkaConfig
import zed.rainxch.rikkaui.cli.registry.ConfigManager
import java.io.File

class InitCommand : CliktCommand(name = "init") {

    override fun help(context: Context) = "Initialize RikkaUI in your project"

    private val packageFlag by option("--package", "-p", help = "Target package name")

    private val pathFlag by option("--path", help = "Components directory (relative to project root)")

    override fun run() {
        if (ConfigManager.exists()) {
            echo("rikka.json already exists. Overwrite? (y/N) ", trailingNewline = false)
            val answer = readlnOrNull()?.trim()?.lowercase()
            if (answer != "y") {
                echo("Aborted.")
                return
            }
        }

        val projectDir = File(System.getProperty("user.dir"))

        // ─── Package name ───────────────────────────────────
        val packageName = packageFlag ?: run {
            val detected = detectPackageName(projectDir)
            if (detected != null) {
                echo("")
                echo("  Detected package: $detected")
                echo("  Package name [$detected]: ", trailingNewline = false)
                val input = readlnOrNull()?.trim()
                if (input.isNullOrBlank()) detected else input
            } else {
                echo("")
                echo("  Package name (e.g. com.myapp.ui): ", trailingNewline = false)
                val input = readlnOrNull()?.trim()
                if (input.isNullOrBlank()) {
                    echo("Error: Package name is required.", err = true)
                    throw Abort()
                }
                input
            }
        }

        // ─── Components directory ───────────────────────────
        val componentsDir = pathFlag ?: run {
            val candidates = scanSourceDirs(projectDir)
            val packagePath = packageName.replace('.', '/')

            if (candidates.isNotEmpty()) {
                echo("")
                echo("  Where should components be added?")
                echo("")
                candidates.forEachIndexed { index, dir ->
                    val fullPath = "$dir/$packagePath"
                    echo("  ${index + 1}) $fullPath")
                }
                echo("  ${candidates.size + 1}) Enter custom path")
                echo("")
                echo("  Choose [1]: ", trailingNewline = false)
                val input = readlnOrNull()?.trim()
                val choice = if (input.isNullOrBlank()) 1 else input.toIntOrNull() ?: 1

                if (choice in 1..candidates.size) {
                    "${candidates[choice - 1]}/$packagePath"
                } else {
                    promptCustomPath()
                }
            } else {
                echo("")
                echo("  No source directories detected.")
                promptCustomPath()
            }
        }

        val config = RikkaConfig(
            packageName = packageName,
            componentsDir = componentsDir,
        )

        ConfigManager.save(config)

        echo("")
        echo("  Created rikka.json")
        echo("")
        echo("  Package:    $packageName")
        echo("  Components: $componentsDir")
        echo("")
        echo("  Add foundation to your build.gradle.kts:")
        echo("  implementation(\"${config.foundation}\")")
        echo("")
        echo("  Then run: rikkaui add button")
        echo("")
    }

    /**
     * Scans the project tree for Kotlin source directories.
     *
     * Looks for `src/{sourceSet}/kotlin` paths under any module,
     * returning them as paths relative to the project root.
     * Common source sets like commonMain are prioritized first.
     */
    private fun scanSourceDirs(projectDir: File): List<String> {
        val sourceSets = listOf("commonMain", "main", "webMain", "desktopMain", "androidMain")
        val found = mutableListOf<String>()

        // Walk up to 4 levels deep to find module directories with src/ folders
        projectDir.walk()
            .maxDepth(4)
            .filter { it.isDirectory && it.name == "kotlin" }
            .forEach { kotlinDir ->
                // Verify it's inside a src/{sourceSet}/kotlin structure
                val sourceSetDir = kotlinDir.parentFile ?: return@forEach
                val srcDir = sourceSetDir.parentFile ?: return@forEach
                if (srcDir.name != "src") return@forEach

                val relativePath = kotlinDir.relativeTo(projectDir).path
                // Skip build directories and generated code
                if (relativePath.contains("build/") || relativePath.contains("buildSrc/")) return@forEach

                found.add(relativePath)
            }

        // Sort: prioritize commonMain, then main, then others; shorter paths first within same priority
        return found.sortedWith(
            compareBy<String> { path ->
                val sourceSet = sourceSets.indexOfFirst { path.contains("/$it/") || path.contains("\\$it\\") }
                if (sourceSet >= 0) sourceSet else sourceSets.size
            }.thenBy { it.length }
        ).distinct()
    }

    private fun promptCustomPath(): String {
        echo("  Components directory (e.g. composeApp/src/commonMain/kotlin/com/myapp): ", trailingNewline = false)
        val input = readlnOrNull()?.trim()
        if (input.isNullOrBlank()) {
            echo("Error: Components directory is required.", err = true)
            throw Abort()
        }
        return input
    }

    private fun detectPackageName(projectDir: File): String? {
        // Try parsing android namespace from build.gradle.kts
        val buildFile = projectDir.resolve("build.gradle.kts")
        if (buildFile.exists()) {
            val content = buildFile.readText()
            val namespaceMatch = Regex("""namespace\s*=\s*"([^"]+)"""").find(content)
            if (namespaceMatch != null) return namespaceMatch.groupValues[1]
        }

        // Try app/build.gradle.kts for multi-module projects
        val appBuildFile = projectDir.resolve("app/build.gradle.kts")
        if (appBuildFile.exists()) {
            val content = appBuildFile.readText()
            val namespaceMatch = Regex("""namespace\s*=\s*"([^"]+)"""").find(content)
            if (namespaceMatch != null) return namespaceMatch.groupValues[1]
        }

        // Try composeApp/build.gradle.kts for KMP projects
        val composeAppBuildFile = projectDir.resolve("composeApp/build.gradle.kts")
        if (composeAppBuildFile.exists()) {
            val content = composeAppBuildFile.readText()
            val namespaceMatch = Regex("""namespace\s*=\s*"([^"]+)"""").find(content)
            if (namespaceMatch != null) return namespaceMatch.groupValues[1]
        }

        return null
    }
}
