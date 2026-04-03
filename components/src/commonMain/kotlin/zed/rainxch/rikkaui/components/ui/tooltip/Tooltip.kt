package zed.rainxch.rikkaui.components.ui.tooltip

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
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
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.window.Popup
import kotlinx.coroutines.delay
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Animation ─────────────────────────────────────────────

enum class TooltipAnimation {
    /** Fade + scale from 95 % to 100 %. */
    FadeScale,

    /** Opacity fade only. */
    Fade,

    /** No animation. */
    None,
}

// ─── Placement ─────────────────────────────────────────────

enum class TooltipPlacement {
    Top,
    Bottom,
    Start,
    End,
}

// ─── Constants ──────────────────────────────────────────────

private const val DEFAULT_SHOW_DELAY_MS = 400L

// ─── Component ──────────────────────────────────────────────

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
    val isFocused by interactionSource.collectIsFocusedAsState()

    val motion = RikkaTheme.motion
    val colors = RikkaTheme.colors
    val shape = RikkaTheme.shapes.sm

    // ─── Delayed visibility ──────────────────────────────
    var isVisible by remember { mutableStateOf(false) }
    val isTriggered = isHovered || isFocused

    LaunchedEffect(isTriggered) {
        if (isTriggered) {
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
                TooltipAnimation.None -> tween(motion.durationInstant)
                else -> tween(motion.durationEnter)
            },
    )

    val scale by animateFloatAsState(
        targetValue =
            when (animation) {
                TooltipAnimation.FadeScale -> {
                    if (isVisible) 1f else motion.overlayScaleIn
                }

                else -> {
                    1f
                }
            },
        animationSpec =
            when (animation) {
                TooltipAnimation.FadeScale -> tween(motion.durationEnter)
                else -> tween(motion.durationInstant)
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
                .onKeyEvent { event ->
                    if (event.type == KeyEventType.KeyDown &&
                        event.key == Key.Escape
                    ) {
                        isVisible = false
                        true
                    } else {
                        false
                    }
                }.hoverable(interactionSource)
                .focusable(interactionSource = interactionSource)
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
                            .semantics { paneTitle = tooltip }
                            .graphicsLayer {
                                this.alpha = alpha
                                scaleX = scale
                                scaleY = scale
                            }.background(colors.onBackground, shape)
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

private fun resolveAlignment(placement: TooltipPlacement): Alignment =
    when (placement) {
        TooltipPlacement.Top -> Alignment.TopCenter
        TooltipPlacement.Bottom -> Alignment.BottomCenter
        TooltipPlacement.Start -> Alignment.CenterStart
        TooltipPlacement.End -> Alignment.CenterEnd
    }

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
