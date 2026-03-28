package zed.rainxch.rikkaui.components.ui.table

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

// ─── TableAnimation ───────────────────────────────────────

/**
 * Controls row visual effects in a [Table].
 *
 * Usage:
 * ```
 * Table(animation = TableAnimation.Stripe) {
 *     TableHeader { /* ... */ }
 *     TableRow { /* ... */ }
 * }
 * ```
 *
 * @property Hover Rows highlight on hover (default).
 * @property Stripe Alternating row colours plus hover highlight.
 * @property None No hover or stripe effects.
 */
enum class TableAnimation {
    /** Rows highlight with a muted background on hover. */
    Hover,

    /** Alternating row background colours plus hover highlight. */
    Stripe,

    /** No visual row effects. */
    None,
}

// ─── TableBorderStyle ─────────────────────────────────────

/**
 * Controls the border style of a [Table].
 *
 * @property Outlined Single outer border with rounded corners (default).
 * @property Bordered Outer border plus horizontal row dividers.
 * @property Borderless No borders at all.
 */
enum class TableBorderStyle {
    /** Single outer border with rounded corners. */
    Outlined,

    /** Outer border plus horizontal row dividers (same as Outlined visually,
     *  but rows always draw bottom borders even when animation is [TableAnimation.None]). */
    Bordered,

    /** No borders. */
    Borderless,
}

// ─── Internal composition locals ──────────────────────────

/**
 * Provides the current [TableAnimation] to child composables.
 */
private val LocalTableAnimation = compositionLocalOf { TableAnimation.Hover }

/**
 * Provides the current [TableBorderStyle] to child composables.
 */
private val LocalTableBorderStyle =
    compositionLocalOf { TableBorderStyle.Outlined }

/**
 * Zero-indexed row counter used by [TableRow] for stripe colouring.
 * Reset to -1 so the first data row becomes index 0.
 */
private val LocalTableRowIndex = compositionLocalOf { -1 }

/**
 * Whether the table header should stick to the top when scrolled.
 * (Provided for forward compatibility; requires a scrollable parent.)
 */
private val LocalTableStickyHeader = compositionLocalOf { false }

// ─── Table ─────────────────────────────────────────────────

/**
 * Data table component for the RikkaUi design system.
 *
 * A structured container for tabular data, matching shadcn/ui's Table.
 * Supports row hover highlights, alternating stripe colours, click
 * handlers, border styles, and sticky headers.
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
 * }
 *
 * // Striped table with click handler
 * Table(animation = TableAnimation.Stripe) {
 *     TableHeader { /* ... */ }
 *     TableRow(onClick = { println("clicked") }) { /* ... */ }
 * }
 * ```
 *
 * @param modifier Modifier for layout and decoration.
 * @param animation Row visual effect mode.
 * @param borderStyle Border rendering mode.
 * @param stickyHeader When `true`, the header row sticks to the top of a
 *   scrollable parent. Requires the table to live inside a
 *   `ScrollArea` or similar scrollable container.
 * @param content Table content — typically [TableHeader] followed by
 *   [TableRow] composables.
 */
@Composable
fun Table(
    modifier: Modifier = Modifier,
    animation: TableAnimation = TableAnimation.Hover,
    borderStyle: TableBorderStyle = TableBorderStyle.Outlined,
    stickyHeader: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = RikkaTheme.shapes.md

    val baseModifier = modifier.fillMaxWidth()
    val styledModifier =
        when (borderStyle) {
            TableBorderStyle.Outlined,
            TableBorderStyle.Bordered,
            ->
                baseModifier
                    .border(1.dp, RikkaTheme.colors.border, shape)
                    .clip(shape)

            TableBorderStyle.Borderless -> baseModifier.clip(shape)
        }

    CompositionLocalProvider(
        LocalTableAnimation provides animation,
        LocalTableBorderStyle provides borderStyle,
        LocalTableStickyHeader provides stickyHeader,
    ) {
        Column(
            modifier =
                styledModifier
                    .semantics(mergeDescendants = false) {},
            content = content,
        )
    }
}

// ─── TableHeader ───────────────────────────────────────────

/**
 * Header row for a [Table].
 *
 * Renders with a muted background to visually distinguish column
 * headers from data rows.
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
 * Renders with a bottom border to separate rows visually. Hover
 * and stripe effects are controlled by the parent [Table]'s
 * [TableAnimation].
 *
 * @param modifier Modifier for layout and decoration.
 * @param rowIndex Zero-based index of this row. Used for stripe
 *   colouring. When `-1` (default) the row ignores stripe logic.
 * @param onClick Optional click handler for the entire row.
 * @param content Row cells — typically [TableCell] composables.
 */
@Composable
fun TableRow(
    modifier: Modifier = Modifier,
    rowIndex: Int = -1,
    onClick: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val animation = LocalTableAnimation.current
    val borderStyle = LocalTableBorderStyle.current
    val borderColor = RikkaTheme.colors.border
    val motion = RikkaTheme.motion
    val colors = RikkaTheme.colors

    // Hover tracking
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val hoverEnabled = animation != TableAnimation.None
    val isStriped = animation == TableAnimation.Stripe
    val isEvenRow = rowIndex >= 0 && rowIndex % 2 == 1

    // Animated hover alpha
    val hoverAlpha by animateFloatAsState(
        targetValue = if (hoverEnabled && isHovered) 1f else 0f,
        animationSpec = tween(motion.durationFast),
    )

    val hoverBg = colors.muted.copy(alpha = 0.5f * hoverAlpha)
    val stripeBg =
        if (isStriped && isEvenRow) {
            colors.muted.copy(alpha = 0.35f)
        } else {
            Color.Transparent
        }

    // Combine stripe + hover (hover overlays stripe)
    val rowBg = if (hoverAlpha > 0f) hoverBg else stripeBg

    val showBorder = borderStyle != TableBorderStyle.Borderless

    val baseModifier =
        modifier
            .fillMaxWidth()
            .then(
                if (showBorder) {
                    Modifier.drawBehind {
                        drawLine(
                            color = borderColor,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 1.dp.toPx(),
                        )
                    }
                } else {
                    Modifier
                },
            ).background(rowBg)

    val interactiveModifier =
        if (onClick != null || hoverEnabled) {
            baseModifier.clickable(
                interactionSource = interactionSource,
                indication = null,
                role = if (onClick != null) Role.Button else null,
                onClick = onClick ?: {},
            )
        } else {
            baseModifier
        }

    Row(
        modifier =
            interactiveModifier
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
 * @param modifier Modifier for layout and decoration.
 *   Use [RowScope.weight] for column sizing.
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
 * Renders text with [TextVariant.Small] and muted foreground color
 * by default, matching shadcn/ui's table header styling.
 *
 * Usage:
 * ```
 * TableHeader {
 *     TableHeaderCell(Modifier.weight(1f)) { Text("Column A") }
 *     TableHeaderCell(Modifier.weight(1f)) { Text("Column B") }
 * }
 * ```
 *
 * @param modifier Modifier for layout and decoration.
 *   Use [RowScope.weight] for column sizing.
 * @param content Cell content. Defaults to [TextVariant.Small] +
 *   mutedForeground styling.
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
