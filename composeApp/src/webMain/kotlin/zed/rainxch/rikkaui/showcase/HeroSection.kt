@file:OptIn(ExperimentalLayoutApi::class)

package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.resources.stringResource
import rikkaui.composeapp.generated.resources.Res
import rikkaui.composeapp.generated.resources.app_name
import rikkaui.composeapp.generated.resources.get_started
import rikkaui.composeapp.generated.resources.github
import rikkaui.composeapp.generated.resources.hero_description
import rikkaui.composeapp.generated.resources.hero_subtitle
import rikkaui.composeapp.generated.resources.preview
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonAnimation
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

@Composable
fun HeroSection(onGetStarted: () -> Unit = {}) {
    val uriHandler = LocalUriHandler.current
    Spacer(Modifier.height(RikkaTheme.spacing.xxxl))

    Badge(
        text = stringResource(Res.string.preview),
        variant = BadgeVariant.Outline,
    )

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    Text(
        text = stringResource(Res.string.app_name),
        variant = TextVariant.H1,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )

    Spacer(Modifier.height(RikkaTheme.spacing.sm))

    Text(
        text = stringResource(Res.string.hero_subtitle),
        variant = TextVariant.Lead,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )

    Spacer(Modifier.height(RikkaTheme.spacing.sm))

    Text(
        text = stringResource(Res.string.hero_description),
        variant = TextVariant.Muted,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )

    Spacer(Modifier.height(RikkaTheme.spacing.xl))

    FlowRow(
        horizontalArrangement =
            Arrangement.spacedBy(
                RikkaTheme.spacing.sm,
                Alignment.CenterHorizontally,
            ),
        verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Button(
            text = stringResource(Res.string.get_started),
            onClick = onGetStarted,
            animation = ButtonAnimation.Bounce,
        )
        Button(
            text = stringResource(Res.string.github),
            onClick = {
                uriHandler.openUri("https://github.com/rainxchzed/RikkaUi")
            },
            variant = ButtonVariant.Outline,
        )
    }

    Spacer(Modifier.height(RikkaTheme.spacing.xxxl))
}
