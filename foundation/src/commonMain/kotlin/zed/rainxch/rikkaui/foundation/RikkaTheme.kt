package zed.rainxch.rikkaui.foundation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.foundation.modifier.LocalMinTouchTarget

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
    minTouchTarget: Dp = 48.dp,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalRikkaColors provides colors,
        LocalRikkaTypography provides typography,
        LocalRikkaSpacing provides spacing,
        LocalRikkaShapes provides shapes,
        LocalRikkaMotion provides motion,
        LocalMinTouchTarget provides minTouchTarget,
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
    style: RikkaStyle,
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
    preset: RikkaStylePreset,
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
    palette: RikkaPalette,
    accent: RikkaAccentPreset = RikkaAccentPreset.Default,
    isDark: Boolean = false,
    preset: RikkaStylePreset = RikkaStylePreset.Default,
    typography: RikkaTypography =
        rikkaTypography(
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

    val minTouchTarget: Dp
        @Composable
        @ReadOnlyComposable
        get() = LocalMinTouchTarget.current
}

/**
 * Returns the appropriate content (foreground) color for the given [backgroundColor].
 *
 * Matches against the current theme's color tokens to find the corresponding
 * foreground color. Falls back to [RikkaColors.foreground] if no match is found.
 *
 * ### Usage
 * ```
 * val bg = RikkaTheme.colors.primary
 * val fg = contentColorFor(bg) // → primaryForeground
 * ```
 */
@Composable
@ReadOnlyComposable
fun contentColorFor(backgroundColor: Color): Color {
    val colors = RikkaTheme.colors
    return when (backgroundColor) {
        colors.background -> colors.foreground
        colors.card -> colors.cardForeground
        colors.popover -> colors.popoverForeground
        colors.primary -> colors.primaryForeground
        colors.secondary -> colors.secondaryForeground
        colors.muted -> colors.mutedForeground
        colors.accent -> colors.accentForeground
        colors.destructive -> colors.destructiveForeground
        colors.warning -> colors.warningForeground
        colors.success -> colors.successForeground
        colors.inverseSurface -> colors.inverseOnSurface
        colors.primaryContainer -> colors.primaryContainerForeground
        colors.destructiveContainer -> colors.destructiveContainerForeground
        else -> colors.foreground
    }
}
