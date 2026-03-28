package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

@Composable
fun TextSection() {
    SectionHeader(
        title = "Text",
        description = "Typography component with 9 variants for headings, body, and utility text.",
    )

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    Card(modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(text = "H1 — Page Heading", variant = TextVariant.H1)
            Spacer(Modifier.height(RikkaTheme.spacing.sm))
            Text(text = "H2 — Section Heading", variant = TextVariant.H2)
            Spacer(Modifier.height(RikkaTheme.spacing.sm))
            Text(text = "H3 — Subsection Heading", variant = TextVariant.H3)
            Spacer(Modifier.height(RikkaTheme.spacing.sm))
            Text(text = "H4 — Card Heading", variant = TextVariant.H4)
            Spacer(Modifier.height(RikkaTheme.spacing.sm))
            Text(text = "P — Body text for paragraphs and general content.", variant = TextVariant.P)
            Spacer(Modifier.height(RikkaTheme.spacing.sm))
            Text(
                text = "Lead — A lead paragraph that stands out from regular body text.",
                variant = TextVariant.Lead,
            )
            Spacer(Modifier.height(RikkaTheme.spacing.sm))
            Text(text = "Large — Emphasized body text.", variant = TextVariant.Large)
            Spacer(Modifier.height(RikkaTheme.spacing.sm))
            Text(text = "Small — Fine print and captions.", variant = TextVariant.Small)
            Spacer(Modifier.height(RikkaTheme.spacing.sm))
            Text(text = "Muted — Secondary, de-emphasized text.", variant = TextVariant.Muted)
        }
    }
}
