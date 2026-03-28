package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.resources.stringResource
import rikkaui.composeapp.generated.resources.Res
import rikkaui.composeapp.generated.resources.built_with_compose
import rikkaui.composeapp.generated.resources.footer_tagline
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

@Composable
fun FooterSection() {
    Separator()

    Spacer(Modifier.height(RikkaTheme.spacing.xl))

    Text(
        text = stringResource(Res.string.built_with_compose),
        variant = TextVariant.Muted,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )

    Spacer(Modifier.height(RikkaTheme.spacing.xs))

    Text(
        text = stringResource(Res.string.footer_tagline),
        variant = TextVariant.Small,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
        color = RikkaTheme.colors.primary,
    )

    Spacer(Modifier.height(RikkaTheme.spacing.xxxl))
}
