package zed.rainxch.rikkaui.cli.registry

import kotlinx.serialization.json.Json
import zed.rainxch.rikkaui.cli.model.RegistryComponent
import zed.rainxch.rikkaui.cli.model.RegistryIndex
import java.net.URI

val json = Json { ignoreUnknownKeys = true }

class RegistryClient(private val baseUrl: String) {

    fun fetchIndex(): RegistryIndex {
        val body = fetch("$baseUrl/index.json")
        return json.decodeFromString<RegistryIndex>(body)
    }

    fun fetchComponent(name: String): RegistryComponent {
        val body = fetch("$baseUrl/$name.json")
        return json.decodeFromString<RegistryComponent>(body)
    }

    /**
     * Recursively resolves all dependencies for a set of component names.
     * Returns a flat list in install order (dependencies first).
     */
    fun resolveTree(names: List<String>): List<RegistryComponent> {
        val resolved = mutableMapOf<String, RegistryComponent>()
        val order = mutableListOf<String>()

        fun visit(name: String) {
            if (name in resolved) return
            val component = fetchComponent(name)
            resolved[name] = component
            component.registryDependencies.forEach { visit(it) }
            order.add(name)
        }

        names.forEach { visit(it) }
        return order.map { resolved.getValue(it) }
    }

    private fun fetch(url: String): String {
        val connection = URI(url).toURL().openConnection()
        connection.connectTimeout = 10_000
        connection.readTimeout = 10_000
        return connection.getInputStream().bufferedReader().readText()
    }
}
