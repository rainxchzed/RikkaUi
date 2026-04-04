package zed.rainxch.rikkaui.components.ui.dialog

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
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.semantics.dismiss
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import kotlinx.coroutines.delay
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.LocalContentColor
import zed.rainxch.rikkaui.foundation.LocalTextStyle
import zed.rainxch.rikkaui.foundation.RikkaMotion
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Animation Enum ─────────────────────────────────────────

/**
 * Animation style for [Dialog] enter/exit transitions.
 */
enum class DialogAnimation {
    /** Fade + scale up from 0.95. Default. */
    FadeScale,

    /** Opacity-only fade. */
    Fade,

    /** No animation. */
    None,
}

// ─── Component ──────────────────────────────────────────────

/**
 * A modal dialog overlay with scrim, keyboard dismiss, and focus trapping.
 *
 * Renders a centered card on top of a semi-transparent scrim. Pressing Escape or
 * clicking the scrim dismisses the dialog. Auto-focuses the dialog on open.
 *
 * @param open Boolean controlling whether the dialog is visible.
 * @param onDismiss Callback invoked when the user requests dismissal (scrim click or Escape).
 * @param modifier [Modifier] applied to the dialog card container.
 * @param label Accessibility pane title announced by screen readers.
 * @param animation [DialogAnimation] style for enter/exit transitions.
 * @param scrimColor [Color] of the backdrop overlay behind the dialog.
 * @param maxWidth Maximum width of the dialog card in [Dp].
 * @param content Composable content rendered inside the dialog card column.
 *
 * @sample
 * ```
 * var showDialog by remember { mutableStateOf(false) }
 *
 * Button(text = "Open", onClick = { showDialog = true })
 *
 * Dialog(open = showDialog, onDismiss = { showDialog = false }) {
 *     DialogHeader(title = "Confirm", description = "Are you sure?")
 *     DialogFooter {
 *         Button(text = "Cancel", onClick = { showDialog = false }, variant = ButtonVariant.Outline)
 *         Button(text = "OK", onClick = { showDialog = false })
 *     }
 * }
 * ```
 */
@Composable
fun Dialog(
    open: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Dialog",
    animation: DialogAnimation = DialogAnimation.FadeScale,
    scrimColor: Color = RikkaTheme.colors.scrim,
    maxWidth: Dp = 480.dp,
    content: @Composable ColumnScope.() -> Unit,
) {
    // Track whether the Popup should stay in composition (true during exit animation)
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
        resolveDialogTransition(
            animation,
            motion,
        )

    Popup(
        onDismissRequest = onDismiss,
        properties = PopupProperties(focusable = true),
    ) {
        val focusRequester = remember { FocusRequester() }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            // ─── Scrim ───────────────────────────────────
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
                                onClick = onDismiss,
                            ),
                )
            }

            // ─── Dialog card ─────────────────────────────
            AnimatedVisibility(
                visible = open,
                enter = cardEnter,
                exit = cardExit,
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides colors.onSurface,
                    LocalTextStyle provides RikkaTheme.typography.p,
                ) {
                    Column(
                        modifier =
                            modifier
                                .focusRequester(focusRequester)
                                .focusable()
                                .onKeyEvent { event ->
                                    if (event.key == Key.Escape &&
                                        event.type == KeyEventType.KeyDown
                                    ) {
                                        onDismiss()
                                        true
                                    } else {
                                        false
                                    }
                                }.widthIn(max = maxWidth)
                                .semantics(mergeDescendants = true) {
                                    paneTitle = label
                                    dismiss {
                                        onDismiss()
                                        true
                                    }
                                }.border(1.dp, colors.border, shapes.lg)
                                .background(colors.surface, shapes.lg)
                                .clip(shapes.lg)
                                .padding(spacing.xl),
                        verticalArrangement =
                            Arrangement.spacedBy(
                                spacing.md,
                            ),
                        content = content,
                    )
                }
            }
        }

        // Auto-focus the dialog card when opened
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        // Remove popup from composition after exit animation completes
        if (!open) {
            LaunchedEffect(Unit) {
                delay(motion.durationEnter.toLong() + 50L)
                showPopup = false
            }
        }
    }
}

// ─── Structured Sections ────────────────────────────────────

/**
 * Structured header section for a [Dialog] with title and optional description.
 *
 * @param title Primary heading text displayed in the dialog header.
 * @param modifier [Modifier] applied to the header column.
 * @param description Optional secondary text shown below the title in muted style.
 *
 * @sample
 * ```
 * Dialog(open = true, onDismiss = {}) {
 *     DialogHeader(title = "Edit Profile", description = "Update your display name.")
 * }
 * ```
 */
@Composable
fun DialogHeader(
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
            variant = TextVariant.H3,
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
 * Footer row for action buttons inside a [Dialog], aligned to the end.
 *
 * @param modifier [Modifier] applied to the footer row.
 * @param content Composable action buttons (e.g., Cancel and Confirm).
 *
 * @sample
 * ```
 * DialogFooter {
 *     Button(text = "Cancel", onClick = onCancel, variant = ButtonVariant.Outline)
 *     Button(text = "Save", onClick = onSave)
 * }
 * ```
 */
@Composable
fun DialogFooter(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier.padding(top = RikkaTheme.spacing.sm),
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

// ─── Internal: Transition Resolution ────────────────────────

private fun resolveDialogTransition(
    animation: DialogAnimation,
    motion: RikkaMotion,
): Pair<EnterTransition, ExitTransition> =
    when (animation) {
        DialogAnimation.FadeScale -> {
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

        DialogAnimation.Fade -> {
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

        DialogAnimation.None -> {
            val enter = fadeIn(animationSpec = tween(motion.durationInstant))
            val exit = fadeOut(animationSpec = tween(motion.durationInstant))
            enter to exit
        }
    }
