package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.dropdown.DropdownMenu
import zed.rainxch.rikkaui.components.ui.dropdown.DropdownMenuItem
import zed.rainxch.rikkaui.components.ui.dropdown.DropdownMenuSeparator
import zed.rainxch.rikkaui.components.ui.hovercard.HoverCard
import zed.rainxch.rikkaui.components.ui.popover.Popover
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.tooltip.Tooltip

@Composable
fun OverlaySection() {
    SectionHeader(
        title = "Tooltip, Popover, Dropdown & Hover Card",
        description = "Contextual overlays and menus.",
    )

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.lg)) {
            // ─── Tooltip ─────────────────────────────────────
            Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm)) {
                Text(text = "Tooltip", variant = TextVariant.Small)

                Tooltip(tooltip = "Click me for a tooltip") {
                    Button(
                        text = "Hover me",
                        onClick = {},
                        variant = ButtonVariant.Outline,
                    )
                }
            }

            // ─── Popover ─────────────────────────────────────
            Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm)) {
                Text(text = "Popover", variant = TextVariant.Small)

                var popoverOpen by remember { mutableStateOf(false) }

                Popover(
                    expanded = popoverOpen,
                    onDismiss = { popoverOpen = false },
                    trigger = {
                        Button(
                            text = "Open Popover",
                            onClick = { popoverOpen = !popoverOpen },
                            variant = ButtonVariant.Outline,
                        )
                    },
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm)) {
                        Text(text = "Popover Content", variant = TextVariant.Large)
                        Text(text = "This is some content inside a popover panel.")
                    }
                }
            }

            // ─── Dropdown Menu ───────────────────────────────
            Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm)) {
                Text(text = "Dropdown Menu", variant = TextVariant.Small)

                var dropdownOpen by remember { mutableStateOf(false) }

                DropdownMenu(
                    expanded = dropdownOpen,
                    onDismiss = { dropdownOpen = false },
                    trigger = {
                        Button(
                            text = "Options",
                            onClick = { dropdownOpen = !dropdownOpen },
                            variant = ButtonVariant.Outline,
                        )
                    },
                ) {
                    DropdownMenuItem(
                        text = "Edit",
                        onClick = { dropdownOpen = false },
                    )
                    DropdownMenuItem(
                        text = "Duplicate",
                        onClick = { dropdownOpen = false },
                    )
                    DropdownMenuSeparator()
                    DropdownMenuItem(
                        text = "Delete",
                        onClick = { dropdownOpen = false },
                    )
                }
            }

            // ─── Hover Card ──────────────────────────────────
            Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm)) {
                Text(text = "Hover Card", variant = TextVariant.Small)

                HoverCard(
                    trigger = {
                        Text(
                            text = "@rikkaui",
                            variant = TextVariant.Small,
                            color = RikkaTheme.colors.primary,
                        )
                    },
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs)) {
                        Text(text = "RikkaUI", variant = TextVariant.Large)
                        Text(
                            text = "A shadcn/ui-inspired component library for Compose Multiplatform.",
                            variant = TextVariant.Muted,
                        )
                    }
                }
            }
        }
    }
}
