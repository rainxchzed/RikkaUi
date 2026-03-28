package zed.rainxch.rikkaui.creator.preview.examples

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.alert.Alert
import zed.rainxch.rikkaui.components.ui.alert.AlertDescription
import zed.rainxch.rikkaui.components.ui.alert.AlertVariant
import zed.rainxch.rikkaui.components.ui.avatar.Avatar
import zed.rainxch.rikkaui.components.ui.avatar.AvatarSize
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
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

// ─── 11. ExampleTaskList ────────────────────────────────────

@Composable
fun ExampleTaskList(modifier: Modifier = Modifier) {
    var task1 by remember { mutableStateOf(true) }
    var task2 by remember { mutableStateOf(true) }
    var task3 by remember { mutableStateOf(false) }
    var task4 by remember { mutableStateOf(false) }
    var task5 by remember { mutableStateOf(true) }

    Card(modifier = modifier) {
        Text(text = "Sprint Tasks", variant = TextVariant.H4)
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        Text(
            text = "3 of 5 completed",
            variant = TextVariant.Muted,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))
        Progress(
            progress = 0.6f,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))
        Column(
            verticalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.sm),
        ) {
            TaskRow(
                checked = task1,
                onCheckedChange = { task1 = it },
                label = "Setup CI pipeline",
                badgeText = "Done",
                badgeVariant = BadgeVariant.Default,
            )
            TaskRow(
                checked = task2,
                onCheckedChange = { task2 = it },
                label = "Write API docs",
                badgeText = "Done",
                badgeVariant = BadgeVariant.Default,
            )
            TaskRow(
                checked = task3,
                onCheckedChange = { task3 = it },
                label = "Deploy to staging",
                badgeText = "In Progress",
                badgeVariant = BadgeVariant.Secondary,
            )
            TaskRow(
                checked = task4,
                onCheckedChange = { task4 = it },
                label = "Code review",
                badgeText = "Pending",
                badgeVariant = BadgeVariant.Outline,
            )
            TaskRow(
                checked = task5,
                onCheckedChange = { task5 = it },
                label = "Fix auth bug",
                badgeText = "Done",
                badgeVariant = BadgeVariant.Default,
            )
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
        Text(text = "API Keys", variant = TextVariant.H4)
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        Text(
            text = "Manage your application keys.",
            variant = TextVariant.Muted,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))
        Column(
            verticalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.sm),
        ) {
            ApiKeyRow(name = "Production", masked = "sk-...a3f9")
            ApiKeyRow(name = "Staging", masked = "sk-...7b2e")
            ApiKeyRow(
                name = "Development",
                masked = "sk-...1d4c",
            )
        }

        Spacer(Modifier.height(RikkaTheme.spacing.md))
        Alert(variant = AlertVariant.Destructive) {
            AlertDescription(
                text = "Deleted keys cannot be recovered.",
                variant = AlertVariant.Destructive,
            )
        }
    }
}

@Composable
private fun ApiKeyRow(name: String, masked: String) {
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
        Button(
            onClick = {},
            variant = ButtonVariant.Ghost,
            size = ButtonSize.Icon,
        ) {
            Icon(
                imageVector = RikkaIcons.Copy,
                contentDescription = "Copy $name key",
            )
        }
        Button(
            onClick = {},
            variant = ButtonVariant.Ghost,
            size = ButtonSize.Icon,
        ) {
            Icon(
                imageVector = RikkaIcons.Trash,
                contentDescription = "Delete $name key",
            )
        }
    }
}

// ─── 13. ExampleChatComposer ────────────────────────────────

@Composable
fun ExampleChatComposer(modifier: Modifier = Modifier) {
    var messageText by remember { mutableStateOf("") }

    Card(modifier = modifier) {
        Text(text = "Messages", variant = TextVariant.H4)

        Spacer(Modifier.height(RikkaTheme.spacing.md))
        Column(
            verticalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.sm),
        ) {
            // Other person's message (left-aligned)
            ChatBubble(
                text = "Hey, how's the project going?",
                isFromUser = false,
            )
            // Other person's message (left-aligned)
            ChatBubble(
                text = "Did you push the latest changes?",
                isFromUser = false,
            )
            // User's message (right-aligned)
            ChatBubble(
                text = "Yes! Just deployed to staging.",
                isFromUser = true,
            )
        }

        Spacer(Modifier.height(RikkaTheme.spacing.md))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.sm),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Input(
                value = messageText,
                onValueChange = { messageText = it },
                placeholder = "Type a message...",
                label = "Message input",
                modifier = Modifier.weight(1f),
            )
            Button(
                onClick = {},
                size = ButtonSize.Icon,
            ) {
                Icon(
                    imageVector = RikkaIcons.Send,
                    contentDescription = "Send message",
                )
            }
        }
    }
}

