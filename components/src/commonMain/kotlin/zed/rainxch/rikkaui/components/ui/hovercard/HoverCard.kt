package zed.rainxch.rikkaui.components.ui.hovercard

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import kotlinx.coroutines.delay
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Animation ─────────────────────────────────────────────

/**
 * Animation style for the [HoverCard] entrance and exit.
 *
 * - [FadeScale] — Fade combined with a subtle scale-up from
 *   95% to 100% (default). Feels lively and connected.
 * - [Fade] — Simple opacity fade only. Lighter and subtler.
 * - [None] — Instant appear/disappear with no animation.
 */
enum class HoverCardAnimation {
    /** Fade + scale from 95% to 100% (default). */
    FadeScale,

    /** Simple opacity fade only. */
    Fade,

    /** Instant appear/disappear, no animation. */
    None,
}

// ─── Placement ─────────────────────────────────────────────

/**
 * Placement of the [HoverCard] popup relative to the trigger.
 *
 * - [BottomStart] — Below the trigger, aligned to the start
 *   edge (default).
 * - [BottomEnd] — Below the trigger, aligned to the end edge.
 * - [TopStart] — Above the trigger, aligned to the start edge.
 * - [TopEnd] — Above the trigger, aligned to the end edge.
 */
enum class HoverCardPlacement {
    BottomStart,
    BottomEnd,
    TopStart,
    TopEnd,
}

// ─── Constants ──────────────────────────────────────────────

/** Default delay before showing the hover card (ms). */
private const val DEFAULT_SHOW_DELAY_MS = 300L

/** Default delay before hiding the hover card (ms). */
private const val DEFAULT_HIDE_DELAY_MS = 200L

// ─── Component ──────────────────────────────────────────────

/**
 * Hover card component for the RikkaUi design system.
 *
 * Shows a floating card when the user hovers over the trigger
 * element. The card appears after a short delay and stays
 * visible while the cursor is over the trigger or the card
 * itself. Maps to shadcn/ui's Hover Card.
 *
 * Features:
 * - Hover-triggered with configurable show/hide delays
 * - Configurable animation style via [HoverCardAnimation]
 * - Configurable placement via [HoverCardPlacement]
 * - Configurable max width via [maxWidth]
 * - Card stays visible while cursor moves from trigger to card
 * - Theme-aware styling with border and shadow
 * - No Material3 dependency
 *
 * Usage:
 * ```
 * HoverCard(
 *     trigger = {
 *         Text("@rikka")
 *     },
 * ) {
 *     Column {
 *         Text("RikkaUi", variant = TextVariant.Large)
 *         Text("A shadcn-inspired component library.")
 *     }
 * }
 *
 * // With custom animation, placement, and delays:
 * HoverCard(
 *     animation = HoverCardAnimation.Fade,
 *     placement = HoverCardPlacement.TopStart,
 *     showDelayMs = 500L,
 *     hideDelayMs = 300L,
 *     maxWidth = 400.dp,
 *     trigger = { Text("Hover me") },
 * ) {
 *     Text("Custom hover card content.")
 * }
 * ```
 *
 * @param modifier Modifier applied to the outer wrapper.
 * @param animation The animation style for entrance and exit.
 *     Defaults to [HoverCardAnimation.FadeScale].
 * @param placement Where the popup appears relative to the
 *     trigger. Defaults to [HoverCardPlacement.BottomStart].
 * @param showDelayMs Delay in milliseconds before showing the
 *     card after hover starts. Defaults to 300ms.
 * @param hideDelayMs Delay in milliseconds before hiding the
 *     card after hover ends. Defaults to 200ms.
 * @param maxWidth Maximum width of the popup card. Defaults
 *     to 360.dp.
 * @param trigger The composable that triggers the hover card
 *     on hover.
 * @param content The composable content displayed inside the
 *     floating card.
 */
@Composable
fun HoverCard(
    modifier: Modifier = Modifier,
    animation: HoverCardAnimation = HoverCardAnimation.FadeScale,
    placement: HoverCardPlacement = HoverCardPlacement.BottomStart,
    showDelayMs: Long = DEFAULT_SHOW_DELAY_MS,
    hideDelayMs: Long = DEFAULT_HIDE_DELAY_MS,
    maxWidth: Dp = 360.dp,
    trigger: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val triggerInteraction = remember { MutableInteractionSource() }
    val isTriggerHovered by triggerInteraction.collectIsHoveredAsState()

    val cardInteraction = remember { MutableInteractionSource() }
    val isCardHovered by cardInteraction.collectIsHoveredAsState()

    var isVisible by remember { mutableStateOf(false) }

    val colors = RikkaTheme.colors
    val shapes = RikkaTheme.shapes
    val spacing = RikkaTheme.spacing
    val motion = RikkaTheme.motion

    val popupAlignment = resolvePlacement(placement)

    // ─── Show/hide with delays ───────────────────────────
    val isAnyHovered = isTriggerHovered || isCardHovered

    LaunchedEffect(isAnyHovered) {
        if (isAnyHovered) {
            delay(showDelayMs)
            isVisible = true
        } else {
            delay(hideDelayMs)
            isVisible = false
        }
    }

    // ─── Animation values ────────────────────────────────
    val useAnimation = animation != HoverCardAnimation.None
    val targetAlpha = if (isVisible) 1f else 0f
    val targetScale = if (isVisible) 1f else 0.95f

    val alpha by animateFloatAsState(
        targetValue = if (useAnimation) targetAlpha else targetAlpha,
        animationSpec =
            if (useAnimation) {
                tween(motion.durationDefault)
            } else {
                tween(0)
            },
    )

    val scale by animateFloatAsState(
        targetValue =
            when (animation) {
                HoverCardAnimation.FadeScale -> {
                    if (isVisible) 1f else 0.95f
                }

                else -> {
                    1f
                }
            },
        animationSpec =
            if (animation == HoverCardAnimation.FadeScale) {
                tween(motion.durationDefault)
            } else {
                tween(0)
            },
    )

    val showPopup =
        when (animation) {
            HoverCardAnimation.None -> isVisible
            else -> isVisible || alpha > 0f
        }

    Box(modifier = modifier) {
        Box(modifier = Modifier.hoverable(triggerInteraction)) {
            trigger()
        }

        if (showPopup) {
            Popup(alignment = popupAlignment) {
                Box(
                    modifier =
                        Modifier
                            .hoverable(cardInteraction)
                            .graphicsLayer {
                                this.alpha = alpha
                                scaleX = scale
                                scaleY = scale
                            }.defaultMinSize(minWidth = 250.dp)
                            .widthIn(max = maxWidth)
                            .shadow(8.dp, shapes.md)
                            .border(
                                1.dp,
                                colors.border,
                                shapes.md,
                            ).background(
                                colors.popover,
                                shapes.md,
                            ).clip(shapes.md)
                            .padding(spacing.lg),
                ) {
                    content()
                }
            }
        }
    }
}

// ─── Private helpers ────────────────────────────────────────

/**
 * Maps [HoverCardPlacement] to a Compose [Alignment] for the
 * [Popup].
 */
private fun resolvePlacement(placement: HoverCardPlacement): Alignment =
    when (placement) {
        HoverCardPlacement.BottomStart -> Alignment.BottomStart
        HoverCardPlacement.BottomEnd -> Alignment.BottomEnd
        HoverCardPlacement.TopStart -> Alignment.TopStart
        HoverCardPlacement.TopEnd -> Alignment.TopEnd
    }
