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

enum class TextareaAnimation {
    /** Animated focus ring that glows outward. */
    Glow,

    /** Border color transition on focus. */
    Color,

    /** No animation, instant border change. */
    None,
}

// ─── Component ──────────────────────────────────────────────

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
