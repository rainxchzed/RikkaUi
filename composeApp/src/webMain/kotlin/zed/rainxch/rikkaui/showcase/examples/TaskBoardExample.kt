package zed.rainxch.rikkaui.showcase.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import rikkaui.composeapp.generated.resources.Res
import rikkaui.composeapp.generated.resources.active_count
import rikkaui.composeapp.generated.resources.assigned_to_you
import rikkaui.composeapp.generated.resources.design_token_system
import rikkaui.composeapp.generated.resources.high
import rikkaui.composeapp.generated.resources.low
import rikkaui.composeapp.generated.resources.medium
import rikkaui.composeapp.generated.resources.sprint_tasks
import rikkaui.composeapp.generated.resources.task_board_label
import rikkaui.composeapp.generated.resources.update_docs
import rikkaui.composeapp.generated.resources.view_board
import rikkaui.composeapp.generated.resources.write_unit_tests
import zed.rainxch.rikkaui.components.ui.avatar.Avatar
import zed.rainxch.rikkaui.components.ui.avatar.AvatarSize
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.card.CardContent
import zed.rainxch.rikkaui.components.ui.card.CardFooter
import zed.rainxch.rikkaui.components.ui.card.CardHeader
import zed.rainxch.rikkaui.components.ui.checkbox.Checkbox
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme

@Composable
fun TaskBoardExample() {
    var task1Checked by remember { mutableStateOf(false) }
    var task2Checked by remember { mutableStateOf(false) }
    var task3Checked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        label = stringResource(Res.string.task_board_label),
    ) {
        CardHeader {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = stringResource(Res.string.sprint_tasks), variant = TextVariant.H4)
                Badge(text = stringResource(Res.string.active_count), variant = BadgeVariant.Default)
            }
        }

        CardContent {
            Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = task1Checked,
                        onCheckedChange = { task1Checked = it },
                        label = stringResource(Res.string.design_token_system),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Badge(text = stringResource(Res.string.high), variant = BadgeVariant.Destructive)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = task2Checked,
                        onCheckedChange = { task2Checked = it },
                        label = stringResource(Res.string.write_unit_tests),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Badge(text = stringResource(Res.string.medium), variant = BadgeVariant.Secondary)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = task3Checked,
                        onCheckedChange = { task3Checked = it },
                        label = stringResource(Res.string.update_docs),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Badge(text = stringResource(Res.string.low), variant = BadgeVariant.Outline)
                }
            }
        }

        Separator()

        CardFooter {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Avatar(fallback = "AK", size = AvatarSize.Sm)
                Text(
                    text = stringResource(Res.string.assigned_to_you),
                    variant = TextVariant.Small,
                    color = RikkaTheme.colors.mutedForeground,
                    modifier = Modifier.weight(1f),
                )
                Button(
                    text = stringResource(Res.string.view_board),
                    onClick = { },
                    variant = ButtonVariant.Ghost,
                )
            }
        }
    }
}
