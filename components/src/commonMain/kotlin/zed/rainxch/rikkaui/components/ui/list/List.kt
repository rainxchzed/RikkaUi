package zed.rainxch.rikkaui.components.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

// ─── Variant ────────────────────────────────────────────────

/**
 * List style variants.
 *
 * - [Unordered] — Bullet points (•).
 * - [Ordered] — Numbered items (1. 2. 3.).
 * - [None] — No markers, just indented items.
 */
enum class ListVariant {
    Unordered,
    Ordered,
    None,
}

// ─── Component ──────────────────────────────────────────────

/**
 * Styled list component for the RikkaUi design system.
 *
 * Renders a vertical list with bullet points, numbers, or no markers.
 * Uses theme spacing and typography tokens for consistent styling.
 *
 * Usage:
 * ```
 * // Bullet list
 * RikkaList(items = listOf("First item", "Second item", "Third item"))
 *
 * // Numbered list
 * RikkaList(
 *     items = listOf("Clone the repo", "Install dependencies", "Run the app"),
 *     variant = ListVariant.Ordered,
 * )
 *
 * // Custom content per item
 * RikkaList(variant = ListVariant.Unordered) {
 *     ListItem { Text("Custom content here") }
 *     ListItem { Text("Another item") }
 * }
 * ```
 *
 * @param items List of string items to render.
 * @param modifier Modifier for layout and decoration.
 * @param variant List style — bullet, numbered, or none.
 * @param textVariant Typography variant for list item text.
 */
@Composable
fun RikkaList(
    items: List<String>,
    modifier: Modifier = Modifier,
    variant: ListVariant = ListVariant.Unordered,
    textVariant: TextVariant = TextVariant.P,
) {
    Column(
        modifier = modifier.padding(start = RikkaTheme.spacing.md),
        verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
    ) {
        items.forEachIndexed { index, item ->
            ListItemRow(
                marker = resolveMarker(variant, index),
                textVariant = textVariant,
            ) {
                Text(text = item, variant = textVariant)
            }
        }
    }
}

/**
 * Styled list with custom content per item.
 *
 * Use [ListItem] inside the content lambda to define each item.
 *
 * ```
 * RikkaList(variant = ListVariant.Ordered) {
 *     ListItem { Text("Step one") }
 *     ListItem { Text("Step two") }
 * }
 * ```
 *
 * @param modifier Modifier for layout and decoration.
 * @param variant List style — bullet, numbered, or none.
 * @param content List items — use [ListItem] composables.
 */
@Composable
fun RikkaList(
    modifier: Modifier = Modifier,
    variant: ListVariant = ListVariant.Unordered,
    content: @Composable ListScope.() -> Unit,
) {
    val scope = ListScopeImpl(variant)
    scope.content()

    Column(
        modifier = modifier.padding(start = RikkaTheme.spacing.md),
        verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
    ) {
        scope.items.forEachIndexed { index, itemContent ->
            ListItemRow(
                marker = resolveMarker(variant, index),
                textVariant = TextVariant.P,
            ) {
                itemContent()
            }
        }
    }
}

// ─── ListItem ───────────────────────────────────────────────

/**
 * Scope for building list items inside [RikkaList].
 */
interface ListScope {
    /**
     * Adds an item to the list.
     *
     * @param content The composable content for this list item.
     */
    fun ListItem(content: @Composable () -> Unit)
}

private class ListScopeImpl(
    private val variant: ListVariant,
) : ListScope {
    val items = mutableListOf<@Composable () -> Unit>()

    override fun ListItem(content: @Composable () -> Unit) {
        items.add(content)
    }
}

// ─── Internal ───────────────────────────────────────────────

@Composable
private fun ListItemRow(
    marker: String,
    textVariant: TextVariant,
    content: @Composable () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
    ) {
        if (marker.isNotEmpty()) {
            Text(
                text = marker,
                variant = textVariant,
                color = RikkaTheme.colors.mutedForeground,
                modifier = Modifier.widthIn(min = 20.dp),
                style = TextStyle.Default,
            )
        }
        content()
    }
}

private fun resolveMarker(variant: ListVariant, index: Int): String =
    when (variant) {
        ListVariant.Unordered -> "•"
        ListVariant.Ordered -> "${index + 1}."
        ListVariant.None -> ""
    }
