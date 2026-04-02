package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import zed.rainxch.rikkaui.components.ui.PopupAnimation
import zed.rainxch.rikkaui.components.ui.select.Select
import zed.rainxch.rikkaui.components.ui.select.SelectOption
import zed.rainxch.rikkaui.components.ui.toggle.Toggle
import zed.rainxch.rikkaui.docs.catalog.ComponentFamilies
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentFamily
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.DoAndDont
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.TabbedDocPage
import zed.rainxch.rikkaui.docs.components.VariantSelector
import zed.rainxch.rikkaui.foundation.RikkaTheme

/**
 * Documentation page for the Select component.
 *
 * Demonstrates dropdown selection with popup animation variants,
 * placeholder text, and a disabled state.
 */
@Composable
fun SelectDoc() {
    val themeOptions =
        listOf(
            SelectOption("light", stringResource(Res.string.select_demo_light)),
            SelectOption("dark", stringResource(Res.string.select_demo_dark)),
            SelectOption("system", stringResource(Res.string.select_demo_system)),
        )

    val fruitOptions =
        listOf(
            SelectOption("apple", stringResource(Res.string.select_demo_apple)),
            SelectOption("banana", stringResource(Res.string.select_demo_banana)),
            SelectOption("cherry", stringResource(Res.string.select_demo_cherry)),
            SelectOption("grape", stringResource(Res.string.select_demo_grape)),
            SelectOption("mango", stringResource(Res.string.select_demo_mango)),
        )

    ComponentPageHeader(
        name = stringResource(Res.string.component_select_name),
        description = stringResource(Res.string.select_page_desc),
    )

    ComponentFamily(
        related = ComponentFamilies.TEXT_INPUTS,
        currentId = "select",
    )

    TabbedDocPage(
        overview = { SelectOverviewTab(themeOptions, fruitOptions) },
        usage = { SelectUsageTab(themeOptions) },
        api = { SelectApiTab() },
    )
}

@Composable
private fun SelectOverviewTab(
    themeOptions: List<SelectOption>,
    fruitOptions: List<SelectOption>,
) {
    // ─── Popup Animations Demo ──────────────────────────────
    DocSection(stringResource(Res.string.select_section_popup_anims)) {
        var selectedAnim by remember {
            mutableStateOf(PopupAnimation.FadeExpand.name)
        }

        VariantSelector(
            options = PopupAnimation.entries.map { it.name },
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = PopupAnimation.valueOf(selectedAnim)
        var selected by remember { mutableStateOf("") }

        DemoBox {
            Select(
                selectedValue = selected,
                onValueChange = { selected = it },
                options = themeOptions,
                placeholder = stringResource(Res.string.select_demo_choose_theme),
                animation = animation,
                modifier = Modifier.width(280.dp),
            )
        }
    }

    // ─── Scrollable List Demo ───────────────────────────────
    DocSection(stringResource(Res.string.select_section_many_options)) {
        var selected by remember { mutableStateOf("") }

        DemoBox {
            Select(
                selectedValue = selected,
                onValueChange = { selected = it },
                options = fruitOptions,
                placeholder = stringResource(Res.string.select_demo_pick_fruit),
                maxHeight = 150.dp,
                modifier = Modifier.width(280.dp),
            )
        }
    }

    // ─── Disabled State ─────────────────────────────────────
    DocSection(stringResource(Res.string.section_disabled)) {
        DemoBox {
            Select(
                selectedValue = "light",
                onValueChange = {},
                options = themeOptions,
                enabled = false,
                modifier = Modifier.width(280.dp),
            )
        }
    }
}

@Composable
private fun SelectUsageTab(themeOptions: List<SelectOption>) {
    // ─── Code Example ───────────────────────────────────────
    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
val options = listOf(
    SelectOption("light", "Light"),
    SelectOption("dark", "Dark"),
    SelectOption("system", "System"),
)
var selected by remember { mutableStateOf("") }

Select(
    selectedValue = selected,
    onValueChange = { selected = it },
    options = options,
    placeholder = "Choose theme...",
)

Select(
    selectedValue = selected,
    onValueChange = { selected = it },
    options = options,
    animation = PopupAnimation.Fade,
    maxHeight = 300.dp,
)
            """.trimIndent(),
        )
    }

    // ─── Do / Don't ─────────────────────────────────────────
    Spacer(Modifier.height(RikkaTheme.spacing.xl))

    var doSelected by remember { mutableStateOf("") }
    var dontChecked by remember { mutableStateOf(false) }

    DoAndDont(
        doContent = {
            Select(
                selectedValue = doSelected,
                onValueChange = { doSelected = it },
                options = themeOptions,
                placeholder = stringResource(Res.string.select_demo_choose_theme),
                modifier = Modifier.width(200.dp),
            )
        },
        doDescription = stringResource(Res.string.select_do_predefined_desc),
        dontContent = {
            Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm)) {
                Toggle(
                    checked = dontChecked,
                    onCheckedChange = { dontChecked = it },
                    label = "Enable",
                )
            }
        },
        dontDescription = stringResource(Res.string.select_dont_two_options_desc),
    )
}

@Composable
private fun SelectApiTab() {
    // ─── API Reference ──────────────────────────────────────
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "selectedValue",
                    "String",
                    "-",
                    stringResource(Res.string.select_prop_selected_value_desc),
                ),
                PropInfo(
                    "onValueChange",
                    "(String) -> Unit",
                    "-",
                    stringResource(Res.string.select_prop_on_value_change_desc),
                ),
                PropInfo(
                    "options",
                    "List<SelectOption>",
                    "-",
                    stringResource(Res.string.select_prop_options_desc),
                ),
                PropInfo(
                    "placeholder",
                    "String",
                    "\"Select...\"",
                    stringResource(Res.string.select_prop_placeholder_desc),
                ),
                PropInfo(
                    "animation",
                    "PopupAnimation",
                    "FadeExpand",
                    stringResource(Res.string.select_prop_animation_desc),
                ),
                PropInfo(
                    "maxHeight",
                    "Dp",
                    "200.dp",
                    stringResource(Res.string.select_prop_max_height_desc),
                ),
                PropInfo(
                    "enabled",
                    "Boolean",
                    "true",
                    stringResource(Res.string.select_prop_enabled_desc),
                ),
                PropInfo(
                    "label",
                    "String",
                    "\"\"",
                    stringResource(Res.string.select_prop_label_desc),
                ),
            ),
        )
    }
}
