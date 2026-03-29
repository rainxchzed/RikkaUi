package zed.rainxch.rikkaui.components.ui

/**
 * Animation style for popup-based components such as [Select][zed.rainxch.rikkaui.components.ui.select.Select],
 * [DropdownMenu][zed.rainxch.rikkaui.components.ui.dropdown.DropdownMenu], and
 * [ContextMenu][zed.rainxch.rikkaui.components.ui.contextmenu.ContextMenu].
 *
 * Controls how the popup content enters and exits.
 * All timed animations reference [RikkaTheme.motion][zed.rainxch.rikkaui.foundation.RikkaTheme]
 * tokens so they respect the active motion preset.
 *
 * Usage:
 * ```
 * Select(
 *     selectedValue = selected,
 *     onValueChange = { selected = it },
 *     options = options,
 *     animation = PopupAnimation.Fade,
 * )
 * ```
 */
enum class PopupAnimation {
    /**
     * Fade in combined with a vertical expand from the top edge.
     *
     * This is the default animation style. It uses
     * [RikkaMotion.durationFast] for the fade and
     * [RikkaMotion.durationDefault] for the expand/shrink.
     */
    FadeExpand,

    /**
     * Simple fade in / fade out with no size change.
     *
     * Uses [RikkaMotion.durationFast] for the fade transition.
     * Ideal for menus where the expand motion feels too heavy.
     */
    Fade,

    /**
     * Instant show/hide with no animation at all.
     *
     * Skips [AnimatedVisibility] entirely — the popup content
     * is shown or removed immediately. Useful for accessibility
     * contexts where motion is reduced, or for programmatic use
     * where speed matters.
     */
    None,
}
