package zed.rainxch.rikkaui.components.ui.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.text.Text

// ─── Variant ────────────────────────────────────────────────

/**
 * Button visual variants, matching shadcn/ui's button variants.
 *
 * - [Default] — Solid primary background. Main call-to-action.
 * - [Outline] — Bordered, transparent background. Secondary action.
 * - [Secondary] — Subtle filled background. Less prominent action.
 * - [Ghost] — No background or border. Minimal footprint.
 * - [Destructive] — Soft red tint. Dangerous/delete actions.
 * - [Link] — Looks like a text link. Underlines on hover.
 */
enum class ButtonVariant {
    Default,
    Outline,
    Secondary,
    Ghost,
    Destructive,
    Link,
}

// ─── Size ───────────────────────────────────────────────────

/**
 * Button sizes.
 *
 * - [Default] — Standard size for most use cases.
 * - [Sm] — Compact, for toolbars or dense UI.
 * - [Lg] — Larger touch target, for prominent actions.
 * - [Icon] — Square, for icon-only buttons.
 */
enum class ButtonSize {
    Default,
    Sm,
    Lg,
    Icon,
}

// ─── Animation ──────────────────────────────────────────────

/**
 * Controls the press animation style on the button.
 *
 * This is something shadcn/ui **can't do** — we leverage Compose's
 * animation system to deliver native-feeling interactions across
 * Android, iOS, Desktop, and Web.
 *
 * - [None] — No animation. Classic web-style button.
 * - [Scale] — Subtle scale-down on press with spring physics.
 * - [Bounce] — Playful spring bounce on press.
 */
enum class ButtonAnimation {
    None,
    Scale,
    Bounce,
}

// ─── Component ──────────────────────────────────────────────

/**
 * Button component for the RikkaUi design system.
 *
 * A drop-in replacement for Material3's Button — uses Rikka theme
 * tokens for styling and requires no Material dependency.
 *
 * Features beyond shadcn/ui:
 * - Spring-physics press animations (optional)
 * - Works natively on Android, iOS, Desktop, Web
 * - Hover + press states with smooth color transitions
 *
 * Usage:
 * ```
 * Button(onClick = { }) {
 *     Text("Click me")
 * }
 *
 * // With animation
 * Button(
 *     onClick = { },
 *     animation = ButtonAnimation.Scale,
 * ) {
 *     Text("Press me")
 * }
 *
 * // Variant + size
 * Button(
 *     onClick = { },
 *     variant = ButtonVariant.Outline,
 *     size = ButtonSize.Sm,
 * ) {
 *     Text("Cancel")
 * }
 * ```
 *
 * @param onClick Called when the button is clicked.
 * @param modifier Modifier for layout and decoration.
 * @param variant Visual variant — controls colors and border.
 * @param size Controls height, padding, and min width.
 * @param animation Press animation style. Defaults to [ButtonAnimation.Scale].
 * @param enabled Whether the button is interactive.
 * @param label Accessibility label for screen readers. When set, overrides content description.
 * @param content Button content — typically [Text] and/or icons.
 */
@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Default,
    size: ButtonSize = ButtonSize.Default,
    animation: ButtonAnimation = ButtonAnimation.Scale,
    enabled: Boolean = true,
    label: String = "",
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val colors = resolveColors(variant, isHovered, isPressed)
    val sizeValues = resolveSizeValues(size)
    val shape =
        when (size) {
            ButtonSize.Sm -> RikkaTheme.shapes.md
            else -> RikkaTheme.shapes.lg
        }

    // ─── Animation values (from theme motion tokens) ────
    val motion = RikkaTheme.motion

    val animationSpec =
        when (animation) {
            ButtonAnimation.None -> {
                null
            }

            ButtonAnimation.Scale -> {
                motion.springDefault
            }

            ButtonAnimation.Bounce -> {
                motion.springBouncy
            }
        }

    val targetScale =
        when {
            !enabled -> {
                1f
            }

            animation == ButtonAnimation.None -> {
                1f
            }

            isPressed -> {
                when (animation) {
                    ButtonAnimation.Scale -> motion.pressScaleSubtle
                    ButtonAnimation.Bounce -> motion.pressScaleBouncy
                    ButtonAnimation.None -> 1f
                }
            }

            else -> {
                1f
            }
        }

    val scale by animateFloatAsState(
        targetValue = targetScale,
        animationSpec = animationSpec ?: spring(),
    )

    // ─── Color animations (from theme motion tokens) ────
    val animatedBackground by animateColorAsState(
        targetValue = colors.background,
        animationSpec = tween(motion.durationDefault),
    )

    // ─── Modifiers ──────────────────────────────────────
    val backgroundModifier =
        if (colors.background != Color.Transparent) {
            Modifier.background(animatedBackground, shape)
        } else {
            Modifier
        }

    val borderModifier =
        if (colors.border != Color.Transparent) {
            Modifier.border(1.dp, colors.border, shape)
        } else {
            Modifier
        }

    val animationModifier =
        if (animation != ButtonAnimation.None) {
            Modifier.graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
        } else {
            Modifier
        }

    Row(
        modifier =
            modifier
                .then(animationModifier)
                .then(borderModifier)
                .then(backgroundModifier)
                .clip(shape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    role = Role.Button,
                    onClick = onClick,
                ).defaultMinSize(
                    minHeight = sizeValues.minHeight,
                    minWidth = sizeValues.minWidth,
                ).padding(
                    horizontal = sizeValues.horizontalPadding,
                    vertical = sizeValues.verticalPadding,
                ).then(if (!enabled) Modifier.alpha(0.5f) else Modifier)
                .semantics(mergeDescendants = true) {
                    if (label.isNotEmpty()) {
                        contentDescription = label
                    }
                    if (!enabled) {
                        disabled()
                    }
                },
        horizontalArrangement =
            Arrangement.spacedBy(
                sizeValues.contentSpacing,
                Alignment.CenterHorizontally,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        content()
    }
}

