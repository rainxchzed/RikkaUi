package zed.rainxch.rikkaui.components.ui.breadcrumb

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── BreadcrumbAnimation ────────────────────────────────────

/**
 * Controls how breadcrumb items appear when the component is first
 * composed.
 *
 * Usage:
 * ```
 * Breadcrumb(
 *     items = items,
 *     animation = BreadcrumbAnimation.Slide,
 * )
 * ```
 *
 * @property Fade Items fade in sequentially.
 * @property Slide Items slide in from the left.
 * @property None Items appear instantly (default).
 */
enum class BreadcrumbAnimation {
    /** Items fade in one after another with a staggered delay. */
    Fade,

    /** Items slide in from the left with a staggered delay. */
    Slide,

    /** Items appear immediately with no animation. */
    None,
}

// ─── Data ──────────────────────────────────────────────────

/**
 * Data class representing a single breadcrumb item.
 *
 * @param label Display text for the breadcrumb.
 * @param onClick Click handler. Pass `null` for the current/last
 *   item (non-clickable).
 */
@Immutable
data class BreadcrumbItemData(
    val label: String,
    val onClick: (() -> Unit)? = null,
)

// ─── Constants ─────────────────────────────────────────────

private const val STAGGER_DELAY_MS = 60
private const val SLIDE_OFFSET_PX = -12f
private const val ELLIPSIS_TEXT = "..."

// ─── Component ─────────────────────────────────────────────

/**
 * Breadcrumb navigation container for the RikkaUi design system.
 *
 * A horizontal Row that lays out [BreadcrumbItem] and
 * [BreadcrumbSeparator] children, providing a navigation landmark
 * for accessibility.
 *
 * Usage:
 * ```
 * Breadcrumb {
 *     BreadcrumbItem("Home", onClick = { navigateHome() })
 *     BreadcrumbSeparator()
 *     BreadcrumbItem("Products", onClick = { navigateProducts() })
 *     BreadcrumbSeparator()
 *     BreadcrumbItem("Widget")  // current page, no onClick
 * }
 * ```
 *
 * @param modifier Modifier for layout and decoration.
 * @param content Row content — typically [BreadcrumbItem] and
 *   [BreadcrumbSeparator] composables.
 */
@Composable
fun Breadcrumb(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier =
            modifier.semantics {
                contentDescription = "Breadcrumb navigation"
            },
        horizontalArrangement =
            Arrangement.spacedBy(
                RikkaTheme.spacing.sm,
            ),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

/**
 * Convenience overload that builds a breadcrumb trail from a list
 * of [BreadcrumbItemData].
 *
 * Automatically inserts separators between items. Supports entrance
 * animations, custom separators, and collapsing to an ellipsis when
 * there are more items than [maxVisibleItems].
 *
 * Usage:
 * ```
 * Breadcrumb(
 *     items = listOf(
 *         BreadcrumbItemData("Home", onClick = { navigateHome() }),
 *         BreadcrumbItemData("Products", onClick = { navigateProducts() }),
 *         BreadcrumbItemData("Widget"),
 *     ),
 *     animation = BreadcrumbAnimation.Fade,
 *     maxVisibleItems = 3,
 * )
 * ```
 *
 * @param items List of breadcrumb items to display.
 * @param modifier Modifier for layout and decoration.
 * @param animation Entrance animation for items.
 * @param separator Custom separator composable placed between items.
 *   When `null` the default "/" separator is used.
 * @param maxVisibleItems Maximum number of items to display before
 *   collapsing middle items into an ellipsis. Pass `0` or a value
 *   greater than or equal to [items] size to show all items.
 * @param onEllipsisClick Optional click handler for the ellipsis
 *   element shown when items are collapsed.
 */
@Composable
fun Breadcrumb(
    items: List<BreadcrumbItemData>,
    modifier: Modifier = Modifier,
    animation: BreadcrumbAnimation = BreadcrumbAnimation.None,
    separator: (@Composable () -> Unit)? = null,
    maxVisibleItems: Int = 0,
    onEllipsisClick: (() -> Unit)? = null,
) {
    val visibleItems = resolveVisibleItems(items, maxVisibleItems)

    Breadcrumb(modifier = modifier) {
        visibleItems.forEachIndexed { index, entry ->
            val animIndex = index

            when (entry) {
                is VisibleEntry.Item -> {
                    AnimatedBreadcrumbItem(
                        text = entry.data.label,
                        onClick = entry.data.onClick,
                        animation = animation,
                        index = animIndex,
                    )
                }

                is VisibleEntry.Ellipsis -> {
                    AnimatedBreadcrumbItem(
                        text = ELLIPSIS_TEXT,
                        onClick = onEllipsisClick,
                        animation = animation,
                        index = animIndex,
                    )
                }

                is VisibleEntry.Separator -> {
                    if (separator != null) {
                        separator()
                    } else {
                        BreadcrumbSeparator()
                    }
                }
            }
        }
    }
}

// ─── BreadcrumbItem ────────────────────────────────────────

/**
 * A single item in a breadcrumb trail.
 *
 * When [onClick] is non-null the item is interactive: it renders in
 * [RikkaTheme.colors.mutedForeground] and gains an underline on
 * hover. When [onClick] is `null` the item represents the current
 * page and renders in [RikkaTheme.colors.foreground] with no
 * interaction.
 *
 * Usage:
 * ```
 * BreadcrumbItem("Home", onClick = { navigateHome() })
 * BreadcrumbItem("Current Page")  // non-clickable
 * ```
 *
 * @param text Display text.
 * @param onClick Click handler. Pass `null` for the current/last item.
 * @param modifier Modifier for layout and decoration.
 */
@Composable
fun BreadcrumbItem(
    text: String,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    if (onClick != null) {
        val interactionSource = remember { MutableInteractionSource() }
        val isHovered by interactionSource.collectIsHoveredAsState()

        val textStyle =
            if (isHovered) {
                TextStyle(textDecoration = TextDecoration.Underline)
            } else {
                TextStyle(textDecoration = TextDecoration.None)
            }

        Text(
            text = text,
            variant = TextVariant.Small,
            color = RikkaTheme.colors.mutedForeground,
            style = textStyle,
            modifier =
                modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        role = Role.Button,
                        onClick = onClick,
                    ).semantics {
                        contentDescription = text
                    },
        )
    } else {
        Text(
            text = text,
            variant = TextVariant.Small,
            color = RikkaTheme.colors.foreground,
            modifier =
                modifier.semantics {
                    contentDescription = "$text, current page"
                },
        )
    }
}

