package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.*
import rikkaui.feature.docs.generated.resources.Res
import zed.rainxch.rikkaui.components.ui.PopupAnimation
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.dropdown.DropdownMenu
import zed.rainxch.rikkaui.components.ui.dropdown.DropdownMenuItem
import zed.rainxch.rikkaui.components.ui.dropdown.DropdownMenuLabel
import zed.rainxch.rikkaui.components.ui.dropdown.DropdownMenuSeparator
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
 * Documentation page for the DropdownMenu component.
 *
 * Demonstrates click-triggered action menus with labels,
 * separators, disabled items, and animation variants.
 */
@Composable
fun DropdownMenuDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_dropdown_menu_name),
        description = stringResource(Res.string.dropdown_page_desc),
    )

    ComponentFamily(
        related = ComponentFamilies.POPUPS,
        currentId = "dropdown-menu",
    )

    TabbedDocPage(
        overview = { DropdownMenuOverviewTab() },
        usage = { DropdownMenuUsageTab() },
        api = { DropdownMenuApiTab() },
    )
}

// ─── Overview Tab ───────────────────────────────────────────

@Composable
private fun DropdownMenuOverviewTab() {
    // ─── Animation Variants ─────────────────────────────────
    DocSection(stringResource(Res.string.section_animations)) {
        var selectedAnim by remember { mutableStateOf("FadeExpand") }
        var open by remember { mutableStateOf(false) }

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
            DropdownMenu(
                expanded = open,
                onDismiss = { open = false },
                animation = animation,
                trigger = {
                    Button(
                        stringResource(Res.string.dropdown_demo_actions),
                        onClick = { open = !open },
                    )
                },
            ) {
                DropdownMenuLabel(stringResource(Res.string.dropdown_demo_file))
                DropdownMenuItem(
                    stringResource(Res.string.dropdown_demo_new),
                    onClick = { open = false },
                )
                DropdownMenuItem(
                    stringResource(Res.string.dropdown_demo_open),
                    onClick = { open = false },
                )
                DropdownMenuSeparator()
                DropdownMenuLabel(stringResource(Res.string.dropdown_demo_edit))
                DropdownMenuItem(
                    stringResource(Res.string.dropdown_demo_copy),
                    onClick = { open = false },
                )
                DropdownMenuItem(
                    stringResource(Res.string.dropdown_demo_paste),
                    onClick = { open = false },
                )
            }
        }
    }

    // ─── With Disabled Items ────────────────────────────────
    DocSection(stringResource(Res.string.dropdown_section_disabled)) {
        var open by remember { mutableStateOf(false) }

        DemoBox {
            DropdownMenu(
                expanded = open,
                onDismiss = { open = false },
                trigger = {
                    Button(
                        stringResource(Res.string.dropdown_demo_more),
                        onClick = { open = !open },
                    )
                },
            ) {
                DropdownMenuItem(
                    stringResource(Res.string.dropdown_demo_edit),
                    onClick = { open = false },
                )
                DropdownMenuItem(
                    stringResource(Res.string.dropdown_demo_duplicate),
                    onClick = { open = false },
                )
                DropdownMenuSeparator()
                DropdownMenuItem(
                    stringResource(Res.string.dropdown_demo_delete),
                    onClick = {},
                    enabled = false,
                )
            }
        }
    }
}

// ─── Usage Tab ──────────────────────────────────────────────

@Composable
private fun DropdownMenuUsageTab() {
    DocSection(stringResource(Res.string.section_usage)) {
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

    DocSection(stringResource(Res.string.dropdown_section_dos_donts)) {
        var doOpen by remember { mutableStateOf(false) }
        var dontOpen by remember { mutableStateOf(false) }

        DoAndDont(
            doContent = {
                DropdownMenu(
                    expanded = doOpen,
                    onDismiss = { doOpen = false },
                    trigger = {
                        Button(
                            "Actions",
                            onClick = { doOpen = !doOpen },
                            variant = ButtonVariant.Outline,
                        )
                    },
                ) {
                    DropdownMenuItem("Edit", onClick = { doOpen = false })
                    DropdownMenuItem("Duplicate", onClick = { doOpen = false })
                    DropdownMenuSeparator()
                    DropdownMenuItem("Delete", onClick = { doOpen = false })
                }
            },
            doDescription = stringResource(Res.string.dropdown_do_actions_desc),
            dontContent = {
                DropdownMenu(
                    expanded = dontOpen,
                    onDismiss = { dontOpen = false },
                    trigger = {
                        Button(
                            "Go to",
                            onClick = { dontOpen = !dontOpen },
                        )
                    },
                ) {
                    DropdownMenuItem("Home", onClick = { dontOpen = false })
                    DropdownMenuItem("Docs", onClick = { dontOpen = false })
                    DropdownMenuItem("Settings", onClick = { dontOpen = false })
                }
            },
            dontDescription = stringResource(Res.string.dropdown_dont_navigation_desc),
        )
    }
}

// ─── API Tab ────────────────────────────────────────────────

@Composable
private fun DropdownMenuApiTab() {
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "expanded",
                    "Boolean",
                    "required",
                    stringResource(Res.string.dropdown_prop_expanded_desc),
                ),
                PropInfo(
                    "onDismiss",
                    "() -> Unit",
                    "required",
                    stringResource(Res.string.dropdown_prop_on_dismiss_desc),
                ),
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(Res.string.dropdown_prop_modifier_desc),
                ),
                PropInfo(
                    "animation",
                    "PopupAnimation",
                    "FadeExpand",
                    stringResource(Res.string.dropdown_prop_animation_desc),
                ),
                PropInfo(
                    "minWidth",
                    "Dp",
                    "180.dp",
                    stringResource(Res.string.dropdown_prop_min_width_desc),
                ),
                PropInfo(
                    "maxWidth",
                    "Dp",
                    "280.dp",
                    stringResource(Res.string.dropdown_prop_max_width_desc),
                ),
                PropInfo(
                    "maxHeight",
                    "Dp",
                    "300.dp",
                    stringResource(Res.string.dropdown_prop_max_height_desc),
                ),
                PropInfo(
                    "trigger",
                    "() -> Unit",
                    "required",
                    stringResource(Res.string.dropdown_prop_trigger_desc),
                ),
                PropInfo(
                    "content",
                    "ColumnScope.() -> Unit",
                    "required",
                    stringResource(Res.string.dropdown_prop_content_desc),
                ),
            ),
        )
    }
}
