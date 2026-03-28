package zed.rainxch.rikkaui.components.ui.table

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

// ─── Table ─────────────────────────────────────────────────

/**
 * Data table component for the RikkaUi design system.
 *
 * A structured container for tabular data, matching shadcn/ui's Table.
 * Uses a bordered Column with rounded corners and clipping.
 *
 * Usage:
 * ```
 * Table {
 *     TableHeader {
 *         TableHeaderCell(Modifier.weight(1f)) { Text("Invoice") }
 *         TableHeaderCell(Modifier.weight(1f)) { Text("Status") }
 *         TableHeaderCell(Modifier.weight(1f)) { Text("Amount") }
 *     }
 *     TableRow {
 *         TableCell(Modifier.weight(1f)) { Text("INV001") }
 *         TableCell(Modifier.weight(1f)) { Text("Paid") }
 *         TableCell(Modifier.weight(1f)) { Text("$250.00") }
 *     }
 *     TableRow {
 *         TableCell(Modifier.weight(1f)) { Text("INV002") }
 *         TableCell(Modifier.weight(1f)) { Text("Pending") }
 *         TableCell(Modifier.weight(1f)) { Text("$150.00") }
 *     }
 * }
 * ```
 *
 * @param modifier Modifier for layout and decoration.
 * @param content Table content — typically [TableHeader] followed by [TableRow] composables.
 */
@Composable
fun Table(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = RikkaTheme.shapes.md

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .border(1.dp, RikkaTheme.colors.border, shape)
                .clip(shape)
                .semantics(mergeDescendants = false) {},
        content = content,
    )
}

// ─── TableHeader ───────────────────────────────────────────

/**
 * Header row for a [Table].
 *
 * Renders with a muted background to visually distinguish column headers
 * from data rows.
 *
 * @param modifier Modifier for layout and decoration.
 * @param content Header cells — typically [TableHeaderCell] composables.
 */
@Composable
fun TableHeader(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(RikkaTheme.colors.muted)
                .padding(
                    horizontal = RikkaTheme.spacing.lg,
                    vertical = RikkaTheme.spacing.md,
                ),
        content = content,
    )
}

// ─── TableRow ──────────────────────────────────────────────

/**
 * Data row for a [Table].
 *
 * Renders with a bottom border to separate rows visually.
 *
 * @param modifier Modifier for layout and decoration.
 * @param content Row cells — typically [TableCell] composables.
 */
@Composable
fun TableRow(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    val borderColor = RikkaTheme.colors.border

    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .drawBehind {
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1.dp.toPx(),
                    )
                }
                .padding(
                    horizontal = RikkaTheme.spacing.lg,
                    vertical = RikkaTheme.spacing.md,
                ),
        content = content,
    )
}

// ─── TableCell ─────────────────────────────────────────────

/**
 * Data cell inside a [TableRow].
 *
 * Apply `Modifier.weight(1f)` (or other weights) via the [modifier]
 * parameter to control column widths.
 *
 * Usage:
 * ```
 * TableRow {
 *     TableCell(Modifier.weight(2f)) { Text("Wide column") }
 *     TableCell(Modifier.weight(1f)) { Text("Narrow") }
 * }
 * ```
 *
 * @param modifier Modifier for layout and decoration. Use [RowScope.weight] for column sizing.
 * @param content Cell content — typically [Text].
 */
@Composable
fun RowScope.TableCell(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier,
    ) {
        content()
    }
}

// ─── TableHeaderCell ───────────────────────────────────────

/**
 * Header cell inside a [TableHeader].
 *
 * Renders text with [TextVariant.Small] and muted foreground color by
 * default, matching shadcn/ui's table header styling.
 *
 * Usage:
 * ```
 * TableHeader {
 *     TableHeaderCell(Modifier.weight(1f)) { Text("Column A") }
 *     TableHeaderCell(Modifier.weight(1f)) { Text("Column B") }
 * }
 * ```
 *
 * @param modifier Modifier for layout and decoration. Use [RowScope.weight] for column sizing.
 * @param content Cell content. Defaults to [TextVariant.Small] + mutedForeground styling.
 */
@Composable
fun RowScope.TableHeaderCell(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier,
    ) {
        content()
    }
}

/**
 * Convenience overload for [TableHeaderCell] with a text label.
 *
 * Automatically styles the text with [TextVariant.Small] and
 * [RikkaTheme.colors.mutedForeground].
 *
 * ```
 * TableHeader {
 *     TableHeaderCell("Invoice", Modifier.weight(1f))
 *     TableHeaderCell("Status", Modifier.weight(1f))
 * }
 * ```
 *
 * @param text Header label text.
 * @param modifier Modifier for layout and decoration.
 */
@Composable
fun RowScope.TableHeaderCell(
    text: String,
    modifier: Modifier = Modifier,
) {
    TableHeaderCell(modifier = modifier) {
        Text(
            text = text,
            variant = TextVariant.Small,
            color = RikkaTheme.colors.mutedForeground,
        )
    }
}

/**
 * Convenience overload for [TableCell] with a text label.
 *
 * ```
 * TableRow {
 *     TableCell("INV001", Modifier.weight(1f))
 *     TableCell("Paid", Modifier.weight(1f))
 * }
 * ```
 *
 * @param text Cell text content.
 * @param modifier Modifier for layout and decoration.
 */
@Composable
fun RowScope.TableCell(
    text: String,
    modifier: Modifier = Modifier,
) {
    TableCell(modifier = modifier) {
        Text(
            text = text,
            variant = TextVariant.P,
        )
    }
}
