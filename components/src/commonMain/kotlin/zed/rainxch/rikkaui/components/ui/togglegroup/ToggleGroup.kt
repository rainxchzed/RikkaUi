package zed.rainxch.rikkaui.components.ui.togglegroup

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

// ─── Variant ───────────────────────────────────────────────

/**
 * Visual variants for [ToggleGroupItem].
 *
 * - [Default] — Muted background when selected, transparent otherwise.
 * - [Outline] — 1dp border always visible; muted background when selected.
 */
enum class ToggleGroupVariant {
    Default,
    Outline,
}

// ─── Animation ────────────────────────────────────────────

/**
 * Animation strategy for [ToggleGroupItem] selection transitions.
 *
 * Controls how background and foreground colors animate when toggling
 * between selected and unselected states.
 *
 * - [Spring] — Spring-based color transition. Handles interruptions
 *   gracefully when rapidly toggling items. **(default)**
 * - [Tween] — Smooth eased transition with a fixed duration from
 *   [RikkaTheme.motion.durationDefault].
 * - [None] — Instant selection change with no animation.
 *
 * ```
 * ToggleGroupItem(
 *     selected = selected == 0,
 *     onClick = { selected = 0 },
 *     text = "Bold",
 *     animation = ToggleGroupAnimation.Spring,
 * )
 * ```
 */
enum class ToggleGroupAnimation {
    /** Spring-based color transition (default). */
    Spring,

    /** Smooth eased tween transition. */
    Tween,

    /** Instant change with no animation. */
    None,
}

// ─── ToggleGroup ───────────────────────────────────────────

/**
 * Horizontal group of toggle buttons.
 *
 * A container that lays out [ToggleGroupItem] composables side by side
 * with consistent spacing, matching shadcn/ui's Toggle Group.
 *
 * Usage:
 * ```
 * var selected by remember { mutableStateOf(0) }
 *
 * ToggleGroup {
 *     ToggleGroupItem(
 *         selected = selected == 0,
 *         onClick = { selected = 0 },
 *         text = "Bold",
 *     )
 *     ToggleGroupItem(
 *         selected = selected == 1,
 *         onClick = { selected = 1 },
 *         text = "Italic",
 *     )
 *     ToggleGroupItem(
 *         selected = selected == 2,
 *         onClick = { selected = 2 },
 *         text = "Underline",
 *     )
 * }
 * ```
 *
 * @param modifier Modifier for layout and decoration.
 * @param content Toggle group items — typically [ToggleGroupItem] composables.
 */
@Composable
fun ToggleGroup(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        content()
    }
}

// ─── ToggleGroupItem ───────────────────────────────────────

/**
 * Individual toggle button inside a [ToggleGroup].
 *
 * Supports two visual variants via [ToggleGroupVariant] and animates
 * background color transitions using theme motion tokens. The animation
 * strategy is configurable via [animation].
 *
 * Usage:
 * ```
 * ToggleGroupItem(
 *     selected = isSelected,
 *     onClick = { toggle() },
 *     variant = ToggleGroupVariant.Outline,
 *     animation = ToggleGroupAnimation.Tween,
 * ) {
 *     Icon(painter = painterResource(...), contentDescription = null)
 * }
 * ```
 *
 * @param selected Whether this item is currently active.
 * @param onClick Called when the item is clicked.
 * @param modifier Modifier for layout and decoration.
 * @param variant Visual variant — controls background and border behavior.
 * @param animation Animation strategy for selection transitions.
 *   Defaults to [ToggleGroupAnimation.Spring].
 * @param label Accessibility label for screen readers.
 * @param selectedColor Override for the selected foreground color.
 *   Defaults to [RikkaTheme.colors.foreground].
 * @param unselectedColor Override for the unselected foreground color.
 *   Defaults to [RikkaTheme.colors.mutedForeground].
 * @param content Item content — typically an icon or text.
 */
