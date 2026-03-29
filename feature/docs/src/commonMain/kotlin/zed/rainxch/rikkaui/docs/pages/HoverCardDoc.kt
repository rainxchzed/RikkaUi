package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.*
import rikkaui.feature.docs.generated.resources.Res
import zed.rainxch.rikkaui.components.ui.hovercard.HoverCard
import zed.rainxch.rikkaui.components.ui.hovercard.HoverCardAnimation
import zed.rainxch.rikkaui.components.ui.hovercard.HoverCardPlacement
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector
import zed.rainxch.rikkaui.foundation.RikkaTheme

/**
 * Documentation page for the HoverCard component.
 *
 * Demonstrates hover-triggered floating cards with configurable
 * animations, placements, and show/hide delays.
 */
@Composable
fun HoverCardDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_hover_card_name),
        description = stringResource(Res.string.hover_card_page_desc),
    )

    // ─── Animation Variants ─────────────────────────────────
    DocSection(stringResource(Res.string.section_animations)) {
        var selectedAnim by remember { mutableStateOf("FadeScale") }

        VariantSelector(
            options = listOf("FadeScale", "Fade", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation =
            when (selectedAnim) {
                "Fade" -> HoverCardAnimation.Fade
                "None" -> HoverCardAnimation.None
                else -> HoverCardAnimation.FadeScale
            }

        DemoBox {
            HoverCard(
                animation = animation,
                trigger = {
                    Text(
                        "@rikkaui",
                        variant = TextVariant.P,
                        color = RikkaTheme.colors.primary,
                    )
                },
            ) {
                Column {
                    Text(
                        stringResource(Res.string.hover_card_demo_rikkaui),
                        variant = TextVariant.Large,
                    )
                    Spacer(Modifier.height(RikkaTheme.spacing.xs))
                    Text(
                        stringResource(Res.string.hover_card_demo_desc),
                        variant = TextVariant.Muted,
                    )
                }
            }
        }
    }

    // ─── Placements ─────────────────────────────────────────
    DocSection(stringResource(Res.string.section_placements)) {
        var selectedPlacement by remember {
            mutableStateOf("BottomStart")
        }

        VariantSelector(
            options =
                listOf(
                    "BottomStart",
                    "BottomEnd",
                    "TopStart",
                    "TopEnd",
                ),
            selected = selectedPlacement,
            onSelect = { selectedPlacement = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val placement =
            when (selectedPlacement) {
                "BottomEnd" -> HoverCardPlacement.BottomEnd
                "TopStart" -> HoverCardPlacement.TopStart
                "TopEnd" -> HoverCardPlacement.TopEnd
                else -> HoverCardPlacement.BottomStart
            }

        DemoBox {
            HoverCard(
                placement = placement,
                trigger = {
                    Text(
                        stringResource(
                            Res.string.hover_card_demo_hover_me,
                            selectedPlacement,
                        ),
                        variant = TextVariant.P,
                        color = RikkaTheme.colors.primary,
                    )
                },
            ) {
                Text(
                    stringResource(
                        Res.string.hover_card_demo_card_at,
                        selectedPlacement,
                    ),
                    variant = TextVariant.P,
                )
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
HoverCard(
    trigger = {
        Text("@rikka")
    },
) {
    Column {
        Text("RikkaUi", variant = TextVariant.Large)
        Text("A shadcn-inspired component library.")
    }
}

// With custom animation, placement, and delays
HoverCard(
    animation = HoverCardAnimation.Fade,
    placement = HoverCardPlacement.TopStart,
    showDelayMs = 500L,
    hideDelayMs = 300L,
    maxWidth = 400.dp,
    trigger = { Text("Hover me") },
) {
    Text("Custom hover card content.")
}
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(Res.string.hover_card_prop_modifier_desc),
                ),
                PropInfo(
                    "animation",
                    "HoverCardAnimation",
                    "FadeScale",
                    stringResource(Res.string.hover_card_prop_animation_desc),
                ),
                PropInfo(
                    "placement",
                    "HoverCardPlacement",
                    "BottomStart",
                    stringResource(Res.string.hover_card_prop_placement_desc),
                ),
                PropInfo(
                    "showDelayMs",
                    "Long",
                    "300L",
                    stringResource(Res.string.hover_card_prop_show_delay_desc),
                ),
                PropInfo(
                    "hideDelayMs",
                    "Long",
                    "200L",
                    stringResource(Res.string.hover_card_prop_hide_delay_desc),
                ),
                PropInfo(
                    "maxWidth",
                    "Dp",
                    "360.dp",
                    stringResource(Res.string.hover_card_prop_max_width_desc),
                ),
                PropInfo(
                    "trigger",
                    "() -> Unit",
                    "required",
                    stringResource(Res.string.hover_card_prop_trigger_desc),
                ),
                PropInfo(
                    "content",
                    "() -> Unit",
                    "required",
                    stringResource(Res.string.hover_card_prop_content_desc),
                ),
            ),
        )
    }
}
