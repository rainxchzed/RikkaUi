package zed.rainxch.rikkaui.components.ui.navigationbar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

// ─── NavigationBar ────────────────────────────────────────────

/**
 * Bottom navigation bar for app-level navigation.
 *
 * Displays a horizontal row of [NavigationBarItem] composables
 * along the bottom of the screen. Each item represents a top-level
 * destination. Inspired by shadcn/ui Navigation Menu and M3
 * NavigationBar, styled entirely with RikkaUI theme tokens.
 *
 * Built on `compose.foundation` only — no Material3 dependency.
 *
 * Usage:
 * ```
 * var selectedIndex by remember { mutableStateOf(0) }
 *
 * NavigationBar {
 *     NavigationBarItem(
 *         selected = selectedIndex == 0,
 *         onClick = { selectedIndex = 0 },
 *         icon = { Icon(RikkaIcons.Home, contentDescription = null) },
 *         label = { Text("Home", variant = TextVariant.Small) },
 *     )
 *     NavigationBarItem(
 *         selected = selectedIndex == 1,
 *         onClick = { selectedIndex = 1 },
 *         icon = { Icon(RikkaIcons.Search, contentDescription = null) },
 *         label = { Text("Search", variant = TextVariant.Small) },
 *     )
 * }
 * ```
 *
 * @param modifier Modifier for layout and decoration.
 * @param containerColor Background color. Defaults to [RikkaTheme.colors.background].
 * @param content Row content — typically [NavigationBarItem] composables.
 */
@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = RikkaTheme.colors.background,
    content: @Composable RowScope.() -> Unit,
) {
    val borderColor = RikkaTheme.colors.border

    Box(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                // Top border line (1dp)
                drawLine(
                    color = borderColor,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx(),
                )
            }
            .background(containerColor),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(
                    horizontal = RikkaTheme.spacing.sm,
                ),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            content()
        }
    }
}

// ─── NavigationBarItem (content lambda) ───────────────────────

/**
 * Individual item inside a [NavigationBar].
 *
 * Displays an icon with an optional label below it. When selected,
 * the icon receives a pill-shaped indicator behind it and both icon
 * and label animate to the [RikkaTheme.colors.primary] color.
 *
 * This is the generic overload that accepts composable lambdas for
 * icon, selectedIcon, and label. For a convenience overload that
 * accepts [ImageVector] and [String] directly, see the other
 * [NavigationBarItem] function.
 *
 * Usage:
 * ```
 * NavigationBar {
 *     NavigationBarItem(
 *         selected = currentRoute == "home",
 *         onClick = { navigate("home") },
 *         icon = { Icon(RikkaIcons.Home, contentDescription = null) },
 *         selectedIcon = { Icon(RikkaIcons.Home, contentDescription = null,
 *             tint = RikkaTheme.colors.primary) },
 *         label = { Text("Home", variant = TextVariant.Small) },
 *     )
 * }
 * ```
 *
 * @param selected Whether this item is currently active.
 * @param onClick Called when the item is tapped.
 * @param icon Icon composable shown when not selected (or always if
 *   [selectedIcon] is null).
 * @param modifier Modifier for layout and decoration.
 * @param label Optional label composable displayed below the icon.
 * @param selectedIcon Optional icon composable shown when selected.
 *   Falls back to [icon] when null.
 * @param enabled Whether the item is interactive.
 * @param alwaysShowLabel When `false`, the label fades out when
 *   the item is not selected.
 */
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
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()
    val colors = RikkaTheme.colors
    val motion = RikkaTheme.motion

    // ─── Indicator animation ─────────────────────────────
    val indicatorWidth by animateDpAsState(
        targetValue = if (selected) 48.dp else 0.dp,
        animationSpec = motion.springDefaultDp,
    )
    val indicatorAlpha by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec = motion.springDefault,
    )

    // ─── Label fade animation ────────────────────────────
    val showLabel = alwaysShowLabel || selected
    val labelAlpha by animateFloatAsState(
        targetValue = if (showLabel && label != null) 1f else 0f,
        animationSpec = tween(motion.durationDefault),
    )
    val labelOffset by animateDpAsState(
        targetValue = if (showLabel && label != null) 0.dp else 4.dp,
        animationSpec = motion.springDefaultDp,
    )

    // ─── Press scale ─────────────────────────────────────
    val pressScale by animateFloatAsState(
        targetValue = if (isPressed && enabled) {
            motion.pressScaleSubtle
        } else {
            1f
        },
        animationSpec = motion.springDefault,
    )

    // ─── Hover background ────────────────────────────────
    val hoverAlpha by animateFloatAsState(
        targetValue = if (isHovered && enabled && !selected) 0.5f else 0f,
        animationSpec = tween(motion.durationFast),
    )

    Box(
        modifier = modifier
            .weight(1f)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Tab,
                enabled = enabled,
                onClick = onClick,
            )
            .semantics {
                this.selected = selected
                if (!enabled) disabled()
            }
            .graphicsLayer {
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
                    modifier = Modifier
                        .width(indicatorWidth)
                        .height(32.dp)
                        .graphicsLayer { alpha = indicatorAlpha }
                        .background(
                            color = colors.accent,
                            shape = RoundedCornerShape(50),
                        ),
                )

                // Hover highlight
                if (hoverAlpha > 0f) {
                    Box(
                        modifier = Modifier
                            .width(48.dp)
                            .height(32.dp)
                            .graphicsLayer { alpha = hoverAlpha }
                            .background(
                                color = colors.muted,
                                shape = RoundedCornerShape(50),
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
                    modifier = Modifier
                        .padding(top = RikkaTheme.spacing.xs)
                        .offset(y = labelOffset)
                        .graphicsLayer { alpha = labelAlpha },
                ) {
                    label()
                }
            }
        }
    }
}

