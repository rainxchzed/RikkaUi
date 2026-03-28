package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.card.CardContent
import zed.rainxch.rikkaui.components.ui.card.CardFooter
import zed.rainxch.rikkaui.components.ui.card.CardHeader
import zed.rainxch.rikkaui.components.ui.card.CardVariant
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

@Composable
fun CardSection() {
    SectionHeader(
        title = "Card",
        description = "Container component with 3 variants for grouping related content.",
    )

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    Column(
        verticalArrangement =
            androidx.compose.foundation.layout.Arrangement
                .spacedBy(RikkaTheme.spacing.md),
    ) {
        // Default variant
        Card(modifier = Modifier.fillMaxWidth(), variant = CardVariant.Default) {
            CardHeader {
                Text(text = "Default Card", variant = TextVariant.H4)
                Text(
                    text = "Bordered card with a subtle background.",
                    variant = TextVariant.Muted,
                )
            }
            CardContent {
                Text(
                    text =
                        "This is the default card variant. It uses a border and card " +
                            "surface background, making it the most common choice for grouping related content.",
                    variant = TextVariant.P,
                )
            }
            CardFooter {
                Button(text = "Action", onClick = {}, variant = ButtonVariant.Outline)
            }
        }

        // Elevated variant
        Card(modifier = Modifier.fillMaxWidth(), variant = CardVariant.Elevated) {
            CardHeader {
                Text(text = "Elevated Card", variant = TextVariant.H4)
                Text(
                    text = "Shadow-based depth without a border.",
                    variant = TextVariant.Muted,
                )
            }
            CardContent {
                Text(
                    text =
                        "The elevated variant uses a subtle shadow to create visual " +
                            "depth. It works well for content that needs to stand out from the page surface.",
                    variant = TextVariant.P,
                )
            }
        }

        // Ghost variant
        Card(modifier = Modifier.fillMaxWidth(), variant = CardVariant.Ghost) {
            CardHeader {
                Text(text = "Ghost Card", variant = TextVariant.H4)
                Text(
                    text = "No border, no shadow, transparent background.",
                    variant = TextVariant.Muted,
                )
            }
            CardContent {
                Text(
                    text =
                        "The ghost variant provides structural grouping without any visual " +
                            "weight. Useful for organizing content in layouts where decoration is unnecessary.",
                    variant = TextVariant.P,
                )
            }
        }
    }
}
