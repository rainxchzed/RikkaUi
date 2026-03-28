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
 * Usage:
 * ```
 * // Components automatically use these tokens.
 * // Override for a custom feel:
 * RikkaTheme(
 *     motion = RikkaMotion(
 *         springDefault = spring(
 *             dampingRatio = Spring.DampingRatioNoBouncy,
 *             stiffness = Spring.StiffnessHigh,
 *         ),
 *     ),
 * ) { ... }
 * ```
 *
 * Design principles:
 * - **Spring first**: Springs handle interruptions gracefully (velocity preservation).
 * - **Tween for color**: Color transitions use fixed-duration tweens for predictability.
 * - **graphicsLayer for transforms**: Scale/rotation/alpha skip composition + layout phases.
 *
 * @param springDefault Default spring for most interactive animations. Medium bouncy, medium-low stiffness.
 * @param springBouncy Gentle spring for playful interactions. Low bouncy, low stiffness.
 * @param springSnap Snappy spring for instant-feeling transitions. No bounce, high stiffness.
 * @param tweenFast Fast tween (100ms) for micro-interactions.
 * @param tweenDefault Standard tween (150ms) for most color/opacity transitions.
 * @param tweenSlow Slow tween (250ms) for larger visual transitions.
 * @param tweenEnter Enter/exit tween (200ms) for content appearing/disappearing.
 * @param durationFast Fast duration in ms (100).
 * @param durationDefault Default duration in ms (150).
 * @param durationSlow Slow duration in ms (250).
 * @param durationEnter Enter/exit duration in ms (200).
 * @param springDefaultDp Default Dp spring for position/size animations (Toggle thumb, sliding panels).
 * @param springBouncyDp Bouncy Dp spring for playful position animations.
 * @param pressScaleSubtle Subtle press scale (0.97). Used by Button Scale animation.
 * @param pressScaleBouncy Noticeable press scale (0.93). Used by Button Bounce animation.
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
        error("No RikkaMotion provided. Wrap your content in RikkaTheme { ... }")
    }

/**
 * Creates a default [RikkaMotion] with balanced, polished animation tokens.
 */
fun defaultRikkaMotion(): RikkaMotion = RikkaMotion()
