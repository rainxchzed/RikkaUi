package zed.rainxch.rikkaui.foundation.modifier

import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.foundation.RikkaTheme

/**
 * Applies a focus ring border using the theme's [ring][zed.rainxch.rikkaui.foundation.RikkaColors.ring] color.
 *
 * This is the RikkaUI equivalent of shadcn's `focus-visible:ring-2`.
 * Apply conditionally when the component is focused:
 *
 * ```
 * val isFocused by interactionSource.collectIsFocusedAsState()
 *
 * Box(
 *     modifier = Modifier
 *         .then(if (isFocused) Modifier.focusRing(shape) else Modifier)
 *         .background(colors.primary, shape),
 * )
 * ```
 *
 * @param shape The shape of the ring border (should match the component shape).
 * @param width The ring border width. Defaults to 2dp.
 * @param offset Additional offset from the component edge. Defaults to 2dp.
 */
@Composable
public fun Modifier.focusRing(
    shape: Shape,
    width: Dp = 2.dp,
    offset: Dp = 2.dp,
): Modifier {
    val ringColor = RikkaTheme.colors.ring
    return this then Modifier.border(width = width + offset, color = ringColor, shape = shape)
}
