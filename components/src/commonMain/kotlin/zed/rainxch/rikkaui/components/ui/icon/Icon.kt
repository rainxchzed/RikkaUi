package zed.rainxch.rikkaui.components.ui.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.semantics.clearAndSetSemantics
import zed.rainxch.rikkaui.components.theme.RikkaTheme

// ─── Component ──────────────────────────────────────────────

/**
 * Icon component for the RikkaUi design system.
 *
 * Renders an [ImageVector] with theme-aware tinting. Built on Foundation's
 * [Image] composable — no Material3 dependency.
 *
 * When [contentDescription] is `null` the icon is treated as decorative and
 * removed from the accessibility tree via [clearAndSetSemantics].
 *
 * Usage:
 * ```
 * // Semantic icon — announced by screen readers
 * Icon(
 *     imageVector = RikkaIcons.Search,
 *     contentDescription = "Search",
 * )
 *
 * // Decorative icon — hidden from accessibility tree
 * Icon(
 *     imageVector = RikkaIcons.ChevronRight,
 *     contentDescription = null,
 * )
 *
 * // Custom tint and size
 * Icon(
 *     imageVector = RikkaIcons.Heart,
 *     contentDescription = "Favorite",
 *     tint = RikkaTheme.colors.destructive,
 *     modifier = Modifier.size(32.dp),
 * )
 * ```
 *
 * @param imageVector The vector graphic to draw.
 * @param contentDescription Accessibility label. Pass `null` for decorative icons.
 * @param modifier Modifier applied to the root layout.
 * @param tint Color applied as a [ColorFilter] over the vector. Defaults to
 *   [RikkaTheme.colors.foreground].
 */
@Composable
fun Icon(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = RikkaTheme.colors.foreground,
) {
    val painter = rememberVectorPainter(imageVector)

    val semanticsModifier =
        if (contentDescription == null) {
            modifier.clearAndSetSemantics {}
        } else {
            modifier
        }

    Box(
        modifier =
            semanticsModifier
                .size(imageVector.defaultWidth, imageVector.defaultHeight),
    ) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            colorFilter = ColorFilter.tint(tint),
        )
    }
}
