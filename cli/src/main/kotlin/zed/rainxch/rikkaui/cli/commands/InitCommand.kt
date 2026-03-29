package zed.rainxch.rikkaui.cli.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import zed.rainxch.rikkaui.cli.model.RikkaConfig
import zed.rainxch.rikkaui.cli.registry.ConfigManager
import java.io.File

class InitCommand : CliktCommand(name = "init") {

    override fun help(context: Context) = "Initialize RikkaUI in your project"

    private val packageName by option("--package", "-p", help = "Target package name")
        .prompt("Package name (e.g. com.myapp.ui)")

    private val sourceSet by option("--source-set", "-s", help = "Kotlin source set")
        .default("commonMain")

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
        val isKmp = projectDir.resolve("src/$sourceSet").exists() ||
            projectDir.walk().maxDepth(3).any { it.name == sourceSet && it.isDirectory }

        val componentsDir = if (isKmp) {
            "src/$sourceSet/kotlin/${packageName.replace('.', '/')}"
        } else {
            "src/main/kotlin/${packageName.replace('.', '/')}"
        }

        val config = RikkaConfig(
            packageName = packageName,
            componentsDir = componentsDir,
            sourceSet = sourceSet,
        )

        ConfigManager.save(config)

        echo("")
        echo("  Created rikka.json")
        echo("")
        echo("  Package:    $packageName")
        echo("  Components: $componentsDir")
        echo("  Source set: $sourceSet")
        echo("")
        echo("  Add foundation to your build.gradle.kts:")
        echo("  implementation(\"${config.foundation}\")")
        echo("")
        echo("  Then run: rikkaui add button")
        echo("")
    }
}
