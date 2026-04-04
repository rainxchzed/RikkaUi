package zed.rainxch.rikkaui.components.ui.pagination

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.LocalContentColor
import zed.rainxch.rikkaui.foundation.RikkaTheme
import zed.rainxch.rikkaui.foundation.modifier.minTouchTarget

// ─── Animation Enum ─────────────────────────────────────────

/** Active page state transition style. */
enum class PaginationAnimation {
    /** Spring scale-up effect (default). */
    Scale,

    /** Alpha crossfade between states. */
    Fade,

    /** No animation. */
    None,
}

// ─── Size Enum ──────────────────────────────────────────────

/** Page button size. */
enum class PaginationSize {
    /** 28dp, compact. */
    Small,

    /** 36dp, standard. */
    Default,

    /** 44dp, touch-friendly. */
    Large,
}

// ─── Component ─────────────────────────────────────────────

/**
 * Smart page navigation with numbered buttons, ellipsis, and previous/next controls.
 *
 * Automatically calculates visible page range with leading/trailing ellipsis when the total
 * page count exceeds [maxVisiblePages]. Active page buttons are highlighted with primary color
 * and optional scale/fade animation.
 *
 * @param currentPage The currently selected page number (1-based).
 * @param totalPages Total number of pages available.
 * @param onPageChange Callback invoked with the new page number when a page button is clicked.
 * @param modifier [Modifier] applied to the pagination row.
 * @param maxVisiblePages Maximum number of page buttons visible before showing ellipsis. Defaults to 5.
 * @param animation [PaginationAnimation] style for active page state transitions. Defaults to [PaginationAnimation.Scale].
 * @param buttonSize [PaginationSize] controlling the size of page buttons. Defaults to [PaginationSize.Default].
 * @param previousContent Optional custom composable for the previous button. Defaults to a chevron-left icon.
 * @param nextContent Optional custom composable for the next button. Defaults to a chevron-right icon.
 */
@Composable
fun Pagination(
    currentPage: Int,
    totalPages: Int,
    onPageChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    maxVisiblePages: Int = 5,
    animation: PaginationAnimation = PaginationAnimation.Scale,
    buttonSize: PaginationSize = PaginationSize.Default,
    previousContent: (@Composable () -> Unit)? = null,
    nextContent: (@Composable () -> Unit)? = null,
) {
    val pageRange =
        resolvePageRange(
            currentPage,
            totalPages,
            maxVisiblePages,
        )
    val sizeValues = resolveSizeValues(buttonSize)

    Row(
        modifier =
            modifier.semantics {
                contentDescription =
                    "Pagination, page $currentPage of $totalPages"
            },
        horizontalArrangement =
            Arrangement.spacedBy(
                RikkaTheme.spacing.xs,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Previous button
        PaginationIconButton(
            onClick = { onPageChange(currentPage - 1) },
            enabled = currentPage > 1,
            label = "Previous page",
            buttonDp = sizeValues.buttonDp,
            iconDp = sizeValues.iconDp,
            animation = animation,
        ) {
            if (previousContent != null) {
                previousContent()
            } else {
                Icon(
                    imageVector = RikkaIcons.ChevronLeft,
                    contentDescription = null,
                    modifier = Modifier.size(sizeValues.iconDp),
                )
            }
        }

        // Leading ellipsis
        if (pageRange.showLeadingEllipsis) {
            PaginationButton(
                text = "1",
                onClick = { onPageChange(1) },
                enabled = true,
                isActive = false,
                label = "Page 1",
                animation = animation,
                sizeValues = sizeValues,
            )
            PaginationEllipsis(buttonDp = sizeValues.buttonDp)
        }

        // Page number buttons
        for (page in pageRange.range) {
            PaginationButton(
                text = page.toString(),
                onClick = { onPageChange(page) },
                enabled = true,
                isActive = page == currentPage,
                label =
                    if (page == currentPage) {
                        "Page $page, current page"
                    } else {
                        "Page $page"
                    },
                animation = animation,
                sizeValues = sizeValues,
            )
        }

        // Trailing ellipsis
        if (pageRange.showTrailingEllipsis) {
            PaginationEllipsis(buttonDp = sizeValues.buttonDp)
            PaginationButton(
                text = totalPages.toString(),
                onClick = { onPageChange(totalPages) },
                enabled = true,
                isActive = false,
                label = "Page $totalPages",
                animation = animation,
                sizeValues = sizeValues,
            )
        }

        // Next button
        PaginationIconButton(
            onClick = { onPageChange(currentPage + 1) },
            enabled = currentPage < totalPages,
            label = "Next page",
            buttonDp = sizeValues.buttonDp,
            iconDp = sizeValues.iconDp,
            animation = animation,
        ) {
            if (nextContent != null) {
                nextContent()
            } else {
                Icon(
                    imageVector = RikkaIcons.ChevronRight,
                    contentDescription = null,
                    modifier = Modifier.size(sizeValues.iconDp),
                )
            }
        }
    }
}

// ─── PaginationButton ──────────────────────────────────────

@Composable
private fun PaginationButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean,
    isActive: Boolean,
    label: String,
    animation: PaginationAnimation,
    sizeValues: PaginationSizeValues,
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

    // ─── Animation modifiers based on enum ──────────────
    val animationModifier =
        resolveAnimationModifier(
            animation = animation,
            isActive = isActive,
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
                .minTouchTarget()
                .then(animationModifier)
                .then(borderModifier)
                .then(backgroundModifier)
                .clip(shape)
                .size(sizeValues.buttonDp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    role = Role.Button,
                    onClick = onClick,
                ).then(if (!enabled) Modifier.alpha(0.5f) else Modifier)
                .semantics(mergeDescendants = true) {
                    contentDescription = label
                    selected = isActive
                    if (!enabled) {
                        disabled()
                    }
                },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            variant = sizeValues.textVariant,
            color = colors.foreground,
        )
    }
}

