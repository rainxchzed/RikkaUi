@file:OptIn(ExperimentalLayoutApi::class)

package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.avatar.Avatar
import zed.rainxch.rikkaui.components.ui.avatar.AvatarSize
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.kbd.Kbd
import zed.rainxch.rikkaui.components.ui.skeleton.Skeleton
import zed.rainxch.rikkaui.components.ui.spinner.Spinner
import zed.rainxch.rikkaui.components.ui.spinner.SpinnerSize
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

@Composable
fun AvatarSection() {
    SectionHeader(
        title = "Avatar, Kbd, Skeleton & Spinner",
        description = "User representations, keyboard hints, and loading states.",
    )

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    Card(modifier = Modifier.fillMaxWidth()) {
        // Avatar
        Text(text = "Avatar", variant = TextVariant.Small)
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
        ) {
            Avatar(fallback = "JD", size = AvatarSize.Sm)
            Avatar(fallback = "AB", size = AvatarSize.Default)
            Avatar(fallback = "RK", size = AvatarSize.Lg)
        }

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        // Kbd
        Text(text = "Kbd", variant = TextVariant.Small)
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
        ) {
            Kbd("\u2318K")
            Kbd("Ctrl+C")
            Kbd("Shift+Enter")
            Kbd("Esc")
        }

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        // Skeleton
        Text(text = "Skeleton", variant = TextVariant.Small)
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        Column(
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
        ) {
            Skeleton(modifier = Modifier.size(width = 200.dp, height = 12.dp))
            Skeleton(modifier = Modifier.size(width = 150.dp, height = 12.dp))
            Skeleton(modifier = Modifier.size(48.dp).clip(CircleShape))
        }

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        // Spinner
        Text(text = "Spinner", variant = TextVariant.Small)
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
        ) {
            Spinner(size = SpinnerSize.Sm)
            Spinner(size = SpinnerSize.Default)
            Spinner(size = SpinnerSize.Lg)
        }
    }
}
