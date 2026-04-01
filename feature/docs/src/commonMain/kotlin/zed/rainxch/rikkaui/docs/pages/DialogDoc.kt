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
import zed.rainxch.rikkaui.foundation.RikkaTheme

/**
 * Documentation page for the Dialog component.
 *
 * Demonstrates dialog animations, structured sections
 * (DialogHeader, DialogFooter), and dismissal behavior.
 */
@Composable
fun DialogDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_dialog_name),
        description = stringResource(Res.string.dialog_page_desc),
    )

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
                "Fade" -> DialogAnimation.Fade
                "None" -> DialogAnimation.None
                else -> DialogAnimation.FadeScale
            }

        DemoBox {
            Button(
                stringResource(Res.string.dialog_demo_open_dialog),
                onClick = { open = true },
            )

            Dialog(
                open = open,
                onDismiss = { open = false },
                animation = animation,
            ) {
                DialogHeader(
                    title = stringResource(Res.string.dialog_demo_edit_profile),
                    description = stringResource(Res.string.dialog_demo_edit_profile_desc),
                )
                Text(
                    stringResource(Res.string.dialog_demo_visible_to_others),
                    variant = TextVariant.P,
                )
                DialogFooter {
                    Button(
                        stringResource(Res.string.dialog_demo_cancel),
                        onClick = { open = false },
                        variant = ButtonVariant.Outline,
                    )
                    Button(
                        stringResource(Res.string.dialog_demo_save),
                        onClick = { open = false },
                    )
                }
            }
        }
    }

    // ─── Structured Sections ────────────────────────────────
    DocSection(stringResource(Res.string.dialog_section_header_footer)) {
        var open by remember { mutableStateOf(false) }

        DemoBox {
            Button(
                stringResource(Res.string.dialog_demo_open_confirmation),
                onClick = { open = true },
            )

            Dialog(
                open = open,
                onDismiss = { open = false },
            ) {
                DialogHeader(
                    title = stringResource(Res.string.dialog_demo_confirm_action),
                    description = stringResource(Res.string.dialog_demo_cannot_undo),
                )
                DialogFooter {
                    Button(
                        stringResource(Res.string.dialog_demo_cancel),
                        onClick = { open = false },
                        variant = ButtonVariant.Outline,
                    )
                    Button(
                        stringResource(Res.string.dialog_demo_confirm),
                        onClick = { open = false },
                    )
                }
            }
        }
    }

    // ─── Simple Content ─────────────────────────────────────
    DocSection(stringResource(Res.string.dialog_section_simple)) {
        var open by remember { mutableStateOf(false) }

        DemoBox {
            Button(
                stringResource(Res.string.dialog_demo_open_simple),
                onClick = { open = true },
            )

            Dialog(
                open = open,
                onDismiss = { open = false },
            ) {
                Text(
                    stringResource(Res.string.dialog_demo_simple_text),
                    variant = TextVariant.P,
                )
                DialogFooter {
                    Button(
                        stringResource(Res.string.dialog_demo_close),
                        onClick = { open = false },
                    )
                }
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection(stringResource(Res.string.section_usage)) {
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
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "open",
                    "Boolean",
                    "required",
                    stringResource(Res.string.dialog_prop_open_desc),
                ),
                PropInfo(
                    "onDismiss",
                    "() -> Unit",
                    "required",
                    stringResource(Res.string.dialog_prop_on_dismiss_desc),
                ),
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(Res.string.dialog_prop_modifier_desc),
                ),
                PropInfo(
                    "label",
                    "String",
                    "\"Dialog\"",
                    stringResource(Res.string.dialog_prop_label_desc),
                ),
                PropInfo(
                    "animation",
                    "DialogAnimation",
                    "FadeScale",
                    stringResource(Res.string.dialog_prop_animation_desc),
                ),
                PropInfo(
                    "scrimColor",
                    "Color",
                    "colors.scrim",
                    stringResource(Res.string.dialog_prop_scrim_desc),
                ),
                PropInfo(
                    "maxWidth",
                    "Dp",
                    "480.dp",
                    stringResource(Res.string.dialog_prop_max_width_desc),
                ),
                PropInfo(
                    "content",
                    "@Composable ColumnScope.() -> Unit",
                    "required",
                    stringResource(Res.string.dialog_prop_content_desc),
                ),
            ),
        )
    }
}
