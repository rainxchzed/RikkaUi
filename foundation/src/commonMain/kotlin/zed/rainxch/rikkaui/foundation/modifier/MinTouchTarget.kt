package zed.rainxch.rikkaui.foundation.modifier

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.currentValueOf
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isSpecified
import kotlin.math.roundToInt

/**
 * Reserves at least [LocalMinTouchTarget] (48dp by default) in both
 * dimensions to ensure accessible touch targets.
 *
 * If the component measures smaller than the minimum, extra space is added
 * around it while the component itself stays visually centered. The visual
 * size does not change — only the tappable area expands.
 *
 * This follows WCAG accessibility guidelines for minimum touch target sizes
 * (48dp on Android, 44pt on iOS). Place this modifier **before** any size
 * modifiers so it can expand the constraints.
 *
 * ### Usage
 * ```
 * Icon(
 *     imageVector = RikkaIcons.Check,
 *     contentDescription = "Confirm",
 *     modifier = Modifier
 *         .minTouchTarget()
 *         .size(24.dp),
 * )
 * ```
 *
 * ### Opt out
 * ```
 * CompositionLocalProvider(
 *     LocalMinTouchTarget provides 0.dp,
 * ) {
 *     // Components inside will not enforce minimum touch targets
 * }
 * ```
 *
 * @see LocalMinTouchTarget
 */
@Stable
fun Modifier.minTouchTarget(): Modifier = this then MinTouchTargetElement

/**
 * CompositionLocal that configures the minimum touch target size for
 * interactive components (buttons, checkboxes, toggles, etc.).
 *
 * Set to [Dp.Unspecified] or `0.dp` to disable enforcement.
 * Default is `48.dp` per WCAG accessibility guidelines.
 */
val LocalMinTouchTarget: ProvidableCompositionLocal<Dp> =
    staticCompositionLocalOf { 48.dp }

// ─── Modifier.Node Implementation ──────────────────────────

private object MinTouchTargetElement :
    ModifierNodeElement<MinTouchTargetNode>() {
    override fun create(): MinTouchTargetNode = MinTouchTargetNode()

    override fun update(node: MinTouchTargetNode) {
        // No instance state — config comes from CompositionLocal
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "minTouchTarget"
        properties["description"] =
            "Reserves at least 48dp to ensure accessible touch targets"
    }

    override fun hashCode(): Int = "MinTouchTargetElement".hashCode()

    override fun equals(other: Any?): Boolean = other === this
}

private class MinTouchTargetNode :
    Modifier.Node(),
    CompositionLocalConsumerModifierNode,
    LayoutModifierNode {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints,
    ): MeasureResult {
        val size =
            currentValueOf(LocalMinTouchTarget)
                .coerceAtLeast(0.dp)
        val placeable = measurable.measure(constraints)
        val enforce = isAttached && size.isSpecified && size > 0.dp

        val sizePx = if (size.isSpecified) size.roundToPx() else 0
        val width = if (enforce) maxOf(placeable.width, sizePx) else placeable.width
        val height = if (enforce) maxOf(placeable.height, sizePx) else placeable.height

        return layout(width, height) {
            val centerX = ((width - placeable.width) / 2f).roundToInt()
            val centerY = ((height - placeable.height) / 2f).roundToInt()
            placeable.place(centerX, centerY)
        }
    }
}
