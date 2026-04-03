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
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.foundation.RikkaMotion
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Animation Enum ─────────────────────────────────────────

enum class ProgressAnimation {
    /** Spring physics, handles interruptions. Default. */
    Spring,

    /** Smooth eased tween using theme duration. */
    Tween,

    /** Instant jump, no interpolation. */
    None,
}

// ─── Component ──────────────────────────────────────────────

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
                    progressBarRangeInfo =
                        ProgressBarRangeInfo(
                            current = clampedProgress,
                            range = 0f..1f,
                        )
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
