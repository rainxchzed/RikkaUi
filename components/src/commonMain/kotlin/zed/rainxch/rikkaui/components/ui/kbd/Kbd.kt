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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

// ─── Size ───────────────────────────────────────────────────

/**
 * Kbd size variants.
 *
 * - [Sm] — Compact, for inline use in dense layouts.
 * - [Default] — Standard size.
 * - [Lg] — Larger, for standalone shortcut displays.
 */
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
 * Kbd (keyboard) component for the RikkaUi design system.
 *
 * A styled inline indicator for keyboard shortcuts or key combinations.
 * Displays text in a bordered box with a monospace feel, similar to
 * HTML's `<kbd>` element.
 *
 * Usage:
 * ```
 * Kbd("⌘K")
 * Kbd("Enter", size = KbdSize.Lg)
 * ```
 *
 * @param text The keyboard shortcut or key name to display (e.g., "⌘K", "Ctrl+C").
 * @param modifier Modifier for layout and decoration.
 * @param size Size variant controlling padding. Defaults to [KbdSize.Default].
 */
@Composable
fun Kbd(
    text: String,
    modifier: Modifier = Modifier,
    size: KbdSize = KbdSize.Default,
) {
    val shape = RikkaTheme.shapes.sm
    val textVariant =
        when (size) {
            KbdSize.Lg -> TextVariant.P
            else -> TextVariant.Small
        }

    Box(
        modifier =
            modifier
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
            color = RikkaTheme.colors.foreground,
            style = TextStyle(fontFamily = FontFamily.Monospace),
        )
    }
}

/**
 * Kbd combo — displays multiple keys with a separator between them.
 *
 * Each key is rendered as an individual [Kbd] badge, joined by a `+` separator.
 * Ideal for displaying keyboard shortcuts like Ctrl+Shift+P.
 *
 * Usage:
 * ```
 * KbdCombo(keys = listOf("Ctrl", "Shift", "P"))
 * KbdCombo(keys = listOf("⌘", "K"), separator = " ")
 * ```
 *
 * @param keys List of individual key labels to display.
 * @param modifier Modifier for the outer row layout.
 * @param size Size variant applied to each key badge.
 * @param separator Text displayed between keys. Defaults to "+".
 */
@Composable
fun KbdCombo(
    keys: List<String>,
    modifier: Modifier = Modifier,
    size: KbdSize = KbdSize.Default,
    separator: String = "+",
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        keys.forEachIndexed { index, key ->
            Kbd(text = key, size = size)
            if (index < keys.lastIndex && separator.isNotEmpty()) {
                Text(
                    text = separator,
                    variant = TextVariant.Muted,
                    color = RikkaTheme.colors.mutedForeground,
                )
            }
        }
    }
}
