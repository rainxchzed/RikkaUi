package zed.rainxch.rikkaui.components.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * RikkaTypography defines the type scale for the design system.
 *
 * Clean, minimal scale — no unnecessary variants.
 * All styles are derived from a [RikkaFontFamily], so changing fonts
 * updates the entire app in one place.
 */
@Immutable
data class RikkaTypography(
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val h4: TextStyle,
    val p: TextStyle,
    val lead: TextStyle,
    val large: TextStyle,
    val small: TextStyle,
    val muted: TextStyle,
)

val LocalRikkaTypography =
    staticCompositionLocalOf<RikkaTypography> {
        error("No RikkaTypography provided. Wrap your content in RikkaTheme { ... }")
    }

/**
 * Creates a [RikkaTypography] wired to the given font family.
 *
 * Every text style in the design system uses this font. Change the font
 * once here, and every Text, Button label, heading, etc. updates automatically.
 *
 * Usage:
 * ```
 * val fontFamily = rememberRikkaFontFamily(
 *     light = Res.font.inter_light,
 *     regular = Res.font.inter_regular,
 *     medium = Res.font.inter_medium,
 *     semiBold = Res.font.inter_semibold,
 *     bold = Res.font.inter_bold,
 *     extraBold = Res.font.inter_extrabold,
 * )
 *
 * RikkaTheme(
 *     typography = rikkaTypography(fontFamily),
 * ) { ... }
 * ```
 *
 * @param fontFamily The font family to use across all text styles.
 */
fun rikkaTypography(fontFamily: RikkaFontFamily = DefaultRikkaFontFamily): RikkaTypography {
    val family = fontFamily.fontFamily

    return RikkaTypography(
        h1 =
            TextStyle(
                fontFamily = family,
                fontSize = 36.sp,
                lineHeight = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-0.5).sp,
            ),
        h2 =
            TextStyle(
                fontFamily = family,
                fontSize = 30.sp,
                lineHeight = 36.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (-0.25).sp,
            ),
        h3 =
            TextStyle(
                fontFamily = family,
                fontSize = 24.sp,
                lineHeight = 32.sp,
                fontWeight = FontWeight.SemiBold,
            ),
        h4 =
            TextStyle(
                fontFamily = family,
                fontSize = 20.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.SemiBold,
            ),
        p =
            TextStyle(
                fontFamily = family,
                fontSize = 16.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.Normal,
            ),
        lead =
            TextStyle(
                fontFamily = family,
                fontSize = 20.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.Normal,
            ),
        large =
            TextStyle(
                fontFamily = family,
                fontSize = 18.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.SemiBold,
            ),
        small =
            TextStyle(
                fontFamily = family,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.Medium,
            ),
        muted =
            TextStyle(
                fontFamily = family,
                fontSize = 14.sp,
                lineHeight = 20.sp,
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
