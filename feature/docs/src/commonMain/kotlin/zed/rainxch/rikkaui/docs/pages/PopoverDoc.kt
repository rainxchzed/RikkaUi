package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Column
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
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.popover.Popover
import zed.rainxch.rikkaui.components.ui.popover.PopoverAnimation
import zed.rainxch.rikkaui.components.ui.popover.PopoverPlacement
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
 * Documentation page for the Popover component.
 *
 * Demonstrates click-triggered popups with various animations
 * and placement options.
 */
@Composable
fun PopoverDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_popover_name),
        description = stringResource(Res.string.popover_page_desc),
    )

    ComponentFamily(
        related = ComponentFamilies.POPUPS,
        currentId = "popover",
    )

    TabbedDocPage(
        overview = { PopoverOverviewTab() },
        usage = { PopoverUsageTab() },
        api = { PopoverApiTab() },
    )
}

// ─── Overview Tab ───────────────────────────────────────────

@Composable
private fun PopoverOverviewTab() {
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
                "Fade" -> PopoverAnimation.Fade
                "None" -> PopoverAnimation.None
                else -> PopoverAnimation.FadeExpand
            }

        DemoBox {
            Popover(
                expanded = open,
                onDismiss = { open = false },
                animation = animation,
                trigger = {
                    Button(
                        stringResource(Res.string.popover_demo_open),
                        onClick = { open = !open },
                    )
                },
            ) {
                Column {
                    Text(
                        stringResource(Res.string.popover_demo_content_title),
                        variant = TextVariant.Large,
                    )
                    Spacer(Modifier.height(RikkaTheme.spacing.xs))
                    Text(
                        stringResource(Res.string.popover_demo_animation_label, selectedAnim),
                        variant = TextVariant.Muted,
                    )
                }
            }
        }
    }

    // ─── Placements ─────────────────────────────────────────
    DocSection(stringResource(Res.string.section_placements)) {
        var selectedPlacement by remember { mutableStateOf("BottomStart") }
        var open by remember { mutableStateOf(false) }

        VariantSelector(
            options =
                listOf(
                    "BottomStart",
                    "BottomEnd",
                    "BottomCenter",
                    "TopStart",
                    "TopEnd",
                    "TopCenter",
                ),
            selected = selectedPlacement,
            onSelect = { selectedPlacement = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val placement =
            when (selectedPlacement) {
                "BottomEnd" -> PopoverPlacement.BottomEnd
                "BottomCenter" -> PopoverPlacement.BottomCenter
                "TopStart" -> PopoverPlacement.TopStart
                "TopEnd" -> PopoverPlacement.TopEnd
                "TopCenter" -> PopoverPlacement.TopCenter
                else -> PopoverPlacement.BottomStart
            }

        DemoBox {
            Popover(
                expanded = open,
                onDismiss = { open = false },
                placement = placement,
                trigger = {
                    Button(
                        stringResource(Res.string.popover_demo_placement_label, selectedPlacement),
                        onClick = { open = !open },
                    )
                },
            ) {
                Text(
                    stringResource(Res.string.popover_demo_placed_at, selectedPlacement),
                    variant = TextVariant.P,
                )
            }
        }
    }
}

// ─── Usage Tab ──────────────────────────────────────────────

@Composable
private fun PopoverUsageTab() {
    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
var open by remember { mutableStateOf(false) }

Popover(
    expanded = open,
    onDismiss = { open = false },
    animation = PopoverAnimation.FadeExpand,
    placement = PopoverPlacement.BottomStart,
    trigger = {
        Button("Open", onClick = { open = true })
    },
) {
    Text("Popover content goes here.")
}
            """.trimIndent(),
        )
    }

    DocSection(stringResource(Res.string.popover_section_dos_donts)) {
        var doOpen by remember { mutableStateOf(false) }
        var dontOpen by remember { mutableStateOf(false) }

        DoAndDont(
            doContent = {
                Popover(
                    expanded = doOpen,
                    onDismiss = { doOpen = false },
                    trigger = {
                        Button(
                            "More info",
                            onClick = { doOpen = !doOpen },
                            variant = ButtonVariant.Outline,
                        )
                    },
                ) {
                    Column {
                        Text("How it works", variant = TextVariant.Large)
                        Spacer(Modifier.height(RikkaTheme.spacing.xs))
                        Text("Click anywhere outside to close.", variant = TextVariant.Muted)
                    }
                }
            },
            doDescription = stringResource(Res.string.popover_do_additional_info_desc),
            dontContent = {
                Popover(
                    expanded = dontOpen,
                    onDismiss = { dontOpen = false },
                    trigger = {
                        Button(
                            "Show terms",
                            onClick = { dontOpen = !dontOpen },
                        )
                    },
                ) {
                    Text(
                        "You must accept terms to continue. Click accept below.",
                        variant = TextVariant.P,
                    )
                }
            },
            dontDescription = stringResource(Res.string.popover_dont_essential_desc),
        )
    }
}

// ─── API Tab ────────────────────────────────────────────────

@Composable
private fun PopoverApiTab() {
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "expanded",
                    "Boolean",
                    "required",
                    stringResource(Res.string.popover_prop_expanded_desc),
                ),
                PropInfo(
                    "onDismiss",
                    "() -> Unit",
                    "required",
                    stringResource(Res.string.popover_prop_on_dismiss_desc),
                ),
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(Res.string.popover_prop_modifier_desc),
                ),
                PropInfo(
                    "animation",
                    "PopoverAnimation",
                    "FadeExpand",
                    stringResource(Res.string.popover_prop_animation_desc),
                ),
                PropInfo(
                    "placement",
                    "PopoverPlacement",
                    "BottomStart",
                    stringResource(Res.string.popover_prop_placement_desc),
                ),
                PropInfo(
                    "maxWidth",
                    "Dp",
                    "360.dp",
                    stringResource(Res.string.popover_prop_max_width_desc),
                ),
                PropInfo(
                    "minWidth",
                    "Dp",
                    "120.dp",
                    stringResource(Res.string.popover_prop_min_width_desc),
                ),
                PropInfo(
                    "trigger",
                    "() -> Unit",
                    "required",
                    stringResource(Res.string.popover_prop_trigger_desc),
                ),
                PropInfo(
                    "content",
                    "() -> Unit",
                    "required",
                    stringResource(Res.string.popover_prop_content_desc),
                ),
            ),
        )
    }
}
