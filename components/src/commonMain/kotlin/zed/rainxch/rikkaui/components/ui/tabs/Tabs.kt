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
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme

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
