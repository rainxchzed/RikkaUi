package zed.rainxch.rikkaui.components.ui.tabs

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme
import zed.rainxch.rikkaui.foundation.modifier.minTouchTarget

// ─── Animation ────────────────────────────────────────────

enum class TabAnimation {
    /** Spring-based color transition (default). */
    Spring,

    /** Smooth eased tween transition. */
    Tween,

    /** Instant, no animation. */
    None,
}

// ─── TabList ───────────────────────────────────────────────

/**
 * Horizontal container for [Tab] composables, rendered as a muted pill-shaped row.
 *
 * Provides traversal group semantics so screen readers navigate tabs as a unit.
 *
 * ```
 * TabList {
 *     Tab(selected = index == 0, onClick = { index = 0 }, text = "Overview")
 *     Tab(selected = index == 1, onClick = { index = 1 }, text = "Settings")
 * }
 * ```
 *
 * @param modifier [Modifier] applied to the tab list row.
 * @param content Composable content containing [Tab] children.
 */
@Composable
fun TabList(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val shape = RikkaTheme.shapes.md

    Row(
        modifier =
            modifier
                .semantics { isTraversalGroup = true }
                .background(RikkaTheme.colors.muted, shape)
                .clip(shape)
                .padding(RikkaTheme.spacing.xs),
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.xs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        content()
    }
}

// ─── Tab ───────────────────────────────────────────────────

/**
 * Individual tab button within a [TabList], with animated selected/unselected state transitions.
 *
 * The active tab receives a subtle elevation shadow and distinct background/text colors.
 * Hover state reduces opacity for unselected tabs. Accessibility semantics include
 * Tab role and selected state.
 *
 * ```
 * Tab(
 *     selected = currentIndex == 0,
 *     onClick = { currentIndex = 0 },
 *     text = "Overview",
 *     animation = TabAnimation.Tween,
 * )
 * ```
 *
 * @param selected Whether this tab is currently active.
 * @param onClick Callback invoked when the tab is clicked.
 * @param text The label string displayed inside the tab.
 * @param modifier [Modifier] applied to the tab container.
 * @param animation [TabAnimation] controlling color and elevation transitions. Defaults to [TabAnimation.Spring].
 * @param activeColor Text color when selected. [Color.Unspecified] defaults to theme onBackground.
 * @param inactiveColor Text color when unselected. [Color.Unspecified] defaults to theme onMuted.
 * @param activeBackground Background color when selected. [Color.Unspecified] defaults to theme background.
 * @param inactiveBackground Background color when unselected. [Color.Unspecified] defaults to theme muted.
 */
@Composable
fun Tab(
    selected: Boolean,
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    animation: TabAnimation = TabAnimation.Spring,
    activeColor: Color = Color.Unspecified,
    inactiveColor: Color = Color.Unspecified,
    activeBackground: Color = Color.Unspecified,
    inactiveBackground: Color = Color.Unspecified,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val colors = RikkaTheme.colors
    val motion = RikkaTheme.motion
    val shape = RikkaTheme.shapes.sm

    // ─── Resolve color targets ────────────────────────────
    val resolvedActiveColor =
        if (activeColor != Color.Unspecified) activeColor else colors.onBackground
    val resolvedInactiveColor =
        if (inactiveColor != Color.Unspecified) {
            inactiveColor
        } else {
            colors.onMuted
        }
    val resolvedActiveBg =
        if (activeBackground != Color.Unspecified) {
            activeBackground
        } else {
            colors.background
        }
    val resolvedInactiveBg =
        if (inactiveBackground != Color.Unspecified) {
            inactiveBackground
        } else {
            colors.muted
        }

    // ─── Resolve animation spec ───────────────────────────
    val colorAnimSpec: AnimationSpec<Color> = resolveAnimSpec(animation, motion)
    val dpAnimSpec: AnimationSpec<Dp> = resolveAnimSpec(animation, motion)

    // ─── Animated colors ──────────────────────────────────
    val backgroundColor by animateColorAsState(
        targetValue =
            if (selected) resolvedActiveBg else resolvedInactiveBg,
        animationSpec = colorAnimSpec,
    )

    val textColor by animateColorAsState(
        targetValue =
            if (selected) resolvedActiveColor else resolvedInactiveColor,
        animationSpec = colorAnimSpec,
    )

    val shadowElevation by animateDpAsState(
        targetValue = if (selected) 1.dp else 0.dp,
        animationSpec = dpAnimSpec,
    )

    Box(
        modifier =
            modifier
                .minTouchTarget()
                .shadow(shadowElevation, shape)
                .background(backgroundColor, shape)
                .clip(shape)
                .graphicsLayer {
                    alpha = if (isHovered && !selected) motion.hoverAlpha else 1f
                }.clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    role = Role.Tab,
                    onClick = onClick,
                ).padding(
                    horizontal = RikkaTheme.spacing.md,
                    vertical = RikkaTheme.spacing.sm,
                ).semantics {
                    this.selected = selected
                    contentDescription = text
                },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            variant = TextVariant.Small,
            color = textColor,
        )
    }
}

// ─── TabContent ────────────────────────────────────────────

/**
 * Animated content area that transitions between tab panels based on [selectedIndex].
 *
 * Uses [AnimatedContent] with fade and vertical slide transitions driven by theme motion tokens.
 * Place this below a [TabList] to display the content for the currently selected tab.
 *
 * ```
 * TabContent(selectedIndex = currentIndex) {
 *     when (currentIndex) {
 *         0 -> Text("Overview panel")
 *         1 -> Text("Settings panel")
 *     }
 * }
 * ```
 *
 * @param selectedIndex Zero-based index of the currently active tab panel. Defaults to 0.
 * @param modifier [Modifier] applied to the content column.
 * @param content Composable content that should render based on the current [selectedIndex].
 */
@Composable
fun TabContent(
    selectedIndex: Int = 0,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val motion = RikkaTheme.motion

    Column(
        modifier =
            modifier
                .padding(top = RikkaTheme.spacing.md),
    ) {
        AnimatedContent(
            targetState = selectedIndex,
            transitionSpec = {
                (
                    fadeIn(tween(motion.durationDefault)) +
                        slideInVertically(tween(motion.durationDefault)) { it / 8 }
                ).togetherWith(
                    fadeOut(tween(motion.durationFast)) +
                        slideOutVertically(tween(motion.durationFast)) { -it / 8 },
                )
            },
        ) {
            content()
        }
    }
}

// ─── Internal: Animation Spec Resolution ──────────────────

@Composable
private fun <T> resolveAnimSpec(
    animation: TabAnimation,
    motion: zed.rainxch.rikkaui.foundation.RikkaMotion,
): AnimationSpec<T> =
    when (animation) {
        TabAnimation.Spring -> motion.spatialSnap()
        TabAnimation.Tween -> motion.effectsDefault()
        TabAnimation.None -> snap()
    }
