package zed.rainxch.rikkaui.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("home")
data object HomeRoute

@Serializable
@SerialName("create")
data object CreatorRoute

@Serializable
@SerialName("docs")
data object DocsRoute

@Serializable
@SerialName("docs/components")
data object ComponentsRoute

@Serializable
@SerialName("docs/components/{componentId}")
data class ComponentDetailRoute(
    val componentId: String,
)
