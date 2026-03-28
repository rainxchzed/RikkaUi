package zed.rainxch.rikkaui.components.ui.dropdown

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import kotlinx.coroutines.delay
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.PopupAnimation
import zed.rainxch.rikkaui.components.ui.text.TextVariant

// ─── Component ──────────────────────────────────────────────

/**
 * Dropdown menu component for the RikkaUi design system.
 *
 * A click-triggered overlay menu that renders a scrollable list of
 * actions anchored to a trigger element. Maps to shadcn/ui's
 * Dropdown Menu.
 *
 * The caller controls visibility via the [expanded] parameter.
 * Place [DropdownMenuItem], [DropdownMenuSeparator], and
 * [DropdownMenuLabel] inside the [content] lambda to build the menu.
 *
 * Features:
 * - Click-triggered with outside-click dismiss
 * - Scrollable when content exceeds max height
 * - Hover highlight on menu items
 * - Separator and label sub-components
 * - Configurable animation, min/max width, and max height
 * - No Material3 dependency
 *
 * Usage:
 * ```
 * var open by remember { mutableStateOf(false) }
 *
 * DropdownMenu(
 *     expanded = open,
 *     onDismiss = { open = false },
 *     trigger = {
 *         Button("Actions", onClick = { open = true })
 *     },
 * ) {
 *     DropdownMenuLabel("File")
 *     DropdownMenuItem("New", onClick = { newFile(); open = false })
 *     DropdownMenuItem("Open", onClick = { openFile(); open = false })
 *     DropdownMenuSeparator()
 *     DropdownMenuItem("Delete", onClick = { delete(); open = false })
 * }
 *
 * // Instant popup with wider menu:
 * DropdownMenu(
 *     expanded = open,
 *     onDismiss = { open = false },
 *     animation = PopupAnimation.None,
 *     minWidth = 220.dp,
 *     maxWidth = 360.dp,
 *     trigger = { Button("Wide", onClick = { open = true }) },
 * ) { ... }
 * ```
 *
 * @param expanded Whether the dropdown popup is currently visible.
 * @param onDismiss Called when the user clicks outside the popup to dismiss it.
 * @param modifier Modifier applied to the outer trigger wrapper.
 * @param animation Controls how the popup enters and exits.
 *     Defaults to [PopupAnimation.FadeExpand].
 * @param minWidth Minimum width of the dropdown panel. Defaults to 180.dp.
 * @param maxWidth Maximum width of the dropdown panel. Defaults to 280.dp.
 * @param maxHeight Maximum height before the panel becomes scrollable.
 *     Defaults to 300.dp.
 * @param trigger The composable that anchors the menu. Rendered inline.
 * @param content Column-scoped builder for menu items, labels, and separators.
 */
@Composable
fun DropdownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    animation: PopupAnimation = PopupAnimation.FadeExpand,
    minWidth: Dp = 180.dp,
    maxWidth: Dp = 280.dp,
    maxHeight: Dp = 300.dp,
    trigger: @Composable () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = RikkaTheme.colors
    val shapes = RikkaTheme.shapes
    val spacing = RikkaTheme.spacing
    val motion = RikkaTheme.motion

    var showPopup by remember { mutableStateOf(false) }
    LaunchedEffect(expanded) {
        if (expanded) showPopup = true
    }

    Box(modifier = modifier) {
        trigger()

        if (showPopup) {
            Popup(
                alignment = Alignment.BottomStart,
                onDismissRequest = onDismiss,
            ) {
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
                        content = content,
                    )
                }

                when (animation) {
                    PopupAnimation.None -> panelContent()

                    PopupAnimation.Fade -> {
                        AnimatedVisibility(
                            visible = expanded,
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
                            visible = expanded,
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
                                        shrinkTowards =
                                            Alignment.Top,
                                    ),
                        ) {
                            panelContent()
                        }
                    }
                }

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

// ─── Menu Item ──────────────────────────────────────────────

/**
 * A single actionable row inside a [DropdownMenu].
 *
 * Highlights with the accent color on hover and dims when disabled.
 *
 * Usage:
 * ```
 * DropdownMenuItem("Copy", onClick = { copy() })
 * DropdownMenuItem("Paste", onClick = { paste() }, enabled = false)
 * ```
 *
 * @param text The label displayed in the menu item.
 * @param onClick Called when the item is clicked.
 * @param enabled Whether the item is interactive. Defaults to true.
 * @param modifier Modifier applied to the item row.
 */
@Composable
fun DropdownMenuItem(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val colors = RikkaTheme.colors
    val spacing = RikkaTheme.spacing

    val backgroundColor =
        if (isHovered && enabled) colors.accent else colors.popover

    val textColor =
        if (enabled) colors.popoverForeground else colors.mutedForeground

    Box(
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
        contentAlignment = Alignment.CenterStart,
    ) {
        BasicText(
            text = text,
            style =
                RikkaTheme.typography.small.merge(
                    TextStyle(color = textColor),
                ),
        )
    }
}

// ─── Separator ──────────────────────────────────────────────

/**
 * A thin horizontal divider inside a [DropdownMenu].
 *
 * Usage:
 * ```
 * DropdownMenuItem("Cut", onClick = { cut() })
 * DropdownMenuSeparator()
 * DropdownMenuItem("Delete", onClick = { delete() })
 * ```
 *
 * @param modifier Modifier applied to the separator line.
 */
@Composable
fun DropdownMenuSeparator(modifier: Modifier = Modifier) {
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
 * A non-clickable header label inside a [DropdownMenu].
 *
 * Styled with bold text at the [TextVariant.Small] size to
 * visually group related menu items.
 *
 * Usage:
 * ```
 * DropdownMenuLabel("Edit")
 * DropdownMenuItem("Cut", onClick = { cut() })
 * DropdownMenuItem("Copy", onClick = { copy() })
 * ```
 *
 * @param text The label text.
 * @param modifier Modifier applied to the label row.
 */
@Composable
fun DropdownMenuLabel(
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
                        color = RikkaTheme.colors.popoverForeground,
                        fontWeight = FontWeight.SemiBold,
                    ),
                ),
        )
    }
}
