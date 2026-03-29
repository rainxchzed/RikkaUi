package zed.rainxch.rikkaui.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class AppNavGraph {
    @Serializable
    @SerialName("home")
    data object HomeRoute : AppNavGraph()

    @Serializable
    @SerialName("create")
    data object CreatorRoute : AppNavGraph()

    @Serializable
    @SerialName("docs")
    data object DocsRoute : AppNavGraph()

    @Serializable
    @SerialName("docs/{guideId}")
    data class DocsGuideRoute(
        val guideId: String,
    ) : AppNavGraph()

    @Serializable
    @SerialName("docs/components")
    data object ComponentsRoute : AppNavGraph()

    @Serializable
    @SerialName("docs/components/{componentId}")
    data class ComponentDetailRoute(
        val componentId: String,
    )
}
