package zed.rainxch.rikkaui.showcase.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.alert.Alert
import zed.rainxch.rikkaui.components.ui.alert.AlertDescription
import zed.rainxch.rikkaui.components.ui.alert.AlertTitle
import zed.rainxch.rikkaui.components.ui.alert.AlertVariant
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.card.CardContent
import zed.rainxch.rikkaui.components.ui.card.CardHeader
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.kbd.Kbd
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

/**
 * API key management card with masked keys, copy actions, and usage alert.
 *
 * Demonstrates [Card], [CardHeader], [CardContent], [Kbd], [Button],
 * [Icon], [Alert], [AlertTitle], [AlertDescription], and [Separator]
 * composed together in a developer tools layout.
 */
@Composable
fun ApiKeyManagerExample() {
    Card {
        // ─── Header with action button ────────────────────
        CardHeader {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "API Keys", variant = TextVariant.H4)
                Button(
                    text = "New Key",
                    onClick = { },
                    size = ButtonSize.Sm,
                )
            }
        }

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.xs))

        // ─── Key entries ──────────────────────────────────
        CardContent {
            KeyEntry(label = "Production", maskedKey = "sk-...a8f2")
            Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))
            KeyEntry(label = "Development", maskedKey = "sk-...c3d1")
        }

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))
        Separator()
        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        // ─── Usage warning ────────────────────────────────
        Alert(variant = AlertVariant.Destructive) {
            AlertTitle(
                text = "Rate Limited",
                variant = AlertVariant.Destructive,
            )
            AlertDescription(
                text = "95% of quota used",
                variant = AlertVariant.Destructive,
            )
        }
    }
}

@Composable
private fun KeyEntry(
    label: String,
    maskedKey: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = label, variant = TextVariant.P)
        Spacer(modifier = Modifier.weight(1f))
        Kbd(text = maskedKey)
        Button(
            onClick = { },
            variant = ButtonVariant.Ghost,
            size = ButtonSize.Icon,
        ) {
            Icon(
                imageVector = RikkaIcons.Copy,
                contentDescription = "Copy $label key",
            )
        }
    }
}
