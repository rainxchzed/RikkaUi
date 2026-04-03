package zed.rainxch.rikkaui.components.ui.navigationbar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Animation ────────────────────────────────────────────────

enum class NavigationBarAnimation {
    /** Spring-based transitions (default). */
    Spring,

    /** Smooth eased tween transitions. */
    Tween,

    /** Instant, no animation. */
    None,
}

// ─── NavigationBar ────────────────────────────────────────────

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = RikkaTheme.colors.background,
    content: @Composable RowScope.() -> Unit,
) {
    val borderColor = RikkaTheme.colors.border
    val dividerHeight = 1.dp

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .drawBehind {
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = dividerHeight.toPx(),
                    )
                }.background(containerColor),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(
                        horizontal = RikkaTheme.spacing.sm,
                    ).semantics { isTraversalGroup = true },
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            content()
        }
    }
}

// ─── NavigationBarItem (content lambda) ───────────────────────

@Composable
fun RowScope.NavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    selectedIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true,
    animation: NavigationBarAnimation = NavigationBarAnimation.Spring,
    indicatorColor: Color = Color.Unspecified,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()
    val colors = RikkaTheme.colors
    val motion = RikkaTheme.motion

    // ─── Resolve animation specs ──────────────────────────
    val floatAnimSpec: AnimationSpec<Float> = resolveAnimSpec(animation, motion)
    val dpAnimSpec: AnimationSpec<Dp> = resolveAnimSpec(animation, motion)
    val fastFloatSpec: AnimationSpec<Float> =
        if (animation == NavigationBarAnimation.Tween) {
            motion.effectsFast()
        } else {
            floatAnimSpec
        }

    val resolvedIndicator =
        if (indicatorColor != Color.Unspecified) {
            indicatorColor
        } else {
            colors.secondary
        }

    // ─── Indicator animation ─────────────────────────────
    val indicatorWidth by animateDpAsState(
        targetValue = if (selected) 48.dp else 0.dp,
        animationSpec = dpAnimSpec,
    )
    val indicatorAlpha by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec = floatAnimSpec,
    )

    // ─── Label fade animation ────────────────────────────
    val showLabel = alwaysShowLabel || selected
    val labelAlpha by animateFloatAsState(
        targetValue = if (showLabel && label != null) 1f else 0f,
        animationSpec = floatAnimSpec,
    )
    val labelOffset by animateDpAsState(
        targetValue = if (showLabel && label != null) 0.dp else 4.dp,
        animationSpec = dpAnimSpec,
    )

    // ─── Press scale ─────────────────────────────────────
    val pressScale by animateFloatAsState(
        targetValue =
            if (isPressed && enabled) {
                motion.pressScaleSubtle
            } else {
                1f
            },
        animationSpec = floatAnimSpec,
    )

    // ─── Hover background ────────────────────────────────
    val hoverAlpha by animateFloatAsState(
        targetValue =
            if (isHovered && enabled && !selected) {
                0.5f
            } else {
                0f
            },
        animationSpec = fastFloatSpec,
    )

    Box(
        modifier =
            modifier
                .weight(1f)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    role = Role.Tab,
                    enabled = enabled,
                    onClick = onClick,
                ).semantics(mergeDescendants = true) {
                    this.selected = selected
                    if (!enabled) disabled()
                }.graphicsLayer {
                    scaleX = pressScale
                    scaleY = pressScale
                },
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // ─── Icon with indicator ─────────────────
            Box(
                contentAlignment = Alignment.Center,
            ) {
                // Indicator pill behind icon
                Box(
                    modifier =
                        Modifier
                            .width(indicatorWidth)
                            .height(32.dp)
                            .graphicsLayer { alpha = indicatorAlpha }
                            .background(
                                color = resolvedIndicator,
                                shape = RikkaTheme.shapes.full,
                            ),
                )

                // Hover highlight
                if (hoverAlpha > 0f) {
                    Box(
                        modifier =
                            Modifier
                                .width(48.dp)
                                .height(32.dp)
                                .graphicsLayer { alpha = hoverAlpha }
                                .background(
                                    color = colors.muted,
                                    shape = RikkaTheme.shapes.full,
                                ),
                    )
                }

                // Icon
                Box(
                    modifier = Modifier.size(24.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    if (selected && selectedIcon != null) {
                        selectedIcon()
                    } else {
                        icon()
                    }
                }
            }

            // ─── Label ───────────────────────────────
            if (label != null) {
                Box(
                    modifier =
                        Modifier
                            .padding(top = RikkaTheme.spacing.xs)
                            .offset { IntOffset(x = 0, y = labelOffset.roundToPx()) }
                            .graphicsLayer { alpha = labelAlpha },
                ) {
                    label()
                }
            }
        }
    }
}

// ─── NavigationBarItem (convenience overload) ─────────────────

@Composable
fun RowScope.NavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier,
    selectedIcon: ImageVector? = null,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true,
    animation: NavigationBarAnimation = NavigationBarAnimation.Spring,
    indicatorColor: Color = Color.Unspecified,
    activeColor: Color = Color.Unspecified,
    inactiveColor: Color = Color.Unspecified,
) {
    val colors = RikkaTheme.colors
    val motion = RikkaTheme.motion

    // ─── Resolve color targets ────────────────────────────
    val resolvedActive =
        if (activeColor != Color.Unspecified) {
            activeColor
        } else {
            colors.primary
        }
    val resolvedInactive =
        if (inactiveColor != Color.Unspecified) {
            inactiveColor
        } else {
            colors.onMuted
        }

    // ─── Resolve animation spec for colors ────────────────
    val colorAnimSpec: AnimationSpec<Color> = resolveAnimSpec(animation, motion)

    // ─── Animated icon + label colors ────────────────────
    val iconColor by animateColorAsState(
        targetValue = if (selected) resolvedActive else resolvedInactive,
        animationSpec = colorAnimSpec,
    )
    val labelColor by animateColorAsState(
        targetValue = if (selected) resolvedActive else resolvedInactive,
        animationSpec = colorAnimSpec,
    )

    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
            )
        },
        modifier =
            modifier.semantics {
                contentDescription = label
            },
        label = {
            Text(
                text = label,
                variant = TextVariant.Small,
                color = labelColor,
            )
        },
        selectedIcon =
            if (selectedIcon != null) {
                {
                    Icon(
                        imageVector = selectedIcon,
                        contentDescription = null,
                        tint = iconColor,
                    )
                }
            } else {
                {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                    )
                }
            },
        enabled = enabled,
        alwaysShowLabel = alwaysShowLabel,
        animation = animation,
        indicatorColor = indicatorColor,
    )
}

// ─── Internal: Animation Spec Resolution ──────────────────────

@Composable
private fun <T> resolveAnimSpec(
    animation: NavigationBarAnimation,
    motion: zed.rainxch.rikkaui.foundation.RikkaMotion,
): AnimationSpec<T> =
    when (animation) {
        NavigationBarAnimation.Spring -> motion.spatialDefault()
        NavigationBarAnimation.Tween -> motion.effectsDefault()
        NavigationBarAnimation.None -> snap()
    }
