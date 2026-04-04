package zed.rainxch.rikkaui.components.ui.collapsible

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.semantics.stateDescription
import zed.rainxch.rikkaui.foundation.RikkaMotion
import zed.rainxch.rikkaui.foundation.RikkaTheme
import zed.rainxch.rikkaui.foundation.modifier.minTouchTarget

// ─── Animation Enum ─────────────────────────────────────────

public enum class CollapsibleAnimation {
    /** Spring-physics expand/collapse with bounce and fade. Default. */
    Spring,

    /** Duration-based eased expand/collapse with fade. */
    Tween,

    /** Instant expand/collapse with no animation. */
    None,
}

// ─── Scope ─────────────────────────────────────────────────

public class CollapsibleScope internal constructor(
    internal val open: Boolean,
    internal val onOpenChange: (Boolean) -> Unit,
) {
    internal var triggerContent: (@Composable () -> Unit)? = null
        private set
    internal var bodyContent: (@Composable () -> Unit)? = null
        private set

    public fun trigger(content: @Composable () -> Unit) {
        triggerContent = content
    }

    public fun content(content: @Composable () -> Unit) {
        bodyContent = content
    }
}

// ─── Components ────────────────────────────────────────────

/**
 * Controlled collapsible container that wraps a trigger and expandable content in a column.
 *
 * This is the low-level overload where the caller manages the [open] state externally.
 * Compose the content with [CollapsibleTrigger] and [CollapsibleContent] children.
 *
 * ```
 * var open by remember { mutableStateOf(false) }
 * Collapsible(open = open, onOpenChange = { open = it }) {
 *     CollapsibleTrigger(onClick = { open = !open }) { Text("Toggle") }
 *     CollapsibleContent(open = open) { Text("Revealed content") }
 * }
 * ```
 *
 * @param open Whether the collapsible content is currently visible.
 * @param onOpenChange Callback invoked with the new open state.
 * @param modifier [Modifier] applied to the root column.
 * @param content Composable content containing [CollapsibleTrigger] and [CollapsibleContent].
 */
@Composable
public fun Collapsible(
    open: Boolean,
    onOpenChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(modifier = modifier) {
        content()
    }
}

/**
 * Self-managed collapsible container using a [CollapsibleScope] DSL builder.
 *
 * Manages its own open/closed state internally. Use the [builder] DSL to define
 * [CollapsibleScope.trigger] and [CollapsibleScope.content] blocks.
 *
 * ```
 * Collapsible(initialOpen = true, animation = CollapsibleAnimation.Tween) {
 *     trigger { Text("Click to toggle") }
 *     content { Text("Expandable content here") }
 * }
 * ```
 *
 * @param modifier [Modifier] applied to the root column.
 * @param initialOpen Whether the content starts expanded. Defaults to false.
 * @param animation [CollapsibleAnimation] controlling expand/collapse transitions. Defaults to [CollapsibleAnimation.Spring].
 * @param builder [CollapsibleScope] DSL builder for defining trigger and content blocks.
 */
@Composable
public fun Collapsible(
    modifier: Modifier = Modifier,
    initialOpen: Boolean = false,
    animation: CollapsibleAnimation = CollapsibleAnimation.Spring,
    builder: CollapsibleScope.() -> Unit,
) {
    var open by remember { mutableStateOf(initialOpen) }
    val scope =
        CollapsibleScope(
            open = open,
            onOpenChange = { open = it },
        ).apply(builder)

    Collapsible(
        open = open,
        onOpenChange = { open = it },
        modifier = modifier,
    ) {
        scope.triggerContent?.let { trigger ->
            CollapsibleTrigger(
                onClick = { open = !open },
                expanded = open,
            ) {
                trigger()
            }
        }
        scope.bodyContent?.let { body ->
            CollapsibleContent(
                open = open,
                animation = animation,
            ) {
                body()
            }
        }
    }
}

