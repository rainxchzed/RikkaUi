package zed.rainxch.rikkaui.foundation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.FontResource

/**
 * Creates a [FontFamily] from font resources with all required weights.
 *
 * This is the primary way to set up fonts. Provide your font files for each
 * weight and the design system handles the rest.
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
 * ) {
 *     // All Text and Button components now use Inter
 * }
 * ```
 *
 * @param light FontResource for weight 300 (used in subtle/decorative text)
 * @param regular FontResource for weight 400 (body text, paragraphs)
 * @param medium FontResource for weight 500 (small text, labels)
 * @param semiBold FontResource for weight 600 (headings h2-h4, large text)
 * @param bold FontResource for weight 700 (emphasis, strong text)
 * @param extraBold FontResource for weight 800 (h1 headings)
 */
@Composable
public fun rememberRikkaFontFamily(
    light: FontResource,
    regular: FontResource,
    medium: FontResource,
    semiBold: FontResource,
    bold: FontResource,
    extraBold: FontResource,
): FontFamily {
    val family =
        FontFamily(
            Font(light, FontWeight.Light),
            Font(regular, FontWeight.Normal),
            Font(medium, FontWeight.Medium),
            Font(semiBold, FontWeight.SemiBold),
            Font(bold, FontWeight.Bold),
            Font(extraBold, FontWeight.ExtraBold),
        )
    return remember(family) { family }
}
