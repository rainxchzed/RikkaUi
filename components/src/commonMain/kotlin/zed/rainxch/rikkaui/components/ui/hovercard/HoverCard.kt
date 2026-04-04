package zed.rainxch.rikkaui.components.ui.hovercard

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import kotlinx.coroutines.delay
import zed.rainxch.rikkaui.foundation.LocalContentColor
import zed.rainxch.rikkaui.foundation.LocalTextStyle
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Animation ─────────────────────────────────────────────

public enum class HoverCardAnimation {
    /** Fade + scale (default). */
    FadeScale,

    /** Opacity fade only. */
    Fade,

    /** No animation. */
    None,
}

// ─── Placement ─────────────────────────────────────────────

public enum class HoverCardPlacement {
    BottomStart,
    BottomEnd,
    TopStart,
    TopEnd,
}

// ─── Constants ──────────────────────────────────────────────

private const val DEFAULT_SHOW_DELAY_MS = 300L
private const val DEFAULT_HIDE_DELAY_MS = 200L

// ─── Component ──────────────────────────────────────────────

/**
 * A hover-triggered popup card that displays additional content with configurable delay.
 *
 * Shows on hover or focus of the trigger element, and remains visible while the cursor
 * is over either the trigger or the card itself. Hides after the [hideDelayMs] delay
 * when the cursor leaves both. Press Escape to dismiss immediately.
 *
 * @param modifier [Modifier] applied to the outer container wrapping trigger and popup.
 * @param label Accessibility pane title for the hover card. Defaults to "Additional information".
 * @param animation [HoverCardAnimation] style for show/hide transitions. Defaults to [HoverCardAnimation.FadeScale].
 * @param placement [HoverCardPlacement] controlling where the card appears relative to the trigger. Defaults to [HoverCardPlacement.BottomStart].
 * @param showDelayMs Delay in milliseconds before the card appears on hover. Defaults to 300ms.
 * @param hideDelayMs Delay in milliseconds before the card disappears after hover ends. Defaults to 200ms.
 * @param maxWidth Maximum width of the hover card. Defaults to 360.dp.
 * @param trigger Composable content that acts as the hover/focus target.
 * @param content Composable content rendered inside the hover card.
 */
@Composable
public fun HoverCard(
    modifier: Modifier = Modifier,
    label: String = "Additional information",
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
    val isTriggerFocused by triggerInteraction.collectIsFocusedAsState()

    val cardInteraction = remember { MutableInteractionSource() }
    val isCardHovered by cardInteraction.collectIsHoveredAsState()

    var isVisible by remember { mutableStateOf(false) }

    val colors = RikkaTheme.colors
    val shapes = RikkaTheme.shapes
    val spacing = RikkaTheme.spacing
    val motion = RikkaTheme.motion

    val popupAlignment = resolvePlacement(placement)

    // ─── Show/hide with delays ───────────────────────────
    val isAnyActive = isTriggerHovered || isTriggerFocused || isCardHovered

    LaunchedEffect(isAnyActive) {
        if (isAnyActive) {
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
        Box(
            modifier =
                Modifier
                    .onKeyEvent { event ->
                        if (event.type == KeyEventType.KeyDown &&
                            event.key == Key.Escape
                        ) {
                            isVisible = false
                            true
                        } else {
                            false
                        }
                    }.hoverable(triggerInteraction)
                    .focusable(interactionSource = triggerInteraction),
        ) {
            trigger()
        }

        if (showPopup) {
            Popup(
                alignment = popupAlignment,
                onDismissRequest = { isVisible = false },
            ) {
                Box(
                    modifier =
                        Modifier
                            .semantics { paneTitle = label }
                            .hoverable(cardInteraction)
                            .graphicsLayer {
                                this.alpha = alpha
                                scaleX = scale
                                scaleY = scale
                            }.defaultMinSize(minWidth = 250.dp)
                            .widthIn(max = maxWidth)
                            .shadow(RikkaTheme.elevation.high, shapes.md)
                            .border(
                                1.dp,
                                colors.border,
                                shapes.md,
                            ).background(
                                colors.surface,
                                shapes.md,
                            ).clip(shapes.md)
                            .padding(spacing.lg),
                ) {
                    CompositionLocalProvider(
                        LocalContentColor provides colors.onSurface,
                        LocalTextStyle provides RikkaTheme.typography.small,
                    ) {
                        content()
                    }
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
