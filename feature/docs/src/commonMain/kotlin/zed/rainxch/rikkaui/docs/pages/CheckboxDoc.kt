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
import rikkaui.feature.docs.generated.resources.*
import rikkaui.feature.docs.generated.resources.Res
import zed.rainxch.rikkaui.components.ui.checkbox.Checkbox
import zed.rainxch.rikkaui.components.ui.checkbox.CheckboxAnimation
import zed.rainxch.rikkaui.components.ui.radio.RadioButton
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
 * Documentation page for the Checkbox component.
 *
 * Demonstrates animation styles, labeled checkboxes,
 * and disabled states.
 */
@Composable
fun CheckboxDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_checkbox_name),
        description = stringResource(Res.string.checkbox_page_desc),
    )

    ComponentFamily(
        related = ComponentFamilies.SELECTION,
        currentId = "checkbox",
    )

    TabbedDocPage(
        overview = { CheckboxOverviewTab() },
        usage = { CheckboxUsageTab() },
        api = { CheckboxApiTab() },
    )
}

@Composable
private fun CheckboxOverviewTab() {
    // ─── Animations Demo ────────────────────────────────────
    DocSection(stringResource(Res.string.section_animations)) {
        var selectedAnim by remember {
            mutableStateOf(CheckboxAnimation.Spring.name)
        }

        VariantSelector(
            options = CheckboxAnimation.entries.map { it.name },
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = CheckboxAnimation.valueOf(selectedAnim)
        var checked by remember { mutableStateOf(false) }

        DemoBox {
            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it },
                label = stringResource(Res.string.checkbox_demo_accept_terms),
                animation = animation,
            )
        }
    }

    // ─── With Labels Demo ───────────────────────────────────
    DocSection(stringResource(Res.string.checkbox_section_with_labels)) {
        var optionA by remember { mutableStateOf(true) }
        var optionB by remember { mutableStateOf(false) }
        var optionC by remember { mutableStateOf(false) }

        DemoBox {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
            ) {
                Checkbox(
                    checked = optionA,
                    onCheckedChange = { optionA = it },
                    label = stringResource(Res.string.checkbox_demo_email_notifications),
                )
                Checkbox(
                    checked = optionB,
                    onCheckedChange = { optionB = it },
                    label = stringResource(Res.string.checkbox_demo_sms_notifications),
                )
                Checkbox(
                    checked = optionC,
                    onCheckedChange = { optionC = it },
                    label = stringResource(Res.string.checkbox_demo_push_notifications),
                )
            }
        }
    }

    // ─── Disabled State ─────────────────────────────────────
    DocSection(stringResource(Res.string.section_disabled)) {
        DemoBox {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
            ) {
                Checkbox(
                    checked = true,
                    onCheckedChange = {},
                    label = stringResource(Res.string.checkbox_demo_checked_disabled),
                    enabled = false,
                )
                Checkbox(
                    checked = false,
                    onCheckedChange = {},
                    label = stringResource(Res.string.checkbox_demo_unchecked_disabled),
                    enabled = false,
                )
            }
        }
    }
}

@Composable
private fun CheckboxUsageTab() {
    // ─── Code Example ───────────────────────────────────────
    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
var accepted by remember { mutableStateOf(false) }

Checkbox(
    checked = accepted,
    onCheckedChange = { accepted = it },
    label = "Accept terms",
)

Checkbox(
    checked = accepted,
    onCheckedChange = { accepted = it },
    animation = CheckboxAnimation.None,
)
            """.trimIndent(),
        )
    }

    // ─── Do / Don't ─────────────────────────────────────────
    Spacer(Modifier.height(RikkaTheme.spacing.xl))

    var doCheckedA by remember { mutableStateOf(true) }
    var doCheckedB by remember { mutableStateOf(false) }
    var dontSelected by remember { mutableIntStateOf(0) }

    DoAndDont(
        doContent = {
            Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm)) {
                Checkbox(
                    checked = doCheckedA,
                    onCheckedChange = { doCheckedA = it },
                    label = stringResource(Res.string.checkbox_demo_email_notifications),
                )
                Checkbox(
                    checked = doCheckedB,
                    onCheckedChange = { doCheckedB = it },
                    label = stringResource(Res.string.checkbox_demo_sms_notifications),
                )
            }
        },
        doDescription = stringResource(Res.string.checkbox_do_independent_desc),
        dontContent = {
            Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm)) {
                RadioButton(
                    selected = dontSelected == 0,
                    onClick = { dontSelected = 0 },
                    label = stringResource(Res.string.radio_demo_option_a),
                )
                RadioButton(
                    selected = dontSelected == 1,
                    onClick = { dontSelected = 1 },
                    label = stringResource(Res.string.radio_demo_option_b),
                )
            }
        },
        dontDescription = stringResource(Res.string.checkbox_dont_exclusive_desc),
    )
}

@Composable
private fun CheckboxApiTab() {
    // ─── API Reference ──────────────────────────────────────
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "checked",
                    "Boolean",
                    "-",
                    stringResource(Res.string.checkbox_prop_checked_desc),
                ),
                PropInfo(
                    "onCheckedChange",
                    "(Boolean) -> Unit",
                    "-",
                    stringResource(Res.string.checkbox_prop_on_checked_change_desc),
                ),
                PropInfo(
                    "animation",
                    "CheckboxAnimation",
                    "Spring",
                    stringResource(Res.string.checkbox_prop_animation_desc),
                ),
                PropInfo(
                    "enabled",
                    "Boolean",
                    "true",
                    stringResource(Res.string.checkbox_prop_enabled_desc),
                ),
                PropInfo(
                    "label",
                    "String",
                    "\"\"",
                    stringResource(Res.string.checkbox_prop_label_desc),
                ),
            ),
        )
    }
}
