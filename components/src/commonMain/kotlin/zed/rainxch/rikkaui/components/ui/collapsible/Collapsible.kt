package zed.rainxch.rikkaui.components.ui.collapsible

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import zed.rainxch.rikkaui.foundation.RikkaMotion
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Animation Enum ─────────────────────────────────────────

/**
 * Controls the expand/collapse animation style for
 * [CollapsibleContent].
 *
 * Each option reads duration and spring parameters from
 * [RikkaTheme.motion] tokens so the animation stays consistent
 * with the rest of the design system.
 *
 * ```
 * CollapsibleContent(
 *     open = isOpen,
 *     animation = CollapsibleAnimation.Tween,
 * ) {
 *     Text("Smooth eased transition")
 * }
 * ```
 */
enum class CollapsibleAnimation {
    /**
     * Spring-physics expand/collapse with medium bounce and a
     * fade overlay. Handles interruptions gracefully and feels
     * organic. This is the default.
     */
    Spring,

    /**
     * Duration-based eased expand/collapse with fade using
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

// ─── Scope ─────────────────────────────────────────────────

/**
 * Receiver scope for the [Collapsible] DSL overload.
 *
 * Provides [trigger] and [content] builder functions so callers
 * can declare the trigger and expandable body inline:
 *
 * ```
 * Collapsible {
 *     trigger { Text("Click me") }
 *     content { Text("Hidden content") }
 * }
 * ```
 */
class CollapsibleScope internal constructor(
    internal val open: Boolean,
    internal val onOpenChange: (Boolean) -> Unit,
) {
    internal var triggerContent: (@Composable () -> Unit)? = null
        private set
    internal var bodyContent: (@Composable () -> Unit)? = null
        private set

    /**
     * Declares the clickable trigger area of the collapsible.
     *
     * @param content Composable content rendered inside the trigger.
     */
    fun trigger(content: @Composable () -> Unit) {
        triggerContent = content
    }

    /**
     * Declares the expandable body area of the collapsible.
     *
     * @param content Composable content shown/hidden on toggle.
     */
    fun content(content: @Composable () -> Unit) {
        bodyContent = content
    }
}

// ─── Components ────────────────────────────────────────────

/**
 * A simple expand/collapse container for the RikkaUi design system.
 *
 * Simpler than [AccordionItem][zed.rainxch.rikkaui.components.ui.accordion.AccordionItem]:
 * single item, no group behavior, no built-in chrome — the caller
 * provides all visuals via [CollapsibleTrigger] and [CollapsibleContent].
 *
 * Inspired by shadcn/ui's Collapsible primitive.
 *
 * ### Controlled usage
 *
 * ```
 * var open by remember { mutableStateOf(false) }
 *
 * Collapsible(
 *     open = open,
 *     onOpenChange = { open = it },
 * ) {
 *     CollapsibleTrigger(
 *         onClick = { open = !open },
 *     ) {
 *         Row(verticalAlignment = Alignment.CenterVertically) {
 *             Text("Starred repositories")
 *             Icon(
 *                 imageVector = RikkaIcons.ChevronDown,
 *                 contentDescription = null,
 *             )
 *         }
 *     }
 *
 *     CollapsibleContent(open = open) {
 *         Column {
 *             Text("@rikka/components")
 *             Text("@rikka/theme")
 *         }
 *     }
 * }
 * ```
 *
 * ### Inside a Card
 *
 * ```
 * Card {
 *     CardHeader {
 *         var open by remember { mutableStateOf(false) }
 *         Collapsible(
 *             open = open,
 *             onOpenChange = { open = it },
 *         ) {
 *             CollapsibleTrigger(onClick = { open = !open }) {
 *                 Text("Details")
 *             }
 *             CollapsibleContent(open = open) {
 *                 Text("Additional information here.")
 *             }
 *         }
 *     }
 * }
 * ```
 *
 * @param open Whether the content section is currently expanded.
 * @param onOpenChange Called when the expanded state should change.
 * @param modifier Modifier applied to the outer container.
 * @param content Builder lambda — typically contains a
 *   [CollapsibleTrigger] and a [CollapsibleContent].
 */
@Composable
fun Collapsible(
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
 * Convenience DSL overload with internally-managed expanded state.
 *
 * Uses a [CollapsibleScope] receiver so the caller can declare
 * [trigger][CollapsibleScope.trigger] and
 * [content][CollapsibleScope.content] blocks inline:
 *
 * ```
 * Collapsible {
 *     trigger {
 *         Row {
 *             Text("Toggle section")
 *             Icon(RikkaIcons.ChevronDown, null)
 *         }
 *     }
 *     content {
 *         Text("Expandable body")
 *     }
 * }
 * ```
 *
 * @param modifier Modifier applied to the outer container.
 * @param initialOpen Initial expanded state. Defaults to `false`.
 * @param animation The expand/collapse animation style passed to
 *   [CollapsibleContent]. Defaults to [CollapsibleAnimation.Spring].
 * @param builder DSL builder providing [CollapsibleScope.trigger]
 *   and [CollapsibleScope.content].
 */
@Composable
fun Collapsible(
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
 * Clickable trigger area for a [Collapsible].
 *
 * Renders as a clickable [Box] with [Role.Button] semantics and
 * hover/press interaction tracking via [MutableInteractionSource].
 * No background, border, or padding is applied by default — the
 * caller owns all visual styling.
 *
 * ```
 * CollapsibleTrigger(
 *     onClick = { open = !open },
 *     expanded = open,
 *     label = "Toggle starred repos",
 * ) {
 *     Row(
 *         modifier = Modifier
 *             .fillMaxWidth()
 *             .padding(vertical = RikkaTheme.spacing.md),
 *         verticalAlignment = Alignment.CenterVertically,
 *     ) {
 *         Text("Starred repos", modifier = Modifier.weight(1f))
 *         Icon(RikkaIcons.ChevronDown, contentDescription = null)
 *     }
 * }
 * ```
 *
 * @param onClick Called when the trigger is clicked.
 * @param modifier Modifier applied to the trigger container.
 * @param label Accessibility content description. Defaults to
 *   `"Toggle section"`.
 * @param expanded Current expanded state for accessibility
 *   `stateDescription`. Pass the parent [Collapsible]'s `open`
 *   value so screen readers announce "Expanded" / "Collapsed".
 * @param content Composable content rendered inside the trigger.
 */
@Composable
fun CollapsibleTrigger(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Toggle section",
    expanded: Boolean? = null,
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier =
            modifier
                .clickable(
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
 * Expandable content area for a [Collapsible].
 *
 * Wraps content in [AnimatedVisibility] with vertical expand/shrink
 * and fade transitions. The animation style is controlled by the
 * [animation] parameter which reads tokens from [RikkaTheme.motion].
 *
 * ```
 * // Spring (default — bouncy, organic)
 * CollapsibleContent(open = isOpen) {
 *     Text("Spring-animated content")
 * }
 *
 * // Tween (smooth, predictable)
 * CollapsibleContent(
 *     open = isOpen,
 *     animation = CollapsibleAnimation.Tween,
 * ) {
 *     Text("Eased content")
 * }
 *
 * // Instant (no animation)
 * CollapsibleContent(
 *     open = isOpen,
 *     animation = CollapsibleAnimation.None,
 * ) {
 *     Text("Instant content")
 * }
 * ```
 *
 * @param open Whether the content is currently visible.
 * @param modifier Modifier applied to the inner content wrapper.
 * @param animation The expand/collapse animation style. Defaults to
 *   [CollapsibleAnimation.Spring].
 * @param content Composable content to show/hide.
 */
@Composable
fun CollapsibleContent(
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

/**
 * Resolves the [AnimatedVisibility] enter transition for the given
 * [CollapsibleAnimation] style, reading tokens from [RikkaMotion].
 */
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
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMediumLow,
                    ),
                expandFrom = Alignment.Top,
            ) +
                fadeIn(
                    animationSpec =
                        tween(
                            durationMillis = motion.durationDefault,
                        ),
                )
        }

        CollapsibleAnimation.Tween -> {
            expandVertically(
                animationSpec =
                    tween(
                        durationMillis = motion.durationSlow,
                    ),
                expandFrom = Alignment.Top,
            ) +
                fadeIn(
                    animationSpec =
                        tween(
                            durationMillis = motion.durationDefault,
                        ),
                )
        }

        CollapsibleAnimation.None -> {
            expandVertically(
                animationSpec = snap(),
                expandFrom = Alignment.Top,
            )
        }
    }

/**
 * Resolves the [AnimatedVisibility] exit transition for the given
 * [CollapsibleAnimation] style, reading tokens from [RikkaMotion].
 */
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
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMediumLow,
                    ),
                shrinkTowards = Alignment.Top,
            ) +
                fadeOut(
                    animationSpec =
                        tween(
                            durationMillis = motion.durationFast,
                        ),
                )
        }

        CollapsibleAnimation.Tween -> {
            shrinkVertically(
                animationSpec =
                    tween(
                        durationMillis = motion.durationSlow,
                    ),
                shrinkTowards = Alignment.Top,
            ) +
                fadeOut(
                    animationSpec =
                        tween(
                            durationMillis = motion.durationFast,
                        ),
                )
        }

        CollapsibleAnimation.None -> {
            shrinkVertically(
                animationSpec = snap(),
                shrinkTowards = Alignment.Top,
            )
        }
    }
