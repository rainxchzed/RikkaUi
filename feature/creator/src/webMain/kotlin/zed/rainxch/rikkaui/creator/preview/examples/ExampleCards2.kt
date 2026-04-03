package zed.rainxch.rikkaui.creator.preview.examples

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.creator.generated.resources.*
import rikkaui.feature.creator.generated.resources.Res
import zed.rainxch.rikkaui.components.ui.alert.Alert
import zed.rainxch.rikkaui.components.ui.alert.AlertDescription
import zed.rainxch.rikkaui.components.ui.alert.AlertVariant
import zed.rainxch.rikkaui.components.ui.avatar.Avatar
import zed.rainxch.rikkaui.components.ui.avatar.AvatarSize
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.IconButton
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.checkbox.Checkbox
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.input.Input
import zed.rainxch.rikkaui.components.ui.kbd.Kbd
import zed.rainxch.rikkaui.components.ui.progress.Progress
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.textarea.Textarea
import zed.rainxch.rikkaui.components.ui.togglegroup.ToggleGroup
import zed.rainxch.rikkaui.components.ui.togglegroup.ToggleGroupItem
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── 11. ExampleTaskList ────────────────────────────────────

@Composable
fun ExampleTaskList(modifier: Modifier = Modifier) {
    var task1 by remember { mutableStateOf(true) }
    var task2 by remember { mutableStateOf(true) }
    var task3 by remember { mutableStateOf(false) }
    var task4 by remember { mutableStateOf(false) }
    var task5 by remember { mutableStateOf(true) }

    Card(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
            ) {
                Text(text = stringResource(Res.string.example_tasks_title), variant = TextVariant.H4)
                Text(
                    text = stringResource(Res.string.example_tasks_progress),
                    variant = TextVariant.Muted,
                )
            }

            Progress(
                progress = 0.6f,
                modifier = Modifier.fillMaxWidth(),
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
            ) {
                TaskRow(
                    checked = task1,
                    onCheckedChange = { task1 = it },
                    label = stringResource(Res.string.example_tasks_ci),
                    badgeText = stringResource(Res.string.example_tasks_done),
                    badgeVariant = BadgeVariant.Default,
                )
                TaskRow(
                    checked = task2,
                    onCheckedChange = { task2 = it },
                    label = stringResource(Res.string.example_tasks_api_docs),
                    badgeText = stringResource(Res.string.example_tasks_done),
                    badgeVariant = BadgeVariant.Default,
                )
                TaskRow(
                    checked = task3,
                    onCheckedChange = { task3 = it },
                    label = stringResource(Res.string.example_tasks_staging),
                    badgeText = stringResource(Res.string.example_tasks_in_progress),
                    badgeVariant = BadgeVariant.Secondary,
                )
                TaskRow(
                    checked = task4,
                    onCheckedChange = { task4 = it },
                    label = stringResource(Res.string.example_tasks_review),
                    badgeText = stringResource(Res.string.example_tasks_pending),
                    badgeVariant = BadgeVariant.Outline,
                )
                TaskRow(
                    checked = task5,
                    onCheckedChange = { task5 = it },
                    label = stringResource(Res.string.example_tasks_auth_bug),
                    badgeText = stringResource(Res.string.example_tasks_done),
                    badgeVariant = BadgeVariant.Default,
                )
            }
        }
    }
}

@Composable
private fun TaskRow(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    badgeText: String,
    badgeVariant: BadgeVariant,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            label = label,
        )
        Spacer(Modifier.weight(1f))
        Badge(text = badgeText, variant = badgeVariant)
    }
}

// ─── 12. ExampleApiKeyManager ───────────────────────────────

@Composable
fun ExampleApiKeyManager(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
            ) {
                Text(text = stringResource(Res.string.example_api_title), variant = TextVariant.H4)
                Text(
                    text = stringResource(Res.string.example_api_description),
                    variant = TextVariant.Muted,
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
            ) {
                ApiKeyRow(
                    name = stringResource(Res.string.example_api_production),
                    masked = stringResource(Res.string.example_api_masked_a3f9),
                )
                ApiKeyRow(
                    name = stringResource(Res.string.example_api_staging),
                    masked = stringResource(Res.string.example_api_masked_7b2e),
                )
                ApiKeyRow(
                    name = stringResource(Res.string.example_api_development),
                    masked = stringResource(Res.string.example_api_masked_1d4c),
                )
            }

            Alert(variant = AlertVariant.Destructive) {
                AlertDescription(
                    text = stringResource(Res.string.example_api_deleted_warning),
                    variant = AlertVariant.Destructive,
                )
            }
        }
    }
}

