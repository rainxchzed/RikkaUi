package zed.rainxch.rikkaui.cli.model

import kotlinx.serialization.Serializable

@Serializable
data class RikkaConfig(
    val foundation: String = "dev.rikkaui:foundation:0.1.0",
    val registry: String = "https://rikkaui.dev/r",
    val sourceSet: String = "commonMain",
    val packageName: String = "",
    val componentsDir: String = "",
)
