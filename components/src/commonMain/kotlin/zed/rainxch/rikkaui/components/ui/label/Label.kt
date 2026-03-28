package zed.rainxch.rikkaui.components.ui.label

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

// ─── Component ──────────────────────────────────────────────

/**
 * Label component for the RikkaUi design system.
 *
 * A text label intended for form elements. Uses [TextVariant.Small] styling
 * with medium font weight, matching shadcn/ui's Label component.
 *
 * Usage:
 * ```
 * Label("Email")
 * Label("Username", disabled = true)
 * Label("Password", modifier = Modifier.padding(bottom = RikkaTheme.spacing.xs))
 * ```
 *
 * @param text The label text to display.
 * @param modifier Modifier for layout and decoration.
 * @param disabled When true, the label is dimmed using [RikkaTheme.colors.mutedForeground].
 */
@Composable
fun Label(
    text: String,
    modifier: Modifier = Modifier,
    disabled: Boolean = false,
) {
    val color = resolveColor(disabled)

    Text(
        text = text,
        modifier = modifier.semantics {
            this.text = AnnotatedString(text)
        },
        variant = TextVariant.Small,
        color = color,
        style = TextStyle(fontWeight = FontWeight.Medium),
    )
}

// ─── Internal: Color Resolution ─────────────────────────────

/**
 * Resolves the label text color based on the disabled state.
 */
@Composable
private fun resolveColor(disabled: Boolean): Color =
    if (disabled) {
        RikkaTheme.colors.mutedForeground
    } else {
        RikkaTheme.colors.foreground
    }
