package zed.rainxch.rikkaui.components.ui.slider

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import kotlin.math.roundToInt

// ─── Component ──────────────────────────────────────────────

/**
 * Slider component for the RikkaUi design system.
 *
 * A draggable range input that lets users select a value between
 * 0 and 1. Maps to shadcn/ui's Slider component.
 *
 * Features:
 * - Smooth thumb animation with spring physics
 * - Tap-to-seek on the track
 * - Horizontal drag gesture for continuous adjustment
 * - Accessibility semantics with percentage state
 * - No Material dependency
 *
 * Usage:
 * ```
 * var volume by remember { mutableFloatStateOf(0.5f) }
 *
 * Slider(
 *     value = volume,
 *     onValueChange = { volume = it },
 * )
 *
 * // Disabled
 * Slider(
 *     value = 0.3f,
 *     onValueChange = {},
 *     enabled = false,
 * )
 * ```
 *
 * @param value Current value, clamped to 0f..1f.
 * @param onValueChange Called when the user drags or taps to change the value.
 * @param modifier Modifier for layout and decoration.
 * @param enabled Whether the slider is interactive.
 * @param label Accessibility label for screen readers.
 */
@Composable
fun Slider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = "",
) {
    val clampedValue = value.coerceIn(0f, 1f)
    val colors = RikkaTheme.colors
    val shapes = RikkaTheme.shapes
    val motion = RikkaTheme.motion
    val density = LocalDensity.current

    val thumbSizeDp = 20.dp
    val trackHeightDp = 6.dp
    val thumbSizePx = with(density) { thumbSizeDp.toPx() }

    // Track the container width in px for fraction calculation.
    val trackWidthPx = remember { mutableIntStateOf(0) }

    // Keep latest callback stable for pointer input lambdas.
    val currentOnValueChange by rememberUpdatedState(onValueChange)

    // ─── Animated fraction (spring physics) ──────────────
    val animatedFraction by animateFloatAsState(
        targetValue = clampedValue,
        animationSpec = motion.springDefault,
    )

    val percentText = "${(clampedValue * 100).toInt()}%"

    // ─── Outer container ─────────────────────────────────
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(thumbSizeDp)
                .then(
                    if (!enabled) {
                        Modifier.graphicsLayer { alpha = 0.5f }
                    } else {
                        Modifier
                    },
                )
                .onSizeChanged { trackWidthPx.intValue = it.width }
                .then(
                    if (enabled) {
                        Modifier
                            .pointerInput(Unit) {
                                detectTapGestures { offset ->
                                    val width = trackWidthPx.intValue.toFloat()
                                    if (width > 0f) {
                                        val newValue = (offset.x / width).coerceIn(0f, 1f)
                                        currentOnValueChange(newValue)
                                    }
                                }
                            }
                            .pointerInput(Unit) {
                                detectHorizontalDragGestures { change, _ ->
                                    change.consume()
                                    val width = trackWidthPx.intValue.toFloat()
                                    if (width > 0f) {
                                        val newValue =
                                            (change.position.x / width).coerceIn(0f, 1f)
                                        currentOnValueChange(newValue)
                                    }
                                }
                            }
                    } else {
                        Modifier
                    },
                )
                .semantics(mergeDescendants = true) {
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
        // ─── Track background ────────────────────────────
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(trackHeightDp)
                    .clip(shapes.full)
                    .background(colors.muted, shapes.full),
            contentAlignment = Alignment.CenterStart,
        ) {
            // ─── Filled track ────────────────────────────
            if (animatedFraction > 0f) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth(fraction = animatedFraction)
                            .height(trackHeightDp)
                            .clip(shapes.full)
                            .background(colors.primary, shapes.full),
                )
            }
        }

        // ─── Thumb ───────────────────────────────────────
        val thumbOffsetPx =
            ((trackWidthPx.intValue - thumbSizePx) * animatedFraction)
                .coerceAtLeast(0f)

        Box(
            modifier =
                Modifier
                    .offset { IntOffset(thumbOffsetPx.roundToInt(), 0) }
                    .size(thumbSizeDp)
                    .clip(CircleShape)
                    .background(colors.background, CircleShape)
                    .border(2.dp, colors.primary, CircleShape),
        )
    }
}
