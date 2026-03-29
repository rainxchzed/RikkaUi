package zed.rainxch.rikkaui.components.theme

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

/**
 * RikkaMotion defines the animation token system for the design system.
 *
 * Every animation in every component references these tokens, so adjusting
 * one value here changes the feel across the entire UI — like a CSS variable
 * for motion.
 *
 * This is something shadcn/ui **can't do**. Web CSS transitions are limited
 * to duration + easing. We have full spring physics, velocity preservation
 * on interruption, and per-component overrides.
 *
 * ### Customization levels
 *
 * **1. Presets (quickest):**
 * ```
 * RikkaTheme(motion = RikkaMotionPresets.snappy()) { ... }
 * RikkaTheme(motion = RikkaMotionPresets.playful()) { ... }
 * RikkaTheme(motion = RikkaMotionPresets.minimal()) { ... }
 * ```
 *
 * **2. Selective override:**
 * ```
 * RikkaTheme(
 *     motion = RikkaMotion(
 *         durationDefault = 200,
 *         pressScaleSubtle = 0.95f,
 *     ),
 * ) { ... }
 * ```
 *
 * **3. Full override:**
 * ```
 * val motion = RikkaMotion(
 *     springDefault = spring(stiffness = 500f, dampingRatio = 0.7f),
 *     springBouncy = spring(stiffness = 200f, dampingRatio = 0.4f),
 *     ...
 * )
 * ```
 *
 * Design principles:
 * - **Spring first**: Springs handle interruptions gracefully.
 * - **Tween for color**: Predictable duration for color transitions.
 * - **graphicsLayer for transforms**: Skip composition + layout phases.
 *
 * @param springDefault Default spring. Medium bouncy, medium-low stiffness.
 * @param springBouncy Gentle spring for playful interactions.
 * @param springSnap Snappy spring for instant transitions.
 * @param tweenFast Fast tween (100ms) for micro-interactions.
 * @param tweenDefault Standard tween (150ms) for color/opacity.
 * @param tweenSlow Slow tween (250ms) for larger transitions.
 * @param tweenEnter Enter/exit tween (200ms).
 * @param durationFast Fast duration in ms.
 * @param durationDefault Default duration in ms.
 * @param durationSlow Slow duration in ms.
 * @param durationEnter Enter/exit duration in ms.
 * @param springDefaultDp Default Dp spring for position/size animations.
 * @param springBouncyDp Bouncy Dp spring for position animations.
 * @param pressScaleSubtle Subtle press scale (0.97).
 * @param pressScaleBouncy Noticeable press scale (0.93).
 */
@Immutable
data class RikkaMotion(
    val springDefault: AnimationSpec<Float> =
        spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow,
        ),
    val springBouncy: AnimationSpec<Float> =
        spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow,
        ),
    val springSnap: AnimationSpec<Float> =
        spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessHigh,
        ),
    val tweenFast: AnimationSpec<Float> = tween(durationMillis = 100),
    val tweenDefault: AnimationSpec<Float> = tween(durationMillis = 150),
    val tweenSlow: AnimationSpec<Float> = tween(durationMillis = 250),
    val tweenEnter: AnimationSpec<Float> = tween(durationMillis = 200),
    val durationFast: Int = 100,
    val durationDefault: Int = 150,
    val durationSlow: Int = 250,
    val durationEnter: Int = 200,
    val springDefaultDp: AnimationSpec<Dp> =
        spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow,
        ),
    val springBouncyDp: AnimationSpec<Dp> =
        spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow,
        ),
    val pressScaleSubtle: Float = 0.97f,
    val pressScaleBouncy: Float = 0.93f,
)

val LocalRikkaMotion =
    staticCompositionLocalOf<RikkaMotion> {
        error(
            "No RikkaMotion provided. Wrap your content in RikkaTheme { ... }",
        )
    }

/**
 * Creates a default [RikkaMotion] with balanced animation tokens.
 */
fun defaultRikkaMotion(): RikkaMotion = RikkaMotion()

/**
 * Pre-built motion presets for common animation styles.
 *
 * ```
 * RikkaTheme(motion = RikkaMotionPresets.snappy()) { ... }
 * ```
 */
object RikkaMotionPresets {
    /**
     * Fast, no-bounce animations. Professional, precise feel.
     * Good for data-heavy dashboards and productivity tools.
     */
    fun snappy(): RikkaMotion =
        RikkaMotion(
            springDefault =
                spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessHigh,
                ),
            springBouncy =
                spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium,
                ),
            springSnap =
                spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessHigh,
                ),
            durationFast = 80,
            durationDefault = 120,
            durationSlow = 180,
            durationEnter = 150,
            tweenFast = tween(durationMillis = 80),
            tweenDefault = tween(durationMillis = 120),
            tweenSlow = tween(durationMillis = 180),
            tweenEnter = tween(durationMillis = 150),
            springDefaultDp =
                spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessHigh,
                ),
            springBouncyDp =
                spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium,
                ),
            pressScaleSubtle = 0.98f,
            pressScaleBouncy = 0.95f,
        )

    /**
     * Bouncy, exaggerated animations. Fun, playful feel.
     * Good for consumer apps, games, creative tools.
     */
    fun playful(): RikkaMotion =
        RikkaMotion(
            springDefault =
                spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow,
                ),
            springBouncy =
                spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessLow,
                ),
            springSnap =
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium,
                ),
            durationFast = 120,
            durationDefault = 200,
            durationSlow = 350,
            durationEnter = 250,
            tweenFast = tween(durationMillis = 120),
            tweenDefault = tween(durationMillis = 200),
            tweenSlow = tween(durationMillis = 350),
            tweenEnter = tween(durationMillis = 250),
            springDefaultDp =
                spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow,
                ),
            springBouncyDp =
                spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessLow,
                ),
            pressScaleSubtle = 0.95f,
            pressScaleBouncy = 0.88f,
        )

    /**
     * Minimal, subtle animations. Calm, understated feel.
     * Good for reading apps, accessibility-focused UIs.
     */
    fun minimal(): RikkaMotion =
        RikkaMotion(
            springDefault =
                spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium,
                ),
            springBouncy =
                spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMediumLow,
                ),
            springSnap =
                spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessHigh,
                ),
            durationFast = 60,
            durationDefault = 100,
            durationSlow = 150,
            durationEnter = 120,
            tweenFast = tween(durationMillis = 60),
            tweenDefault = tween(durationMillis = 100),
            tweenSlow = tween(durationMillis = 150),
            tweenEnter = tween(durationMillis = 120),
            springDefaultDp =
                spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium,
                ),
            springBouncyDp =
                spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMediumLow,
                ),
            pressScaleSubtle = 0.99f,
            pressScaleBouncy = 0.97f,
        )
}
