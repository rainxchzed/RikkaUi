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
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.Res
import rikkaui.feature.docs.generated.resources.*
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
        name = stringResource(Res.string.component_card_name),
        description = stringResource(Res.string.card_page_desc),
    )

    // ─── Variants ───────────────────────────────────────────
    DocSection(stringResource(Res.string.section_variants)) {
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
                    Text(
                        stringResource(Res.string.card_demo_title),
                        variant = TextVariant.H4,
                    )
                    Text(
                        stringResource(Res.string.card_demo_description),
                        variant = TextVariant.Muted,
                    )
                }
                CardContent {
                    Text(
                        stringResource(
                            Res.string.card_demo_variant_text,
                            selectedVariant,
                        ),
                        variant = TextVariant.P,
                    )
                }
            }
        }
    }

    // ─── Structured Sections ────────────────────────────────
    DocSection(stringResource(Res.string.card_section_structured)) {
        DemoBox {
            Card {
                CardHeader {
                    Text(
                        stringResource(Res.string.card_demo_project_settings),
                        variant = TextVariant.H4,
                    )
                    Text(
                        stringResource(Res.string.card_demo_manage_config),
                        variant = TextVariant.Muted,
                    )
                }
                CardContent {
                    Text(
                        stringResource(Res.string.card_demo_project_theme),
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
                        ) { color ->
                            Text(
                                stringResource(Res.string.card_demo_cancel),
                                color = color,
                            )
                        }
                        Button(onClick = {}) { color ->
                            Text(
                                stringResource(Res.string.card_demo_save),
                                color = color,
                            )
                        }
                    }
                }
            }
        }
    }

    // ─── Clickable with Animations ──────────────────────────
    DocSection(stringResource(Res.string.card_section_clickable)) {
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
                    Text(
                        stringResource(Res.string.card_demo_click_hover),
                        variant = TextVariant.H4,
                    )
                    Text(
                        stringResource(
                            Res.string.card_demo_animation_label,
                            selectedAnim,
                        ),
                        variant = TextVariant.Muted,
                    )
                }

                Card(
                    onClick = {},
                    animation = animation,
                    elevation = 2.dp,
                    label = "Elevated interactive card",
                ) {
                    Text(
                        stringResource(Res.string.card_demo_custom_elevation),
                        variant = TextVariant.H4,
                    )
                    Text(
                        stringResource(Res.string.card_demo_elevation_value),
                        variant = TextVariant.Muted,
                    )
                }
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection(stringResource(Res.string.section_usage)) {
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
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    stringResource(Res.string.card_prop_modifier_desc),
                ),
                PropInfo(
                    "variant", "CardVariant", "Default",
                    stringResource(Res.string.card_prop_variant_desc),
                ),
                PropInfo(
                    "onClick", "(() -> Unit)?", "null",
                    stringResource(Res.string.card_prop_onclick_desc),
                ),
                PropInfo(
                    "animation", "CardAnimation", "Hover",
                    stringResource(Res.string.card_prop_animation_desc),
                ),
                PropInfo(
                    "elevation", "Dp?", "null",
                    stringResource(Res.string.card_prop_elevation_desc),
                ),
                PropInfo(
                    "label", "String", "\"\"",
                    stringResource(Res.string.card_prop_label_desc),
                ),
                PropInfo(
                    "content", "ColumnScope.() -> Unit", "required",
                    stringResource(Res.string.card_prop_content_desc),
                ),
            ),
        )
    }
}
