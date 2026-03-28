package zed.rainxch.rikkaui.showcase.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.card.CardContent
import zed.rainxch.rikkaui.components.ui.card.CardFooter
import zed.rainxch.rikkaui.components.ui.card.CardHeader
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.input.Input
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

/**
 * Mini file browser card example for the showcase mosaic grid.
 *
 * Demonstrates Card, Icon, Input, Button (Icon size), and Separator
 * in a file explorer pattern.
 */
@Composable
fun FileExplorerExample() {
    var searchQuery by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        label = "Project file browser",
    ) {
        CardHeader {
            Text(
                text = "Project Files",
                variant = TextVariant.H4,
            )
        }

        CardContent {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = RikkaIcons.Edit,
                    contentDescription = "Kotlin file",
                    tint = RikkaTheme.colors.foreground,
                    modifier = Modifier.size(16.dp),
                )
                Text(
                    text = "App.kt",
                    variant = TextVariant.P,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = "2.4 KB",
                    variant = TextVariant.Muted,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = RikkaIcons.Settings,
                    contentDescription = "Build file",
                    tint = RikkaTheme.colors.foreground,
                    modifier = Modifier.size(16.dp),
                )
                Text(
                    text = "build.gradle",
                    variant = TextVariant.P,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = "1.8 KB",
                    variant = TextVariant.Muted,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = RikkaIcons.Download,
                    contentDescription = "Readme file",
                    tint = RikkaTheme.colors.foreground,
                    modifier = Modifier.size(16.dp),
                )
                Text(
                    text = "README.md",
                    variant = TextVariant.P,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = "856 B",
                    variant = TextVariant.Muted,
                )
            }
        }

        Separator()

        CardFooter {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Input(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = "Search files...",
                    label = "File search",
                    modifier = Modifier.weight(1f),
                )
                Button(
                    onClick = { },
                    size = ButtonSize.Icon,
                    label = "Search",
                ) {
                    Icon(
                        imageVector = RikkaIcons.Search,
                        contentDescription = "Search files",
                        tint = RikkaTheme.colors.primaryForeground,
                        modifier = Modifier.size(16.dp),
                    )
                }
            }

            Text(
                text = "3 files",
                variant = TextVariant.Muted,
            )
        }
    }
}
