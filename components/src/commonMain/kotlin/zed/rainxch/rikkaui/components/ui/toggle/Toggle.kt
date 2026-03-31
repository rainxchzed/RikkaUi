package zed.rainxch.rikkaui.components.ui.toggle

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Animation ──────────────────────────────────────────────

/** Animation style for toggle transitions. */
enum class ToggleAnimation {
    /** Bouncy spring physics (default). */
    Spring,

    /** Linear tween transition. */
    Tween,

    /** Instant state change, no animation. */
    None,
}

// ─── Size ───────────────────────────────────────────────────

/** Toggle size variants. */
enum class ToggleSize {
    /** Standard toggle (44x24dp). */
    Default,

    /** Compact toggle (36x20dp). */
    Sm,
}

// ─── Component ──────────────────────────────────────────────

@Composable
fun Toggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    size: ToggleSize = ToggleSize.Default,
    animation: ToggleAnimation = ToggleAnimation.Spring,
    enabled: Boolean = true,
    label: String = "",
) {
    val interactionSource = remember { MutableInteractionSource() }
    val colors = RikkaTheme.colors
    val motion = RikkaTheme.motion

    val sizeValues = resolveSizeValues(size)

    // ─── Resolve target values ───────────────────────────
    val targetBgColor =
        when {
            !enabled && checked -> colors.primary.copy(alpha = 0.5f)
            !enabled -> colors.input.copy(alpha = 0.5f)
            checked -> colors.primary
            else -> colors.input
        }

    val targetOffset =
        if (checked) {
            sizeValues.trackWidth - sizeValues.thumbSize - sizeValues.thumbPadding * 2
        } else {
            0.dp
        }

    // ─── Animated values (from theme motion tokens) ──────
    val backgroundColor by animateColorAsState(
        targetValue = targetBgColor,
        animationSpec =
            when (animation) {
                ToggleAnimation.Spring -> tween(motion.durationDefault)
                ToggleAnimation.Tween -> tween(motion.durationDefault)
                ToggleAnimation.None -> snap()
            },
    )

    val thumbOffset by animateDpAsState(
        targetValue = targetOffset,
        animationSpec =
            when (animation) {
                ToggleAnimation.Spring -> motion.springDefaultDp
                ToggleAnimation.Tween -> tween(motion.durationDefault)
                ToggleAnimation.None -> snap()
            },
    )

    // ─── Track ───────────────────────────────────────────
    Box(
        modifier =
            modifier
                .size(
                    width = sizeValues.trackWidth,
                    height = sizeValues.trackHeight,
                ).clip(RikkaTheme.shapes.full)
                .background(backgroundColor, RikkaTheme.shapes.full)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    role = Role.Switch,
                    onClick = { onCheckedChange(!checked) },
                ).semantics {
                    if (label.isNotEmpty()) {
                        contentDescription = label
                    }
                    stateDescription = if (checked) "On" else "Off"
                },
        contentAlignment = Alignment.CenterStart,
    ) {
        // ─── Thumb ───────────────────────────────────────
        Box(
            modifier =
                Modifier
                    .offset(x = sizeValues.thumbPadding + thumbOffset)
                    .size(sizeValues.thumbSize)
                    .clip(CircleShape)
                    .background(
                        if (checked) colors.primaryForeground else Color.White,
                        CircleShape,
                    ),
        )
    }
}

// ─── Internal: Size Resolution ──────────────────────────────

private data class ToggleSizeValues(
    val trackWidth: androidx.compose.ui.unit.Dp,
    val trackHeight: androidx.compose.ui.unit.Dp,
    val thumbSize: androidx.compose.ui.unit.Dp,
    val thumbPadding: androidx.compose.ui.unit.Dp,
)

@Composable
private fun resolveSizeValues(size: ToggleSize): ToggleSizeValues =
    when (size) {
        ToggleSize.Default -> {
            ToggleSizeValues(
                trackWidth = 44.dp,
                trackHeight = 24.dp,
                thumbSize = 20.dp,
                thumbPadding = 2.dp,
            )
        }

        ToggleSize.Sm -> {
            ToggleSizeValues(
                trackWidth = 36.dp,
                trackHeight = 20.dp,
                thumbSize = 16.dp,
                thumbPadding = 2.dp,
            )
        }
    }
