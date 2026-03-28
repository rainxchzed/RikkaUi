package zed.rainxch.rikkaui.components.ui.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
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
    Content,
}

// ─── Component ─────────────────────────────────────────────

/**
 * Page-level layout structure for the RikkaUi design system.
 *
 * Provides slot-based layout with [topBar], [bottomBar],
 * [floatingActionButton], and [content] areas. Uses
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
 * With a floating action button:
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
 * ) { paddingValues ->
 *     Column(Modifier.padding(paddingValues)) {
 *         Text("Content with FAB")
 *     }
 * }
 * ```
 *
 * @param modifier Modifier applied to the outer container.
 * @param topBar Slot rendered at the top, spanning full width.
 * @param bottomBar Slot rendered at the bottom, spanning full width.
 * @param floatingActionButton Slot positioned at bottom-end with
 *   theme-based padding offset above the bottom bar.
 * @param containerColor Background color for the scaffold.
 *   Defaults to [RikkaTheme.colors.background].
 * @param contentColor Foreground color hint for content.
 *   Passed for semantic use; does not auto-apply to children.
 * @param content Main content area. Receives [PaddingValues]
 *   accounting for topBar and bottomBar heights so content
 *   does not render beneath the bars.
 */
@Composable
fun Scaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    containerColor: Color = RikkaTheme.colors.background,
    contentColor: Color = RikkaTheme.colors.foreground,
    content: @Composable (PaddingValues) -> Unit,
) {
    val fabSpacing = RikkaTheme.spacing.md

    SubcomposeLayout(
        modifier = modifier
            .fillMaxSize()
            .background(containerColor),
    ) { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight
        val fabPadding = fabSpacing.roundToPx()

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        // ── Measure top bar ────────────────────────────────
        val topBarPlaceables = subcompose(ScaffoldSlot.TopBar, topBar)
            .map { it.measure(looseConstraints) }
        val topBarHeight = topBarPlaceables.maxOfOrNull { it.height } ?: 0

        // ── Measure bottom bar ─────────────────────────────
        val bottomBarPlaceables = subcompose(ScaffoldSlot.BottomBar, bottomBar)
            .map { it.measure(looseConstraints) }
        val bottomBarHeight = bottomBarPlaceables.maxOfOrNull { it.height } ?: 0

        // ── Measure FAB ────────────────────────────────────
        val fabPlaceables = subcompose(ScaffoldSlot.Fab, floatingActionButton)
            .map { it.measure(looseConstraints) }
        val fabWidth = fabPlaceables.maxOfOrNull { it.width } ?: 0
        val fabHeight = fabPlaceables.maxOfOrNull { it.height } ?: 0

        // ── Measure content ────────────────────────────────
        val contentPaddingValues = PaddingValues(
            top = topBarHeight.toDp(),
            bottom = bottomBarHeight.toDp(),
        )
        val contentConstraints = looseConstraints.offset(
            vertical = -(topBarHeight + bottomBarHeight),
        )
        val contentPlaceables = subcompose(ScaffoldSlot.Content) {
            content(contentPaddingValues)
        }.map { it.measure(contentConstraints) }

        // ── Place everything ───────────────────────────────
        layout(layoutWidth, layoutHeight) {
            // Content below top bar
            contentPlaceables.forEach { it.placeRelative(0, topBarHeight) }

            // Top bar at y=0
            topBarPlaceables.forEach { it.placeRelative(0, 0) }

            // Bottom bar pinned to bottom
            bottomBarPlaceables.forEach {
                it.placeRelative(0, layoutHeight - bottomBarHeight)
            }

            // FAB at bottom-end, above bottom bar
            fabPlaceables.forEach {
                val fabX = layoutWidth - fabWidth - fabPadding
                val fabY = layoutHeight - bottomBarHeight - fabHeight - fabPadding
                it.placeRelative(fabX, fabY)
            }
        }
    }
}
