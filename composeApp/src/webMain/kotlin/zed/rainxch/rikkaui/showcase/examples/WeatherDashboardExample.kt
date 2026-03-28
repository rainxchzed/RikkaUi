package zed.rainxch.rikkaui.showcase.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import rikkaui.composeapp.generated.resources.Res
import rikkaui.composeapp.generated.resources.cloudy
import rikkaui.composeapp.generated.resources.condition_column
import rikkaui.composeapp.generated.resources.day_column
import rikkaui.composeapp.generated.resources.humidity
import rikkaui.composeapp.generated.resources.humidity_value
import rikkaui.composeapp.generated.resources.live
import rikkaui.composeapp.generated.resources.monday
import rikkaui.composeapp.generated.resources.monday_temp
import rikkaui.composeapp.generated.resources.partly_cloudy
import rikkaui.composeapp.generated.resources.rain
import rikkaui.composeapp.generated.resources.sunny
import rikkaui.composeapp.generated.resources.temp_column
import rikkaui.composeapp.generated.resources.temperature
import rikkaui.composeapp.generated.resources.three_day_forecast
import rikkaui.composeapp.generated.resources.tuesday
import rikkaui.composeapp.generated.resources.tuesday_temp
import rikkaui.composeapp.generated.resources.uv_index
import rikkaui.composeapp.generated.resources.uv_index_value
import rikkaui.composeapp.generated.resources.weather
import rikkaui.composeapp.generated.resources.weather_label
import rikkaui.composeapp.generated.resources.wednesday
import rikkaui.composeapp.generated.resources.wednesday_temp
import rikkaui.composeapp.generated.resources.wind
import rikkaui.composeapp.generated.resources.wind_value
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

@Composable
fun WeatherDashboardExample() {
    Card(label = stringResource(Res.string.weather_label)) {
        CardHeader {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = stringResource(Res.string.weather), variant = TextVariant.H4)
                Badge(text = stringResource(Res.string.live), variant = BadgeVariant.Default)
            }
        }

        CardContent {
            Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md)) {
                Text(text = stringResource(Res.string.temperature), variant = TextVariant.H1)
                Text(text = stringResource(Res.string.partly_cloudy), variant = TextVariant.Muted)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    WeatherStat(
                        label = stringResource(Res.string.humidity),
                        value = stringResource(Res.string.humidity_value),
                    )
                    WeatherStat(
                        label = stringResource(Res.string.wind),
                        value = stringResource(Res.string.wind_value),
                    )
                    WeatherStat(
                        label = stringResource(Res.string.uv_index),
                        value = stringResource(Res.string.uv_index_value),
                    )
                }

                Separator()

                Text(text = stringResource(Res.string.three_day_forecast), variant = TextVariant.Small)

                Table {
                    TableHeader {
                        TableHeaderCell(text = stringResource(Res.string.day_column), modifier = Modifier.weight(1f))
                        TableHeaderCell(text = stringResource(Res.string.temp_column), modifier = Modifier.weight(1f))
                        TableHeaderCell(
                            text = stringResource(Res.string.condition_column),
                            modifier = Modifier.weight(1f),
                        )
                    }
                    TableRow {
                        TableCell(text = stringResource(Res.string.monday), modifier = Modifier.weight(1f))
                        TableCell(text = stringResource(Res.string.monday_temp), modifier = Modifier.weight(1f))
                        TableCell(text = stringResource(Res.string.sunny), modifier = Modifier.weight(1f))
                    }
                    TableRow {
                        TableCell(text = stringResource(Res.string.tuesday), modifier = Modifier.weight(1f))
                        TableCell(text = stringResource(Res.string.tuesday_temp), modifier = Modifier.weight(1f))
                        TableCell(text = stringResource(Res.string.cloudy), modifier = Modifier.weight(1f))
                    }
                    TableRow {
                        TableCell(text = stringResource(Res.string.wednesday), modifier = Modifier.weight(1f))
                        TableCell(text = stringResource(Res.string.wednesday_temp), modifier = Modifier.weight(1f))
                        TableCell(text = stringResource(Res.string.rain), modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun WeatherStat(
    label: String,
    value: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
    ) {
        Text(text = label, variant = TextVariant.Muted)
        Text(text = value, variant = TextVariant.Large)
    }
}
