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
        name = "RadioButton",
        description = "A selection control rendered as a circle with an animated " +
            "inner dot. Use in groups for single-choice selection.",
    )

    // ─── Animations Demo ────────────────────────────────────
    DocSection("Animations") {
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
                    label = "Option A",
                    animation = animation,
                )
                RadioButton(
                    selected = selected == 1,
                    onClick = { selected = 1 },
                    label = "Option B",
                    animation = animation,
                )
                RadioButton(
                    selected = selected == 2,
                    onClick = { selected = 2 },
                    label = "Option C",
                    animation = animation,
                )
            }
        }
    }

    // ─── Radio Group Demo ───────────────────────────────────
    DocSection("Radio Group") {
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
                    label = "Free",
                )
                RadioButton(
                    selected = plan == 1,
                    onClick = { plan = 1 },
                    label = "Pro - \$9/mo",
                )
                RadioButton(
                    selected = plan == 2,
                    onClick = { plan = 2 },
                    label = "Enterprise - \$29/mo",
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
                RadioButton(
                    selected = true,
                    onClick = {},
                    label = "Selected & disabled",
                    enabled = false,
                )
                RadioButton(
                    selected = false,
                    onClick = {},
                    label = "Unselected & disabled",
                    enabled = false,
                )
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
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
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "selected",
                    "Boolean",
                    "-",
                    "Whether the radio button is selected.",
                ),
                PropInfo(
                    "onClick",
                    "() -> Unit",
                    "-",
                    "Called when the radio button is clicked.",
                ),
                PropInfo(
                    "animation",
                    "RadioAnimation",
                    "Spring",
                    "Animation: Spring, Tween, None.",
                ),
                PropInfo(
                    "enabled",
                    "Boolean",
                    "true",
                    "Whether the radio button is interactive.",
                ),
                PropInfo(
                    "label",
                    "String",
                    "\"\"",
                    "Label text rendered beside the circle and used for accessibility.",
                ),
            ),
        )
    }
}
