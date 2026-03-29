package zed.rainxch.rikkaui.components.ui.badge

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Variant ────────────────────────────────────────────────

/**
 * Badge visual variants, matching shadcn/ui's badge variants.
 *
 * - [Default] — Solid primary background. Standard status indicator.
 * - [Secondary] — Muted background. Less prominent.
 * - [Outline] — Bordered, transparent background. Subtle tag.
 * - [Destructive] — Red tint. Error or critical status.
 */
enum class BadgeVariant {
    Default,
    Secondary,
    Outline,
    Destructive,
}

// ─── Animation ──────────────────────────────────────────────

/**
 * Controls the entrance animation when a badge first appears.
 *
 * Uses [RikkaTheme.motion] tokens for consistent motion feel across
 * the entire design system.
 *
 * - [Pulse] — Subtle pulse/pop animation when appearing. Eye-catching but not distracting.
 * - [Scale] — Scale up from 0 to full size. Good for count badges and notifications.
 * - [None] — Instant appear with no animation.
 *
 * Usage:
 * ```
 * Badge("New", animation = BadgeAnimation.Pulse)
 * Badge("3", animation = BadgeAnimation.Scale)
 * ```
 */
enum class BadgeAnimation {
    Pulse,
    Scale,
    None,
}

// ─── Size ───────────────────────────────────────────────────

/**
 * Badge size presets.
 *
 * - [Default] — Standard badge size. Works for most labels and tags.
 * - [Sm] — Compact badge. Good for inline status indicators and dense layouts.
 * - [Lg] — Larger badge. Good for prominent labels and notification counts.
 *
 * Usage:
 * ```
 * Badge("Active", size = BadgeSize.Sm)
 * Badge("Featured", size = BadgeSize.Lg)
 * ```
 */
enum class BadgeSize {
    Sm,
    Default,
    Lg,
}

// ─── Component ──────────────────────────────────────────────

/**
 * Badge component for the RikkaUi design system.
 *
 * A small status indicator or label. Replaces Material3 chips/badges
 * with Rikka theme tokens.
 *
 * Usage:
 * ```
 * Badge("New")
 * Badge("Draft", variant = BadgeVariant.Secondary)
 * Badge("Error", variant = BadgeVariant.Destructive)
 * Badge("v1.0", variant = BadgeVariant.Outline)
 *
 * // With animation and size
 * Badge("3", animation = BadgeAnimation.Scale, size = BadgeSize.Sm)
 * ```
 *
 * @param text The label text.
 * @param modifier Modifier for layout and decoration.
 * @param variant Visual variant — controls colors and border.
 * @param animation Entrance animation style. Defaults to [BadgeAnimation.None].
 * @param size Badge size preset. Defaults to [BadgeSize.Default].
 */
@Composable
fun Badge(
    text: String,
    modifier: Modifier = Modifier,
    variant: BadgeVariant = BadgeVariant.Default,
    animation: BadgeAnimation = BadgeAnimation.None,
    size: BadgeSize = BadgeSize.Default,
) {
    val resolved = resolveColors(variant)
    val sizeValues = resolveSizeValues(size)
    val shape = RikkaTheme.shapes.full

    val backgroundModifier =
        if (resolved.background != Color.Transparent) {
            Modifier.background(resolved.background, shape)
        } else {
            Modifier
        }

    val borderModifier =
        if (resolved.border != Color.Transparent) {
            Modifier.border(1.dp, resolved.border, shape)
        } else {
            Modifier
        }

    val animationModifier = resolveAnimationModifier(animation)

    Box(
        modifier =
            modifier
                .then(animationModifier)
                .then(borderModifier)
                .then(backgroundModifier)
                .clip(shape)
                .padding(
                    horizontal = sizeValues.horizontalPadding,
                    vertical = sizeValues.verticalPadding,
                ),
    ) {
        Text(
            text = text,
            color = resolved.foreground,
            style =
                sizeValues.textStyle.merge(
                    TextStyle(
                        fontSize = sizeValues.textStyle.fontSize,
                        lineHeight = sizeValues.textStyle.lineHeight,
                    ),
                ),
        )
    }
}

/**
 * Badge with custom content (icon + text, etc).
 *
 * ```
 * Badge(variant = BadgeVariant.Outline) {
 *     Icon(...)
 *     Text("Tagged")
 * }
 *
 * Badge(animation = BadgeAnimation.Pulse, size = BadgeSize.Lg) {
 *     Icon(RikkaIcons.Star, size = 12.dp)
 *     Text("Featured")
 * }
 * ```
 *
 * @param modifier Modifier for layout and decoration.
 * @param variant Visual variant — controls colors and border.
 * @param animation Entrance animation style. Defaults to [BadgeAnimation.None].
 * @param size Badge size preset. Defaults to [BadgeSize.Default].
 * @param content Custom badge content.
 */
