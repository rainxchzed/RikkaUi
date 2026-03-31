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

enum class HoverCardAnimation {
    /** Fade + scale (default). */
    FadeScale,

    /** Opacity fade only. */
    Fade,

    /** No animation. */
    None,
}

// ─── Placement ─────────────────────────────────────────────

enum class HoverCardPlacement {
    BottomStart,
    BottomEnd,
    TopStart,
    TopEnd,
}

// ─── Constants ──────────────────────────────────────────────

private const val DEFAULT_SHOW_DELAY_MS = 300L
private const val DEFAULT_HIDE_DELAY_MS = 200L

// ─── Component ──────────────────────────────────────────────

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
    val targetScale = if (isVisible) 1f else motion.overlayScaleIn

    val alpha by animateFloatAsState(
        targetValue = if (useAnimation) targetAlpha else targetAlpha,
        animationSpec =
            if (useAnimation) {
                tween(motion.durationDefault)
            } else {
                tween(motion.durationInstant)
            },
    )

    val scale by animateFloatAsState(
        targetValue =
            when (animation) {
                HoverCardAnimation.FadeScale -> {
                    if (isVisible) 1f else motion.overlayScaleIn
                }

                else -> {
                    1f
                }
            },
        animationSpec =
            if (animation == HoverCardAnimation.FadeScale) {
                tween(motion.durationDefault)
            } else {
                tween(motion.durationInstant)
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

private fun resolvePlacement(placement: HoverCardPlacement): Alignment =
    when (placement) {
        HoverCardPlacement.BottomStart -> Alignment.BottomStart
        HoverCardPlacement.BottomEnd -> Alignment.BottomEnd
        HoverCardPlacement.TopStart -> Alignment.TopStart
        HoverCardPlacement.TopEnd -> Alignment.TopEnd
    }
