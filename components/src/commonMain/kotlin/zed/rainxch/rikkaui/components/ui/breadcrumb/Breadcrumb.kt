package zed.rainxch.rikkaui.components.ui.breadcrumb

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

// ─── Data ──────────────────────────────────────────────────

/**
 * Data class representing a single breadcrumb item.
 *
 * @param label Display text for the breadcrumb.
 * @param onClick Click handler. Pass `null` for the current/last item (non-clickable).
 */
@Immutable
data class BreadcrumbItemData(
    val label: String,
    val onClick: (() -> Unit)? = null,
)

// ─── Component ─────────────────────────────────────────────

/**
 * Breadcrumb navigation container for the RikkaUi design system.
 *
 * A horizontal Row that lays out [BreadcrumbItem] and [BreadcrumbSeparator]
 * children, providing a navigation landmark for accessibility.
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
 * @param content Row content — typically [BreadcrumbItem] and [BreadcrumbSeparator] composables.
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
        horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

/**
 * Convenience overload that builds a breadcrumb trail from a list of [BreadcrumbItemData].
 *
 * Automatically inserts [BreadcrumbSeparator] between items.
 *
 * Usage:
 * ```
 * Breadcrumb(
 *     items = listOf(
 *         BreadcrumbItemData("Home", onClick = { navigateHome() }),
 *         BreadcrumbItemData("Products", onClick = { navigateProducts() }),
 *         BreadcrumbItemData("Widget"),  // current page
 *     ),
 * )
 * ```
 *
 * @param items List of breadcrumb items to display.
 * @param modifier Modifier for layout and decoration.
 */
@Composable
fun Breadcrumb(
    items: List<BreadcrumbItemData>,
    modifier: Modifier = Modifier,
) {
    Breadcrumb(modifier = modifier) {
        items.forEachIndexed { index, item ->
            BreadcrumbItem(
                text = item.label,
                onClick = item.onClick,
            )
            if (index < items.lastIndex) {
                BreadcrumbSeparator()
            }
        }
    }
}

// ─── BreadcrumbItem ────────────────────────────────────────

/**
 * A single item in a breadcrumb trail.
 *
 * When [onClick] is non-null the item is interactive: it renders in
 * [RikkaTheme.colors.mutedForeground] and gains an underline on hover.
 * When [onClick] is `null` the item represents the current page and
 * renders in [RikkaTheme.colors.foreground] with no interaction.
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
 * Separator between breadcrumb items. Defaults to "/" in muted foreground.
 *
 * Usage:
 * ```
 * // Default separator
 * BreadcrumbSeparator()
 *
 * // Custom separator content
 * BreadcrumbSeparator {
 *     Text(">", color = RikkaTheme.colors.mutedForeground, variant = TextVariant.Small)
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
