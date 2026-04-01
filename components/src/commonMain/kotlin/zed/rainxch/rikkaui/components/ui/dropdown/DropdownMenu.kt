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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
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
        if (isHovered && enabled) colors.secondary else colors.surface

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
                        color = RikkaTheme.colors.onSurface,
                        fontWeight = FontWeight.SemiBold,
                    ),
                ),
        )
    }
}
