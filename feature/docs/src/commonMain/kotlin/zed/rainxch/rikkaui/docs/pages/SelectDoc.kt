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
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.PopupAnimation
import zed.rainxch.rikkaui.components.ui.select.Select
import zed.rainxch.rikkaui.components.ui.select.SelectOption
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector

/**
 * Documentation page for the Select component.
 *
 * Demonstrates dropdown selection with popup animation variants,
 * placeholder text, and a disabled state.
 */
@Composable
fun SelectDoc() {
    val themeOptions = listOf(
        SelectOption("light", "Light"),
        SelectOption("dark", "Dark"),
        SelectOption("system", "System"),
    )

    val fruitOptions = listOf(
        SelectOption("apple", "Apple"),
        SelectOption("banana", "Banana"),
        SelectOption("cherry", "Cherry"),
        SelectOption("grape", "Grape"),
        SelectOption("mango", "Mango"),
    )

    ComponentPageHeader(
        name = "Select",
        description = "A dropdown select component with animated popup, " +
            "hover highlighting, and selection indicators.",
    )

    // ─── Popup Animations Demo ──────────────────────────────
    DocSection("Popup Animations") {
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
                placeholder = "Choose theme...",
                animation = animation,
                modifier = Modifier.width(280.dp),
            )
        }
    }

    // ─── Scrollable List Demo ───────────────────────────────
    DocSection("With Many Options") {
        var selected by remember { mutableStateOf("") }

        DemoBox {
            Select(
                selectedValue = selected,
                onValueChange = { selected = it },
                options = fruitOptions,
                placeholder = "Pick a fruit...",
                maxHeight = 150.dp,
                modifier = Modifier.width(280.dp),
            )
        }
    }

    // ─── Disabled State ─────────────────────────────────────
    DocSection("Disabled") {
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

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
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

    // ─── API Reference ──────────────────────────────────────
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "selectedValue",
                    "String",
                    "-",
                    "Currently selected value (matches SelectOption.value).",
                ),
                PropInfo(
                    "onValueChange",
                    "(String) -> Unit",
                    "-",
                    "Called with the new value on selection.",
                ),
                PropInfo(
                    "options",
                    "List<SelectOption>",
                    "-",
                    "Available options with value and label.",
                ),
                PropInfo(
                    "placeholder",
                    "String",
                    "\"Select...\"",
                    "Text shown when no option is selected.",
                ),
                PropInfo(
                    "animation",
                    "PopupAnimation",
                    "FadeExpand",
                    "Popup animation: FadeExpand, Fade, None.",
                ),
                PropInfo(
                    "maxHeight",
                    "Dp",
                    "200.dp",
                    "Maximum dropdown height before scrolling.",
                ),
                PropInfo(
                    "enabled",
                    "Boolean",
                    "true",
                    "Whether the select is interactive.",
                ),
                PropInfo(
                    "label",
                    "String",
                    "\"\"",
                    "Accessibility label for screen readers.",
                ),
            ),
        )
    }
}
