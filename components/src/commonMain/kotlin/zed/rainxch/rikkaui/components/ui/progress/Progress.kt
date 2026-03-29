package zed.rainxch.rikkaui.components.ui.progress

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.foundation.RikkaMotion
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Animation Enum ─────────────────────────────────────────

/**
 * Animation style for [Progress] value transitions.
 *
 * Controls how the fill bar interpolates between old and new
 * progress values. Each option maps to theme motion tokens so
 * the feel stays consistent with the active [RikkaTheme].
 *
 * ```
 * // Bouncy spring (default)
 * Progress(progress = 0.7f, animation = ProgressAnimation.Spring)
 *
 * // Smooth eased tween
 * Progress(progress = 0.7f, animation = ProgressAnimation.Tween)
 *
 * // Instant jump
 * Progress(progress = 0.7f, animation = ProgressAnimation.None)
 * ```
 */
enum class ProgressAnimation {
    /** Spring physics — bouncy, handles interruptions gracefully. Default. */
    Spring,

    /** Smooth eased tween using theme duration. */
    Tween,

    /** Instant value jump with no interpolation. */
    None,
}

// ─── Component ──────────────────────────────────────────────

/**
 * Progress bar component for the RikkaUi design system.
 *
 * A horizontal bar that fills from left to right to indicate
 * progress. Maps to shadcn/ui's Progress component.
 *
 * The fill width animates using the selected [animation] style
 * (spring by default), so updating [progress] feels fluid
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
 * // Custom colors and height
 * Progress(
 *     progress = 0.5f,
 *     trackColor = RikkaTheme.colors.secondary,
 *     fillColor = RikkaTheme.colors.destructive,
 *     height = 12.dp,
 * )
 *
 * // Tween animation
 * Progress(
 *     progress = 0.8f,
 *     animation = ProgressAnimation.Tween,
 * )
 *
 * // No animation (instant)
 * Progress(
 *     progress = 1f,
 *     animation = ProgressAnimation.None,
 * )
 * ```
 *
 * @param progress Current progress value, clamped to 0f..1f.
 * @param modifier Modifier for layout and decoration.
 * @param trackColor Background track color. Defaults to theme muted color.
 * @param fillColor Fill bar color. Defaults to theme primary color.
 * @param height Height of the progress bar. Defaults to 8.dp.
 * @param animation Animation style for value transitions. Defaults to [ProgressAnimation.Spring].
 * @param label Accessibility label describing what this progress bar represents.
 */
@Composable
fun Progress(
    progress: Float,
    modifier: Modifier = Modifier,
    trackColor: Color = Color.Unspecified,
    fillColor: Color = Color.Unspecified,
    height: Dp = 8.dp,
    animation: ProgressAnimation = ProgressAnimation.Spring,
    label: String = "",
) {
    val clampedProgress = progress.coerceIn(0f, 1f)
    val motion = RikkaTheme.motion
    val colors = RikkaTheme.colors
    val shapes = RikkaTheme.shapes

    val resolvedTrackColor =
        if (trackColor != Color.Unspecified) trackColor else colors.muted
    val resolvedFillColor =
        if (fillColor != Color.Unspecified) fillColor else colors.primary

    // ─── Resolve animation spec from enum + theme tokens ──
    val animationSpec: AnimationSpec<Float> = resolveAnimationSpec(animation, motion)

    // ─── Animated fill fraction ───────────────────────────
    val animatedFraction by animateFloatAsState(
        targetValue = clampedProgress,
        animationSpec = animationSpec,
    )

    val percentText = "${(clampedProgress * 100).toInt()}%"

    // ─── Track ────────────────────────────────────────────
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(height)
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
        // ─── Fill ─────────────────────────────────────────
        if (animatedFraction > 0f) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth(fraction = animatedFraction)
                        .height(height)
                        .clip(shapes.full)
                        .background(resolvedFillColor, shapes.full),
            )
        }
    }
}

// ─── Private helpers ────────────────────────────────────────

/**
 * Maps a [ProgressAnimation] enum value to a concrete
 * [AnimationSpec] using the current theme motion tokens.
 */
@Composable
private fun resolveAnimationSpec(
    animation: ProgressAnimation,
    motion: RikkaMotion,
): AnimationSpec<Float> =
    when (animation) {
        ProgressAnimation.Spring -> motion.springDefault
        ProgressAnimation.Tween -> motion.tweenDefault
        ProgressAnimation.None -> snap()
    }
