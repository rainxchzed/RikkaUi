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
import zed.rainxch.rikkaui.components.theme.RikkaTheme
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

/**
 * Documentation page for the ToggleGroup component.
 *
 * Demonstrates grouped toggle buttons with Default/Outline
 * variants and animation strategies.
 */
@Composable
fun ToggleGroupDoc() {
    ComponentPageHeader(
        name = "ToggleGroup",
        description = "A horizontal group of toggle buttons for "
            + "mutually exclusive selection.",
    )

    // ─── Variants ───────────────────────────────────────────
    DocSection("Variants") {
        var selectedVariant by remember { mutableStateOf("Default") }
        var selected by remember { mutableStateOf(0) }

        VariantSelector(
            options = listOf("Default", "Outline"),
            selected = selectedVariant,
            onSelect = { selectedVariant = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val variant = when (selectedVariant) {
            "Outline" -> ToggleGroupVariant.Outline
            else -> ToggleGroupVariant.Default
        }

        DemoBox {
            ToggleGroup {
                ToggleGroupItem(
                    text = "Bold",
                    selected = selected == 0,
                    onClick = { selected = 0 },
                    variant = variant,
                )
                ToggleGroupItem(
                    text = "Italic",
                    selected = selected == 1,
                    onClick = { selected = 1 },
                    variant = variant,
                )
                ToggleGroupItem(
                    text = "Underline",
                    selected = selected == 2,
                    onClick = { selected = 2 },
                    variant = variant,
                )
            }
        }
    }

    // ─── Animation Variants ─────────────────────────────────
    DocSection("Animations") {
        var selectedAnim by remember { mutableStateOf("Spring") }
        var selected by remember { mutableStateOf(0) }

        VariantSelector(
            options = listOf("Spring", "Tween", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = when (selectedAnim) {
            "Tween" -> ToggleGroupAnimation.Tween
            "None" -> ToggleGroupAnimation.None
            else -> ToggleGroupAnimation.Spring
        }

        DemoBox {
            ToggleGroup {
                ToggleGroupItem(
                    text = "Day",
                    selected = selected == 0,
                    onClick = { selected = 0 },
                    animation = animation,
                )
                ToggleGroupItem(
                    text = "Week",
                    selected = selected == 1,
                    onClick = { selected = 1 },
                    animation = animation,
                )
                ToggleGroupItem(
                    text = "Month",
                    selected = selected == 2,
                    onClick = { selected = 2 },
                    animation = animation,
                )
            }
        }
    }

    // ─── Outline with Selection Text ────────────────────────
    DocSection("Outline Variant") {
        var selected by remember { mutableStateOf(0) }
        val options = listOf("Left", "Center", "Right")

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
                    "Selected: ${options[selected]}",
                    variant = TextVariant.Muted,
                )
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
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
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "text", "String", "required",
                    "Label text displayed in the toggle item.",
                ),
                PropInfo(
                    "selected", "Boolean", "required",
                    "Whether this item is currently active.",
                ),
                PropInfo(
                    "onClick", "() -> Unit", "required",
                    "Called when the item is clicked.",
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier for layout and decoration.",
                ),
                PropInfo(
                    "variant", "ToggleGroupVariant", "Default",
                    "Visual variant: Default, Outline.",
                ),
                PropInfo(
                    "animation", "ToggleGroupAnimation", "Spring",
                    "Animation strategy: Spring, Tween, None.",
                ),
                PropInfo(
                    "selectedColor", "Color", "foreground",
                    "Override for selected foreground color.",
                ),
                PropInfo(
                    "unselectedColor", "Color", "mutedForeground",
                    "Override for unselected foreground color.",
                ),
            ),
        )
    }
}
