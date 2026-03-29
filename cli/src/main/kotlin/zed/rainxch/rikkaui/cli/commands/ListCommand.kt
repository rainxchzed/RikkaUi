package zed.rainxch.rikkaui.cli.commands

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.options.option
import zed.rainxch.rikkaui.cli.registry.ConfigManager
import zed.rainxch.rikkaui.cli.registry.RegistryClient

class ListCommand : CliktCommand(name = "list") {

    override fun help(context: Context) = "List all available components"

    private val registryFlag by option("--registry", "-r", help = "Registry URL")

    override fun run() {
        val config = ConfigManager.load()
        val registryUrl = registryFlag ?: config?.registry ?: "https://rikkaui.dev/r"
        val client = RegistryClient(registryUrl)

        echo("Fetching components from $registryUrl...")
        echo("")

        val index = try {
            client.fetchIndex()
        } catch (e: Exception) {
            echo("Error: Failed to fetch registry: ${e.message}", err = true)
            throw Abort()
        }

        echo("  RikkaUI v${index.version} — ${index.items.size} components")
        echo("")

        index.items.forEach { item ->
            val files = item.files.joinToString(", ") { it.name }
            echo("  ${item.name.padEnd(20)} $files")
        }

        echo("")
        echo("  Run 'rikkaui add <name>' to add a component.")
        echo("")
    }
}
