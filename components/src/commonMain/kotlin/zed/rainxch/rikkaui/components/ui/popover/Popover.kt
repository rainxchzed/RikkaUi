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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import kotlinx.coroutines.delay
import zed.rainxch.rikkaui.components.theme.RikkaTheme

// ─── Animation ─────────────────────────────────────────────

/**
 * Animation style for the [Popover] entrance and exit.
 *
 * - [FadeExpand] — Fade combined with vertical expand/shrink
 *   (default). Feels smooth and anchored to the trigger.
 * - [Fade] — Simple opacity fade only. Lighter and subtler.
 * - [None] — Instant appear/disappear with no animation.
 */
enum class PopoverAnimation {
    /** Fade + vertical expand from top (default). */
    FadeExpand,

    /** Simple opacity fade only. */
    Fade,

    /** Instant appear/disappear, no animation. */
    None,
}

// ─── Alignment ─────────────────────────────────────────────

/**
 * Placement of the [Popover] popup relative to the trigger.
 *
 * - [BottomStart] — Below the trigger, aligned to the start
 *   edge (default).
 * - [BottomEnd] — Below the trigger, aligned to the end edge.
 * - [TopStart] — Above the trigger, aligned to the start edge.
 * - [TopEnd] — Above the trigger, aligned to the end edge.
 * - [BottomCenter] — Below the trigger, centered horizontally.
 * - [TopCenter] — Above the trigger, centered horizontally.
 */
enum class PopoverPlacement {
    BottomStart,
    BottomEnd,
    TopStart,
    TopEnd,
    BottomCenter,
    TopCenter,
}

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
 * rendered inline -- hook your click handler there to toggle
 * [expanded].
 *
 * Features:
 * - Click-triggered (not hover) -- user controls open/close
 * - Configurable animation style via [PopoverAnimation]
 * - Configurable placement via [PopoverPlacement]
 * - Configurable max width via [maxWidth]
 * - Theme-aware card styling with border and shadow
 * - Dismiss on outside click via [Popup.onDismissRequest]
 * - Constrained max width to prevent full-screen
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
 *
 * // With custom animation and placement:
 * Popover(
 *     expanded = open,
 *     onDismiss = { open = false },
 *     animation = PopoverAnimation.Fade,
 *     placement = PopoverPlacement.TopStart,
 *     maxWidth = 400.dp,
 *     trigger = { Button("Open", onClick = { open = true }) },
 * ) {
 *     Text("Custom popover content.")
 * }
 * ```
 *
 * @param expanded Whether the popover popup is currently
 *     visible.
 * @param onDismiss Called when the user clicks outside the
 *     popup to dismiss it.
 * @param modifier Modifier applied to the outer trigger
 *     wrapper.
 * @param animation The animation style for entrance and exit.
 *     Defaults to [PopoverAnimation.FadeExpand].
 * @param placement Where the popup appears relative to the
 *     trigger. Defaults to [PopoverPlacement.BottomStart].
 * @param maxWidth Maximum width of the popup card. Defaults
 *     to 360.dp.
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
    animation: PopoverAnimation = PopoverAnimation.FadeExpand,
    placement: PopoverPlacement = PopoverPlacement.BottomStart,
    minWidth: Dp = 120.dp,
    maxWidth: Dp = 360.dp,
    trigger: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val motion = RikkaTheme.motion

    val popupAlignment = resolvePlacement(placement)

    var showPopup by remember { mutableStateOf(false) }
    LaunchedEffect(expanded) {
        if (expanded) showPopup = true
    }

    Box(modifier = modifier) {
        trigger()

        if (showPopup) {
            Popup(
                alignment = popupAlignment,
                onDismissRequest = onDismiss,
            ) {
                when (animation) {
                    PopoverAnimation.FadeExpand -> {
                        val expandFrom = resolveExpandFrom(placement)
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
                                        expandFrom = expandFrom,
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
                                        shrinkTowards = expandFrom,
                                    ),
                        ) {
                            PopoverCard(
                                minWidth = minWidth,
                                maxWidth = maxWidth,
                                content = content,
                            )
                        }
                    }

                    PopoverAnimation.Fade -> {
                        AnimatedVisibility(
                            visible = expanded,
                            enter =
                                fadeIn(
                                    animationSpec =
                                        tween(
                                            motion.durationDefault,
                                        ),
                                ),
                            exit =
                                fadeOut(
                                    animationSpec =
                                        tween(
                                            motion.durationDefault,
                                        ),
                                ),
                        ) {
                            PopoverCard(
                                minWidth = minWidth,
                                maxWidth = maxWidth,
                                content = content,
                            )
                        }
                    }

                    PopoverAnimation.None -> {
                        PopoverCard(
                            minWidth = minWidth,
                            maxWidth = maxWidth,
                            content = content,
                        )
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

// ─── Private helpers ────────────────────────────────────────

/**
 * The shared card styling for the popover popup content.
 */
@Composable
private fun PopoverCard(
    minWidth: Dp,
    maxWidth: Dp,
    content: @Composable () -> Unit,
) {
    val colors = RikkaTheme.colors
    val shapes = RikkaTheme.shapes
    val spacing = RikkaTheme.spacing

    Box(
        modifier =
            Modifier
                .widthIn(min = minWidth, max = maxWidth)
                .shadow(8.dp, shapes.md)
                .border(1.dp, colors.border, shapes.md)
                .background(colors.popover, shapes.md)
                .clip(shapes.md)
                .padding(spacing.lg),
    ) {
        content()
    }
}

/**
 * Maps [PopoverPlacement] to a Compose [Alignment] for the
 * [Popup].
 */
private fun resolvePlacement(placement: PopoverPlacement): Alignment =
    when (placement) {
        PopoverPlacement.BottomStart -> Alignment.BottomStart
        PopoverPlacement.BottomEnd -> Alignment.BottomEnd
        PopoverPlacement.TopStart -> Alignment.TopStart
        PopoverPlacement.TopEnd -> Alignment.TopEnd
        PopoverPlacement.BottomCenter -> Alignment.BottomCenter
        PopoverPlacement.TopCenter -> Alignment.TopCenter
    }

/**
 * Resolves the vertical expand anchor based on placement so
 * the expand animation originates from the correct edge.
 */
private fun resolveExpandFrom(placement: PopoverPlacement): Alignment.Vertical =
    when (placement) {
        PopoverPlacement.BottomStart,
        PopoverPlacement.BottomEnd,
        PopoverPlacement.BottomCenter,
        -> Alignment.Top

        PopoverPlacement.TopStart,
        PopoverPlacement.TopEnd,
        PopoverPlacement.TopCenter,
        -> Alignment.Bottom
    }
