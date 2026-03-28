package zed.rainxch.rikkaui.theme

import zed.rainxch.rikkaui.components.theme.RikkaAccentPreset
import zed.rainxch.rikkaui.components.theme.RikkaColors
import zed.rainxch.rikkaui.components.theme.RikkaPalette

/**
 * Resolves a [RikkaColors] palette by enum and dark-mode flag.
 *
 * ```
 * val colors = resolvePalette(RikkaPalette.Zinc, isDark = true)
 * ```
 */
fun resolvePalette(
    palette: RikkaPalette,
    isDark: Boolean,
): RikkaColors = palette.resolve(isDark)

/**
 * Applies an accent color overlay to a base palette.
 * Returns the base unchanged for [RikkaAccentPreset.Default].
 *
 * ```
 * val accented = resolveAccent(base, RikkaAccentPreset.Blue, isDark = true)
 * ```
 */
fun resolveAccent(
    base: RikkaColors,
    accent: RikkaAccentPreset,
    isDark: Boolean,
): RikkaColors = accent.applyTo(base, isDark)
