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
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.popover.Popover
import zed.rainxch.rikkaui.components.ui.popover.PopoverAnimation
import zed.rainxch.rikkaui.components.ui.popover.PopoverPlacement
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
 * Documentation page for the Popover component.
 *
 * Demonstrates click-triggered popups with various animations
 * and placement options.
 */
@Composable
fun PopoverDoc() {
    ComponentPageHeader(
        name = "Popover",
        description = "A click-triggered floating card anchored to a "
            + "trigger element. Dismisses on outside click.",
    )

    // ─── Animation Variants ─────────────────────────────────
    DocSection("Animations") {
        var selectedAnim by remember { mutableStateOf("FadeExpand") }
        var open by remember { mutableStateOf(false) }

        VariantSelector(
            options = listOf("FadeExpand", "Fade", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = when (selectedAnim) {
            "Fade" -> PopoverAnimation.Fade
            "None" -> PopoverAnimation.None
            else -> PopoverAnimation.FadeExpand
        }

        DemoBox {
            Popover(
                expanded = open,
                onDismiss = { open = false },
                animation = animation,
                trigger = {
                    Button("Open Popover", onClick = { open = !open })
                },
            ) {
                Column {
                    Text("Popover Content", variant = TextVariant.Large)
                    Spacer(Modifier.height(RikkaTheme.spacing.xs))
                    Text(
                        "Animation: $selectedAnim",
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
        var open by remember { mutableStateOf(false) }

        VariantSelector(
            options = listOf(
                "BottomStart", "BottomEnd", "BottomCenter",
                "TopStart", "TopEnd", "TopCenter",
            ),
            selected = selectedPlacement,
            onSelect = { selectedPlacement = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val placement = when (selectedPlacement) {
            "BottomEnd" -> PopoverPlacement.BottomEnd
            "BottomCenter" -> PopoverPlacement.BottomCenter
            "TopStart" -> PopoverPlacement.TopStart
            "TopEnd" -> PopoverPlacement.TopEnd
            "TopCenter" -> PopoverPlacement.TopCenter
            else -> PopoverPlacement.BottomStart
        }

        DemoBox {
            Popover(
                expanded = open,
                onDismiss = { open = false },
                placement = placement,
                trigger = {
                    Button(
                        "Placement: $selectedPlacement",
                        onClick = { open = !open },
                    )
                },
            ) {
                Text(
                    "Placed at $selectedPlacement",
                    variant = TextVariant.P,
                )
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
        CodeBlock(
            """
var open by remember { mutableStateOf(false) }

Popover(
    expanded = open,
    onDismiss = { open = false },
    animation = PopoverAnimation.FadeExpand,
    placement = PopoverPlacement.BottomStart,
    trigger = {
        Button("Open", onClick = { open = true })
    },
) {
    Text("Popover content goes here.")
}
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "expanded", "Boolean", "required",
                    "Whether the popover popup is visible.",
                ),
                PropInfo(
                    "onDismiss", "() -> Unit", "required",
                    "Called when clicking outside to dismiss.",
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier applied to the trigger wrapper.",
                ),
                PropInfo(
                    "animation", "PopoverAnimation", "FadeExpand",
                    "Animation style: FadeExpand, Fade, None.",
                ),
                PropInfo(
                    "placement", "PopoverPlacement", "BottomStart",
                    "Popup position relative to trigger.",
                ),
                PropInfo(
                    "maxWidth", "Dp", "360.dp",
                    "Maximum width of the popup card.",
                ),
                PropInfo(
                    "trigger", "() -> Unit", "required",
                    "The composable that anchors the popover.",
                ),
                PropInfo(
                    "content", "() -> Unit", "required",
                    "Content displayed inside the floating card.",
                ),
            ),
        )
    }
}
