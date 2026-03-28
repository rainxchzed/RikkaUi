package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.dialog.Dialog
import zed.rainxch.rikkaui.components.ui.dialog.DialogAnimation
import zed.rainxch.rikkaui.components.ui.dialog.DialogFooter
import zed.rainxch.rikkaui.components.ui.dialog.DialogHeader
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
 * Documentation page for the Dialog component.
 *
 * Demonstrates dialog animations, structured sections
 * (DialogHeader, DialogFooter), and dismissal behavior.
 */
@Composable
fun DialogDoc() {
    ComponentPageHeader(
        name = "Dialog",
        description = "A modal overlay that displays content in a "
            + "centered card with a dismissible scrim.",
    )

    // ─── Animation Variants ─────────────────────────────────
    DocSection("Animations") {
        var selectedAnim by remember { mutableStateOf("FadeScale") }
        var open by remember { mutableStateOf(false) }

        VariantSelector(
            options = listOf("FadeScale", "Fade", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = when (selectedAnim) {
            "Fade" -> DialogAnimation.Fade
            "None" -> DialogAnimation.None
            else -> DialogAnimation.FadeScale
        }

        DemoBox {
            Button("Open Dialog", onClick = { open = true })

            Dialog(
                open = open,
                onDismiss = { open = false },
                animation = animation,
            ) {
                DialogHeader(
                    title = "Edit Profile",
                    description = "Make changes to your profile here.",
                )
                Text(
                    "Your name and avatar will be visible to others.",
                    variant = TextVariant.P,
                )
                DialogFooter {
                    Button(
                        "Cancel",
                        onClick = { open = false },
                        variant = ButtonVariant.Outline,
                    )
                    Button("Save", onClick = { open = false })
                }
            }
        }
    }

    // ─── Structured Sections ────────────────────────────────
    DocSection("With Header and Footer") {
        var open by remember { mutableStateOf(false) }

        DemoBox {
            Button(
                "Open Confirmation",
                onClick = { open = true },
            )

            Dialog(
                open = open,
                onDismiss = { open = false },
            ) {
                DialogHeader(
                    title = "Confirm Action",
                    description = "This action cannot be undone.",
                )
                DialogFooter {
                    Button(
                        "Cancel",
                        onClick = { open = false },
                        variant = ButtonVariant.Outline,
                    )
                    Button(
                        "Confirm",
                        onClick = { open = false },
                    )
                }
            }
        }
    }

    // ─── Simple Content ─────────────────────────────────────
    DocSection("Simple Content") {
        var open by remember { mutableStateOf(false) }

        DemoBox {
            Button("Open Simple", onClick = { open = true })

            Dialog(
                open = open,
                onDismiss = { open = false },
            ) {
                Text(
                    "This is a simple dialog with just text content.",
                    variant = TextVariant.P,
                )
                DialogFooter {
                    Button("Close", onClick = { open = false })
                }
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
        CodeBlock(
            """
var open by remember { mutableStateOf(false) }

Button("Open", onClick = { open = true })

Dialog(
    open = open,
    onDismiss = { open = false },
    animation = DialogAnimation.FadeScale,
) {
    DialogHeader(
        title = "Edit Profile",
        description = "Make changes here.",
    )
    Text("Content goes here.")
    DialogFooter {
        Button(
            "Cancel",
            onClick = { open = false },
            variant = ButtonVariant.Outline,
        )
        Button("Save", onClick = { open = false })
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
                    "Whether the dialog is visible.",
                ),
                PropInfo(
                    "onDismiss", "() -> Unit", "required",
                    "Called when the user dismisses the dialog.",
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier applied to the dialog card.",
                ),
                PropInfo(
                    "label", "String", "\"Dialog\"",
                    "Accessibility label for the dialog.",
                ),
                PropInfo(
                    "animation", "DialogAnimation", "FadeScale",
                    "Enter/exit animation: FadeScale, Fade, None.",
                ),
                PropInfo(
                    "scrimColor", "Color", "Black(0.5f)",
                    "Color of the backdrop scrim.",
                ),
                PropInfo(
                    "maxWidth", "Dp", "480.dp",
                    "Maximum width of the dialog card.",
                ),
                PropInfo(
                    "content", "ColumnScope.() -> Unit", "required",
                    "Dialog content. Use DialogHeader/DialogFooter.",
                ),
            ),
        )
    }
}
