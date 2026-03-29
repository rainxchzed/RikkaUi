package zed.rainxch.rikkaui.components.ui.select

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import zed.rainxch.rikkaui.components.ui.PopupAnimation
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Data ───────────────────────────────────────────────────

/**
 * Represents a single option in a [Select] dropdown.
 *
 * @param value The internal value used for selection logic.
 * @param label The human-readable label displayed in the dropdown.
 */
data class SelectOption(
    val value: String,
    val label: String,
)

// ─── Component ──────────────────────────────────────────────

/**
 * Dropdown select component for the RikkaUi design system.
 *
 * A custom dropdown that replaces HTML `<select>` and Material3's
 * ExposedDropdownMenuBox. Uses Rikka theme tokens for styling with
 * hover highlights and selection indicators.
 *
 * Features:
 * - Input-like trigger with chevron indicator
 * - Animated popup dropdown with smooth expand/fade
 * - Hover highlighting and check mark for selected option
 * - Popup width matches trigger width exactly
 * - Full keyboard and accessibility support
 * - No Material dependency
 *
 * Usage:
 * ```
 * val options = listOf(
 *     SelectOption("light", "Light"),
 *     SelectOption("dark", "Dark"),
 *     SelectOption("system", "System"),
 * )
 * var selected by remember { mutableStateOf("") }
 *
 * Select(
 *     selectedValue = selected,
 *     onValueChange = { selected = it },
 *     options = options,
 *     placeholder = "Choose theme...",
 *     label = "Theme selector",
 * )
 *
 * // Fade-only animation with custom max height:
 * Select(
 *     selectedValue = selected,
 *     onValueChange = { selected = it },
 *     options = options,
 *     animation = PopupAnimation.Fade,
 *     maxHeight = 300.dp,
 * )
 * ```
 *
 * @param selectedValue The currently selected value
 *     (matches [SelectOption.value]).
 * @param onValueChange Called with the new value when
 *     the user selects an option.
 * @param options The list of available options.
 * @param modifier Modifier applied to the trigger.
 * @param placeholder Text shown when no option is selected.
 * @param enabled Whether the select is interactive.
 * @param label Accessibility label for screen readers.
 * @param animation Controls how the dropdown popup enters and exits.
 *     Defaults to [PopupAnimation.FadeExpand].
 * @param maxHeight Maximum height of the dropdown list before scrolling.
 *     Defaults to 200.dp.
 */
@Composable
fun Select(
    selectedValue: String,
    onValueChange: (String) -> Unit,
    options: List<SelectOption>,
    modifier: Modifier = Modifier,
    placeholder: String = "Select...",
    enabled: Boolean = true,
    label: String = "",
    animation: PopupAnimation = PopupAnimation.FadeExpand,
    maxHeight: Dp = 200.dp,
) {
    val colors = RikkaTheme.colors
    val shapes = RikkaTheme.shapes
    val spacing = RikkaTheme.spacing
    val motion = RikkaTheme.motion

    var expanded by remember { mutableStateOf(false) }
    var triggerWidth by remember { mutableStateOf(0) }
    val density = LocalDensity.current

    val selectedOption = options.find { it.value == selectedValue }
    val displayText = selectedOption?.label ?: placeholder
    val isPlaceholder = selectedOption == null

    val accessibilityLabel = label.ifEmpty { placeholder }

    // ─── Trigger ─────────────────────────────────────
    Box(modifier = modifier) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 40.dp)
                    .onGloballyPositioned { coordinates ->
                        triggerWidth = coordinates.size.width
                    }.border(1.dp, colors.input, shapes.md)
                    .background(colors.background, shapes.md)
                    .clip(shapes.md)
                    .clickable(
                        interactionSource =
                            remember { MutableInteractionSource() },
                        indication = null,
                        enabled = enabled,
                        role = Role.DropdownList,
                        onClick = { expanded = !expanded },
                    ).padding(
                        horizontal = spacing.md,
                        vertical = spacing.sm,
                    ).then(
                        if (!enabled) {
                            Modifier.alpha(0.5f)
                        } else {
                            Modifier
                        },
                    ).semantics(mergeDescendants = true) {
                        if (accessibilityLabel.isNotEmpty()) {
                            contentDescription =
                                accessibilityLabel
                        }
                        if (!enabled) {
                            disabled()
                        }
                    },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = displayText,
                variant = TextVariant.P,
                color =
                    if (isPlaceholder) {
                        colors.mutedForeground
                    } else {
                        colors.foreground
                    },
                modifier = Modifier.weight(1f),
                maxLines = 1,
            )
            Icon(
                imageVector = RikkaIcons.ChevronDown,
                contentDescription = null,
                tint = colors.mutedForeground,
                modifier = Modifier.size(16.dp),
            )
        }

        // ─── Dropdown popup ──────────────────────────
        if (expanded) {
            val triggerWidthDp =
                with(density) { triggerWidth.toDp() }

            Popup(
                onDismissRequest = { expanded = false },
                popupPositionProvider =
                DropdownPositionProvider,
            ) {
                SelectDropdownContent(
                    animation = animation,
                    triggerWidthDp = triggerWidthDp,
                    maxHeight = maxHeight,
                    options = options,
                    selectedValue = selectedValue,
                    onValueChange = onValueChange,
                    onDismiss = { expanded = false },
                )
            }
        }
    }
}

