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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Variant ────────────────────────────────────────────────

/**
 * Alert visual variants, matching shadcn/ui's alert variants.
 *
 * - [Default] — Bordered card background with foreground text. Informational alerts.
 * - [Destructive] — Destructive border tint with destructive foreground. Errors or critical warnings.
 */
enum class AlertVariant {
    Default,
    Destructive,
}

// ─── Animation ──────────────────────────────────────────────

/**
 * Controls the entrance animation when an alert first appears.
 *
 * Uses [RikkaTheme.motion] tokens for consistent motion feel across
 * the entire design system.
 *
 * - [SlideIn] — Slide in from the left with a fade. Good for notifications and toasts.
 * - [Fade] — Simple fade in. Subtle, non-distracting entrance.
 * - [None] — Instant appear with no animation.
 *
 * Usage:
 * ```
 * Alert(animation = AlertAnimation.SlideIn) {
 *     AlertTitle("Heads up!")
 *     AlertDescription("Something happened.")
 * }
 * ```
 */
enum class AlertAnimation {
    SlideIn,
    Fade,
    None,
}

// ─── Component ──────────────────────────────────────────────

/**
 * Alert component for the RikkaUi design system.
 *
 * A status container that displays important messages to the user.
 * Replaces Material3's snackbar/alert patterns with Rikka theme tokens.
 *
 * Usage:
 * ```
 * Alert {
 *     AlertTitle("Heads up!")
 *     AlertDescription("You can add components to your app using the CLI.")
 * }
 *
 * Alert(variant = AlertVariant.Destructive) {
 *     AlertTitle("Error")
 *     AlertDescription("Your session has expired. Please log in again.")
 * }
 *
 * // With icon and animation
 * Alert(
 *     animation = AlertAnimation.SlideIn,
 *     icon = { Icon(RikkaIcons.Mail, tint = RikkaTheme.colors.foreground) },
 * ) {
 *     AlertTitle("New message")
 *     AlertDescription("You have 3 unread messages.")
 * }
 * ```
 *
 * @param modifier Modifier for layout and decoration.
 * @param variant Visual variant — controls border color and text styling.
 * @param animation Entrance animation style. Defaults to [AlertAnimation.None].
 * @param icon Optional leading icon composable. Rendered to the left of the content.
 *   The icon should be sized appropriately (16-20dp recommended).
 * @param label Accessibility label for screen readers. Describes the alert's purpose.
 * @param content Alert content — use [AlertTitle] and [AlertDescription] for structured layout.
 */
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

    val semanticsModifier =
        if (label.isNotEmpty()) {
            Modifier.semantics(mergeDescendants = true) {
                contentDescription = label
            }
        } else {
            Modifier.semantics(mergeDescendants = true) {}
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

// ─── Structured Sections ────────────────────────────────────

/**
 * Title section for an [Alert]. Renders as an H4 heading.
 *
 * ```
 * Alert {
 *     AlertTitle("Heads up!")
 *     AlertDescription("Description text here.")
 * }
 * ```
 *
 * @param text The title text.
 * @param modifier Modifier for layout and decoration.
 */
@Composable
fun AlertTitle(
    text: String,
    modifier: Modifier = Modifier,
    variant: AlertVariant = AlertVariant.Default,
) {
    val color =
        when (variant) {
            AlertVariant.Default -> RikkaTheme.colors.foreground
            AlertVariant.Destructive -> RikkaTheme.colors.destructive
        }
    Text(
        text = text,
        modifier = modifier,
        variant = TextVariant.H4,
        color = color,
    )
}

/**
 * Description section for an [Alert]. Renders as body text with color
 * matching the alert variant.
 *
 * - In [AlertVariant.Default], text is muted foreground.
 * - In [AlertVariant.Destructive], text is destructive foreground.
 *
 * ```
 * Alert(variant = AlertVariant.Destructive) {
 *     AlertTitle("Error")
 *     AlertDescription("Something went wrong.")
 * }
 * ```
 *
 * @param text The description text.
 * @param modifier Modifier for layout and decoration.
 * @param variant The parent alert variant — controls text color.
 */
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

/**
 * Resolves the entrance animation modifier based on [AlertAnimation].
 * Uses [RikkaTheme.motion] tokens for consistent feel.
 */
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
)

@Composable
private fun resolveColors(variant: AlertVariant): AlertColors {
    val colors = RikkaTheme.colors

    return when (variant) {
        AlertVariant.Default -> {
            AlertColors(
                background = colors.card,
                border = colors.border,
            )
        }

        AlertVariant.Destructive -> {
            AlertColors(
                background = colors.destructive.copy(alpha = 0.1f),
                border = colors.destructive.copy(alpha = 0.3f),
            )
        }
    }
}

@Composable
private fun resolveDescriptionColor(variant: AlertVariant): Color {
    val colors = RikkaTheme.colors

    return when (variant) {
        AlertVariant.Default -> colors.mutedForeground
        AlertVariant.Destructive -> colors.destructive
    }
}
