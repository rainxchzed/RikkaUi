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
import zed.rainxch.rikkaui.components.theme.RikkaTheme

// ─── Subcompose Slots ──────────────────────────────────────

/**
 * Internal enum identifying each slot in [Scaffold]'s subcompose layout.
 */
@Immutable
private enum class ScaffoldSlot {
    TopBar,
    BottomBar,
    Fab,
    SnackbarHost,
    Content,
}

// ─── Content Window Insets ──────────────────────────────────

/**
 * Inset values applied to the content area of [Scaffold].
 *
 * Use this to account for system bars, notches, or other UI chrome
 * that the scaffold should respect when laying out its content area.
 * These insets are added to the padding values provided to the
 * content lambda, on top of the bar heights.
 *
 * ```
 * Scaffold(
 *     contentWindowInsets = ScaffoldWindowInsets(
 *         left = 16.dp,
 *         right = 16.dp,
 *     ),
 * ) { paddingValues ->
 *     // paddingValues includes top/bottom bar heights + insets
 *     Column(Modifier.padding(paddingValues)) { ... }
 * }
 * ```
 *
 * @param left Inset from the left edge.
 * @param top Inset from the top edge (added to topBar height).
 * @param right Inset from the right edge.
 * @param bottom Inset from the bottom edge (added to bottomBar height).
 */
@Immutable
data class ScaffoldWindowInsets(
    val left: Dp = 0.dp,
    val top: Dp = 0.dp,
    val right: Dp = 0.dp,
    val bottom: Dp = 0.dp,
)

// ─── Component ─────────────────────────────────────────────

/**
 * Page-level layout structure for the RikkaUi design system.
 *
 * Provides slot-based layout with [topBar], [bottomBar],
 * [floatingActionButton], [snackbarHost], and [content] areas. Uses
 * [SubcomposeLayout] to measure bar heights dynamically and
 * pass correct [PaddingValues] to [content].
 *
 * This is a pure layout primitive — no borders, shadows, or
 * decorations. Inspired by M3 Scaffold and web layout patterns,
 * built entirely on `compose.foundation`.
 *
 * Usage:
 * ```
 * Scaffold(
 *     topBar = {
 *         Box(
 *             Modifier
 *                 .fillMaxWidth()
 *                 .background(RikkaTheme.colors.card)
 *                 .padding(RikkaTheme.spacing.md),
 *         ) {
 *             Text("My App", variant = TextVariant.H4)
 *         }
 *     },
 *     bottomBar = {
 *         Box(
 *             Modifier
 *                 .fillMaxWidth()
 *                 .background(RikkaTheme.colors.card)
 *                 .padding(RikkaTheme.spacing.md),
 *         ) {
 *             Text("Footer")
 *         }
 *     },
 * ) { paddingValues ->
 *     Column(Modifier.padding(paddingValues)) {
 *         Text("Page content goes here")
 *     }
 * }
 * ```
 *
 * With a snackbar host and FAB:
 * ```
 * Scaffold(
 *     floatingActionButton = {
 *         Button(
 *             onClick = { /* action */ },
 *             size = ButtonSize.Icon,
 *         ) {
 *             Icon(RikkaIcons.Plus, "Add")
 *         }
 *     },
 *     snackbarHost = {
 *         // Your custom snackbar/toast composable
 *         MyToastHost(hostState = toastHostState)
 *     },
 * ) { paddingValues ->
 *     Column(Modifier.padding(paddingValues)) {
 *         Text("Content with FAB and snackbar")
 *     }
 * }
 * ```
 *
 * With content window insets:
 * ```
 * Scaffold(
 *     contentWindowInsets = ScaffoldWindowInsets(
 *         left = 16.dp,
 *         right = 16.dp,
 *         bottom = 24.dp,
 *     ),
 * ) { paddingValues ->
 *     Column(Modifier.padding(paddingValues)) {
 *         Text("Content respects system insets")
 *     }
 * }
 * ```
 *
 * @param modifier Modifier applied to the outer container.
 * @param topBar Slot rendered at the top, spanning full width.
 * @param bottomBar Slot rendered at the bottom, spanning full width.
 * @param floatingActionButton Slot positioned at bottom-end with
 *   theme-based padding offset above the bottom bar.
 * @param snackbarHost Slot for hosting snackbar or toast notifications.
 *   Positioned above the bottom bar (and above the FAB if present),
 *   centered horizontally.
 * @param containerColor Background color for the scaffold.
 *   Defaults to [RikkaTheme.colors.background].
 * @param contentColor Foreground color hint for content.
 *   Passed for semantic use; does not auto-apply to children.
 * @param contentWindowInsets Insets applied to the content area,
 *   added on top of bar heights in the [PaddingValues] given to
 *   [content]. Defaults to zero on all sides.
 * @param content Main content area. Receives [PaddingValues]
 *   accounting for topBar and bottomBar heights plus
 *   [contentWindowInsets] so content does not render beneath
 *   the bars or system chrome.
 */
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
