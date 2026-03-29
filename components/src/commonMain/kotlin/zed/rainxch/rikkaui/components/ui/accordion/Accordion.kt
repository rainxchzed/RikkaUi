package zed.rainxch.rikkaui.components.ui.accordion

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaMotion
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Animation Enum ─────────────────────────────────────────

/**
 * Controls the expand/collapse animation style for [AccordionItem].
 *
 * Each option produces a different visual feel while reading
 * duration and spring parameters from [RikkaTheme.motion] tokens
 * so the animation stays consistent with the rest of the design
 * system.
 *
 * ```
 * AccordionItem(
 *     title = "Details",
 *     expanded = expanded,
 *     onExpandedChange = { expanded = it },
 *     animation = AccordionAnimation.Tween,
 * ) {
 *     Text("Smooth eased transition")
 * }
 * ```
 */
enum class AccordionAnimation {
    /**
     * Spring-physics expand/collapse with medium bounce.
     * Handles interruptions gracefully and feels organic.
     * This is the default.
     */
    Spring,

    /**
     * Duration-based eased expand/collapse using
     * [RikkaTheme.motion] tween durations. Smoother and more
     * predictable than [Spring], good for data-heavy UIs.
     */
    Tween,

    /**
     * Instant expand/collapse with no animation. Useful for
     * reduced-motion preferences or performance-critical lists.
     */
    None,
}

// ─── Component ──────────────────────────────────────────────

/**
 * Accordion item component for the RikkaUi design system.
 *
 * An expandable/collapsible content section with an animated
 * chevron indicator and smooth height transition. Maps to
 * shadcn/ui's Accordion component.
 *
 * Features:
 * - Configurable animation style via [AccordionAnimation]
 * - Customizable chevron icon via [chevronIcon]
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
 * // With tween animation and custom icon
 * AccordionItem(
 *     title = "Smooth section",
 *     expanded = expanded,
 *     onExpandedChange = { expanded = it },
 *     animation = AccordionAnimation.Tween,
 *     chevronIcon = RikkaIcons.ChevronDown,
 * ) {
 *     Text("Eased transition content")
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
 * @param onExpandedChange Called when the user clicks the title row
 *   to toggle.
 * @param modifier Modifier for layout and decoration.
 * @param animation The expand/collapse animation style. Defaults to
 *   [AccordionAnimation.Spring].
 * @param chevronIcon The icon displayed as the expand indicator.
 *   Defaults to [RikkaIcons.ChevronRight]. Rotates 90 degrees when
 *   expanded. Pass [RikkaIcons.ChevronDown] for a vertical chevron
 *   or any other [ImageVector].
 * @param content The expandable content slot.
 */
@Composable
fun AccordionItem(
    title: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    animation: AccordionAnimation = AccordionAnimation.Spring,
    chevronIcon: ImageVector = RikkaIcons.ChevronRight,
    content: @Composable () -> Unit,
) {
    val colors = RikkaTheme.colors
    val spacing = RikkaTheme.spacing
    val motion = RikkaTheme.motion
    val interactionSource = remember { MutableInteractionSource() }

    // ─── Chevron rotation ─────────────────────────────────
    val chevronRotation by animateFloatAsState(
        targetValue = if (expanded) 90f else 0f,
        animationSpec = resolveFloatSpec(animation, motion),
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
                    ).padding(vertical = spacing.lg)
                    .semantics(mergeDescendants = true) {
                        contentDescription = title
                        stateDescription =
                            if (expanded) "Expanded" else "Collapsed"
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
                imageVector = chevronIcon,
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
            enter = resolveEnter(animation, motion),
            exit = resolveExit(animation, motion),
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

// ─── Private animation resolution ───────────────────────────

/**
 * Resolves a [Float] animation spec from the [AccordionAnimation]
 * and [RikkaTheme.motion] tokens. Used for the chevron rotation.
 */
@Composable
private fun resolveFloatSpec(
    animation: AccordionAnimation,
    motion: RikkaMotion,
): androidx.compose.animation.core.AnimationSpec<Float> =
    when (animation) {
        AccordionAnimation.Spring -> motion.springDefault
        AccordionAnimation.Tween -> motion.tweenDefault
        AccordionAnimation.None -> snap()
    }

/**
 * Resolves the [AnimatedVisibility] enter transition for the given
 * animation style.
 */
@Composable
private fun resolveEnter(
    animation: AccordionAnimation,
    motion: RikkaMotion,
): androidx.compose.animation.EnterTransition =
    when (animation) {
        AccordionAnimation.Spring -> {
            expandVertically(
                animationSpec =
                    spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMediumLow,
                    ),
            )
        }

        AccordionAnimation.Tween -> {
            expandVertically(
                animationSpec =
                    tween(
                        durationMillis = motion.durationDefault,
                    ),
            )
        }

        AccordionAnimation.None -> {
            expandVertically(
                animationSpec = snap(),
            )
        }
    }

/**
 * Resolves the [AnimatedVisibility] exit transition for the given
 * animation style.
 */
@Composable
private fun resolveExit(
    animation: AccordionAnimation,
    motion: RikkaMotion,
): androidx.compose.animation.ExitTransition =
    when (animation) {
        AccordionAnimation.Spring -> {
            shrinkVertically(
                animationSpec =
                    spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMediumLow,
                    ),
            )
        }

        AccordionAnimation.Tween -> {
            shrinkVertically(
                animationSpec =
                    tween(
                        durationMillis = motion.durationDefault,
                    ),
            )
        }

        AccordionAnimation.None -> {
            shrinkVertically(
                animationSpec = snap(),
            )
        }
    }
