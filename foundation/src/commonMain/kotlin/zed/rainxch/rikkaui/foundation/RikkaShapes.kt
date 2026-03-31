package zed.rainxch.rikkaui.foundation

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * RikkaShapes defines the shape scale for the design system.
 *
 * Based on a single radius value that scales up, matching
 * shadcn/ui's `--radius` token system. Adjust one number and every
 * component's corner rounding updates across the entire app.
 *
 * ### Customization levels
 *
 * **1. Factory function (recommended):**
 * ```
 * val shapes = rikkaShapes(radius = 10.dp)  // default
 * val sharp = rikkaShapes(radius = 4.dp)    // minimal rounding
 * val round = rikkaShapes(radius = 16.dp)   // very rounded
 * val square = rikkaShapes(radius = 0.dp)   // no rounding at all
 * ```
 *
 * **2. Presets:**
 * ```
 * val shapes = RikkaShapesPresets.sharp()    // 4dp — minimal
 * val shapes = RikkaShapesPresets.rounded()  // 16dp — soft
 * val shapes = RikkaShapesPresets.pill()     // 24dp — pill-like
 * val shapes = RikkaShapesPresets.square()   // 0dp — no rounding
 * ```
 *
 * **3. Full override:**
 * ```
 * val shapes = RikkaShapes(
 *     sm = RoundedCornerShape(2.dp),
 *     md = RoundedCornerShape(4.dp),
 *     lg = RoundedCornerShape(8.dp),
 *     xl = RoundedCornerShape(12.dp),
 *     full = RoundedCornerShape(50),
 * )
 * ```
 */
@Immutable
data class RikkaShapes(
    /** Small rounding — checkboxes, badges, small inputs. */
    val sm: Shape,
    /** Medium rounding — buttons, inputs, cards. */
    val md: Shape,
    /** Large rounding — cards, dialogs, sheets. */
    val lg: Shape,
    /** Extra large rounding — large containers, hero sections. */
    val xl: Shape,
    /** Fully rounded — avatars, circular buttons, pills. */
    val full: Shape,
) {
    /** No rounding at all — sharp rectangle. Table cells, code blocks, dividers. */
    val none: Shape get() = RectangleShape
}

val LocalRikkaShapes =
    staticCompositionLocalOf<RikkaShapes> {
        error(
            "No RikkaShapes provided. Wrap your content in RikkaTheme { ... }",
        )
    }

/**
 * Creates a [RikkaShapes] from a base radius value.
 *
 * Scale: sm = radius-4, md = radius-2, lg = radius, xl = radius+4.
 * All values are clamped to 0 minimum.
 *
 * @param radius The base corner radius. Default is 10dp.
 */
fun rikkaShapes(radius: Dp = 10.dp): RikkaShapes {
    val r = radius.value
    return RikkaShapes(
        sm = RoundedCornerShape((r - 4f).coerceAtLeast(0f).dp),
        md = RoundedCornerShape((r - 2f).coerceAtLeast(0f).dp),
        lg = RoundedCornerShape(r.coerceAtLeast(0f).dp),
        xl = RoundedCornerShape((r + 4f).coerceAtLeast(0f).dp),
        full = RoundedCornerShape(50),
    )
}

/**
 * Default shape scale (10dp base radius).
 *
 * Equivalent to `rikkaShapes(radius = 10.dp)`.
 */
fun defaultRikkaShapes(baseRadius: Float = 10f): RikkaShapes = rikkaShapes(radius = baseRadius.dp)

// ─── Directional Shape Extensions ──────────────────────────

private val zeroCorner = CornerSize(0.dp)

/**
 * Returns a copy with only the **top** corners rounded (bottom zeroed).
 *
 * Useful for sheets sliding from the bottom, top app bars.
 *
 * ```
 * val topRounded = (RikkaTheme.shapes.lg as CornerBasedShape).top()
 * ```
 */
fun CornerBasedShape.top(): CornerBasedShape = copy(bottomStart = zeroCorner, bottomEnd = zeroCorner)

/**
 * Returns a copy with only the **bottom** corners rounded (top zeroed).
 *
 * Useful for sheets sliding from the top, bottom bars.
 */
fun CornerBasedShape.bottom(): CornerBasedShape = copy(topStart = zeroCorner, topEnd = zeroCorner)

/**
 * Returns a copy with only the **start** (left in LTR) corners rounded (end zeroed).
 *
 * Useful for sheets sliding from the end, navigation drawers.
 */
fun CornerBasedShape.start(): CornerBasedShape = copy(topEnd = zeroCorner, bottomEnd = zeroCorner)

/**
 * Returns a copy with only the **end** (right in LTR) corners rounded (start zeroed).
 *
 * Useful for sheets sliding from the start, side panels.
 */
fun CornerBasedShape.end(): CornerBasedShape = copy(topStart = zeroCorner, bottomStart = zeroCorner)

// ─── Shape Presets ────────────────────────────────────────

/**
 * Pre-built shape presets for common design styles.
 *
 * ```
 * RikkaTheme(shapes = RikkaShapesPresets.sharp()) { ... }
 * ```
 */
object RikkaShapesPresets {
    /** No rounding. Brutalist, geometric. */
    fun square(): RikkaShapes = rikkaShapes(radius = 0.dp)

    /** Minimal rounding. Clean, technical. */
    fun sharp(): RikkaShapes = rikkaShapes(radius = 4.dp)

    /** Soft rounding. Friendly, modern. */
    fun rounded(): RikkaShapes = rikkaShapes(radius = 16.dp)

    /** Heavy rounding. Pill-like, playful. */
    fun pill(): RikkaShapes = rikkaShapes(radius = 24.dp)

    /**
     * Samsung One UI inspired shape scale.
     *
     * Larger radii than typical Material — 12dp small elements,
     * 20dp medium containers, 26dp large cards/dialogs, 32dp sheets.
     * Creates a soft, modern feel with generous rounding.
     */
    fun oneUI(): RikkaShapes =
        RikkaShapes(
            sm = RoundedCornerShape(12.dp),
            md = RoundedCornerShape(20.dp),
            lg = RoundedCornerShape(26.dp),
            xl = RoundedCornerShape(32.dp),
            full = RoundedCornerShape(50),
        )
}