// ─── PaginationIconButton ─────────────────────────────────

@Composable
private fun PaginationIconButton(
    onClick: () -> Unit,
    enabled: Boolean,
    label: String,
    buttonDp: Dp,
    iconDp: Dp,
    animation: PaginationAnimation,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val colors =
        resolveButtonColors(
            isActive = false,
            isHovered = isHovered,
        )
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
                .minTouchTarget()
                .then(borderModifier)
                .then(backgroundModifier)
                .clip(shape)
                .size(buttonDp)
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
        CompositionLocalProvider(LocalContentColor provides colors.foreground) {
            content()
        }
    }
}

// ─── PaginationEllipsis ────────────────────────────────────

@Composable
private fun PaginationEllipsis(
    buttonDp: Dp,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.size(buttonDp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "\u2026",
            variant = TextVariant.Small,
            color = RikkaTheme.colors.onMuted,
        )
    }
}

// ─── Internal: Animation Resolution ────────────────────────

@Composable
private fun resolveAnimationModifier(
    animation: PaginationAnimation,
    isActive: Boolean,
): Modifier {
    val motion = RikkaTheme.motion

    return when (animation) {
        PaginationAnimation.Scale -> {
            val scale by animateFloatAsState(
                targetValue = if (isActive) 1.1f else 1f,
                animationSpec = motion.spatialDefault(),
            )
            Modifier.graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
        }

        PaginationAnimation.Fade -> {
            val alpha by animateFloatAsState(
                targetValue = if (isActive) 1f else 0.65f,
                animationSpec = tween(motion.durationDefault),
            )
            Modifier.graphicsLayer { this.alpha = alpha }
        }

        PaginationAnimation.None -> {
            Modifier
        }
    }
}

// ─── Internal: Size Resolution ─────────────────────────────

private data class PaginationSizeValues(
    val buttonDp: Dp,
    val iconDp: Dp,
    val textVariant: TextVariant,
)

@Composable
private fun resolveSizeValues(size: PaginationSize): PaginationSizeValues =
    when (size) {
        PaginationSize.Small -> {
            PaginationSizeValues(
                buttonDp = 28.dp,
                iconDp = 14.dp,
                textVariant = TextVariant.Muted,
            )
        }

        PaginationSize.Default -> {
            PaginationSizeValues(
                buttonDp = 36.dp,
                iconDp = 16.dp,
                textVariant = TextVariant.Small,
            )
        }

        PaginationSize.Large -> {
            PaginationSizeValues(
                buttonDp = 44.dp,
                iconDp = 20.dp,
                textVariant = TextVariant.P,
            )
        }
    }

// ─── Internal: Color Resolution ────────────────────────────

private data class PaginationButtonColors(
    val background: Color,
    val foreground: Color,
    val border: Color,
)

@Composable
private fun resolveButtonColors(
    isActive: Boolean,
    isHovered: Boolean,
): PaginationButtonColors {
    val colors = RikkaTheme.colors

    return if (isActive) {
        PaginationButtonColors(
            background = colors.primary,
            foreground = colors.onPrimary,
            border = Color.Transparent,
        )
    } else {
        PaginationButtonColors(
            background =
                when {
                    isHovered ->
                        if (colors.secondaryHover != Color.Unspecified) {
                            colors.secondaryHover
                        } else {
                            colors.muted
                        }
                    else -> Color.Transparent
                },
            foreground = colors.onBackground,
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
