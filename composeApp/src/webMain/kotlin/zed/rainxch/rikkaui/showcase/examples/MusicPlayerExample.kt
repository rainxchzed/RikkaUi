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
import zed.rainxch.rikkaui.components.theme.RikkaTheme
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

/**
 * A music player card demonstrating Card, Button, Progress, Slider,
 * Toggle, Badge, and Text components in a realistic media playback UI.
 */
@Composable
fun MusicPlayerExample() {
    var isPlaying by remember { mutableStateOf(false) }
    var volume by remember { mutableFloatStateOf(0.7f) }
    var shuffleOn by remember { mutableStateOf(false) }
    var repeatOn by remember { mutableStateOf(true) }

    Card(
        label = "Music player",
    ) {
        // ─── Header ──────────────────────────────────────
        CardHeader {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Now Playing",
                    variant = TextVariant.H4,
                )
                Badge(
                    text = "HD Audio",
                    variant = BadgeVariant.Secondary,
                )
            }
        }

        // ─── Content ─────────────────────────────────────
        CardContent {
            Column(
                verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
            ) {
                // Song info
                Column(
                    verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
                ) {
                    Text(
                        text = "Midnight Drive",
                        variant = TextVariant.Large,
                    )
                    Text(
                        text = "Neon Waves",
                        variant = TextVariant.Muted,
                    )
                }

                // Playback progress
                Column(
                    verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
                ) {
                    Progress(
                        progress = 0.65f,
                        label = "Playback progress",
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = "2:18",
                            variant = TextVariant.Muted,
                        )
                        Text(
                            text = "3:33",
                            variant = TextVariant.Muted,
                        )
                    }
                }

                // Transport controls
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
                        onClick = { /* previous track */ },
                        variant = ButtonVariant.Ghost,
                        size = ButtonSize.Icon,
                        label = "Previous track",
                    ) {
                        Text(text = "\u25C1", variant = TextVariant.P)
                    }
                    Button(
                        onClick = { isPlaying = !isPlaying },
                        size = ButtonSize.Icon,
                        label = if (isPlaying) "Pause" else "Play",
                    ) {
                        Text(
                            text = if (isPlaying) "\u23F8" else "\u25B6",
                            variant = TextVariant.P,
                            color = RikkaTheme.colors.primaryForeground,
                        )
                    }
                    Button(
                        onClick = { /* next track */ },
                        variant = ButtonVariant.Ghost,
                        size = ButtonSize.Icon,
                        label = "Next track",
                    ) {
                        Text(text = "\u25B7", variant = TextVariant.P)
                    }
                }

                Spacer(modifier = Modifier.height(RikkaTheme.spacing.xs))

                // Volume slider
                Column(
                    verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
                ) {
                    Label("Volume")
                    Slider(
                        value = volume,
                        onValueChange = { volume = it },
                        label = "Volume control",
                    )
                }
            }
        }

        // ─── Footer ─────────────────────────────────────
        CardFooter {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xl),
            ) {
                Row(
                    horizontalArrangement =
                        Arrangement.spacedBy(
                            RikkaTheme.spacing.sm,
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Shuffle",
                        variant = TextVariant.Small,
                    )
                    Toggle(
                        checked = shuffleOn,
                        onCheckedChange = { shuffleOn = it },
                        label = "Shuffle",
                    )
                }
                Row(
                    horizontalArrangement =
                        Arrangement.spacedBy(
                            RikkaTheme.spacing.sm,
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Repeat",
                        variant = TextVariant.Small,
                    )
                    Toggle(
                        checked = repeatOn,
                        onCheckedChange = { repeatOn = it },
                        label = "Repeat",
                    )
                }
            }
        }
    }
}
