package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.card.CardAnimation
import zed.rainxch.rikkaui.components.ui.card.CardContent
import zed.rainxch.rikkaui.components.ui.card.CardFooter
import zed.rainxch.rikkaui.components.ui.card.CardHeader
import zed.rainxch.rikkaui.components.ui.card.CardVariant
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector

/**
 * Documentation page for the Card component.
 *
 * Demonstrates all card variants, animations, and structured
 * sections (CardHeader, CardContent, CardFooter).
 */
@Composable
fun CardDoc() {
    ComponentPageHeader(
        name = "Card",
        description = "A container that groups related content with visual separation.",
    )

    // ─── Variants ───────────────────────────────────────────
    DocSection("Variants") {
        var selectedVariant by remember { mutableStateOf("Default") }

        VariantSelector(
            options = listOf("Default", "Elevated", "Ghost"),
            selected = selectedVariant,
            onSelect = { selectedVariant = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val variant = when (selectedVariant) {
            "Elevated" -> CardVariant.Elevated
            "Ghost" -> CardVariant.Ghost
            else -> CardVariant.Default
        }

        DemoBox {
            Card(variant = variant) {
                CardHeader {
                    Text("Card Title", variant = TextVariant.H4)
                    Text(
                        "Card description goes here.",
                        variant = TextVariant.Muted,
                    )
                }
                CardContent {
                    Text(
                        "This is the $selectedVariant variant.",
                        variant = TextVariant.P,
                    )
                }
            }
        }
    }

    // ─── Structured Sections ────────────────────────────────
    DocSection("Structured Sections") {
        DemoBox {
            Card {
                CardHeader {
                    Text("Project Settings", variant = TextVariant.H4)
                    Text(
                        "Manage your project configuration.",
                        variant = TextVariant.Muted,
                    )
                }
                CardContent {
                    Text(
                        "Your project is using the default theme "
                            + "with Zinc palette and Blue accent.",
                        variant = TextVariant.P,
                    )
                }
                CardFooter {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(
                            RikkaTheme.spacing.sm,
                        ),
                    ) {
                        Button(
                            onClick = {},
                            variant = ButtonVariant.Outline,
                        ) { Text("Cancel") }
                        Button(onClick = {}) { Text("Save") }
                    }
                }
            }
        }
    }

    // ─── Clickable with Animations ──────────────────────────
    DocSection("Clickable with Animations") {
        var selectedAnim by remember { mutableStateOf("Hover") }

        VariantSelector(
            options = listOf("Hover", "Press", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = when (selectedAnim) {
            "Press" -> CardAnimation.Press
            "None" -> CardAnimation.None
            else -> CardAnimation.Hover
        }

        DemoBox {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.md,
                ),
            ) {
                Card(
                    onClick = {},
                    animation = animation,
                    label = "Interactive card",
                ) {
                    Text("Click or hover me", variant = TextVariant.H4)
                    Text(
                        "Animation: $selectedAnim",
                        variant = TextVariant.Muted,
                    )
                }

                Card(
                    onClick = {},
                    animation = animation,
                    elevation = 2.dp,
                    label = "Elevated interactive card",
                ) {
                    Text("With custom elevation", variant = TextVariant.H4)
                    Text(
                        "elevation = 2.dp",
                        variant = TextVariant.Muted,
                    )
                }
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
        CodeBlock(
            """
Card {
    CardHeader {
        Text("Title", variant = TextVariant.H4)
        Text("Description", variant = TextVariant.Muted)
    }
    CardContent {
        Text("Main content here")
    }
    CardFooter {
        Button(onClick = {}) { Text("Action") }
    }
}

// Clickable card with hover animation
Card(
    onClick = { navigateToDetail() },
    animation = CardAnimation.Hover,
    variant = CardVariant.Elevated,
) {
    Text("Click me")
}
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier for layout and decoration.",
                ),
                PropInfo(
                    "variant", "CardVariant", "Default",
                    "Visual variant: Default, Elevated, Ghost.",
                ),
                PropInfo(
                    "onClick", "(() -> Unit)?", "null",
                    "Click handler. When non-null the card is clickable.",
                ),
                PropInfo(
                    "animation", "CardAnimation", "Hover",
                    "Interaction animation: Hover, Press, None.",
                ),
                PropInfo(
                    "elevation", "Dp?", "null",
                    "Custom shadow elevation. Overrides variant default.",
                ),
                PropInfo(
                    "label", "String", "\"\"",
                    "Accessibility label for screen readers.",
                ),
                PropInfo(
                    "content", "ColumnScope.() -> Unit", "required",
                    "Card content. Use CardHeader/CardContent/CardFooter.",
                ),
            ),
        )
    }
}