@Composable
private fun ChatBubble(text: String, isFromUser: Boolean) {
    val bgColor = if (isFromUser) {
        RikkaTheme.colors.primary
    } else {
        RikkaTheme.colors.muted
    }
    val textColor = if (isFromUser) {
        RikkaTheme.colors.primaryForeground
    } else {
        RikkaTheme.colors.foreground
    }
    val alignment = if (isFromUser) {
        Alignment.CenterEnd
    } else {
        Alignment.CenterStart
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = alignment,
    ) {
        Box(
            modifier = Modifier
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
        Text(text = "Documents", variant = TextVariant.H4)
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        Text(
            text = "Recent files",
            variant = TextVariant.Muted,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))
        Column {
            FileRow(
                icon = RikkaIcons.Edit,
                name = "Project Brief.doc",
                size = "2.4 MB",
            )
            Separator()
            FileRow(
                icon = RikkaIcons.Settings,
                name = "app.config",
                size = "12 KB",
            )
            Separator()
            FileRow(
                icon = RikkaIcons.Download,
                name = "assets-v2.zip",
                size = "8.1 MB",
            )
            Separator()
            FileRow(
                icon = RikkaIcons.Edit,
                name = "Meeting Notes.doc",
                size = "1.8 MB",
            )
        }

        Spacer(Modifier.height(RikkaTheme.spacing.md))
        Text(
            text = "4 files \u00B7 12.3 MB",
            variant = TextVariant.Muted,
        )
    }
}

@Composable
private fun FileRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    name: String,
    size: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = RikkaTheme.spacing.sm),
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = RikkaTheme.colors.mutedForeground,
            modifier = Modifier.size(16.dp),
        )
        Text(text = name, variant = TextVariant.Small)
        Spacer(Modifier.weight(1f))
        Text(
            text = size,
            variant = TextVariant.Small,
            color = RikkaTheme.colors.mutedForeground,
        )
        Button(
            onClick = {},
            variant = ButtonVariant.Ghost,
            size = ButtonSize.Icon,
        ) {
            Icon(
                imageVector = RikkaIcons.MoreHorizontal,
                contentDescription = "File options",
            )
        }
    }
}

// ─── 15. ExampleUserDirectory ───────────────────────────────

