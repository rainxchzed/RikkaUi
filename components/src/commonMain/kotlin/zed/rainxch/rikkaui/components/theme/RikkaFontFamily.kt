package zed.rainxch.rikkaui.components.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.FontResource

/**
 * RikkaFontFamily defines the font used across the entire design system.
 *
 * Instead of hardcoding a specific font, the user provides their font files
 * and RikkaUi wires them into every typography style automatically.
 *
 * When downloading the design system from RikkaUi, users receive font files
 * (Inter by default) to drop into their `composeResources/font/` directory.
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
 */
@Immutable
data class RikkaFontFamily(
    val fontFamily: FontFamily,
)

/**
 * Creates a [RikkaFontFamily] from font resources with all required weights.
 *
 * This is the primary way to set up fonts. Provide your font files for each
 * weight and the design system handles the rest.
 *
 * @param light FontResource for weight 300 (used in subtle/decorative text)
 * @param regular FontResource for weight 400 (body text, paragraphs)
 * @param medium FontResource for weight 500 (small text, labels)
 * @param semiBold FontResource for weight 600 (headings h2-h4, large text)
 * @param bold FontResource for weight 700 (emphasis, strong text)
 * @param extraBold FontResource for weight 800 (h1 headings)
 */
@Composable
fun rememberRikkaFontFamily(
    light: FontResource,
    regular: FontResource,
    medium: FontResource,
    semiBold: FontResource,
    bold: FontResource,
    extraBold: FontResource,
): RikkaFontFamily {
    val family =
        FontFamily(
            Font(light, FontWeight.Light),
            Font(regular, FontWeight.Normal),
            Font(medium, FontWeight.Medium),
            Font(semiBold, FontWeight.SemiBold),
            Font(bold, FontWeight.Bold),
            Font(extraBold, FontWeight.ExtraBold),
        )
    return remember(family) { RikkaFontFamily(fontFamily = family) }
}

/**
 * Default font family using system fonts.
 *
 * This is used when no custom fonts are provided.
 * For the best experience, use [rememberRikkaFontFamily] with Inter
 * or another font from the RikkaUi font collection.
 */
val DefaultRikkaFontFamily = RikkaFontFamily(fontFamily = FontFamily.Default)
