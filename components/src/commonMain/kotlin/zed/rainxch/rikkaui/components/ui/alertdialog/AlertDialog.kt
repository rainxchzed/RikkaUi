package zed.rainxch.rikkaui.components.ui.alertdialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.dismiss
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import kotlinx.coroutines.delay
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaMotion
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Animation Enum ─────────────────────────────────────────

enum class AlertDialogAnimation {
    /** Fade + scale up from 0.95. */
    FadeScale,

    /** Opacity-only fade. */
    Fade,

    /** No animation. */
    None,
}

// ─── Component ──────────────────────────────────────────────

@Composable
fun AlertDialog(
    open: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Alert Dialog",
    animation: AlertDialogAnimation = AlertDialogAnimation.FadeScale,
    scrimColor: Color = RikkaTheme.colors.scrim,
    maxWidth: Dp = 520.dp,
    content: @Composable () -> Unit,
) {
    var showPopup by remember { mutableStateOf(false) }
    LaunchedEffect(open) {
        if (open) showPopup = true
    }
    if (!showPopup) return

    val colors = RikkaTheme.colors
    val shapes = RikkaTheme.shapes
    val spacing = RikkaTheme.spacing
    val motion = RikkaTheme.motion

    val (cardEnter, cardExit) =
        resolveAlertDialogTransition(
            animation,
            motion,
        )

    Popup(
        onDismissRequest = onDismiss,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            // ─── Scrim (non-dismissing) ───────────────
            AnimatedVisibility(
                visible = open,
                enter =
                    fadeIn(
                        animationSpec = tween(motion.durationEnter),
                    ),
                exit =
                    fadeOut(
                        animationSpec = tween(motion.durationEnter),
                    ),
            ) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .background(scrimColor)
                            .clickable(
                                interactionSource =
                                    remember {
                                        MutableInteractionSource()
                                    },
                                indication = null,
                                onClick = {
                                    // Intentionally empty — alert dialogs
                                    // must not dismiss on scrim click.
                                },
                            ),
                )
            }

            // ─── Dialog card ──────────────────────────
            AnimatedVisibility(
                visible = open,
                enter = cardEnter,
                exit = cardExit,
            ) {
                Column(
                    modifier =
                        modifier
                            .widthIn(max = maxWidth)
                            .semantics(mergeDescendants = true) {
                                paneTitle = label
                                contentDescription = label
                                dismiss {
                                    onDismiss()
                                    true
                                }
                            }.shadow(RikkaTheme.elevation.high, shapes.lg)
                            .border(1.dp, colors.border, shapes.lg)
                            .background(colors.popover, shapes.lg)
                            .clip(shapes.lg)
                            .padding(spacing.xl),
                    verticalArrangement =
                        Arrangement.spacedBy(
                            spacing.md,
                        ),
                ) {
                    content()
                }
            }

            // ─── Cleanup: remove Popup after exit animation ──
            if (!open) {
                LaunchedEffect(Unit) {
                    delay(motion.durationEnter.toLong() + 50L)
                    showPopup = false
                }
            }
        }
    }
}

// ─── Structured Sections ────────────────────────────────────

@Composable
fun AlertDialogHeader(
    title: String,
    modifier: Modifier = Modifier,
    description: String = "",
) {
    Column(
        modifier = modifier,
        verticalArrangement =
            Arrangement.spacedBy(
                RikkaTheme.spacing.xs,
            ),
    ) {
        Text(
            text = title,
            variant = TextVariant.Large,
        )
        if (description.isNotEmpty()) {
            Text(
                text = description,
                variant = TextVariant.Muted,
            )
        }
    }
}

@Composable
fun AlertDialogFooter(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(top = RikkaTheme.spacing.sm),
        horizontalArrangement =
            Arrangement.spacedBy(
                RikkaTheme.spacing.sm,
                Alignment.End,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        content()
    }
}

// ─── Action Buttons ─────────────────────────────────────────

@Composable
fun AlertDialogCancel(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = "Cancel",
) {
    Button(
        text = text,
        onClick = onClick,
        modifier = modifier,
        variant = ButtonVariant.Outline,
    )
}

// ─── Action Variant ─────────────────────────────────────────

enum class AlertDialogActionVariant {
    /** Primary-colored confirm button. */
    Default,

    /** Red-tinted button for dangerous actions. */
    Destructive,
}

@Composable
fun AlertDialogAction(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: AlertDialogActionVariant =
        AlertDialogActionVariant.Default,
) {
    val buttonVariant =
        when (variant) {
            AlertDialogActionVariant.Default -> {
                ButtonVariant.Default
            }

            AlertDialogActionVariant.Destructive -> {
                ButtonVariant.Destructive
            }
        }

    Button(
        text = text,
        onClick = onClick,
        modifier = modifier,
        variant = buttonVariant,
    )
}

// ─── Internal: Transition Resolution ────────────────────────

private fun resolveAlertDialogTransition(
    animation: AlertDialogAnimation,
    motion: RikkaMotion,
): Pair<EnterTransition, ExitTransition> =
    when (animation) {
        AlertDialogAnimation.FadeScale -> {
            val enter =
                fadeIn(
                    animationSpec = tween(motion.durationEnter),
                ) +
                    scaleIn(
                        initialScale = motion.overlayScaleIn,
                        animationSpec = tween(motion.durationEnter),
                    )
            val exit =
                fadeOut(
                    animationSpec = tween(motion.durationEnter),
                ) +
                    scaleOut(
                        targetScale = motion.overlayScaleIn,
                        animationSpec = tween(motion.durationEnter),
                    )
            enter to exit
        }

        AlertDialogAnimation.Fade -> {
            val enter =
                fadeIn(
                    animationSpec = tween(motion.durationEnter),
                )
            val exit =
                fadeOut(
                    animationSpec = tween(motion.durationEnter),
                )
            enter to exit
        }

        AlertDialogAnimation.None -> {
            val enter = fadeIn(animationSpec = tween(motion.durationInstant))
            val exit = fadeOut(animationSpec = tween(motion.durationInstant))
            enter to exit
        }
    }
