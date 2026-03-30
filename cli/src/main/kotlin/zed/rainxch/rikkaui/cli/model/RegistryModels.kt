package zed.rainxch.rikkaui.cli.model

import kotlinx.serialization.Serializable

@Serializable
data class RegistryIndex(
    val name: String,
    val homepage: String,
    val version: String,
    val items: List<RegistryIndexItem>,
)

@Serializable
data class RegistryIndexItem(
    val name: String,
    val type: String,
    val title: String,
    val description: String = "",
    val category: String = "",
    val registryDependencies: List<String> = emptyList(),
    val files: List<RegistryFileMeta>,
)

@Serializable
data class RegistryFileMeta(
    val name: String,
    val path: String,
    val type: String,
)

@Serializable
data class RegistryComponent(
    val name: String,
    val type: String,
    val title: String,
    val description: String = "",
    val category: String = "",
    val version: String,
    val registryDependencies: List<String> = emptyList(),
    val gradleDependencies: List<String> = emptyList(),
    val files: List<RegistryFile>,
)

@Serializable
data class RegistryFile(
    val name: String,
    val path: String,
    val type: String,
    val content: String,
)