/**
 * Convenience overload with a text label.
 *
 * ```
 * Button("Save", onClick = { save() })
 * Button("Delete", onClick = { delete() }, variant = ButtonVariant.Destructive)
 * Button("Bounce!", onClick = { }, animation = ButtonAnimation.Bounce)
 * ```
 */
@Composable
fun Button(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Default,
    size: ButtonSize = ButtonSize.Default,
    animation: ButtonAnimation = ButtonAnimation.Scale,
    enabled: Boolean = true,
) {
    val colors = resolveColors(variant, isHovered = false, isPressed = false)

    Button(
        onClick = onClick,
        modifier = modifier,
        variant = variant,
        size = size,
        animation = animation,
        enabled = enabled,
    ) {
        val textStyle =
            if (variant == ButtonVariant.Link) {
                TextStyle(textDecoration = TextDecoration.None)
            } else {
                TextStyle.Default
            }

        Text(
            text = text,
            color = colors.foreground,
            style = RikkaTheme.typography.small.merge(textStyle),
        )
    }
}

// ─── Internal: Color Resolution ─────────────────────────────

private data class ButtonColors(
    val background: Color,
    val foreground: Color,
    val border: Color,
)

@Composable
private fun resolveColors(
    variant: ButtonVariant,
    isHovered: Boolean,
    isPressed: Boolean,
): ButtonColors {
    val colors = RikkaTheme.colors

    return when (variant) {
        ButtonVariant.Default -> {
            ButtonColors(
                background =
                    when {
                        isPressed -> colors.primary.copy(alpha = 0.7f)
                        isHovered -> colors.primary.copy(alpha = 0.8f)
                        else -> colors.primary
                    },
                foreground = colors.primaryForeground,
                border = Color.Transparent,
            )
        }

        ButtonVariant.Outline -> {
            ButtonColors(
                background =
                    when {
                        isPressed -> colors.muted.copy(alpha = 0.8f)
                        isHovered -> colors.muted
                        else -> Color.Transparent
                    },
                foreground = colors.foreground,
                border = colors.border,
            )
        }

        ButtonVariant.Secondary -> {
            ButtonColors(
                background =
                    when {
                        isPressed -> colors.secondary.copy(alpha = 0.7f)
                        isHovered -> colors.secondary.copy(alpha = 0.8f)
                        else -> colors.secondary
                    },
                foreground = colors.secondaryForeground,
                border = Color.Transparent,
            )
        }

        ButtonVariant.Ghost -> {
            ButtonColors(
                background =
                    when {
                        isPressed -> colors.muted.copy(alpha = 0.6f)
                        isHovered -> colors.muted
                        else -> Color.Transparent
                    },
                foreground = colors.foreground,
                border = Color.Transparent,
            )
        }

        ButtonVariant.Destructive -> {
            ButtonColors(
                background =
                    when {
                        isPressed -> colors.destructive.copy(alpha = 0.2f)
                        isHovered -> colors.destructive.copy(alpha = 0.15f)
                        else -> colors.destructive.copy(alpha = 0.1f)
                    },
                foreground = colors.destructive,
                border = Color.Transparent,
            )
        }

        ButtonVariant.Link -> {
            ButtonColors(
                background = Color.Transparent,
                foreground = colors.primary,
                border = Color.Transparent,
            )
        }
    }
}

// ─── Internal: Size Resolution ──────────────────────────────

private data class SizeValues(
    val minHeight: Dp,
    val minWidth: Dp,
    val horizontalPadding: Dp,
    val verticalPadding: Dp,
    val contentSpacing: Dp,
)

@Composable
private fun resolveSizeValues(size: ButtonSize): SizeValues =
    when (size) {
        ButtonSize.Default -> {
            SizeValues(
                minHeight = 36.dp,
                minWidth = 0.dp,
                horizontalPadding = RikkaTheme.spacing.md,
                verticalPadding = RikkaTheme.spacing.sm,
                contentSpacing = 6.dp,
            )
        }

        ButtonSize.Sm -> {
            SizeValues(
                minHeight = 28.dp,
                minWidth = 0.dp,
                horizontalPadding = RikkaTheme.spacing.sm,
                verticalPadding = 4.dp,
                contentSpacing = 4.dp,
            )
        }

        ButtonSize.Lg -> {
            SizeValues(
                minHeight = 44.dp,
                minWidth = 0.dp,
                horizontalPadding = RikkaTheme.spacing.lg,
                verticalPadding = RikkaTheme.spacing.sm,
                contentSpacing = 6.dp,
            )
        }

        ButtonSize.Icon -> {
            SizeValues(
                minHeight = 36.dp,
                minWidth = 36.dp,
                horizontalPadding = 0.dp,
                verticalPadding = 0.dp,
                contentSpacing = 0.dp,
            )
        }
    }
