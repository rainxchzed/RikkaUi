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
import org.jetbrains.compose.resources.stringResource
import rikkaui.composeapp.generated.resources.Res
import rikkaui.composeapp.generated.resources.app_kt_name
import rikkaui.composeapp.generated.resources.app_kt_size
import rikkaui.composeapp.generated.resources.build_file_desc
import rikkaui.composeapp.generated.resources.build_gradle_name
import rikkaui.composeapp.generated.resources.build_gradle_size
import rikkaui.composeapp.generated.resources.file_browser_label
import rikkaui.composeapp.generated.resources.file_count
import rikkaui.composeapp.generated.resources.file_search_label
import rikkaui.composeapp.generated.resources.kotlin_file_desc
import rikkaui.composeapp.generated.resources.project_files
import rikkaui.composeapp.generated.resources.readme_file_desc
import rikkaui.composeapp.generated.resources.readme_md_name
import rikkaui.composeapp.generated.resources.readme_size
import rikkaui.composeapp.generated.resources.search_files_desc
import rikkaui.composeapp.generated.resources.search_files_placeholder
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
import zed.rainxch.rikkaui.foundation.RikkaTheme

@Composable
fun FileExplorerExample() {
    var searchQuery by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        label = stringResource(Res.string.file_browser_label),
    ) {
        CardHeader {
            Text(text = stringResource(Res.string.project_files), variant = TextVariant.H4)
        }

        CardContent {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = RikkaIcons.Edit,
                    contentDescription = stringResource(Res.string.kotlin_file_desc),
                    tint = RikkaTheme.colors.foreground,
                    modifier = Modifier.size(16.dp),
                )
                Text(text = stringResource(Res.string.app_kt_name), variant = TextVariant.P, modifier = Modifier.weight(1f))
                Text(text = stringResource(Res.string.app_kt_size), variant = TextVariant.Muted)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = RikkaIcons.Settings,
                    contentDescription = stringResource(Res.string.build_file_desc),
                    tint = RikkaTheme.colors.foreground,
                    modifier = Modifier.size(16.dp),
                )
                Text(
                    text = stringResource(Res.string.build_gradle_name),
                    variant = TextVariant.P,
                    modifier = Modifier.weight(1f),
                )
                Text(text = stringResource(Res.string.build_gradle_size), variant = TextVariant.Muted)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = RikkaIcons.Download,
                    contentDescription = stringResource(Res.string.readme_file_desc),
                    tint = RikkaTheme.colors.foreground,
                    modifier = Modifier.size(16.dp),
                )
                Text(text = stringResource(Res.string.readme_md_name), variant = TextVariant.P, modifier = Modifier.weight(1f))
                Text(text = stringResource(Res.string.readme_size), variant = TextVariant.Muted)
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
                    placeholder = stringResource(Res.string.search_files_placeholder),
                    label = stringResource(Res.string.file_search_label),
                    modifier = Modifier.weight(1f),
                )
                Button(
                    onClick = { },
                    size = ButtonSize.Icon,
                    label = stringResource(Res.string.search_files_desc),
                ) {
                    Icon(
                        imageVector = RikkaIcons.Search,
                        contentDescription = stringResource(Res.string.search_files_desc),
                        tint = RikkaTheme.colors.primaryForeground,
                        modifier = Modifier.size(16.dp),
                    )
                }
            }

            Text(text = stringResource(Res.string.file_count), variant = TextVariant.Muted)
        }
    }
}
