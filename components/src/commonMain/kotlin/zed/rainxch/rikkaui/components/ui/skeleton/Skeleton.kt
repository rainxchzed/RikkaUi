package zed.rainxch.rikkaui.components.ui.skeleton

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Animation ──────────────────────────────────────────────

enum class SkeletonAnimation {
    /** Pulsing opacity (default). */
    Pulse,

    /** Horizontal gradient sweep. */
    Shimmer,

    /** Static muted block, no animation. */
    None,
}

// ─── Component ──────────────────────────────────────────────

@Composable
fun Skeleton(
    modifier: Modifier = Modifier,
    animation: SkeletonAnimation = SkeletonAnimation.Pulse,
    shape: Shape = RikkaTheme.shapes.md,
    label: String = "Loading",
) {
    val motion = RikkaTheme.motion
    val mutedColor = RikkaTheme.colors.muted
    val bgColor = RikkaTheme.colors.background

    when (animation) {
        SkeletonAnimation.Pulse -> {
            val infiniteTransition = rememberInfiniteTransition()
            val alpha by infiniteTransition.animateFloat(
                initialValue = 1.0f,
                targetValue = 0.4f,
                animationSpec =
                    infiniteRepeatable(
                        animation =
                            tween(
                                durationMillis = motion.durationSlow * 4,
                            ),
                        repeatMode = RepeatMode.Reverse,
                    ),
            )

            Box(
                modifier =
                    modifier
                        .clip(shape)
                        .background(mutedColor)
                        .graphicsLayer { this.alpha = alpha }
                        .clearAndSetSemantics { contentDescription = label },
            )
        }

        SkeletonAnimation.Shimmer -> {
            val infiniteTransition = rememberInfiniteTransition()
            val shimmerProgress by infiniteTransition.animateFloat(
                initialValue = -1f,
                targetValue = 2f,
                animationSpec =
                    infiniteRepeatable(
                        animation =
                            tween(
                                durationMillis = motion.durationSlow * 6,
                            ),
                        repeatMode = RepeatMode.Restart,
                    ),
            )

            Box(
                modifier =
                    modifier
                        .clip(shape)
                        .background(mutedColor)
                        .drawWithContent {
                            drawContent()
                            val width = size.width
                            val shimmerX = shimmerProgress * width
                            val brush =
                                Brush.linearGradient(
                                    colors =
                                        listOf(
                                            mutedColor,
                                            bgColor.copy(alpha = 0.6f),
                                            mutedColor,
                                        ),
                                    start = Offset(shimmerX - width * 0.5f, 0f),
                                    end = Offset(shimmerX + width * 0.5f, 0f),
                                )
                            drawRect(brush = brush)
                        }.clearAndSetSemantics { contentDescription = label },
            )
        }

        SkeletonAnimation.None -> {
            Box(
                modifier =
                    modifier
                        .clip(shape)
                        .background(mutedColor)
                        .clearAndSetSemantics { contentDescription = label },
            )
        }
    }
}
