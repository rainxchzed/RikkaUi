package zed.rainxch.rikkaui.foundation

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
 * ### Motion categories (inspired by M3 MotionScheme)
 *
 * Animations are split into two categories:
 * - **Spatial**: Position, size, scale. Springs handle interruptions gracefully.
 * - **Effects**: Color, opacity, alpha. Tweens give predictable durations.
 *
 * Factory methods like [spatialDefault] and [effectsDefault] return generic
 * `AnimationSpec<T>`, so they work with any type (Float, Dp, Color, etc.)
 * without duplication.
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
 *     spatialDampingDefault = Spring.DampingRatioMediumBouncy,
 *     spatialStiffnessDefault = Spring.StiffnessMediumLow,
 *     ...
 * )
 * ```
 *
 * Design principles:
 * - **Spring first**: Springs handle interruptions gracefully.
 * - **Tween for effects**: Predictable duration for color/opacity transitions.
 * - **graphicsLayer for transforms**: Skip composition + layout phases.
 *
 * @param spatialDampingDefault Damping ratio for default spatial spring.
 * @param spatialStiffnessDefault Stiffness for default spatial spring.
 * @param spatialDampingBouncy Damping ratio for bouncy spatial spring.
 * @param spatialStiffnessBouncy Stiffness for bouncy spatial spring.
 * @param spatialDampingSnap Damping ratio for snap spatial spring.
 * @param spatialStiffnessSnap Stiffness for snap spatial spring.
 * @param durationFast Fast duration in ms (micro-interactions).
 * @param durationDefault Default duration in ms (color/opacity).
 * @param durationSlow Slow duration in ms (larger transitions).
 * @param durationEnter Enter/exit duration in ms.
 * @param durationInstant Zero-duration for intentionally instant transitions.
 * @param durationSpin Full-rotation cycle duration for spinning indicators.
 * @param durationPulse Full-cycle duration for pulsing/breathing animations.
 * @param pressScaleSubtle Subtle press scale (0.97).
 * @param pressScaleBouncy Noticeable press scale (0.93).
 * @param overlayScaleIn Scale factor for overlay enter animations.
 * @param toastScaleIn Scale factor for toast enter animation.
 * @param hoverAlpha Alpha shift applied on hover for interactive feedback.
 * @param pressAlpha Alpha shift applied on press for interactive feedback.
 */
@Immutable
data class RikkaMotion(
    // ─── Spatial spring parameters (position, size, scale) ───
    val spatialDampingDefault: Float = Spring.DampingRatioMediumBouncy,
    val spatialStiffnessDefault: Float = Spring.StiffnessMediumLow,
    val spatialDampingBouncy: Float = Spring.DampingRatioLowBouncy,
    val spatialStiffnessBouncy: Float = Spring.StiffnessLow,
    val spatialDampingSnap: Float = Spring.DampingRatioNoBouncy,
    val spatialStiffnessSnap: Float = Spring.StiffnessHigh,
    // ─── Effects durations (color, opacity, alpha) ───
    val durationFast: Int = 100,
    val durationDefault: Int = 150,
    val durationSlow: Int = 250,
    val durationEnter: Int = 200,
    val durationInstant: Int = 0,
    val durationSpin: Int = 800,
    val durationPulse: Int = 1000,
    // ─── Scale tokens ───
    val pressScaleSubtle: Float = 0.97f,
    val pressScaleBouncy: Float = 0.93f,
    val overlayScaleIn: Float = 0.95f,
    val toastScaleIn: Float = 0.8f,
    // ─── Alpha tokens ───
    val hoverAlpha: Float = 0.85f,
    val pressAlpha: Float = 0.7f,
) {
    // ─── Spatial factory methods (generic — works with Float, Dp, etc.) ───

    /**
     * Default spatial spring. Medium bouncy, medium-low stiffness.
     * Use for most position/size/scale animations.
     */
    fun <T> spatialDefault(): AnimationSpec<T> = spring(dampingRatio = spatialDampingDefault, stiffness = spatialStiffnessDefault)

    /**
     * Bouncy spatial spring. Playful, exaggerated motion.
     * Use for delightful interactions (toggles, expand/collapse).
     */
    fun <T> spatialBouncy(): AnimationSpec<T> = spring(dampingRatio = spatialDampingBouncy, stiffness = spatialStiffnessBouncy)

    /**
     * Snap spatial spring. Instant, no-bounce.
     * Use for quick snapping (selection indicators, tabs).
     */
    fun <T> spatialSnap(): AnimationSpec<T> = spring(dampingRatio = spatialDampingSnap, stiffness = spatialStiffnessSnap)

    // ─── Effects factory methods (generic — works with Float, Color, etc.) ───

    /**
     * Fast effects tween (100ms default). Micro-interactions.
     * Use for hover state color changes.
     */
    fun <T> effectsFast(): AnimationSpec<T> = tween(durationMillis = durationFast)

    /**
     * Default effects tween (150ms default). Standard color/opacity.
     * Use for most color and alpha transitions.
     */
    fun <T> effectsDefault(): AnimationSpec<T> = tween(durationMillis = durationDefault)

    /**
     * Slow effects tween (250ms default). Larger transitions.
     * Use for page-level color changes, theme switching.
     */
    fun <T> effectsSlow(): AnimationSpec<T> = tween(durationMillis = durationSlow)

    /**
     * Enter/exit effects tween (200ms default).
     * Use for element appear/disappear animations.
     */
    fun <T> effectsEnter(): AnimationSpec<T> = tween(durationMillis = durationEnter)

    // ─── Backward-compatible pre-built specs (Float) ───

    /** @see spatialDefault */
    val springDefault: AnimationSpec<Float> get() = spatialDefault()

    /** @see spatialBouncy */
    val springBouncy: AnimationSpec<Float> get() = spatialBouncy()

    /** @see spatialSnap */
    val springSnap: AnimationSpec<Float> get() = spatialSnap()

    /** @see effectsFast */
    val tweenFast: AnimationSpec<Float> get() = effectsFast()

    /** @see effectsDefault */
    val tweenDefault: AnimationSpec<Float> get() = effectsDefault()

    /** @see effectsSlow */
    val tweenSlow: AnimationSpec<Float> get() = effectsSlow()

    /** @see effectsEnter */
    val tweenEnter: AnimationSpec<Float> get() = effectsEnter()
}

val LocalRikkaMotion =
    staticCompositionLocalOf<RikkaMotion> {
        error(
            "No RikkaMotion provided. Wrap your content in RikkaTheme { ... }",
        )
    }

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
            spatialDampingDefault = Spring.DampingRatioNoBouncy,
            spatialStiffnessDefault = Spring.StiffnessHigh,
            spatialDampingBouncy = Spring.DampingRatioNoBouncy,
            spatialStiffnessBouncy = Spring.StiffnessMedium,
            spatialDampingSnap = Spring.DampingRatioNoBouncy,
            spatialStiffnessSnap = Spring.StiffnessHigh,
            durationFast = 80,
            durationDefault = 120,
            durationSlow = 180,
            durationEnter = 150,
            durationInstant = 0,
            durationSpin = 600,
            durationPulse = 800,
            pressScaleSubtle = 0.98f,
            pressScaleBouncy = 0.95f,
            overlayScaleIn = 0.96f,
            toastScaleIn = 0.85f,
            hoverAlpha = 0.9f,
            pressAlpha = 0.75f,
        )

    /**
     * Bouncy, exaggerated animations. Fun, playful feel.
     * Good for consumer apps, games, creative tools.
     */
    fun playful(): RikkaMotion =
        RikkaMotion(
            spatialDampingDefault = Spring.DampingRatioLowBouncy,
            spatialStiffnessDefault = Spring.StiffnessLow,
            spatialDampingBouncy = Spring.DampingRatioHighBouncy,
            spatialStiffnessBouncy = Spring.StiffnessLow,
            spatialDampingSnap = Spring.DampingRatioMediumBouncy,
            spatialStiffnessSnap = Spring.StiffnessMedium,
            durationFast = 120,
            durationDefault = 200,
            durationSlow = 350,
            durationEnter = 250,
            durationInstant = 0,
            durationSpin = 1000,
            durationPulse = 1400,
            pressScaleSubtle = 0.95f,
            pressScaleBouncy = 0.88f,
            overlayScaleIn = 0.92f,
            toastScaleIn = 0.75f,
            hoverAlpha = 0.8f,
            pressAlpha = 0.65f,
        )

    /**
     * Minimal, subtle animations. Calm, understated feel.
     * Good for reading apps, accessibility-focused UIs.
     */
    fun minimal(): RikkaMotion =
        RikkaMotion(
            spatialDampingDefault = Spring.DampingRatioNoBouncy,
            spatialStiffnessDefault = Spring.StiffnessMedium,
            spatialDampingBouncy = Spring.DampingRatioNoBouncy,
            spatialStiffnessBouncy = Spring.StiffnessMediumLow,
            spatialDampingSnap = Spring.DampingRatioNoBouncy,
            spatialStiffnessSnap = Spring.StiffnessHigh,
            durationFast = 60,
            durationDefault = 100,
            durationSlow = 150,
            durationEnter = 120,
            durationInstant = 0,
            durationSpin = 700,
            durationPulse = 900,
            pressScaleSubtle = 0.99f,
            pressScaleBouncy = 0.97f,
            overlayScaleIn = 0.97f,
            toastScaleIn = 0.9f,
            hoverAlpha = 0.92f,
            pressAlpha = 0.8f,
        )
}
