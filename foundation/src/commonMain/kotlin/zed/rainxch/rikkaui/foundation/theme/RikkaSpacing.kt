package zed.rainxch.rikkaui.components.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * RikkaSpacing defines the spacing scale for consistent layout.
 *
 * Based on a configurable base unit that generates a proportional scale.
 * Change one value and the entire app adapts — dense dashboards, spacious
 * editorial layouts, or anything in between.
 *
 * ### Customization levels
 *
 * **1. Factory function (recommended):**
 * ```
 * val spacing = rikkaSpacing(base = 4.dp)   // default
 * val compact = rikkaSpacing(base = 3.dp)   // dense UI
 * val spacious = rikkaSpacing(base = 6.dp)  // airy layout
 * ```
 *
 * **2. Presets:**
 * ```
 * val spacing = RikkaSpacingPresets.compact()
 * val spacing = RikkaSpacingPresets.comfortable()
 * val spacing = RikkaSpacingPresets.spacious()
 * ```
 *
 * **3. Full override:**
 * ```
 * val spacing = RikkaSpacing(
 *     xs = 2.dp, sm = 6.dp, md = 10.dp,
 *     lg = 14.dp, xl = 20.dp, xxl = 28.dp, xxxl = 40.dp,
 * )
 * ```
 */
@Immutable
data class RikkaSpacing(
    /** Extra small — tight padding, icon gaps. */
    val xs: Dp,
    /** Small — inline spacing, compact padding. */
    val sm: Dp,
    /** Medium — default gap between related elements. */
    val md: Dp,
    /** Large — section padding, card padding. */
    val lg: Dp,
    /** Extra large — between sections, generous padding. */
    val xl: Dp,
    /** 2x extra large — major section breaks. */
    val xxl: Dp,
    /** 3x extra large — page-level spacing, hero padding. */
    val xxxl: Dp,
)

val LocalRikkaSpacing =
    staticCompositionLocalOf<RikkaSpacing> {
        error(
            "No RikkaSpacing provided. Wrap your content in RikkaTheme { ... }",
        )
    }

/**
 * Creates a [RikkaSpacing] from a base unit.
 *
 * All spacing steps are multiples of the base:
 * xs = 1x, sm = 2x, md = 3x, lg = 4x, xl = 6x, xxl = 8x, xxxl = 12x
 *
 * @param base The base spacing unit. Default is 4dp (standard grid).
 */
fun rikkaSpacing(base: Dp = 4.dp): RikkaSpacing =
    RikkaSpacing(
        xs = base,
        sm = base * 2,
        md = base * 3,
        lg = base * 4,
        xl = base * 6,
        xxl = base * 8,
        xxxl = base * 12,
    )

/**
 * Default spacing scale (4dp base grid).
 *
 * Equivalent to `rikkaSpacing(base = 4.dp)`.
 */
fun defaultRikkaSpacing(): RikkaSpacing = rikkaSpacing()

/**
 * Pre-built spacing presets for common layout densities.
 *
 * ```
 * RikkaTheme(spacing = RikkaSpacingPresets.compact()) { ... }
 * ```
 */
object RikkaSpacingPresets {

    /** Dense layout (3dp base). Dashboards, data-heavy UIs. */
    fun compact(): RikkaSpacing = rikkaSpacing(base = 3.dp)

    /** Slightly more breathing room (5dp base). */
    fun comfortable(): RikkaSpacing = rikkaSpacing(base = 5.dp)

    /** Generous layout (6dp base). Editorial, marketing pages. */
    fun spacious(): RikkaSpacing = rikkaSpacing(base = 6.dp)
}
