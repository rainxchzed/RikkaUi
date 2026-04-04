package zed.rainxch.rikkaui.foundation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp

/**
 * A bundled style configuration that combines shapes, spacing, motion,
 * and typography scale into one cohesive visual identity.
 *
 * ### Usage
 *
 * **1. Use a preset enum (quickest):**
 * ```
 * RikkaTheme(
 *     style = RikkaStylePreset.Nova,
 *     colors = RikkaPalettes.ZincDark,
 *     typography = rikkaTypography(myFont, scale = RikkaStylePreset.Nova.typeScale),
 * ) { ... }
 * ```
 *
 * **2. Build a custom style:**
 * ```
 * val custom = RikkaStyle(
 *     shapes = rikkaShapes(radius = 12.dp),
 *     spacing = rikkaSpacing(base = 5.dp),
 *     motion = RikkaMotionPresets.playful(),
 *     typeScale = 1.1f,
 * )
 * RikkaTheme(style = custom, ...) { ... }
 * ```
 *
 * @param shapes Corner radius scale.
 * @param spacing Spacing multiplier scale.
 * @param motion Animation token set (springs, tweens, press scales).
 * @param typeScale Proportional typography multiplier (1.0 = default).
 */
@Immutable
public data class RikkaStyle(
    val shapes: RikkaShapes,
    val spacing: RikkaSpacing,
    val motion: RikkaMotion,
    val typeScale: Float,
)

/**
 * Type-safe named style presets that bundle shapes, spacing, motion,
 * and typography scale into cohesive visual identities.
 *
 * Each entry resolves to a [RikkaStyle] via the [style] property.
 *
 * | Preset  | Radius | Base | Motion  | Scale | Feel                     |
 * |---------|--------|------|---------|-------|--------------------------|
 * | Default | 10 dp  | 4 dp | default | 1.0   | Balanced, professional   |
 * | Nova    | 4 dp   | 3 dp | snappy  | 0.9   | Sharp, dense, technical  |
 * | Vega    | 20 dp  | 5 dp | playful | 1.05  | Rounded, bouncy, fun     |
 * | Aurora  | 14 dp  | 5 dp | default | 1.1   | Spacious, large, elegant |
 * | Nebula  | 0 dp   | 3 dp | minimal | 0.85  | Square, tight, brutalist |
 *
 * ```
 * // Type-safe — compiler catches typos
 * RikkaTheme(style = RikkaStylePreset.Vega) { ... }
 *
 * // Iterate all presets in a UI picker
 * RikkaStylePreset.entries.forEach { preset ->
 *     Button(text = preset.label, onClick = { selected = preset })
 * }
 * ```
 */
public enum class RikkaStylePreset(
    /** Display label for UI pickers (e.g. "Nova"). */
    public val label: String,
) {
    /** Balanced, professional. 10dp radius, 4dp spacing, default motion, 1.0x type. */
    Default("Default"),

    /** Sharp, dense, technical. 4dp radius, 3dp spacing, snappy motion, 0.9x type. */
    Nova("Nova"),

    /** Rounded, bouncy, fun. 20dp radius, 5dp spacing, playful motion, 1.05x type. */
    Vega("Vega"),

    /** Spacious, large, elegant. 14dp radius, 5dp spacing, default motion, 1.1x type. */
    Aurora("Aurora"),

    /** Square, tight, brutalist. 0dp radius, 3dp spacing, minimal motion, 0.85x type. */
    Nebula("Nebula"),
    ;

    /** The resolved [RikkaStyle] for this preset (cached). */
    public val style: RikkaStyle by lazy {
        when (this) {
            Default -> {
                RikkaStyle(
                    shapes = rikkaShapes(),
                    spacing = RikkaSpacingPresets.comfortable(),
                    motion = RikkaMotion(),
                    typeScale = 1f,
                )
            }

            Nova -> {
                RikkaStyle(
                    shapes = rikkaShapes(radius = 4.dp),
                    spacing = RikkaSpacingPresets.compact(),
                    motion = RikkaMotionPresets.snappy(),
                    typeScale = 0.9f,
                )
            }

            Vega -> {
                RikkaStyle(
                    shapes = rikkaShapes(radius = 20.dp),
                    spacing = RikkaSpacingPresets.spacious(),
                    motion = RikkaMotionPresets.playful(),
                    typeScale = 1.05f,
                )
            }

            Aurora -> {
                RikkaStyle(
                    shapes = rikkaShapes(radius = 14.dp),
                    spacing = RikkaSpacingPresets.spacious(),
                    motion = RikkaMotion(),
                    typeScale = 1.1f,
                )
            }

            Nebula -> {
                RikkaStyle(
                    shapes = rikkaShapes(radius = 0.dp),
                    spacing = RikkaSpacingPresets.compact(),
                    motion = RikkaMotionPresets.minimal(),
                    typeScale = 0.85f,
                )
            }
        }
    }

    /** Shortcut: the typography scale for this preset. */
    public val typeScale: Float get() = style.typeScale

    /** Shortcut: the shapes for this preset. */
    public val shapes: RikkaShapes get() = style.shapes

    /** Shortcut: the spacing for this preset. */
    public val spacing: RikkaSpacing get() = style.spacing

    /** Shortcut: the motion for this preset. */
    public val motion: RikkaMotion get() = style.motion
}
