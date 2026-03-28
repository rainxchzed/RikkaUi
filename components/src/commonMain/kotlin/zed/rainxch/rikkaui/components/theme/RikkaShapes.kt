package zed.rainxch.rikkaui.components.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * RikkaShapes defines the shape scale for the design system.
 *
 * Based on a single radius value that scales up, matching
 * shadcn/ui's --radius token system.
 */
@Immutable
data class RikkaShapes(
    val sm: Shape,
    val md: Shape,
    val lg: Shape,
    val xl: Shape,
    val full: Shape,
)

val LocalRikkaShapes = staticCompositionLocalOf<RikkaShapes> {
    error("No RikkaShapes provided. Wrap your content in RikkaTheme { ... }")
}

/**
 * Creates a RikkaShapes from a base radius value.
 * All other sizes scale relative to this base.
 *
 * @param baseRadius The base radius in dp (default: 10dp ≈ 0.625rem)
 */
fun defaultRikkaShapes(baseRadius: Float = 10f): RikkaShapes = RikkaShapes(
    sm = RoundedCornerShape((baseRadius - 4f).coerceAtLeast(0f).dp),
    md = RoundedCornerShape((baseRadius - 2f).coerceAtLeast(0f).dp),
    lg = RoundedCornerShape(baseRadius.dp),
    xl = RoundedCornerShape((baseRadius + 4f).dp),
    full = RoundedCornerShape(50),
)
