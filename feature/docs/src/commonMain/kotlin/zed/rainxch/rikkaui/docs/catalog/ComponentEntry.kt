package zed.rainxch.rikkaui.docs.catalog

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import rikkaui.feature.docs.generated.resources.Res
import rikkaui.feature.docs.generated.resources.category_data_display
import rikkaui.feature.docs.generated.resources.category_feedback
import rikkaui.feature.docs.generated.resources.category_forms
import rikkaui.feature.docs.generated.resources.category_layout
import rikkaui.feature.docs.generated.resources.category_navigation
import rikkaui.feature.docs.generated.resources.category_overlays

/**
 * Categories for grouping components in the sidebar.
 *
 * Mirrors shadcn/ui's implicit groupings but made explicit
 * for navigation and filtering.
 */
enum class ComponentCategory(val labelRes: StringResource) {
    Layout(Res.string.category_layout),
    Forms(Res.string.category_forms),
    DataDisplay(Res.string.category_data_display),
    Feedback(Res.string.category_feedback),
    Overlays(Res.string.category_overlays),
    Navigation(Res.string.category_navigation),
}

/**
 * A single component entry in the catalog.
 *
 * @param id URL-safe identifier (e.g. "button", "context-menu").
 * @param nameRes String resource for the display name.
 * @param descriptionRes String resource for the one-line description.
 * @param category Which sidebar group this belongs to.
 * @param content The full component documentation page composable.
 */
data class ComponentEntry(
    val id: String,
    val nameRes: StringResource,
    val descriptionRes: StringResource,
    val category: ComponentCategory,
    val content: @Composable () -> Unit,
)
