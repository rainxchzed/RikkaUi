package zed.rainxch.rikkaui.components.ui.spinner

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
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
import zed.rainxch.rikkaui.foundation.LocalContentColor
import zed.rainxch.rikkaui.foundation.RikkaMotion
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Size ───────────────────────────────────────────────────

/** Spinner size variants. */
enum class SpinnerSize(
    val diameter: Dp,
    val stroke: Dp,
) {
    Sm(diameter = 16.dp, stroke = 1.5.dp),
    Default(diameter = 24.dp, stroke = 2.dp),
    Lg(diameter = 32.dp, stroke = 3.dp),
}

// ─── Animation ──────────────────────────────────────────────

/** Spinner animation variants. */
enum class SpinnerAnimation {
    /** Continuous rotation. */
    Spin,

    /** Pulsing opacity. */
    Pulse,

    /** Static, no animation. */
    None,
}

// ─── Component ──────────────────────────────────────────────

@Composable
fun Spinner(
    modifier: Modifier = Modifier,
    size: SpinnerSize = SpinnerSize.Default,
    animation: SpinnerAnimation = SpinnerAnimation.Spin,
    color: Color = Color.Unspecified,
    trackColor: Color? = RikkaTheme.colors.muted,
    sweepAngle: Float = 240f,
    label: String = "Loading",
) {
    val resolvedColor =
        when {
            color != Color.Unspecified -> color
            LocalContentColor.current != Color.Unspecified -> LocalContentColor.current
            else -> RikkaTheme.colors.primary
        }
    val motion = RikkaTheme.motion

    val resolved = resolveSpinnerAnimation(animation, motion)

    val inset = size.stroke / 2

    Canvas(
        modifier =
            modifier
                .size(size.diameter)
                .padding(inset)
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
            color = resolvedColor,
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
    motion: RikkaMotion,
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