@Composable
fun Badge(
    modifier: Modifier = Modifier,
    variant: BadgeVariant = BadgeVariant.Default,
    animation: BadgeAnimation = BadgeAnimation.None,
    size: BadgeSize = BadgeSize.Default,
    content: @Composable () -> Unit,
) {
    val resolved = resolveColors(variant)
    val sizeValues = resolveSizeValues(size)
    val shape = RikkaTheme.shapes.full

    val backgroundModifier =
        if (resolved.background != Color.Transparent) {
            Modifier.background(resolved.background, shape)
        } else {
            Modifier
        }

    val borderModifier =
        if (resolved.border != Color.Transparent) {
            Modifier.border(1.dp, resolved.border, shape)
        } else {
            Modifier
        }

    val animationModifier = resolveAnimationModifier(animation)

    Box(
        modifier =
            modifier
                .then(animationModifier)
                .then(borderModifier)
                .then(backgroundModifier)
                .clip(shape)
                .padding(
                    horizontal = sizeValues.horizontalPadding,
                    vertical = sizeValues.verticalPadding,
                ),
    ) {
        content()
    }
}

// ─── Internal: Animation Resolution ─────────────────────────

/**
 * Resolves the entrance animation modifier based on [BadgeAnimation].
 * Uses [RikkaTheme.motion] tokens for consistent feel.
 */
@Composable
private fun resolveAnimationModifier(animation: BadgeAnimation): Modifier {
    if (animation == BadgeAnimation.None) return Modifier

    val motion = RikkaTheme.motion

    // Trigger animation on first composition
    var appeared by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { appeared = true }

    return when (animation) {
        BadgeAnimation.Pulse -> {
            val scale by animateFloatAsState(
                targetValue = if (appeared) 1f else 0.6f,
                animationSpec = motion.springBouncy,
            )
            val alpha by animateFloatAsState(
                targetValue = if (appeared) 1f else 0f,
                animationSpec = tween(motion.durationDefault),
            )
            Modifier.graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
        }

        BadgeAnimation.Scale -> {
            val scale by animateFloatAsState(
                targetValue = if (appeared) 1f else 0f,
                animationSpec = motion.springDefault,
            )
            Modifier.graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
        }

        BadgeAnimation.None -> {
            Modifier
        }
    }
}

// ─── Internal: Color Resolution ─────────────────────────────

private data class BadgeColors(
    val background: Color,
    val foreground: Color,
    val border: Color,
)

@Composable
private fun resolveColors(variant: BadgeVariant): BadgeColors {
    val colors = RikkaTheme.colors

    return when (variant) {
        BadgeVariant.Default -> {
            BadgeColors(
                background = colors.primary,
                foreground = colors.primaryForeground,
                border = Color.Transparent,
            )
        }

        BadgeVariant.Secondary -> {
            BadgeColors(
                background = colors.secondary,
                foreground = colors.secondaryForeground,
                border = Color.Transparent,
            )
        }

        BadgeVariant.Outline -> {
            BadgeColors(
                background = Color.Transparent,
                foreground = colors.foreground,
                border = colors.border,
            )
        }

        BadgeVariant.Destructive -> {
            BadgeColors(
                background = colors.destructive,
                foreground = colors.destructiveForeground,
                border = Color.Transparent,
            )
        }
    }
}

// ─── Internal: Size Resolution ──────────────────────────────

private data class BadgeSizeValues(
    val horizontalPadding: androidx.compose.ui.unit.Dp,
    val verticalPadding: androidx.compose.ui.unit.Dp,
    val textStyle: TextStyle,
)

@Composable
private fun resolveSizeValues(size: BadgeSize): BadgeSizeValues {
    val typography = RikkaTheme.typography
    val spacing = RikkaTheme.spacing

    return when (size) {
        BadgeSize.Sm -> {
            BadgeSizeValues(
                horizontalPadding = spacing.xs,
                verticalPadding = 1.dp,
                textStyle = typography.muted,
            )
        }

        BadgeSize.Default -> {
            BadgeSizeValues(
                horizontalPadding = spacing.sm,
                verticalPadding = 2.dp,
                textStyle = typography.small,
            )
        }

        BadgeSize.Lg -> {
            BadgeSizeValues(
                horizontalPadding = spacing.md,
                verticalPadding = spacing.xs,
                textStyle = typography.small,
            )
        }
    }
}
