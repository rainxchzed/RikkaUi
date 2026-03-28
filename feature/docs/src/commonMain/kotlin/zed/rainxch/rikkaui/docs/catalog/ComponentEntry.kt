package zed.rainxch.rikkaui.docs.catalog

import androidx.compose.runtime.Composable

/**
 * Categories for grouping components in the sidebar.
 *
 * Mirrors shadcn/ui's implicit groupings but made explicit
 * for navigation and filtering.
 */
enum class ComponentCategory(val label: String) {
    Layout("Layout"),
    Forms("Forms"),
    DataDisplay("Data Display"),
    Feedback("Feedback"),
    Overlays("Overlays"),
    Navigation("Navigation"),
}

/**
 * A single component entry in the catalog.
 *
 * @param id URL-safe identifier (e.g. "button", "context-menu").
 * @param name Display name (e.g. "Button", "Context Menu").
 * @param description One-line description.
 * @param category Which sidebar group this belongs to.
 * @param content The full component documentation page composable.
 */
data class ComponentEntry(
    val id: String,
    val name: String,
    val description: String,
    val category: ComponentCategory,
    val content: @Composable () -> Unit,
)
