package zed.rainxch.rikkaui.components.ui.text

import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import zed.rainxch.rikkaui.foundation.LocalContentColor
import zed.rainxch.rikkaui.foundation.LocalTextStyle
import zed.rainxch.rikkaui.foundation.RikkaTheme

/** Typography variant mapping to a theme text style. */
enum class TextVariant {
    /** Largest heading */
    H1,

    /** Second-level heading */
    H2,

    /** Third-level heading */
    H3,

    /** Fourth-level heading */
    H4,

    /** Body paragraph (default) */
    P,

    /** Lead paragraph, muted color */
    Lead,

    /** Large emphasis text */
    Large,

    /** Small text */
    Small,

    /** Muted/secondary text */
    Muted,
}

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    variant: TextVariant = TextVariant.P,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    selectable: Boolean = false,
    style: TextStyle = TextStyle.Default,
) {
    val baseStyle = variantStyle(variant)
    val parentTextStyle = LocalTextStyle.current
    val contentColor = LocalContentColor.current
    val resolvedColor =
        when {
            color != Color.Unspecified -> color
            contentColor != Color.Unspecified -> contentColor
            else -> variantColor(variant)
        }

    val textAlignStyle =
        if (textAlign != null) {
            TextStyle(textAlign = textAlign)
        } else {
            TextStyle.Default
        }

    // Merge order: parent default → variant override → explicit style → color → alignment.
    // Parent (Card, Button) provides a default text style. The variant (H1, P, etc.)
    // overrides it so headings inside cards still render at heading size.
    // Explicit `style` param wins over everything.
    val mergedStyle =
        parentTextStyle
            .merge(baseStyle)
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

    val textContent = @Composable {
        BasicText(
            text = text,
            modifier = semanticsModifier,
            style = mergedStyle,
            overflow = overflow,
            maxLines = maxLines,
            minLines = minLines,
        )
    }

    if (selectable) {
        SelectionContainer { textContent() }
    } else {
        textContent()
    }
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
        TextVariant.Lead -> RikkaTheme.colors.onMuted
        TextVariant.Muted -> RikkaTheme.colors.onMuted
        else -> RikkaTheme.colors.onBackground
    }
