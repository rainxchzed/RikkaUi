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
import androidx.compose.foundation.focusable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import kotlinx.coroutines.delay
import zed.rainxch.rikkaui.components.ui.PopupAnimation
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Component ──────────────────────────────────────────────

/**
 * A dropdown action menu anchored to a trigger element.
 *
 * Displays a scrollable list of menu items, separators, and labels in a popup card.
 * Press Escape or click outside to dismiss. The menu panel auto-scrolls when content
 * exceeds [maxHeight].
 *
 * @param expanded Whether the dropdown menu is currently visible.
 * @param onDismiss Callback invoked when the user requests dismissal (outside click or Escape).
 * @param modifier [Modifier] applied to the outer container wrapping trigger and popup.
 * @param label Accessibility pane title for the menu. Defaults to "Menu".
 * @param animation [PopupAnimation] style for enter/exit transitions. Defaults to [PopupAnimation.FadeExpand].
 * @param minWidth Minimum width of the menu panel. Defaults to 180.dp.
 * @param maxWidth Maximum width of the menu panel. Defaults to 280.dp.
 * @param maxHeight Maximum height of the menu panel before scrolling. Defaults to 300.dp.
 * @param trigger Composable content that acts as the anchor element (e.g., a button).
 * @param content Menu items rendered inside the dropdown panel column.
 */
@Composable
public fun DropdownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Menu",
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

    val focusRequester = remember { FocusRequester() }

    var showPopup by remember { mutableStateOf(false) }
    LaunchedEffect(expanded) {
        if (expanded) showPopup = true
    }

    Box(modifier = modifier) {
        Box(
            modifier =
                Modifier.semantics {
                    stateDescription = if (expanded) "Expanded" else "Collapsed"
                },
        ) {
            trigger()
        }

        if (showPopup) {
            Popup(
                alignment = Alignment.BottomStart,
                onDismissRequest = onDismiss,
            ) {
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }

                val panelContent: @Composable () -> Unit = {
                    Column(
                        modifier =
                            Modifier
                                .focusRequester(focusRequester)
                                .focusable()
                                .onKeyEvent {
                                    if (it.key == Key.Escape && it.type == KeyEventType.KeyDown) {
                                        onDismiss()
                                        true
                                    } else {
                                        false
                                    }
                                }.semantics { paneTitle = label }
                                .defaultMinSize(
                                    minWidth = minWidth,
                                ).widthIn(max = maxWidth)
                                .heightIn(max = maxHeight)
                                .shadow(
                                    RikkaTheme.elevation.high,
                                    shapes.md,
                                ).border(
                                    1.dp,
                                    colors.border,
                                    shapes.md,
                                ).background(
                                    colors.surface,
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
                    PopupAnimation.None -> {
                        panelContent()
                    }

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
 * A single actionable item inside a [DropdownMenu].
 *
 * Displays hover highlighting and disabled state styling. Uses theme-aware colors.
 *
 * @param text Label text displayed in the menu item.
 * @param onClick Callback invoked when the item is clicked.
 * @param enabled Whether the item is interactive. Defaults to true. Disabled items are visually dimmed.
 * @param modifier [Modifier] applied to the menu item row.
 */
@Composable
public fun DropdownMenuItem(
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
        if (isHovered && enabled) {
            if (colors.secondaryHover != Color.Unspecified) colors.secondaryHover else colors.secondary
        } else {
            colors.surface
        }

    val textColor =
        if (enabled) colors.onSurface else colors.onMuted

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
                ).semantics {
                    contentDescription = text
                    if (!enabled) {
                        disabled()
                    }
                }.then(
                    if (!enabled) {
                        Modifier.alpha(0.5f)
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
 * A decorative horizontal separator line within a [DropdownMenu].
 *
 * Cleared from accessibility semantics via [clearAndSetSemantics].
 *
 * @param modifier [Modifier] applied to the separator.
 */
@Composable
public fun DropdownMenuSeparator(modifier: Modifier = Modifier) {
    val spacing = RikkaTheme.spacing

    Box(
        modifier =
            modifier
                .clearAndSetSemantics {}
                .fillMaxWidth()
                .padding(vertical = spacing.xs)
                .height(1.dp)
                .background(RikkaTheme.colors.border),
    )
}

// ─── Label ──────────────────────────────────────────────────

/**
 * A non-interactive label used to group related items within a [DropdownMenu].
 *
 * Rendered in semi-bold weight to visually distinguish it from actionable items.
 *
 * @param text Label text displayed in the menu.
 * @param modifier [Modifier] applied to the label container.
 */
@Composable
public fun DropdownMenuLabel(
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
                        color = RikkaTheme.colors.onSurface,
                        fontWeight = FontWeight.SemiBold,
                    ),
                ),
        )
    }
}
