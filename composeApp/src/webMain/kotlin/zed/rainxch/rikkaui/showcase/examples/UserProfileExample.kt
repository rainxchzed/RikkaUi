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
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.avatar.Avatar
import zed.rainxch.rikkaui.components.ui.avatar.AvatarSize
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.toggle.Toggle

/**
 * User profile card with avatar, contact info, and preference toggles.
 *
 * Demonstrates [Card], [Avatar], [Toggle], [Separator], [Button],
 * and [Text] composed together in a profile settings layout.
 */
@Composable
fun UserProfileExample() {
    var notifications by remember { mutableStateOf(true) }
    var darkMode by remember { mutableStateOf(false) }
    var newsletter by remember { mutableStateOf(true) }

    Card {
        // ─── Avatar + Identity ────────────────────────────
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
        ) {
            Avatar(
                fallback = "JD",
                size = AvatarSize.Lg,
            )
            Text(
                text = "Jane Doe",
                variant = TextVariant.H4,
                textAlign = TextAlign.Center,
            )
            Text(
                text = "jane@example.com",
                variant = TextVariant.Muted,
                textAlign = TextAlign.Center,
            )
        }

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))
        Separator()
        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        // ─── Preference toggles ───────────────────────────
        PreferenceRow(
            label = "Notifications",
            checked = notifications,
            onCheckedChange = { notifications = it },
        )
        PreferenceRow(
            label = "Dark Mode",
            checked = darkMode,
            onCheckedChange = { darkMode = it },
        )
        PreferenceRow(
            label = "Newsletter",
            checked = newsletter,
            onCheckedChange = { newsletter = it },
        )

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        // ─── Edit Profile button ──────────────────────────
        Button(
            text = "Edit Profile",
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
