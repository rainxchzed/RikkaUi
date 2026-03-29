package zed.rainxch.rikkaui.components.ui.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Subcompose Slots ──────────────────────────────────────

@Immutable
private enum class ScaffoldSlot {
    TopBar,
    BottomBar,
    Fab,
    SnackbarHost,
    Content,
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
    snackbarHost: @Composable () -> Unit = {},
    containerColor: Color = RikkaTheme.colors.background,
    contentColor: Color = RikkaTheme.colors.foreground,
    contentWindowInsets: ScaffoldWindowInsets = ScaffoldWindowInsets(),
    content: @Composable (PaddingValues) -> Unit,
) {
    val fabSpacing = RikkaTheme.spacing.md

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

        // ── Measure FAB ────────────────────────────────────
        val fabPlaceables =
            subcompose(ScaffoldSlot.Fab, floatingActionButton)
                .map { it.measure(looseConstraints) }
        val fabWidth = fabPlaceables.maxOfOrNull { it.width } ?: 0
        val fabHeight = fabPlaceables.maxOfOrNull { it.height } ?: 0

        // ── Measure snackbar host ──────────────────────────
        val snackbarPlaceables =
            subcompose(
                ScaffoldSlot.SnackbarHost,
                snackbarHost,
            ).map { it.measure(looseConstraints) }
        val snackbarWidth = snackbarPlaceables.maxOfOrNull { it.width } ?: 0
        val snackbarHeight = snackbarPlaceables.maxOfOrNull { it.height } ?: 0

        // ── Measure content ────────────────────────────────
        val totalTopPadding = topBarHeight + insetTop
        val totalBottomPadding = bottomBarHeight + insetBottom

        val contentPaddingValues =
            PaddingValues(
                start = contentWindowInsets.left,
                top = totalTopPadding.toDp(),
                end = contentWindowInsets.right,
                bottom = totalBottomPadding.toDp(),
            )
        val contentConstraints =
            looseConstraints.offset(
                horizontal = -(insetLeft + insetRight),
                vertical = -(totalTopPadding + totalBottomPadding),
            )
        val contentPlaceables =
            subcompose(ScaffoldSlot.Content) {
                content(contentPaddingValues)
            }.map { it.measure(contentConstraints) }

        // ── Place everything ───────────────────────────────
        layout(layoutWidth, layoutHeight) {
            // Content below top bar + top inset
            contentPlaceables.forEach {
                it.placeRelative(insetLeft, totalTopPadding)
            }

            // Top bar at y=0
            topBarPlaceables.forEach { it.placeRelative(0, 0) }

            // Bottom bar pinned to bottom
            bottomBarPlaceables.forEach {
                it.placeRelative(0, layoutHeight - bottomBarHeight)
            }

            // FAB at bottom-end, above bottom bar
            fabPlaceables.forEach {
                val fabX = layoutWidth - fabWidth - fabPadding
                val fabY =
                    layoutHeight - bottomBarHeight - fabHeight - fabPadding
                it.placeRelative(fabX, fabY)
            }

            // Snackbar centered horizontally, above bottom bar
            // (and above FAB if present)
            snackbarPlaceables.forEach {
                val snackbarX = (layoutWidth - snackbarWidth) / 2
                val fabOffset =
                    if (fabHeight > 0) fabHeight + fabPadding else 0
                val snackbarY =
                    layoutHeight - bottomBarHeight -
                        fabOffset - snackbarHeight - fabPadding
                it.placeRelative(snackbarX, snackbarY)
            }
        }
    }
}
