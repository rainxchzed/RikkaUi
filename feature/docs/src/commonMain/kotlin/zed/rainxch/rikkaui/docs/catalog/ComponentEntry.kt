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

enum class ComponentCategory(
    val labelRes: StringResource,
) {
    Layout(Res.string.category_layout),
    Forms(Res.string.category_forms),
    DataDisplay(Res.string.category_data_display),
    Feedback(Res.string.category_feedback),
    Overlays(Res.string.category_overlays),
    Navigation(Res.string.category_navigation),
}

data class ComponentEntry(
    val id: String,
    val rawName: String,
    val rawDescription: String,
    val nameRes: StringResource,
    val descriptionRes: StringResource,
    val category: ComponentCategory,
    val content: @Composable () -> Unit,
)
