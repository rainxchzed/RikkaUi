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
import zed.rainxch.rikkaui.components.theme.RikkaTheme
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

/**
 * Kanban-style task board card example for the showcase mosaic grid.
 *
 * Demonstrates Card with header, Checkbox, Badge variants, Avatar,
 * Separator, and Button in a sprint task tracker pattern.
 */
@Composable
fun TaskBoardExample() {
    var task1Checked by remember { mutableStateOf(false) }
    var task2Checked by remember { mutableStateOf(false) }
    var task3Checked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        label = "Sprint task board card",
    ) {
        CardHeader {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Sprint Tasks",
                    variant = TextVariant.H4,
                )
                Badge(
                    text = "3 active",
                    variant = BadgeVariant.Default,
                )
            }
        }

        CardContent {
            Column(
                verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = task1Checked,
                        onCheckedChange = { task1Checked = it },
                        label = "Design token system",
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Badge(text = "High", variant = BadgeVariant.Destructive)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = task2Checked,
                        onCheckedChange = { task2Checked = it },
                        label = "Write unit tests",
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Badge(text = "Medium", variant = BadgeVariant.Secondary)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = task3Checked,
                        onCheckedChange = { task3Checked = it },
                        label = "Update docs",
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Badge(text = "Low", variant = BadgeVariant.Outline)
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
                Avatar(
                    fallback = "AK",
                    size = AvatarSize.Sm,
                )
                Text(
                    text = "Assigned to you",
                    variant = TextVariant.Small,
                    color = RikkaTheme.colors.mutedForeground,
                    modifier = Modifier.weight(1f),
                )
                Button(
                    text = "View Board",
                    onClick = { },
                    variant = ButtonVariant.Ghost,
                )
            }
        }
    }
}
