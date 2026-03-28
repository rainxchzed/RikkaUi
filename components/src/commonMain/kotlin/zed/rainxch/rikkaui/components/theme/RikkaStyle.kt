package zed.rainxch.rikkaui.components.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp

/**
 * A bundled style configuration that combines shapes, spacing, motion,
 * and typography scale into one cohesive visual identity.
 *
 * Style presets are the easiest way to dramatically change the look
 * and feel of your entire app with a single value — like shadcn/ui's
 * named themes (New York, Default) but with more dimensions.
 *
 * ### Usage
 *
 * **1. Use a named preset (quickest):**
 * ```
 * val style = RikkaStylePresets.nova()
 * RikkaTheme(
 *     shapes = style.shapes,
 *     spacing = style.spacing,
 *     motion = style.motion,
 *     typography = rikkaTypography(fontFamily, scale = style.typeScale),
 * ) { ... }
 * ```
 *
 * **2. Build your own:**
 * ```
 * val custom = RikkaStyle(
 *     shapes = rikkaShapes(radius = 12.dp),
 *     spacing = rikkaSpacing(base = 5.dp),
 *     motion = RikkaMotionPresets.playful(),
 *     typeScale = 1.1f,
 * )
 * ```
 *
 * @param shapes Corner radius scale.
 * @param spacing Spacing multiplier scale.
 * @param motion Animation token set (springs, tweens, press scales).
 * @param typeScale Proportional typography multiplier (1.0 = default).
 */
@Immutable
data class RikkaStyle(
    val shapes: RikkaShapes,
    val spacing: RikkaSpacing,
    val motion: RikkaMotion,
    val typeScale: Float,
)

/**
 * Pre-built named style presets that bundle shapes, spacing, motion,
 * and typography scale into cohesive visual identities.
 *
 * Each preset creates a distinct personality for the UI:
 *
 * | Preset  | Radius | Base | Motion  | Scale | Feel                     |
 * |---------|--------|------|---------|-------|--------------------------|
 * | default | 10 dp  | 4 dp | default | 1.0   | Balanced, professional   |
 * | nova    | 4 dp   | 3 dp | snappy  | 0.9   | Sharp, dense, technical  |
 * | vega    | 20 dp  | 5 dp | playful | 1.05  | Rounded, bouncy, fun     |
 * | aurora  | 14 dp  | 5 dp | default | 1.1   | Spacious, large, elegant |
 * | nebula  | 0 dp   | 3 dp | minimal | 0.85  | Square, tight, brutalist |
 *
 * ```
 * val style = RikkaStylePresets.vega()
 * RikkaTheme(
 *     shapes = style.shapes,
 *     spacing = style.spacing,
 *     motion = style.motion,
 *     typography = rikkaTypography(myFont, scale = style.typeScale),
 * ) { ... }
 * ```
 */
object RikkaStylePresets {

    /** All available preset names. */
    val names: List<String> = listOf(
        "Default", "Nova", "Vega", "Aurora", "Nebula",
    )

    /**
     * Balanced, professional defaults. 10dp radius, 4dp spacing,
     * medium-bouncy springs, 1.0x type scale.
     */
    fun default(): RikkaStyle =
        RikkaStyle(
            shapes = rikkaShapes(),
            spacing = rikkaSpacing(),
            motion = RikkaMotion(),
            typeScale = 1f,
        )

    /**
     * Sharp, dense, technical. 4dp radius, 3dp spacing,
     * snappy no-bounce animations, 0.9x compact type.
     * Great for dashboards and data-heavy UIs.
     */
    fun nova(): RikkaStyle =
        RikkaStyle(
            shapes = rikkaShapes(radius = 4.dp),
            spacing = rikkaSpacing(base = 3.dp),
            motion = RikkaMotionPresets.snappy(),
            typeScale = 0.9f,
        )

    /**
     * Rounded, bouncy, fun. 20dp radius, 5dp spacing,
     * playful bouncy animations, 1.05x type.
     * Great for consumer apps, creative tools.
     */
    fun vega(): RikkaStyle =
        RikkaStyle(
            shapes = rikkaShapes(radius = 20.dp),
            spacing = rikkaSpacing(base = 5.dp),
            motion = RikkaMotionPresets.playful(),
            typeScale = 1.05f,
        )

    /**
     * Spacious, large, elegant. 14dp radius, 5dp spacing,
     * default balanced motion, 1.1x large type.
     * Great for editorial content, marketing pages.
     */
    fun aurora(): RikkaStyle =
        RikkaStyle(
            shapes = rikkaShapes(radius = 14.dp),
            spacing = rikkaSpacing(base = 5.dp),
            motion = RikkaMotion(),
            typeScale = 1.1f,
        )

    /**
     * Square, tight, brutalist. 0dp radius, 3dp spacing,
     * minimal subtle animations, 0.85x compact type.
     * Great for technical tools, brutalist aesthetics.
     */
    fun nebula(): RikkaStyle =
        RikkaStyle(
            shapes = rikkaShapes(radius = 0.dp),
            spacing = rikkaSpacing(base = 3.dp),
            motion = RikkaMotionPresets.minimal(),
            typeScale = 0.85f,
        )

    /**
     * Resolves a preset by name (case-sensitive).
     * Returns [default] for unrecognized names.
     *
     * ```
     * val style = RikkaStylePresets.fromName("Nova")
     * ```
     */
    fun fromName(name: String): RikkaStyle =
        when (name) {
            "Nova" -> nova()
            "Vega" -> vega()
            "Aurora" -> aurora()
            "Nebula" -> nebula()
            else -> default()
        }
}
