package zed.rainxch.rikkaui.components.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

/**
 * RikkaTheme is the top-level theme composable for the RikkaUi design system.
 *
 * Usage:
 * ```
 * RikkaTheme(
 *     colors = RikkaPalettes.NeutralLight,  // or NeutralDark, ZincLight, RoseLight, etc.
 * ) {
 *     // Your app content — access tokens via RikkaTheme.colors, RikkaTheme.typography, etc.
 *     Button(...)
 * }
 * ```
 *
 * All parameters have sensible defaults (Neutral light palette).
 */
@Composable
fun RikkaTheme(
    colors: RikkaColors = RikkaPalettes.NeutralLight,
    typography: RikkaTypography = defaultRikkaTypography(),
    spacing: RikkaSpacing = defaultRikkaSpacing(),
    shapes: RikkaShapes = defaultRikkaShapes(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalRikkaColors provides colors,
        LocalRikkaTypography provides typography,
        LocalRikkaSpacing provides spacing,
        LocalRikkaShapes provides shapes,
        content = content,
    )
}

/**
 * Access point for the current RikkaUi theme values.
 *
 * Usage:
 * ```
 * val primary = RikkaTheme.colors.primary
 * val heading = RikkaTheme.typography.h1
 * val padding = RikkaTheme.spacing.lg
 * val rounded = RikkaTheme.shapes.md
 * ```
 */
object RikkaTheme {

    val colors: RikkaColors
        @Composable
        @ReadOnlyComposable
        get() = LocalRikkaColors.current

    val typography: RikkaTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalRikkaTypography.current

    val spacing: RikkaSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalRikkaSpacing.current

    val shapes: RikkaShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalRikkaShapes.current
}
