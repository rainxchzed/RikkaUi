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
import kotlinx.coroutines.delay
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
import zed.rainxch.rikkaui.components.theme.RikkaMotion
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

// ─── Animation Enum ─────────────────────────────────────────

/**
 * Animation style for [AlertDialog] enter/exit transitions.
 *
 * Each variant uses [RikkaTheme.motion] tokens for timing, so
 * switching the motion preset (snappy / playful / minimal)
 * automatically adjusts the dialog animation speed.
 *
 * ```
 * AlertDialog(
 *     open = open,
 *     onDismiss = { open = false },
 *     onConfirm = { confirm() },
 *     animation = AlertDialogAnimation.Fade,
 * ) { ... }
 * ```
 *
 * - [FadeScale] — Fade in combined with a subtle 0.95 -> 1.0
 *   scale. The default, gives the dialog a "zoom-in" feel.
 * - [Fade] — Opacity-only transition, no scale.
 * - [None] — Instant appear/disappear with no animation.
 */
enum class AlertDialogAnimation {
    /** Fade + subtle scale up from 0.95. Default. */
    FadeScale,

    /** Opacity-only fade, no scale. */
    Fade,

    /** Instant appear/disappear. */
    None,
}

// ─── Component ──────────────────────────────────────────────

/**
 * Alert dialog for destructive-action confirmations in the RikkaUi
 * design system.
 *
 * Unlike [zed.rainxch.rikkaui.components.ui.dialog.Dialog], the
 * alert dialog does **not** dismiss when the user clicks the scrim.
 * The user is forced to choose an explicit action (cancel or confirm).
 * This makes it ideal for "Are you sure?" confirmations before
 * irreversible operations such as deleting data.
 *
 * Uses [Popup] for overlay rendering with [AnimatedVisibility] for
 * enter/exit transitions driven by theme motion tokens.
 *
 * Usage:
 * ```
 * var open by remember { mutableStateOf(false) }
 *
 * Button("Delete Account", onClick = { open = true })
 *
 * AlertDialog(
 *     open = open,
 *     onDismiss = { open = false },
 *     onConfirm = { deleteAccount(); open = false },
 * ) {
 *     AlertDialogHeader(
 *         title = "Are you absolutely sure?",
 *         description = "This action cannot be undone. This will "
 *             + "permanently delete your account and remove your "
 *             + "data from our servers.",
 *     )
 *     AlertDialogFooter {
 *         AlertDialogCancel(onClick = { open = false })
 *         AlertDialogAction(
 *             text = "Delete Account",
 *             onClick = { deleteAccount(); open = false },
 *             variant = AlertDialogActionVariant.Destructive,
 *         )
 *     }
 * }
 * ```
 *
 * @param open Whether the alert dialog is visible.
 * @param onDismiss Called when the cancel action is triggered (e.g.
 *   via accessibility dismiss gesture). The scrim does **not** call
 *   this — only explicit cancel actions do.
 * @param onConfirm Called when the confirm action is triggered via
 *   accessibility. Provide the same callback you pass to
 *   [AlertDialogAction].
 * @param modifier Modifier applied to the dialog card container.
 * @param label Accessibility label describing the dialog's purpose.
 * @param animation The enter/exit animation style. Defaults to
 *   [AlertDialogAnimation.FadeScale].
 * @param scrimColor Color of the backdrop scrim. Defaults to
 *   semi-transparent black (`Color.Black.copy(alpha = 0.5f)`).
 * @param maxWidth Maximum width of the dialog card. Defaults to
 *   520.dp.
 * @param content Dialog content — use [AlertDialogHeader],
 *   [AlertDialogFooter], and any other composables.
 */
@Composable
fun AlertDialog(
    open: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Alert Dialog",
    animation: AlertDialogAnimation = AlertDialogAnimation.FadeScale,
    scrimColor: Color = Color.Black.copy(alpha = 0.5f),
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
                            }.shadow(8.dp, shapes.lg)
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

/**
 * Header section for an [AlertDialog]. Contains a title and an
 * optional description.
 *
 * ```
 * AlertDialogHeader(
 *     title = "Are you absolutely sure?",
 *     description = "This action cannot be undone.",
 * )
 * ```
 *
 * @param title The dialog title, rendered as [TextVariant.Large].
 * @param description Optional description, rendered as
 *   [TextVariant.Muted].
 * @param modifier Modifier for the header column.
 */
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

/**
 * Footer section for an [AlertDialog]. Arranges action buttons in a
 * right-aligned row — typically [AlertDialogCancel] followed by
 * [AlertDialogAction].
 *
 * ```
 * AlertDialogFooter {
 *     AlertDialogCancel(onClick = { open = false })
 *     AlertDialogAction(
 *         text = "Continue",
 *         onClick = { confirm() },
 *     )
 * }
 * ```
 *
 * @param modifier Modifier for the footer row.
 * @param content Footer content — typically cancel and action buttons.
 */
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

/**
 * Cancel button for an [AlertDialog]. Renders as an
 * [ButtonVariant.Outline] button.
 *
 * ```
 * AlertDialogCancel(onClick = { open = false })
 * AlertDialogCancel(text = "Go Back", onClick = { open = false })
 * ```
 *
 * @param onClick Called when the cancel button is clicked.
 * @param modifier Modifier for the button.
 * @param text Button label. Defaults to "Cancel".
 */
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

/**
 * Visual variant for [AlertDialogAction].
 *
 * - [Default] — Primary-colored confirm button.
 * - [Destructive] — Red-tinted button for dangerous actions.
 */
enum class AlertDialogActionVariant {
    Default,
    Destructive,
}

/**
 * Confirm/action button for an [AlertDialog].
 *
 * ```
 * // Default (primary) action
 * AlertDialogAction(
 *     text = "Continue",
 *     onClick = { proceed() },
 * )
 *
 * // Destructive action
 * AlertDialogAction(
 *     text = "Delete",
 *     onClick = { delete() },
 *     variant = AlertDialogActionVariant.Destructive,
 * )
 * ```
 *
 * @param text Button label.
 * @param onClick Called when the action button is clicked.
 * @param modifier Modifier for the button.
 * @param variant Visual variant — [AlertDialogActionVariant.Default]
 *   or [AlertDialogActionVariant.Destructive].
 */
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
            AlertDialogActionVariant.Default -> ButtonVariant.Default
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

/**
 * Resolves enter/exit transitions for the alert dialog card based
 * on the chosen [AlertDialogAnimation] and current [RikkaMotion]
 * tokens.
 */
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
                        initialScale = 0.95f,
                        animationSpec = tween(motion.durationEnter),
                    )
            val exit =
                fadeOut(
                    animationSpec = tween(motion.durationEnter),
                ) +
                    scaleOut(
                        targetScale = 0.95f,
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
            val enter = fadeIn(animationSpec = tween(0))
            val exit = fadeOut(animationSpec = tween(0))
            enter to exit
        }
    }
