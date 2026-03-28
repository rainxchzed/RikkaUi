package zed.rainxch.rikkaui.creator.preview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

/**
 * A staggered (masonry-style) grid that distributes
 * composables across [columns] columns. Each item is
 * placed in the next column round-robin style, allowing
 * different-height cards to pack tightly without gaps.
 *
 * ```
 *  ┌─────┐ ┌─────┐ ┌─────┐
 *  │  1  │ │  2  │ │  3  │
 *  │     │ └─────┘ │     │
 *  └─────┘ ┌─────┐ └─────┘
 *  ┌─────┐ │  5  │ ┌─────┐
 *  │  4  │ │     │ │  6  │
 *  └─────┘ │     │ └─────┘
 *           └─────┘
 * ```
 */
@Composable
fun StaggeredGrid(
    columns: Int,
    spacing: Dp,
    modifier: Modifier = Modifier,
    content: StaggeredGridScope.() -> Unit,
) {
    val scope = StaggeredGridScopeImpl().apply(content)
    val items = scope.items

    // Distribute items round-robin across columns
    val columnItems = List(columns) { col ->
        items.filterIndexed { index, _ -> index % columns == col }
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(spacing),
    ) {
        columnItems.forEach { colItems ->
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement =
                    Arrangement.spacedBy(spacing),
            ) {
                colItems.forEach { item -> item() }
            }
        }
    }
}

/** Scope for [StaggeredGrid] content builder. */
interface StaggeredGridScope {
    /** Adds a composable item to the staggered grid. */
    fun item(content: @Composable () -> Unit)
}

private class StaggeredGridScopeImpl : StaggeredGridScope {
    val items = mutableListOf<@Composable () -> Unit>()

    override fun item(content: @Composable () -> Unit) {
        items.add(content)
    }
}
