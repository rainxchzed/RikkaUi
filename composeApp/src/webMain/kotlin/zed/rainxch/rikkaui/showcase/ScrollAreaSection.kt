package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.scrollarea.ScrollArea
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

@Composable
fun ScrollAreaSection() {
    SectionHeader(
        title = "Scroll Area",
        description = "Custom scrollable containers with themed scrollbars.",
    )

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    Card(modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(text = "Vertical Scroll Area", variant = TextVariant.Small)

            Spacer(Modifier.height(RikkaTheme.spacing.sm))

            ScrollArea(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                repeat(15) { index ->
                    Text(
                        text = "Item ${index + 1}",
                        modifier = Modifier.padding(vertical = RikkaTheme.spacing.sm),
                    )
                }
            }
        }
    }
}
