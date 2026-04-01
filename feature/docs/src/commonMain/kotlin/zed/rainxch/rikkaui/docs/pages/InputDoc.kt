package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.*
import rikkaui.feature.docs.generated.resources.Res
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.input.Input
import zed.rainxch.rikkaui.components.ui.input.InputAnimation
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector
import zed.rainxch.rikkaui.foundation.RikkaTheme

/**
 * Documentation page for the Input component.
 *
 * Demonstrates focus animations, icon slots, clearable mode,
 * character count, and disabled state.
 */
@Composable
fun InputDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_input_name),
        description = stringResource(Res.string.input_page_desc),
    )

    // ─── Focus Animations Demo ──────────────────────────────
    DocSection(stringResource(Res.string.input_section_focus)) {
        var selectedAnim by remember {
            mutableStateOf(InputAnimation.Glow.name)
        }

        VariantSelector(
            options = InputAnimation.entries.map { it.name },
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = InputAnimation.valueOf(selectedAnim)
        var text by remember { mutableStateOf("") }

        DemoBox {
            Input(
                value = text,
                onValueChange = { text = it },
                placeholder = stringResource(Res.string.input_demo_focus_placeholder),
                animation = animation,
                modifier = Modifier.width(320.dp),
            )
        }
    }

    // ─── With Icons Demo ────────────────────────────────────
    DocSection(stringResource(Res.string.input_section_icons)) {
        var search by remember { mutableStateOf("") }

        DemoBox {
            Input(
                value = search,
                onValueChange = { search = it },
                placeholder = stringResource(Res.string.input_demo_search_placeholder),
                leadingIcon = RikkaIcons.Search,
                clearable = true,
                modifier = Modifier.width(320.dp),
            )
        }
    }

    // ─── Character Count Demo ───────────────────────────────
    DocSection(stringResource(Res.string.input_section_char_count)) {
        var username by remember { mutableStateOf("") }

        DemoBox {
            Input(
                value = username,
                onValueChange = { username = it },
                placeholder = stringResource(Res.string.input_demo_username_placeholder),
                maxLength = 32,
                showCharCount = true,
                modifier = Modifier.width(320.dp),
            )
        }
    }

    // ─── Disabled State ─────────────────────────────────────
    DocSection(stringResource(Res.string.section_disabled)) {
        DemoBox {
            Input(
                value = stringResource(Res.string.input_demo_disabled_value),
                onValueChange = {},
                enabled = false,
                modifier = Modifier.width(320.dp),
            )
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
var text by remember { mutableStateOf("") }

Input(
    value = text,
    onValueChange = { text = it },
    placeholder = "Enter your name...",
)

Input(
    value = text,
    onValueChange = { text = it },
    placeholder = "Search...",
    leadingIcon = RikkaIcons.Search,
    clearable = true,
    animation = InputAnimation.Glow,
)
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "value",
                    "String",
                    "-",
                    stringResource(Res.string.input_prop_value_desc),
                ),
                PropInfo(
                    "onValueChange",
                    "(String) -> Unit",
                    "-",
                    stringResource(Res.string.input_prop_on_value_change_desc),
                ),
                PropInfo(
                    "placeholder",
                    "String",
                    "\"\"",
                    stringResource(Res.string.input_prop_placeholder_desc),
                ),
                PropInfo(
                    "animation",
                    "InputAnimation",
                    "Glow",
                    stringResource(Res.string.input_prop_animation_desc),
                ),
                PropInfo(
                    "leadingIcon",
                    "ImageVector?",
                    "null",
                    stringResource(Res.string.input_prop_leading_icon_desc),
                ),
                PropInfo(
                    "trailingIcon",
                    "ImageVector?",
                    "null",
                    stringResource(Res.string.input_prop_trailing_icon_desc),
                ),
                PropInfo(
                    "clearable",
                    "Boolean",
                    "false",
                    stringResource(Res.string.input_prop_clearable_desc),
                ),
                PropInfo(
                    "maxLength",
                    "Int?",
                    "null",
                    stringResource(Res.string.input_prop_max_length_desc),
                ),
                PropInfo(
                    "showCharCount",
                    "Boolean",
                    "false",
                    stringResource(Res.string.input_prop_show_char_count_desc),
                ),
                PropInfo(
                    "enabled",
                    "Boolean",
                    "true",
                    stringResource(Res.string.input_prop_enabled_desc),
                ),
                PropInfo(
                    "readOnly",
                    "Boolean",
                    "false",
                    stringResource(Res.string.input_prop_read_only_desc),
                ),
                PropInfo(
                    "singleLine",
                    "Boolean",
                    "true",
                    stringResource(Res.string.input_prop_single_line_desc),
                ),
                PropInfo(
                    "label",
                    "String",
                    "\"\"",
                    stringResource(Res.string.input_prop_label_desc),
                ),
                PropInfo(
                    "onClear",
                    "(() -> Unit)?",
                    "null",
                    stringResource(Res.string.input_prop_on_clear_desc),
                ),
                PropInfo(
                    "colors",
                    "InputColorValues",
                    "InputDefaults.colors()",
                    stringResource(Res.string.input_prop_colors_desc),
                ),
            ),
        )
    }
}
