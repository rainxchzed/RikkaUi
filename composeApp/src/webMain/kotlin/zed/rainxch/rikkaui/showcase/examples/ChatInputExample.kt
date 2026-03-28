package zed.rainxch.rikkaui.showcase.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.input.Input
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

/**
 * AI chat input area example for the showcase mosaic grid.
 *
 * Demonstrates Input, Button (Ghost and Default variants),
 * and Text in a chat composer pattern.
 */
@Composable
fun ChatInputExample() {
    var query by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        label = "AI chat input area",
    ) {
        Input(
            value = query,
            onValueChange = { query = it },
            placeholder = "Ask, Search or Chat...",
            label = "AI chat input",
        )

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Left side: add button + auto label
            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.xs,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(
                    onClick = { },
                    variant = ButtonVariant.Ghost,
                    size = ButtonSize.Icon,
                    label = "Add attachment",
                ) {
                    Text(
                        text = "+",
                        variant = TextVariant.Small,
                        color = RikkaTheme.colors.foreground,
                    )
                }
                Text(
                    text = "Auto",
                    variant = TextVariant.Small,
                    color = RikkaTheme.colors.mutedForeground,
                )
            }

            // Right side: usage label + send button
            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "52% used",
                    variant = TextVariant.Small,
                    color = RikkaTheme.colors.mutedForeground,
                )
                Button(
                    onClick = { },
                    size = ButtonSize.Icon,
                    label = "Send message",
                ) {
                    Text(
                        text = "\u2191",
                        variant = TextVariant.Small,
                        color = RikkaTheme.colors.primaryForeground,
                    )
                }
            }
        }
    }
}
