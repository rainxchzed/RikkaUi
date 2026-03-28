package zed.rainxch.rikkaui.components.ui.icon

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme

// ─── Size ───────────────────────────────────────────────────

/**
 * Icon size variants.
 *
 * - [Xs] — 12dp. Tiny inline indicators.
 * - [Sm] — 16dp. Compact icons for dense layouts.
 * - [Default] — 20dp. Standard size matching most text.
 * - [Lg] — 24dp. Prominent icons, toolbar actions.
 * - [Xl] — 32dp. Large standalone icons.
 */
enum class IconSize(
    val dp: Dp,
) {
    Xs(12.dp),
    Sm(16.dp),
    Default(20.dp),
    Lg(24.dp),
    Xl(32.dp),
}

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
 *     size = IconSize.Lg,
 * )
 *
 * // Spinning loading icon
 * Icon(
 *     imageVector = RikkaIcons.Settings,
 *     contentDescription = "Loading",
 *     spin = true,
 * )
 * ```
 *
 * @param imageVector The vector graphic to draw.
 * @param contentDescription Accessibility label. Pass `null` for decorative icons.
 * @param modifier Modifier applied to the root layout.
 * @param tint Color applied as a [ColorFilter] over the vector. Defaults to
 *   [RikkaTheme.colors.foreground].
 * @param size Icon size variant. When `null`, uses the vector's default dimensions.
 * @param spin When true, continuously rotates the icon. Useful for loading indicators
 *   or refresh icons.
 */
@Composable
fun Icon(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = RikkaTheme.colors.foreground,
    size: IconSize? = null,
    spin: Boolean = false,
) {
    val painter = rememberVectorPainter(imageVector)

    val semanticsModifier =
        if (contentDescription == null) {
            modifier.clearAndSetSemantics {}
        } else {
            modifier
        }

    val sizeModifier =
        if (size != null) {
            Modifier.size(size.dp)
        } else {
            Modifier.size(
                imageVector.defaultWidth,
                imageVector.defaultHeight,
            )
        }

    val spinModifier =
        if (spin) {
            val motion = RikkaTheme.motion
            val infiniteTransition = rememberInfiniteTransition()
            val rotation by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec =
                    infiniteRepeatable(
                        animation =
                            tween(
                                durationMillis = 800,
                                easing = LinearEasing,
                            ),
                    ),
            )
            Modifier.graphicsLayer { rotationZ = rotation }
        } else {
            Modifier
        }

    Box(
        modifier =
            semanticsModifier
                .then(sizeModifier)
                .then(spinModifier),
    ) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            colorFilter = ColorFilter.tint(tint),
        )
    }
}
