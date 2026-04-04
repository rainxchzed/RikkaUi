package zed.rainxch.rikkaui.foundation.modifier

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Constraints
import kotlinx.coroutines.launch

/** Stagger delay strategy for list enter animations. */
public enum class StaggerDelay {
    /** 50ms between items — fast cascade. */
    Fast,

    /** 80ms between items — balanced. */
    Default,

    /** 120ms between items — dramatic reveal. */
    Slow,
}

/** Direction from which items slide in. */
public enum class StaggerDirection {
    /** Slide up from below. */
    Up,

    /** Slide down from above. */
    Down,

    /** Slide in from the left. */
    Left,

    /** Slide in from the right. */
    Right,
}

@Stable
public fun Modifier.staggeredEnter(
    index: Int,
    delay: StaggerDelay = StaggerDelay.Default,
    direction: StaggerDirection = StaggerDirection.Up,
    durationMs: Int = 300,
    slideOffset: Float = 24f,
): Modifier = this then StaggeredEnterElement(index, delay, direction, durationMs, slideOffset)

private data class StaggeredEnterElement(
    val index: Int,
    val delay: StaggerDelay,
    val direction: StaggerDirection,
    val durationMs: Int,
    val slideOffset: Float,
) : ModifierNodeElement<StaggeredEnterNode>() {
    override fun create(): StaggeredEnterNode = StaggeredEnterNode(index, delay, direction, durationMs, slideOffset)

    override fun update(node: StaggeredEnterNode) {
        node.update(index, delay, direction, durationMs, slideOffset)
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "staggeredEnter"
        properties["index"] = index
        properties["delay"] = delay
        properties["direction"] = direction
        properties["durationMs"] = durationMs
        properties["slideOffset"] = slideOffset
    }
}

private class StaggeredEnterNode(
    private var index: Int,
    private var delay: StaggerDelay,
    private var direction: StaggerDirection,
    private var durationMs: Int,
    private var slideOffset: Float,
) : Modifier.Node(),
    LayoutModifierNode {
    private val alpha = Animatable(0f)
    private val offset = Animatable(1f)

    fun update(
        index: Int,
        delay: StaggerDelay,
        direction: StaggerDirection,
        durationMs: Int,
        slideOffset: Float,
    ) {
        this.index = index
        this.delay = delay
        this.direction = direction
        this.durationMs = durationMs
        this.slideOffset = slideOffset
    }

    override fun onAttach() {
        val delayMs =
            when (delay) {
                StaggerDelay.Fast -> 50
                StaggerDelay.Default -> 80
                StaggerDelay.Slow -> 120
            }
        val staggerMs = index * delayMs

        coroutineScope.launch {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = durationMs, delayMillis = staggerMs),
            )
        }
        coroutineScope.launch {
            offset.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = durationMs, delayMillis = staggerMs),
            )
        }
    }

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints,
    ): MeasureResult {
        val placeable = measurable.measure(constraints)
        return layout(placeable.width, placeable.height) {
            placeable.placeWithLayer(0, 0) {
                this.alpha = this@StaggeredEnterNode.alpha.value
                val px = this@StaggeredEnterNode.offset.value * slideOffset
                when (direction) {
                    StaggerDirection.Up -> translationY = px
                    StaggerDirection.Down -> translationY = -px
                    StaggerDirection.Left -> translationX = px
                    StaggerDirection.Right -> translationX = -px
                }
            }
        }
    }
}
