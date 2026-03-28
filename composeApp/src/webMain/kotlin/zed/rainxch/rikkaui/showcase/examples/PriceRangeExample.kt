package zed.rainxch.rikkaui.showcase.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import kotlin.math.roundToInt

/**
 * A price range selector card demonstrating Card, Slider, Progress,
 * and Text components working together in a realistic filter widget.
 */
@Composable
fun PriceRangeExample() {
    var sliderValue by remember { mutableFloatStateOf(0.5f) }

    val minPrice = 200
    val maxPrice = 800
    val currentLow =
        minPrice +
            ((maxPrice - minPrice) * sliderValue * 0.4f)
                .roundToInt()
    val currentHigh =
        minPrice +
            ((maxPrice - minPrice) * (0.6f + sliderValue * 0.4f))
                .roundToInt()
                .coerceAtMost(maxPrice)
    val percentage = (sliderValue * 100).roundToInt()

    Card(
        label = "Price range selector",
    ) {
        Text(
            text = "Price Range",
            variant = TextVariant.H4,
        )
        Text(
            text = "Set your budget range (\$$minPrice \u2013 \$$maxPrice).",
            variant = TextVariant.Muted,
        )

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.md))

        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            label = "Price range slider",
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "\$$currentLow",
                variant = TextVariant.Small,
            )
            Text(
                text = "\$$currentHigh",
                variant = TextVariant.Small,
            )
        }

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        Progress(
            progress = sliderValue,
            label = "Budget usage",
        )

        Text(
            text = "$percentage% of budget range",
            variant = TextVariant.Muted,
        )
    }
}
