package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.PopupAnimation
import zed.rainxch.rikkaui.components.ui.contextmenu.ContextMenu
import zed.rainxch.rikkaui.components.ui.contextmenu.ContextMenuItem
import zed.rainxch.rikkaui.components.ui.contextmenu.ContextMenuLabel
import zed.rainxch.rikkaui.components.ui.contextmenu.ContextMenuSeparator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector

/**
 * Documentation page for the ContextMenu component.
 *
 * Demonstrates long-press triggered context menus with labels,
 * separators, shortcuts, and animation variants.
 */
@Composable
fun ContextMenuDoc() {
    ComponentPageHeader(
        name = "ContextMenu",
        description = "A long-press triggered overlay menu. On "
            + "desktop/web maps to right-click, on mobile to "
            + "long-press.",
    )

    // ─── Animation Variants ─────────────────────────────────
    DocSection("Animations") {
        var selectedAnim by remember { mutableStateOf("FadeExpand") }

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
            ContextMenu(
                animation = animation,
                menuContent = {
                    ContextMenuLabel("Edit")
                    ContextMenuItem(
                        "Cut",
                        onClick = {},
                        shortcut = "\u2318X",
                    )
                    ContextMenuItem(
                        "Copy",
                        onClick = {},
                        shortcut = "\u2318C",
                    )
                    ContextMenuItem(
                        "Paste",
                        onClick = {},
                        shortcut = "\u2318V",
                    )
                    ContextMenuSeparator()
                    ContextMenuItem("Delete", onClick = {})
                },
            ) {
                Box(
                    modifier = Modifier
                        .border(
                            1.dp,
                            RikkaTheme.colors.border,
                            RikkaTheme.shapes.md,
                        )
                        .background(
                            RikkaTheme.colors.muted,
                            RikkaTheme.shapes.md,
                        )
                        .padding(RikkaTheme.spacing.xl),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        "Long-press or right-click me",
                        variant = TextVariant.Muted,
                    )
                }
            }
        }
    }

    // ─── With Disabled Items ────────────────────────────────
    DocSection("Disabled Items & Shortcuts") {
        DemoBox {
            ContextMenu(
                menuContent = {
                    ContextMenuLabel("Actions")
                    ContextMenuItem(
                        "Edit",
                        onClick = {},
                        shortcut = "\u2318E",
                    )
                    ContextMenuItem(
                        "Duplicate",
                        onClick = {},
                    )
                    ContextMenuSeparator()
                    ContextMenuItem(
                        "Delete",
                        onClick = {},
                        enabled = false,
                    )
                },
            ) {
                Box(
                    modifier = Modifier
                        .border(
                            1.dp,
                            RikkaTheme.colors.border,
                            RikkaTheme.shapes.md,
                        )
                        .background(
                            RikkaTheme.colors.muted,
                            RikkaTheme.shapes.md,
                        )
                        .padding(RikkaTheme.spacing.xl),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        "Right-click for actions",
                        variant = TextVariant.Muted,
                    )
                }
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
        CodeBlock(
            """
ContextMenu(
    menuContent = {
        ContextMenuLabel("Edit")
        ContextMenuItem(
            "Copy",
            onClick = { copy() },
            shortcut = "\u2318C",
        )
        ContextMenuSeparator()
        ContextMenuItem("Delete", onClick = { del() })
    },
) {
    Card {
        Text("Long-press or right-click me")
    }
}
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "menuContent", "ColumnScope.() -> Unit", "required",
                    "Builder for items, labels, and separators.",
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
                    "minWidth", "Dp", "200.dp",
                    "Minimum width of the context menu.",
                ),
                PropInfo(
                    "maxWidth", "Dp", "280.dp",
                    "Maximum width of the context menu.",
                ),
                PropInfo(
                    "maxHeight", "Dp", "300.dp",
                    "Max height before scrolling.",
                ),
                PropInfo(
                    "content", "() -> Unit", "required",
                    "Trigger area that responds to long-press.",
                ),
            ),
        )
    }
}
