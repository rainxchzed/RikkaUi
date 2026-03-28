package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.accordion.AccordionAnimation
import zed.rainxch.rikkaui.components.ui.accordion.AccordionItem
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
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
 * Documentation page for the Accordion component.
 *
 * Demonstrates expandable sections with different animation
 * styles and grouped accordion patterns.
 */
@Composable
fun AccordionDoc() {
    ComponentPageHeader(
        name = "Accordion",
        description = "Expandable content sections with animated chevron indicators.",
    )

    // ─── Basic Accordion ────────────────────────────────────
    DocSection("Basic Accordion") {
        DemoBox {
            Column(modifier = Modifier.fillMaxWidth()) {
                var faqOneOpen by remember { mutableStateOf(false) }
                var faqTwoOpen by remember { mutableStateOf(false) }
                var faqThreeOpen by remember { mutableStateOf(false) }

                AccordionItem(
                    title = "Is it accessible?",
                    expanded = faqOneOpen,
                    onExpandedChange = { faqOneOpen = it },
                ) {
                    Text(
                        "Yes. It uses proper semantics with "
                            + "contentDescription and stateDescription.",
                        variant = TextVariant.Muted,
                    )
                }

                AccordionItem(
                    title = "Is it styled?",
                    expanded = faqTwoOpen,
                    onExpandedChange = { faqTwoOpen = it },
                ) {
                    Text(
                        "Yes. It follows the RikkaUI design system "
                            + "with theme tokens for colors and spacing.",
                        variant = TextVariant.Muted,
                    )
                }

                AccordionItem(
                    title = "Is it animated?",
                    expanded = faqThreeOpen,
                    onExpandedChange = { faqThreeOpen = it },
                ) {
                    Text(
                        "Yes. Choose from Spring, Tween, or None "
                            + "animation styles.",
                        variant = TextVariant.Muted,
                    )
                }
            }
        }
    }

    // ─── Animation Styles ───────────────────────────────────
    DocSection("Animation Styles") {
        var selectedAnim by remember { mutableStateOf("Spring") }

        VariantSelector(
            options = listOf("Spring", "Tween", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = when (selectedAnim) {
            "Tween" -> AccordionAnimation.Tween
            "None" -> AccordionAnimation.None
            else -> AccordionAnimation.Spring
        }

        DemoBox {
            Column(modifier = Modifier.fillMaxWidth()) {
                var expanded by remember { mutableStateOf(false) }

                AccordionItem(
                    title = "Animation: $selectedAnim",
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    animation = animation,
                ) {
                    Text(
                        "This section uses the $selectedAnim animation. "
                            + "Toggle it to see the difference.",
                        variant = TextVariant.Muted,
                    )
                }
            }
        }
    }

    // ─── Custom Chevron Icon ────────────────────────────────
    DocSection("Custom Chevron Icon") {
        DemoBox {
            Column(modifier = Modifier.fillMaxWidth()) {
                var expanded by remember { mutableStateOf(false) }

                AccordionItem(
                    title = "With ChevronDown icon",
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    chevronIcon = RikkaIcons.ChevronDown,
                ) {
                    Text(
                        "This accordion uses ChevronDown instead "
                            + "of the default ChevronRight.",
                        variant = TextVariant.Muted,
                    )
                }
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
        CodeBlock(
            """
var expanded by remember { mutableStateOf(false) }

AccordionItem(
    title = "Is it accessible?",
    expanded = expanded,
    onExpandedChange = { expanded = it },
) {
    Text("Yes. It uses WAI-ARIA patterns.")
}

// With tween animation and custom icon
AccordionItem(
    title = "Details",
    expanded = expanded,
    onExpandedChange = { expanded = it },
    animation = AccordionAnimation.Tween,
    chevronIcon = RikkaIcons.ChevronDown,
) {
    Text("Smooth eased transition")
}

// Multiple items in a Column
Column {
    items.forEachIndexed { index, item ->
        var open by remember { mutableStateOf(false) }
        AccordionItem(
            title = item.title,
            expanded = open,
            onExpandedChange = { open = it },
        ) {
            Text(item.description)
        }
    }
}
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "title", "String", "required",
                    "Header text in the clickable title row.",
                ),
                PropInfo(
                    "expanded", "Boolean", "required",
                    "Whether the content section is visible.",
                ),
                PropInfo(
                    "onExpandedChange", "(Boolean) -> Unit", "required",
                    "Called when the user toggles the section.",
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier for layout and decoration.",
                ),
                PropInfo(
                    "animation", "AccordionAnimation", "Spring",
                    "Animation style: Spring, Tween, None.",
                ),
                PropInfo(
                    "chevronIcon", "ImageVector",
                    "RikkaIcons.ChevronRight",
                    "Icon for the expand indicator. Rotates 90 degrees.",
                ),
                PropInfo(
                    "content", "() -> Unit", "required",
                    "The expandable content slot.",
                ),
            ),
        )
    }
}