@Composable
fun ToggleGroupItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ToggleGroupVariant = ToggleGroupVariant.Default,
    animation: ToggleGroupAnimation = ToggleGroupAnimation.Spring,
    label: String = "",
    selectedColor: Color = Color.Unspecified,
    unselectedColor: Color = Color.Unspecified,
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val colors = RikkaTheme.colors
    val motion = RikkaTheme.motion
    val shape = RikkaTheme.shapes.md

    val resolved =
        resolveColors(
            variant = variant,
            selected = selected,
            selectedColorOverride = selectedColor,
            unselectedColorOverride = unselectedColor,
        )

    // ─── Resolve animation spec ───────────────────────────
    val colorAnimSpec: AnimationSpec<Color> =
        resolveColorAnimSpec(animation, motion.durationDefault)

    // ─── Animated background (from theme motion tokens) ──
    val animatedBackground by animateColorAsState(
        targetValue = resolved.background,
        animationSpec = colorAnimSpec,
    )

    val backgroundModifier =
        if (resolved.background != Color.Transparent) {
            Modifier.background(animatedBackground, shape)
        } else {
            Modifier
        }

    val borderModifier =
        if (resolved.border != Color.Transparent) {
            Modifier.border(1.dp, resolved.border, shape)
        } else {
            Modifier
        }

    Box(
        modifier =
            modifier
                .then(borderModifier)
                .then(backgroundModifier)
                .clip(shape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    role = Role.Button,
                    onClick = onClick,
                ).padding(
                    horizontal = RikkaTheme.spacing.md,
                    vertical = RikkaTheme.spacing.sm,
                ).semantics {
                    if (label.isNotEmpty()) {
                        contentDescription = label
                    }
                },
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

/**
 * Convenience overload with a text label.
 *
 * ```
 * ToggleGroupItem(
 *     selected = selected == 0,
 *     onClick = { selected = 0 },
 *     text = "Bold",
 *     variant = ToggleGroupVariant.Outline,
 *     animation = ToggleGroupAnimation.Tween,
 * )
 * ```
 *
 * @param text Label text displayed in the toggle item.
 * @param selected Whether this item is currently active.
 * @param onClick Called when the item is clicked.
 * @param modifier Modifier for layout and decoration.
 * @param variant Visual variant — controls background and border behavior.
 * @param animation Animation strategy for selection transitions.
 *   Defaults to [ToggleGroupAnimation.Spring].
 * @param selectedColor Override for the selected foreground color.
 *   Defaults to [RikkaTheme.colors.foreground].
 * @param unselectedColor Override for the unselected foreground color.
 *   Defaults to [RikkaTheme.colors.mutedForeground].
 */
@Composable
fun ToggleGroupItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ToggleGroupVariant = ToggleGroupVariant.Default,
    animation: ToggleGroupAnimation = ToggleGroupAnimation.Spring,
    selectedColor: Color = Color.Unspecified,
    unselectedColor: Color = Color.Unspecified,
) {
    val resolved =
        resolveColors(
            variant = variant,
            selected = selected,
            selectedColorOverride = selectedColor,
            unselectedColorOverride = unselectedColor,
        )

    // ─── Resolve animation spec for text color ────────────
    val colorAnimSpec: AnimationSpec<Color> =
        resolveColorAnimSpec(
            animation,
            RikkaTheme.motion.durationDefault,
        )

    val animatedForeground by animateColorAsState(
        targetValue = resolved.foreground,
        animationSpec = colorAnimSpec,
    )

    ToggleGroupItem(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        variant = variant,
        animation = animation,
        label = text,
        selectedColor = selectedColor,
        unselectedColor = unselectedColor,
    ) {
        Text(
            text = text,
            variant = TextVariant.Small,
            color = animatedForeground,
        )
    }
}

// ─── Internal: Color Resolution ─────────────────────────────

private data class ToggleGroupColors(
    val background: Color,
    val foreground: Color,
    val border: Color,
)

@Composable
private fun resolveColors(
    variant: ToggleGroupVariant,
    selected: Boolean,
    selectedColorOverride: Color = Color.Unspecified,
    unselectedColorOverride: Color = Color.Unspecified,
): ToggleGroupColors {
    val colors = RikkaTheme.colors

    val baseForeground =
        when {
            selected && selectedColorOverride != Color.Unspecified ->
                selectedColorOverride
            !selected && unselectedColorOverride != Color.Unspecified ->
                unselectedColorOverride
            selected -> colors.foreground
            else -> colors.mutedForeground
        }

    return when (variant) {
        ToggleGroupVariant.Default -> {
            ToggleGroupColors(
                background =
                    if (selected) colors.muted else Color.Transparent,
                foreground = baseForeground,
                border = Color.Transparent,
            )
        }

        ToggleGroupVariant.Outline -> {
            ToggleGroupColors(
                background =
                    if (selected) colors.muted else Color.Transparent,
                foreground = baseForeground,
                border = colors.border,
            )
        }
    }
}

// ─── Internal: Animation Spec Resolution ──────────────────

/**
 * Resolves a [ToggleGroupAnimation] to an [AnimationSpec] for color
 * transitions, using the theme's motion duration tokens.
 */
@Composable
private fun resolveColorAnimSpec(
    animation: ToggleGroupAnimation,
    durationMs: Int,
): AnimationSpec<Color> =
    when (animation) {
        ToggleGroupAnimation.Spring ->
            spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMediumLow,
            )
        ToggleGroupAnimation.Tween -> tween(durationMs)
        ToggleGroupAnimation.None -> snap()
    }
