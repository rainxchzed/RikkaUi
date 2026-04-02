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
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.*
import rikkaui.feature.docs.generated.resources.Res
import zed.rainxch.rikkaui.components.ui.PopupAnimation
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.contextmenu.ContextMenu
import zed.rainxch.rikkaui.components.ui.contextmenu.ContextMenuItem
import zed.rainxch.rikkaui.components.ui.contextmenu.ContextMenuLabel
import zed.rainxch.rikkaui.components.ui.contextmenu.ContextMenuSeparator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.catalog.ComponentFamilies
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentFamily
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DoAndDont
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.TabbedDocPage
import zed.rainxch.rikkaui.docs.components.VariantSelector
import zed.rainxch.rikkaui.foundation.RikkaTheme

/**
 * Documentation page for the ContextMenu component.
 *
 * Demonstrates long-press triggered context menus with labels,
 * separators, shortcuts, and animation variants.
 */
@Composable
fun ContextMenuDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_context_menu_name),
        description = stringResource(Res.string.context_menu_page_desc),
    )

    ComponentFamily(
        related = ComponentFamilies.POPUPS,
        currentId = "context-menu",
    )

    TabbedDocPage(
        overview = { ContextMenuOverviewTab() },
        usage = { ContextMenuUsageTab() },
        api = { ContextMenuApiTab() },
    )
}

// ─── Overview Tab ───────────────────────────────────────────

@Composable
private fun ContextMenuOverviewTab() {
    // ─── Animation Variants ─────────────────────────────────
    DocSection(stringResource(Res.string.section_animations)) {
        var selectedAnim by remember { mutableStateOf("FadeExpand") }

        VariantSelector(
            options = listOf("FadeExpand", "Fade", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation =
            when (selectedAnim) {
                "Fade" -> PopupAnimation.Fade
                "None" -> PopupAnimation.None
                else -> PopupAnimation.FadeExpand
            }

        DemoBox {
            ContextMenu(
                animation = animation,
                menuContent = {
                    ContextMenuLabel(stringResource(Res.string.context_menu_demo_edit))
                    ContextMenuItem(
                        stringResource(Res.string.context_menu_demo_cut),
                        onClick = {},
                        shortcut = "\u2318X",
                    )
                    ContextMenuItem(
                        stringResource(Res.string.context_menu_demo_copy),
                        onClick = {},
                        shortcut = "\u2318C",
                    )
                    ContextMenuItem(
                        stringResource(Res.string.context_menu_demo_paste),
                        onClick = {},
                        shortcut = "\u2318V",
                    )
                    ContextMenuSeparator()
                    ContextMenuItem(
                        stringResource(Res.string.context_menu_demo_delete),
                        onClick = {},
                    )
                },
            ) {
                Box(
                    modifier =
                        Modifier
                            .border(1.dp, RikkaTheme.colors.border, RikkaTheme.shapes.md)
                            .background(RikkaTheme.colors.muted, RikkaTheme.shapes.md)
                            .padding(RikkaTheme.spacing.xl),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        stringResource(Res.string.context_menu_demo_long_press),
                        variant = TextVariant.Muted,
                    )
                }
            }
        }
    }

    // ─── With Disabled Items ────────────────────────────────
    DocSection(stringResource(Res.string.context_menu_section_disabled)) {
        DemoBox {
            ContextMenu(
                menuContent = {
                    ContextMenuLabel(stringResource(Res.string.context_menu_demo_actions))
                    ContextMenuItem(
                        stringResource(Res.string.context_menu_demo_edit),
                        onClick = {},
                        shortcut = "\u2318E",
                    )
                    ContextMenuItem(
                        stringResource(Res.string.context_menu_demo_duplicate),
                        onClick = {},
                    )
                    ContextMenuSeparator()
                    ContextMenuItem(
                        stringResource(Res.string.context_menu_demo_delete),
                        onClick = {},
                        enabled = false,
                    )
                },
            ) {
                Box(
                    modifier =
                        Modifier
                            .border(1.dp, RikkaTheme.colors.border, RikkaTheme.shapes.md)
                            .background(RikkaTheme.colors.muted, RikkaTheme.shapes.md)
                            .padding(RikkaTheme.spacing.xl),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        stringResource(Res.string.context_menu_demo_right_click),
                        variant = TextVariant.Muted,
                    )
                }
            }
        }
    }
}

// ─── Usage Tab ──────────────────────────────────────────────

@Composable
private fun ContextMenuUsageTab() {
    DocSection(stringResource(Res.string.section_usage)) {
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

    DocSection(stringResource(Res.string.context_menu_section_dos_donts)) {
        DoAndDont(
            doContent = {
                ContextMenu(
                    menuContent = {
                        ContextMenuLabel("File Actions")
                        ContextMenuItem("Rename", onClick = {}, shortcut = "F2")
                        ContextMenuItem("Copy", onClick = {}, shortcut = "\u2318C")
                        ContextMenuSeparator()
                        ContextMenuItem("Delete", onClick = {})
                    },
                ) {
                    Box(
                        modifier =
                            Modifier
                                .border(1.dp, RikkaTheme.colors.border, RikkaTheme.shapes.md)
                                .background(RikkaTheme.colors.muted, RikkaTheme.shapes.md)
                                .padding(RikkaTheme.spacing.lg),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("Right-click me", variant = TextVariant.Muted)
                    }
                }
            },
            doDescription = stringResource(Res.string.context_menu_do_right_click_desc),
            dontContent = {
                Box(
                    modifier =
                        Modifier
                            .border(1.dp, RikkaTheme.colors.border, RikkaTheme.shapes.md)
                            .background(RikkaTheme.colors.muted, RikkaTheme.shapes.md)
                            .padding(RikkaTheme.spacing.lg),
                    contentAlignment = Alignment.Center,
                ) {
                    ContextMenu(
                        menuContent = {
                            ContextMenuItem("The only way to delete", onClick = {})
                        },
                    ) {
                        Text("Right-click to delete", variant = TextVariant.Muted)
                    }
                }
            },
            dontDescription = stringResource(Res.string.context_menu_dont_only_access_desc),
        )
    }
}

// ─── API Tab ────────────────────────────────────────────────

@Composable
private fun ContextMenuApiTab() {
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "menuContent",
                    "ColumnScope.() -> Unit",
                    "required",
                    stringResource(Res.string.context_menu_prop_menu_content_desc),
                ),
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(Res.string.context_menu_prop_modifier_desc),
                ),
                PropInfo(
                    "animation",
                    "PopupAnimation",
                    "FadeExpand",
                    stringResource(Res.string.context_menu_prop_animation_desc),
                ),
                PropInfo(
                    "minWidth",
                    "Dp",
                    "200.dp",
                    stringResource(Res.string.context_menu_prop_min_width_desc),
                ),
                PropInfo(
                    "maxWidth",
                    "Dp",
                    "280.dp",
                    stringResource(Res.string.context_menu_prop_max_width_desc),
                ),
                PropInfo(
                    "maxHeight",
                    "Dp",
                    "300.dp",
                    stringResource(Res.string.context_menu_prop_max_height_desc),
                ),
                PropInfo(
                    "content",
                    "() -> Unit",
                    "required",
                    stringResource(Res.string.context_menu_prop_content_desc),
                ),
            ),
        )
    }
}
