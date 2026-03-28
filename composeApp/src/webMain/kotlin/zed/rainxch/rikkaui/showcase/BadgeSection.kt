@file:OptIn(ExperimentalLayoutApi::class)

package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.components.ui.card.Card

@Composable
fun BadgeSection() {
    SectionHeader(
        title = "Badge",
        description = "Small status indicators in four variants.",
    )

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    Card(modifier = Modifier.fillMaxWidth()) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
        ) {
            Badge("Default", variant = BadgeVariant.Default)
            Badge("Secondary", variant = BadgeVariant.Secondary)
            Badge("Outline", variant = BadgeVariant.Outline)
            Badge("Destructive", variant = BadgeVariant.Destructive)
        }
    }
}
