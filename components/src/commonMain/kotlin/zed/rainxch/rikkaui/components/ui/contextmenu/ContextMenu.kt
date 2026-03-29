package zed.rainxch.rikkaui.components.ui.contextmenu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import kotlinx.coroutines.delay
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.PopupAnimation

// ─── Component ──────────────────────────────────────────────

/**
 * Context menu component for the RikkaUi design system.
 *
 * A long-press-triggered overlay menu that renders a scrollable
 * list of actions anchored to a trigger area. On desktop/web this
 * maps to right-click; on mobile it triggers via long-press.
 *
 * The component manages its own open/close state internally.
 * Place [ContextMenuItem], [ContextMenuSeparator], and
 * [ContextMenuLabel] inside the [menuContent] lambda.
 *
 * Features:
 * - Long-press triggered (cross-platform)
 * - Outside-click dismiss via Popup
 * - Scrollable when content exceeds max height
 * - Hover highlight on menu items
 * - Trailing shortcut text support
 * - Separator and label sub-components
 * - Configurable animation, min/max width, and max height
 * - No Material3 dependency
 *
 * Usage:
 * ```
 * ContextMenu(
 *     menuContent = {
 *         ContextMenuLabel("Edit")
 *         ContextMenuItem("Cut", onClick = { cut() })
 *         ContextMenuItem(
 *             "Copy",
 *             onClick = { copy() },
 *             shortcut = "\u2318C",
 *         )
 *         ContextMenuSeparator()
 *         ContextMenuItem("Delete", onClick = { del() })
 *     },
 * ) {
 *     Card {
 *         Text("Long-press or right-click me")
 *     }
 * }
 *
 * // Instant popup:
 * ContextMenu(
 *     animation = PopupAnimation.None,
 *     menuContent = { ... },
 * ) { ... }
 * ```
 *
 * @param menuContent Column-scoped builder for items, labels, separators.
 * @param modifier Modifier applied to the outer trigger wrapper.
 * @param animation Controls how the popup enters and exits.
 *     Defaults to [PopupAnimation.FadeExpand].
 * @param minWidth Minimum width of the context menu panel. Defaults to 200.dp.
 * @param maxWidth Maximum width of the context menu panel. Defaults to 280.dp.
 * @param maxHeight Maximum height before the panel becomes scrollable.
 *     Defaults to 300.dp.
 * @param content The trigger area that responds to long-press / right-click.
 */
@Composable
fun ContextMenu(
    menuContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    animation: PopupAnimation = PopupAnimation.FadeExpand,
    minWidth: Dp = 200.dp,
    maxWidth: Dp = 280.dp,
    maxHeight: Dp = 300.dp,
    content: @Composable () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val motion = RikkaTheme.motion

    var showPopup by remember { mutableStateOf(false) }
    LaunchedEffect(expanded) {
        if (expanded) showPopup = true
    }

    Box(modifier = modifier) {
        Box(
            modifier =
                Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = { expanded = true },
                    )
                },
        ) {
            content()
        }

        if (showPopup) {
            Popup(
                alignment = Alignment.TopStart,
                onDismissRequest = { expanded = false },
            ) {
                ContextMenuPanel(
                    visible = expanded,
                    animation = animation,
                    minWidth = minWidth,
                    maxWidth = maxWidth,
                    maxHeight = maxHeight,
                    menuContent = menuContent,
                )

                if (!expanded) {
                    LaunchedEffect(Unit) {
                        delay(motion.durationDefault.toLong() + 50L)
                        showPopup = false
                    }
                }
            }
        }
    }
}

/**
 * Controlled context menu variant where the caller manages
 * open/close state externally.
 *
 * Usage:
 * ```
 * var open by remember { mutableStateOf(false) }
 *
 * ControlledContextMenu(
 *     expanded = open,
 *     onDismiss = { open = false },
 *     menuContent = {
 *         ContextMenuItem("Copy", onClick = { copy(); open = false })
 *     },
 * ) {
 *     Box(
 *         modifier = Modifier.pointerInput(Unit) {
 *             detectTapGestures(onLongPress = { open = true })
 *         },
 *     ) { Text("Right-click me") }
 * }
 * ```
 *
 * @param expanded Whether the context menu popup is visible.
 * @param onDismiss Called when the user clicks outside the popup.
 * @param menuContent Column-scoped builder for items, labels, separators.
 * @param modifier Modifier applied to the outer wrapper.
 * @param animation Controls how the popup enters and exits.
 *     Defaults to [PopupAnimation.FadeExpand].
 * @param minWidth Minimum width of the context menu panel. Defaults to 200.dp.
 * @param maxWidth Maximum width of the context menu panel. Defaults to 280.dp.
 * @param maxHeight Maximum height before the panel becomes scrollable.
 *     Defaults to 300.dp.
 * @param content The trigger area.
 */
@Composable
fun ControlledContextMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    menuContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    animation: PopupAnimation = PopupAnimation.FadeExpand,
    minWidth: Dp = 200.dp,
    maxWidth: Dp = 280.dp,
    maxHeight: Dp = 300.dp,
    content: @Composable () -> Unit,
) {
    val motion = RikkaTheme.motion

    var showPopup by remember { mutableStateOf(false) }
    LaunchedEffect(expanded) {
        if (expanded) showPopup = true
    }

    Box(modifier = modifier) {
        content()

        if (showPopup) {
            Popup(
                alignment = Alignment.TopStart,
                onDismissRequest = onDismiss,
            ) {
                ContextMenuPanel(
                    visible = expanded,
                    animation = animation,
                    minWidth = minWidth,
                    maxWidth = maxWidth,
                    maxHeight = maxHeight,
                    menuContent = menuContent,
                )

                if (!expanded) {
                    LaunchedEffect(Unit) {
                        delay(motion.durationDefault.toLong() + 50L)
                        showPopup = false
                    }
                }
            }
        }
    }
}

