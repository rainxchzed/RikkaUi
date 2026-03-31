package zed.rainxch.rikkaui.foundation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * RikkaTypography defines the type scale for the design system.
 *
 * Clean, minimal 9-level scale. Every style is configurable — change the
 * font, adjust individual sizes, or scale everything proportionally.
 *
 * ### Customization levels
 *
 * **1. Font only (quickest):**
 * ```
 * val typography = rikkaTypography(fontFamily = myFont)
 * ```
 *
 * **2. Proportional scale (one number):**
 * ```
 * val large = rikkaTypography(fontFamily = myFont, scale = 1.15f)
 * val compact = rikkaTypography(fontFamily = myFont, scale = 0.85f)
 * ```
 *
 * **3. Individual size overrides:**
 * ```
 * val typography = rikkaTypography(
 *     fontFamily = myFont,
 *     h1Size = 48.sp,
 *     pSize = 18.sp,
 *     smallSize = 12.sp,
 * )
 * ```
 *
 * **4. Full override:**
 * ```
 * val typography = RikkaTypography(
 *     h1 = TextStyle(fontSize = 48.sp, ...),
 *     h2 = TextStyle(fontSize = 36.sp, ...),
 *     ...
 * )
 * ```
 */
@Immutable
data class RikkaTypography(
    /** Display heading. 36sp ExtraBold by default. */
    val h1: TextStyle,
    /** Section heading. 30sp SemiBold by default. */
    val h2: TextStyle,
    /** Subsection heading. 24sp SemiBold by default. */
    val h3: TextStyle,
    /** Card/group heading. 20sp SemiBold by default. */
    val h4: TextStyle,
    /** Body text. 16sp Normal by default. */
    val p: TextStyle,
    /** Lead paragraph. 20sp Normal by default. */
    val lead: TextStyle,
    /** Large emphasis text. 18sp SemiBold by default. */
    val large: TextStyle,
    /** Small text, labels. 14sp Medium by default. */
    val small: TextStyle,
    /** Muted/helper text. 14sp Normal by default. */
    val muted: TextStyle,
)

val LocalRikkaTypography =
    staticCompositionLocalOf<RikkaTypography> {
        error(
            "No RikkaTypography provided. " +
                "Wrap your content in RikkaTheme { ... }",
        )
    }

/**
 * Composition local for the current default text style.
 *
 * Containers like Button provide this so their children (Text, etc.)
 * inherit the correct typography without explicit parameters.
 * When [TextStyle.Default], the Text component falls back to its
 * variant-based style resolution.
 */
val LocalTextStyle =
    staticCompositionLocalOf { TextStyle.Default }

/**
 * Creates a [RikkaTypography] with full customization.
 *
 * @param fontFamily Font family for all styles.
 * @param scale Proportional multiplier applied to all font sizes.
 *   1.0 = default, 0.85 = compact, 1.15 = large.
 * @param h1Size Override H1 font size (before scale).
 * @param h2Size Override H2 font size (before scale).
 * @param h3Size Override H3 font size (before scale).
 * @param h4Size Override H4 font size (before scale).
 * @param pSize Override body (P) font size (before scale).
 * @param leadSize Override lead paragraph font size (before scale).
 * @param largeSize Override large text font size (before scale).
 * @param smallSize Override small text font size (before scale).
 * @param mutedSize Override muted text font size (before scale).
 */
@Suppress("LongParameterList")
fun rikkaTypography(
    fontFamily: RikkaFontFamily = DefaultRikkaFontFamily,
    scale: Float = 1f,
    h1Size: TextUnit = 36.sp,
    h2Size: TextUnit = 30.sp,
    h3Size: TextUnit = 24.sp,
    h4Size: TextUnit = 20.sp,
    pSize: TextUnit = 16.sp,
    leadSize: TextUnit = 20.sp,
    largeSize: TextUnit = 18.sp,
    smallSize: TextUnit = 14.sp,
    mutedSize: TextUnit = 14.sp,
): RikkaTypography {
    val family = fontFamily.fontFamily

    return RikkaTypography(
        h1 =
            TextStyle(
                fontFamily = family,
                fontSize = h1Size * scale,
                lineHeight = h1Size * scale * 1.11f,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-0.5).sp,
            ),
        h2 =
            TextStyle(
                fontFamily = family,
                fontSize = h2Size * scale,
                lineHeight = h2Size * scale * 1.2f,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (-0.25).sp,
            ),
        h3 =
            TextStyle(
                fontFamily = family,
                fontSize = h3Size * scale,
                lineHeight = h3Size * scale * 1.33f,
                fontWeight = FontWeight.SemiBold,
            ),
        h4 =
            TextStyle(
                fontFamily = family,
                fontSize = h4Size * scale,
                lineHeight = h4Size * scale * 1.4f,
                fontWeight = FontWeight.SemiBold,
            ),
        p =
            TextStyle(
                fontFamily = family,
                fontSize = pSize * scale,
                lineHeight = pSize * scale * 1.75f,
                fontWeight = FontWeight.Normal,
            ),
        lead =
            TextStyle(
                fontFamily = family,
                fontSize = leadSize * scale,
                lineHeight = leadSize * scale * 1.4f,
                fontWeight = FontWeight.Normal,
            ),
        large =
            TextStyle(
                fontFamily = family,
                fontSize = largeSize * scale,
                lineHeight = largeSize * scale * 1.56f,
                fontWeight = FontWeight.SemiBold,
            ),
        small =
            TextStyle(
                fontFamily = family,
                fontSize = smallSize * scale,
                lineHeight = smallSize * scale * 1.43f,
                fontWeight = FontWeight.Medium,
            ),
        muted =
            TextStyle(
                fontFamily = family,
                fontSize = mutedSize * scale,
                lineHeight = mutedSize * scale * 1.43f,
                fontWeight = FontWeight.Normal,
            ),
    )
}

/**
 * Creates a default [RikkaTypography] using system fonts.
 *
 * Prefer [rikkaTypography] with a [RikkaFontFamily] for a polished look.
 */
fun defaultRikkaTypography(): RikkaTypography = rikkaTypography()

// ─── Emphasis Extensions ───────────────────────────────────

/**
 * Returns an emphasized version of this text style (SemiBold weight).
 *
 * Use when you need the same size but heavier weight for emphasis,
 * without jumping to a heading level.
 *
 * ```
 * Text(
 *     text = "Important note",
 *     style = RikkaTheme.typography.p.emphasized(),
 * )
 * ```
 */
fun TextStyle.emphasized(): TextStyle = copy(fontWeight = FontWeight.SemiBold)

/**
 * Returns a strong version of this text style (Bold weight).
 *
 * Stronger emphasis than [emphasized]. Use sparingly.
 *
 * ```
 * Text(
 *     text = "Critical warning",
 *     style = RikkaTheme.typography.p.strong(),
 * )
 * ```
 */
fun TextStyle.strong(): TextStyle = copy(fontWeight = FontWeight.Bold)
