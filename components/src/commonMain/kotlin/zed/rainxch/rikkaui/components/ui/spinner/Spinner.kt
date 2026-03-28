package zed.rainxch.rikkaui.components.ui.spinner

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme

// ─── Size ───────────────────────────────────────────────────

/**
 * Spinner size variants.
 *
 * - [Sm] — 16dp diameter, 1.5dp stroke. Inline indicators.
 * - [Default] — 24dp diameter, 2dp stroke. Standard loading.
 * - [Lg] — 32dp diameter, 3dp stroke. Page-level loading.
 */
enum class SpinnerSize(
    val diameter: Dp,
    val stroke: Dp,
) {
    Sm(diameter = 16.dp, stroke = 1.5.dp),
    Default(diameter = 24.dp, stroke = 2.dp),
    Lg(diameter = 32.dp, stroke = 3.dp),
}

// ─── Animation ──────────────────────────────────────────────

/**
 * Spinner animation variants.
 *
 * Controls the visual feedback style of the loading indicator.
 * All animated variants respect [RikkaTheme.motion] duration tokens.
 *
 * - [Spin] — Continuous rotation (default). Classic loading spinner.
 * - [Pulse] — Pulsing opacity. Subtle, calm loading indicator.
 * - [None] — Static arc with no animation. Useful for snapshot tests
 *   or reduced-motion contexts.
 *
 * ```
 * Spinner(animation = SpinnerAnimation.Pulse)
 * Spinner(animation = SpinnerAnimation.None)
 * ```
 */
enum class SpinnerAnimation {
    Spin,
    Pulse,
    None,
}

// ─── Component ──────────────────────────────────────────────

/**
 * Spinner component for the RikkaUi design system.
 *
 * A circular loading indicator with configurable animation, size,
 * and color. Draws a partial arc using [Canvas].
 *
 * Usage:
 * ```
 * Spinner()
 * Spinner(size = SpinnerSize.Sm)
 * Spinner(size = SpinnerSize.Lg, color = RikkaTheme.colors.destructive)
 * Spinner(animation = SpinnerAnimation.Pulse)
 * Spinner(animation = SpinnerAnimation.None, label = "Paused")
 * Spinner(sweepAngle = 180f) // half-arc spinner
 * Spinner(trackColor = RikkaTheme.colors.muted) // visible track ring
 * ```
 *
 * @param modifier Modifier for layout and decoration.
 * @param size Spinner size variant — controls diameter and stroke width.
 * @param animation Animation style. Defaults to [SpinnerAnimation.Spin].
 * @param color Arc color. Defaults to [RikkaTheme.colors.primary].
 * @param trackColor Optional background track ring color. When non-null,
 *   a full 360-degree arc is drawn behind the spinner arc.
 * @param sweepAngle Arc sweep angle in degrees. Defaults to 270f.
 *   Smaller values produce a shorter arc tail.
 * @param label Accessibility content description. Defaults to "Loading".
 */
@Composable
fun Spinner(
    modifier: Modifier = Modifier,
    size: SpinnerSize = SpinnerSize.Default,
    animation: SpinnerAnimation = SpinnerAnimation.Spin,
    color: Color = RikkaTheme.colors.primary,
    trackColor: Color? = RikkaTheme.colors.muted,
    sweepAngle: Float = 240f,
    label: String = "Loading",
) {
    val motion = RikkaTheme.motion

    val resolved = resolveSpinnerAnimation(animation, motion)

    Canvas(
        modifier =
            modifier
                .size(size.diameter)
                .graphicsLayer {
                    rotationZ = resolved.rotation
                    alpha = resolved.alpha
                }.semantics { contentDescription = label },
    ) {
        val strokeWidth = size.stroke.toPx()
        val strokeStyle =
            Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round,
            )

        if (trackColor != null) {
            drawArc(
                color = trackColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = strokeStyle,
            )
        }

        drawArc(
            color = color,
            startAngle = 0f,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = strokeStyle,
        )
    }
}

// ─── Internal: Animation Resolution ────────────────────────

private data class SpinnerAnimationValues(
    val rotation: Float,
    val alpha: Float,
)

@Composable
private fun resolveSpinnerAnimation(
    animation: SpinnerAnimation,
    motion: zed.rainxch.rikkaui.components.theme.RikkaMotion,
): SpinnerAnimationValues =
    when (animation) {
        SpinnerAnimation.Spin -> {
            val infiniteTransition = rememberInfiniteTransition()
            val rotation by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec =
                    infiniteRepeatable(
                        animation =
                            tween(
                                durationMillis = 800,
                                easing = LinearEasing,
                            ),
                    ),
            )
            SpinnerAnimationValues(rotation = rotation, alpha = 1f)
        }

        SpinnerAnimation.Pulse -> {
            val infiniteTransition = rememberInfiniteTransition()
            val alpha by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 0.3f,
                animationSpec =
                    infiniteRepeatable(
                        animation =
                            tween(durationMillis = 1000),
                        repeatMode = RepeatMode.Reverse,
                    ),
            )
            SpinnerAnimationValues(rotation = 0f, alpha = alpha)
        }

        SpinnerAnimation.None -> {
            SpinnerAnimationValues(rotation = 0f, alpha = 1f)
        }
    }
