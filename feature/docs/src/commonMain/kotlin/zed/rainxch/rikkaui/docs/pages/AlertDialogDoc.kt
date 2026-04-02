package zed.rainxch.rikkaui.docs.pages

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
import zed.rainxch.rikkaui.components.ui.alertdialog.AlertDialog
import zed.rainxch.rikkaui.components.ui.alertdialog.AlertDialogAction
import zed.rainxch.rikkaui.components.ui.alertdialog.AlertDialogActionVariant
import zed.rainxch.rikkaui.components.ui.alertdialog.AlertDialogAnimation
import zed.rainxch.rikkaui.components.ui.alertdialog.AlertDialogCancel
import zed.rainxch.rikkaui.components.ui.alertdialog.AlertDialogFooter
import zed.rainxch.rikkaui.components.ui.alertdialog.AlertDialogHeader
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.docs.catalog.ComponentFamilies
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentFamily
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DoAndDont
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.TabbedDocPage
import zed.rainxch.rikkaui.docs.components.VariantSelector
import zed.rainxch.rikkaui.foundation.RikkaTheme

/**
 * Documentation page for the AlertDialog component.
 *
 * Demonstrates destructive confirmation patterns, animation
 * variants, and the non-dismissible scrim behavior.
 */
@Composable
fun AlertDialogDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_alert_dialog_name),
        description = stringResource(Res.string.alert_dialog_page_desc),
    )

    ComponentFamily(
        related = ComponentFamilies.DIALOGS,
        currentId = "alert-dialog",
    )

    TabbedDocPage(
        overview = { AlertDialogOverviewTab() },
        usage = { AlertDialogUsageTab() },
        api = { AlertDialogApiTab() },
    )
}

// ─── Overview Tab ───────────────────────────────────────────

@Composable
private fun AlertDialogOverviewTab() {
    // ─── Animation Variants ─────────────────────────────────
    DocSection(stringResource(Res.string.section_animations)) {
        var selectedAnim by remember { mutableStateOf("FadeScale") }
        var open by remember { mutableStateOf(false) }

        VariantSelector(
            options = listOf("FadeScale", "Fade", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation =
            when (selectedAnim) {
                "Fade" -> AlertDialogAnimation.Fade
                "None" -> AlertDialogAnimation.None
                else -> AlertDialogAnimation.FadeScale
            }

        DemoBox {
            Button(
                stringResource(Res.string.alert_dialog_demo_delete_account),
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
                    title = stringResource(Res.string.alert_dialog_demo_are_you_sure),
                    description = stringResource(Res.string.alert_dialog_demo_cannot_undo),
                )
                AlertDialogFooter {
                    AlertDialogCancel(onClick = { open = false })
                    AlertDialogAction(
                        text = stringResource(Res.string.alert_dialog_demo_delete_account),
                        onClick = { open = false },
                        variant = AlertDialogActionVariant.Destructive,
                    )
                }
            }
        }
    }

    // ─── Default Action ─────────────────────────────────────
    DocSection(stringResource(Res.string.alert_dialog_section_default_action)) {
        var open by remember { mutableStateOf(false) }

        DemoBox {
            Button(
                stringResource(Res.string.alert_dialog_demo_continue_setup),
                onClick = { open = true },
            )

            AlertDialog(
                open = open,
                onDismiss = { open = false },
                onConfirm = { open = false },
            ) {
                AlertDialogHeader(
                    title = stringResource(Res.string.alert_dialog_demo_continue_question),
                    description = stringResource(Res.string.alert_dialog_demo_configure_workspace),
                )
                AlertDialogFooter {
                    AlertDialogCancel(onClick = { open = false })
                    AlertDialogAction(
                        text = stringResource(Res.string.alert_dialog_demo_continue),
                        onClick = { open = false },
                    )
                }
            }
        }
    }
}

// ─── Usage Tab ──────────────────────────────────────────────

@Composable
private fun AlertDialogUsageTab() {
    DocSection(stringResource(Res.string.section_usage)) {
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

    DocSection(stringResource(Res.string.alert_dialog_section_dos_donts)) {
        var doOpen by remember { mutableStateOf(false) }
        var dontOpen by remember { mutableStateOf(false) }

        DoAndDont(
            doContent = {
                Button(
                    "Remove File",
                    onClick = { doOpen = true },
                    variant = ButtonVariant.Destructive,
                )
                AlertDialog(
                    open = doOpen,
                    onDismiss = { doOpen = false },
                    onConfirm = { doOpen = false },
                ) {
                    AlertDialogHeader(
                        title = "Remove this file?",
                        description = "This action cannot be undone.",
                    )
                    AlertDialogFooter {
                        AlertDialogCancel(onClick = { doOpen = false })
                        AlertDialogAction(
                            text = "Remove",
                            onClick = { doOpen = false },
                            variant = AlertDialogActionVariant.Destructive,
                        )
                    }
                }
            },
            doDescription = stringResource(Res.string.alert_dialog_do_confirm_desc),
            dontContent = {
                Button(
                    "Edit Settings",
                    onClick = { dontOpen = true },
                )
                AlertDialog(
                    open = dontOpen,
                    onDismiss = { dontOpen = false },
                    onConfirm = { dontOpen = false },
                ) {
                    AlertDialogHeader(
                        title = "Edit Settings",
                        description = "Configure name, theme, and notifications.",
                    )
                    AlertDialogFooter {
                        AlertDialogCancel(onClick = { dontOpen = false })
                        AlertDialogAction(
                            text = "Save",
                            onClick = { dontOpen = false },
                        )
                    }
                }
            },
            dontDescription = stringResource(Res.string.alert_dialog_dont_complex_desc),
        )
    }
}

// ─── API Tab ────────────────────────────────────────────────

@Composable
private fun AlertDialogApiTab() {
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "open",
                    "Boolean",
                    "required",
                    stringResource(Res.string.alert_dialog_prop_open_desc),
                ),
                PropInfo(
                    "onDismiss",
                    "() -> Unit",
                    "required",
                    stringResource(Res.string.alert_dialog_prop_on_dismiss_desc),
                ),
                PropInfo(
                    "onConfirm",
                    "() -> Unit",
                    "required",
                    stringResource(Res.string.alert_dialog_prop_on_confirm_desc),
                ),
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(Res.string.alert_dialog_prop_modifier_desc),
                ),
                PropInfo(
                    "label",
                    "String",
                    "\"Alert Dialog\"",
                    stringResource(Res.string.alert_dialog_prop_label_desc),
                ),
                PropInfo(
                    "animation",
                    "AlertDialogAnimation",
                    "FadeScale",
                    stringResource(Res.string.alert_dialog_prop_animation_desc),
                ),
                PropInfo(
                    "scrimColor",
                    "Color",
                    "colors.scrim",
                    stringResource(Res.string.alert_dialog_prop_scrim_desc),
                ),
                PropInfo(
                    "maxWidth",
                    "Dp",
                    "520.dp",
                    stringResource(Res.string.alert_dialog_prop_max_width_desc),
                ),
                PropInfo(
                    "content",
                    "@Composable () -> Unit",
                    "required",
                    stringResource(Res.string.alert_dialog_prop_content_desc),
                ),
            ),
        )
    }
}
