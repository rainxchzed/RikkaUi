package zed.rainxch.rikkaui.components.ui.separator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme

// ─── Orientation ────────────────────────────────────────────

/**
 * Separator orientation.
 *
 * - [Horizontal] — Full-width line divider. Default.
 * - [Vertical] — Full-height line divider. For side-by-side layouts.
 */
enum class SeparatorOrientation {
    Horizontal,
    Vertical,
}

// ─── Component ──────────────────────────────────────────────

/**
 * Separator component for the RikkaUi design system.
 *
 * A thin line that divides content visually. Maps to shadcn/ui's Separator.
 *
 * Usage:
 * ```
 * Column {
 *     Text("Section 1")
 *     Separator()
 *     Text("Section 2")
 * }
 *
 * Row {
 *     Text("Left")
 *     Separator(orientation = SeparatorOrientation.Vertical)
 *     Text("Right")
 * }
 * ```
 *
 * @param modifier Modifier for layout and decoration.
 * @param orientation Horizontal (default) or Vertical.
 * @param color Override color. Defaults to theme border color.
 * @param thickness Line thickness. Defaults to 1dp.
 */
@Composable
fun Separator(
    modifier: Modifier = Modifier,
    orientation: SeparatorOrientation = SeparatorOrientation.Horizontal,
    color: Color = Color.Unspecified,
    thickness: Dp = 1.dp,
) {
    val resolvedColor =
        if (color != Color.Unspecified) color else RikkaTheme.colors.border

    val sizeModifier =
        when (orientation) {
            SeparatorOrientation.Horizontal -> {
                Modifier
                    .fillMaxWidth()
                    .height(thickness)
            }

            SeparatorOrientation.Vertical -> {
                Modifier
                    .fillMaxHeight()
                    .width(thickness)
            }
        }

    Box(
        modifier =
            modifier
                .then(sizeModifier)
                .background(resolvedColor)
                .semantics { },
    )
}
