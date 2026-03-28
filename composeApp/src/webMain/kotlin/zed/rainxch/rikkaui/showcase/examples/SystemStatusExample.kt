package zed.rainxch.rikkaui.showcase.examples

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
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

/**
 * System status monitor card example for the showcase mosaic grid.
 *
 * Demonstrates Card, Spinner, Badge, colored Box indicators,
 * Progress bar, and Text in a service health dashboard pattern.
 */
@Composable
fun SystemStatusExample() {
    val greenDot = Color(0xFF22C55E)
    val yellowDot = Color(0xFFFACC15)

    Card(
        modifier = Modifier.fillMaxWidth(),
        label = "System status monitor",
    ) {
        CardHeader {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "System Status",
                    variant = TextVariant.H4,
                )
                Spinner(size = SpinnerSize.Sm)
            }
        }

        CardContent {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier =
                        Modifier
                            .size(8.dp)
                            .background(greenDot, CircleShape),
                )
                Text(
                    text = "API Server",
                    variant = TextVariant.P,
                    modifier = Modifier.weight(1f),
                )
                Badge(text = "Operational", variant = BadgeVariant.Default)
            }

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.xs))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier =
                        Modifier
                            .size(8.dp)
                            .background(greenDot, CircleShape),
                )
                Text(
                    text = "Database",
                    variant = TextVariant.P,
                    modifier = Modifier.weight(1f),
                )
                Badge(text = "Healthy", variant = BadgeVariant.Default)
            }

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.xs))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier =
                        Modifier
                            .size(8.dp)
                            .background(yellowDot, CircleShape),
                )
                Text(
                    text = "CDN",
                    variant = TextVariant.P,
                    modifier = Modifier.weight(1f),
                )
                Badge(text = "Degraded", variant = BadgeVariant.Secondary)
            }

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.md))

            Text(
                text = "Uptime: 99.87%",
                variant = TextVariant.Small,
                color = RikkaTheme.colors.mutedForeground,
            )

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.xs))

            Progress(
                progress = 0.87f,
                modifier = Modifier.fillMaxWidth(),
                label = "System uptime",
            )
        }
    }
}
