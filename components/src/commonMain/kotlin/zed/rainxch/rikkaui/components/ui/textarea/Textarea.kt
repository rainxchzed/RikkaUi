package zed.rainxch.rikkaui.components.ui.textarea

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Animation ──────────────────────────────────────────────

/**
 * Controls the focus animation style on the textarea.
 *
 * Mirrors [zed.rainxch.rikkaui.components.ui.input.InputAnimation]
 * for consistency across text input components.
 *
 * - [Glow] — Animated focus ring that glows outward from the border.
 *   Uses the `ring` color token with animated spread and opacity.
 *   This is the default for a polished, modern feel.
 * - [Color] — Simple border color transition on focus.
 *   Clean and minimal, uses tween from `RikkaTheme.motion`.
 * - [None] — No animation. Border color changes instantly on focus.
 *   Useful for reduced-motion preferences or performance-critical UIs.
 */
enum class TextareaAnimation {
    Glow,
    Color,
    None,
}

// ─── Component ──────────────────────────────────────────────

/**
 * Textarea component for the RikkaUi design system.
 *
 * A multi-line text input that mirrors the styling of Input but
 * supports multiple lines of text. Uses Rikka theme tokens for
 * styling with animated focus states.
 *
 * Features:
 * - Three focus animation styles: Glow, Color, None
 * - Multi-line text editing with configurable min/max lines
 * - Optional character count display with max length enforcement
 * - Animated border color on focus (uses ring token)
 * - Placeholder text support
 * - No Material dependency
 *
 * Usage:
 * ```
 * var text by remember { mutableStateOf("") }
 *
 * Textarea(
 *     value = text,
 *     onValueChange = { text = it },
 *     placeholder = "Write your message...",
 * )
 *
 * // With glow animation and character limit
 * Textarea(
 *     value = text,
 *     onValueChange = { text = it },
 *     placeholder = "Bio (max 280 chars)...",
 *     animation = TextareaAnimation.Glow,
 *     maxLength = 280,
 *     showCharCount = true,
 * )
 *
 * Textarea(
 *     value = bio,
 *     onValueChange = { bio = it },
 *     placeholder = "Tell us about yourself...",
 *     minLines = 4,
 *     maxLines = 8,
 *     animation = TextareaAnimation.Color,
 * )
 *
 * Textarea(
 *     value = notes,
 *     onValueChange = { notes = it },
 *     placeholder = "Read-only notes",
 *     readOnly = true,
 *     animation = TextareaAnimation.None,
 * )
 * ```
 *
 * @param value Current text value.
 * @param onValueChange Called when the text changes.
 * @param modifier Modifier for layout and decoration.
 * @param placeholder Placeholder text shown when empty.
 * @param enabled Whether the textarea is interactive.
 * @param readOnly Whether the textarea is read-only.
 * @param minLines Minimum number of visible lines. Defaults to 3.
 * @param maxLines Maximum number of visible lines. Defaults to 5.
 * @param label Accessibility label for screen readers.
 * @param style Override text style. Merged on top of theme's paragraph style.
 * @param animation Focus animation style. Defaults to [TextareaAnimation.Glow].
 * @param maxLength Optional maximum character limit. When set, input is
 *   truncated to this length.
 * @param showCharCount When true and [maxLength] is set, displays a
 *   character count (e.g. "128/280") below the textarea.
 */
