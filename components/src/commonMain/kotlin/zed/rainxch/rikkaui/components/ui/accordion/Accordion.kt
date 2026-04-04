package zed.rainxch.rikkaui.components.ui.accordion

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaMotion
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Animation Enum ─────────────────────────────────────────

enum class AccordionAnimation {
    /** Spring-physics expand/collapse with medium bounce (default). */
    Spring,

    /** Duration-based eased expand/collapse. */
    Tween,

    /** Instant expand/collapse with no animation. */
    None,
}

// ─── Component ──────────────────────────────────────────────

/**
 * Expandable accordion section with an animated chevron indicator and collapsible content area.
 *
 * The title row is clickable and toggles the [expanded] state. A rotating chevron icon
 * indicates the current expansion state (right when collapsed, down when expanded).
 * The title underlines on hover for visual feedback. Screen readers announce the
 * expanded/collapsed state via [stateDescription].
 *
 * ```
 * var expanded by remember { mutableStateOf(false) }
 * AccordionItem(
 *     title = "Section Title",
 *     expanded = expanded,
 *     onExpandedChange = { expanded = it },
 * ) {
 *     Text("Hidden content revealed when expanded.")
 * }
 * ```
 *
 * @param title The heading text displayed in the clickable title row.
 * @param expanded Whether the content area is currently visible.
 * @param onExpandedChange Callback invoked with the new expanded state when the title is clicked.
 * @param modifier [Modifier] applied to the root column.
 * @param animation [AccordionAnimation] controlling expand/collapse transitions. Defaults to [AccordionAnimation.Spring].
 * @param chevronIcon [ImageVector] used as the expansion indicator. Defaults to [RikkaIcons.ChevronRight].
 * @param content Composable content displayed when expanded.
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
    val isHovered by interactionSource.collectIsHoveredAsState()

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
                style =
                    TextStyle(
                        fontWeight = FontWeight.Medium,
                        textDecoration =
                            if (isHovered) {
                                TextDecoration.Underline
                            } else {
                                TextDecoration.None
                            },
                    ),
                modifier = Modifier.weight(1f),
            )

            // Chevron indicator — rotates 90 degrees when expanded.
            Icon(
                imageVector = chevronIcon,
                contentDescription = null,
                tint = colors.onMuted,
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
                        dampingRatio = motion.spatialDampingDefault,
                        stiffness = motion.spatialStiffnessDefault,
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
                        dampingRatio = motion.spatialDampingDefault,
                        stiffness = motion.spatialStiffnessDefault,
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
