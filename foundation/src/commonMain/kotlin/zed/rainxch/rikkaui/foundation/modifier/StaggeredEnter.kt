package zed.rainxch.rikkaui.foundation.modifier

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.launch

/** Stagger delay strategy for list enter animations. */
enum class StaggerDelay {
    /** 50ms between items — fast cascade. */
    Fast,

    /** 80ms between items — balanced. */
    Default,

    /** 120ms between items — dramatic reveal. */
    Slow,
}

/** Direction from which items slide in. */
enum class StaggerDirection {
    /** Slide up from below. */
    Up,

    /** Slide down from above. */
    Down,

    /** Slide in from the left. */
    Left,

    /** Slide in from the right. */
    Right,
}

fun Modifier.staggeredEnter(
    index: Int,
    delay: StaggerDelay = StaggerDelay.Default,
    direction: StaggerDirection = StaggerDirection.Up,
    durationMs: Int = 300,
    slideOffset: Float = 24f,
): Modifier =
    composed {
        val delayMs =
            when (delay) {
                StaggerDelay.Fast -> 50
                StaggerDelay.Default -> 80
                StaggerDelay.Slow -> 120
            }

        val alpha = remember { Animatable(0f) }
        val offset = remember { Animatable(slideOffset) }

        LaunchedEffect(Unit) {
            val staggerMs = index * delayMs
            launch {
                alpha.animateTo(
                    targetValue = 1f,
                    animationSpec =
                        tween(
                            durationMillis = durationMs,
                            delayMillis = staggerMs,
                        ),
                )
            }
            launch {
                offset.animateTo(
                    targetValue = 0f,
                    animationSpec =
                        tween(
                            durationMillis = durationMs,
                            delayMillis = staggerMs,
                        ),
                )
            }
        }

        graphicsLayer {
            this.alpha = alpha.value
            when (direction) {
                StaggerDirection.Up -> translationY = offset.value
                StaggerDirection.Down -> translationY = -offset.value
                StaggerDirection.Left -> translationX = offset.value
                StaggerDirection.Right -> translationX = -offset.value
            }
        }
    }
