package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.*
import rikkaui.feature.docs.generated.resources.Res
import zed.rainxch.rikkaui.components.ui.table.Table
import zed.rainxch.rikkaui.components.ui.table.TableAnimation
import zed.rainxch.rikkaui.components.ui.table.TableBorderStyle
import zed.rainxch.rikkaui.components.ui.table.TableCell
import zed.rainxch.rikkaui.components.ui.table.TableHeader
import zed.rainxch.rikkaui.components.ui.table.TableHeaderCell
import zed.rainxch.rikkaui.components.ui.table.TableRow
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector
import zed.rainxch.rikkaui.foundation.RikkaTheme

/**
 * Documentation page for the Table component.
 *
 * Demonstrates table animations, border styles,
 * and structured header/row/cell usage.
 */
@Composable
fun TableDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_table_name),
        description = stringResource(Res.string.table_page_desc),
    )

    // ─── Animation Modes ────────────────────────────────────
    DocSection(stringResource(Res.string.table_section_animation_modes)) {
        var selectedAnim by remember { mutableStateOf("Hover") }

        VariantSelector(
            options = listOf("Hover", "Stripe", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation =
            when (selectedAnim) {
                "Stripe" -> TableAnimation.Stripe
                "None" -> TableAnimation.None
                else -> TableAnimation.Hover
            }

        DemoBox {
            Table(animation = animation) {
                TableHeader {
                    TableHeaderCell(stringResource(Res.string.table_demo_invoice), Modifier.weight(1f))
                    TableHeaderCell(stringResource(Res.string.table_demo_status), Modifier.weight(1f))
                    TableHeaderCell(stringResource(Res.string.table_demo_amount), Modifier.weight(1f))
                }
                TableRow(rowIndex = 0) {
                    TableCell("INV001", Modifier.weight(1f))
                    TableCell("Paid", Modifier.weight(1f))
                    TableCell("$250.00", Modifier.weight(1f))
                }
                TableRow(rowIndex = 1) {
                    TableCell("INV002", Modifier.weight(1f))
                    TableCell("Pending", Modifier.weight(1f))
                    TableCell("$150.00", Modifier.weight(1f))
                }
                TableRow(rowIndex = 2) {
                    TableCell("INV003", Modifier.weight(1f))
                    TableCell("Overdue", Modifier.weight(1f))
                    TableCell("$350.00", Modifier.weight(1f))
                }
            }
        }
    }

    // ─── Border Styles ──────────────────────────────────────
    DocSection(stringResource(Res.string.table_section_border_styles)) {
        var selectedBorder by remember { mutableStateOf("Outlined") }

        VariantSelector(
            options = listOf("Outlined", "Bordered", "Borderless"),
            selected = selectedBorder,
            onSelect = { selectedBorder = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val borderStyle =
            when (selectedBorder) {
                "Bordered" -> TableBorderStyle.Bordered
                "Borderless" -> TableBorderStyle.Borderless
                else -> TableBorderStyle.Outlined
            }

        DemoBox {
            Table(borderStyle = borderStyle) {
                TableHeader {
                    TableHeaderCell(stringResource(Res.string.table_demo_name), Modifier.weight(1f))
                    TableHeaderCell(stringResource(Res.string.table_demo_role), Modifier.weight(1f))
                }
                TableRow(rowIndex = 0) {
                    TableCell("Alice", Modifier.weight(1f))
                    TableCell("Engineer", Modifier.weight(1f))
                }
                TableRow(rowIndex = 1) {
                    TableCell("Bob", Modifier.weight(1f))
                    TableCell("Designer", Modifier.weight(1f))
                }
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
        CodeBlock(
            """
Table {
    TableHeader {
        TableHeaderCell("Invoice", Modifier.weight(1f))
        TableHeaderCell("Status", Modifier.weight(1f))
        TableHeaderCell("Amount", Modifier.weight(1f))
    }
    TableRow(rowIndex = 0) {
        TableCell("INV001", Modifier.weight(1f))
        TableCell("Paid", Modifier.weight(1f))
        TableCell("${'$'}250.00", Modifier.weight(1f))
    }
}

// Striped with bordered style
Table(
    animation = TableAnimation.Stripe,
    borderStyle = TableBorderStyle.Bordered,
) {
    // ...
}
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    "Modifier for layout and decoration.",
                ),
                PropInfo(
                    "animation",
                    "TableAnimation",
                    "Hover",
                    "Row visual effect: Hover, Stripe, None.",
                ),
                PropInfo(
                    "borderStyle",
                    "TableBorderStyle",
                    "Outlined",
                    "Border rendering: Outlined, Bordered, Borderless.",
                ),
                PropInfo(
                    "stickyHeader",
                    "Boolean",
                    "false",
                    "Sticky header in scrollable parent.",
                ),
                PropInfo(
                    "content",
                    "ColumnScope.() -> Unit",
                    "required",
                    "Table content: TableHeader + TableRow composables.",
                ),
            ),
        )
    }
}
