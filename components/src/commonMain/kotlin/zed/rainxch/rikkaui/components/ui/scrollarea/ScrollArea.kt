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
import zed.rainxch.rikkaui.foundation.RikkaTheme
import zed.rainxch.rikkaui.foundation.modifier.ScrollFocusMode
import zed.rainxch.rikkaui.foundation.modifier.keyboardScrollable

// ─── ScrollbarAnimation ─────────────────────────────────────

enum class ScrollbarAnimation {
    /** Fades in when scrolling, dims when idle. */
    Fade,

    /** Always visible when content overflows. */
    Always,

    /** No scrollbar rendered. */
    None,
}

// ─── Constants ─────────────────────────────────────────────

private val DEFAULT_SCROLLBAR_THICKNESS: Dp = 4.dp
private val SCROLLBAR_MIN_THUMB: Dp = 24.dp
private val SCROLLBAR_PADDING: Dp = 2.dp

// ─── ScrollArea (Vertical) ─────────────────────────────────

@Composable
fun ScrollArea(
    modifier: Modifier = Modifier,
    scrollbarAnimation: ScrollbarAnimation = ScrollbarAnimation.Fade,
    scrollbarWidth: Dp = DEFAULT_SCROLLBAR_THICKNESS,
    scrollbarColor: Color? = null,
    keyboardScrolling: Boolean = true,
    scrollFocusMode: ScrollFocusMode = ScrollFocusMode.RequestFocus,
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

    val boxModifier =
        if (keyboardScrolling) {
            modifier
                .keyboardScrollable(scrollState, scrollFocusMode)
                .clip(shape)
                .onSizeChanged { containerHeightPx.intValue = it.height }
        } else {
            modifier
                .clip(shape)
                .onSizeChanged { containerHeightPx.intValue = it.height }
        }

    Box(modifier = boxModifier) {
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

@Composable
fun HorizontalScrollArea(
    modifier: Modifier = Modifier,
    scrollbarAnimation: ScrollbarAnimation = ScrollbarAnimation.Fade,
    scrollbarWidth: Dp = DEFAULT_SCROLLBAR_THICKNESS,
    scrollbarColor: Color? = null,
    keyboardScrolling: Boolean = true,
    scrollFocusMode: ScrollFocusMode = ScrollFocusMode.RequestFocus,
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

    val boxModifier =
        if (keyboardScrolling) {
            modifier
                .keyboardScrollable(scrollState, scrollFocusMode)
                .clip(shape)
                .onSizeChanged { containerWidthPx.intValue = it.width }
        } else {
            modifier
                .clip(shape)
                .onSizeChanged { containerWidthPx.intValue = it.width }
        }

    Box(modifier = boxModifier) {
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

private fun resolveScrollbarAlpha(
    animation: ScrollbarAnimation,
    isScrollable: Boolean,
    isScrolling: Boolean,
): Float =
    when (animation) {
        ScrollbarAnimation.Fade -> {
            when {
                isScrolling && isScrollable -> 1f
                isScrollable -> 0.4f
                else -> 0f
            }
        }

        ScrollbarAnimation.Always -> {
            if (isScrollable) 1f else 0f
        }

        ScrollbarAnimation.None -> {
            0f
        }
    }

// ─── Internal: Vertical Scrollbar ──────────────────────────

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
    val thumbColor = thumbColorOverride ?: RikkaTheme.colors.onMuted
    val thumbShape = RikkaTheme.shapes.full

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
    val thumbColor = thumbColorOverride ?: RikkaTheme.colors.onMuted
    val thumbShape = RikkaTheme.shapes.full

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
