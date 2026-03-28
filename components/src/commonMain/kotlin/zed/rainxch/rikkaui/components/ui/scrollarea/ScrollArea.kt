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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme

// ─── Constants ─────────────────────────────────────────────

private val SCROLLBAR_THICKNESS: Dp = 4.dp
private val SCROLLBAR_MIN_THUMB: Dp = 24.dp
private val SCROLLBAR_PADDING: Dp = 2.dp

// ─── ScrollArea (Vertical) ─────────────────────────────────

/**
 * Vertical scrollable container with a custom scrollbar indicator.
 *
 * Provides a thin rounded scrollbar on the right side that fades
 * in when content is scrolled and fades out when idle. The scrollbar
 * thumb position and size are calculated from the scroll state.
 *
 * Usage:
 * ```
 * ScrollArea(modifier = Modifier.fillMaxSize()) {
 *     repeat(50) { index ->
 *         Text("Item $index")
 *     }
 * }
 * ```
 *
 * @param modifier Modifier for layout and decoration.
 * @param content Scrollable column content.
 */
@Composable
fun ScrollArea(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val scrollState = rememberScrollState()
    val motion = RikkaTheme.motion
    val shape = RikkaTheme.shapes.md

    // Track container height in px for thumb calculation
    val containerHeightPx = remember { androidx.compose.runtime.mutableIntStateOf(0) }

    // Whether the content overflows and scrollbar is needed
    val isScrollable by remember {
        derivedStateOf { scrollState.maxValue > 0 }
    }

    // Scrollbar visibility: show when scrollable and content is being scrolled
    val isScrolling by remember {
        derivedStateOf { scrollState.isScrollInProgress }
    }

    val scrollbarAlpha by animateFloatAsState(
        targetValue = if (isScrolling && isScrollable) 1f else if (isScrollable) 0.4f else 0f,
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
                    .padding(end = if (isScrollable) SCROLLBAR_THICKNESS + SCROLLBAR_PADDING * 2 else 0.dp),
            content = content,
        )

        // Scrollbar track + thumb
        if (isScrollable) {
            VerticalScrollbar(
                scrollValue = scrollState.value,
                maxScrollValue = scrollState.maxValue,
                containerHeightPx = containerHeightPx.intValue,
                alpha = scrollbarAlpha,
                modifier = Modifier.align(Alignment.CenterEnd),
            )
        }
    }
}

// ─── HorizontalScrollArea ──────────────────────────────────

/**
 * Horizontal scrollable container with a custom scrollbar indicator.
 *
 * Provides a thin rounded scrollbar on the bottom that fades
 * in when content is scrolled and fades out when idle.
 *
 * Usage:
 * ```
 * HorizontalScrollArea(modifier = Modifier.fillMaxWidth()) {
 *     repeat(20) { index ->
 *         Box(modifier = Modifier.size(100.dp).background(RikkaTheme.colors.muted))
 *     }
 * }
 * ```
 *
 * @param modifier Modifier for layout and decoration.
 * @param content Scrollable row content.
 */
