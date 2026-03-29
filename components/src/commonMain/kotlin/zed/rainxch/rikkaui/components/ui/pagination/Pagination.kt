package zed.rainxch.rikkaui.components.ui.pagination

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Animation Enum ─────────────────────────────────────────

/**
 * Controls the animation style for active page state transitions.
 *
 * - [Scale] — Active page button scales up with a spring animation
 *   using [RikkaTheme.motion] tokens. Creates a bouncy highlight effect.
 *   This is the default.
 * - [Fade] — Active/inactive transitions use alpha fade, creating a
 *   smooth crossfade between states.
 * - [None] — Instant state change with no animation. Colors still
 *   transition via tween for polish, but no scale or fade is applied.
 *
 * ```
 * Pagination(
 *     currentPage = page,
 *     totalPages = 20,
 *     onPageChange = { page = it },
 *     animation = PaginationAnimation.Scale,
 * )
 * ```
 */
enum class PaginationAnimation {
    Scale,
    Fade,
    None,
}

// ─── Size Enum ──────────────────────────────────────────────

/**
 * Controls the size of page buttons in [Pagination].
 *
 * - [Small] — 28dp buttons with [TextVariant.Muted] text. Compact.
 * - [Default] — 36dp buttons with [TextVariant.Small] text. Standard.
 * - [Large] — 44dp buttons with [TextVariant.P] text. Touch-friendly.
 *
 * ```
 * Pagination(
 *     currentPage = page,
 *     totalPages = 10,
 *     onPageChange = { page = it },
 *     buttonSize = PaginationSize.Large,
 * )
 * ```
 */
enum class PaginationSize {
    Small,
    Default,
    Large,
}

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
 * // With scale animation, large buttons, and custom visible pages
 * Pagination(
 *     currentPage = currentPage,
 *     totalPages = 50,
 *     onPageChange = { currentPage = it },
 *     maxVisiblePages = 7,
 *     animation = PaginationAnimation.Scale,
 *     buttonSize = PaginationSize.Large,
 * )
 *
 * // With custom previous/next content
 * Pagination(
 *     currentPage = currentPage,
 *     totalPages = 10,
 *     onPageChange = { currentPage = it },
 *     previousContent = { tint ->
 *         Text("Prev", color = tint, variant = TextVariant.Small)
 *     },
 *     nextContent = { tint ->
 *         Text("Next", color = tint, variant = TextVariant.Small)
 *     },
 * )
 * ```
 *
 * @param currentPage The currently active page (1-indexed).
 * @param totalPages Total number of pages.
 * @param onPageChange Called when the user selects a different page.
 * @param modifier Modifier for layout and decoration.
 * @param maxVisiblePages Maximum number of page buttons visible at
 *   once (excluding nav buttons). Defaults to 5.
 * @param animation Controls the active page transition animation.
 *   Defaults to [PaginationAnimation.Scale].
 * @param buttonSize Controls the size of page buttons.
 *   Defaults to [PaginationSize.Default].
 * @param previousContent Custom content for the previous button.
 *   Receives the resolved foreground [Color] as a parameter.
 *   When `null` (default), renders a chevron-left icon.
 * @param nextContent Custom content for the next button.
 *   Receives the resolved foreground [Color] as a parameter.
 *   When `null` (default), renders a chevron-right icon.
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
    previousContent: (@Composable (Color) -> Unit)? = null,
    nextContent: (@Composable (Color) -> Unit)? = null,
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
        ) { tint ->
            if (previousContent != null) {
                previousContent(tint)
            } else {
                Icon(
                    imageVector = RikkaIcons.ChevronLeft,
                    contentDescription = null,
                    tint = tint,
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
        ) { tint ->
            if (nextContent != null) {
                nextContent(tint)
            } else {
                Icon(
                    imageVector = RikkaIcons.ChevronRight,
                    contentDescription = null,
                    tint = tint,
                    modifier = Modifier.size(sizeValues.iconDp),
                )
            }
        }
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
 * @param animation The animation strategy for active state transitions.
 * @param sizeValues Resolved size values for the button.
 * @param modifier Modifier for layout and decoration.
 */
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

/**
 * Navigation button variant that accepts icon content instead of text.
 * Used for Previous/Next buttons with [Icon] composables or custom
 * content.
 *
 * @param onClick Called when the button is clicked.
 * @param enabled Whether the button is interactive.
 * @param label Accessibility label for screen readers.
 * @param buttonDp Size of the button in dp.
 * @param iconDp Size of the default icon in dp.
 * @param animation The animation strategy for hover transitions.
 * @param modifier Modifier for layout and decoration.
 * @param content Icon content slot receiving the resolved foreground
 *   [Color].
 */
@Composable
private fun PaginationIconButton(
    onClick: () -> Unit,
    enabled: Boolean,
    label: String,
    buttonDp: Dp,
    iconDp: Dp,
    animation: PaginationAnimation,
    modifier: Modifier = Modifier,
    content: @Composable (Color) -> Unit,
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
        content(colors.foreground)
    }
}

// ─── PaginationEllipsis ────────────────────────────────────

/**
 * Ellipsis indicator for truncated page ranges.
 *
 * @param buttonDp Size matching the page buttons for alignment.
 * @param modifier Modifier for layout and decoration.
 */
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
            color = RikkaTheme.colors.mutedForeground,
        )
    }
}

// ─── Internal: Animation Resolution ────────────────────────

/**
 * Resolves the [Modifier] for the active page animation based
 * on the [PaginationAnimation] enum. Uses `graphicsLayer` to
 * skip composition and layout phases for performance.
 */
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
                animationSpec =
                    spring(
                        dampingRatio = motion.springDefault.let { 0.6f },
                        stiffness = motion.springDefault.let { 400f },
                    ),
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

/**
 * Resolves the button dimensions and text variant for each
 * [PaginationSize].
 */
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
 * Calculates which page numbers to display, and whether to show
 * ellipsis.
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