@Composable
private fun ApiKeyRow(
    name: String,
    masked: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = name,
            variant = TextVariant.Small,
        )
        Spacer(Modifier.weight(1f))
        Kbd(text = masked)
        IconButton(
            icon = RikkaIcons.Copy,
            contentDescription = stringResource(Res.string.example_api_copy_key, name),
            onClick = {},
        )
        IconButton(
            icon = RikkaIcons.Trash,
            contentDescription = stringResource(Res.string.example_api_delete_key, name),
            onClick = {},
        )
    }
}

// ─── 13. ExampleChatComposer ────────────────────────────────

@Composable
fun ExampleChatComposer(modifier: Modifier = Modifier) {
    var messageText by remember { mutableStateOf("") }

    Card(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Text(text = stringResource(Res.string.example_chat_title), variant = TextVariant.H4)

            Column(
                verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
            ) {
                ChatBubble(
                    text = stringResource(Res.string.example_chat_msg1),
                    isFromUser = false,
                )
                ChatBubble(
                    text = stringResource(Res.string.example_chat_msg2),
                    isFromUser = false,
                )
                ChatBubble(
                    text = stringResource(Res.string.example_chat_msg3),
                    isFromUser = true,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Input(
                    value = messageText,
                    onValueChange = { messageText = it },
                    placeholder = stringResource(Res.string.example_chat_input_placeholder),
                    label = stringResource(Res.string.example_chat_input_label),
                    modifier = Modifier.weight(1f),
                )
                IconButton(
                    icon = RikkaIcons.Send,
                    contentDescription = stringResource(Res.string.example_chat_send),
                    onClick = {},
                )
            }
        }
    }
}

@Composable
private fun ChatBubble(
    text: String,
    isFromUser: Boolean,
) {
    val bgColor =
        if (isFromUser) {
            RikkaTheme.colors.primary
        } else {
            RikkaTheme.colors.muted
        }
    val textColor =
        if (isFromUser) {
            RikkaTheme.colors.onPrimary
        } else {
            RikkaTheme.colors.onBackground
        }
    val alignment =
        if (isFromUser) {
            Alignment.CenterEnd
        } else {
            Alignment.CenterStart
        }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = alignment,
    ) {
        Box(
            modifier =
                Modifier
                    .clip(RikkaTheme.shapes.md)
                    .background(bgColor)
                    .padding(
                        horizontal = RikkaTheme.spacing.md,
                        vertical = RikkaTheme.spacing.sm,
                    ),
        ) {
            Text(
                text = text,
                variant = TextVariant.Small,
                color = textColor,
            )
        }
    }
}

// ─── 14. ExampleFileManager ─────────────────────────────────

@Composable
fun ExampleFileManager(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
            ) {
                Text(text = stringResource(Res.string.example_files_title), variant = TextVariant.H4)
                Text(
                    text = stringResource(Res.string.example_files_subtitle),
                    variant = TextVariant.Muted,
                )
            }

            Column {
                FileRow(
                    icon = RikkaIcons.Edit,
                    name = stringResource(Res.string.example_files_name1),
                    size = stringResource(Res.string.example_files_size1),
                )
                Separator()
                FileRow(
                    icon = RikkaIcons.Settings,
                    name = stringResource(Res.string.example_files_name2),
                    size = stringResource(Res.string.example_files_size2),
                )
                Separator()
                FileRow(
                    icon = RikkaIcons.Download,
                    name = stringResource(Res.string.example_files_name3),
                    size = stringResource(Res.string.example_files_size3),
                )
                Separator()
                FileRow(
                    icon = RikkaIcons.Edit,
                    name = stringResource(Res.string.example_files_name4),
                    size = stringResource(Res.string.example_files_size4),
                )
            }

            Text(
                text = stringResource(Res.string.example_files_summary),
                variant = TextVariant.Muted,
            )
        }
    }
}

@Composable
private fun FileRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    name: String,
    size: String,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = RikkaTheme.spacing.sm),
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = RikkaTheme.colors.onMuted,
            modifier = Modifier.size(16.dp),
        )
        Text(text = name, variant = TextVariant.Small)
        Spacer(Modifier.weight(1f))
        Text(
            text = size,
            variant = TextVariant.Small,
            color = RikkaTheme.colors.onMuted,
        )
        IconButton(
            icon = RikkaIcons.MoreHorizontal,
            contentDescription = stringResource(Res.string.example_files_options),
            onClick = {},
        )
    }
}

// ─── 15. ExampleUserDirectory ───────────────────────────────

