package zed.rainxch.rikkaui.components.ui.spinner

import androidx.compose.animation.core.LinearEasing
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

// ─── Component ──────────────────────────────────────────────

/**
 * Spinner component for the RikkaUi design system.
 *
 * A circular loading indicator that rotates continuously.
 * Draws a partial arc using [Canvas] with configurable size and color.
 *
 * Usage:
 * ```
 * Spinner()
 * Spinner(size = SpinnerSize.Sm)
 * Spinner(size = SpinnerSize.Lg, color = RikkaTheme.colors.destructive)
 * ```
 *
 * @param modifier Modifier for layout and decoration.
 * @param size Spinner size variant — controls diameter and stroke width.
 * @param color Arc color. Defaults to [RikkaTheme.colors.primary].
 * @param label Accessibility content description. Defaults to "Loading".
 */
@Composable
fun Spinner(
    modifier: Modifier = Modifier,
    size: SpinnerSize = SpinnerSize.Default,
    color: Color = RikkaTheme.colors.primary,
    label: String = "Loading",
) {
    val motion = RikkaTheme.motion
    val infiniteTransition = rememberInfiniteTransition()

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = motion.durationSlow * 4,
                easing = LinearEasing,
            ),
        ),
    )

    Canvas(
        modifier = modifier
            .size(size.diameter)
            .graphicsLayer { rotationZ = rotation }
            .semantics { contentDescription = label },
    ) {
        val strokeWidth = size.stroke.toPx()
        drawArc(
            color = color,
            startAngle = 0f,
            sweepAngle = 270f,
            useCenter = false,
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round,
            ),
        )
    }
}
