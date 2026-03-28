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
 * Convenience overload that applies a [RikkaStyle] directly.
 *
 * Usage:
 * ```
 * RikkaTheme(
 *     colors = RikkaPalettes.ZincDark,
 *     style = RikkaStylePreset.Nova.style,
 *     typography = rikkaTypography(myFont, scale = RikkaStylePreset.Nova.typeScale),
 * ) { ... }
 * ```
 *
 * The [style] provides shapes, spacing, and motion. You still supply
 * colors and typography separately since they depend on font/palette choice.
 */
@Composable
fun RikkaTheme(
    colors: RikkaColors = RikkaPalettes.NeutralLight,
    style: RikkaStyle = RikkaStylePreset.Default.style,
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
 * Convenience overload that applies a [RikkaStylePreset] enum directly.
 *
 * The simplest way to theme your entire app:
 * ```
 * RikkaTheme(
 *     colors = RikkaPalettes.ZincDark,
 *     preset = RikkaStylePreset.Nova,
 * ) { ... }
 * ```
 */
@Composable
fun RikkaTheme(
    colors: RikkaColors = RikkaPalettes.NeutralLight,
    preset: RikkaStylePreset = RikkaStylePreset.Default,
    typography: RikkaTypography = rikkaTypography(scale = preset.typeScale),
    content: @Composable () -> Unit,
) {
    RikkaTheme(
        colors = colors,
        typography = typography,
        spacing = preset.spacing,
        shapes = preset.shapes,
        motion = preset.motion,
        content = content,
    )
}

/**
 * All-in-one overload: palette + accent + dark mode in a single call.
 *
 * The simplest way to set up a fully themed app:
 * ```
 * RikkaTheme(
 *     palette = RikkaPalette.Zinc,
 *     accent = RikkaAccentPreset.Blue,
 *     isDark = true,
 *     preset = RikkaStylePreset.Vega,
 * ) { ... }
 * ```
 */
@Composable
fun RikkaTheme(
    palette: RikkaPalette = RikkaPalette.Zinc,
    accent: RikkaAccentPreset = RikkaAccentPreset.Default,
    isDark: Boolean = false,
    preset: RikkaStylePreset = RikkaStylePreset.Default,
    typography: RikkaTypography = rikkaTypography(
        scale = preset.typeScale,
    ),
    content: @Composable () -> Unit,
) {
    val colors = accent.applyTo(palette.resolve(isDark), isDark)
    RikkaTheme(
        colors = colors,
        typography = typography,
        spacing = preset.spacing,
        shapes = preset.shapes,
        motion = preset.motion,
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
