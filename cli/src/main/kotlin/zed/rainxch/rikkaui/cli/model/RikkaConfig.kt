package zed.rainxch.rikkaui.cli.model

import kotlinx.serialization.Serializable
import zed.rainxch.rikkaui.cli.CliVersion

@Serializable
data class RikkaConfig(
    val foundation: String = CliVersion.FOUNDATION,
    val registry: String = "https://rikkaui.dev/r",
    val packageName: String = "",
    val componentsDir: String = "",
)
