package zed.rainxch.rikkaui.components.ui.tabs

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

// ─── Animation ────────────────────────────────────────────

/**
 * Animation strategy for [Tab] selection transitions.
 *
 * Controls how background and text colors animate when switching
 * between selected and unselected states.
 *
 * - [Spring] — Spring-based color transition using theme motion tokens.
 *   Handles interruptions gracefully. **(default)**
 * - [Tween] — Smooth eased transition with a fixed duration from
 *   [RikkaTheme.motion.durationDefault].
 * - [None] — Instant selection change with no animation.
 *
 * ```
 * Tab(
 *     selected = index == 0,
 *     onClick = { index = 0 },
 *     text = "Account",
 *     animation = TabAnimation.Tween,
 * )
 * ```
 */
enum class TabAnimation {
    /** Spring-based color transition (default). */
    Spring,

    /** Smooth eased tween transition. */
    Tween,

    /** Instant change with no animation. */
    None,
}

// ─── TabList ───────────────────────────────────────────────

/**
 * Horizontal container for [Tab] triggers.
 *
 * Renders a muted-background pill that holds tab items side by side,
 * matching shadcn/ui's TabsList.
 *
 * Usage:
 * ```
 * TabList {
 *     Tab(selected = index == 0, onClick = { index = 0 }, text = "Account")
 *     Tab(selected = index == 1, onClick = { index = 1 }, text = "Password")
 * }
 * ```
 *
 * @param modifier Modifier for layout and decoration.
 * @param content Tab triggers — typically [Tab] composables.
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
 * Individual tab trigger inside a [TabList].
 *
 * Animates between selected and unselected states with smooth color
 * transitions driven by theme motion tokens. The animation strategy
 * is configurable via the [animation] parameter.
 *
 * Usage:
 * ```
 * var selectedIndex by remember { mutableStateOf(0) }
 *
 * TabList {
 *     Tab(
 *         selected = selectedIndex == 0,
 *         onClick = { selectedIndex = 0 },
 *         text = "Overview",
 *     )
 *     Tab(
 *         selected = selectedIndex == 1,
 *         onClick = { selectedIndex = 1 },
 *         text = "Analytics",
 *         animation = TabAnimation.Tween,
 *     )
 * }
 * ```
 *
 * @param selected Whether this tab is currently active.
 * @param onClick Called when the tab is clicked.
 * @param text Label text displayed in the tab.
 * @param modifier Modifier for layout and decoration.
 * @param animation Animation strategy for selection transitions.
 *   Defaults to [TabAnimation.Spring].
 * @param activeColor Override for the selected text color.
 *   Defaults to [RikkaTheme.colors.foreground].
 * @param inactiveColor Override for the unselected text color.
 *   Defaults to [RikkaTheme.colors.mutedForeground].
 * @param activeBackground Override for the selected background color.
 *   Defaults to [RikkaTheme.colors.background].
 * @param inactiveBackground Override for the unselected background color.
 *   Defaults to [RikkaTheme.colors.muted].
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
    val colors = RikkaTheme.colors
    val motion = RikkaTheme.motion
    val shape = RikkaTheme.shapes.sm

    // ─── Resolve color targets ────────────────────────────
    val resolvedActiveColor =
        if (activeColor != Color.Unspecified) activeColor else colors.foreground
    val resolvedInactiveColor =
        if (inactiveColor != Color.Unspecified) {
            inactiveColor
        } else {
            colors.mutedForeground
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
    val colorAnimSpec: AnimationSpec<Color> =
        resolveColorAnimSpec(animation, motion.durationDefault)
    val dpAnimSpec: AnimationSpec<Dp> =
        resolveDpAnimSpec(animation, motion.durationDefault)

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
                .shadow(shadowElevation, shape)
                .background(backgroundColor, shape)
                .clip(shape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    role = Role.Tab,
                    onClick = onClick,
                ).padding(
                    horizontal = RikkaTheme.spacing.md,
                    vertical = RikkaTheme.spacing.sm,
                ).semantics {
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
 * Content panel displayed below a [TabList].
 *
 * Adds top padding and optionally animates content transitions
 * when the [selectedIndex] changes. The content fades and slides
 * in from the bottom for a polished tab-switch experience.
 *
 * Usage:
 * ```
 * TabList { ... }
 *
 * TabContent(selectedIndex = selectedTab) {
 *     when (selectedTab) {
 *         0 -> Text("Account settings")
 *         1 -> Text("Password settings")
 *     }
 * }
 * ```
 *
 * @param selectedIndex The currently selected tab index, used as
 *   the animation key. Content animates when this value changes.
 * @param modifier Modifier for layout and decoration.
 * @param content The tab panel content.
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

/**
 * Resolves a [TabAnimation] to an [AnimationSpec] for color
 * transitions, using the theme's motion duration tokens.
 */
@Composable
private fun resolveColorAnimSpec(
    animation: TabAnimation,
    durationMs: Int,
): AnimationSpec<Color> =
    when (animation) {
        TabAnimation.Spring ->
            spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessMedium,
            )
        TabAnimation.Tween -> tween(durationMs)
        TabAnimation.None -> snap()
    }

@Composable
private fun resolveDpAnimSpec(
    animation: TabAnimation,
    durationMs: Int,
): AnimationSpec<Dp> =
    when (animation) {
        TabAnimation.Spring ->
            spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessMedium,
            )
        TabAnimation.Tween -> tween(durationMs)
        TabAnimation.None -> snap()
    }
