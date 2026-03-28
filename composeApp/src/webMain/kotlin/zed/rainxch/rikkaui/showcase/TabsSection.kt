package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.tabs.Tab
import zed.rainxch.rikkaui.components.ui.tabs.TabContent
import zed.rainxch.rikkaui.components.ui.tabs.TabList
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.togglegroup.ToggleGroup
import zed.rainxch.rikkaui.components.ui.togglegroup.ToggleGroupItem
import zed.rainxch.rikkaui.components.ui.togglegroup.ToggleGroupVariant

@Composable
fun TabsSection() {
    SectionHeader(
        title = "Tabs & Toggle Group",
        description = "Segmented navigation and grouped selections.",
    )

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    Card(modifier = Modifier.fillMaxWidth()) {
        // Tabs
        Text(text = "Tabs", variant = TextVariant.Small)
        Spacer(Modifier.height(RikkaTheme.spacing.xs))

        var selectedTab by remember { mutableStateOf(0) }

        TabList {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = "Account",
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = "Password",
            )
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                text = "Settings",
            )
        }

        TabContent {
            when (selectedTab) {
                0 ->
                    Text(
                        text = "Manage your account details and preferences.",
                        variant = TextVariant.Muted,
                    )
                1 ->
                    Text(
                        text = "Update your password and security settings.",
                        variant = TextVariant.Muted,
                    )
                2 ->
                    Text(
                        text = "Configure application settings and notifications.",
                        variant = TextVariant.Muted,
                    )
            }
        }

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        // Toggle Group
        Text(text = "Toggle Group", variant = TextVariant.Small)
        Spacer(Modifier.height(RikkaTheme.spacing.xs))

        var boldSelected by remember { mutableStateOf(false) }
        var italicSelected by remember { mutableStateOf(false) }
        var underlineSelected by remember { mutableStateOf(false) }

        ToggleGroup {
            ToggleGroupItem(
                text = "Bold",
                selected = boldSelected,
                onClick = { boldSelected = !boldSelected },
                variant = ToggleGroupVariant.Outline,
            )
            ToggleGroupItem(
                text = "Italic",
                selected = italicSelected,
                onClick = { italicSelected = !italicSelected },
                variant = ToggleGroupVariant.Outline,
            )
            ToggleGroupItem(
                text = "Underline",
                selected = underlineSelected,
                onClick = { underlineSelected = !underlineSelected },
                variant = ToggleGroupVariant.Outline,
            )
        }
    }
}
