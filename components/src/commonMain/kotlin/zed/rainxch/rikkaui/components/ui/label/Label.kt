package zed.rainxch.rikkaui.components.ui.label

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Component ──────────────────────────────────────────────

@Composable
fun Label(
    text: String,
    modifier: Modifier = Modifier,
    disabled: Boolean = false,
    required: Boolean = false,
) {
    val color = resolveColor(disabled)
    val semanticText =
        if (required) "$text (required)" else text

    Row(
        modifier =
            modifier.semantics {
                this.text = AnnotatedString(semanticText)
            },
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Text(
            text = text,
            variant = TextVariant.Small,
            color = color,
            style = TextStyle(fontWeight = FontWeight.Medium),
        )

        if (required) {
            Text(
                text = "*",
                variant = TextVariant.Small,
                color = RikkaTheme.colors.destructive,
                style = TextStyle(fontWeight = FontWeight.Medium),
            )
        }
    }
}

// ─── Internal: Color Resolution ─────────────────────────────

@Composable
private fun resolveColor(disabled: Boolean): Color =
    if (disabled) {
        RikkaTheme.colors.mutedForeground
    } else {
        RikkaTheme.colors.foreground
    }
