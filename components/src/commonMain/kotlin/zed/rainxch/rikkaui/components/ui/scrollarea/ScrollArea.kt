package zed.rainxch.rikkaui.components.ui.scrollarea

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme

// ─── ScrollbarAnimation ─────────────────────────────────────

/**
 * Controls scrollbar visibility and animation behaviour in [ScrollArea]
 * and [HorizontalScrollArea].
 *
 * Usage:
 * ```
 * ScrollArea(scrollbarAnimation = ScrollbarAnimation.Always) {
 *     // content
 * }
 * ```
 *
 * @property Fade Scrollbar fades in when scrolling and dims when idle (default).
 * @property Always Scrollbar is always fully visible when content overflows.
 * @property None Scrollbar is never shown.
 */
enum class ScrollbarAnimation {
    /** Scrollbar fades in when scrolling, dims to semi-transparent when idle. */
    Fade,

    /** Scrollbar is always fully visible when content overflows. */
    Always,

    /** No scrollbar is rendered. */
    None,
}

// ─── Constants ─────────────────────────────────────────────

private val DEFAULT_SCROLLBAR_THICKNESS: Dp = 4.dp
private val SCROLLBAR_MIN_THUMB: Dp = 24.dp
private val SCROLLBAR_PADDING: Dp = 2.dp

// ─── ScrollArea (Vertical) ─────────────────────────────────

/**
 * Vertical scrollable container with a custom scrollbar indicator.
 *
 * Provides a thin rounded scrollbar on the right side whose visibility
 * is controlled by [scrollbarAnimation]. The scrollbar thumb position
 * and size are calculated from the scroll state.
 *
 * Usage:
 * ```
 * ScrollArea(modifier = Modifier.fillMaxSize()) {
 *     repeat(50) { index ->
 *         Text("Item $index")
 *     }
 * }
 *
 * // Always-visible wider scrollbar with custom colour
 * ScrollArea(
 *     scrollbarAnimation = ScrollbarAnimation.Always,
 *     scrollbarWidth = 6.dp,
 *     scrollbarColor = RikkaTheme.colors.primary,
 * ) {
 *     // content
 * }
 * ```
 *
 * @param modifier Modifier for layout and decoration.
 * @param scrollbarAnimation Controls scrollbar visibility behaviour.
 * @param scrollbarWidth Thickness of the scrollbar track and thumb.
 * @param scrollbarColor Optional colour override for the scrollbar thumb.
 *   When `null` the theme's [RikkaTheme.colors.mutedForeground] is used.
 * @param content Scrollable column content.
 */
@Composable
fun ScrollArea(
    modifier: Modifier = Modifier,
    scrollbarAnimation: ScrollbarAnimation = ScrollbarAnimation.Fade,
    scrollbarWidth: Dp = DEFAULT_SCROLLBAR_THICKNESS,
    scrollbarColor: Color? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val scrollState = rememberScrollState()
    val motion = RikkaTheme.motion
    val shape = RikkaTheme.shapes.md

    // Track container height in px for thumb calculation
    val containerHeightPx =
        remember {
            androidx.compose.runtime.mutableIntStateOf(0)
        }

    // Whether the content overflows and scrollbar is needed
    val isScrollable by remember {
        derivedStateOf { scrollState.maxValue > 0 }
    }

    // Scrollbar visibility: show when scrollable and content is being scrolled
    val isScrolling by remember {
        derivedStateOf { scrollState.isScrollInProgress }
    }

    val showScrollbar = scrollbarAnimation != ScrollbarAnimation.None

    val scrollbarAlpha by animateFloatAsState(
        targetValue =
            resolveScrollbarAlpha(
                scrollbarAnimation,
                isScrollable,
                isScrolling,
            ),
        animationSpec = tween(motion.durationDefault),
    )

    Box(
        modifier =
            modifier
                .clip(shape)
                .onSizeChanged { containerHeightPx.intValue = it.height },
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(
                        end =
                            if (isScrollable && showScrollbar) {
                                scrollbarWidth + SCROLLBAR_PADDING * 2
                            } else {
                                0.dp
                            },
                    ),
            content = content,
        )

        // Scrollbar track + thumb
        if (isScrollable && showScrollbar) {
            VerticalScrollbar(
                scrollValue = scrollState.value,
                maxScrollValue = scrollState.maxValue,
                containerHeightPx = containerHeightPx.intValue,
                alpha = scrollbarAlpha,
                thickness = scrollbarWidth,
                thumbColorOverride = scrollbarColor,
                modifier = Modifier.align(Alignment.CenterEnd),
            )
        }
    }
}

