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
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.togglegroup.ToggleGroup
import zed.rainxch.rikkaui.components.ui.togglegroup.ToggleGroupAnimation
import zed.rainxch.rikkaui.components.ui.togglegroup.ToggleGroupItem
import zed.rainxch.rikkaui.components.ui.togglegroup.ToggleGroupVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector
import zed.rainxch.rikkaui.foundation.RikkaTheme

/**
 * Documentation page for the ToggleGroup component.
 *
 * Demonstrates grouped toggle buttons with Default/Outline
 * variants and animation strategies.
 */
@Composable
fun ToggleGroupDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_toggle_group_name),
        description = stringResource(Res.string.toggle_group_page_desc),
    )

    // ─── Variants ───────────────────────────────────────────
    DocSection(stringResource(Res.string.section_variants)) {
        var selectedVariant by remember { mutableStateOf("Default") }
        var selected by remember { mutableStateOf(0) }

        VariantSelector(
            options = listOf("Default", "Outline"),
            selected = selectedVariant,
            onSelect = { selectedVariant = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val variant =
            when (selectedVariant) {
                "Outline" -> ToggleGroupVariant.Outline
                else -> ToggleGroupVariant.Default
            }

        DemoBox {
            ToggleGroup {
                ToggleGroupItem(
                    text = stringResource(Res.string.toggle_group_demo_bold),
                    selected = selected == 0,
                    onClick = { selected = 0 },
                    variant = variant,
                )
                ToggleGroupItem(
                    text = stringResource(Res.string.toggle_group_demo_italic),
                    selected = selected == 1,
                    onClick = { selected = 1 },
                    variant = variant,
                )
                ToggleGroupItem(
                    text = stringResource(Res.string.toggle_group_demo_underline),
                    selected = selected == 2,
                    onClick = { selected = 2 },
                    variant = variant,
                )
            }
        }
    }

    // ─── Animation Variants ─────────────────────────────────
    DocSection(stringResource(Res.string.section_animations)) {
        var selectedAnim by remember { mutableStateOf("Spring") }
        var selected by remember { mutableStateOf(0) }

        VariantSelector(
            options = listOf("Spring", "Tween", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation =
            when (selectedAnim) {
                "Tween" -> ToggleGroupAnimation.Tween
                "None" -> ToggleGroupAnimation.None
                else -> ToggleGroupAnimation.Spring
            }

        DemoBox {
            ToggleGroup {
                ToggleGroupItem(
                    text = stringResource(Res.string.toggle_group_demo_day),
                    selected = selected == 0,
                    onClick = { selected = 0 },
                    animation = animation,
                )
                ToggleGroupItem(
                    text = stringResource(Res.string.toggle_group_demo_week),
                    selected = selected == 1,
                    onClick = { selected = 1 },
                    animation = animation,
                )
                ToggleGroupItem(
                    text = stringResource(Res.string.toggle_group_demo_month),
                    selected = selected == 2,
                    onClick = { selected = 2 },
                    animation = animation,
                )
            }
        }
    }

    // ─── Outline with Selection Text ────────────────────────
    DocSection(stringResource(Res.string.toggle_group_section_outline)) {
        var selected by remember { mutableStateOf(0) }
        val options =
            listOf(
                stringResource(Res.string.toggle_group_demo_left),
                stringResource(Res.string.toggle_group_demo_center),
                stringResource(Res.string.toggle_group_demo_right),
            )

        DemoBox {
            Column {
                ToggleGroup {
                    options.forEachIndexed { index, label ->
                        ToggleGroupItem(
                            text = label,
                            selected = selected == index,
                            onClick = { selected = index },
                            variant = ToggleGroupVariant.Outline,
                        )
                    }
                }
                Spacer(Modifier.height(RikkaTheme.spacing.sm))
                Text(
                    stringResource(
                        Res.string.toggle_group_demo_selected,
                        options[selected],
                    ),
                    variant = TextVariant.Muted,
                )
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
var selected by remember { mutableStateOf(0) }

ToggleGroup {
    ToggleGroupItem(
        text = "Bold",
        selected = selected == 0,
        onClick = { selected = 0 },
        variant = ToggleGroupVariant.Default,
        animation = ToggleGroupAnimation.Spring,
    )
    ToggleGroupItem(
        text = "Italic",
        selected = selected == 1,
        onClick = { selected = 1 },
    )
    ToggleGroupItem(
        text = "Underline",
        selected = selected == 2,
        onClick = { selected = 2 },
    )
}
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "text",
                    "String",
                    "required",
                    stringResource(Res.string.toggle_group_prop_text_desc),
                ),
                PropInfo(
                    "selected",
                    "Boolean",
                    "required",
                    stringResource(Res.string.toggle_group_prop_selected_desc),
                ),
                PropInfo(
                    "onClick",
                    "() -> Unit",
                    "required",
                    stringResource(Res.string.toggle_group_prop_onclick_desc),
                ),
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(Res.string.toggle_group_prop_modifier_desc),
                ),
                PropInfo(
                    "variant",
                    "ToggleGroupVariant",
                    "Default",
                    stringResource(Res.string.toggle_group_prop_variant_desc),
                ),
                PropInfo(
                    "animation",
                    "ToggleGroupAnimation",
                    "Spring",
                    stringResource(Res.string.toggle_group_prop_animation_desc),
                ),
                PropInfo(
                    "label",
                    "String",
                    "\"\"",
                    stringResource(Res.string.toggle_group_prop_label_desc),
                ),
                PropInfo(
                    "selectedColor",
                    "Color",
                    "Unspecified",
                    stringResource(Res.string.toggle_group_prop_selected_color_desc),
                ),
                PropInfo(
                    "unselectedColor",
                    "Color",
                    "Unspecified",
                    stringResource(Res.string.toggle_group_prop_unselected_color_desc),
                ),
            ),
        )
    }
}
