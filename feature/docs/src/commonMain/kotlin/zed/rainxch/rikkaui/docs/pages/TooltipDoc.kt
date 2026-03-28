package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.tooltip.Tooltip
import zed.rainxch.rikkaui.components.ui.tooltip.TooltipAnimation
import zed.rainxch.rikkaui.components.ui.tooltip.TooltipPlacement
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector

/**
 * Documentation page for the Tooltip component.
 *
 * Demonstrates hover-triggered tooltips with various animations,
 * placements, and delay configurations.
 */
@Composable
fun TooltipDoc() {
    ComponentPageHeader(
        name = "Tooltip",
        description = "A small popup label that appears on hover "
            + "with inverted theme colors for contrast.",
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
            "Fade" -> TooltipAnimation.Fade
            "None" -> TooltipAnimation.None
            else -> TooltipAnimation.FadeScale
        }

        DemoBox {
            Tooltip(
                tooltip = "Save your changes",
                animation = animation,
            ) {
                Button("Hover me", onClick = {})
            }
        }
    }

    // ─── Placements ─────────────────────────────────────────
    DocSection("Placements") {
        DemoBox {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.md,
                ),
            ) {
                Tooltip(
                    tooltip = "Top tooltip",
                    placement = TooltipPlacement.Top,
                ) {
                    Button("Top", onClick = {})
                }
                Tooltip(
                    tooltip = "Bottom tooltip",
                    placement = TooltipPlacement.Bottom,
                ) {
                    Button("Bottom", onClick = {})
                }
                Tooltip(
                    tooltip = "Start tooltip",
                    placement = TooltipPlacement.Start,
                ) {
                    Button("Start", onClick = {})
                }
                Tooltip(
                    tooltip = "End tooltip",
                    placement = TooltipPlacement.End,
                ) {
                    Button("End", onClick = {})
                }
            }
        }
    }

    // ─── Custom Delay ───────────────────────────────────────
    DocSection("Custom Show Delay") {
        DemoBox {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.md,
                ),
            ) {
                Tooltip(
                    tooltip = "Instant (0ms)",
                    showDelayMs = 0L,
                ) {
                    Button("No delay", onClick = {})
                }
                Tooltip(
                    tooltip = "Slow (800ms)",
                    showDelayMs = 800L,
                ) {
                    Button("Slow delay", onClick = {})
                }
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
        CodeBlock(
            """
Tooltip(tooltip = "Save changes") {
    Button("Save", onClick = { save() })
}

// With custom animation, placement, and delay
Tooltip(
    tooltip = "Copy to clipboard",
    animation = TooltipAnimation.Fade,
    placement = TooltipPlacement.Bottom,
    showDelayMs = 200L,
) {
    Button("Copy", onClick = { copy() })
}
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "tooltip", "String", "required",
                    "Text displayed inside the tooltip popup.",
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier applied to the outer wrapper.",
                ),
                PropInfo(
                    "animation", "TooltipAnimation", "FadeScale",
                    "Animation style: FadeScale, Fade, None.",
                ),
                PropInfo(
                    "placement", "TooltipPlacement", "Top",
                    "Position relative to content: Top, Bottom, Start, End.",
                ),
                PropInfo(
                    "showDelayMs", "Long", "400L",
                    "Delay in ms before showing the tooltip.",
                ),
                PropInfo(
                    "content", "() -> Unit", "required",
                    "The composable that triggers the tooltip on hover.",
                ),
            ),
        )
    }
}
