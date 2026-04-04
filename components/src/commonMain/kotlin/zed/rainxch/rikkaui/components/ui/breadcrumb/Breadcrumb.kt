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
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme
import zed.rainxch.rikkaui.foundation.modifier.minTouchTarget

// ─── BreadcrumbAnimation ────────────────────────────────────

public enum class BreadcrumbAnimation {
    /** Staggered fade-in. */
    Fade,

    /** Staggered slide from left. */
    Slide,

    /** No animation (default). */
    None,
}

// ─── Data ──────────────────────────────────────────────────

@Immutable
public data class BreadcrumbItemData(
    public val label: String,
    public val onClick: (() -> Unit)? = null,
)

// ─── Constants ─────────────────────────────────────────────

private const val STAGGER_DELAY_MS = 60
private const val SLIDE_OFFSET_PX = -12f
private const val ELLIPSIS_TEXT = "..."

// ─── Component ─────────────────────────────────────────────

/**
 * A breadcrumb navigation row for manually composing breadcrumb items and separators.
 *
 * This is the content-lambda overload. Use [BreadcrumbItem] and [BreadcrumbSeparator]
 * inside the [content] lambda. For a data-driven approach, use the list-based overload.
 *
 * @param modifier [Modifier] applied to the breadcrumb row.
 * @param content Row content lambda for [BreadcrumbItem] and [BreadcrumbSeparator] composables.
 */
@Composable
public fun Breadcrumb(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier =
            modifier.semantics {
                contentDescription = "Breadcrumb navigation"
                isTraversalGroup = true
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
 * A data-driven breadcrumb navigation row with automatic separators and optional ellipsis collapse.
 *
 * Renders [BreadcrumbItemData] entries with auto-inserted separators. When [maxVisibleItems]
 * is set, collapses intermediate items into an ellipsis ("...") that can be clicked to expand.
 *
 * @param items List of [BreadcrumbItemData] entries to display. The last item is treated as the current page.
 * @param modifier [Modifier] applied to the breadcrumb row.
 * @param animation [BreadcrumbAnimation] entrance effect (Fade, Slide, or None). Defaults to [BreadcrumbAnimation.None].
 * @param separator Optional custom separator composable. Defaults to a "/" text separator.
 * @param maxVisibleItems Maximum number of items to display before collapsing. Set to 0 to show all. Defaults to 0.
 * @param onEllipsisClick Optional callback invoked when the ellipsis item is clicked.
 */
@Composable
public fun Breadcrumb(
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
 * A single breadcrumb item that renders as a clickable link or static current-page indicator.
 *
 * When [onClick] is provided, the item renders as a muted-color clickable link with hover
 * underline. When null, it renders as the current page in foreground color.
 *
 * @param text Label text displayed for this breadcrumb item.
 * @param onClick Optional click handler. Pass null to mark this as the current (non-clickable) page.
 * @param modifier [Modifier] applied to the item.
 */
@Composable
public fun BreadcrumbItem(
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
            color = RikkaTheme.colors.onMuted,
            style = textStyle,
            modifier =
                modifier
                    .minTouchTarget()
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
            color = RikkaTheme.colors.onBackground,
            modifier =
                modifier.semantics {
                    contentDescription = "$text, current page"
                },
        )
    }
}

// ─── BreadcrumbSeparator ───────────────────────────────────

/**
 * A separator element between breadcrumb items, defaulting to a "/" character.
 *
 * Cleared from accessibility semantics as it is purely decorative.
 *
 * @param modifier [Modifier] applied to the separator.
 * @param content Optional custom separator composable. When null, renders a "/" text separator.
 */
@Composable
public fun BreadcrumbSeparator(
    modifier: Modifier = Modifier,
    content: (@Composable () -> Unit)? = null,
) {
    if (content != null) {
        content()
    } else {
        Text(
            text = "/",
            variant = TextVariant.Small,
            color = RikkaTheme.colors.onMuted,
            modifier = modifier.clearAndSetSemantics {},
        )
    }
}

// ─── Internal: Animated wrapper ────────────────────────────

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

private sealed interface VisibleEntry {
    data class Item(
        val data: BreadcrumbItemData,
    ) : VisibleEntry

    data object Ellipsis : VisibleEntry

    data object Separator : VisibleEntry
}

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