// ─── HorizontalScrollArea ──────────────────────────────────

/**
 * Horizontal scrollable container with a custom scrollbar indicator.
 *
 * Provides a thin rounded scrollbar on the bottom whose visibility
 * is controlled by [scrollbarAnimation].
 *
 * Usage:
 * ```
 * HorizontalScrollArea(modifier = Modifier.fillMaxWidth()) {
 *     repeat(20) { index ->
 *         Box(modifier = Modifier.size(100.dp))
 *     }
 * }
 *
 * // Hidden scrollbar
 * HorizontalScrollArea(scrollbarAnimation = ScrollbarAnimation.None) {
 *     // content
 * }
 * ```
 *
 * @param modifier Modifier for layout and decoration.
 * @param scrollbarAnimation Controls scrollbar visibility behaviour.
 * @param scrollbarWidth Thickness of the scrollbar track and thumb.
 * @param scrollbarColor Optional colour override for the scrollbar thumb.
 *   When `null` the theme's [RikkaTheme.colors.mutedForeground] is used.
 * @param content Scrollable row content.
 */
@Composable
fun HorizontalScrollArea(
    modifier: Modifier = Modifier,
    scrollbarAnimation: ScrollbarAnimation = ScrollbarAnimation.Fade,
    scrollbarWidth: Dp = DEFAULT_SCROLLBAR_THICKNESS,
    scrollbarColor: Color? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val scrollState = rememberScrollState()
    val motion = RikkaTheme.motion
    val shape = RikkaTheme.shapes.md

    val containerWidthPx =
        remember {
            androidx.compose.runtime.mutableIntStateOf(0)
        }

    val isScrollable by remember {
        derivedStateOf { scrollState.maxValue > 0 }
    }

    val isScrolling by remember {
        derivedStateOf { scrollState.isScrollInProgress }
    }

    val showScrollbar = scrollbarAnimation != ScrollbarAnimation.None

    val scrollbarAlpha by animateFloatAsState(
        targetValue =
            resolveScrollbarAlpha(
                scrollbarAnimation,
                isScrollable,
                isScrolling,
            ),
        animationSpec = tween(motion.durationDefault),
    )

    Box(
        modifier =
            modifier
                .clip(shape)
                .onSizeChanged { containerWidthPx.intValue = it.width },
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxSize()
                    .horizontalScroll(scrollState)
                    .padding(
                        bottom =
                            if (isScrollable && showScrollbar) {
                                scrollbarWidth + SCROLLBAR_PADDING * 2
                            } else {
                                0.dp
                            },
                    ),
            content = content,
        )

        // Scrollbar track + thumb
        if (isScrollable && showScrollbar) {
            HorizontalScrollbar(
                scrollValue = scrollState.value,
                maxScrollValue = scrollState.maxValue,
                containerWidthPx = containerWidthPx.intValue,
                alpha = scrollbarAlpha,
                thickness = scrollbarWidth,
                thumbColorOverride = scrollbarColor,
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

// ─── Internal: resolveScrollbarAlpha ────────────────────────

/**
 * Resolves target alpha for the scrollbar based on animation mode.
 */
private fun resolveScrollbarAlpha(
    animation: ScrollbarAnimation,
    isScrollable: Boolean,
    isScrolling: Boolean,
): Float =
    when (animation) {
        ScrollbarAnimation.Fade ->
            when {
                isScrolling && isScrollable -> 1f
                isScrollable -> 0.4f
                else -> 0f
            }
        ScrollbarAnimation.Always -> if (isScrollable) 1f else 0f
        ScrollbarAnimation.None -> 0f
    }

// ─── Internal: Vertical Scrollbar ──────────────────────────

/**
 * Draws a vertical scrollbar track and thumb.
 *
 * @param scrollValue Current scroll position in pixels.
 * @param maxScrollValue Maximum scroll position in pixels.
 * @param containerHeightPx Container height in pixels.
 * @param alpha Overall scrollbar alpha for fade animation.
 * @param thickness Scrollbar width.
 * @param thumbColorOverride Optional thumb colour override.
 * @param modifier Modifier for positioning.
 */
@Composable
private fun VerticalScrollbar(
    scrollValue: Int,
    maxScrollValue: Int,
    containerHeightPx: Int,
    alpha: Float,
    thickness: Dp,
    thumbColorOverride: Color?,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val trackColor = RikkaTheme.colors.muted.copy(alpha = 0.5f)
    val thumbColor = thumbColorOverride ?: RikkaTheme.colors.mutedForeground
    val thumbShape = RoundedCornerShape(50)

    val minThumbPx: Float = with(density) { SCROLLBAR_MIN_THUMB.toPx() }

    // Calculate thumb size and position
    val totalContentPx = containerHeightPx + maxScrollValue
    val thumbFraction =
        if (totalContentPx > 0) {
            (containerHeightPx.toFloat() / totalContentPx)
                .coerceIn(0.05f, 1f)
        } else {
            1f
        }
    val thumbHeightPx =
        (containerHeightPx * thumbFraction).coerceAtLeast(minThumbPx)
    val thumbHeightDp: Dp = with(density) { thumbHeightPx.toDp() }

    val scrollFraction =
        if (maxScrollValue > 0) {
            scrollValue.toFloat() / maxScrollValue
        } else {
            0f
        }
    val thumbOffsetPx =
        ((containerHeightPx - thumbHeightPx) * scrollFraction).toInt()

    Box(
        modifier =
            modifier
                .fillMaxHeight()
                .width(thickness + SCROLLBAR_PADDING * 2)
                .padding(horizontal = SCROLLBAR_PADDING)
                .graphicsLayer { this.alpha = alpha },
    ) {
        // Track
        Box(
            modifier =
                Modifier
                    .fillMaxHeight()
                    .width(thickness)
                    .background(trackColor, thumbShape),
        )

        // Thumb
        Box(
            modifier =
                Modifier
                    .width(thickness)
                    .height(thumbHeightDp)
                    .offset { IntOffset(0, thumbOffsetPx) }
                    .background(thumbColor, thumbShape),
        )
    }
}

// ─── Internal: Horizontal Scrollbar ────────────────────────

/**
 * Draws a horizontal scrollbar track and thumb.
 *
 * @param scrollValue Current scroll position in pixels.
 * @param maxScrollValue Maximum scroll position in pixels.
 * @param containerWidthPx Container width in pixels.
 * @param alpha Overall scrollbar alpha for fade animation.
 * @param thickness Scrollbar height.
 * @param thumbColorOverride Optional thumb colour override.
 * @param modifier Modifier for positioning.
 */
@Composable
private fun HorizontalScrollbar(
    scrollValue: Int,
    maxScrollValue: Int,
    containerWidthPx: Int,
    alpha: Float,
    thickness: Dp,
    thumbColorOverride: Color?,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val trackColor = RikkaTheme.colors.muted.copy(alpha = 0.5f)
    val thumbColor = thumbColorOverride ?: RikkaTheme.colors.mutedForeground
    val thumbShape = RoundedCornerShape(50)

    val minThumbPx: Float = with(density) { SCROLLBAR_MIN_THUMB.toPx() }

    val totalContentPx = containerWidthPx + maxScrollValue
    val thumbFraction =
        if (totalContentPx > 0) {
            (containerWidthPx.toFloat() / totalContentPx)
                .coerceIn(0.05f, 1f)
        } else {
            1f
        }
    val thumbWidthPx =
        (containerWidthPx * thumbFraction).coerceAtLeast(minThumbPx)
    val thumbWidthDp: Dp = with(density) { thumbWidthPx.toDp() }

    val scrollFraction =
        if (maxScrollValue > 0) {
            scrollValue.toFloat() / maxScrollValue
        } else {
            0f
        }
    val thumbOffsetPx =
        ((containerWidthPx - thumbWidthPx) * scrollFraction).toInt()

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(thickness + SCROLLBAR_PADDING * 2)
                .padding(vertical = SCROLLBAR_PADDING)
                .graphicsLayer { this.alpha = alpha },
    ) {
        // Track
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(thickness)
                    .background(trackColor, thumbShape),
        )

        // Thumb
        Box(
            modifier =
                Modifier
                    .height(thickness)
                    .width(thumbWidthDp)
                    .offset { IntOffset(thumbOffsetPx, 0) }
                    .background(thumbColor, thumbShape),
        )
    }
}
