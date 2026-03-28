package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

@Composable
fun SeparatorSection() {
    SectionHeader(
        title = "Separator",
        description = "Horizontal dividers for visual separation.",
    )

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    Card(modifier = Modifier.fillMaxWidth()) {
        // Horizontal (default)
        Text(text = "Horizontal (default)", variant = TextVariant.Small)
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        Text(text = "Content above the separator.", variant = TextVariant.P)
        Spacer(Modifier.height(RikkaTheme.spacing.sm))
        Separator()
        Spacer(Modifier.height(RikkaTheme.spacing.sm))
        Text(text = "Content below the separator.", variant = TextVariant.P)

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        // Custom
        Text(text = "Custom", variant = TextVariant.Small)
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        Separator(
            color = RikkaTheme.colors.primary,
            thickness = 2.dp,
        )
    }
}
