package zed.rainxch.rikkaui.components.ui.textarea

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme

// ─── Component ──────────────────────────────────────────────

/**
 * Textarea component for the RikkaUi design system.
 *
 * A multi-line text input that mirrors the styling of [Input] but
 * supports multiple lines of text. Uses Rikka theme tokens for
 * styling with animated focus states.
 *
 * Features:
 * - Multi-line text editing with configurable min/max lines
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
 * Textarea(
 *     value = bio,
 *     onValueChange = { bio = it },
 *     placeholder = "Tell us about yourself...",
 *     minLines = 4,
 *     maxLines = 8,
 * )
 *
 * Textarea(
 *     value = notes,
 *     onValueChange = { notes = it },
 *     placeholder = "Read-only notes",
 *     readOnly = true,
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
 * @param label Accessibility label for screen readers. Describes the textarea's purpose.
 * @param style Override text style. Merged on top of theme's paragraph style.
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
