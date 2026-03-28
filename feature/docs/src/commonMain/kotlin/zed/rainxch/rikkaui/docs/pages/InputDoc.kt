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
import zed.rainxch.rikkaui.components.theme.RikkaTheme
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

/**
 * Documentation page for the Input component.
 *
 * Demonstrates focus animations, icon slots, clearable mode,
 * character count, and disabled state.
 */
@Composable
fun InputDoc() {
    ComponentPageHeader(
        name = "Input",
        description = "A single-line text field with animated focus states, " +
            "icon slots, and optional character count.",
    )

    // ─── Focus Animations Demo ──────────────────────────────
    DocSection("Focus Animations") {
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
                placeholder = "Click to see focus animation...",
                animation = animation,
                modifier = Modifier.width(320.dp),
            )
        }
    }

    // ─── With Icons Demo ────────────────────────────────────
    DocSection("With Icons") {
        var search by remember { mutableStateOf("") }

        DemoBox {
            Input(
                value = search,
                onValueChange = { search = it },
                placeholder = "Search...",
                leadingIcon = RikkaIcons.Search,
                clearable = true,
                modifier = Modifier.width(320.dp),
            )
        }
    }

    // ─── Character Count Demo ───────────────────────────────
    DocSection("Character Count") {
        var username by remember { mutableStateOf("") }

        DemoBox {
            Input(
                value = username,
                onValueChange = { username = it },
                placeholder = "Username",
                maxLength = 32,
                showCharCount = true,
                modifier = Modifier.width(320.dp),
            )
        }
    }

    // ─── Disabled State ─────────────────────────────────────
    DocSection("Disabled") {
        DemoBox {
            Input(
                value = "Read only content",
                onValueChange = {},
                enabled = false,
                modifier = Modifier.width(320.dp),
            )
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
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
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "value",
                    "String",
                    "-",
                    "Current text value.",
                ),
                PropInfo(
                    "onValueChange",
                    "(String) -> Unit",
                    "-",
                    "Called when the text changes.",
                ),
                PropInfo(
                    "placeholder",
                    "String",
                    "\"\"",
                    "Placeholder text shown when empty.",
                ),
                PropInfo(
                    "animation",
                    "InputAnimation",
                    "Glow",
                    "Focus animation: Glow, Color, None.",
                ),
                PropInfo(
                    "leadingIcon",
                    "ImageVector?",
                    "null",
                    "Icon displayed before the text field.",
                ),
                PropInfo(
                    "trailingIcon",
                    "ImageVector?",
                    "null",
                    "Icon displayed after the text field.",
                ),
                PropInfo(
                    "clearable",
                    "Boolean",
                    "false",
                    "Shows a clear (X) button when text is non-empty.",
                ),
                PropInfo(
                    "maxLength",
                    "Int?",
                    "null",
                    "Maximum character limit.",
                ),
                PropInfo(
                    "showCharCount",
                    "Boolean",
                    "false",
                    "Displays character count when maxLength is set.",
                ),
                PropInfo(
                    "enabled",
                    "Boolean",
                    "true",
                    "Whether the input is interactive.",
                ),
            ),
        )
    }
}
