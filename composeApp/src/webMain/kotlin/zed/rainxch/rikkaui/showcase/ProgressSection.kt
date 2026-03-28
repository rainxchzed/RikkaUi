package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.progress.Progress
import zed.rainxch.rikkaui.components.ui.slider.Slider
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

@Composable
fun ProgressSection() {
    SectionHeader(
        title = "Progress & Slider",
        description = "Visual indicators for completion and range input.",
    )

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    Card(modifier = Modifier.fillMaxWidth()) {
        // Progress
        Text(text = "Progress", variant = TextVariant.Small)
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        Progress(
            progress = 0.6f,
            modifier = Modifier.fillMaxWidth(),
            label = "Upload progress",
        )

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        // Slider
        Text(text = "Slider", variant = TextVariant.Small)
        Spacer(Modifier.height(RikkaTheme.spacing.xs))

        var sliderValue by remember { mutableFloatStateOf(0.5f) }

        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            modifier = Modifier.fillMaxWidth(),
            label = "Volume",
        )
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        Text(
            text = "${(sliderValue * 100).toInt()}%",
            variant = TextVariant.Muted,
        )
    }
}