// ─── Internal: Animated panel ───────────────────────────────

/**
 * Shared panel rendering for both [ContextMenu] and
 * [ControlledContextMenu], applying the requested
 * [PopupAnimation].
 */
@Composable
private fun ContextMenuPanel(
    visible: Boolean,
    animation: PopupAnimation,
    minWidth: Dp,
    maxWidth: Dp,
    maxHeight: Dp,
    menuContent: @Composable ColumnScope.() -> Unit,
) {
    val colors = RikkaTheme.colors
    val shapes = RikkaTheme.shapes
    val spacing = RikkaTheme.spacing
    val motion = RikkaTheme.motion

    val panelContent: @Composable () -> Unit = {
        Column(
            modifier =
                Modifier
                    .defaultMinSize(
                        minWidth = minWidth,
                    ).widthIn(max = maxWidth)
                    .heightIn(max = maxHeight)
                    .shadow(
                        8.dp,
                        shapes.md,
                    ).border(
                        1.dp,
                        colors.border,
                        shapes.md,
                    ).background(
                        colors.popover,
                        shapes.md,
                    ).clip(shapes.md)
                    .verticalScroll(
                        rememberScrollState(),
                    ).padding(
                        vertical = spacing.xs,
                    ),
            content = menuContent,
        )
    }

    when (animation) {
        PopupAnimation.None -> panelContent()

        PopupAnimation.Fade -> {
            AnimatedVisibility(
                visible = visible,
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
                panelContent()
            }
        }

        PopupAnimation.FadeExpand -> {
            AnimatedVisibility(
                visible = visible,
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
                panelContent()
            }
        }
    }
}

// ─── Menu Item ──────────────────────────────────────────────

/**
 * A single actionable row inside a [ContextMenu].
 *
 * Highlights with the accent color on hover. Supports an
 * optional leading icon and trailing shortcut text.
 *
 * Usage:
 * ```
 * ContextMenuItem("Copy", onClick = { copy() }, shortcut = "\u2318C")
 * ContextMenuItem("Paste", onClick = { paste() }, enabled = false)
 * ```
 *
 * @param text The label displayed in the menu item.
 * @param onClick Called when the item is clicked.
 * @param modifier Modifier applied to the item row.
 * @param enabled Whether the item is interactive. Defaults to true.
 * @param leadingIcon Optional icon composable rendered before the label.
 * @param shortcut Optional trailing shortcut text (e.g. "\u2318C").
 */
@Composable
fun ContextMenuItem(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    shortcut: String? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val colors = RikkaTheme.colors
    val spacing = RikkaTheme.spacing

    val backgroundColor =
        if (isHovered && enabled) colors.accent else colors.popover

    val textColor =
        if (enabled) colors.popoverForeground else colors.mutedForeground

    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .hoverable(interactionSource)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    role = Role.Button,
                    onClick = onClick,
                ).background(backgroundColor)
                .padding(
                    horizontal = spacing.md,
                    vertical = spacing.sm,
                ).then(
                    if (!enabled) {
                        Modifier
                            .alpha(0.5f)
                            .semantics { disabled() }
                    } else {
                        Modifier
                    },
                ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement =
            Arrangement.spacedBy(spacing.sm),
    ) {
        if (leadingIcon != null) {
            leadingIcon()
        }

        BasicText(
            text = text,
            modifier = Modifier.weight(1f),
            style =
                RikkaTheme.typography.small.merge(
                    TextStyle(color = textColor),
                ),
        )

        if (shortcut != null) {
            BasicText(
                text = shortcut,
                style =
                    RikkaTheme.typography.small.merge(
                        TextStyle(
                            color = colors.mutedForeground,
                        ),
                    ),
            )
        }
    }
}

// ─── Separator ──────────────────────────────────────────────

/**
 * A thin horizontal divider inside a [ContextMenu].
 *
 * Usage:
 * ```
 * ContextMenuItem("Cut", onClick = { cut() })
 * ContextMenuSeparator()
 * ContextMenuItem("Paste", onClick = { paste() })
 * ```
 *
 * @param modifier Modifier applied to the separator line.
 */
@Composable
fun ContextMenuSeparator(modifier: Modifier = Modifier) {
    val spacing = RikkaTheme.spacing

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(vertical = spacing.xs)
                .height(1.dp)
                .background(RikkaTheme.colors.border),
    )
}

// ─── Label ──────────────────────────────────────────────────

/**
 * A non-clickable header label inside a [ContextMenu].
 *
 * Styled with bold text at the small size to visually group
 * related menu items.
 *
 * Usage:
 * ```
 * ContextMenuLabel("Actions")
 * ContextMenuItem("Edit", onClick = { edit() })
 * ContextMenuItem("Delete", onClick = { del() })
 * ```
 *
 * @param text The label text.
 * @param modifier Modifier applied to the label row.
 */
@Composable
fun ContextMenuLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    val spacing = RikkaTheme.spacing

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(
                    horizontal = spacing.md,
                    vertical = spacing.sm,
                ),
        contentAlignment = Alignment.CenterStart,
    ) {
        BasicText(
            text = text,
            style =
                RikkaTheme.typography.small.merge(
                    TextStyle(
                        color =
                            RikkaTheme.colors.popoverForeground,
                        fontWeight = FontWeight.SemiBold,
                    ),
                ),
        )
    }
}
