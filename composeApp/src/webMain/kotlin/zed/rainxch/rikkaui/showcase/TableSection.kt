package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.table.Table
import zed.rainxch.rikkaui.components.ui.table.TableCell
import zed.rainxch.rikkaui.components.ui.table.TableHeader
import zed.rainxch.rikkaui.components.ui.table.TableHeaderCell
import zed.rainxch.rikkaui.components.ui.table.TableRow

@Composable
fun TableSection() {
    SectionHeader(
        title = "Table",
        description = "Structured data display with headers and rows.",
    )

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    Card(modifier = Modifier.fillMaxWidth()) {
        Table {
            TableHeader {
                TableHeaderCell("Name", Modifier.weight(1f))
                TableHeaderCell("Status", Modifier.weight(1f))
                TableHeaderCell("Role", Modifier.weight(1f))
            }
            TableRow {
                TableCell("Alice", Modifier.weight(1f))
                TableCell("Active", Modifier.weight(1f))
                TableCell("Admin", Modifier.weight(1f))
            }
            TableRow {
                TableCell("Bob", Modifier.weight(1f))
                TableCell("Inactive", Modifier.weight(1f))
                TableCell("User", Modifier.weight(1f))
            }
            TableRow {
                TableCell("Charlie", Modifier.weight(1f))
                TableCell("Active", Modifier.weight(1f))
                TableCell("Editor", Modifier.weight(1f))
            }
            TableRow {
                TableCell("Diana", Modifier.weight(1f))
                TableCell("Active", Modifier.weight(1f))
                TableCell("Viewer", Modifier.weight(1f))
            }
        }
    }
}