@Composable
fun ExampleUserDirectory(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = stringResource(Res.string.example_team_title), variant = TextVariant.H4)
                Badge(text = stringResource(Res.string.example_team_count))
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
            ) {
                UserRow(
                    initials = "AC",
                    name = stringResource(Res.string.example_team_name1),
                    role = stringResource(Res.string.example_team_role1),
                    status = stringResource(Res.string.example_team_status_active),
                    statusVariant = BadgeVariant.Default,
                )
                UserRow(
                    initials = "BK",
                    name = stringResource(Res.string.example_team_name2),
                    role = stringResource(Res.string.example_team_role2),
                    status = stringResource(Res.string.example_team_status_active),
                    statusVariant = BadgeVariant.Default,
                )
                UserRow(
                    initials = "CD",
                    name = stringResource(Res.string.example_team_name3),
                    role = stringResource(Res.string.example_team_role3),
                    status = stringResource(Res.string.example_team_status_away),
                    statusVariant = BadgeVariant.Secondary,
                )
                UserRow(
                    initials = "DP",
                    name = stringResource(Res.string.example_team_name4),
                    role = stringResource(Res.string.example_team_role4),
                    status = stringResource(Res.string.example_team_status_offline),
                    statusVariant = BadgeVariant.Outline,
                )
            }
        }
    }
}

@Composable
private fun UserRow(
    initials: String,
    name: String,
    role: String,
    status: String,
    statusVariant: BadgeVariant,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Avatar(fallback = initials, size = AvatarSize.Sm)
        Column(modifier = Modifier.weight(1f)) {
            Text(text = name, variant = TextVariant.Small)
            Text(
                text = role,
                variant = TextVariant.Muted,
            )
        }
        Badge(text = status, variant = statusVariant)
    }
}

// ─── 16. ExamplePricingCard ─────────────────────────────────

@Composable
fun ExamplePricingCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = stringResource(Res.string.example_pricing_title), variant = TextVariant.H4)
                Badge(text = stringResource(Res.string.example_pricing_popular))
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
            ) {
                Text(text = stringResource(Res.string.example_pricing_price), variant = TextVariant.H2)
                Text(
                    text = stringResource(Res.string.example_pricing_description),
                    variant = TextVariant.Muted,
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
            ) {
                FeatureItem(text = stringResource(Res.string.example_pricing_feature1))
                FeatureItem(text = stringResource(Res.string.example_pricing_feature2))
                FeatureItem(text = stringResource(Res.string.example_pricing_feature3))
                FeatureItem(text = stringResource(Res.string.example_pricing_feature4))
                FeatureItem(text = stringResource(Res.string.example_pricing_feature5))
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
            ) {
                Button(
                    text = stringResource(Res.string.example_pricing_upgrade),
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                )
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(Res.string.example_pricing_cancel),
                        variant = TextVariant.Muted,
                    )
                }
            }
        }
    }
}

@Composable
private fun FeatureItem(text: String) {
    Row(
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = RikkaIcons.Check,
            contentDescription = null,
            tint = RikkaTheme.colors.primary,
            modifier = Modifier.size(16.dp),
        )
        Text(text = text, variant = TextVariant.Small)
    }
}

// ─── 17. ExampleSearchCommand ───────────────────────────────

@Composable
fun ExampleSearchCommand(modifier: Modifier = Modifier) {
    var searchText by remember { mutableStateOf("") }

    Card(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Text(text = stringResource(Res.string.example_search_title), variant = TextVariant.H4)

            Input(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = stringResource(Res.string.example_search_placeholder),
                label = stringResource(Res.string.example_search_label),
                modifier = Modifier.fillMaxWidth(),
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
            ) {
                CommandRow(
                    icon = RikkaIcons.Search,
                    label = stringResource(Res.string.example_search_files),
                    shortcut = stringResource(Res.string.example_search_files_shortcut),
                )
                CommandRow(
                    icon = RikkaIcons.Settings,
                    label = stringResource(Res.string.example_search_settings),
                    shortcut = stringResource(Res.string.example_search_settings_shortcut),
                )
                CommandRow(
                    icon = RikkaIcons.User,
                    label = stringResource(Res.string.example_search_account),
                    shortcut = stringResource(Res.string.example_search_account_shortcut),
                )
                CommandRow(
                    icon = RikkaIcons.Moon,
                    label = stringResource(Res.string.example_search_theme),
                    shortcut = stringResource(Res.string.example_search_theme_shortcut),
                )
            }
        }
    }
}

@Composable
private fun CommandRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    shortcut: String,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(RikkaTheme.shapes.sm)
                .background(RikkaTheme.colors.muted.copy(alpha = 0.3f))
                .padding(
                    horizontal = RikkaTheme.spacing.sm,
                    vertical = RikkaTheme.spacing.sm,
                ),
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = RikkaTheme.colors.onMuted,
            modifier = Modifier.size(16.dp),
        )
        Text(
            text = label,
            variant = TextVariant.Small,
        )
        Spacer(Modifier.weight(1f))
        Kbd(text = shortcut)
    }
}

// ─── 18. ExampleOnboarding ──────────────────────────────────

