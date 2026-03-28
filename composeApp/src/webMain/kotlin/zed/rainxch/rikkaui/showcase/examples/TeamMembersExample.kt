package zed.rainxch.rikkaui.showcase.examples

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.avatar.Avatar
import zed.rainxch.rikkaui.components.ui.avatar.AvatarSize
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

/**
 * Team members invitation card example for the showcase mosaic grid.
 *
 * Demonstrates Card, Avatar, Text, and Button working together
 * in a realistic "invite team" pattern inspired by shadcn/ui.
 */
@Composable
fun TeamMembersExample() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        label = "Team members invitation card",
    ) {
        // Overlapping avatars row
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Avatar(
                fallback = "JD",
                size = AvatarSize.Default,
                modifier =
                    Modifier
                        .offset(x = (-28).dp)
                        .zIndex(3f),
            )
            Avatar(
                fallback = "AB",
                size = AvatarSize.Default,
                modifier =
                    Modifier
                        .zIndex(2f),
            )
            Avatar(
                fallback = "RK",
                size = AvatarSize.Default,
                modifier =
                    Modifier
                        .offset(x = 28.dp)
                        .zIndex(1f),
            )
        }

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        Text(
            text = "No Team Members",
            variant = TextVariant.H4,
            modifier = Modifier.fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        )

        Text(
            text = "Invite your team to collaborate on this project.",
            variant = TextVariant.Muted,
            modifier = Modifier.fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        Button(
            text = "+ Invite Members",
            onClick = { },
            variant = ButtonVariant.Outline,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
