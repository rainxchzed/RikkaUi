package zed.rainxch.rikkaui.components.ui.skeleton

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
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
import zed.rainxch.rikkaui.components.theme.RikkaTheme

// ─── Animation ──────────────────────────────────────────────

/**
 * Skeleton animation variants.
 *
 * Controls the visual loading feedback style. All animated variants
 * respect [RikkaTheme.motion] duration tokens.
 *
 * - [Pulse] — Pulsing opacity (default). Matches shadcn/ui behavior.
 * - [Shimmer] — Horizontal gradient sweep. Eye-catching loading effect
 *   common in content-heavy layouts.
 * - [None] — Static muted block with no animation. Useful for snapshot
 *   tests or reduced-motion contexts.
 *
 * ```
 * Skeleton(animation = SkeletonAnimation.Shimmer)
 * Skeleton(animation = SkeletonAnimation.None)
 * ```
 */
enum class SkeletonAnimation {
    Pulse,
    Shimmer,
    None,
}

// ─── Component ──────────────────────────────────────────────

/**
 * Skeleton component for the RikkaUi design system.
 *
 * An animated loading placeholder. The caller controls size through
 * the [modifier] parameter. Matches shadcn/ui's Skeleton component
 * with additional animation and shape options.
 *
 * Usage:
 * ```
 * // Avatar placeholder
 * Skeleton(modifier = Modifier.size(40.dp).clip(CircleShape))
 *
 * // Text line placeholder
 * Skeleton(modifier = Modifier.fillMaxWidth().height(16.dp))
 *
 * // Shimmer card placeholder
 * Skeleton(
 *     modifier = Modifier.fillMaxWidth().height(120.dp),
 *     animation = SkeletonAnimation.Shimmer,
 * )
 *
 * // Custom rounded shape
 * Skeleton(
 *     modifier = Modifier.size(48.dp),
 *     shape = RikkaTheme.shapes.full,
 * )
 * ```
 *
 * @param modifier Modifier for layout — set size via
 *   [Modifier.size], [Modifier.height], etc.
 * @param animation Animation style. Defaults to [SkeletonAnimation.Pulse].
 * @param shape Clip shape applied to the skeleton. Defaults to
 *   [RikkaTheme.shapes.md]. Pass [RikkaTheme.shapes.full] for circular
 *   placeholders or any custom [RoundedCornerShape].
 */
@Composable
fun Skeleton(
    modifier: Modifier = Modifier,
    animation: SkeletonAnimation = SkeletonAnimation.Pulse,
    shape: Shape = RikkaTheme.shapes.md,
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
                        .clearAndSetSemantics { },
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
                        }.clearAndSetSemantics { },
            )
        }

        SkeletonAnimation.None -> {
            Box(
                modifier =
                    modifier
                        .clip(shape)
                        .background(mutedColor)
                        .clearAndSetSemantics { },
            )
        }
    }
}
