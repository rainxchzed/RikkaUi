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
        val packageName = packageFlag ?: promptPackageName(projectDir)

        // ─── Components directory ───────────────────────────
        val componentsDir = pathFlag ?: promptComponentsDir(projectDir, packageName)

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

    private fun promptPackageName(projectDir: File): String {
        val detected = ProjectDetector.detectPackageName(projectDir)
        if (detected != null) {
            echo("")
            echo("  Detected package: $detected")
            echo("  Package name [$detected]: ", trailingNewline = false)
            val input = readlnOrNull()?.trim()
            return if (input.isNullOrBlank()) detected else input
        }

        echo("")
        echo("  Package name (e.g. com.myapp.ui): ", trailingNewline = false)
        val input = readlnOrNull()?.trim()
        if (input.isNullOrBlank()) {
            echo("Error: Package name is required.", err = true)
            throw Abort()
        }
        return input
    }

    private fun promptComponentsDir(projectDir: File, packageName: String): String {
        val candidates = ProjectDetector.scanSourceDirs(projectDir)
        val packagePath = packageName.replace('.', '/')

        if (candidates.isNotEmpty()) {
            echo("")
            echo("  Where should components be added?")
            echo("")
            candidates.forEachIndexed { index, dir ->
                echo("  ${index + 1}) $dir/$packagePath")
            }
            echo("  ${candidates.size + 1}) Browse folders...")
            echo("")
            echo("  Choose [1]: ", trailingNewline = false)
            val input = readlnOrNull()?.trim()
            val choice = if (input.isNullOrBlank()) 1 else input.toIntOrNull() ?: 1

            return if (choice in 1..candidates.size) {
                "${candidates[choice - 1]}/$packagePath"
            } else {
                browseFolders(projectDir) ?: throw Abort()
            }
        }

        echo("")
        echo("  No source directories detected.")
        return browseFolders(projectDir) ?: throw Abort()
    }

    private fun browseFolders(projectDir: File): String? {
        val selected = FolderBrowser.browse(
            projectRoot = projectDir,
            echo = { msg, newline -> echo(msg, trailingNewline = newline) },
        )

        if (selected == null) {
            echo("  Cancelled.")
            return null
        }

        echo("")
        echo("  Selected: $selected")
        return selected
    }
}
