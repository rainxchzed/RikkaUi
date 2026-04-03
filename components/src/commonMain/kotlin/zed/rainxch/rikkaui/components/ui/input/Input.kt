package zed.rainxch.rikkaui.components.ui.input

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.foundation.RikkaTheme
import zed.rainxch.rikkaui.foundation.modifier.minTouchTarget

// ─── Animation ──────────────────────────────────────────────

enum class InputAnimation {
    /** Animated focus ring that glows outward from the border. */
    Glow,

    /** Simple border color transition on focus. */
    Color,

    /** No animation — border color changes instantly. */
    None,
}

// ─── Defaults ───────────────────────────────────────────────

object InputDefaults {
    @Composable
    fun colors(): InputColorValues {
        val c = RikkaTheme.colors
        return InputColorValues(
            background = c.background,
            border = c.border,
            focusedBorder = c.ring,
            disabledBorder = c.border.copy(alpha = 0.5f),
            disabledBackground = c.muted.copy(alpha = 0.3f),
            text = c.onBackground,
            placeholder = c.onMuted,
            ring = c.ring,
        )
    }
}

@Immutable
data class InputColorValues(
    val background: Color,
    val border: Color,
    val focusedBorder: Color,
    val disabledBorder: Color,
    val disabledBackground: Color,
    val text: Color,
    val placeholder: Color,
    val ring: Color,
) {
    fun borderColor(
        focused: Boolean,
        enabled: Boolean,
        isError: Boolean = false,
        errorBorder: Color = Color.Unspecified,
    ): Color =
        when {
            !enabled -> disabledBorder
            isError -> errorBorder
            focused -> focusedBorder
            else -> border
        }
}

// ─── Component ──────────────────────────────────────────────

