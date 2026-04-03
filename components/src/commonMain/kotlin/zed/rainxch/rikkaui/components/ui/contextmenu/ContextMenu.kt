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
import androidx.compose.foundation.focusable
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.pointerInput
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

@Composable
fun ContextMenu(
    menuContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Context menu",
    animation: PopupAnimation = PopupAnimation.FadeExpand,
    minWidth: Dp = 200.dp,
    maxWidth: Dp = 280.dp,
    maxHeight: Dp = 300.dp,
    content: @Composable () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val motion = RikkaTheme.motion
    val focusRequester = remember { FocusRequester() }

    var showPopup by remember { mutableStateOf(false) }
    LaunchedEffect(expanded) {
        if (expanded) showPopup = true
    }

    Box(modifier = modifier) {
        Box(
            modifier =
                Modifier
                    .semantics {
                        stateDescription = if (expanded) "Expanded" else "Collapsed"
                    }.onKeyEvent {
                        if (it.key == Key.F10 && it.type == KeyEventType.KeyDown) {
                            expanded = true
                            true
                        } else {
                            false
                        }
                    }.pointerInput(Unit) {
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
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }

                ContextMenuPanel(
                    visible = expanded,
                    animation = animation,
                    minWidth = minWidth,
                    maxWidth = maxWidth,
                    maxHeight = maxHeight,
                    menuContent = menuContent,
                    label = label,
                    focusRequester = focusRequester,
                    onDismiss = { expanded = false },
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

@Composable
fun ControlledContextMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    menuContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Context menu",
    animation: PopupAnimation = PopupAnimation.FadeExpand,
    minWidth: Dp = 200.dp,
    maxWidth: Dp = 280.dp,
    maxHeight: Dp = 300.dp,
    content: @Composable () -> Unit,
) {
    val motion = RikkaTheme.motion
    val focusRequester = remember { FocusRequester() }

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
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }

                ContextMenuPanel(
                    visible = expanded,
                    animation = animation,
                    minWidth = minWidth,
                    maxWidth = maxWidth,
                    maxHeight = maxHeight,
                    menuContent = menuContent,
                    label = label,
                    focusRequester = focusRequester,
                    onDismiss = onDismiss,
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

@Composable
private fun ContextMenuPanel(
    visible: Boolean,
    animation: PopupAnimation,
    minWidth: Dp,
    maxWidth: Dp,
    maxHeight: Dp,
    menuContent: @Composable ColumnScope.() -> Unit,
    label: String = "Context menu",
    focusRequester: FocusRequester? = null,
    onDismiss: (() -> Unit)? = null,
) {
    val colors = RikkaTheme.colors
    val shapes = RikkaTheme.shapes
    val spacing = RikkaTheme.spacing
    val motion = RikkaTheme.motion

    val panelContent: @Composable () -> Unit = {
        Column(
            modifier =
                Modifier
                    .then(
                        if (focusRequester != null) {
                            Modifier.focusRequester(focusRequester)
                        } else {
                            Modifier
                        },
                    ).focusable()
                    .onKeyEvent {
                        if (it.key == Key.Escape && it.type == KeyEventType.KeyDown) {
                            onDismiss?.invoke()
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
            content = menuContent,
        )
    }

    when (animation) {
        PopupAnimation.None -> {
            panelContent()
        }

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
        if (isHovered && enabled) {
            if (colors.secondaryHover != Color.Unspecified) colors.secondaryHover else colors.secondary
        } else {
            colors.surface
        }

    val textColor =
        if (enabled) colors.onSurface else colors.onMuted

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
                            color = colors.onMuted,
                        ),
                    ),
            )
        }
    }
}

// ─── Separator ──────────────────────────────────────────────

@Composable
fun ContextMenuSeparator(modifier: Modifier = Modifier) {
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
                            RikkaTheme.colors.onSurface,
                        fontWeight = FontWeight.SemiBold,
                    ),
                ),
        )
    }
}
