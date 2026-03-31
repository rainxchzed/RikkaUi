package zed.rainxch.rikkaui.components.ui.checkbox

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Animation ──────────────────────────────────────────────

enum class CheckboxAnimation {
    /** Bouncy spring physics (default). */
    Spring,

    /** Linear tween transition. */
    Tween,

    /** Instant state change, no animation. */
    None,
}

// ─── Component ──────────────────────────────────────────────

@Composable
fun Checkbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    animation: CheckboxAnimation = CheckboxAnimation.Spring,
    enabled: Boolean = true,
    label: String = "",
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val colors = RikkaTheme.colors
    val motion = RikkaTheme.motion
    val shape = RikkaTheme.shapes.sm

    // ─── Color resolution ───────────────────────────────
    val resolvedColors = resolveCheckboxColors(checked, isHovered, isPressed, enabled)

    val animatedBackground by animateColorAsState(
        targetValue = resolvedColors.background,
        animationSpec =
            when (animation) {
                CheckboxAnimation.Spring -> tween(motion.durationDefault)
                CheckboxAnimation.Tween -> tween(motion.durationDefault)
                CheckboxAnimation.None -> snap()
            },
    )

    val animatedBorder by animateColorAsState(
        targetValue = resolvedColors.border,
        animationSpec =
            when (animation) {
                CheckboxAnimation.Spring -> tween(motion.durationDefault)
                CheckboxAnimation.Tween -> tween(motion.durationDefault)
                CheckboxAnimation.None -> snap()
            },
    )

    // ─── Checkmark scale animation ──────────────────────
    val checkmarkScale by animateFloatAsState(
        targetValue = if (checked) 1f else 0f,
        animationSpec =
            when (animation) {
                CheckboxAnimation.Spring -> motion.springDefault
                CheckboxAnimation.Tween -> tween(motion.durationDefault)
                CheckboxAnimation.None -> snap()
            },
    )

    val checkmarkColor = colors.primaryForeground

    // ─── Layout ─────────────────────────────────────────
    Row(
        modifier =
            modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    role = Role.Checkbox,
                    onClick = { onCheckedChange(!checked) },
                ).semantics(mergeDescendants = true) {
                    if (label.isNotEmpty()) {
                        contentDescription = label
                    }
                    stateDescription = if (checked) "Checked" else "Unchecked"
                    if (!enabled) {
                        disabled()
                    }
                },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // ─── Checkbox box ───────────────────────────────
        Box(
            modifier =
                Modifier
                    .size(16.dp)
                    .clip(shape)
                    .background(animatedBackground, shape)
                    .border(1.5.dp, animatedBorder, shape)
                    .then(if (!enabled) Modifier.alpha(0.5f) else Modifier)
                    .graphicsLayer {
                        scaleX = checkmarkScale
                        scaleY = checkmarkScale
                    }.drawBehind {
                        if (checked) {
                            val strokeWidth = 2.dp.toPx()
                            val w = size.width
                            val h = size.height

                            // Checkmark: two lines forming an "L"-rotated shape
                            // Start at left-center, go to bottom-center, go to top-right
                            val start = Offset(w * 0.22f, h * 0.52f)
                            val mid = Offset(w * 0.42f, h * 0.72f)
                            val end = Offset(w * 0.78f, h * 0.30f)

                            drawLine(
                                color = checkmarkColor,
                                start = start,
                                end = mid,
                                strokeWidth = strokeWidth,
                                cap = StrokeCap.Round,
                            )
                            drawLine(
                                color = checkmarkColor,
                                start = mid,
                                end = end,
                                strokeWidth = strokeWidth,
                                cap = StrokeCap.Round,
                            )
                        }
                    },
            contentAlignment = Alignment.Center,
        ) {}

        // ─── Label ──────────────────────────────────────
        if (label.isNotEmpty()) {
            Spacer(modifier = Modifier.width(RikkaTheme.spacing.sm))
            Text(
                text = label,
                color =
                    if (enabled) {
                        RikkaTheme.colors.foreground
                    } else {
                        RikkaTheme.colors.mutedForeground
                    },
                style = RikkaTheme.typography.small,
            )
        }
    }
}

// ─── Internal: Color Resolution ─────────────────────────────

private data class CheckboxColors(
    val background: Color,
    val border: Color,
)

@Composable
private fun resolveCheckboxColors(
    checked: Boolean,
    isHovered: Boolean,
    isPressed: Boolean,
    enabled: Boolean,
): CheckboxColors {
    val colors = RikkaTheme.colors
    val motion = RikkaTheme.motion

    return when {
        !enabled && checked -> {
            CheckboxColors(
                background = colors.primary.copy(alpha = 0.5f),
                border = colors.primary.copy(alpha = 0.5f),
            )
        }

        !enabled -> {
            CheckboxColors(
                background = Color.Transparent,
                border = colors.border.copy(alpha = 0.5f),
            )
        }

        checked && isPressed -> {
            val pressed = lerp(colors.primary, colors.foreground, 1f - motion.pressAlpha)
            CheckboxColors(
                background = pressed,
                border = pressed,
            )
        }

        checked && isHovered -> {
            val hovered = lerp(colors.primary, colors.foreground, 1f - motion.hoverAlpha)
            CheckboxColors(
                background = hovered,
                border = hovered,
            )
        }

        checked -> {
            CheckboxColors(
                background = colors.primary,
                border = colors.primary,
            )
        }

        isPressed -> {
            CheckboxColors(
                background = Color.Transparent,
                border = colors.primary.copy(alpha = 0.6f),
            )
        }

        isHovered -> {
            CheckboxColors(
                background = Color.Transparent,
                border = colors.primary.copy(alpha = 0.4f),
            )
        }

        else -> {
            CheckboxColors(
                background = Color.Transparent,
                border = colors.border,
            )
        }
    }
}
