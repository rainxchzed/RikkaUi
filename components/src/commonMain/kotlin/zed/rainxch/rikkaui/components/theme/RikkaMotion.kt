package zed.rainxch.rikkaui.components.theme

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

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
 */
@Immutable
data class RikkaMotion(
    // ─── Spring animations (interactive, interruptible) ─────

    /**
     * Default spring for most interactive animations.
     * Medium bouncy, medium-low stiffness — responsive but not snappy.
     * Used by: Button scale, Toggle thumb, expandable content.
     */
    val springDefault: AnimationSpec<Float> =
        spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow,
        ),

    /**
     * Gentle spring for playful interactions.
     * Low bouncy, low stiffness — visible overshoot.
     * Used by: Button bounce animation, fun micro-interactions.
     */
    val springBouncy: AnimationSpec<Float> =
        spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow,
        ),

    /**
     * Snappy spring for instant-feeling transitions.
     * No bounce, high stiffness — crisp and decisive.
     * Used by: Focus ring, selection indicators, snap-to-position.
     */
    val springSnap: AnimationSpec<Float> =
        spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessHigh,
        ),

    // ─── Tween animations (fixed-duration, predictable) ─────

    /**
     * Fast tween for micro-interactions.
     * 100ms — hover state color changes, opacity toggles.
     */
    val tweenFast: AnimationSpec<Float> = tween(durationMillis = 100),

    /**
     * Standard tween for most color/opacity transitions.
     * 150ms — button background, border color, focus ring.
     */
    val tweenDefault: AnimationSpec<Float> = tween(durationMillis = 150),

    /**
     * Slow tween for larger visual transitions.
     * 250ms — card expand, page fade-in, layout shifts.
     */
    val tweenSlow: AnimationSpec<Float> = tween(durationMillis = 250),

    /**
     * Enter/exit tween for content appearing/disappearing.
     * 200ms — dropdown open, tooltip fade, dialog enter.
     */
    val tweenEnter: AnimationSpec<Float> = tween(durationMillis = 200),

    // ─── Duration tokens (for custom AnimationSpec) ──────────

    /** Fast duration in ms. For direct-feeling micro-interactions. */
    val durationFast: Int = 100,

    /** Default duration in ms. For most transitions. */
    val durationDefault: Int = 150,

    /** Slow duration in ms. For larger, more visible transitions. */
    val durationSlow: Int = 250,

    /** Enter/exit duration in ms. For content appearing/disappearing. */
    val durationEnter: Int = 200,

    // ─── Scale targets (for press animations) ────────────────

    /** Subtle press scale (0.97). Used by Button Scale animation. */
    val pressScaleSubtle: Float = 0.97f,

    /** Noticeable press scale (0.93). Used by Button Bounce animation. */
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