// ─── Internal: Animated dropdown wrapper ────────────────────

/**
 * Renders the dropdown list content with the requested
 * [PopupAnimation] style.
 */
@Composable
private fun SelectDropdownContent(
    animation: PopupAnimation,
    triggerWidthDp: Dp,
    maxHeight: Dp,
    options: List<SelectOption>,
    selectedValue: String,
    onValueChange: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val colors = RikkaTheme.colors
    val shapes = RikkaTheme.shapes
    val spacing = RikkaTheme.spacing
    val motion = RikkaTheme.motion

    val listContent: @Composable () -> Unit = {
        LazyColumn(
            modifier =
                Modifier
                    .width(triggerWidthDp)
                    .heightIn(max = maxHeight)
                    .shadow(8.dp, shapes.md)
                    .border(
                        1.dp,
                        colors.border,
                        shapes.md,
                    ).background(
                        colors.popover,
                        shapes.md,
                    ).clip(shapes.md)
                    .padding(vertical = spacing.xs),
        ) {
            items(options) { option ->
                SelectItem(
                    option = option,
                    isSelected =
                        option.value == selectedValue,
                    onClick = {
                        onValueChange(option.value)
                        onDismiss()
                    },
                )
            }
        }
    }

    when (animation) {
        PopupAnimation.None -> {
            listContent()
        }

        PopupAnimation.Fade -> {
            AnimatedVisibility(
                visible = true,
                enter =
                    fadeIn(
                        animationSpec =
                            tween(
                                motion.durationFast,
                            ),
                    ),
                exit =
                    fadeOut(
                        animationSpec =
                            tween(
                                motion.durationFast,
                            ),
                    ),
            ) {
                listContent()
            }
        }

        PopupAnimation.FadeExpand -> {
            AnimatedVisibility(
                visible = true,
                enter =
                    fadeIn(
                        animationSpec =
                            tween(
                                motion.durationFast,
                            ),
                    ) +
                        expandVertically(
                            animationSpec =
                                tween(
                                    motion.durationDefault,
                                ),
                            expandFrom = Alignment.Top,
                        ),
                exit =
                    fadeOut(
                        animationSpec =
                            tween(
                                motion.durationFast,
                            ),
                    ) +
                        shrinkVertically(
                            animationSpec =
                                tween(
                                    motion.durationDefault,
                                ),
                            shrinkTowards = Alignment.Top,
                        ),
            ) {
                listContent()
            }
        }
    }
}

// ─── Internal: Select Item ──────────────────────────────────

/**
 * A single option row within the dropdown.
 */
@Composable
private fun SelectItem(
    option: SelectOption,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val colors = RikkaTheme.colors
    val spacing = RikkaTheme.spacing
    val motion = RikkaTheme.motion

    val interactionSource =
        remember { MutableInteractionSource() }
    val isHovered by
        interactionSource.collectIsHoveredAsState()

    val backgroundColor by animateColorAsState(
        targetValue =
            when {
                isHovered -> colors.accent
                else -> colors.popover
            },
        animationSpec = tween(motion.durationFast),
    )

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick,
                ).padding(
                    horizontal = spacing.md,
                    vertical = spacing.sm,
                ),
        horizontalArrangement =
            Arrangement.spacedBy(spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Check mark for selected item
        if (isSelected) {
            Icon(
                imageVector = RikkaIcons.Check,
                contentDescription = null,
                tint = colors.foreground,
                modifier = Modifier.size(16.dp),
            )
        } else {
            Spacer(modifier = Modifier.width(16.dp))
        }
        Text(
            text = option.label,
            variant = TextVariant.P,
            color =
                if (isHovered) {
                    colors.accentForeground
                } else {
                    colors.popoverForeground
                },
            maxLines = 1,
        )
    }
}

// ─── Internal: Position Provider ────────────────────────────

/**
 * Positions the dropdown popup directly below the trigger,
 * with a 4px gap.
 */
private object DropdownPositionProvider : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize,
    ): IntOffset {
        val x = anchorBounds.left
        val y = anchorBounds.bottom + 4
        return IntOffset(x = x, y = y)
    }
}
