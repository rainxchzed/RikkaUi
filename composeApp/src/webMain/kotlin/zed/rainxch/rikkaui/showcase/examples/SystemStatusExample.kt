package zed.rainxch.rikkaui.showcase.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import rikkaui.composeapp.generated.resources.Res
import rikkaui.composeapp.generated.resources.api_server
import rikkaui.composeapp.generated.resources.cdn
import rikkaui.composeapp.generated.resources.database
import rikkaui.composeapp.generated.resources.degraded
import rikkaui.composeapp.generated.resources.healthy
import rikkaui.composeapp.generated.resources.operational
import rikkaui.composeapp.generated.resources.system_status
import rikkaui.composeapp.generated.resources.system_status_label
import rikkaui.composeapp.generated.resources.system_uptime
import rikkaui.composeapp.generated.resources.uptime_text
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.card.CardContent
import zed.rainxch.rikkaui.components.ui.card.CardHeader
import zed.rainxch.rikkaui.components.ui.progress.Progress
import zed.rainxch.rikkaui.components.ui.spinner.Spinner
import zed.rainxch.rikkaui.components.ui.spinner.SpinnerSize
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme

@Composable
fun SystemStatusExample() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        label = stringResource(Res.string.system_status_label),
    ) {
        CardHeader {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = stringResource(Res.string.system_status), variant = TextVariant.H4)
                Spinner(size = SpinnerSize.Sm)
            }
        }

        CardContent {
            StatusRow(
                name = stringResource(Res.string.api_server),
                badge = stringResource(Res.string.operational),
                variant = BadgeVariant.Default,
            )

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

            StatusRow(
                name = stringResource(Res.string.database),
                badge = stringResource(Res.string.healthy),
                variant = BadgeVariant.Default,
            )

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

            StatusRow(
                name = stringResource(Res.string.cdn),
                badge = stringResource(Res.string.degraded),
                variant = BadgeVariant.Secondary,
            )

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.md))

            Text(
                text = stringResource(Res.string.uptime_text),
                variant = TextVariant.Small,
                color = RikkaTheme.colors.onMuted,
            )

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.xs))

            Progress(
                progress = 0.87f,
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(Res.string.system_uptime),
            )
        }
    }
}

@Composable
private fun StatusRow(
    name: String,
    badge: String,
    variant: BadgeVariant,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = name, variant = TextVariant.P)
        Badge(text = badge, variant = variant)
    }
}
