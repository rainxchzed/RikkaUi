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
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.checkbox.Checkbox
import zed.rainxch.rikkaui.components.ui.checkbox.CheckboxAnimation
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector

/**
 * Documentation page for the Checkbox component.
 *
 * Demonstrates animation styles, labeled checkboxes,
 * and disabled states.
 */
@Composable
fun CheckboxDoc() {
    ComponentPageHeader(
        name = "Checkbox",
        description = "A boolean toggle rendered as a square box with an " +
            "animated checkmark. Supports spring, tween, and instant animations.",
    )

    // ─── Animations Demo ────────────────────────────────────
    DocSection("Animations") {
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
                label = "Accept terms and conditions",
                animation = animation,
            )
        }
    }

    // ─── With Labels Demo ───────────────────────────────────
    DocSection("With Labels") {
        var optionA by remember { mutableStateOf(true) }
        var optionB by remember { mutableStateOf(false) }
        var optionC by remember { mutableStateOf(false) }

        DemoBox {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
            ) {
                Checkbox(
                    checked = optionA,
                    onCheckedChange = { optionA = it },
                    label = "Email notifications",
                )
                Checkbox(
                    checked = optionB,
                    onCheckedChange = { optionB = it },
                    label = "SMS notifications",
                )
                Checkbox(
                    checked = optionC,
                    onCheckedChange = { optionC = it },
                    label = "Push notifications",
                )
            }
        }
    }

    // ─── Disabled State ─────────────────────────────────────
    DocSection("Disabled") {
        DemoBox {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
            ) {
                Checkbox(
                    checked = true,
                    onCheckedChange = {},
                    label = "Checked & disabled",
                    enabled = false,
                )
                Checkbox(
                    checked = false,
                    onCheckedChange = {},
                    label = "Unchecked & disabled",
                    enabled = false,
                )
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
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

    // ─── API Reference ──────────────────────────────────────
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "checked",
                    "Boolean",
                    "-",
                    "Whether the checkbox is checked.",
                ),
                PropInfo(
                    "onCheckedChange",
                    "(Boolean) -> Unit",
                    "-",
                    "Called when the checkbox state changes.",
                ),
                PropInfo(
                    "animation",
                    "CheckboxAnimation",
                    "Spring",
                    "Animation: Spring, Tween, None.",
                ),
                PropInfo(
                    "enabled",
                    "Boolean",
                    "true",
                    "Whether the checkbox is interactive.",
                ),
                PropInfo(
                    "label",
                    "String",
                    "\"\"",
                    "Label text rendered beside the checkbox and used for accessibility.",
                ),
            ),
        )
    }
}