@Composable
fun ExampleOnboarding(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
            ) {
                Text(
                    text = stringResource(Res.string.example_onboarding_title),
                    variant = TextVariant.H4,
                )
                Text(
                    text = stringResource(Res.string.example_onboarding_description),
                    variant = TextVariant.Muted,
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
            ) {
                OnboardingStep(
                    number = "1",
                    title = stringResource(Res.string.example_onboarding_step1),
                    isCompleted = true,
                )
                OnboardingStep(
                    number = "2",
                    title = stringResource(Res.string.example_onboarding_step2),
                    isCompleted = true,
                )
                OnboardingStep(
                    number = "3",
                    title = stringResource(Res.string.example_onboarding_step3),
                    isCompleted = false,
                )
            }

            Progress(
                progress = 0.66f,
                modifier = Modifier.fillMaxWidth(),
            )

            Button(
                text = stringResource(Res.string.example_onboarding_continue),
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun OnboardingStep(
    number: String,
    title: String,
    isCompleted: Boolean,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(RikkaTheme.colors.primary),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = number,
                variant = TextVariant.Small,
                color = RikkaTheme.colors.onPrimary,
            )
        }
        Text(
            text = title,
            variant = TextVariant.Small,
            modifier = Modifier.weight(1f),
        )
        if (isCompleted) {
            Icon(
                imageVector = RikkaIcons.Check,
                contentDescription = stringResource(Res.string.example_onboarding_completed),
                tint = RikkaTheme.colors.primary,
                modifier = Modifier.size(16.dp),
            )
        }
    }
}

// ─── 19. ExampleActivityLog ─────────────────────────────────

@Composable
fun ExampleActivityLog(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
            ) {
                Text(text = stringResource(Res.string.example_activity_title), variant = TextVariant.H4)
                Text(
                    text = stringResource(Res.string.example_activity_subtitle),
                    variant = TextVariant.Muted,
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
            ) {
                ActivityEntry(
                    text = stringResource(Res.string.example_activity_event1),
                    timestamp = stringResource(Res.string.example_activity_time1),
                )
                ActivityEntry(
                    text = stringResource(Res.string.example_activity_event2),
                    timestamp = stringResource(Res.string.example_activity_time2),
                )
                ActivityEntry(
                    text = stringResource(Res.string.example_activity_event3),
                    timestamp = stringResource(Res.string.example_activity_time3),
                )
                ActivityEntry(
                    text = stringResource(Res.string.example_activity_event4),
                    timestamp = stringResource(Res.string.example_activity_time4),
                )
                ActivityEntry(
                    text = stringResource(Res.string.example_activity_event5),
                    timestamp = stringResource(Res.string.example_activity_time5),
                )
            }
        }
    }
}

@Composable
private fun ActivityEntry(
    text: String,
    timestamp: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier =
                Modifier
                    .padding(top = 6.dp)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(RikkaTheme.colors.primary),
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(text = text, variant = TextVariant.Small)
            Text(
                text = timestamp,
                variant = TextVariant.Small,
                color = RikkaTheme.colors.onMuted,
            )
        }
    }
}

// ─── 20. ExampleQuickNote ───────────────────────────────────

@Composable
fun ExampleQuickNote(modifier: Modifier = Modifier) {
    val defaultNote = stringResource(Res.string.example_note_default_text)
    var noteText by remember { mutableStateOf(defaultNote) }
    val boldLabel = stringResource(Res.string.example_note_bold)
    var selectedFormat by remember { mutableStateOf(boldLabel) }

    Card(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Text(text = stringResource(Res.string.example_note_title), variant = TextVariant.H4)

            Textarea(
                value = noteText,
                onValueChange = { noteText = it },
                placeholder = stringResource(Res.string.example_note_placeholder),
                label = stringResource(Res.string.example_note_label),
                modifier = Modifier.fillMaxWidth(),
            )

            val italicLabel = stringResource(Res.string.example_note_italic)
            val codeLabel = stringResource(Res.string.example_note_code)
            ToggleGroup {
                ToggleGroupItem(
                    text = boldLabel,
                    selected = selectedFormat == boldLabel,
                    onClick = { selectedFormat = boldLabel },
                )
                ToggleGroupItem(
                    text = italicLabel,
                    selected = selectedFormat == italicLabel,
                    onClick = { selectedFormat = italicLabel },
                )
                ToggleGroupItem(
                    text = codeLabel,
                    selected = selectedFormat == codeLabel,
                    onClick = { selectedFormat = codeLabel },
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Badge(
                    text = stringResource(Res.string.example_note_auto_saved),
                    variant = BadgeVariant.Secondary,
                )
                Text(
                    text = stringResource(Res.string.example_note_last_edited),
                    variant = TextVariant.Muted,
                )
            }
        }
    }
}
