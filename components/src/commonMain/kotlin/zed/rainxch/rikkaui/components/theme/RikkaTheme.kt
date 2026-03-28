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
 *     colors = RikkaPalettes.ZincLight,  // or ZincDark, SlateLight, NeutralDark, etc.
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
    motion: RikkaMotion = defaultRikkaMotion(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalRikkaColors provides colors,
        LocalRikkaTypography provides typography,
        LocalRikkaSpacing provides spacing,
        LocalRikkaShapes provides shapes,
        LocalRikkaMotion provides motion,
        content = content,
    )
}

/**
 * Convenience overload that applies a [RikkaStyle] preset.
 *
 * Usage:
 * ```
 * val style = RikkaStylePresets.nova()
 * RikkaTheme(
 *     colors = RikkaPalettes.ZincDark,
 *     style = style,
 *     typography = rikkaTypography(myFont, scale = style.typeScale),
 * ) { ... }
 * ```
 *
 * The [style] provides shapes, spacing, and motion. You still supply
 * colors and typography separately since they depend on font/palette choice.
 */
@Composable
fun RikkaTheme(
    colors: RikkaColors = RikkaPalettes.NeutralLight,
    style: RikkaStyle = RikkaStylePresets.default(),
    typography: RikkaTypography = rikkaTypography(scale = style.typeScale),
    content: @Composable () -> Unit,
) {
    RikkaTheme(
        colors = colors,
        typography = typography,
        spacing = style.spacing,
        shapes = style.shapes,
        motion = style.motion,
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
 * val spring = RikkaTheme.motion.springDefault
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

    val motion: RikkaMotion
        @Composable
        @ReadOnlyComposable
        get() = LocalRikkaMotion.current
}
