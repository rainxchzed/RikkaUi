package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.accordion.AccordionItem
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

@Composable
fun AccordionSection() {
    SectionHeader(
        title = "Accordion",
        description = "Expandable content sections with animated transitions.",
    )

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    Card(modifier = Modifier.fillMaxWidth()) {
        var expanded1 by remember { mutableStateOf(false) }
        var expanded2 by remember { mutableStateOf(false) }
        var expanded3 by remember { mutableStateOf(false) }

        Column(modifier = Modifier.fillMaxWidth()) {
            AccordionItem(
                title = "Is it accessible?",
                expanded = expanded1,
                onExpandedChange = { expanded1 = it },
            ) {
                Text(
                    text = "Yes. It follows WAI-ARIA patterns.",
                    variant = TextVariant.Muted,
                )
            }

            AccordionItem(
                title = "Is it styled?",
                expanded = expanded2,
                onExpandedChange = { expanded2 = it },
            ) {
                Text(
                    text = "Yes. It uses Rikka theme tokens for consistent styling.",
                    variant = TextVariant.Muted,
                )
            }

            AccordionItem(
                title = "Is it animated?",
                expanded = expanded3,
                onExpandedChange = { expanded3 = it },
            ) {
                Text(
                    text = "Yes. Spring-physics animations for smooth transitions.",
                    variant = TextVariant.Muted,
                )
            }
        }
    }
}
