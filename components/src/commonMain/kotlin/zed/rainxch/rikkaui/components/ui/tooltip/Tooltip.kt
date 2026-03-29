package zed.rainxch.rikkaui.components.ui.tooltip

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.window.Popup
import kotlinx.coroutines.delay
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Animation ─────────────────────────────────────────────

/**
 * Animation style for the [Tooltip] entrance and exit.
 *
 * - [FadeScale] — Fade combined with a subtle scale-up from
 *   95% to 100% (default). Feels lively and polished.
 * - [Fade] — Simple opacity fade only. Lighter and subtler.
 * - [None] — Instant appear/disappear with no animation.
 */
enum class TooltipAnimation {
    /** Fade + scale from 95% to 100% (default). */
    FadeScale,

    /** Simple opacity fade only. */
    Fade,

    /** Instant appear/disappear, no animation. */
    None,
}

// ─── Placement ─────────────────────────────────────────────

/**
 * Placement of the [Tooltip] relative to the trigger content.
 *
 * - [Top] — Above the content, centered (default).
 * - [Bottom] — Below the content, centered.
 * - [Start] — To the start (left in LTR) of the content.
 * - [End] — To the end (right in LTR) of the content.
 */
enum class TooltipPlacement {
    Top,
    Bottom,
    Start,
    End,
}

// ─── Constants ──────────────────────────────────────────────

/** Default delay before showing the tooltip (ms). */
private const val DEFAULT_SHOW_DELAY_MS = 400L

// ─── Component ──────────────────────────────────────────────

/**
 * Tooltip wrapper for the RikkaUi design system.
 *
 * Wraps any composable and shows a small popup label on hover.
 * The tooltip uses inverted theme colors (foreground as
 * background, background as text) for clear contrast. No
 * Material dependency.
 *
 * Features:
 * - Configurable animation style via [TooltipAnimation]
 * - Configurable placement via [TooltipPlacement]
 * - Configurable show delay via [showDelayMs]
 * - Inverted color scheme for contrast on any theme
 * - Hover-triggered via [MutableInteractionSource]
 * - All animations use theme motion tokens
 *
 * Usage:
 * ```
 * Tooltip(tooltip = "Save changes") {
 *     Button("Save", onClick = { save() })
 * }
 *
 * // With custom animation, placement, and delay:
 * Tooltip(
 *     tooltip = "Copy to clipboard",
 *     animation = TooltipAnimation.Fade,
 *     placement = TooltipPlacement.Bottom,
 *     showDelayMs = 200L,
 * ) {
 *     IconButton(onClick = { copy() }) {
 *         Icon(Icons.Copy)
 *     }
 * }
 * ```
 *
 * @param tooltip The text to display inside the tooltip popup.
 * @param modifier Modifier applied to the outer wrapper.
 * @param animation The animation style for entrance and exit.
 *     Defaults to [TooltipAnimation.FadeScale].
 * @param placement Where the tooltip appears relative to the
 *     content. Defaults to [TooltipPlacement.Top].
 * @param showDelayMs Delay in milliseconds before showing the
 *     tooltip after hover starts. Defaults to 400ms. Set to
 *     0L for no delay.
 * @param content The composable content that triggers the
 *     tooltip on hover.
 */
@Composable
fun Tooltip(
    tooltip: String,
    modifier: Modifier = Modifier,
    animation: TooltipAnimation = TooltipAnimation.FadeScale,
    placement: TooltipPlacement = TooltipPlacement.Top,
    showDelayMs: Long = DEFAULT_SHOW_DELAY_MS,
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val motion = RikkaTheme.motion
    val colors = RikkaTheme.colors
    val shape = RikkaTheme.shapes.sm

    // ─── Delayed visibility ──────────────────────────────
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(isHovered) {
        if (isHovered) {
            if (showDelayMs > 0L) delay(showDelayMs)
            isVisible = true
        } else {
            isVisible = false
        }
    }

    // ─── Fade animation (from theme motion tokens) ──────
    val targetAlpha = if (isVisible) 1f else 0f
    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec =
            when (animation) {
                TooltipAnimation.None -> tween(0)
                else -> tween(motion.durationEnter)
            },
    )

    val scale by animateFloatAsState(
        targetValue =
            when (animation) {
                TooltipAnimation.FadeScale -> {
                    if (isVisible) 1f else 0.95f
                }

                else -> {
                    1f
                }
            },
        animationSpec =
            when (animation) {
                TooltipAnimation.FadeScale -> tween(motion.durationEnter)
                else -> tween(0)
            },
    )

    // ─── Content measurement for popup placement ────────
    var contentSize by remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current

    val showPopup =
        when (animation) {
            TooltipAnimation.None -> isVisible
            else -> isVisible || alpha > 0f
        }

    // ─── Resolve popup alignment and offset ─────────────
    val popupAlignment = resolveAlignment(placement)
    val popupOffset = resolveOffset(placement, density)

    Box(
        modifier =
            modifier
                .hoverable(interactionSource)
                .onGloballyPositioned { coordinates ->
                    contentSize = coordinates.size
                },
    ) {
        content()

        // ─── Tooltip popup ──────────────────────────────
        if (showPopup) {
            Popup(
                alignment = popupAlignment,
                offset = popupOffset,
            ) {
                Box(
                    modifier =
                        Modifier
                            .graphicsLayer {
                                this.alpha = alpha
                                scaleX = scale
                                scaleY = scale
                            }.background(colors.foreground, shape)
                            .clip(shape)
                            .padding(
                                horizontal = RikkaTheme.spacing.sm,
                                vertical = RikkaTheme.spacing.xs,
                            ),
                ) {
                    BasicText(
                        text = tooltip,
                        style =
                            RikkaTheme.typography.small.merge(
                                TextStyle(
                                    color = colors.background,
                                ),
                            ),
                    )
                }
            }
        }
    }
}

// ─── Private helpers ────────────────────────────────────────

/**
 * Maps [TooltipPlacement] to a Compose [Alignment] for the
 * [Popup].
 */
private fun resolveAlignment(placement: TooltipPlacement): Alignment =
    when (placement) {
        TooltipPlacement.Top -> Alignment.TopCenter
        TooltipPlacement.Bottom -> Alignment.BottomCenter
        TooltipPlacement.Start -> Alignment.CenterStart
        TooltipPlacement.End -> Alignment.CenterEnd
    }

/**
 * Resolves the [IntOffset] for the popup based on placement
 * and density, adding a small gap from the trigger.
 */
@Composable
private fun resolveOffset(
    placement: TooltipPlacement,
    density: androidx.compose.ui.unit.Density,
): IntOffset {
    val gap =
        with(density) {
            RikkaTheme.spacing.xs.roundToPx()
        }
    return when (placement) {
        TooltipPlacement.Top -> IntOffset(x = 0, y = -gap)
        TooltipPlacement.Bottom -> IntOffset(x = 0, y = gap)
        TooltipPlacement.Start -> IntOffset(x = -gap, y = 0)
        TooltipPlacement.End -> IntOffset(x = gap, y = 0)
    }
}
