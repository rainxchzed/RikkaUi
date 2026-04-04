package zed.rainxch.rikkaui.foundation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * RikkaSpacing defines the spacing scale for consistent layout.
 *
 * Based on a 4dp base grid with proportional multipliers, following the
 * industry-standard 4pt-base-with-8pt-rhythm pattern used by Material Design 3,
 * Tailwind, Fluent 2, and Carbon.
 *
 * ### Three levels of spacing
 *
 * - **Micro** (component-internal): `xs` (4dp), `sm` (8dp), `md` (12dp)
 * - **Meso** (component-to-component): `md` (12dp), `lg` (16dp), `xl` (24dp)
 * - **Macro** (page/layout): `xxl` (32dp), `xxxl` (48dp), `huge` (64dp)
 *
 * ### Customization levels
 *
 * **1. Factory function (recommended):**
 * ```
 * val spacing = rikkaSpacing(base = 4.dp)   // default
 * val compact = rikkaSpacing(base = 3.dp)   // dense UI
 * val spacious = rikkaSpacing(base = 5.dp)  // airy layout
 * ```
 *
 * **2. Presets:**
 * ```
 * val spacing = RikkaSpacingPresets.compact()      // 3dp base — dashboards, tables
 * val spacing = RikkaSpacingPresets.comfortable()   // 4dp base — balanced (default)
 * val spacing = RikkaSpacingPresets.spacious()      // 5dp base — editorial, reading
 * ```
 *
 * **3. Full override:**
 * ```
 * val spacing = RikkaSpacing(
 *     none = 0.dp, xs = 4.dp, sm = 8.dp, md = 12.dp,
 *     lg = 16.dp, xl = 24.dp, xxl = 32.dp, xxxl = 48.dp, huge = 64.dp,
 * )
 * ```
 */
@Immutable
public data class RikkaSpacing(
    /** Explicit zero — for override resets. Use instead of bare `0.dp`. */
    val none: Dp,
    /** Extra small (4dp default) — icon-to-text gap, badge padding, tight internal. */
    val xs: Dp,
    /** Small (8dp default) — element gaps, chip padding, compact internal. */
    val sm: Dp,
    /** Medium (12dp default) — list item padding, form field element gaps. */
    val md: Dp,
    /** Large (16dp default) — THE standard value: screen margin, card padding, form field gap. */
    val lg: Dp,
    /** Extra large (24dp default) — card content padding, section internal spacing. */
    val xl: Dp,
    /** 2x extra large (32dp default) — between sections, dialog padding. */
    val xxl: Dp,
    /** 3x extra large (48dp default) — page-level spacing, hero sections. */
    val xxxl: Dp,
    /** Huge (64dp default) — max-scale layout, hero padding, desktop margins. */
    val huge: Dp,
)

public val LocalRikkaSpacing: androidx.compose.runtime.ProvidableCompositionLocal<RikkaSpacing> =
    staticCompositionLocalOf<RikkaSpacing> {
        error(
            "No RikkaSpacing provided. Wrap your content in RikkaTheme { ... }",
        )
    }

/**
 * Creates a [RikkaSpacing] from a base unit.
 *
 * All spacing steps are multiples of the base:
 * none = 0, xs = 1x, sm = 2x, md = 3x, lg = 4x, xl = 6x, xxl = 8x, xxxl = 12x, huge = 16x
 *
 * With the default base of 4dp, this produces: 0, 4, 8, 12, 16, 24, 32, 48, 64 —
 * the standard 4pt grid used by Material Design 3, Tailwind, and most multiplatform systems.
 *
 * @param base The base spacing unit. Default is 4dp (standard grid).
 */
public fun rikkaSpacing(base: Dp = 4.dp): RikkaSpacing =
    RikkaSpacing(
        none = 0.dp,
        xs = base,
        sm = base * 2,
        md = base * 3,
        lg = base * 4,
        xl = base * 6,
        xxl = base * 8,
        xxxl = base * 12,
        huge = base * 16,
    )

/**
 * Named spacing presets for common density levels.
 *
 * | Preset       | Base | lg (standard) | Feel                    |
 * |-------------|------|---------------|-------------------------|
 * | compact     | 3dp  | 12dp          | Dense: dashboards, tables |
 * | comfortable | 4dp  | 16dp          | Balanced (recommended)    |
 * | spacious    | 5dp  | 20dp          | Airy: editorial, reading  |
 */
public object RikkaSpacingPresets {
    /** Dense — for data-heavy UIs: dashboards, tables, admin panels. Base = 3dp. */
    public fun compact(): RikkaSpacing = rikkaSpacing(base = 3.dp)

    /** Balanced — the recommended default for most apps. Base = 4dp. */
    public fun comfortable(): RikkaSpacing = rikkaSpacing(base = 4.dp)

    /** Open — for content-first, editorial, reading apps. Base = 5dp. */
    public fun spacious(): RikkaSpacing = rikkaSpacing(base = 5.dp)
}
