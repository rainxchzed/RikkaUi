package zed.rainxch.rikkaui.components.ui.pagination

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

// ─── Component ─────────────────────────────────────────────

/**
 * Pagination component for the RikkaUi design system.
 *
 * Displays a row of page number buttons with Previous/Next navigation.
 * Automatically calculates visible page ranges and inserts ellipsis
 * when there are too many pages to display.
 *
 * Usage:
 * ```
 * var currentPage by remember { mutableIntStateOf(1) }
 *
 * Pagination(
 *     currentPage = currentPage,
 *     totalPages = 20,
 *     onPageChange = { currentPage = it },
 * )
 *
 * // With custom visible pages
 * Pagination(
 *     currentPage = currentPage,
 *     totalPages = 50,
 *     onPageChange = { currentPage = it },
 *     maxVisiblePages = 7,
 * )
 * ```
 *
 * @param currentPage The currently active page (1-indexed).
 * @param totalPages Total number of pages.
 * @param onPageChange Called when the user selects a different page.
 * @param modifier Modifier for layout and decoration.
 * @param maxVisiblePages Maximum number of page buttons visible at once (excluding nav buttons).
 */
@Composable
fun Pagination(
    currentPage: Int,
    totalPages: Int,
    onPageChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    maxVisiblePages: Int = 5,
) {
    val pageRange = resolvePageRange(currentPage, totalPages, maxVisiblePages)

    Row(
        modifier =
            modifier.semantics {
                contentDescription = "Pagination, page $currentPage of $totalPages"
            },
        horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Previous button
        PaginationButton(
            text = "\u2190",
            onClick = { onPageChange(currentPage - 1) },
            enabled = currentPage > 1,
            isActive = false,
            label = "Previous page",
        )

        // Leading ellipsis
        if (pageRange.showLeadingEllipsis) {
            PaginationButton(
                text = "1",
                onClick = { onPageChange(1) },
                enabled = true,
                isActive = false,
                label = "Page 1",
            )
            PaginationEllipsis()
        }

        // Page number buttons
        for (page in pageRange.range) {
            PaginationButton(
                text = page.toString(),
                onClick = { onPageChange(page) },
                enabled = true,
                isActive = page == currentPage,
                label = if (page == currentPage) "Page $page, current page" else "Page $page",
            )
        }

        // Trailing ellipsis
        if (pageRange.showTrailingEllipsis) {
            PaginationEllipsis()
            PaginationButton(
                text = totalPages.toString(),
                onClick = { onPageChange(totalPages) },
                enabled = true,
                isActive = false,
                label = "Page $totalPages",
            )
        }

        // Next button
        PaginationButton(
            text = "\u2192",
            onClick = { onPageChange(currentPage + 1) },
            enabled = currentPage < totalPages,
            isActive = false,
            label = "Next page",
        )
    }
}

// ─── PaginationButton ──────────────────────────────────────

/**
 * Internal page button used by [Pagination].
 *
 * @param text Button label text.
 * @param onClick Called when the button is clicked.
 * @param enabled Whether the button is interactive.
 * @param isActive Whether this is the current/active page.
 * @param label Accessibility label for screen readers.
 * @param modifier Modifier for layout and decoration.
 */
@Composable
private fun PaginationButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean,
    isActive: Boolean,
    label: String,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val colors = resolveButtonColors(isActive, isHovered)
    val motion = RikkaTheme.motion
    val shape = RikkaTheme.shapes.md

    val animatedBackground by animateColorAsState(
        targetValue = colors.background,
        animationSpec = tween(motion.durationDefault),
    )

    val backgroundModifier =
        if (colors.background != Color.Transparent) {
            Modifier.background(animatedBackground, shape)
        } else {
            Modifier
        }

    val borderModifier =
        if (colors.border != Color.Transparent) {
            Modifier.border(1.dp, colors.border, shape)
        } else {
            Modifier
        }

    Box(
        modifier =
            modifier
                .then(borderModifier)
                .then(backgroundModifier)
                .clip(shape)
                .size(36.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    role = Role.Button,
                    onClick = onClick,
                ).then(if (!enabled) Modifier.alpha(0.5f) else Modifier)
                .semantics(mergeDescendants = true) {
                    contentDescription = label
                    if (!enabled) {
                        disabled()
                    }
                },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            variant = TextVariant.Small,
            color = colors.foreground,
        )
    }
}

// ─── PaginationEllipsis ────────────────────────────────────

/**
 * Ellipsis indicator for truncated page ranges.
 *
 * @param modifier Modifier for layout and decoration.
 */
@Composable
private fun PaginationEllipsis(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.size(36.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "\u2026",
            variant = TextVariant.Small,
            color = RikkaTheme.colors.mutedForeground,
        )
    }
}

// ─── Internal: Color Resolution ────────────────────────────

private data class PaginationButtonColors(
    val background: Color,
    val foreground: Color,
    val border: Color,
)

/**
 * Resolves button colors based on active/hover state.
 *
 * Active page uses primary background (like ButtonVariant.Default).
 * Other pages use ghost/outline style with hover feedback.
 */
@Composable
private fun resolveButtonColors(
    isActive: Boolean,
    isHovered: Boolean,
): PaginationButtonColors {
    val colors = RikkaTheme.colors

    return if (isActive) {
        PaginationButtonColors(
            background = colors.primary,
            foreground = colors.primaryForeground,
            border = Color.Transparent,
        )
    } else {
        PaginationButtonColors(
            background =
                when {
                    isHovered -> colors.muted
                    else -> Color.Transparent
                },
            foreground = colors.foreground,
            border = colors.border,
        )
    }
}

// ─── Internal: Page Range Calculation ──────────────────────

private data class PageRange(
    val range: IntRange,
    val showLeadingEllipsis: Boolean,
    val showTrailingEllipsis: Boolean,
)

/**
 * Calculates which page numbers to display, and whether to show ellipsis.
 *
 * If [totalPages] fits within [maxVisible], all pages are shown.
 * Otherwise, a window around [currentPage] is shown with ellipsis
 * at the edges as needed.
 */
private fun resolvePageRange(
    currentPage: Int,
    totalPages: Int,
    maxVisible: Int,
): PageRange {
    if (totalPages <= maxVisible) {
        return PageRange(
            range = 1..totalPages,
            showLeadingEllipsis = false,
            showTrailingEllipsis = false,
        )
    }

    // Reserve space for first/last page shown alongside ellipsis
    val sideSlots = maxVisible - 2
    val halfWindow = sideSlots / 2

    val start: Int
    val end: Int

    when {
        // Near the start — no leading ellipsis needed
        currentPage <= halfWindow + 2 -> {
            start = 1
            end = maxVisible - 1
            return PageRange(
                range = start..end,
                showLeadingEllipsis = false,
                showTrailingEllipsis = true,
            )
        }
        // Near the end — no trailing ellipsis needed
        currentPage >= totalPages - halfWindow - 1 -> {
            start = totalPages - maxVisible + 2
            end = totalPages
            return PageRange(
                range = start..end,
                showLeadingEllipsis = true,
                showTrailingEllipsis = false,
            )
        }
        // In the middle — both ellipses
        else -> {
            start = currentPage - halfWindow
            end = currentPage + halfWindow
            return PageRange(
                range = start..end,
                showLeadingEllipsis = true,
                showTrailingEllipsis = true,
            )
        }
    }
}
