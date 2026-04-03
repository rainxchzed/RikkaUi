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
import androidx.compose.ui.semantics.CollectionInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.collectionInfo
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── TableAnimation ───────────────────────────────────────

enum class TableAnimation {
    /** Hover highlight on rows. */
    Hover,

    /** Alternating row colours plus hover. */
    Stripe,

    /** No visual row effects. */
    None,
}

// ─── TableBorderStyle ─────────────────────────────────────

enum class TableBorderStyle {
    /** Outer border with rounded corners. */
    Outlined,

    /** Outer border plus horizontal row dividers. */
    Bordered,

    /** No borders. */
    Borderless,
}

// ─── Internal composition locals ──────────────────────────

private val LocalTableAnimation = compositionLocalOf { TableAnimation.Hover }

private val LocalTableBorderStyle =
    compositionLocalOf { TableBorderStyle.Outlined }

// Reset to -1 so the first data row becomes index 0
private val LocalTableRowIndex = compositionLocalOf { -1 }

private val LocalTableStickyHeader = compositionLocalOf { false }

// ─── Table ─────────────────────────────────────────────────

@Composable
fun Table(
    modifier: Modifier = Modifier,
    rowCount: Int = -1,
    columnCount: Int = -1,
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
            -> {
                baseModifier
                    .border(1.dp, RikkaTheme.colors.border, shape)
                    .clip(shape)
            }

            TableBorderStyle.Borderless -> {
                baseModifier.clip(shape)
            }
        }

    CompositionLocalProvider(
        LocalTableAnimation provides animation,
        LocalTableBorderStyle provides borderStyle,
        LocalTableStickyHeader provides stickyHeader,
    ) {
        Column(
            modifier =
                styledModifier
                    .semantics(mergeDescendants = false) {
                        if (rowCount >= 0 && columnCount >= 0) {
                            collectionInfo =
                                CollectionInfo(
                                    rowCount = rowCount,
                                    columnCount = columnCount,
                                )
                        }
                    },
            content = content,
        )
    }
}

// ─── TableHeader ───────────────────────────────────────────

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
    val dividerHeight = 1.dp
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
                            strokeWidth = dividerHeight.toPx(),
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

@Composable
fun RowScope.TableHeaderCell(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier.semantics { heading() },
    ) {
        content()
    }
}

@Composable
fun RowScope.TableHeaderCell(
    text: String,
    modifier: Modifier = Modifier,
) {
    TableHeaderCell(modifier = modifier) {
        Text(
            text = text,
            variant = TextVariant.Small,
            color = RikkaTheme.colors.onMuted,
        )
    }
}

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