/**
 * Clickable trigger element that toggles the collapsible section open or closed.
 *
 * Renders with hover opacity feedback and button role semantics.
 * When [expanded] is provided, screen readers announce the expanded/collapsed state.
 *
 * ```
 * CollapsibleTrigger(onClick = { open = !open }, expanded = open) {
 *     Row {
 *         Text("Details")
 *         Icon(RikkaIcons.ChevronDown, contentDescription = null)
 *     }
 * }
 * ```
 *
 * @param onClick Callback invoked when the trigger is clicked.
 * @param modifier [Modifier] applied to the trigger container.
 * @param label Accessibility content description for the trigger. Defaults to "Toggle section".
 * @param expanded Optional expanded state for accessibility announcements. Defaults to null (no state announced).
 * @param content Composable content rendered inside the trigger.
 */
@Composable
public fun CollapsibleTrigger(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Toggle section",
    expanded: Boolean? = null,
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val motion = RikkaTheme.motion

    Box(
        modifier =
            modifier
                .minTouchTarget()
                .graphicsLayer {
                    alpha = if (isHovered) motion.hoverAlpha else 1f
                }.clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    role = Role.Button,
                    onClick = onClick,
                ).semantics(mergeDescendants = true) {
                    contentDescription = label
                    if (expanded != null) {
                        stateDescription =
                            if (expanded) "Expanded" else "Collapsed"
                    }
                },
        contentAlignment = Alignment.CenterStart,
    ) {
        content()
    }
}

/**
 * Animated expandable content area within a [Collapsible].
 *
 * Uses [AnimatedVisibility] with vertical expand/shrink and fade transitions
 * driven by the selected [animation] strategy and theme motion tokens.
 *
 * ```
 * CollapsibleContent(open = isOpen, animation = CollapsibleAnimation.Tween) {
 *     Text("This content expands and collapses with animation.")
 * }
 * ```
 *
 * @param open Whether the content is currently visible.
 * @param modifier [Modifier] applied to the inner content box.
 * @param animation [CollapsibleAnimation] controlling the transition style. Defaults to [CollapsibleAnimation.Spring].
 * @param content Composable content rendered when [open] is true.
 */
@Composable
public fun CollapsibleContent(
    open: Boolean,
    modifier: Modifier = Modifier,
    animation: CollapsibleAnimation = CollapsibleAnimation.Spring,
    content: @Composable () -> Unit,
) {
    val motion = RikkaTheme.motion

    AnimatedVisibility(
        visible = open,
        enter = resolveEnter(animation, motion),
        exit = resolveExit(animation, motion),
    ) {
        Box(modifier = modifier.fillMaxWidth()) {
            content()
        }
    }
}

// ─── Private animation resolution ───────────────────────────

@Composable
private fun resolveEnter(
    animation: CollapsibleAnimation,
    motion: RikkaMotion,
): androidx.compose.animation.EnterTransition =
    when (animation) {
        CollapsibleAnimation.Spring -> {
            expandVertically(
                animationSpec =
                    spring(
                        dampingRatio = motion.spatialDampingDefault,
                        stiffness = motion.spatialStiffnessDefault,
                    ),
                expandFrom = Alignment.Top,
            ) +
                fadeIn(
                    animationSpec = tween(motion.durationDefault),
                )
        }

        CollapsibleAnimation.Tween -> {
            expandVertically(
                animationSpec = tween(motion.durationSlow),
                expandFrom = Alignment.Top,
            ) +
                fadeIn(
                    animationSpec = tween(motion.durationDefault),
                )
        }

        CollapsibleAnimation.None -> {
            expandVertically(
                animationSpec = snap(),
                expandFrom = Alignment.Top,
            )
        }
    }

@Composable
private fun resolveExit(
    animation: CollapsibleAnimation,
    motion: RikkaMotion,
): androidx.compose.animation.ExitTransition =
    when (animation) {
        CollapsibleAnimation.Spring -> {
            shrinkVertically(
                animationSpec =
                    spring(
                        dampingRatio = motion.spatialDampingDefault,
                        stiffness = motion.spatialStiffnessDefault,
                    ),
                shrinkTowards = Alignment.Top,
            ) +
                fadeOut(
                    animationSpec = tween(motion.durationFast),
                )
        }

        CollapsibleAnimation.Tween -> {
            shrinkVertically(
                animationSpec = tween(motion.durationSlow),
                shrinkTowards = Alignment.Top,
            ) +
                fadeOut(
                    animationSpec = tween(motion.durationFast),
                )
        }

        CollapsibleAnimation.None -> {
            shrinkVertically(
                animationSpec = snap(),
                shrinkTowards = Alignment.Top,
            )
        }
    }
