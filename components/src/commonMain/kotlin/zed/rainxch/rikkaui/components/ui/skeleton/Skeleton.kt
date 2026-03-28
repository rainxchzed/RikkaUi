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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.clearAndSetSemantics
import zed.rainxch.rikkaui.components.theme.RikkaTheme

// ─── Component ──────────────────────────────────────────────

/**
 * Skeleton component for the RikkaUi design system.
 *
 * An animated loading placeholder that pulses in opacity. The caller
 * controls size and shape through the [modifier] parameter.
 * Matches shadcn/ui's Skeleton component.
 *
 * Usage:
 * ```
 * // Avatar placeholder
 * Skeleton(modifier = Modifier.size(40.dp).clip(CircleShape))
 *
 * // Text line placeholder
 * Skeleton(modifier = Modifier.fillMaxWidth().height(16.dp))
 *
 * // Card placeholder
 * Skeleton(modifier = Modifier.fillMaxWidth().height(120.dp))
 * ```
 *
 * @param modifier Modifier for layout — set size via [Modifier.size], [Modifier.height], etc.
 */
@Composable
fun Skeleton(modifier: Modifier = Modifier) {
    val motion = RikkaTheme.motion
    val infiniteTransition = rememberInfiniteTransition()

    val alpha by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 0.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = motion.durationSlow * 4),
            repeatMode = RepeatMode.Reverse,
        ),
    )

    // Skeleton is purely decorative — clear all semantics so
    // screen readers skip it entirely.
    Box(
        modifier = modifier
            .clip(RikkaTheme.shapes.md)
            .background(RikkaTheme.colors.muted)
            .graphicsLayer { this.alpha = alpha }
            .clearAndSetSemantics { },
    )
}
