package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.PopupAnimation
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.dropdown.DropdownMenu
import zed.rainxch.rikkaui.components.ui.dropdown.DropdownMenuItem
import zed.rainxch.rikkaui.components.ui.dropdown.DropdownMenuLabel
import zed.rainxch.rikkaui.components.ui.dropdown.DropdownMenuSeparator
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector

/**
 * Documentation page for the DropdownMenu component.
 *
 * Demonstrates click-triggered action menus with labels,
 * separators, disabled items, and animation variants.
 */
@Composable
fun DropdownMenuDoc() {
    ComponentPageHeader(
        name = "DropdownMenu",
        description = "A click-triggered overlay menu with scrollable "
            + "actions, labels, and separators.",
    )

    // ─── Animation Variants ─────────────────────────────────
    DocSection("Animations") {
        var selectedAnim by remember { mutableStateOf("FadeExpand") }
        var open by remember { mutableStateOf(false) }

        VariantSelector(
            options = listOf("FadeExpand", "Fade", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = when (selectedAnim) {
            "Fade" -> PopupAnimation.Fade
            "None" -> PopupAnimation.None
            else -> PopupAnimation.FadeExpand
        }

        DemoBox {
            DropdownMenu(
                expanded = open,
                onDismiss = { open = false },
                animation = animation,
                trigger = {
                    Button("Actions", onClick = { open = !open })
                },
            ) {
                DropdownMenuLabel("File")
                DropdownMenuItem(
                    "New",
                    onClick = { open = false },
                )
                DropdownMenuItem(
                    "Open",
                    onClick = { open = false },
                )
                DropdownMenuSeparator()
                DropdownMenuLabel("Edit")
                DropdownMenuItem(
                    "Copy",
                    onClick = { open = false },
                )
                DropdownMenuItem(
                    "Paste",
                    onClick = { open = false },
                )
            }
        }
    }

    // ─── With Disabled Items ────────────────────────────────
    DocSection("Disabled Items") {
        var open by remember { mutableStateOf(false) }

        DemoBox {
            DropdownMenu(
                expanded = open,
                onDismiss = { open = false },
                trigger = {
                    Button("More", onClick = { open = !open })
                },
            ) {
                DropdownMenuItem(
                    "Edit",
                    onClick = { open = false },
                )
                DropdownMenuItem(
                    "Duplicate",
                    onClick = { open = false },
                )
                DropdownMenuSeparator()
                DropdownMenuItem(
                    "Delete",
                    onClick = {},
                    enabled = false,
                )
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
        CodeBlock(
            """
var open by remember { mutableStateOf(false) }

DropdownMenu(
    expanded = open,
    onDismiss = { open = false },
    animation = PopupAnimation.FadeExpand,
    trigger = {
        Button("Actions", onClick = { open = true })
    },
) {
    DropdownMenuLabel("File")
    DropdownMenuItem("New", onClick = { open = false })
    DropdownMenuItem("Open", onClick = { open = false })
    DropdownMenuSeparator()
    DropdownMenuItem("Delete", onClick = { open = false })
}
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "expanded", "Boolean", "required",
                    "Whether the dropdown popup is visible.",
                ),
                PropInfo(
                    "onDismiss", "() -> Unit", "required",
                    "Called when clicking outside to dismiss.",
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier applied to the trigger wrapper.",
                ),
                PropInfo(
                    "animation", "PopupAnimation", "FadeExpand",
                    "Animation style: FadeExpand, Fade, None.",
                ),
                PropInfo(
                    "minWidth", "Dp", "180.dp",
                    "Minimum width of the dropdown panel.",
                ),
                PropInfo(
                    "maxWidth", "Dp", "280.dp",
                    "Maximum width of the dropdown panel.",
                ),
                PropInfo(
                    "maxHeight", "Dp", "300.dp",
                    "Max height before scrolling.",
                ),
                PropInfo(
                    "trigger", "() -> Unit", "required",
                    "The composable that anchors the menu.",
                ),
                PropInfo(
                    "content", "ColumnScope.() -> Unit", "required",
                    "Menu items, labels, and separators.",
                ),
            ),
        )
    }
}
