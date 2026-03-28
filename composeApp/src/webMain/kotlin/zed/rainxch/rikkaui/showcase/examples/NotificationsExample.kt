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
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.input.Input
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

/**
 * Status notifications card example for the showcase mosaic grid.
 *
 * Demonstrates Badge variants, Separator, Input, and Button
 * combined in a notification panel pattern.
 */
@Composable
fun NotificationsExample() {
    var message by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        label = "Status notifications panel",
    ) {
        Row(
            horizontalArrangement =
                Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
        ) {
            Badge(
                text = "\u2022 Syncing",
                variant = BadgeVariant.Default,
            )
            Badge(
                text = "\u2022 Updating",
                variant = BadgeVariant.Secondary,
            )
            Badge(
                text = "\u2022 Loading",
                variant = BadgeVariant.Outline,
            )
        }

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        Separator()

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Input(
                value = message,
                onValueChange = { message = it },
                placeholder = "Send a message...",
                modifier = Modifier.weight(1f),
                label = "Message input",
            )
            Button(
                onClick = { },
                size = ButtonSize.Icon,
                label = "Send message",
            ) {
                Text(
                    text = ">",
                    variant = TextVariant.Small,
                    color = RikkaTheme.colors.primaryForeground,
                )
            }
        }
    }
}