@Composable
fun HorizontalScrollArea(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    val scrollState = rememberScrollState()
    val motion = RikkaTheme.motion
    val shape = RikkaTheme.shapes.md

    val containerWidthPx = remember { androidx.compose.runtime.mutableIntStateOf(0) }

    val isScrollable by remember {
        derivedStateOf { scrollState.maxValue > 0 }
    }

    val isScrolling by remember {
        derivedStateOf { scrollState.isScrollInProgress }
    }

    val scrollbarAlpha by animateFloatAsState(
        targetValue = if (isScrolling && isScrollable) 1f else if (isScrollable) 0.4f else 0f,
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
                    .padding(bottom = if (isScrollable) SCROLLBAR_THICKNESS + SCROLLBAR_PADDING * 2 else 0.dp),
            content = content,
        )

        // Scrollbar track + thumb
        if (isScrollable) {
            HorizontalScrollbar(
                scrollValue = scrollState.value,
                maxScrollValue = scrollState.maxValue,
                containerWidthPx = containerWidthPx.intValue,
                alpha = scrollbarAlpha,
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

// ─── Internal: Vertical Scrollbar ──────────────────────────

/**
 * Draws a vertical scrollbar track and thumb.
 *
 * @param scrollValue Current scroll position in pixels.
 * @param maxScrollValue Maximum scroll position in pixels.
 * @param containerHeightPx Container height in pixels.
 * @param alpha Overall scrollbar alpha for fade animation.
 * @param modifier Modifier for positioning.
 */
@Composable
private fun VerticalScrollbar(
    scrollValue: Int,
    maxScrollValue: Int,
    containerHeightPx: Int,
    alpha: Float,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val trackColor = RikkaTheme.colors.muted.copy(alpha = 0.5f)
    val thumbColor = RikkaTheme.colors.mutedForeground
    val thumbShape = RoundedCornerShape(50)

    val minThumbPx: Float = with(density) { SCROLLBAR_MIN_THUMB.toPx() }

    // Calculate thumb size and position
    val totalContentPx = containerHeightPx + maxScrollValue
    val thumbFraction =
        if (totalContentPx > 0) {
            (containerHeightPx.toFloat() / totalContentPx).coerceIn(0.05f, 1f)
        } else {
            1f
        }
    val thumbHeightPx = (containerHeightPx * thumbFraction).coerceAtLeast(minThumbPx)
    val thumbHeightDp: Dp = with(density) { thumbHeightPx.toDp() }

    val scrollFraction =
        if (maxScrollValue > 0) {
            scrollValue.toFloat() / maxScrollValue
        } else {
            0f
        }
    val thumbOffsetPx = ((containerHeightPx - thumbHeightPx) * scrollFraction).toInt()

    Box(
        modifier =
            modifier
                .fillMaxHeight()
                .width(SCROLLBAR_THICKNESS + SCROLLBAR_PADDING * 2)
                .padding(horizontal = SCROLLBAR_PADDING)
                .graphicsLayer { this.alpha = alpha },
    ) {
        // Track
        Box(
            modifier =
                Modifier
                    .fillMaxHeight()
                    .width(SCROLLBAR_THICKNESS)
                    .background(trackColor, thumbShape),
        )

        // Thumb
        Box(
            modifier =
                Modifier
                    .width(SCROLLBAR_THICKNESS)
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
 * @param modifier Modifier for positioning.
 */
@Composable
private fun HorizontalScrollbar(
    scrollValue: Int,
    maxScrollValue: Int,
    containerWidthPx: Int,
    alpha: Float,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val trackColor = RikkaTheme.colors.muted.copy(alpha = 0.5f)
    val thumbColor = RikkaTheme.colors.mutedForeground
    val thumbShape = RoundedCornerShape(50)

    val minThumbPx: Float = with(density) { SCROLLBAR_MIN_THUMB.toPx() }

    val totalContentPx = containerWidthPx + maxScrollValue
    val thumbFraction =
        if (totalContentPx > 0) {
            (containerWidthPx.toFloat() / totalContentPx).coerceIn(0.05f, 1f)
        } else {
            1f
        }
    val thumbWidthPx = (containerWidthPx * thumbFraction).coerceAtLeast(minThumbPx)
    val thumbWidthDp: Dp = with(density) { thumbWidthPx.toDp() }

    val scrollFraction =
        if (maxScrollValue > 0) {
            scrollValue.toFloat() / maxScrollValue
        } else {
            0f
        }
    val thumbOffsetPx = ((containerWidthPx - thumbWidthPx) * scrollFraction).toInt()

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(SCROLLBAR_THICKNESS + SCROLLBAR_PADDING * 2)
                .padding(vertical = SCROLLBAR_PADDING)
                .graphicsLayer { this.alpha = alpha },
    ) {
        // Track
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(SCROLLBAR_THICKNESS)
                    .background(trackColor, thumbShape),
        )

        // Thumb
        Box(
            modifier =
                Modifier
                    .height(SCROLLBAR_THICKNESS)
                    .width(thumbWidthDp)
                    .offset { IntOffset(thumbOffsetPx, 0) }
                    .background(thumbColor, thumbShape),
        )
    }
}
