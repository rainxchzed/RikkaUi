package zed.rainxch.rikkaui.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Top-level navigation destinations for the RikkaUi website.
 *
 * Each route is a `@Serializable` object/class for type-safe
 * navigation with Navigation 2.
 */

/** Showcase landing page. */
@Serializable
@SerialName("home")
data object HomeRoute

/** "Create Your Design System" page. */
@Serializable
@SerialName("create")
data object CreatorRoute

/** Docs root — shows the getting-started / overview page. */
@Serializable
@SerialName("docs")
data object DocsRoute

/** Component catalog — lists all component categories. */
@Serializable
@SerialName("docs/components")
data object ComponentsRoute

/**
 * Individual component detail page.
 *
 * @param componentId URL-safe component identifier (e.g. "button", "input").
 */
@Serializable
@SerialName("docs/components/{componentId}")
data class ComponentDetailRoute(val componentId: String)
