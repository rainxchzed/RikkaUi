package zed.rainxch.rikkaui.components.ui.kbd

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Size ───────────────────────────────────────────────────

/** Kbd size variants. */
enum class KbdSize(
    val horizontalPadding: Dp,
    val verticalPadding: Dp,
) {
    Sm(horizontalPadding = 4.dp, verticalPadding = 1.dp),
    Default(horizontalPadding = 6.dp, verticalPadding = 2.dp),
    Lg(horizontalPadding = 8.dp, verticalPadding = 3.dp),
}

// ─── Component ──────────────────────────────────────────────

/**
 * Keyboard shortcut indicator rendered as a bordered, muted-background key cap.
 *
 * Automatically sets an accessibility content description of "Keyboard shortcut: {text}"
 * unless an explicit [label] is provided.
 *
 * ```
 * Kbd(text = "Ctrl")
 *
 * Kbd(text = "K", size = KbdSize.Sm)
 * ```
 *
 * @param text The key label displayed inside the indicator (e.g. "Ctrl", "K", "Enter").
 * @param modifier [Modifier] applied to the key cap container.
 * @param size [KbdSize] controlling horizontal/vertical padding.
 * @param label Accessibility content description override; defaults to "Keyboard shortcut: {text}".
 */
@Composable
fun Kbd(
    text: String,
    modifier: Modifier = Modifier,
    size: KbdSize = KbdSize.Default,
    label: String? = null,
) {
    val shape = RikkaTheme.shapes.sm
    val textVariant =
        when (size) {
            KbdSize.Lg -> TextVariant.P
            else -> TextVariant.Small
        }
    val resolvedLabel = label ?: "Keyboard shortcut: $text"

    Box(
        modifier =
            modifier
                .semantics { contentDescription = resolvedLabel }
                .border(1.dp, RikkaTheme.colors.border, shape)
                .background(RikkaTheme.colors.muted, shape)
                .clip(shape)
                .padding(
                    horizontal = size.horizontalPadding,
                    vertical = size.verticalPadding,
                ),
    ) {
        Text(
            text = text,
            variant = textVariant,
            color = RikkaTheme.colors.onBackground,
            maxLines = 1,
        )
    }
}

/**
 * Displays a keyboard shortcut combination as a row of [Kbd] indicators with separators.
 *
 * Merges descendants for accessibility and provides a combined content description.
 *
 * ```
 * KbdCombo(keys = listOf("Ctrl", "Shift", "K"))
 *
 * KbdCombo(keys = listOf("Cmd", "S"), separator = "+", size = KbdSize.Lg)
 * ```
 *
 * @param keys List of key labels to display (e.g. ["Ctrl", "C"]).
 * @param modifier [Modifier] applied to the row container.
 * @param size [KbdSize] applied to each individual key indicator.
 * @param separator String rendered between keys (e.g. "+"); empty string hides separators.
 */
@Composable
fun KbdCombo(
    keys: List<String>,
    modifier: Modifier = Modifier,
    size: KbdSize = KbdSize.Default,
    separator: String = "+",
) {
    val comboLabel =
        "Keyboard shortcut: " + keys.joinToString(separator)
    Row(
        modifier =
            modifier.semantics(mergeDescendants = true) {
                contentDescription = comboLabel
            },
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        keys.forEachIndexed { index, key ->
            Kbd(text = key, size = size)
            if (index < keys.lastIndex && separator.isNotEmpty()) {
                Text(
                    text = separator,
                    variant = TextVariant.Muted,
                    color = RikkaTheme.colors.onMuted,
                )
            }
        }
    }
}
