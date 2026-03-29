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

/**
 * Avatar size variants.
 *
 * - [Sm] — 32dp. Compact contexts like lists or inline mentions.
 * - [Default] — 40dp. Standard usage in headers and profiles.
 * - [Lg] — 48dp. Prominent placement like profile pages.
 */
enum class AvatarSize {
    Sm,
    Default,
    Lg,
}

// ─── Animation ──────────────────────────────────────────────

/**
 * Avatar animation variants.
 *
 * Controls the entrance animation when the avatar first appears.
 * All animated variants respect [RikkaTheme.motion] spring tokens.
 *
 * - [FadeIn] — Fade in from transparent (default). Subtle entrance.
 * - [Scale] — Scale up from 0 to full size. Playful pop-in effect.
 * - [None] — Instant appear with no animation. Useful for snapshot
 *   tests or reduced-motion contexts.
 *
 * ```
 * Avatar(fallback = "JD", animation = AvatarAnimation.Scale)
 * Avatar(fallback = "A", animation = AvatarAnimation.None)
 * ```
 */
enum class AvatarAnimation {
    FadeIn,
    Scale,
    None,
}

// ─── Status Indicator ───────────────────────────────────────

/**
 * Avatar online-status indicator dot.
 *
 * When set, a small colored dot is drawn at the bottom-end corner
 * of the avatar to communicate presence or availability.
 *
 * - [Online] — Green dot.
 * - [Offline] — Gray dot.
 * - [Busy] — Destructive (red) dot.
 *
 * ```
 * Avatar(fallback = "JD", status = AvatarStatus.Online)
 * ```
 */
enum class AvatarStatus {
    Online,
    Offline,
    Busy,
}

// ─── Component ──────────────────────────────────────────────

/**
 * Avatar component for the RikkaUi design system.
 *
 * A circular container displaying user initials as a fallback,
 * with optional entrance animation and status indicator.
 *
 * Usage:
 * ```
 * Avatar(fallback = "JD")
 * Avatar(fallback = "A", size = AvatarSize.Sm)
 * Avatar(fallback = "RX", size = AvatarSize.Lg)
 * Avatar(fallback = "JD", animation = AvatarAnimation.Scale)
 * Avatar(fallback = "JD", status = AvatarStatus.Online)
 * ```
 *
 * @param fallback 1-2 character initials shown as the avatar content.
 * @param modifier Modifier for layout and decoration.
 * @param size Size variant — controls the avatar diameter and text scale.
 * @param animation Entrance animation style. Defaults to [AvatarAnimation.FadeIn].
 * @param status Optional presence indicator dot. When null, no dot is shown.
 */
@Composable
fun Avatar(
    fallback: String,
    modifier: Modifier = Modifier,
    size: AvatarSize = AvatarSize.Default,
    animation: AvatarAnimation = AvatarAnimation.FadeIn,
    status: AvatarStatus? = null,
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

    Box(
        modifier =
            modifier
                .semantics { contentDescription = fallback }
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
            color = RikkaTheme.colors.mutedForeground,
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

/**
 * Resolves the dot color for the given [AvatarStatus], or null
 * when no status is set.
 */
@Composable
private fun resolveStatusColor(status: AvatarStatus?): Color? =
    when (status) {
        AvatarStatus.Online -> Color(0xFF22C55E)
        AvatarStatus.Offline -> RikkaTheme.colors.mutedForeground
        AvatarStatus.Busy -> RikkaTheme.colors.destructive
        null -> null
    }
