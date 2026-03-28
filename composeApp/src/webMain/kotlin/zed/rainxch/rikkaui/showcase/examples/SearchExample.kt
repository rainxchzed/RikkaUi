package zed.rainxch.rikkaui.showcase.examples

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.input.Input
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

/**
 * Search input example with results count and URL bar.
 *
 * Demonstrates [Input], [Badge], [Button], [Card], and [Separator]
 * composed together in a realistic search + URL entry layout.
 */
@Composable
fun SearchExample() {
    var searchQuery by remember { mutableStateOf("") }
    var urlValue by remember { mutableStateOf("") }

    Card {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement =
                Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
        ) {
            Input(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = "Search...",
                label = "Search components",
                modifier = Modifier.weight(1f),
            )
            Badge(
                text = "12 results",
                variant = BadgeVariant.Secondary,
            )
        }

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))
        Separator()
        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement =
                Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
        ) {
            Input(
                value = urlValue,
                onValueChange = { urlValue = it },
                placeholder = "https:// example.com",
                label = "URL input",
                modifier = Modifier.weight(1f),
            )
            Button(
                onClick = { },
                variant = ButtonVariant.Outline,
                size = ButtonSize.Sm,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = "Go",
                        variant = TextVariant.Small,
                    )
                    Text(
                        text = "⌘K",
                        variant = TextVariant.Muted,
                        modifier =
                            Modifier
                                .background(
                                    color = RikkaTheme.colors.muted,
                                    shape = RoundedCornerShape(4.dp),
                                ).padding(
                                    horizontal = 4.dp,
                                    vertical = 1.dp,
                                ),
                    )
                }
            }
        }
    }
}
