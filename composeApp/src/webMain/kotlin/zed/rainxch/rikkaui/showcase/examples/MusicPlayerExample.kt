package zed.rainxch.rikkaui.showcase.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import rikkaui.composeapp.generated.resources.Res
import rikkaui.composeapp.generated.resources.current_time
import rikkaui.composeapp.generated.resources.hd_audio
import rikkaui.composeapp.generated.resources.music_player_label
import rikkaui.composeapp.generated.resources.next_track
import rikkaui.composeapp.generated.resources.now_playing
import rikkaui.composeapp.generated.resources.pause
import rikkaui.composeapp.generated.resources.play
import rikkaui.composeapp.generated.resources.playback_progress
import rikkaui.composeapp.generated.resources.previous_track
import rikkaui.composeapp.generated.resources.repeat_label
import rikkaui.composeapp.generated.resources.shuffle
import rikkaui.composeapp.generated.resources.song_artist
import rikkaui.composeapp.generated.resources.song_title
import rikkaui.composeapp.generated.resources.total_time
import rikkaui.composeapp.generated.resources.volume
import rikkaui.composeapp.generated.resources.volume_control
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.card.CardContent
import zed.rainxch.rikkaui.components.ui.card.CardFooter
import zed.rainxch.rikkaui.components.ui.card.CardHeader
import zed.rainxch.rikkaui.components.ui.label.Label
import zed.rainxch.rikkaui.components.ui.progress.Progress
import zed.rainxch.rikkaui.components.ui.slider.Slider
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.toggle.Toggle
import zed.rainxch.rikkaui.foundation.RikkaTheme

@Composable
fun MusicPlayerExample() {
    var isPlaying by remember { mutableStateOf(false) }
    var volume by remember { mutableFloatStateOf(0.7f) }
    var shuffleOn by remember { mutableStateOf(false) }
    var repeatOn by remember { mutableStateOf(true) }

    Card(
        label = stringResource(Res.string.music_player_label),
    ) {
        CardHeader {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(Res.string.now_playing),
                    variant = TextVariant.H4,
                )
                Badge(
                    text = stringResource(Res.string.hd_audio),
                    variant = BadgeVariant.Secondary,
                )
            }
        }

        CardContent {
            Column(
                verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
                ) {
                    Text(
                        text = stringResource(Res.string.song_title),
                        variant = TextVariant.Large,
                    )
                    Text(
                        text = stringResource(Res.string.song_artist),
                        variant = TextVariant.Muted,
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
                ) {
                    Progress(
                        progress = 0.65f,
                        label = stringResource(Res.string.playback_progress),
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = stringResource(Res.string.current_time),
                            variant = TextVariant.Muted,
                        )
                        Text(
                            text = stringResource(Res.string.total_time),
                            variant = TextVariant.Muted,
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(
                            RikkaTheme.spacing.sm,
                            Alignment.CenterHorizontally,
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Button(
                        onClick = { },
                        variant = ButtonVariant.Ghost,
                        size = ButtonSize.Icon,
                        label = stringResource(Res.string.previous_track),
                    ) {
                        Text(text = "\u25C1", variant = TextVariant.P)
                    }
                    Button(
                        onClick = { isPlaying = !isPlaying },
                        size = ButtonSize.Icon,
                        label =
                            if (isPlaying) {
                                stringResource(Res.string.pause)
                            } else {
                                stringResource(Res.string.play)
                            },
                    ) {
                        Text(
                            text = if (isPlaying) "\u23F8" else "\u25B6",
                            variant = TextVariant.P,
                            color = RikkaTheme.colors.primaryForeground,
                        )
                    }
                    Button(
                        onClick = { },
                        variant = ButtonVariant.Ghost,
                        size = ButtonSize.Icon,
                        label = stringResource(Res.string.next_track),
                    ) {
                        Text(text = "\u25B7", variant = TextVariant.P)
                    }
                }

                Spacer(modifier = Modifier.height(RikkaTheme.spacing.xs))

                Column(
                    verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
                ) {
                    Label(stringResource(Res.string.volume))
                    Slider(
                        value = volume,
                        onValueChange = { volume = it },
                        label = stringResource(Res.string.volume_control),
                    )
                }
            }
        }

        CardFooter {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xl),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(Res.string.shuffle),
                        variant = TextVariant.Small,
                    )
                    Toggle(
                        checked = shuffleOn,
                        onCheckedChange = { shuffleOn = it },
                        label = stringResource(Res.string.shuffle),
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(Res.string.repeat_label),
                        variant = TextVariant.Small,
                    )
                    Toggle(
                        checked = repeatOn,
                        onCheckedChange = { repeatOn = it },
                        label = stringResource(Res.string.repeat_label),
                    )
                }
            }
        }
    }
}