@Composable
fun Input(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    label: String = "",
    isError: Boolean = false,
    errorMessage: String = "",
    style: TextStyle = TextStyle.Default,
    animation: InputAnimation = InputAnimation.Glow,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    clearable: Boolean = false,
    onClear: (() -> Unit)? = null,
    maxLength: Int? = null,
    showCharCount: Boolean = false,
    colors: InputColorValues = InputDefaults.colors(),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val themeColors = RikkaTheme.colors
    val motion = RikkaTheme.motion
    val shape = RikkaTheme.shapes.md
    val spacing = RikkaTheme.spacing

    // ─── Resolve border color & animation ─────────────────
    val errorBorderColor = themeColors.destructive
    val targetBorderColor =
        colors.borderColor(
            focused = isFocused,
            enabled = enabled,
            isError = isError,
            errorBorder = errorBorderColor,
        )

    val borderColor =
        when (animation) {
            InputAnimation.None -> {
                targetBorderColor
            }

            InputAnimation.Color -> {
                val animated by animateColorAsState(
                    targetValue = targetBorderColor,
                    animationSpec = tween(motion.durationDefault),
                )
                animated
            }

            InputAnimation.Glow -> {
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
                animation == InputAnimation.Glow &&
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
                animation == InputAnimation.Glow &&
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
                TextStyle(color = colors.text),
            ).merge(style)

    val placeholderStyle =
        RikkaTheme.typography.p.merge(
            TextStyle(color = colors.placeholder),
        )

    val countStyle =
        RikkaTheme.typography.small.merge(
            TextStyle(color = colors.placeholder),
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
    val inputBgColor = colors.background
    val density = LocalDensity.current

    BasicTextField(
        value = value,
        onValueChange = effectiveOnValueChange,
        modifier =
            modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 40.dp)
                .then(semanticsModifier),
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(colors.text),
        decorationBox = { innerTextField ->
            Box(
                modifier =
                    Modifier
                        .then(
                            if (glowSpread > 0.dp) {
                                Modifier.drawBehind {
                                    val spreadPx = glowSpread.toPx()
                                    val outline =
                                        shape.createOutline(
                                            size,
                                            LayoutDirection.Ltr,
                                            density,
                                        )
                                    val cr =
                                        when (outline) {
                                            is androidx.compose.ui
                                                .graphics.Outline.Rounded,
                                            -> {
                                                outline.roundRect
                                                    .topLeftCornerRadius.x +
                                                    spreadPx
                                            }

                                            else -> {
                                                spreadPx
                                            }
                                        }
                                    drawRoundRect(
                                        color =
                                            ringColor.copy(
                                                alpha = glowAlpha,
                                            ),
                                        topLeft =
                                            Offset(
                                                -spreadPx,
                                                -spreadPx,
                                            ),
                                        size =
                                            Size(
                                                size.width + spreadPx * 2,
                                                size.height + spreadPx * 2,
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
                        .background(inputBgColor, shape)
                        .clip(shape)
                        .padding(
                            horizontal = spacing.md,
                            vertical = spacing.sm,
                        ).then(
                            if (!enabled) {
                                Modifier.background(
                                    colors.disabledBackground,
                                    shape,
                                )
                            } else {
                                Modifier
                            },
                        ),
                contentAlignment = Alignment.CenterStart,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    // ─── Leading icon ─────────────────
                    if (leadingIcon != null) {
                        val painter = rememberVectorPainter(leadingIcon)
                        androidx.compose.foundation.Image(
                            painter = painter,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            colorFilter =
                                ColorFilter.tint(
                                    colors.placeholder,
                                ),
                        )
                        Spacer(Modifier.width(spacing.sm))
                    }

                    // ─── Text field + placeholder ─────
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart,
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

                    // ─── Character count ──────────────
                    if (showCharCount && maxLength != null) {
                        Spacer(Modifier.width(spacing.sm))
                        BasicText(
                            text = "${value.length}/$maxLength",
                            style = countStyle,
                        )
                    }

                    // ─── Trailing icon / clear button ─
                    val showClear =
                        clearable &&
                            value.isNotEmpty() &&
                            enabled &&
                            !readOnly
                    if (showClear) {
                        Spacer(Modifier.width(spacing.sm))
                        Box(
                            modifier =
                                Modifier
                                    .minTouchTarget()
                                    .size(16.dp)
                                    .clickable(
                                        interactionSource =
                                            remember {
                                                MutableInteractionSource()
                                            },
                                        indication = null,
                                        role = Role.Button,
                                    ) {
                                        if (onClear != null) {
                                            onClear()
                                        } else {
                                            onValueChange("")
                                        }
                                    },
                            contentAlignment = Alignment.Center,
                        ) {
                            ClearIcon(
                                tint = colors.placeholder,
                            )
                        }
                    } else if (trailingIcon != null) {
                        Spacer(Modifier.width(spacing.sm))
                        val painter =
                            rememberVectorPainter(
                                trailingIcon,
                            )
                        androidx.compose.foundation.Image(
                            painter = painter,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            colorFilter =
                                ColorFilter.tint(
                                    colors.placeholder,
                                ),
                        )
                    }
                }
            }
        },
    )
}

// ─── Clear icon (inline X — avoids RikkaIcons dependency cycle) ─

// Inline X icon to avoid circular dependency on RikkaIcons
@Composable
private fun ClearIcon(tint: Color) {
    val vector =
        remember {
            androidx.compose.ui.graphics.vector.ImageVector
                .Builder(
                    name = "ClearX",
                    defaultWidth = 16.dp,
                    defaultHeight = 16.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f,
                ).apply {
                    addPath(
                        pathData =
                            androidx.compose.ui.graphics.vector.PathData {
                                moveTo(18f, 6f)
                                lineTo(6f, 18f)
                                moveTo(6f, 6f)
                                lineTo(18f, 18f)
                            },
                        stroke = SolidColor(Color.Black),
                        strokeLineWidth = 2f,
                        strokeLineCap = androidx.compose.ui.graphics.StrokeCap.Round,
                    )
                }.build()
        }
    val painter = rememberVectorPainter(vector)
    androidx.compose.foundation.Image(
        painter = painter,
        contentDescription = "Clear input",
        modifier = Modifier.size(16.dp),
        colorFilter = ColorFilter.tint(tint),
    )
}
