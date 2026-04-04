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
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.foundation.LocalContentColor
import zed.rainxch.rikkaui.foundation.RikkaMotion
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Size ───────────────────────────────────────────────────

/** Spinner size variants. */
public enum class SpinnerSize(
    public val diameter: Dp,
    public val stroke: Dp,
) {
    Sm(diameter = 16.dp, stroke = 1.5.dp),
    Default(diameter = 24.dp, stroke = 2.dp),
    Lg(diameter = 32.dp, stroke = 3.dp),
}

// ─── Animation ──────────────────────────────────────────────

/** Spinner animation variants. */
public enum class SpinnerAnimation {
    /** Continuous rotation. */
    Spin,

    /** Pulsing opacity. */
    Pulse,

    /** Static, no animation. */
    None,
}

// ─── Component ──────────────────────────────────────────────

/**
 * Rotating or pulsing loading indicator drawn as a circular arc with an optional track.
 *
 * Color resolution order: explicit [color] > [LocalContentColor] > theme primary.
 * Accessibility semantics include an indeterminate progress bar announcement and
 * the provided [label] as content description.
 *
 * ```
 * Spinner()
 *
 * Spinner(
 *     size = SpinnerSize.Lg,
 *     animation = SpinnerAnimation.Pulse,
 *     color = RikkaTheme.colors.destructive,
 *     label = "Submitting",
 * )
 * ```
 *
 * @param modifier [Modifier] applied to the spinner canvas.
 * @param size [SpinnerSize] controlling the diameter and stroke width. Defaults to [SpinnerSize.Default].
 * @param animation [SpinnerAnimation] controlling the visual effect (Spin, Pulse, or None). Defaults to [SpinnerAnimation.Spin].
 * @param color Explicit arc color. [Color.Unspecified] defers to [LocalContentColor] then theme primary.
 * @param trackColor Background track circle color. Defaults to theme muted. Pass null to hide the track.
 * @param sweepAngle Arc sweep angle in degrees. Defaults to 240f.
 * @param label Accessibility content description. Defaults to "Loading".
 */
@Composable
public fun Spinner(
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
                }.semantics {
                    contentDescription = label
                    progressBarRangeInfo = ProgressBarRangeInfo.Indeterminate
                },
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
                                durationMillis = motion.durationSpin,
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
                            tween(durationMillis = motion.durationPulse),
                        repeatMode = RepeatMode.Reverse,
                    ),
            )
            SpinnerAnimationValues(rotation = 0f, alpha = alpha)
        }

        SpinnerAnimation.None -> {
            SpinnerAnimationValues(rotation = 0f, alpha = 1f)
        }
    }
