package zed.rainxch.rikkaui.components.ui.accordion

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

// ─── Component ──────────────────────────────────────────────

/**
 * Accordion item component for the RikkaUi design system.
 *
 * An expandable/collapsible content section with an animated
 * chevron indicator and smooth height transition. Maps to
 * shadcn/ui's Accordion component.
 *
 * Features:
 * - Spring-physics chevron rotation (90 degrees when expanded)
 * - AnimatedVisibility with vertical expand/shrink transitions
 * - Bottom border separator for visual grouping
 * - No Material dependency
 *
 * Usage:
 * ```
 * var expanded by remember { mutableStateOf(false) }
 *
 * AccordionItem(
 *     title = "Is it accessible?",
 *     expanded = expanded,
 *     onExpandedChange = { expanded = it },
 * ) {
 *     Text("Yes. It adheres to the WAI-ARIA design pattern.")
 * }
 *
 * // Multiple items in a Column
 * Column {
 *     items.forEachIndexed { index, item ->
 *         var open by remember { mutableStateOf(false) }
 *         AccordionItem(
 *             title = item.title,
 *             expanded = open,
 *             onExpandedChange = { open = it },
 *         ) {
 *             Text(item.description, variant = TextVariant.Muted)
 *         }
 *     }
 * }
 * ```
 *
 * @param title The header text displayed in the clickable title row.
 * @param expanded Whether the content section is currently visible.
 * @param onExpandedChange Called when the user clicks the title row to toggle.
 * @param modifier Modifier for layout and decoration.
 * @param content The expandable content slot.
 */
@Composable
fun AccordionItem(
    title: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val colors = RikkaTheme.colors
    val spacing = RikkaTheme.spacing
    val motion = RikkaTheme.motion
    val interactionSource = remember { MutableInteractionSource() }

    // ─── Chevron rotation (spring physics) ───────────────
    val chevronRotation by animateFloatAsState(
        targetValue = if (expanded) 90f else 0f,
        animationSpec = motion.springDefault,
    )

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        // ─── Title row ───────────────────────────────────
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        role = Role.Button,
                        onClick = { onExpandedChange(!expanded) },
                    )
                    .padding(vertical = spacing.lg)
                    .semantics(mergeDescendants = true) {
                        contentDescription = title
                        stateDescription = if (expanded) "Expanded" else "Collapsed"
                    },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Title text
            Text(
                text = title,
                variant = TextVariant.P,
                style = TextStyle(fontWeight = FontWeight.Medium),
                modifier = Modifier.weight(1f),
            )

            // Chevron indicator — rotates 90 degrees when expanded.
            Icon(
                imageVector = RikkaIcons.ChevronRight,
                contentDescription = null,
                tint = colors.mutedForeground,
                modifier =
                    Modifier.graphicsLayer {
                        rotationZ = chevronRotation
                    },
            )
        }

        // ─── Expandable content ──────────────────────────
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(),
            exit = shrinkVertically(),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = spacing.lg),
            ) {
                content()
            }
        }

        // ─── Bottom border separator ─────────────────────
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(colors.border),
        )
    }
}
