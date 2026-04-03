package zed.rainxch.rikkaui.components.ui.alert

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.LocalContentColor
import zed.rainxch.rikkaui.foundation.LocalTextStyle
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Variant ────────────────────────────────────────────────

/** Alert visual style. */
enum class AlertVariant {
    /** Bordered card background with foreground text. */
    Default,

    /** Destructive border tint with destructive text color. */
    Destructive,
}

// ─── Animation ──────────────────────────────────────────────

/** Entrance animation when an alert first appears. */
enum class AlertAnimation {
    /** Slide in from the left with a fade. */
    SlideIn,

    /** Simple fade in. */
    Fade,

    /** Instant appear, no animation. */
    None,
}

// ─── Component ──────────────────────────────────────────────

@Composable
fun Alert(
    modifier: Modifier = Modifier,
    variant: AlertVariant = AlertVariant.Default,
    animation: AlertAnimation = AlertAnimation.None,
    icon: (@Composable () -> Unit)? = null,
    label: String = "",
    content: @Composable ColumnScope.() -> Unit,
) {
    val resolved = resolveColors(variant)
    val shape = RikkaTheme.shapes.md

    val resolvedLiveRegion =
        when (variant) {
            AlertVariant.Default -> LiveRegionMode.Polite
            AlertVariant.Destructive -> LiveRegionMode.Assertive
        }

    val semanticsModifier =
        if (label.isNotEmpty()) {
            Modifier.semantics(mergeDescendants = true) {
                contentDescription = label
                liveRegion = resolvedLiveRegion
            }
        } else {
            Modifier.semantics(mergeDescendants = true) {
                liveRegion = resolvedLiveRegion
            }
        }

    val animationModifier = resolveAnimationModifier(animation)

    val baseModifier =
        modifier
            .then(semanticsModifier)
            .then(animationModifier)
            .border(1.dp, resolved.border, shape)
            .background(resolved.background, shape)
            .clip(shape)
            .padding(RikkaTheme.spacing.lg)

    CompositionLocalProvider(
        LocalContentColor provides resolved.foreground,
        LocalTextStyle provides RikkaTheme.typography.small,
    ) {
        if (icon != null) {
            Row(
                modifier = baseModifier,
                horizontalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.md,
                    ),
                verticalAlignment = Alignment.Top,
            ) {
                Box(
                    modifier =
                        Modifier
                            .padding(top = 2.dp)
                            .size(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    icon()
                }
                Column(content = content)
            }
        } else {
            Column(
                modifier = baseModifier,
                content = content,
            )
        }
    }
}

// ─── Structured Sections ────────────────────────────────────

@Composable
fun AlertTitle(
    text: String,
    modifier: Modifier = Modifier,
    variant: AlertVariant = AlertVariant.Default,
) {
    val color =
        when (variant) {
            AlertVariant.Default -> RikkaTheme.colors.onBackground
            AlertVariant.Destructive -> RikkaTheme.colors.destructive
        }
    Text(
        text = text,
        modifier = modifier,
        variant = TextVariant.H4,
        color = color,
    )
}

@Composable
fun AlertDescription(
    text: String,
    modifier: Modifier = Modifier,
    variant: AlertVariant = AlertVariant.Default,
) {
    val color = resolveDescriptionColor(variant)

    Text(
        text = text,
        modifier = modifier,
        variant = TextVariant.P,
        color = color,
    )
}

// ─── Internal: Animation Resolution ─────────────────────────

@Composable
private fun resolveAnimationModifier(animation: AlertAnimation): Modifier {
    if (animation == AlertAnimation.None) return Modifier

    val motion = RikkaTheme.motion

    // Trigger animation on first composition
    var appeared by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { appeared = true }

    return when (animation) {
        AlertAnimation.SlideIn -> {
            val offsetX by animateFloatAsState(
                targetValue = if (appeared) 0f else -24f,
                animationSpec = motion.springDefault,
            )
            val alpha by animateFloatAsState(
                targetValue = if (appeared) 1f else 0f,
                animationSpec = tween(motion.durationEnter),
            )
            Modifier.graphicsLayer {
                translationX = offsetX
                this.alpha = alpha
            }
        }

        AlertAnimation.Fade -> {
            val alpha by animateFloatAsState(
                targetValue = if (appeared) 1f else 0f,
                animationSpec = tween(motion.durationEnter),
            )
            Modifier.graphicsLayer {
                this.alpha = alpha
            }
        }

        AlertAnimation.None -> {
            Modifier
        }
    }
}

// ─── Internal: Color Resolution ─────────────────────────────

private data class AlertColors(
    val background: Color,
    val border: Color,
    val foreground: Color,
)

@Composable
private fun resolveColors(variant: AlertVariant): AlertColors {
    val colors = RikkaTheme.colors

    return when (variant) {
        AlertVariant.Default -> {
            AlertColors(
                background = colors.surface,
                border = colors.border,
                foreground = colors.onSurface,
            )
        }

        AlertVariant.Destructive -> {
            AlertColors(
                background = colors.destructive.copy(alpha = 0.1f),
                border = colors.destructive.copy(alpha = 0.3f),
                foreground = colors.destructive,
            )
        }
    }
}

@Composable
private fun resolveDescriptionColor(variant: AlertVariant): Color {
    val colors = RikkaTheme.colors

    return when (variant) {
        AlertVariant.Default -> colors.onMuted
        AlertVariant.Destructive -> colors.destructive
    }
}
