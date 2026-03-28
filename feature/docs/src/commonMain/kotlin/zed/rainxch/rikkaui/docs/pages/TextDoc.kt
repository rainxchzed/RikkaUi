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
 * Documentation page for the Text component.
 *
 * Demonstrates all typography variants, color overrides,
 * and selectable text.
 */
@Composable
fun TextDoc() {
    ComponentPageHeader(
        name = "Text",
        description = "A typography component that renders text with "
            + "theme-aware styles and heading semantics.",
    )

    // ─── Variants ───────────────────────────────────────────
    DocSection("Variants") {
        var selected by remember { mutableStateOf("P") }

        VariantSelector(
            options = listOf(
                "H1", "H2", "H3", "H4",
                "P", "Lead", "Large", "Small", "Muted",
            ),
            selected = selected,
            onSelect = { selected = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val variant = when (selected) {
            "H1" -> TextVariant.H1
            "H2" -> TextVariant.H2
            "H3" -> TextVariant.H3
            "H4" -> TextVariant.H4
            "Lead" -> TextVariant.Lead
            "Large" -> TextVariant.Large
            "Small" -> TextVariant.Small
            "Muted" -> TextVariant.Muted
            else -> TextVariant.P
        }

        DemoBox {
            Text(
                text = "The quick brown fox jumps over the lazy dog",
                variant = variant,
            )
        }
    }

    // ─── All Variants Preview ───────────────────────────────
    DocSection("All Variants") {
        DemoBox {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
            ) {
                Text("Heading 1", variant = TextVariant.H1)
                Text("Heading 2", variant = TextVariant.H2)
                Text("Heading 3", variant = TextVariant.H3)
                Text("Heading 4", variant = TextVariant.H4)
                Text("Paragraph text", variant = TextVariant.P)
                Text("Lead text for introductions", variant = TextVariant.Lead)
                Text("Large text", variant = TextVariant.Large)
                Text("Small text", variant = TextVariant.Small)
                Text("Muted text for secondary info", variant = TextVariant.Muted)
            }
        }
    }

    // ─── Selectable Text ────────────────────────────────────
    DocSection("Selectable Text") {
        DemoBox {
            Text(
                text = "This text can be selected and copied.",
                variant = TextVariant.P,
                selectable = true,
            )
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
        CodeBlock(
            """
Text("Hello world")
Text("Page title", variant = TextVariant.H1)
Text("Subtle info", variant = TextVariant.Muted)
Text("Custom color", color = RikkaTheme.colors.destructive)
Text("Selectable", selectable = true)
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "text", "String", "required",
                    "The text content to display.",
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier for layout and decoration.",
                ),
                PropInfo(
                    "variant", "TextVariant", "P",
                    "Typography variant: H1, H2, H3, H4, P, "
                        + "Lead, Large, Small, Muted.",
                ),
                PropInfo(
                    "color", "Color", "Unspecified",
                    "Override color. Uses variant default when unspecified.",
                ),
                PropInfo(
                    "textAlign", "TextAlign?", "null",
                    "Text alignment within the composable.",
                ),
                PropInfo(
                    "overflow", "TextOverflow", "Clip",
                    "How to handle text overflow.",
                ),
                PropInfo(
                    "maxLines", "Int", "Int.MAX_VALUE",
                    "Maximum number of lines to display.",
                ),
                PropInfo(
                    "minLines", "Int", "1",
                    "Minimum number of lines to occupy.",
                ),
                PropInfo(
                    "selectable", "Boolean", "false",
                    "When true, wraps text in a SelectionContainer.",
                ),
                PropInfo(
                    "style", "TextStyle", "TextStyle.Default",
                    "Override style merged on top of variant style.",
                ),
            ),
        )
    }
}