// ─── BreadcrumbSeparator ───────────────────────────────────

/**
 * Separator between breadcrumb items. Defaults to "/" in muted
 * foreground.
 *
 * Usage:
 * ```
 * // Default separator
 * BreadcrumbSeparator()
 *
 * // Custom separator content
 * BreadcrumbSeparator {
 *     Text(
 *         ">",
 *         color = RikkaTheme.colors.mutedForeground,
 *         variant = TextVariant.Small,
 *     )
 * }
 * ```
 *
 * @param modifier Modifier for layout and decoration.
 * @param content Custom separator content. Defaults to "/" text.
 */
@Composable
fun BreadcrumbSeparator(
    modifier: Modifier = Modifier,
    content: (@Composable () -> Unit)? = null,
) {
    if (content != null) {
        content()
    } else {
        Text(
            text = "/",
            variant = TextVariant.Small,
            color = RikkaTheme.colors.mutedForeground,
            modifier = modifier,
        )
    }
}

// ─── Internal: Animated wrapper ────────────────────────────

/**
 * Wraps a [BreadcrumbItem] with entrance animation driven by
 * [RikkaTheme.motion] tokens.
 */
@Composable
private fun AnimatedBreadcrumbItem(
    text: String,
    onClick: (() -> Unit)?,
    animation: BreadcrumbAnimation,
    index: Int,
) {
    if (animation == BreadcrumbAnimation.None) {
        BreadcrumbItem(text = text, onClick = onClick)
        return
    }

    val motion = RikkaTheme.motion
    val delayMs = index * STAGGER_DELAY_MS

    // Trigger animation on first composition
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    val animatedAlpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec =
            tween(
                durationMillis = motion.durationEnter,
                delayMillis = delayMs,
            ),
    )

    val animatedTranslateX by animateFloatAsState(
        targetValue = if (visible) 0f else SLIDE_OFFSET_PX,
        animationSpec =
            tween(
                durationMillis = motion.durationEnter,
                delayMillis = delayMs,
            ),
    )

    val itemModifier =
        when (animation) {
            BreadcrumbAnimation.Fade -> {
                Modifier.graphicsLayer {
                    alpha = animatedAlpha
                }
            }

            BreadcrumbAnimation.Slide -> {
                Modifier.graphicsLayer {
                    alpha = animatedAlpha
                    translationX = animatedTranslateX
                }
            }

            BreadcrumbAnimation.None -> {
                Modifier
            }
        }

    BreadcrumbItem(
        text = text,
        onClick = onClick,
        modifier = itemModifier,
    )
}

// ─── Internal: Ellipsis / collapse logic ───────────────────

/**
 * Sealed interface for items in the resolved visible list.
 */
private sealed interface VisibleEntry {
    data class Item(
        val data: BreadcrumbItemData,
    ) : VisibleEntry

    data object Ellipsis : VisibleEntry

    data object Separator : VisibleEntry
}

/**
 * Resolves which items should be displayed, collapsing middle
 * items into an ellipsis when [maxVisibleItems] is exceeded.
 *
 * The first and last items are always shown. If collapsing is
 * needed, one slot is reserved for the ellipsis element.
 */
private fun resolveVisibleItems(
    items: List<BreadcrumbItemData>,
    maxVisibleItems: Int,
): List<VisibleEntry> {
    val shouldCollapse = maxVisibleItems in 2 until items.size

    val displayItems: List<BreadcrumbItemData> =
        if (!shouldCollapse) {
            items
        } else {
            // Always show first item + last (maxVisibleItems - 2) items + ellipsis
            val tailCount = (maxVisibleItems - 2).coerceAtLeast(0)
            val first = listOf(items.first())
            val tail =
                items
                    .takeLast(tailCount + 1) // +1 for current page
                    .takeLast(maxVisibleItems - 1)
            first + tail
        }

    val result = mutableListOf<VisibleEntry>()

    if (shouldCollapse) {
        // First item
        result.add(VisibleEntry.Item(items.first()))
        result.add(VisibleEntry.Separator)
        // Ellipsis
        result.add(VisibleEntry.Ellipsis)
        // Remaining tail items with separators
        val tailCount = (maxVisibleItems - 2).coerceAtLeast(0)
        val tailItems =
            items
                .takeLast(tailCount + 1)
                .takeLast(maxVisibleItems - 1)
        tailItems.forEach { item ->
            result.add(VisibleEntry.Separator)
            result.add(VisibleEntry.Item(item))
        }
    } else {
        items.forEachIndexed { index, item ->
            result.add(VisibleEntry.Item(item))
            if (index < items.lastIndex) {
                result.add(VisibleEntry.Separator)
            }
        }
    }

    return result
}
