package zed.rainxch.rikkaui.creator.preview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

/**
 * A staggered (masonry-style) grid that distributes
 * composables across [columns] columns. Each item is
 * placed in the next column round-robin style, allowing
 * different-height cards to pack tightly without gaps.
 *
 * When [columnWidth] is provided, each column gets a
 * fixed width (enabling horizontal scroll on the parent).
 * When null, columns share available width equally via
 * `Modifier.weight(1f)`.
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
    columnWidth: Dp? = null,
    content: StaggeredGridScope.() -> Unit,
) {
    val scope = StaggeredGridScopeImpl().apply(content)
    val items = scope.items

    // Distribute items round-robin across columns
    val columnItems = List(columns) { col ->
        items.filterIndexed { index, _ -> index % columns == col }
    }

    Row(
        modifier = if (columnWidth != null) {
            modifier
        } else {
            modifier.fillMaxWidth()
        },
        horizontalArrangement = Arrangement.spacedBy(spacing),
    ) {
        columnItems.forEach { colItems ->
            Column(
                modifier = if (columnWidth != null) {
                    Modifier.width(columnWidth)
                } else {
                    Modifier.weight(1f)
                },
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
