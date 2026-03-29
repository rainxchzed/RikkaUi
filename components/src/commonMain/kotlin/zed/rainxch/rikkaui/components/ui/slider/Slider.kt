package zed.rainxch.rikkaui.components.ui.slider

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.foundation.RikkaMotion
import zed.rainxch.rikkaui.foundation.RikkaTheme
import kotlin.math.roundToInt

// ─── Animation Enum ─────────────────────────────────────────

enum class SliderAnimation {
    /** Spring physics — default. */
    Spring,

    /** Smooth eased tween. */
    Tween,

    /** Instant, no interpolation. */
    None,
}

// ─── Component ──────────────────────────────────────────────

@Composable
fun Slider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    animation: SliderAnimation = SliderAnimation.Spring,
    thumbSize: Dp = 20.dp,
    trackHeight: Dp = 6.dp,
    trackColor: Color = Color.Unspecified,
    fillColor: Color = Color.Unspecified,
    thumbColor: Color = Color.Unspecified,
    thumbBorderColor: Color = Color.Unspecified,
    label: String = "",
) {
    val clampedValue = value.coerceIn(0f, 1f)
    val colors = RikkaTheme.colors
    val shapes = RikkaTheme.shapes
    val motion = RikkaTheme.motion
    val density = LocalDensity.current

    val thumbSizePx = with(density) { thumbSize.toPx() }

    val resolvedTrackColor =
        if (trackColor != Color.Unspecified) trackColor else colors.muted
    val resolvedFillColor =
        if (fillColor != Color.Unspecified) fillColor else colors.primary
    val resolvedThumbColor =
        if (thumbColor != Color.Unspecified) thumbColor else colors.background
    val resolvedThumbBorderColor =
        if (thumbBorderColor != Color.Unspecified) {
            thumbBorderColor
        } else {
            colors.primary
        }

    // Track the container width in px for fraction calculation.
    val trackWidthPx = remember { mutableIntStateOf(0) }

    // Keep latest callback stable for pointer input lambdas.
    val currentOnValueChange by rememberUpdatedState(onValueChange)

    // ─── Resolve animation spec from enum + theme tokens ──
    val animationSpec: AnimationSpec<Float> =
        resolveAnimationSpec(animation, motion)

    // ─── Animated fraction ────────────────────────────────
    val animatedFraction by animateFloatAsState(
        targetValue = clampedValue,
        animationSpec = animationSpec,
    )

    val percentText = "${(clampedValue * 100).toInt()}%"

    // ─── Outer container ──────────────────────────────────
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(thumbSize)
                .then(
                    if (!enabled) {
                        Modifier.graphicsLayer { alpha = 0.5f }
                    } else {
                        Modifier
                    },
                ).onSizeChanged { trackWidthPx.intValue = it.width }
                .then(
                    if (enabled) {
                        Modifier
                            .pointerInput(Unit) {
                                detectTapGestures { offset ->
                                    val width =
                                        trackWidthPx.intValue.toFloat()
                                    if (width > 0f) {
                                        val newValue =
                                            (offset.x / width)
                                                .coerceIn(0f, 1f)
                                        currentOnValueChange(newValue)
                                    }
                                }
                            }.pointerInput(Unit) {
                                detectHorizontalDragGestures { change, _ ->
                                    change.consume()
                                    val width =
                                        trackWidthPx.intValue.toFloat()
                                    if (width > 0f) {
                                        val newValue =
                                            (change.position.x / width)
                                                .coerceIn(0f, 1f)
                                        currentOnValueChange(newValue)
                                    }
                                }
                            }
                    } else {
                        Modifier
                    },
                ).semantics(mergeDescendants = true) {
                    stateDescription =
                        if (label.isNotEmpty()) {
                            "$label: $percentText"
                        } else {
                            percentText
                        }
                    if (!enabled) {
                        disabled()
                    }
                },
        contentAlignment = Alignment.CenterStart,
    ) {
        // ─── Track background ─────────────────────────────
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(trackHeight)
                    .clip(shapes.full)
                    .background(resolvedTrackColor, shapes.full),
            contentAlignment = Alignment.CenterStart,
        ) {
            // ─── Filled track ─────────────────────────────
            if (animatedFraction > 0f) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth(fraction = animatedFraction)
                            .height(trackHeight)
                            .clip(shapes.full)
                            .background(resolvedFillColor, shapes.full),
                )
            }
        }

        // ─── Thumb ────────────────────────────────────────
        val thumbOffsetPx =
            ((trackWidthPx.intValue - thumbSizePx) * animatedFraction)
                .coerceAtLeast(0f)

        Box(
            modifier =
                Modifier
                    .offset { IntOffset(thumbOffsetPx.roundToInt(), 0) }
                    .size(thumbSize)
                    .clip(CircleShape)
                    .background(resolvedThumbColor, CircleShape)
                    .border(2.dp, resolvedThumbBorderColor, CircleShape),
        )
    }
}

// ─── Private helpers ────────────────────────────────────────

@Composable
private fun resolveAnimationSpec(
    animation: SliderAnimation,
    motion: RikkaMotion,
): AnimationSpec<Float> =
    when (animation) {
        SliderAnimation.Spring -> motion.springDefault
        SliderAnimation.Tween -> motion.tweenDefault
        SliderAnimation.None -> snap()
    }
