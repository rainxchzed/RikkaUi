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
import zed.rainxch.rikkaui.components.theme.RikkaTheme
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

/**
 * Documentation page for the HoverCard component.
 *
 * Demonstrates hover-triggered floating cards with configurable
 * animations, placements, and show/hide delays.
 */
@Composable
fun HoverCardDoc() {
    ComponentPageHeader(
        name = "HoverCard",
        description = "A floating card that appears when hovering "
            + "over a trigger element. Stays visible while the "
            + "cursor is on the trigger or card.",
    )

    // ─── Animation Variants ─────────────────────────────────
    DocSection("Animations") {
        var selectedAnim by remember { mutableStateOf("FadeScale") }

        VariantSelector(
            options = listOf("FadeScale", "Fade", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = when (selectedAnim) {
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
                    Text("RikkaUI", variant = TextVariant.Large)
                    Spacer(Modifier.height(RikkaTheme.spacing.xs))
                    Text(
                        "A shadcn-inspired component library "
                            + "for Compose Multiplatform.",
                        variant = TextVariant.Muted,
                    )
                }
            }
        }
    }

    // ─── Placements ─────────────────────────────────────────
    DocSection("Placements") {
        var selectedPlacement by remember {
            mutableStateOf("BottomStart")
        }

        VariantSelector(
            options = listOf(
                "BottomStart", "BottomEnd",
                "TopStart", "TopEnd",
            ),
            selected = selectedPlacement,
            onSelect = { selectedPlacement = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val placement = when (selectedPlacement) {
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
                        "Hover me ($selectedPlacement)",
                        variant = TextVariant.P,
                        color = RikkaTheme.colors.primary,
                    )
                },
            ) {
                Text(
                    "Card at $selectedPlacement",
                    variant = TextVariant.P,
                )
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
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
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier applied to the outer wrapper.",
                ),
                PropInfo(
                    "animation", "HoverCardAnimation", "FadeScale",
                    "Animation style: FadeScale, Fade, None.",
                ),
                PropInfo(
                    "placement", "HoverCardPlacement", "BottomStart",
                    "Popup position: BottomStart/End, TopStart/End.",
                ),
                PropInfo(
                    "showDelayMs", "Long", "300L",
                    "Delay in ms before showing the card.",
                ),
                PropInfo(
                    "hideDelayMs", "Long", "200L",
                    "Delay in ms before hiding the card.",
                ),
                PropInfo(
                    "maxWidth", "Dp", "360.dp",
                    "Maximum width of the popup card.",
                ),
                PropInfo(
                    "trigger", "() -> Unit", "required",
                    "The composable that triggers on hover.",
                ),
                PropInfo(
                    "content", "() -> Unit", "required",
                    "Content displayed inside the floating card.",
                ),
            ),
        )
    }
}
