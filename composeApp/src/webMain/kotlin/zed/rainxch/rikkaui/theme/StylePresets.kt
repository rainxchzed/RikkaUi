package zed.rainxch.rikkaui.theme

import zed.rainxch.rikkaui.components.theme.RikkaStyle
import zed.rainxch.rikkaui.components.theme.RikkaStylePresets

/**
 * Re-exports from the component library for showcase convenience.
 *
 * The actual preset definitions live in `:components` at
 * [RikkaStylePresets] so that all library consumers can use them.
 */

/** Named style preset names shown in the theme switcher UI. */
val stylePresetNames: List<String> get() = RikkaStylePresets.names

/** Resolves a named style preset by name. */
fun resolveStyle(name: String): RikkaStyle = RikkaStylePresets.fromName(name)
