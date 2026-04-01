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
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.sheet.Sheet
import zed.rainxch.rikkaui.components.ui.sheet.SheetAnimation
import zed.rainxch.rikkaui.components.ui.sheet.SheetContent
import zed.rainxch.rikkaui.components.ui.sheet.SheetFooter
import zed.rainxch.rikkaui.components.ui.sheet.SheetHeader
import zed.rainxch.rikkaui.components.ui.sheet.SheetSide
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector
import zed.rainxch.rikkaui.foundation.RikkaTheme

/**
 * Documentation page for the Sheet component.
 *
 * Demonstrates sheet sides, animation variants, and structured
 * sections (SheetHeader, SheetContent, SheetFooter).
 */
@Composable
fun SheetDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_sheet_name),
        description = stringResource(Res.string.sheet_page_desc),
    )

    // ─── Side Variants ──────────────────────────────────────
    DocSection(stringResource(Res.string.sheet_section_sides)) {
        var selectedSide by remember { mutableStateOf("Right") }
        var open by remember { mutableStateOf(false) }

        VariantSelector(
            options = listOf("Right", "Left", "Top", "Bottom"),
            selected = selectedSide,
            onSelect = { selectedSide = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val side =
            when (selectedSide) {
                "Left" -> SheetSide.Left
                "Top" -> SheetSide.Top
                "Bottom" -> SheetSide.Bottom
                else -> SheetSide.Right
            }

        DemoBox {
            Button(
                stringResource(Res.string.sheet_demo_open_sheet, selectedSide),
                onClick = { open = true },
            )

            Sheet(
                open = open,
                onDismiss = { open = false },
                side = side,
            ) {
                SheetHeader(
                    title = stringResource(Res.string.sheet_demo_settings),
                    description = stringResource(Res.string.sheet_demo_adjust_prefs),
                )
                SheetContent {
                    Text(
                        stringResource(Res.string.sheet_demo_sliding_from, selectedSide),
                        variant = TextVariant.P,
                    )
                }
                SheetFooter {
                    Button(
                        stringResource(Res.string.sheet_demo_cancel),
                        onClick = { open = false },
                        variant = ButtonVariant.Outline,
                    )
                    Button(stringResource(Res.string.sheet_demo_apply), onClick = { open = false })
                }
            }
        }
    }

    // ─── Animation Variants ─────────────────────────────────
    DocSection(stringResource(Res.string.section_animations)) {
        var selectedAnim by remember { mutableStateOf("Slide") }
        var open by remember { mutableStateOf(false) }

        VariantSelector(
            options = listOf("Slide", "FadeScale", "Fade", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation =
            when (selectedAnim) {
                "FadeScale" -> SheetAnimation.FadeScale
                "Fade" -> SheetAnimation.Fade
                "None" -> SheetAnimation.None
                else -> SheetAnimation.Slide
            }

        DemoBox {
            Button(
                stringResource(Res.string.sheet_demo_open),
                onClick = { open = true },
            )

            Sheet(
                open = open,
                onDismiss = { open = false },
                animation = animation,
            ) {
                SheetHeader(title = stringResource(Res.string.sheet_demo_navigation))
                SheetContent {
                    Text(
                        stringResource(Res.string.sheet_demo_animation_label, selectedAnim),
                        variant = TextVariant.P,
                    )
                }
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
var open by remember { mutableStateOf(false) }

Button("Open Sheet", onClick = { open = true })

Sheet(
    open = open,
    onDismiss = { open = false },
    side = SheetSide.Right,
    animation = SheetAnimation.Slide,
) {
    SheetHeader(
        title = "Settings",
        description = "Adjust your preferences.",
    )
    SheetContent {
        Text("Sheet body content here.")
    }
    SheetFooter {
        Button("Close", onClick = { open = false })
    }
}
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "open",
                    "Boolean",
                    "required",
                    stringResource(Res.string.sheet_prop_open_desc),
                ),
                PropInfo(
                    "onDismiss",
                    "() -> Unit",
                    "required",
                    stringResource(Res.string.sheet_prop_on_dismiss_desc),
                ),
                PropInfo(
                    "side",
                    "SheetSide",
                    "Right",
                    stringResource(Res.string.sheet_prop_side_desc),
                ),
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(Res.string.sheet_prop_modifier_desc),
                ),
                PropInfo(
                    "label",
                    "String",
                    "\"Sheet\"",
                    stringResource(Res.string.sheet_prop_label_desc),
                ),
                PropInfo(
                    "animation",
                    "SheetAnimation",
                    "Slide",
                    stringResource(Res.string.sheet_prop_animation_desc),
                ),
                PropInfo(
                    "scrimColor",
                    "Color",
                    "colors.scrim",
                    stringResource(Res.string.sheet_prop_scrim_desc),
                ),
                PropInfo(
                    "panelWidth",
                    "Dp",
                    "320.dp",
                    stringResource(Res.string.sheet_prop_panel_width_desc),
                ),
                PropInfo(
                    "content",
                    "@Composable ColumnScope.() -> Unit",
                    "required",
                    stringResource(Res.string.sheet_prop_content_desc),
                ),
            ),
        )
    }
}
