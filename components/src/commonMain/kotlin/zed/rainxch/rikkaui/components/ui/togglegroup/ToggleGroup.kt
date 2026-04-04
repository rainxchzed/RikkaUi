package zed.rainxch.rikkaui.components.ui.togglegroup

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.snap
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.LocalContentColor
import zed.rainxch.rikkaui.foundation.RikkaTheme
import zed.rainxch.rikkaui.foundation.modifier.minTouchTarget

// ─── Variant ───────────────────────────────────────────────

/** Muted bg when selected; Outline adds a 1dp border. */
enum class ToggleGroupVariant {
    /** Muted background when selected, transparent otherwise. */
    Default,

    /** 1dp border always visible; muted background when selected. */
    Outline,
}

// ─── Animation ────────────────────────────────────────────

/** Animation strategy for selection color transitions. */
enum class ToggleGroupAnimation {
    /** Spring-based color transition (default). */
    Spring,

    /** Smooth eased tween transition. */
    Tween,

    /** Instant change, no animation. */
    None,
}

// ─── ToggleGroup ───────────────────────────────────────────

/**
 * A horizontal group container for [ToggleGroupItem] buttons with selectable-group semantics.
 *
 * Applies `selectableGroup()` semantics so screen readers treat child items
 * as a mutually exclusive selection set. Items are spaced using [RikkaTheme.spacing.xs].
 *
 * ```
 * var selected by remember { mutableStateOf("left") }
 * ToggleGroup {
 *     ToggleGroupItem(text = "Left", selected = selected == "left", onClick = { selected = "left" })
 *     ToggleGroupItem(text = "Center", selected = selected == "center", onClick = { selected = "center" })
 *     ToggleGroupItem(text = "Right", selected = selected == "right", onClick = { selected = "right" })
 * }
 * ```
 *
 * @param modifier Modifier applied to the outer Row container.
 * @param content Composable slot for [ToggleGroupItem] children.
 */
@Composable
fun ToggleGroup(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier.selectableGroup(),
        horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        content()
    }
}

// ─── ToggleGroupItem ───────────────────────────────────────

/**
 * An individual selectable item inside a [ToggleGroup].
 *
 * Renders a box with animated background and foreground color transitions
 * that reflect the current selection state. The resolved foreground color
 * is provided to children via [LocalContentColor], so Text and Icon composables
 * inside the [content] lambda automatically inherit the correct color.
 *
 * ```
 * ToggleGroupItem(
 *     selected = isSelected,
 *     onClick = { onSelect() },
 *     label = "Bold",
 * ) {
 *     Icon(RikkaIcons.Edit, contentDescription = null)
 * }
 * ```
 *
 * @param selected Whether this item is currently selected.
 * @param onClick Called when the user taps this item.
 * @param modifier Modifier applied to the item Box container.
 * @param variant Visual style -- [ToggleGroupVariant.Default] or [ToggleGroupVariant.Outline].
 *   Defaults to [ToggleGroupVariant.Default].
 * @param animation Color transition style -- [ToggleGroupAnimation.Spring], Tween, None.
 *   Defaults to [ToggleGroupAnimation.Spring].
 * @param label Accessibility content description for screen readers. Defaults to empty.
 * @param selectedColor Override foreground color when selected. [Color.Unspecified] uses theme default.
 * @param unselectedColor Override foreground color when unselected. [Color.Unspecified] uses theme default.
 * @param content Composable content displayed inside the item.
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
    val isHovered by interactionSource.collectIsHoveredAsState()
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
    val colorAnimSpec: AnimationSpec<Color> = resolveAnimSpec(animation, motion)

    // ─── Animated colors (from theme motion tokens) ───────
    val animatedBackground by animateColorAsState(
        targetValue = resolved.background,
        animationSpec = colorAnimSpec,
    )

    val animatedForeground by animateColorAsState(
        targetValue = resolved.foreground,
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
                .minTouchTarget()
                .then(borderModifier)
                .then(backgroundModifier)
                .clip(shape)
                .graphicsLayer {
                    alpha = if (isHovered && !selected) motion.hoverAlpha else 1f
                }.selectable(
                    selected = selected,
                    interactionSource = interactionSource,
                    indication = null,
                    role = Role.RadioButton,
                    onClick = onClick,
                ).padding(
                    horizontal = RikkaTheme.spacing.md,
                    vertical = RikkaTheme.spacing.sm,
                ).semantics {
                    this.selected = selected
                    if (label.isNotEmpty()) {
                        contentDescription = label
                    }
                },
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(LocalContentColor provides animatedForeground) {
            content()
        }
    }
}

/**
 * Convenience overload of [ToggleGroupItem] that renders a text label.
 *
 * Equivalent to wrapping a [Text] composable inside the content lambda of the
 * generic [ToggleGroupItem] overload. The [text] value is also used as the
 * accessibility content description.
 *
 * ```
 * ToggleGroupItem(
 *     text = "Bold",
 *     selected = isBold,
 *     onClick = { toggleBold() },
 * )
 * ```
 *
 * @param text The label to display and use as accessibility description.
 * @param selected Whether this item is currently selected.
 * @param onClick Called when the user taps this item.
 * @param modifier Modifier applied to the item Box container.
 * @param variant Visual style -- [ToggleGroupVariant.Default] or [ToggleGroupVariant.Outline].
 *   Defaults to [ToggleGroupVariant.Default].
 * @param animation Color transition style -- [ToggleGroupAnimation.Spring], Tween, None.
 *   Defaults to [ToggleGroupAnimation.Spring].
 * @param selectedColor Override foreground color when selected. [Color.Unspecified] uses theme default.
 * @param unselectedColor Override foreground color when unselected. [Color.Unspecified] uses theme default.
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
            selected && selectedColorOverride != Color.Unspecified -> {
                selectedColorOverride
            }

            !selected && unselectedColorOverride != Color.Unspecified -> {
                unselectedColorOverride
            }

            selected -> {
                colors.onBackground
            }

            else -> {
                colors.onMuted
            }
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

@Composable
private fun <T> resolveAnimSpec(
    animation: ToggleGroupAnimation,
    motion: zed.rainxch.rikkaui.foundation.RikkaMotion,
): AnimationSpec<T> =
    when (animation) {
        ToggleGroupAnimation.Spring -> motion.spatialDefault()
        ToggleGroupAnimation.Tween -> motion.effectsDefault()
        ToggleGroupAnimation.None -> snap()
    }
