package zed.rainxch.rikkaui.components.ui.kbd

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

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
 * Kbd("Ctrl+C")
 * Kbd("Enter")
 * ```
 *
 * @param text The keyboard shortcut or key name to display (e.g., "⌘K", "Ctrl+C").
 * @param modifier Modifier for layout and decoration.
 */
@Composable
fun Kbd(
    text: String,
    modifier: Modifier = Modifier,
) {
    val shape = RikkaTheme.shapes.sm

    Box(
        modifier =
            modifier
                .border(1.dp, RikkaTheme.colors.border, shape)
                .background(RikkaTheme.colors.muted, shape)
                .clip(shape)
                .padding(
                    horizontal = 6.dp,
                    vertical = 2.dp,
                ),
    ) {
        Text(
            text = text,
            variant = TextVariant.Small,
            color = RikkaTheme.colors.foreground,
            style = TextStyle(fontFamily = FontFamily.Monospace),
        )
    }
}
