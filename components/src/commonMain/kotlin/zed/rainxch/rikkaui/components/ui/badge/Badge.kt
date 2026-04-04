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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Variant ────────────────────────────────────────────────

/** Badge visual variants. */
public enum class BadgeVariant {
    /** Solid primary background. */
    Default,

    /** Muted background, less prominent. */
    Secondary,

    /** Bordered, transparent background. */
    Outline,

    /** Red tint for error or critical status. */
    Destructive,
}

// ─── Animation ──────────────────────────────────────────────

/** Badge entrance animation style. */
public enum class BadgeAnimation {
    /** Subtle pulse/pop on appear. */
    Pulse,

    /** Scale up from 0 to full size. */
    Scale,

    /** No animation. */
    None,
}

// ─── Size ───────────────────────────────────────────────────

/** Badge size presets. */
public enum class BadgeSize {
    /** Compact size for dense layouts. */
    Sm,

    /** Standard size. */
    Default,

    /** Larger size for prominent labels. */
    Lg,
}

// ─── Component ──────────────────────────────────────────────

/**
 * Displays a small status label with themed colors and optional entrance animation.
 *
 * Convenience overload that renders a text string inside the badge.
 *
 * ```
 * Badge(text = "New", variant = BadgeVariant.Default)
 *
 * Badge(
 *     text = "Error",
 *     variant = BadgeVariant.Destructive,
 *     animation = BadgeAnimation.Pulse,
 * )
 * ```
 *
 * @param text The label string displayed inside the badge.
 * @param modifier [Modifier] applied to the badge container.
 * @param variant [BadgeVariant] controlling background and foreground colors.
 * @param animation [BadgeAnimation] entrance effect when the badge first appears.
 * @param size [BadgeSize] controlling padding and text style.
 * @param label Accessibility content description; defaults to empty.
 */
@Composable
public fun Badge(
    text: String,
    modifier: Modifier = Modifier,
    variant: BadgeVariant = BadgeVariant.Default,
    animation: BadgeAnimation = BadgeAnimation.None,
    size: BadgeSize = BadgeSize.Default,
    label: String = "",
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

    val semanticsModifier =
        if (label.isNotEmpty()) {
            Modifier.semantics { contentDescription = label }
        } else {
            Modifier
        }

    Box(
        modifier =
            modifier
                .then(semanticsModifier)
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
 * Displays a small status label with themed colors, accepting a custom content lambda.
 *
 * Generic overload for rendering arbitrary composable content inside the badge.
 *
 * ```
 * Badge(variant = BadgeVariant.Secondary) {
 *     Icon(RikkaIcons.Star, contentDescription = null, size = IconSize.Xs)
 *     Text("Featured")
 * }
 * ```
 *
 * @param modifier [Modifier] applied to the badge container.
 * @param variant [BadgeVariant] controlling background and foreground colors.
 * @param animation [BadgeAnimation] entrance effect when the badge first appears.
 * @param size [BadgeSize] controlling padding dimensions.
 * @param label Accessibility content description; defaults to empty.
 * @param content Composable content rendered inside the badge.
 */
@Composable
public fun Badge(
    modifier: Modifier = Modifier,
    variant: BadgeVariant = BadgeVariant.Default,
    animation: BadgeAnimation = BadgeAnimation.None,
    size: BadgeSize = BadgeSize.Default,
    label: String = "",
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

    val semanticsModifier =
        if (label.isNotEmpty()) {
            Modifier.semantics { contentDescription = label }
        } else {
            Modifier
        }

    Box(
        modifier =
            modifier
                .then(semanticsModifier)
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
                foreground = colors.onPrimary,
                border = Color.Transparent,
            )
        }

        BadgeVariant.Secondary -> {
            BadgeColors(
                background = colors.secondary,
                foreground = colors.onSecondary,
                border = Color.Transparent,
            )
        }

        BadgeVariant.Outline -> {
            BadgeColors(
                background = Color.Transparent,
                foreground = colors.onBackground,
                border = colors.border,
            )
        }

        BadgeVariant.Destructive -> {
            BadgeColors(
                background = colors.destructive,
                foreground = colors.onDestructive,
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
