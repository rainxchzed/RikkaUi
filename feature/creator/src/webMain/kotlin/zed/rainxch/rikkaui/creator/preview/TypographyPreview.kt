package zed.rainxch.rikkaui.creator.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.creator.generated.resources.Res
import rikkaui.feature.creator.generated.resources.*
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
            text = stringResource(Res.string.typography_heading),
            variant = TextVariant.H2,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        Text(
            text = stringResource(Res.string.typography_body),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        Text(
            text = stringResource(Res.string.typography_muted),
            variant = TextVariant.Muted,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        Button(
            text = stringResource(Res.string.typography_sample_button),
            onClick = {},
            variant = ButtonVariant.Outline,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
