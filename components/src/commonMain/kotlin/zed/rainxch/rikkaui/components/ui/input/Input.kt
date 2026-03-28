package zed.rainxch.rikkaui.components.ui.input

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme

// ─── Component ──────────────────────────────────────────────

/**
 * Input (text field) component for the RikkaUi design system.
 *
 * A single-line text input that replaces Material3's TextField/OutlinedTextField.
 * Uses Rikka theme tokens for styling with animated focus states.
 *
 * Features:
 * - Animated border color on focus (uses ring token)
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
 * Input(
 *     value = email,
 *     onValueChange = { email = it },
 *     placeholder = "email@example.com",
 *     enabled = false,
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
 * @param label Accessibility label for screen readers. Describes the input's purpose.
 * @param style Override text style. Merged on top of theme's paragraph style.
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
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val colors = RikkaTheme.colors
    val motion = RikkaTheme.motion
    val shape = RikkaTheme.shapes.md

    // ─── Animated border (from theme motion tokens) ──────
    val borderColor by animateColorAsState(
        targetValue =
            when {
                !enabled -> colors.input.copy(alpha = 0.5f)
                isFocused -> colors.ring
                else -> colors.input
            },
        animationSpec = tween(motion.durationDefault),
    )

    val textStyle =
        RikkaTheme.typography.p
            .merge(
                TextStyle(color = colors.foreground),
            ).merge(style)

    val placeholderStyle =
        RikkaTheme.typography.p.merge(
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

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
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
                        .border(1.dp, borderColor, shape)
                        .background(colors.background, shape)
                        .clip(shape)
                        .padding(
                            horizontal = RikkaTheme.spacing.md,
                            vertical = RikkaTheme.spacing.sm,
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
                if (value.isEmpty() && placeholder.isNotEmpty()) {
                    androidx.compose.foundation.text.BasicText(
                        text = placeholder,
                        style = placeholderStyle,
                    )
                }
                innerTextField()
            }
        },
    )
}
