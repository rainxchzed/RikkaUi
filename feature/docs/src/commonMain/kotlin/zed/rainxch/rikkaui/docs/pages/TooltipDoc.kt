package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.tooltip.Tooltip
import zed.rainxch.rikkaui.components.ui.tooltip.TooltipAnimation
import zed.rainxch.rikkaui.components.ui.tooltip.TooltipPlacement
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
 * Documentation page for the Tooltip component.
 *
 * Demonstrates hover-triggered tooltips with various animations,
 * placements, and delay configurations.
 */
@Composable
fun TooltipDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_tooltip_name),
        description = stringResource(Res.string.tooltip_page_desc),
    )

    ComponentFamily(
        related = ComponentFamilies.POPUPS,
        currentId = "tooltip",
    )

    TabbedDocPage(
        overview = { TooltipOverviewTab() },
        usage = { TooltipUsageTab() },
        api = { TooltipApiTab() },
    )
}

// ─── Overview Tab ───────────────────────────────────────────

@Composable
private fun TooltipOverviewTab() {
    // ─── Animation Variants ─────────────────────────────────
    DocSection(stringResource(Res.string.section_animations)) {
        var selectedAnim by remember { mutableStateOf("FadeScale") }

        VariantSelector(
            options = listOf("FadeScale", "Fade", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation =
            when (selectedAnim) {
                "Fade" -> TooltipAnimation.Fade
                "None" -> TooltipAnimation.None
                else -> TooltipAnimation.FadeScale
            }

        DemoBox {
            Tooltip(
                tooltip = stringResource(Res.string.tooltip_demo_save_changes),
                animation = animation,
            ) {
                Button(
                    stringResource(Res.string.tooltip_demo_hover_me),
                    onClick = {},
                )
            }
        }
    }

    // ─── Placements ─────────────────────────────────────────
    DocSection(stringResource(Res.string.section_placements)) {
        DemoBox {
            Row(
                horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
            ) {
                Tooltip(
                    tooltip = stringResource(Res.string.tooltip_demo_top_tooltip),
                    placement = TooltipPlacement.Top,
                ) {
                    Button(
                        stringResource(Res.string.tooltip_demo_top),
                        onClick = {},
                    )
                }
                Tooltip(
                    tooltip = stringResource(Res.string.tooltip_demo_bottom_tooltip),
                    placement = TooltipPlacement.Bottom,
                ) {
                    Button(
                        stringResource(Res.string.tooltip_demo_bottom),
                        onClick = {},
                    )
                }
                Tooltip(
                    tooltip = stringResource(Res.string.tooltip_demo_start_tooltip),
                    placement = TooltipPlacement.Start,
                ) {
                    Button(
                        stringResource(Res.string.tooltip_demo_start),
                        onClick = {},
                    )
                }
                Tooltip(
                    tooltip = stringResource(Res.string.tooltip_demo_end_tooltip),
                    placement = TooltipPlacement.End,
                ) {
                    Button(
                        stringResource(Res.string.tooltip_demo_end),
                        onClick = {},
                    )
                }
            }
        }
    }

    // ─── Custom Delay ───────────────────────────────────────
    DocSection(stringResource(Res.string.tooltip_section_custom_delay)) {
        DemoBox {
            Row(
                horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
            ) {
                Tooltip(
                    tooltip = stringResource(Res.string.tooltip_demo_instant),
                    showDelayMs = 0L,
                ) {
                    Button(
                        stringResource(Res.string.tooltip_demo_no_delay),
                        onClick = {},
                    )
                }
                Tooltip(
                    tooltip = stringResource(Res.string.tooltip_demo_slow),
                    showDelayMs = 800L,
                ) {
                    Button(
                        stringResource(Res.string.tooltip_demo_slow_delay),
                        onClick = {},
                    )
                }
            }
        }
    }
}

// ─── Usage Tab ──────────────────────────────────────────────

@Composable
private fun TooltipUsageTab() {
    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
Tooltip(tooltip = "Save changes") {
    Button("Save", onClick = { save() })
}

// With custom animation, placement, and delay
Tooltip(
    tooltip = "Copy to clipboard",
    animation = TooltipAnimation.Fade,
    placement = TooltipPlacement.Bottom,
    showDelayMs = 200L,
) {
    Button("Copy", onClick = { copy() })
}
            """.trimIndent(),
        )
    }

    DocSection(stringResource(Res.string.tooltip_section_dos_donts)) {
        DoAndDont(
            doContent = {
                Tooltip(tooltip = "Save changes") {
                    Button("Save", onClick = {})
                }
            },
            doDescription = stringResource(Res.string.tooltip_do_brief_desc),
            dontContent = {
                Tooltip(
                    tooltip = "Click here for more",
                ) {
                    Button("Settings", onClick = {})
                }
            },
            dontDescription = stringResource(Res.string.tooltip_dont_interactive_desc),
        )
    }
}

// ─── API Tab ────────────────────────────────────────────────

@Composable
private fun TooltipApiTab() {
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "tooltip",
                    "String",
                    "required",
                    stringResource(Res.string.tooltip_prop_tooltip_desc),
                ),
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(Res.string.tooltip_prop_modifier_desc),
                ),
                PropInfo(
                    "animation",
                    "TooltipAnimation",
                    "FadeScale",
                    stringResource(Res.string.tooltip_prop_animation_desc),
                ),
                PropInfo(
                    "placement",
                    "TooltipPlacement",
                    "Top",
                    stringResource(Res.string.tooltip_prop_placement_desc),
                ),
                PropInfo(
                    "showDelayMs",
                    "Long",
                    "400L",
                    stringResource(Res.string.tooltip_prop_show_delay_desc),
                ),
                PropInfo(
                    "content",
                    "() -> Unit",
                    "required",
                    stringResource(Res.string.tooltip_prop_content_desc),
                ),
            ),
        )
    }
}