@Composable
fun Textarea(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    minLines: Int = 3,
    maxLines: Int = 5,
    label: String = "",
    style: TextStyle = TextStyle.Default,
    animation: TextareaAnimation = TextareaAnimation.Glow,
    maxLength: Int? = null,
    showCharCount: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val colors = RikkaTheme.colors
    val motion = RikkaTheme.motion
    val shape = RikkaTheme.shapes.md
    val spacing = RikkaTheme.spacing

    // ─── Resolve border color & animation ─────────────────
    val targetBorderColor =
        when {
            !enabled -> colors.input.copy(alpha = 0.5f)
            isFocused -> colors.ring
            else -> colors.input
        }

    val borderColor =
        when (animation) {
            TextareaAnimation.None -> {
                targetBorderColor
            }

            TextareaAnimation.Color -> {
                val animated by animateColorAsState(
                    targetValue = targetBorderColor,
                    animationSpec = tween(motion.durationDefault),
                )
                animated
            }

            TextareaAnimation.Glow -> {
                val animated by animateColorAsState(
                    targetValue = targetBorderColor,
                    animationSpec = tween(motion.durationDefault),
                )
                animated
            }
        }

    // ─── Glow spread + opacity (only for Glow mode) ───────
    val glowSpread by animateDpAsState(
        targetValue =
            if (
                animation == TextareaAnimation.Glow &&
                isFocused &&
                enabled
            ) {
                3.dp
            } else {
                0.dp
            },
        animationSpec =
            spring(
                dampingRatio = motion.pressScaleSubtle,
                stiffness = 400f,
            ),
    )
    val glowAlpha by animateFloatAsState(
        targetValue =
            if (
                animation == TextareaAnimation.Glow &&
                isFocused &&
                enabled
            ) {
                0.35f
            } else {
                0f
            },
        animationSpec = tween(motion.durationDefault),
    )

    // ─── Enforce maxLength ────────────────────────────────
    val effectiveOnValueChange: (String) -> Unit =
        if (maxLength != null) {
            { newValue -> onValueChange(newValue.take(maxLength)) }
        } else {
            onValueChange
        }

    val textStyle =
        RikkaTheme.typography.p
            .merge(
                TextStyle(color = colors.foreground),
            ).merge(style)

    val placeholderStyle =
        RikkaTheme.typography.p.merge(
            TextStyle(color = colors.mutedForeground),
        )

    val countStyle =
        RikkaTheme.typography.small.merge(
            TextStyle(color = colors.mutedForeground),
        )

    // ─── Accessibility ─────────────────────────────────
    val accessibilityLabel = label.ifEmpty { placeholder }
    val semanticsModifier =
        Modifier.semantics {
            if (accessibilityLabel.isNotEmpty()) {
                contentDescription = accessibilityLabel
            }
            if (!enabled) {
                disabled()
            }
        }

    val ringColor = colors.ring
    val density = LocalDensity.current

    Column(modifier = modifier.fillMaxWidth()) {
        BasicTextField(
            value = value,
            onValueChange = effectiveOnValueChange,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 80.dp)
                    .then(semanticsModifier),
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            singleLine = false,
            minLines = minLines,
            maxLines = maxLines,
            interactionSource = interactionSource,
            cursorBrush = SolidColor(colors.foreground),
            decorationBox = { innerTextField ->
                Box(
                    modifier =
                        Modifier
                            .then(
                                if (glowSpread > 0.dp) {
                                    Modifier.drawBehind {
                                        val spreadPx =
                                            glowSpread.toPx()
                                        val outline =
                                            shape.createOutline(
                                                size,
                                                LayoutDirection.Ltr,
                                                density,
                                            )
                                        val cr =
                                            when (outline) {
                                                is androidx.compose.ui
                                                    .graphics.Outline
                                                    .Rounded,
                                                -> {
                                                    outline.roundRect
                                                        .topLeftCornerRadius
                                                        .x + spreadPx
                                                }

                                                else -> {
                                                    spreadPx
                                                }
                                            }
                                        drawRoundRect(
                                            color =
                                                ringColor.copy(
                                                    alpha =
                                                    glowAlpha,
                                                ),
                                            topLeft =
                                                Offset(
                                                    -spreadPx,
                                                    -spreadPx,
                                                ),
                                            size =
                                                Size(
                                                    size.width +
                                                        spreadPx * 2,
                                                    size.height +
                                                        spreadPx * 2,
                                                ),
                                            cornerRadius =
                                                CornerRadius(
                                                    cr,
                                                    cr,
                                                ),
                                            style =
                                                Stroke(
                                                    width = spreadPx,
                                                ),
                                        )
                                    }
                                } else {
                                    Modifier
                                },
                            ).border(1.dp, borderColor, shape)
                            .background(colors.background, shape)
                            .clip(shape)
                            .padding(
                                horizontal = spacing.md,
                                vertical = spacing.sm,
                            ).then(
                                if (!enabled) {
                                    Modifier.background(
                                        colors.muted.copy(
                                            alpha = 0.3f,
                                        ),
                                        shape,
                                    )
                                } else {
                                    Modifier
                                },
                            ),
                ) {
                    if (
                        value.isEmpty() &&
                        placeholder.isNotEmpty()
                    ) {
                        BasicText(
                            text = placeholder,
                            style = placeholderStyle,
                        )
                    }
                    innerTextField()
                }
            },
        )

        // ─── Character count (below the textarea) ─────────
        if (showCharCount && maxLength != null) {
            Spacer(Modifier.height(spacing.xs))
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Spacer(Modifier.weight(1f))
                BasicText(
                    text = "${value.length}/$maxLength",
                    style = countStyle,
                )
            }
        }
    }
}
