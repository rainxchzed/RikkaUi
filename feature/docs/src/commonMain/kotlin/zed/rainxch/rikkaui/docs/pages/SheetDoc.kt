package zed.rainxch.rikkaui.docs.pages

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
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.sheet.Sheet
import zed.rainxch.rikkaui.components.ui.sheet.SheetAnimation
import zed.rainxch.rikkaui.components.ui.sheet.SheetContent
import zed.rainxch.rikkaui.components.ui.sheet.SheetFooter
import zed.rainxch.rikkaui.components.ui.sheet.SheetHeader
import zed.rainxch.rikkaui.components.ui.sheet.SheetSide
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
 * Documentation page for the Sheet component.
 *
 * Demonstrates sheet sides, animation variants, and structured
 * sections (SheetHeader, SheetContent, SheetFooter).
 */
@Composable
fun SheetDoc() {
    ComponentPageHeader(
        name = "Sheet",
        description = "A panel that slides in from any edge of the "
            + "screen with a dismissible scrim overlay.",
    )

    // ─── Side Variants ──────────────────────────────────────
    DocSection("Sides") {
        var selectedSide by remember { mutableStateOf("Right") }
        var open by remember { mutableStateOf(false) }

        VariantSelector(
            options = listOf("Right", "Left", "Top", "Bottom"),
            selected = selectedSide,
            onSelect = { selectedSide = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val side = when (selectedSide) {
            "Left" -> SheetSide.Left
            "Top" -> SheetSide.Top
            "Bottom" -> SheetSide.Bottom
            else -> SheetSide.Right
        }

        DemoBox {
            Button(
                "Open $selectedSide Sheet",
                onClick = { open = true },
            )

            Sheet(
                open = open,
                onDismiss = { open = false },
                side = side,
            ) {
                SheetHeader(
                    title = "Settings",
                    description = "Adjust your preferences.",
                )
                SheetContent {
                    Text(
                        "Sheet sliding from the $selectedSide side.",
                        variant = TextVariant.P,
                    )
                }
                SheetFooter {
                    Button(
                        "Cancel",
                        onClick = { open = false },
                        variant = ButtonVariant.Outline,
                    )
                    Button("Apply", onClick = { open = false })
                }
            }
        }
    }

    // ─── Animation Variants ─────────────────────────────────
    DocSection("Animations") {
        var selectedAnim by remember { mutableStateOf("Slide") }
        var open by remember { mutableStateOf(false) }

        VariantSelector(
            options = listOf("Slide", "FadeScale", "Fade", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = when (selectedAnim) {
            "FadeScale" -> SheetAnimation.FadeScale
            "Fade" -> SheetAnimation.Fade
            "None" -> SheetAnimation.None
            else -> SheetAnimation.Slide
        }

        DemoBox {
            Button(
                "Open Sheet",
                onClick = { open = true },
            )

            Sheet(
                open = open,
                onDismiss = { open = false },
                animation = animation,
            ) {
                SheetHeader(title = "Navigation")
                SheetContent {
                    Text(
                        "Animation: $selectedAnim",
                        variant = TextVariant.P,
                    )
                }
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
        CodeBlock(
            """
var open by remember { mutableStateOf(false) }

Button("Open Sheet", onClick = { open = true })

Sheet(
    open = open,
    onDismiss = { open = false },
    side = SheetSide.Right,
    animation = SheetAnimation.Slide,
) {
    SheetHeader(
        title = "Settings",
        description = "Adjust your preferences.",
    )
    SheetContent {
        Text("Sheet body content here.")
    }
    SheetFooter {
        Button("Close", onClick = { open = false })
    }
}
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "open", "Boolean", "required",
                    "Whether the sheet is visible.",
                ),
                PropInfo(
                    "onDismiss", "() -> Unit", "required",
                    "Called when the user dismisses the sheet.",
                ),
                PropInfo(
                    "side", "SheetSide", "Right",
                    "Edge to slide from: Right, Left, Top, Bottom.",
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier applied to the sheet panel.",
                ),
                PropInfo(
                    "label", "String", "\"Sheet\"",
                    "Accessibility label for the sheet.",
                ),
                PropInfo(
                    "animation", "SheetAnimation", "Slide",
                    "Enter/exit animation: Slide, FadeScale, Fade, None.",
                ),
                PropInfo(
                    "scrimColor", "Color", "Black(0.5f)",
                    "Color of the backdrop scrim.",
                ),
                PropInfo(
                    "panelWidth", "Dp", "320.dp",
                    "Width for Left/Right sheets.",
                ),
                PropInfo(
                    "content", "ColumnScope.() -> Unit", "required",
                    "Sheet content. Use SheetHeader/Content/Footer.",
                ),
            ),
        )
    }
}
