package zed.rainxch.rikkaui.components.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * RikkaSpacing defines the spacing scale for consistent layout.
 *
 * Based on a 4dp base unit, following the same 4-point grid system
 * used by most modern design systems.
 */
@Immutable
data class RikkaSpacing(
    val xs: Dp,
    val sm: Dp,
    val md: Dp,
    val lg: Dp,
    val xl: Dp,
    val xxl: Dp,
    val xxxl: Dp,
)

val LocalRikkaSpacing = staticCompositionLocalOf<RikkaSpacing> {
    error("No RikkaSpacing provided. Wrap your content in RikkaTheme { ... }")
}

fun defaultRikkaSpacing(): RikkaSpacing = RikkaSpacing(
    xs = 4.dp,
    sm = 8.dp,
    md = 12.dp,
    lg = 16.dp,
    xl = 24.dp,
    xxl = 32.dp,
    xxxl = 48.dp,
)
