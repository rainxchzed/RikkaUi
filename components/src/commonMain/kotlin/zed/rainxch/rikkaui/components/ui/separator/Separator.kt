package zed.rainxch.rikkaui.components.ui.separator

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Orientation ────────────────────────────────────────────

enum class SeparatorOrientation {
    /** Full-width line divider. */
    Horizontal,
    /** Full-height line divider. */
    Vertical,
}

// ─── Style ──────────────────────────────────────────────────

enum class SeparatorStyle {
    /** Continuous line. */
    Solid,
    /** Dashed line with configurable dash/gap lengths. */
    Dashed,
    /** Dotted line (short dashes with equal gaps). */
    Dotted,
}

// ─── Component ──────────────────────────────────────────────

@Composable
fun Separator(
    modifier: Modifier = Modifier,
    orientation: SeparatorOrientation = SeparatorOrientation.Horizontal,
    color: Color = Color.Unspecified,
    thickness: Dp = 1.dp,
    style: SeparatorStyle = SeparatorStyle.Solid,
) {
    val resolvedColor =
        if (color != Color.Unspecified) color else RikkaTheme.colors.border

    val sizeModifier =
        when (orientation) {
            SeparatorOrientation.Horizontal -> {
                Modifier
                    .fillMaxWidth()
                    .height(thickness)
            }

            SeparatorOrientation.Vertical -> {
                Modifier
                    .fillMaxHeight()
                    .width(thickness)
            }
        }

    // Separators are purely decorative — clear all semantics so
    // screen readers skip them entirely instead of announcing "empty".
    Canvas(
        modifier =
            modifier
                .then(sizeModifier)
                .clearAndSetSemantics { },
    ) {
        val pathEffect =
            when (style) {
                SeparatorStyle.Solid -> {
                    null
                }

                SeparatorStyle.Dashed -> {
                    PathEffect.dashPathEffect(
                        floatArrayOf(8f, 4f),
                    )
                }

                SeparatorStyle.Dotted -> {
                    PathEffect.dashPathEffect(
                        floatArrayOf(2f, 2f),
                    )
                }
            }

        val isHorizontal =
            orientation == SeparatorOrientation.Horizontal
        val strokeWidth = thickness.toPx()
        val center = strokeWidth / 2f

        drawLine(
            color = resolvedColor,
            start =
                if (isHorizontal) {
                    Offset(0f, center)
                } else {
                    Offset(center, 0f)
                },
            end =
                if (isHorizontal) {
                    Offset(size.width, center)
                } else {
                    Offset(center, size.height)
                },
            strokeWidth = strokeWidth,
            pathEffect = pathEffect,
        )
    }
}
