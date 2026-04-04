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
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Animation ──────────────────────────────────────────────

/**
 * Focus animation style for [Textarea].
 *
 * @property Glow Animated focus ring that glows outward.
 * @property Color Border color transition on focus.
 * @property None No animation, instant border change.
 */
public enum class TextareaAnimation {
    /** Animated focus ring that glows outward. */
    Glow,

    /** Border color transition on focus. */
    Color,

    /** No animation, instant border change. */
    None,
}

// ─── Component ──────────────────────────────────────────────

/**
 * A multi-line text input area with animated focus ring, placeholder, and validation.
 *
 * Wraps [BasicTextField] in multi-line mode with RikkaUI theming, an optional
 * character counter, and three focus [animation] styles.
 *
 * ```
 * var bio by remember { mutableStateOf("") }
 * Textarea(
 *     value = bio,
 *     onValueChange = { bio = it },
 *     placeholder = "Tell us about yourself",
 *     maxLength = 500,
 *     showCharCount = true,
 * )
 * ```
 *
 * @param value Current text value.
 * @param onValueChange Called when the text changes.
 * @param modifier Modifier applied to the outer Column container.
 * @param placeholder Hint text shown when [value] is empty.
 * @param enabled Whether the textarea accepts user input.
 * @param readOnly When true, the field is focusable but not editable.
 * @param minLines Minimum visible lines before scrolling.
 * @param maxLines Maximum visible lines before scrolling.
 * @param label Accessibility content description for screen readers.
 * @param isError When true, the border color changes to destructive.
 * @param errorMessage Error text announced to assistive technologies when [isError] is true.
 * @param style Additional [TextStyle] merged with the theme typography.
 * @param animation Focus animation style — [TextareaAnimation.Glow], Color, None.
 * @param maxLength Maximum number of characters allowed; null for unlimited.
 * @param showCharCount When true and [maxLength] is set, displays a character counter below the textarea.
 */
@Composable
public fun Textarea(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    minLines: Int = 3,
    maxLines: Int = 5,
    label: String = "",
    isError: Boolean = false,
    errorMessage: String = "",
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
    val errorBorderColor = colors.destructive
    val targetBorderColor =
        when {
            !enabled -> colors.border.copy(alpha = 0.5f)
            isError -> errorBorderColor
            isFocused -> colors.ring
            else -> colors.border
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
                dampingRatio = motion.spatialDampingDefault,
                stiffness = motion.spatialStiffnessDefault,
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
                TextStyle(color = colors.onBackground),
            ).merge(style)

    val placeholderStyle =
        RikkaTheme.typography.p.merge(
            TextStyle(color = colors.onMuted),
        )

    val countStyle =
        RikkaTheme.typography.small.merge(
            TextStyle(color = colors.onMuted),
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
            if (isError && errorMessage.isNotEmpty()) {
                error(errorMessage)
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
            cursorBrush = SolidColor(colors.onBackground),
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
