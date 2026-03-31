package zed.rainxch.rikkaui.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

object RoutePaths {
    const val HOME = "home"
    const val CREATE = "create"
    const val DOCS = "docs"
    const val DOCS_GUIDE = "docs/guide"
    const val DOCS_COMPONENTS = "docs/components"
}

@Serializable
sealed class AppNavGraph {
    @Serializable
    @SerialName(RoutePaths.HOME)
    data object HomeRoute : AppNavGraph()

    @Serializable
    @SerialName(RoutePaths.CREATE)
    data object CreatorRoute : AppNavGraph()

    @Serializable
    @SerialName(RoutePaths.DOCS)
    data object DocsRoute : AppNavGraph()

    @Serializable
    @SerialName(RoutePaths.DOCS_GUIDE)
    data class DocsGuideRoute(
        val guideId: String,
    ) : AppNavGraph()

    @Serializable
    @SerialName(RoutePaths.DOCS_COMPONENTS)
    data class ComponentDetailRoute(
        val componentId: String,
    ) : AppNavGraph()
}
