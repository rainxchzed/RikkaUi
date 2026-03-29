package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.Res
import rikkaui.feature.docs.generated.resources.*
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.radio.RadioAnimation
import zed.rainxch.rikkaui.components.ui.radio.RadioButton
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector

/**
 * Documentation page for the RadioButton component.
 *
 * Demonstrates animation styles, radio groups,
 * and disabled states.
 */
@Composable
fun RadioDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_radio_name),
        description = stringResource(Res.string.radio_page_desc),
    )

    // ─── Animations Demo ────────────────────────────────────
    DocSection(stringResource(Res.string.section_animations)) {
        var selectedAnim by remember {
            mutableStateOf(RadioAnimation.Spring.name)
        }

        VariantSelector(
            options = RadioAnimation.entries.map { it.name },
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = RadioAnimation.valueOf(selectedAnim)
        var selected by remember { mutableIntStateOf(0) }

        DemoBox {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
            ) {
                RadioButton(
                    selected = selected == 0,
                    onClick = { selected = 0 },
                    label = stringResource(Res.string.radio_demo_option_a),
                    animation = animation,
                )
                RadioButton(
                    selected = selected == 1,
                    onClick = { selected = 1 },
                    label = stringResource(Res.string.radio_demo_option_b),
                    animation = animation,
                )
                RadioButton(
                    selected = selected == 2,
                    onClick = { selected = 2 },
                    label = stringResource(Res.string.radio_demo_option_c),
                    animation = animation,
                )
            }
        }
    }

    // ─── Radio Group Demo ───────────────────────────────────
    DocSection(stringResource(Res.string.radio_section_radio_group)) {
        var plan by remember { mutableIntStateOf(1) }

        DemoBox {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
            ) {
                RadioButton(
                    selected = plan == 0,
                    onClick = { plan = 0 },
                    label = stringResource(Res.string.radio_demo_free),
                )
                RadioButton(
                    selected = plan == 1,
                    onClick = { plan = 1 },
                    label = stringResource(Res.string.radio_demo_pro),
                )
                RadioButton(
                    selected = plan == 2,
                    onClick = { plan = 2 },
                    label = stringResource(Res.string.radio_demo_enterprise),
                )
            }
        }
    }

    // ─── Disabled State ─────────────────────────────────────
    DocSection(stringResource(Res.string.section_disabled)) {
        DemoBox {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
            ) {
                RadioButton(
                    selected = true,
                    onClick = {},
                    label = stringResource(Res.string.radio_demo_selected_disabled),
                    enabled = false,
                )
                RadioButton(
                    selected = false,
                    onClick = {},
                    label = stringResource(Res.string.radio_demo_unselected_disabled),
                    enabled = false,
                )
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
var selected by remember { mutableIntStateOf(0) }

RadioButton(
    selected = selected == 0,
    onClick = { selected = 0 },
    label = "Option A",
)
RadioButton(
    selected = selected == 1,
    onClick = { selected = 1 },
    label = "Option B",
    animation = RadioAnimation.None,
)
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "selected",
                    "Boolean",
                    "-",
                    stringResource(Res.string.radio_prop_selected_desc),
                ),
                PropInfo(
                    "onClick",
                    "() -> Unit",
                    "-",
                    stringResource(Res.string.radio_prop_onclick_desc),
                ),
                PropInfo(
                    "animation",
                    "RadioAnimation",
                    "Spring",
                    stringResource(Res.string.radio_prop_animation_desc),
                ),
                PropInfo(
                    "enabled",
                    "Boolean",
                    "true",
                    stringResource(Res.string.radio_prop_enabled_desc),
                ),
                PropInfo(
                    "label",
                    "String",
                    "\"\"",
                    stringResource(Res.string.radio_prop_label_desc),
                ),
            ),
        )
    }
}