@Composable
fun ExampleUserDirectory(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "Team", variant = TextVariant.H4)
            Badge(text = "8 members")
        }

        Spacer(Modifier.height(RikkaTheme.spacing.md))
        Column(
            verticalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.sm),
        ) {
            UserRow(
                initials = "AC",
                name = "Alice Chen",
                role = "Engineering Lead",
                status = "Active",
                statusVariant = BadgeVariant.Default,
            )
            UserRow(
                initials = "BK",
                name = "Bob Kim",
                role = "Designer",
                status = "Active",
                statusVariant = BadgeVariant.Default,
            )
            UserRow(
                initials = "CD",
                name = "Carol Davis",
                role = "PM",
                status = "Away",
                statusVariant = BadgeVariant.Secondary,
            )
            UserRow(
                initials = "DP",
                name = "Dan Park",
                role = "Intern",
                status = "Offline",
                statusVariant = BadgeVariant.Outline,
            )
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "Pro Plan", variant = TextVariant.H4)
            Badge(text = "Popular")
        }

        Spacer(Modifier.height(RikkaTheme.spacing.sm))
        Text(text = "$29/month", variant = TextVariant.H2)
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        Text(
            text = "Everything you need to scale.",
            variant = TextVariant.Muted,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))
        Column(
            verticalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.sm),
        ) {
            FeatureItem(text = "Unlimited projects")
            FeatureItem(text = "Priority support")
            FeatureItem(text = "Advanced analytics")
            FeatureItem(text = "Custom domains")
            FeatureItem(text = "API access")
        }

        Spacer(Modifier.height(RikkaTheme.spacing.lg))
        Button(
            text = "Upgrade to Pro",
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(RikkaTheme.spacing.sm))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Cancel anytime",
                variant = TextVariant.Muted,
            )
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
        Text(text = "Search", variant = TextVariant.H4)

        Spacer(Modifier.height(RikkaTheme.spacing.md))
        Input(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = "Search commands...",
            label = "Command search",
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))
        Column(
            verticalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.xs),
        ) {
            CommandRow(
                icon = RikkaIcons.Search,
                label = "Search files",
                shortcut = "Ctrl+P",
            )
            CommandRow(
                icon = RikkaIcons.Settings,
                label = "Open settings",
                shortcut = "Ctrl+,",
            )
            CommandRow(
                icon = RikkaIcons.User,
                label = "Switch account",
                shortcut = "Ctrl+K",
            )
            CommandRow(
                icon = RikkaIcons.Moon,
                label = "Toggle theme",
                shortcut = "Ctrl+T",
            )
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
        modifier = Modifier
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
            tint = RikkaTheme.colors.mutedForeground,
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
        Text(
            text = "Welcome to RikkaUI",
            variant = TextVariant.H4,
        )
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        Text(
            text = "Let's get you set up in a few steps.",
            variant = TextVariant.Muted,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))
        Column(
            verticalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.sm),
        ) {
            OnboardingStep(
                number = "1",
                title = "Create your project",
                isCompleted = true,
            )
            OnboardingStep(
                number = "2",
                title = "Configure theme",
                isCompleted = true,
            )
            OnboardingStep(
                number = "3",
                title = "Add components",
                isCompleted = false,
            )
        }

        Spacer(Modifier.height(RikkaTheme.spacing.md))
        Progress(
            progress = 0.66f,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))
        Button(
            text = "Continue Setup",
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        )
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
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(RikkaTheme.colors.primary),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = number,
                variant = TextVariant.Small,
                color = RikkaTheme.colors.primaryForeground,
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
                contentDescription = "Completed",
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
        Text(text = "Activity", variant = TextVariant.H4)
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        Text(
            text = "Recent events",
            variant = TextVariant.Muted,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))
        Column(
            verticalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            ActivityEntry(
                text = "Deployed v2.1.0 to production",
                timestamp = "2 min ago",
            )
            ActivityEntry(
                text = "Merged PR #142",
                timestamp = "15 min ago",
            )
            ActivityEntry(
                text = "Updated environment variables",
                timestamp = "1 hour ago",
            )
            ActivityEntry(
                text = "Added team member",
                timestamp = "3 hours ago",
            )
            ActivityEntry(
                text = "Created new API key",
                timestamp = "Yesterday",
            )
        }
    }
}

@Composable
private fun ActivityEntry(text: String, timestamp: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier
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
                color = RikkaTheme.colors.mutedForeground,
            )
        }
    }
}

// ─── 20. ExampleQuickNote ───────────────────────────────────

@Composable
fun ExampleQuickNote(modifier: Modifier = Modifier) {
    var noteText by remember {
        mutableStateOf(
            "Remember to update the API docs before" +
                " the release. Also check the migration" +
                " guide for breaking changes.",
        )
    }
    var selectedFormat by remember { mutableStateOf("Bold") }

    Card(modifier = modifier) {
        Text(text = "Quick Note", variant = TextVariant.H4)

        Spacer(Modifier.height(RikkaTheme.spacing.md))
        Textarea(
            value = noteText,
            onValueChange = { noteText = it },
            placeholder = "Write something...",
            label = "Note content",
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))
        ToggleGroup {
            ToggleGroupItem(
                text = "Bold",
                selected = selectedFormat == "Bold",
                onClick = { selectedFormat = "Bold" },
            )
            ToggleGroupItem(
                text = "Italic",
                selected = selectedFormat == "Italic",
                onClick = { selectedFormat = "Italic" },
            )
            ToggleGroupItem(
                text = "Code",
                selected = selectedFormat == "Code",
                onClick = { selectedFormat = "Code" },
            )
        }

        Spacer(Modifier.height(RikkaTheme.spacing.sm))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Badge(
                text = "Auto-saved",
                variant = BadgeVariant.Secondary,
            )
            Text(
                text = "Last edited 2 min ago",
                variant = TextVariant.Muted,
            )
        }
    }
}
