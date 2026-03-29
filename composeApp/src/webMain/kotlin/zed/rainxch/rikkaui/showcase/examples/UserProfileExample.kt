package zed.rainxch.rikkaui.showcase.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.resources.stringResource
import rikkaui.composeapp.generated.resources.Res
import rikkaui.composeapp.generated.resources.dark_mode_preference
import rikkaui.composeapp.generated.resources.edit_profile
import rikkaui.composeapp.generated.resources.newsletter
import rikkaui.composeapp.generated.resources.notifications
import rikkaui.composeapp.generated.resources.user_email
import rikkaui.composeapp.generated.resources.user_name
import zed.rainxch.rikkaui.components.ui.avatar.Avatar
import zed.rainxch.rikkaui.components.ui.avatar.AvatarSize
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.toggle.Toggle
import zed.rainxch.rikkaui.foundation.RikkaTheme

@Composable
fun UserProfileExample() {
    var notifications by remember { mutableStateOf(true) }
    var darkMode by remember { mutableStateOf(false) }
    var newsletter by remember { mutableStateOf(true) }

    Card {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
        ) {
            Avatar(fallback = "JD", size = AvatarSize.Lg)
            Text(
                text = stringResource(Res.string.user_name),
                variant = TextVariant.H4,
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(Res.string.user_email),
                variant = TextVariant.Muted,
                textAlign = TextAlign.Center,
            )
        }

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))
        Separator()
        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        PreferenceRow(
            label = stringResource(Res.string.notifications),
            checked = notifications,
            onCheckedChange = { notifications = it },
        )
        PreferenceRow(
            label = stringResource(Res.string.dark_mode_preference),
            checked = darkMode,
            onCheckedChange = { darkMode = it },
        )
        PreferenceRow(
            label = stringResource(Res.string.newsletter),
            checked = newsletter,
            onCheckedChange = { newsletter = it },
        )

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        Button(
            text = stringResource(Res.string.edit_profile),
            onClick = { },
            variant = ButtonVariant.Outline,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun PreferenceRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = label, variant = TextVariant.P)
        Toggle(
            checked = checked,
            onCheckedChange = onCheckedChange,
            label = "$label toggle",
        )
    }
}