// ─── NavigationBarItem (convenience overload) ─────────────────

/**
 * Convenience overload of [NavigationBarItem] that accepts an
 * [ImageVector] and a [String] label directly.
 *
 * Automatically handles icon tinting based on selection state:
 * selected items use [RikkaTheme.colors.primary], unselected items
 * use [RikkaTheme.colors.mutedForeground].
 *
 * Usage:
 * ```
 * var selectedIndex by remember { mutableStateOf(0) }
 *
 * NavigationBar {
 *     NavigationBarItem(
 *         selected = selectedIndex == 0,
 *         onClick = { selectedIndex = 0 },
 *         icon = RikkaIcons.Home,
 *         label = "Home",
 *     )
 *     NavigationBarItem(
 *         selected = selectedIndex == 1,
 *         onClick = { selectedIndex = 1 },
 *         icon = RikkaIcons.Search,
 *         label = "Search",
 *     )
 *     NavigationBarItem(
 *         selected = selectedIndex == 2,
 *         onClick = { selectedIndex = 2 },
 *         icon = RikkaIcons.Settings,
 *         label = "Settings",
 *     )
 * }
 * ```
 *
 * @param selected Whether this item is currently active.
 * @param onClick Called when the item is tapped.
 * @param icon The icon [ImageVector] to display.
 * @param label The text label displayed below the icon.
 * @param modifier Modifier for layout and decoration.
 * @param selectedIcon Optional different icon for the selected state.
 *   Falls back to [icon] when null.
 * @param enabled Whether the item is interactive.
 * @param alwaysShowLabel When `false`, the label fades out when
 *   the item is not selected.
 */
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
) {
    val colors = RikkaTheme.colors
    val motion = RikkaTheme.motion

    // ─── Animated icon + label colors ────────────────────
    val iconColor by animateColorAsState(
        targetValue = if (selected) {
            colors.primary
        } else {
            colors.mutedForeground
        },
        animationSpec = tween(motion.durationDefault),
    )
    val labelColor by animateColorAsState(
        targetValue = if (selected) {
            colors.primary
        } else {
            colors.mutedForeground
        },
        animationSpec = tween(motion.durationDefault),
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
        modifier = modifier.semantics {
            contentDescription = label
        },
        label = {
            Text(
                text = label,
                variant = TextVariant.Small,
                color = labelColor,
            )
        },
        selectedIcon = if (selectedIcon != null) {
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
    )
}
