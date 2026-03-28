package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.breadcrumb.Breadcrumb
import zed.rainxch.rikkaui.components.ui.breadcrumb.BreadcrumbAnimation
import zed.rainxch.rikkaui.components.ui.breadcrumb.BreadcrumbItem
import zed.rainxch.rikkaui.components.ui.breadcrumb.BreadcrumbItemData
import zed.rainxch.rikkaui.components.ui.breadcrumb.BreadcrumbSeparator
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
 * Documentation page for the Breadcrumb component.
 *
 * Covers basic usage, data-driven lists, ellipsis
 * collapsing, code snippets, and full API reference.
 */
@Composable
fun BreadcrumbDoc() {
    ComponentPageHeader(
        name = "Breadcrumb",
        description = "Displays the user's current location in a"
            + " page hierarchy with clickable navigation links.",
    )

    // ─── Basic Usage ──────────────────────────────────────
    DocSection("Basic Usage") {
        DemoBox {
            Breadcrumb {
                BreadcrumbItem("Home", onClick = {})
                BreadcrumbSeparator()
                BreadcrumbItem("Products", onClick = {})
                BreadcrumbSeparator()
                BreadcrumbItem("Widgets")
            }
        }
    }

    // ─── Data-Driven ──────────────────────────────────────
    DocSection("Data-Driven") {
        var selectedAnim by remember { mutableStateOf("None") }

        VariantSelector(
            options = listOf("None", "Fade", "Slide"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = when (selectedAnim) {
            "Fade" -> BreadcrumbAnimation.Fade
            "Slide" -> BreadcrumbAnimation.Slide
            else -> BreadcrumbAnimation.None
        }

        DemoBox {
            Breadcrumb(
                items = listOf(
                    BreadcrumbItemData("Home", onClick = {}),
                    BreadcrumbItemData(
                        "Products",
                        onClick = {},
                    ),
                    BreadcrumbItemData(
                        "Widgets",
                        onClick = {},
                    ),
                    BreadcrumbItemData("Current Page"),
                ),
                animation = animation,
            )
        }
    }

    // ─── Ellipsis Collapsing ──────────────────────────────
    DocSection("Ellipsis Collapsing") {
        DemoBox {
            Breadcrumb(
                items = listOf(
                    BreadcrumbItemData("Home", onClick = {}),
                    BreadcrumbItemData(
                        "Documents",
                        onClick = {},
                    ),
                    BreadcrumbItemData(
                        "Projects",
                        onClick = {},
                    ),
                    BreadcrumbItemData(
                        "Design",
                        onClick = {},
                    ),
                    BreadcrumbItemData("Components"),
                ),
                maxVisibleItems = 3,
            )
        }
    }

    // ─── Usage ────────────────────────────────────────────
    DocSection("Usage") {
        CodeBlock(
            """
// Manual composition — full control
Breadcrumb {
    BreadcrumbItem("Home", onClick = { nav() })
    BreadcrumbSeparator()
    BreadcrumbItem("Products", onClick = { nav() })
    BreadcrumbSeparator()
    BreadcrumbItem("Current Page")
}

// Data-driven — pass a list, get separators for free
Breadcrumb(
    items = listOf(
        BreadcrumbItemData("Home", onClick = { nav() }),
        BreadcrumbItemData("Products", onClick = { nav() }),
        BreadcrumbItemData("Current Page"),
    ),
    animation = BreadcrumbAnimation.Fade,
    maxVisibleItems = 3,
)
            """.trimIndent(),
        )
    }

    // ─── API Reference ────────────────────────────────────
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "items",
                    "List<BreadcrumbItemData>",
                    "required",
                    "List of breadcrumb items to display.",
                ),
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    "Modifier for layout and decoration.",
                ),
                PropInfo(
                    "animation",
                    "BreadcrumbAnimation",
                    "None",
                    "Entrance animation: None, Fade, Slide.",
                ),
                PropInfo(
                    "separator",
                    "(@Composable () -> Unit)?",
                    "null",
                    "Custom separator composable. Defaults"
                        + " to /.",
                ),
                PropInfo(
                    "maxVisibleItems",
                    "Int",
                    "0",
                    "Max items before collapsing middle"
                        + " items into an ellipsis. 0 = show all.",
                ),
                PropInfo(
                    "onEllipsisClick",
                    "(() -> Unit)?",
                    "null",
                    "Click handler for the ellipsis element.",
                ),
            ),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        Text(
            "BreadcrumbItemData",
            variant = TextVariant.Large,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        PropsTable(
            listOf(
                PropInfo(
                    "label",
                    "String",
                    "required",
                    "Display text for the breadcrumb item.",
                ),
                PropInfo(
                    "onClick",
                    "(() -> Unit)?",
                    "null",
                    "Click handler. Null = current page"
                        + " (non-clickable).",
                ),
            ),
        )
    }
}
