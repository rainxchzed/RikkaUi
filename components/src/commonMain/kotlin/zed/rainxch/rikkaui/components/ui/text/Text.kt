package zed.rainxch.rikkaui.components.ui.text

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import zed.rainxch.rikkaui.components.theme.RikkaTheme

/**
 * Typography variant that maps to a pre-defined text style from the theme.
 */
enum class TextVariant {
    H1,
    H2,
    H3,
    H4,
    P,
    Lead,
    Large,
    Small,
    Muted,
}

/**
 * Text component for the RikkaUi design system.
 *
 * A drop-in replacement for Material3's Text — uses the Rikka theme
 * tokens for styling and requires no Material dependency.
 *
 * Usage:
 * ```
 * Text("Hello world")
 * Text("Page title", variant = TextVariant.H1)
 * Text("Subtle info", variant = TextVariant.Muted)
 * Text("Custom", color = RikkaTheme.colors.destructive)
 * ```
 *
 * @param text The text to display.
 * @param modifier Modifier for layout and decoration.
 * @param variant Typography variant — maps to a pre-defined style from the theme.
 * @param color Override color. If [Color.Unspecified], uses the variant's default color.
 * @param textAlign Text alignment.
 * @param overflow How to handle text overflow.
 * @param maxLines Maximum number of lines.
 * @param style Override style. Merged on top of the variant style.
 */
@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    variant: TextVariant = TextVariant.P,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = TextStyle.Default,
) {
    val baseStyle = variantStyle(variant)
    val resolvedColor = if (color != Color.Unspecified) color else variantColor(variant)

    val textAlignStyle =
        if (textAlign != null) {
            TextStyle(textAlign = textAlign)
        } else {
            TextStyle.Default
        }

    val mergedStyle =
        baseStyle
            .merge(style)
            .merge(
                TextStyle(color = resolvedColor),
            ).merge(textAlignStyle)

    // Heading variants get accessibility heading semantics,
    // enabling screen reader heading navigation (swipe up/down on TalkBack,
    // rotor on VoiceOver).
    val isHeading = variant in setOf(TextVariant.H1, TextVariant.H2, TextVariant.H3, TextVariant.H4)
    val semanticsModifier =
        if (isHeading) {
            modifier.semantics { heading() }
        } else {
            modifier
        }

    BasicText(
        text = text,
        modifier = semanticsModifier,
        style = mergedStyle,
        overflow = overflow,
        maxLines = maxLines,
    )
}

@Composable
private fun variantStyle(variant: TextVariant): TextStyle =
    when (variant) {
        TextVariant.H1 -> RikkaTheme.typography.h1
        TextVariant.H2 -> RikkaTheme.typography.h2
        TextVariant.H3 -> RikkaTheme.typography.h3
        TextVariant.H4 -> RikkaTheme.typography.h4
        TextVariant.P -> RikkaTheme.typography.p
        TextVariant.Lead -> RikkaTheme.typography.lead
        TextVariant.Large -> RikkaTheme.typography.large
        TextVariant.Small -> RikkaTheme.typography.small
        TextVariant.Muted -> RikkaTheme.typography.muted
    }

@Composable
private fun variantColor(variant: TextVariant): Color =
    when (variant) {
        TextVariant.Lead -> RikkaTheme.colors.mutedForeground
        TextVariant.Muted -> RikkaTheme.colors.mutedForeground
        else -> RikkaTheme.colors.foreground
    }
