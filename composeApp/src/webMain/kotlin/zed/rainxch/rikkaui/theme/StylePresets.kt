package zed.rainxch.rikkaui.theme

import zed.rainxch.rikkaui.components.theme.RikkaStylePreset

/**
 * Resolves a [RikkaStylePreset] enum from a display name string.
 *
 * Used by the theme switcher UI which stores selection as a string.
 * Falls back to [RikkaStylePreset.Default] for unknown names.
 */
fun resolveStylePreset(name: String): RikkaStylePreset =
    RikkaStylePreset.entries.find { it.label == name }
        ?: RikkaStylePreset.Default
