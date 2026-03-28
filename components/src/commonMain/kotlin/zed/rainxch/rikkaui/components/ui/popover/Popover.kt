package zed.rainxch.rikkaui.components.ui.popover

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import zed.rainxch.rikkaui.components.theme.RikkaTheme

// ─── Component ──────────────────────────────────────────────

/**
 * Popover component for the RikkaUi design system.
 *
 * A click-triggered popup that renders content in a floating
 * card anchored to a trigger element. Maps to shadcn/ui's
 * Popover.
 *
 * The caller controls visibility via the [expanded] parameter
 * and dismisses via [onDismiss]. The trigger composable is
 * rendered inline — hook your click handler there to toggle
 * [expanded].
 *
 * Features:
 * - Click-triggered (not hover) — user controls open/close
 * - Animated expand/fade entrance and exit
 * - Theme-aware card styling with border and shadow
 * - Dismiss on outside click via [Popup.onDismissRequest]
 * - Constrained max width (360dp) to prevent full-screen
 * - No Material3 dependency
 *
 * Usage:
 * ```
 * var open by remember { mutableStateOf(false) }
 *
 * Popover(
 *     expanded = open,
 *     onDismiss = { open = false },
 *     trigger = {
 *         Button("Open", onClick = { open = true })
 *     },
 * ) {
 *     Text("Popover content goes here.")
 * }
 * ```
 *
 * @param expanded Whether the popover popup is currently
 *     visible.
 * @param onDismiss Called when the user clicks outside the
 *     popup to dismiss it.
 * @param modifier Modifier applied to the outer trigger
 *     wrapper.
 * @param trigger The composable that anchors the popover.
 *     Rendered inline.
 * @param content The composable content displayed inside the
 *     floating card.
 */
@Composable
fun Popover(
    expanded: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    trigger: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val colors = RikkaTheme.colors
    val shapes = RikkaTheme.shapes
    val spacing = RikkaTheme.spacing
    val motion = RikkaTheme.motion

    Box(modifier = modifier) {
        trigger()

        if (expanded) {
            Popup(
                alignment = Alignment.BottomStart,
                onDismissRequest = onDismiss,
            ) {
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(
                        animationSpec = tween(
                            motion.durationFast,
                        ),
                    ) + expandVertically(
                        animationSpec = tween(
                            motion.durationDefault,
                        ),
                        expandFrom = Alignment.Top,
                    ),
                    exit = fadeOut(
                        animationSpec = tween(
                            motion.durationFast,
                        ),
                    ) + shrinkVertically(
                        animationSpec = tween(
                            motion.durationDefault,
                        ),
                        shrinkTowards = Alignment.Top,
                    ),
                ) {
                    Box(
                        modifier =
                            Modifier
                                .defaultMinSize(
                                    minWidth = 200.dp,
                                )
                                .widthIn(max = 360.dp)
                                .shadow(
                                    8.dp,
                                    shapes.md,
                                )
                                .border(
                                    1.dp,
                                    colors.border,
                                    shapes.md,
                                )
                                .background(
                                    colors.popover,
                                    shapes.md,
                                )
                                .clip(shapes.md)
                                .padding(spacing.lg),
                    ) {
                        content()
                    }
                }
            }
        }
    }
}
