package zed.rainxch.rikkaui.showcase.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.card.CardContent
import zed.rainxch.rikkaui.components.ui.card.CardHeader
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.table.Table
import zed.rainxch.rikkaui.components.ui.table.TableCell
import zed.rainxch.rikkaui.components.ui.table.TableHeader
import zed.rainxch.rikkaui.components.ui.table.TableHeaderCell
import zed.rainxch.rikkaui.components.ui.table.TableRow
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

/**
 * A weather dashboard card demonstrating Card, Badge, Table, Separator,
 * and Text components in a realistic weather information layout.
 */
@Composable
fun WeatherDashboardExample() {
    Card(
        label = "Weather dashboard",
    ) {
        // ─── Header ──────────────────────────────────────
        CardHeader {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Weather",
                    variant = TextVariant.H4,
                )
                Badge(
                    text = "Live",
                    variant = BadgeVariant.Default,
                )
            }
        }

        // ─── Content ─────────────────────────────────────
        CardContent {
            Column(
                verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
            ) {
                // Temperature display
                Text(
                    text = "24\u00B0C",
                    variant = TextVariant.H1,
                )
                Text(
                    text = "Partly Cloudy",
                    variant = TextVariant.Muted,
                )

                // Weather stats row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    WeatherStat(label = "Humidity", value = "65%")
                    WeatherStat(label = "Wind", value = "12 km/h")
                    WeatherStat(label = "UV Index", value = "4")
                }

                Separator()

                // 3-day forecast table
                Text(
                    text = "3-Day Forecast",
                    variant = TextVariant.Small,
                )

                Table {
                    TableHeader {
                        TableHeaderCell(
                            text = "Day",
                            modifier = Modifier.weight(1f),
                        )
                        TableHeaderCell(
                            text = "Temp",
                            modifier = Modifier.weight(1f),
                        )
                        TableHeaderCell(
                            text = "Condition",
                            modifier = Modifier.weight(1f),
                        )
                    }
                    TableRow {
                        TableCell(
                            text = "Mon",
                            modifier = Modifier.weight(1f),
                        )
                        TableCell(
                            text = "26\u00B0C",
                            modifier = Modifier.weight(1f),
                        )
                        TableCell(
                            text = "Sunny",
                            modifier = Modifier.weight(1f),
                        )
                    }
                    TableRow {
                        TableCell(
                            text = "Tue",
                            modifier = Modifier.weight(1f),
                        )
                        TableCell(
                            text = "22\u00B0C",
                            modifier = Modifier.weight(1f),
                        )
                        TableCell(
                            text = "Cloudy",
                            modifier = Modifier.weight(1f),
                        )
                    }
                    TableRow {
                        TableCell(
                            text = "Wed",
                            modifier = Modifier.weight(1f),
                        )
                        TableCell(
                            text = "19\u00B0C",
                            modifier = Modifier.weight(1f),
                        )
                        TableCell(
                            text = "Rain",
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }
        }
    }
}

/**
 * A small weather statistic display with a muted label and a value.
 */
@Composable
private fun WeatherStat(
    label: String,
    value: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
    ) {
        Text(
            text = label,
            variant = TextVariant.Muted,
        )
        Text(
            text = value,
            variant = TextVariant.Large,
        )
    }
}
