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
import zed.rainxch.rikkaui.components.ui.alertdialog.AlertDialog
import zed.rainxch.rikkaui.components.ui.alertdialog.AlertDialogAction
import zed.rainxch.rikkaui.components.ui.alertdialog.AlertDialogActionVariant
import zed.rainxch.rikkaui.components.ui.alertdialog.AlertDialogAnimation
import zed.rainxch.rikkaui.components.ui.alertdialog.AlertDialogCancel
import zed.rainxch.rikkaui.components.ui.alertdialog.AlertDialogFooter
import zed.rainxch.rikkaui.components.ui.alertdialog.AlertDialogHeader
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector

/**
 * Documentation page for the AlertDialog component.
 *
 * Demonstrates destructive confirmation patterns, animation
 * variants, and the non-dismissible scrim behavior.
 */
@Composable
fun AlertDialogDoc() {
    ComponentPageHeader(
        name = "AlertDialog",
        description = "A modal dialog for destructive-action confirmations. "
            + "The scrim does not dismiss -- users must choose an action.",
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
            "Fade" -> AlertDialogAnimation.Fade
            "None" -> AlertDialogAnimation.None
            else -> AlertDialogAnimation.FadeScale
        }

        DemoBox {
            Button(
                "Delete Account",
                onClick = { open = true },
                variant = ButtonVariant.Destructive,
            )

            AlertDialog(
                open = open,
                onDismiss = { open = false },
                onConfirm = { open = false },
                animation = animation,
            ) {
                AlertDialogHeader(
                    title = "Are you absolutely sure?",
                    description = "This action cannot be undone. "
                        + "This will permanently delete your "
                        + "account and remove your data.",
                )
                AlertDialogFooter {
                    AlertDialogCancel(onClick = { open = false })
                    AlertDialogAction(
                        text = "Delete Account",
                        onClick = { open = false },
                        variant = AlertDialogActionVariant.Destructive,
                    )
                }
            }
        }
    }

    // ─── Default Action ─────────────────────────────────────
    DocSection("Default Action Variant") {
        var open by remember { mutableStateOf(false) }

        DemoBox {
            Button(
                "Continue Setup",
                onClick = { open = true },
            )

            AlertDialog(
                open = open,
                onDismiss = { open = false },
                onConfirm = { open = false },
            ) {
                AlertDialogHeader(
                    title = "Continue with setup?",
                    description = "This will configure your workspace "
                        + "with the recommended settings.",
                )
                AlertDialogFooter {
                    AlertDialogCancel(onClick = { open = false })
                    AlertDialogAction(
                        text = "Continue",
                        onClick = { open = false },
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

Button("Delete", onClick = { open = true })

AlertDialog(
    open = open,
    onDismiss = { open = false },
    onConfirm = { deleteAccount(); open = false },
    animation = AlertDialogAnimation.FadeScale,
) {
    AlertDialogHeader(
        title = "Are you absolutely sure?",
        description = "This action cannot be undone.",
    )
    AlertDialogFooter {
        AlertDialogCancel(onClick = { open = false })
        AlertDialogAction(
            text = "Delete",
            onClick = { open = false },
            variant = AlertDialogActionVariant.Destructive,
        )
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
                    "Whether the alert dialog is visible.",
                ),
                PropInfo(
                    "onDismiss", "() -> Unit", "required",
                    "Called when cancel action is triggered.",
                ),
                PropInfo(
                    "onConfirm", "() -> Unit", "required",
                    "Called when confirm action is triggered.",
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier applied to the dialog card.",
                ),
                PropInfo(
                    "label", "String", "\"Alert Dialog\"",
                    "Accessibility label for the dialog.",
                ),
                PropInfo(
                    "animation", "AlertDialogAnimation", "FadeScale",
                    "Enter/exit animation: FadeScale, Fade, None.",
                ),
                PropInfo(
                    "scrimColor", "Color", "Black(0.5f)",
                    "Color of the non-dismissible scrim.",
                ),
                PropInfo(
                    "maxWidth", "Dp", "520.dp",
                    "Maximum width of the dialog card.",
                ),
                PropInfo(
                    "content", "() -> Unit", "required",
                    "Dialog content. Use AlertDialogHeader/Footer.",
                ),
            ),
        )
    }
}
