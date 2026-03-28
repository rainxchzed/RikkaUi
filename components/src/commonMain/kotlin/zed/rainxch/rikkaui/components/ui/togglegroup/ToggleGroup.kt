package zed.rainxch.rikkaui.components.ui.togglegroup

import androidx.compose.animation.animateColorAsState
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
 * background color transitions using theme motion tokens.
 *
 * Usage:
 * ```
 * ToggleGroupItem(
 *     selected = isSelected,
 *     onClick = { toggle() },
 *     variant = ToggleGroupVariant.Outline,
 * ) {
 *     Icon(painter = painterResource(...), contentDescription = null)
 * }
 * ```
 *
 * @param selected Whether this item is currently active.
 * @param onClick Called when the item is clicked.
 * @param modifier Modifier for layout and decoration.
 * @param variant Visual variant — controls background and border behavior.
 * @param label Accessibility label for screen readers.
 * @param content Item content — typically an icon or text.
 */
@Composable
fun ToggleGroupItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ToggleGroupVariant = ToggleGroupVariant.Default,
    label: String = "",
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val colors = RikkaTheme.colors
    val motion = RikkaTheme.motion
    val shape = RikkaTheme.shapes.md

    val resolved = resolveColors(variant, selected)

    // ─── Animated background (from theme motion tokens) ──
    val animatedBackground by animateColorAsState(
        targetValue = resolved.background,
        animationSpec = tween(motion.durationDefault),
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
                )
                .padding(
                    horizontal = RikkaTheme.spacing.md,
                    vertical = RikkaTheme.spacing.sm,
                )
                .semantics {
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
 * )
 * ```
 *
 * @param text Label text displayed in the toggle item.
 * @param selected Whether this item is currently active.
 * @param onClick Called when the item is clicked.
 * @param modifier Modifier for layout and decoration.
 * @param variant Visual variant — controls background and border behavior.
 */
@Composable
fun ToggleGroupItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ToggleGroupVariant = ToggleGroupVariant.Default,
) {
    val colors = resolveColors(variant, selected)

    ToggleGroupItem(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        variant = variant,
        label = text,
    ) {
        Text(
            text = text,
            variant = TextVariant.Small,
            color = colors.foreground,
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
): ToggleGroupColors {
    val colors = RikkaTheme.colors

    return when (variant) {
        ToggleGroupVariant.Default -> {
            ToggleGroupColors(
                background =
                    if (selected) colors.muted else Color.Transparent,
                foreground =
                    if (selected) colors.foreground else colors.mutedForeground,
                border = Color.Transparent,
            )
        }

        ToggleGroupVariant.Outline -> {
            ToggleGroupColors(
                background =
                    if (selected) colors.muted else Color.Transparent,
                foreground =
                    if (selected) colors.foreground else colors.mutedForeground,
                border = colors.border,
            )
        }
    }
}
