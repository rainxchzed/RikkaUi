package zed.rainxch.rikkaui.creator.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

/**
 * Typography preview section showing heading and body text
 * with the user's selected font, matching shadcn's
 * "Designing with rhythm and hierarchy" section.
 */
@Composable
fun TypographyPreview(
    fontDisplayName: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = fontDisplayName.uppercase(),
            variant = TextVariant.Muted,
            style = RikkaTheme.typography.small,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        Text(
            text = "Designing with rhythm" +
                " and hierarchy.",
            variant = TextVariant.H2,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        Text(
            text = "A strong body style keeps long-form" +
                " content readable and balances the" +
                " visual weight of headings.",
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        Text(
            text = "Thoughtful spacing and cadence help" +
                " paragraphs scan quickly without" +
                " feeling dense.",
            variant = TextVariant.Muted,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        Button(
            text = "Sample Button",
            onClick = {},
            variant = ButtonVariant.Outline,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
