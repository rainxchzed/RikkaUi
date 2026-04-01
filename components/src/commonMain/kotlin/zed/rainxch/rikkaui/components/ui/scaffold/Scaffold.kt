package zed.rainxch.rikkaui.components.ui.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import zed.rainxch.rikkaui.foundation.LocalContentColor
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Subcompose Slots ──────────────────────────────────────

@Immutable
private enum class ScaffoldSlot {
    TopBar,
    BottomBar,
    Fab,
    SnackbarHost,
    ToastHost,
    Content,
}

// ─── FAB Position ──────────────────────────────────────────

@kotlin.jvm.JvmInline
value class FabPosition internal constructor(
    @Suppress("unused") private val value: Int,
) {
    companion object {
        /** Position FAB at the bottom of the screen at the start, above the bottom bar. */
        val Start = FabPosition(0)

        /** Position FAB at the bottom of the screen in the center, above the bottom bar. */
        val Center = FabPosition(1)

        /** Position FAB at the bottom of the screen at the end, above the bottom bar. */
        val End = FabPosition(2)

        /** Position FAB at the bottom of the screen at the end, overlaying the bottom bar. */
        val EndOverlay = FabPosition(3)
    }

    override fun toString(): String =
        when (this) {
            Start -> "FabPosition.Start"
            Center -> "FabPosition.Center"
            End -> "FabPosition.End"
            else -> "FabPosition.EndOverlay"
        }
}

// ─── Content Window Insets ──────────────────────────────────

@Immutable
data class ScaffoldWindowInsets(
    val left: Dp = 0.dp,
    val top: Dp = 0.dp,
    val right: Dp = 0.dp,
    val bottom: Dp = 0.dp,
)

// ─── Component ─────────────────────────────────────────────

