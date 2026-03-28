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
import zed.rainxch.rikkaui.components.ui.dialog.Dialog
import zed.rainxch.rikkaui.components.ui.dialog.DialogFooter
import zed.rainxch.rikkaui.components.ui.dialog.DialogHeader
import zed.rainxch.rikkaui.components.ui.sheet.Sheet
import zed.rainxch.rikkaui.components.ui.sheet.SheetContent
import zed.rainxch.rikkaui.components.ui.sheet.SheetFooter
import zed.rainxch.rikkaui.components.ui.sheet.SheetHeader
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

@Composable
fun DialogSection() {
    SectionHeader(
        title = "Dialog & Sheet",
        description = "Modal overlays and side panels.",
    )

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.lg)) {
            // ─── Dialog ──────────────────────────────────────
            Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm)) {
                Text(text = "Dialog", variant = TextVariant.Small)

                var dialogOpen by remember { mutableStateOf(false) }

                Button(
                    text = "Open Dialog",
                    onClick = { dialogOpen = true },
                )

                Dialog(
                    open = dialogOpen,
                    onDismiss = { dialogOpen = false },
                    label = "Confirmation dialog",
                ) {
                    DialogHeader(
                        title = "Are you sure?",
                        description = "This action cannot be undone.",
                    )
                    DialogFooter {
                        Button(
                            text = "Cancel",
                            onClick = { dialogOpen = false },
                            variant = ButtonVariant.Outline,
                        )
                        Button(
                            text = "Continue",
                            onClick = { dialogOpen = false },
                        )
                    }
                }
            }

            // ─── Sheet ───────────────────────────────────────
            Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm)) {
                Text(text = "Sheet", variant = TextVariant.Small)

                var sheetOpen by remember { mutableStateOf(false) }

                Button(
                    text = "Open Sheet",
                    onClick = { sheetOpen = true },
                )

                Sheet(
                    open = sheetOpen,
                    onDismiss = { sheetOpen = false },
                    label = "Settings sheet",
                ) {
                    SheetHeader(
                        title = "Settings",
                        description = "Adjust your preferences below.",
                    )
                    SheetContent {
                        Text(
                            text =
                                "This is placeholder content inside the sheet panel. " +
                                    "You can place any composable here.",
                        )
                    }
                    SheetFooter {
                        Button(
                            text = "Close",
                            onClick = { sheetOpen = false },
                            variant = ButtonVariant.Outline,
                        )
                    }
                }
            }
        }
    }
}
