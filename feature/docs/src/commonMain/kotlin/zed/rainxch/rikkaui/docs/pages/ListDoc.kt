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
import zed.rainxch.rikkaui.components.ui.list.ListVariant
import zed.rainxch.rikkaui.components.ui.list.RikkaList
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
 * Documentation page for the RikkaList component.
 *
 * Showcases unordered, ordered, and unstyled list variants
 * with live demos, code snippets, and an API reference.
 */
@Composable
fun ListDoc() {
    ComponentPageHeader(
        name = "List",
        description = "Styled lists with bullet points, numbers, "
            + "or custom content per item.",
    )

    // ─── Variants ─────────────────────────────────────────
    DocSection("Variants") {
        var selected by remember { mutableStateOf("Unordered") }

        VariantSelector(
            options = listOf("Unordered", "Ordered", "None"),
            selected = selected,
            onSelect = { selected = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val variant = when (selected) {
            "Ordered" -> ListVariant.Ordered
            "None" -> ListVariant.None
            else -> ListVariant.Unordered
        }

        DemoBox {
            RikkaList(
                items = listOf(
                    "Clone the repository",
                    "Install dependencies",
                    "Run the development server",
                ),
                variant = variant,
            )
        }
    }

    // ─── Unordered List ───────────────────────────────────
    DocSection("Unordered List") {
        DemoBox {
            RikkaList(
                items = listOf(
                    "Foundation-only, no Material3",
                    "Spring-physics animations",
                    "Full theme customization",
                    "Works on all platforms",
                ),
            )
        }
    }

    // ─── Ordered List ─────────────────────────────────────
    DocSection("Ordered List") {
        DemoBox {
            RikkaList(
                items = listOf(
                    "Add the dependency to your project",
                    "Wrap your app in RikkaTheme",
                    "Use any component from the library",
                    "Customize with theme tokens",
                ),
                variant = ListVariant.Ordered,
            )
        }
    }

    // ─── Custom Content ───────────────────────────────────
    DocSection("Custom Content") {
        DemoBox {
            Column {
                Text(
                    "Features",
                    variant = TextVariant.H4,
                )
                Spacer(Modifier.height(RikkaTheme.spacing.sm))
                RikkaList {
                    ListItem {
                        Text(
                            "Composable DSL for complex list items",
                            variant = TextVariant.P,
                        )
                    }
                    ListItem {
                        Text(
                            "Theme-aware spacing and typography",
                            variant = TextVariant.P,
                        )
                    }
                    ListItem {
                        Text(
                            "Accessible by default",
                            variant = TextVariant.P,
                        )
                    }
                }
            }
        }
    }

    // ─── Usage ────────────────────────────────────────────
    DocSection("Usage") {
        CodeBlock(
            """
// Bullet list (default)
RikkaList(
    items = listOf("First", "Second", "Third"),
)

// Numbered list
RikkaList(
    items = listOf("Step one", "Step two"),
    variant = ListVariant.Ordered,
)

// Custom content per item
RikkaList(variant = ListVariant.Unordered) {
    ListItem { Text("Custom composable") }
    ListItem { Text("Another item") }
}
            """.trimIndent(),
        )
    }

    // ─── API Reference ────────────────────────────────────
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "items",
                    "List<String>",
                    "-",
                    "List of string items to render.",
                ),
                PropInfo(
                    "variant",
                    "ListVariant",
                    "Unordered",
                    "List style: Unordered, Ordered, None.",
                ),
                PropInfo(
                    "textVariant",
                    "TextVariant",
                    "P",
                    "Typography variant for item text.",
                ),
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    "Modifier for layout and decoration.",
                ),
            ),
        )
    }
}
