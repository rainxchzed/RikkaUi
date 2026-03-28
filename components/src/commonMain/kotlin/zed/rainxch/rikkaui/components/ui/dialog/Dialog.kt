package zed.rainxch.rikkaui.components.ui.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.dismiss
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

// ─── Component ──────────────────────────────────────────────

/**
 * Modal dialog overlay for the RikkaUi design system.
 *
 * Displays a centered card on top of a semi-transparent scrim.
 * Clicking the scrim dismisses the dialog. Uses [Popup] for
 * overlay rendering and [AnimatedVisibility] for fade in/out.
 *
 * Usage:
 * ```
 * var open by remember { mutableStateOf(false) }
 *
 * Button("Open Dialog", onClick = { open = true })
 *
 * Dialog(open = open, onDismiss = { open = false }) {
 *     DialogHeader(
 *         title = "Confirm Action",
 *         description = "This action cannot be undone.",
 *     )
 *     Text("Are you sure you want to proceed?")
 *     DialogFooter {
 *         Button("Cancel", onClick = { open = false }, variant = ButtonVariant.Outline)
 *         Button("Confirm", onClick = { confirm(); open = false })
 *     }
 * }
 * ```
 *
 * @param open Whether the dialog is visible.
 * @param onDismiss Called when the user dismisses the dialog (scrim click or back gesture).
 * @param modifier Modifier applied to the dialog card.
 * @param label Accessibility label describing the dialog's purpose.
 * @param content Dialog content — use [DialogHeader], [DialogFooter], and any other composables.
 */
@Composable
fun Dialog(
    open: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Dialog",
    content: @Composable ColumnScope.() -> Unit,
) {
    if (!open) return

    val colors = RikkaTheme.colors
    val shapes = RikkaTheme.shapes
    val spacing = RikkaTheme.spacing
    val motion = RikkaTheme.motion

    Popup(
        onDismissRequest = onDismiss,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            // ─── Scrim ───────────────────────────────────
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(
                    animationSpec = androidx.compose.animation.core.tween(
                        motion.durationEnter,
                    ),
                ),
                exit = fadeOut(
                    animationSpec = androidx.compose.animation.core.tween(
                        motion.durationEnter,
                    ),
                ),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = onDismiss,
                        ),
                )
            }

            // ─── Dialog card ─────────────────────────────
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(
                    animationSpec = androidx.compose.animation.core.tween(
                        motion.durationEnter,
                    ),
                ),
                exit = fadeOut(
                    animationSpec = androidx.compose.animation.core.tween(
                        motion.durationEnter,
                    ),
                ),
            ) {
                Column(
                    modifier = modifier
                        .widthIn(max = 480.dp)
                        .semantics(mergeDescendants = true) {
                            paneTitle = label
                            dismiss { onDismiss(); true }
                        }
                        .border(1.dp, colors.border, shapes.lg)
                        .background(colors.card, shapes.lg)
                        .clip(shapes.lg)
                        .padding(spacing.xl),
                    verticalArrangement = Arrangement.spacedBy(spacing.md),
                    content = content,
                )
            }
        }
    }
}

// ─── Structured Sections ────────────────────────────────────

/**
 * Header section for a [Dialog]. Contains a title and optional description.
 *
 * ```
 * DialogHeader(
 *     title = "Edit Profile",
 *     description = "Make changes to your profile here.",
 * )
 * ```
 *
 * @param title The dialog title, rendered as H3.
 * @param description Optional description text, rendered as Muted.
 * @param modifier Modifier for the header column.
 */
@Composable
fun DialogHeader(
    title: String,
    modifier: Modifier = Modifier,
    description: String = "",
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
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
 * Footer section for a [Dialog]. Arranges action buttons at the end (right-aligned).
 *
 * ```
 * DialogFooter {
 *     Button("Cancel", onClick = { dismiss() }, variant = ButtonVariant.Outline)
 *     Button("Save", onClick = { save() })
 * }
 * ```
 *
 * @param modifier Modifier for the footer row.
 * @param content Footer content — typically buttons.
 */
@Composable
fun DialogFooter(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier.padding(top = RikkaTheme.spacing.sm),
        horizontalArrangement = Arrangement.spacedBy(
            RikkaTheme.spacing.sm,
            Alignment.End,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        content()
    }
}