@Composable
fun Scaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    snackbarHost: @Composable () -> Unit = {},
    toastHost: @Composable () -> Unit = {},
    containerColor: Color = RikkaTheme.colors.background,
    contentColor: Color = RikkaTheme.colors.onBackground,
    contentWindowInsets: ScaffoldWindowInsets = ScaffoldWindowInsets(),
    content: @Composable (PaddingValues) -> Unit,
) {
    val fabSpacing = RikkaTheme.spacing.md

    // Smart PaddingValues: remembered once, backing state updated during
    // measurement (before body subcomposition) to avoid recomposition.
    val contentPadding =
        remember {
            MutableScaffoldPaddingValues(PaddingValues(0.dp))
        }

    CompositionLocalProvider(LocalContentColor provides contentColor) {
        SubcomposeLayout(
            modifier =
                modifier
                    .fillMaxSize()
                    .background(containerColor),
        ) { constraints ->
            val layoutWidth = constraints.maxWidth
            val layoutHeight = constraints.maxHeight
            val fabPadding = fabSpacing.roundToPx()

            val insetLeft = contentWindowInsets.left.roundToPx()
            val insetTop = contentWindowInsets.top.roundToPx()
            val insetRight = contentWindowInsets.right.roundToPx()
            val insetBottom = contentWindowInsets.bottom.roundToPx()

            val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

            // ── Measure top bar ────────────────────────────────
            val topBarPlaceables =
                subcompose(ScaffoldSlot.TopBar, topBar)
                    .map { it.measure(looseConstraints) }
            val topBarHeight = topBarPlaceables.maxOfOrNull { it.height } ?: 0

            // ── Measure bottom bar ─────────────────────────────
            val bottomBarPlaceables =
                subcompose(ScaffoldSlot.BottomBar, bottomBar)
                    .map { it.measure(looseConstraints) }
            val bottomBarHeight = bottomBarPlaceables.maxOfOrNull { it.height } ?: 0
            val isBottomBarEmpty = bottomBarHeight == 0

            // ── Measure FAB ────────────────────────────────────
            val fabPlaceables =
                subcompose(ScaffoldSlot.Fab, floatingActionButton)
                    .map {
                        it.measure(
                            looseConstraints.offset(
                                horizontal = -(insetLeft + insetRight),
                                vertical = -insetBottom,
                            ),
                        )
                    }
            val fabWidth = fabPlaceables.maxOfOrNull { it.width } ?: 0
            val fabHeight = fabPlaceables.maxOfOrNull { it.height } ?: 0
            val isFabEmpty = fabWidth == 0 && fabHeight == 0

            // ── FAB placement ──────────────────────────────────
            val fabLeftOffset =
                if (!isFabEmpty) {
                    when (floatingActionButtonPosition) {
                        FabPosition.Start -> {
                            if (layoutDirection == LayoutDirection.Ltr) {
                                fabPadding + insetLeft
                            } else {
                                layoutWidth - fabPadding - fabWidth - insetRight
                            }
                        }

                        FabPosition.Center -> {
                            (layoutWidth - fabWidth + insetLeft - insetRight) / 2
                        }

                        FabPosition.End,
                        FabPosition.EndOverlay,
                        -> {
                            if (layoutDirection == LayoutDirection.Ltr) {
                                layoutWidth - fabPadding - fabWidth - insetRight
                            } else {
                                fabPadding + insetLeft
                            }
                        }

                        else -> {
                            (layoutWidth - fabWidth + insetLeft - insetRight) / 2
                        }
                    }
                } else {
                    0
                }

            val fabOffsetFromBottom =
                if (!isFabEmpty) {
                    if (isBottomBarEmpty || floatingActionButtonPosition == FabPosition.EndOverlay) {
                        fabHeight + fabPadding + insetBottom
                    } else {
                        bottomBarHeight + fabHeight + fabPadding
                    }
                } else {
                    0
                }

            // ── Measure snackbar host ──────────────────────────
            val snackbarPlaceables =
                subcompose(ScaffoldSlot.SnackbarHost, snackbarHost)
                    .map { it.measure(looseConstraints) }
            val snackbarWidth = snackbarPlaceables.maxOfOrNull { it.width } ?: 0
            val snackbarHeight = snackbarPlaceables.maxOfOrNull { it.height } ?: 0

            val snackbarOffsetFromBottom =
                if (snackbarHeight != 0) {
                    snackbarHeight +
                        (
                            if (fabOffsetFromBottom > 0) {
                                fabOffsetFromBottom
                            } else if (!isBottomBarEmpty) {
                                bottomBarHeight
                            } else {
                                insetBottom
                            }
                        )
                } else {
                    0
                }

            // ── Measure toast host (full-screen overlay) ────────
            val toastPlaceables =
                subcompose(ScaffoldSlot.ToastHost, toastHost)
                    .map { it.measure(looseConstraints) }

            // ── Update content padding before measuring body ───
            val totalTopPadding = topBarHeight + insetTop
            val totalBottomPadding = bottomBarHeight + insetBottom

            contentPadding.paddingHolder =
                PaddingValues(
                    start = contentWindowInsets.left,
                    top =
                        if (topBarHeight == 0) {
                            contentWindowInsets.top
                        } else {
                            topBarHeight.toDp()
                        },
                    end = contentWindowInsets.right,
                    bottom =
                        if (isBottomBarEmpty) {
                            contentWindowInsets.bottom
                        } else {
                            bottomBarHeight.toDp()
                        },
                )

            // ── Measure content ────────────────────────────────
            val contentConstraints =
                looseConstraints.offset(
                    horizontal = -(insetLeft + insetRight),
                    vertical = -(totalTopPadding + totalBottomPadding),
                )
            val contentPlaceables =
                subcompose(ScaffoldSlot.Content) {
                    content(contentPadding)
                }.map { it.measure(contentConstraints) }

            // ── Place everything (order = drawing order) ───────
            layout(layoutWidth, layoutHeight) {
                // Body first (lowest layer)
                contentPlaceables.forEach {
                    it.placeRelative(insetLeft, totalTopPadding)
                }
                // Top bar over content
                topBarPlaceables.forEach { it.placeRelative(0, 0) }
                // Bottom bar pinned to bottom
                bottomBarPlaceables.forEach {
                    it.placeRelative(0, layoutHeight - bottomBarHeight)
                }
                // FAB above bottom bar (respects RTL via explicit offset)
                if (!isFabEmpty) {
                    fabPlaceables.forEach {
                        it.place(fabLeftOffset, layoutHeight - fabOffsetFromBottom)
                    }
                }
                // Snackbar centered, above bottom bar / FAB
                snackbarPlaceables.forEach {
                    val snackbarX =
                        (layoutWidth - snackbarWidth + insetLeft - insetRight) / 2
                    it.placeRelative(snackbarX, layoutHeight - snackbarOffsetFromBottom)
                }
                // Toast overlay on top of everything (highest layer)
                toastPlaceables.forEach { it.placeRelative(0, 0) }
            }
        }
    }
}

// ─── Smart PaddingValues ──────────────────────────────────

private class MutableScaffoldPaddingValues(
    initialPadding: PaddingValues,
) : PaddingValues {
    var paddingHolder by mutableStateOf(initialPadding)

    override fun calculateLeftPadding(layoutDirection: LayoutDirection): Dp = paddingHolder.calculateLeftPadding(layoutDirection)

    override fun calculateTopPadding(): Dp = paddingHolder.calculateTopPadding()

    override fun calculateRightPadding(layoutDirection: LayoutDirection): Dp = paddingHolder.calculateRightPadding(layoutDirection)

    override fun calculateBottomPadding(): Dp = paddingHolder.calculateBottomPadding()
}
