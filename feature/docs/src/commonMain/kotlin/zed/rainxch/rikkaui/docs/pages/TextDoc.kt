package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
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
import rikkaui.feature.docs.generated.resources.Res
import rikkaui.feature.docs.generated.resources.*
import zed.rainxch.rikkaui.components.theme.RikkaTheme
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
 * Documentation page for the Text component.
 *
 * Demonstrates all typography variants, color overrides,
 * and selectable text.
 */
@Composable
fun TextDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_text_name),
        description = stringResource(Res.string.text_page_desc),
    )

    // ─── Variants ───────────────────────────────────────────
    DocSection(stringResource(Res.string.section_variants)) {
        var selected by remember { mutableStateOf("P") }

        VariantSelector(
            options = listOf(
                "H1", "H2", "H3", "H4",
                "P", "Lead", "Large", "Small", "Muted",
            ),
            selected = selected,
            onSelect = { selected = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val variant = when (selected) {
            "H1" -> TextVariant.H1
            "H2" -> TextVariant.H2
            "H3" -> TextVariant.H3
            "H4" -> TextVariant.H4
            "Lead" -> TextVariant.Lead
            "Large" -> TextVariant.Large
            "Small" -> TextVariant.Small
            "Muted" -> TextVariant.Muted
            else -> TextVariant.P
        }

        DemoBox {
            Text(
                text = stringResource(Res.string.text_demo_quick_fox),
                variant = variant,
            )
        }
    }

    // ─── All Variants Preview ───────────────────────────────
    DocSection(stringResource(Res.string.text_section_all_variants)) {
        DemoBox {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
            ) {
                Text(stringResource(Res.string.text_demo_heading_1), variant = TextVariant.H1)
                Text(stringResource(Res.string.text_demo_heading_2), variant = TextVariant.H2)
                Text(stringResource(Res.string.text_demo_heading_3), variant = TextVariant.H3)
                Text(stringResource(Res.string.text_demo_heading_4), variant = TextVariant.H4)
                Text(stringResource(Res.string.text_demo_paragraph), variant = TextVariant.P)
                Text(stringResource(Res.string.text_demo_lead), variant = TextVariant.Lead)
                Text(stringResource(Res.string.text_demo_large), variant = TextVariant.Large)
                Text(stringResource(Res.string.text_demo_small), variant = TextVariant.Small)
                Text(stringResource(Res.string.text_demo_muted), variant = TextVariant.Muted)
            }
        }
    }

    // ─── Selectable Text ────────────────────────────────────
    DocSection(stringResource(Res.string.text_section_selectable)) {
        DemoBox {
            Text(
                text = stringResource(Res.string.text_demo_selectable),
                variant = TextVariant.P,
                selectable = true,
            )
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
Text("Hello world")
Text("Page title", variant = TextVariant.H1)
Text("Subtle info", variant = TextVariant.Muted)
Text("Custom color", color = RikkaTheme.colors.destructive)
Text("Selectable", selectable = true)
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "text", "String", "required",
                    stringResource(Res.string.text_prop_text_desc),
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    stringResource(Res.string.text_prop_modifier_desc),
                ),
                PropInfo(
                    "variant", "TextVariant", "P",
                    stringResource(Res.string.text_prop_variant_desc),
                ),
                PropInfo(
                    "color", "Color", "Unspecified",
                    stringResource(Res.string.text_prop_color_desc),
                ),
                PropInfo(
                    "textAlign", "TextAlign?", "null",
                    stringResource(Res.string.text_prop_text_align_desc),
                ),
                PropInfo(
                    "overflow", "TextOverflow", "Clip",
                    stringResource(Res.string.text_prop_overflow_desc),
                ),
                PropInfo(
                    "maxLines", "Int", "Int.MAX_VALUE",
                    stringResource(Res.string.text_prop_max_lines_desc),
                ),
                PropInfo(
                    "minLines", "Int", "1",
                    stringResource(Res.string.text_prop_min_lines_desc),
                ),
                PropInfo(
                    "selectable", "Boolean", "false",
                    stringResource(Res.string.text_prop_selectable_desc),
                ),
                PropInfo(
                    "style", "TextStyle", "TextStyle.Default",
                    stringResource(Res.string.text_prop_style_desc),
                ),
            ),
        )
    }
}
