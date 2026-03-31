package zed.rainxch.rikkaui.components.ui.togglegroup

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.snap
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
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme

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
    val colorAnimSpec: AnimationSpec<Color> = resolveAnimSpec(animation, motion)

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
        resolveAnimSpec(animation, RikkaTheme.motion)

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
            selected && selectedColorOverride != Color.Unspecified -> {
                selectedColorOverride
            }

            !selected && unselectedColorOverride != Color.Unspecified -> {
                unselectedColorOverride
            }

            selected -> {
                colors.foreground
            }

            else -> {
                colors.mutedForeground
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
