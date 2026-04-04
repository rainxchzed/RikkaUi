package zed.rainxch.rikkaui.components.ui.avatar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Size ───────────────────────────────────────────────────

enum class AvatarSize {
    /** 32dp — compact contexts. */
    Sm,

    /** 40dp — standard usage. */
    Default,

    /** 48dp — prominent placement. */
    Lg,
}

// ─── Animation ──────────────────────────────────────────────

enum class AvatarAnimation {
    /** Fade in from transparent. */
    FadeIn,

    /** Scale up from 0 to full size. */
    Scale,

    /** Instant appear, no animation. */
    None,
}

// ─── Status Indicator ───────────────────────────────────────

enum class AvatarStatus {
    /** Green dot. */
    Online,

    /** Gray dot. */
    Offline,

    /** Red dot. */
    Busy,
}

// ─── Component ──────────────────────────────────────────────

/**
 * Circular avatar displaying fallback initials with an optional status indicator dot.
 *
 * The avatar supports entrance animations and a colored status dot
 * (online/offline/busy) drawn at the bottom-right corner.
 *
 * ```
 * Avatar(fallback = "JD")
 *
 * Avatar(
 *     fallback = "AB",
 *     size = AvatarSize.Lg,
 *     status = AvatarStatus.Online,
 *     animation = AvatarAnimation.Scale,
 * )
 * ```
 *
 * @param fallback The initials or short text displayed inside the avatar circle.
 * @param modifier [Modifier] applied to the avatar container.
 * @param size [AvatarSize] controlling the diameter (Sm=32dp, Default=40dp, Lg=48dp).
 * @param animation [AvatarAnimation] entrance effect (FadeIn, Scale, or None).
 * @param status Optional [AvatarStatus] that renders a colored dot indicator.
 * @param label Accessibility content description; defaults to [fallback] text.
 */
@Composable
fun Avatar(
    fallback: String,
    modifier: Modifier = Modifier,
    size: AvatarSize = AvatarSize.Default,
    animation: AvatarAnimation = AvatarAnimation.FadeIn,
    status: AvatarStatus? = null,
    label: String? = null,
) {
    val resolved = resolveSizeValues(size)
    val shape = RikkaTheme.shapes.full
    val motion = RikkaTheme.motion

    // Entrance animation state
    var appeared by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { appeared = true }

    val animAlpha by animateFloatAsState(
        targetValue =
            when {
                animation == AvatarAnimation.None -> 1f
                appeared -> 1f
                else -> 0f
            },
        animationSpec = motion.tweenEnter,
    )

    val animScale by animateFloatAsState(
        targetValue =
            when {
                animation == AvatarAnimation.None -> {
                    1f
                }

                appeared -> {
                    1f
                }

                else -> {
                    when (animation) {
                        AvatarAnimation.Scale -> 0f
                        else -> 1f
                    }
                }
            },
        animationSpec = motion.springDefault,
    )

    // Resolve status dot color
    val statusColor = resolveStatusColor(status)

    // Build contentDescription: use label if provided, otherwise fallback.
    // Append status name when a status is set.
    val baseDescription = label ?: fallback
    val resolvedDescription =
        if (status != null) {
            "$baseDescription, ${status.name}"
        } else {
            baseDescription
        }

    Box(
        modifier =
            modifier
                .semantics { contentDescription = resolvedDescription }
                .size(resolved.diameter)
                .graphicsLayer {
                    alpha = animAlpha
                    scaleX = animScale
                    scaleY = animScale
                }.background(RikkaTheme.colors.muted, shape)
                .clip(shape)
                .then(
                    if (statusColor != null) {
                        Modifier.drawBehind {
                            val dotRadius = this.size.width * 0.15f
                            val dotOffset = dotRadius * 0.3f
                            drawCircle(
                                color = statusColor,
                                radius = dotRadius,
                                center =
                                    androidx.compose.ui.geometry.Offset(
                                        x = this.size.width - dotOffset,
                                        y = this.size.height - dotOffset,
                                    ),
                            )
                        }
                    } else {
                        Modifier
                    },
                ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = fallback,
            variant = resolved.textVariant,
            color = RikkaTheme.colors.onMuted,
        )
    }
}

// ─── Internal: Size Resolution ──────────────────────────────

private data class AvatarSizeValues(
    val diameter: Dp,
    val textVariant: TextVariant,
)

private fun resolveSizeValues(size: AvatarSize): AvatarSizeValues =
    when (size) {
        AvatarSize.Sm -> {
            AvatarSizeValues(
                diameter = 32.dp,
                textVariant = TextVariant.Small,
            )
        }

        AvatarSize.Default -> {
            AvatarSizeValues(
                diameter = 40.dp,
                textVariant = TextVariant.P,
            )
        }

        AvatarSize.Lg -> {
            AvatarSizeValues(
                diameter = 48.dp,
                textVariant = TextVariant.Large,
            )
        }
    }

// ─── Internal: Status Color Resolution ─────────────────────

@Composable
private fun resolveStatusColor(status: AvatarStatus?): Color? =
    when (status) {
        AvatarStatus.Online -> RikkaTheme.colors.success
        AvatarStatus.Offline -> RikkaTheme.colors.onMuted
        AvatarStatus.Busy -> RikkaTheme.colors.destructive
        null -> null
    }
