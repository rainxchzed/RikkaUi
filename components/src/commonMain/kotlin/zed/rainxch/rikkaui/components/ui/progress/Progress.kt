package zed.rainxch.rikkaui.components.ui.progress

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme

// ─── Component ──────────────────────────────────────────────

/**
 * Progress bar component for the RikkaUi design system.
 *
 * A horizontal bar that fills from left to right to indicate
 * progress. Maps to shadcn/ui's Progress component.
 *
 * The fill width animates smoothly using spring physics from
 * the theme motion tokens, so updating [progress] feels fluid
 * and handles interruptions gracefully.
 *
 * Usage:
 * ```
 * // Determinate progress
 * Progress(progress = 0.6f)
 *
 * // Animated — just update the value
 * var upload by remember { mutableFloatStateOf(0f) }
 * Progress(progress = upload)
 *
 * // Custom colors
 * Progress(
 *     progress = 0.5f,
 *     trackColor = RikkaTheme.colors.secondary,
 *     fillColor = RikkaTheme.colors.destructive,
 * )
 * ```
 *
 * @param progress Current progress value, clamped to 0f..1f.
 * @param modifier Modifier for layout and decoration.
 * @param trackColor Background track color. Defaults to theme muted color.
 * @param fillColor Fill bar color. Defaults to theme primary color.
 * @param label Accessibility label describing what this progress bar represents.
 */
@Composable
fun Progress(
    progress: Float,
    modifier: Modifier = Modifier,
    trackColor: Color = Color.Unspecified,
    fillColor: Color = Color.Unspecified,
    label: String = "",
) {
    val clampedProgress = progress.coerceIn(0f, 1f)
    val motion = RikkaTheme.motion
    val colors = RikkaTheme.colors
    val shapes = RikkaTheme.shapes

    val resolvedTrackColor = if (trackColor != Color.Unspecified) trackColor else colors.muted
    val resolvedFillColor = if (fillColor != Color.Unspecified) fillColor else colors.primary

    // ─── Animated fill fraction (spring physics) ─────────
    val animatedFraction by animateFloatAsState(
        targetValue = clampedProgress,
        animationSpec = motion.springDefault,
    )

    val percentText = "${(clampedProgress * 100).toInt()}%"

    // ─── Track ───────────────────────────────────────────
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(shapes.full)
                .background(resolvedTrackColor, shapes.full)
                .semantics(mergeDescendants = true) {
                    stateDescription =
                        if (label.isNotEmpty()) {
                            "$label: $percentText"
                        } else {
                            "Progress: $percentText"
                        }
                },
        contentAlignment = Alignment.CenterStart,
    ) {
        // ─── Fill ────────────────────────────────────────
        if (animatedFraction > 0f) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth(fraction = animatedFraction)
                        .height(8.dp)
                        .clip(shapes.full)
                        .background(resolvedFillColor, shapes.full),
            )
        }
    }
}
