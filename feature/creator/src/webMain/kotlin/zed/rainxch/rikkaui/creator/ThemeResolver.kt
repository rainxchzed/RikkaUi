package zed.rainxch.rikkaui.creator

import zed.rainxch.rikkaui.foundation.RikkaAccentPreset
import zed.rainxch.rikkaui.foundation.RikkaColors
import zed.rainxch.rikkaui.foundation.RikkaPalette

/** Resolves a [RikkaColors] for the given palette and dark-mode flag. */
internal fun resolvePalette(
    palette: RikkaPalette,
    isDark: Boolean,
): RikkaColors = palette.resolve(isDark)

/**
 * Applies an accent color overlay to a base palette.
 * Returns the base unchanged for [RikkaAccentPreset.Default].
 */
internal fun resolveAccent(
    base: RikkaColors,
    accent: RikkaAccentPreset,
    isDark: Boolean,
): RikkaColors = accent.applyTo(base, isDark)
