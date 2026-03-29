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
import rikkaui.composeapp.generated.resources.api_keys
import rikkaui.composeapp.generated.resources.copy_key_label
import rikkaui.composeapp.generated.resources.development
import rikkaui.composeapp.generated.resources.development_key
import rikkaui.composeapp.generated.resources.new_key
import rikkaui.composeapp.generated.resources.production
import rikkaui.composeapp.generated.resources.production_key
import rikkaui.composeapp.generated.resources.quota_used
import rikkaui.composeapp.generated.resources.rate_limited
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.alert.Alert
import zed.rainxch.rikkaui.components.ui.alert.AlertDescription
import zed.rainxch.rikkaui.components.ui.alert.AlertTitle
import zed.rainxch.rikkaui.components.ui.alert.AlertVariant
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.button.IconButton
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.card.CardContent
import zed.rainxch.rikkaui.components.ui.card.CardHeader
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.kbd.Kbd
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

@Composable
fun ApiKeyManagerExample() {
    Card {
        CardHeader {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = stringResource(Res.string.api_keys), variant = TextVariant.H4)
                Button(text = stringResource(Res.string.new_key), onClick = { }, size = ButtonSize.Sm)
            }
        }

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.xs))

        CardContent {
            KeyEntry(
                label = stringResource(Res.string.production),
                maskedKey = stringResource(Res.string.production_key),
            )
            Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))
            KeyEntry(
                label = stringResource(Res.string.development),
                maskedKey = stringResource(Res.string.development_key),
            )
        }

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))
        Separator()
        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        Alert(variant = AlertVariant.Destructive) {
            AlertTitle(text = stringResource(Res.string.rate_limited), variant = AlertVariant.Destructive)
            AlertDescription(text = stringResource(Res.string.quota_used), variant = AlertVariant.Destructive)
        }
    }
}

@Composable
private fun KeyEntry(
    label: String,
    maskedKey: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = label, variant = TextVariant.P)
        Spacer(modifier = Modifier.weight(1f))
        Kbd(text = maskedKey)
        IconButton(
            icon = RikkaIcons.Copy,
            contentDescription = stringResource(Res.string.copy_key_label, label),
            onClick = { },
        )
    }
}
