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
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Animation ──────────────────────────────────────────────

/**
 * Controls the focus animation style on the input.
 *
 * Compose lets us do things CSS can't — animated glow rings,
 * spring-physics border transitions, and per-token motion control.
 *
 * - [Glow] — Animated focus ring that glows outward from the border.
 *   Uses the `ring` color token with animated spread and opacity.
 *   This is the default for a polished, modern feel.
 * - [Color] — Simple border color transition on focus.
 *   Clean and minimal, uses tween from `RikkaTheme.motion`.
 * - [None] — No animation. Border color changes instantly on focus.
 *   Useful for reduced-motion preferences or performance-critical UIs.
 */
enum class InputAnimation {
    Glow,
    Color,
    None,
}

// ─── Component ──────────────────────────────────────────────

/**
 * Input (text field) component for the RikkaUi design system.
 *
 * A single-line text input that replaces Material3's TextField/OutlinedTextField.
 * Uses Rikka theme tokens for styling with animated focus states.
 *
 * Features:
 * - Three focus animation styles: Glow, Color, None
 * - Optional leading and trailing icon slots
 * - Optional clear button (trailing X icon)
 * - Optional character count display
 * - Placeholder text support
 * - Full keyboard options and actions support
 * - No Material dependency
 *
 * Usage:
 * ```
 * var text by remember { mutableStateOf("") }
 *
 * Input(
 *     value = text,
 *     onValueChange = { text = it },
 *     placeholder = "Enter your name...",
 * )
 *
 * // With glow animation (default)
 * Input(
 *     value = text,
 *     onValueChange = { text = it },
 *     placeholder = "Search...",
 *     leadingIcon = RikkaIcons.Search,
 *     animation = InputAnimation.Glow,
 * )
 *
 * // With clear button and character count
 * Input(
 *     value = text,
 *     onValueChange = { text = it },
 *     placeholder = "Username",
 *     clearable = true,
 *     maxLength = 32,
 *     showCharCount = true,
 * )
 *
 * // No animation
 * Input(
 *     value = text,
 *     onValueChange = { text = it },
 *     placeholder = "Instant focus",
 *     animation = InputAnimation.None,
 * )
 * ```
 *
 * @param value Current text value.
 * @param onValueChange Called when the text changes.
 * @param modifier Modifier for layout and decoration.
 * @param placeholder Placeholder text shown when empty.
 * @param enabled Whether the input is interactive.
 * @param readOnly Whether the input is read-only.
 * @param singleLine Whether to constrain to a single line. Defaults to true.
 * @param keyboardOptions Software keyboard configuration.
 * @param keyboardActions IME action handlers.
 * @param visualTransformation Visual transformation (e.g., password masking).
 * @param label Accessibility label for screen readers.
 * @param style Override text style. Merged on top of theme's paragraph style.
 * @param animation Focus animation style. Defaults to [InputAnimation.Glow].
 * @param leadingIcon Optional icon displayed before the text field.
 * @param trailingIcon Optional icon displayed after the text field.
 * @param clearable When true, shows a clear (X) button when the input has text.
 *   The clear button replaces [trailingIcon] when text is non-empty.
 * @param onClear Called when the clear button is tapped. Defaults to
 *   calling `onValueChange("")`.
 * @param maxLength Optional maximum character limit. When set, input is
 *   truncated to this length.
 * @param showCharCount When true and [maxLength] is set, displays a
 *   character count (e.g. "12/32") after the input.
 */
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
    style: TextStyle = TextStyle.Default,
    animation: InputAnimation = InputAnimation.Glow,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    clearable: Boolean = false,
    onClear: (() -> Unit)? = null,
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
                dampingRatio = motion.pressScaleSubtle,
                stiffness = 400f,
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
        cursorBrush = SolidColor(colors.foreground),
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
                        .background(colors.background, shape)
                        .clip(shape)
                        .padding(
                            horizontal = spacing.md,
                            vertical = spacing.sm,
                        ).then(
                            if (!enabled) {
                                Modifier.background(
                                    colors.muted.copy(alpha = 0.3f),
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
                                    colors.mutedForeground,
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
                                tint = colors.mutedForeground,
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
                                    colors.mutedForeground,
                                ),
                        )
                    }
                }
            }
        },
    )
}

// ─── Clear icon (inline X — avoids RikkaIcons dependency cycle) ─

/**
 * Small X icon used for the clear button.
 * Drawn inline to avoid a circular dependency on RikkaIcons.
 */
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
