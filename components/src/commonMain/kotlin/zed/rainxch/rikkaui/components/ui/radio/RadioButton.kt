package zed.rainxch.rikkaui.components.ui.radio

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.Stroke
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

/** Animation style for radio button dot and ring transitions. */
enum class RadioAnimation {
    /** Bouncy spring physics (default). */
    Spring,

    /** Linear tween transition. */
    Tween,

    /** Instant state change, no animation. */
    None,
}

// ─── Component ──────────────────────────────────────────────

@Composable
fun RadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    animation: RadioAnimation = RadioAnimation.Spring,
    enabled: Boolean = true,
    label: String = "",
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val motion = RikkaTheme.motion
    val ringStrokeWidth = 1.5.dp

    // ─── Color resolution ───────────────────────────────
    val resolvedColors = resolveRadioColors(selected, isHovered, isPressed, enabled)

    val animatedRingColor by animateColorAsState(
        targetValue = resolvedColors.ring,
        animationSpec =
            when (animation) {
                RadioAnimation.Spring -> motion.effectsDefault()
                RadioAnimation.Tween -> motion.effectsDefault()
                RadioAnimation.None -> snap()
            },
    )

    val animatedDotColor by animateColorAsState(
        targetValue = resolvedColors.dot,
        animationSpec =
            when (animation) {
                RadioAnimation.Spring -> motion.effectsDefault()
                RadioAnimation.Tween -> motion.effectsDefault()
                RadioAnimation.None -> snap()
            },
    )

    // ─── Inner dot scale animation ──────────────────────
    val dotScale by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec =
            when (animation) {
                RadioAnimation.Spring -> motion.spatialDefault()
                RadioAnimation.Tween -> motion.effectsDefault()
                RadioAnimation.None -> snap()
            },
    )

    // ─── Layout ─────────────────────────────────────────
    Row(
        modifier =
            modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    role = Role.RadioButton,
                    onClick = onClick,
                ).semantics(mergeDescendants = true) {
                    if (label.isNotEmpty()) {
                        contentDescription = label
                    }
                    stateDescription = if (selected) "Selected" else "Not selected"
                    if (!enabled) {
                        disabled()
                    }
                },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // ─── Radio circle ───────────────────────────────
        Box(
            modifier =
                Modifier
                    .size(20.dp)
                    .then(if (!enabled) Modifier.alpha(0.5f) else Modifier)
                    .drawBehind {
                        val strokeWidth = ringStrokeWidth.toPx()
                        val radius = (size.minDimension - strokeWidth) / 2f

                        // Outer ring
                        drawCircle(
                            color = animatedRingColor,
                            radius = radius,
                            style = Stroke(width = strokeWidth),
                        )
                    }.graphicsLayer {
                        // Scale only the inner dot drawing
                    },
            contentAlignment = Alignment.Center,
        ) {
            // ─── Inner dot ──────────────────────────────
            Box(
                modifier =
                    Modifier
                        .size(10.dp)
                        .graphicsLayer {
                            scaleX = dotScale
                            scaleY = dotScale
                        }.drawBehind {
                            drawCircle(
                                color = animatedDotColor,
                            )
                        },
            )
        }

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

private data class RadioColors(
    val ring: androidx.compose.ui.graphics.Color,
    val dot: androidx.compose.ui.graphics.Color,
)

@Composable
private fun resolveRadioColors(
    selected: Boolean,
    isHovered: Boolean,
    isPressed: Boolean,
    enabled: Boolean,
): RadioColors {
    val colors = RikkaTheme.colors

    val motion = RikkaTheme.motion

    return when {
        !enabled && selected -> {
            RadioColors(
                ring = colors.primary.copy(alpha = 0.5f),
                dot = colors.primary.copy(alpha = 0.5f),
            )
        }

        !enabled -> {
            RadioColors(
                ring = colors.border.copy(alpha = 0.5f),
                dot = androidx.compose.ui.graphics.Color.Transparent,
            )
        }

        selected && isPressed -> {
            val pressed = lerp(colors.primary, colors.foreground, 1f - motion.pressAlpha)
            RadioColors(
                ring = pressed,
                dot = pressed,
            )
        }

        selected && isHovered -> {
            val hovered = lerp(colors.primary, colors.foreground, 1f - motion.hoverAlpha)
            RadioColors(
                ring = hovered,
                dot = hovered,
            )
        }

        selected -> {
            RadioColors(
                ring = colors.primary,
                dot = colors.primary,
            )
        }

        isPressed -> {
            RadioColors(
                ring = colors.primary.copy(alpha = 0.6f),
                dot = androidx.compose.ui.graphics.Color.Transparent,
            )
        }

        isHovered -> {
            RadioColors(
                ring = colors.primary.copy(alpha = 0.4f),
                dot = androidx.compose.ui.graphics.Color.Transparent,
            )
        }

        else -> {
            RadioColors(
                ring = colors.border,
                dot = androidx.compose.ui.graphics.Color.Transparent,
            )
        }
    }
}
