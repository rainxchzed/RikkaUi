package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.*
import rikkaui.feature.docs.generated.resources.Res
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.toast.LocalToastHostState
import zed.rainxch.rikkaui.components.ui.toast.ToastVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector
import zed.rainxch.rikkaui.foundation.RikkaTheme

@Composable
fun ToastDoc() {
    val toastState = LocalToastHostState.current
    val scope = rememberCoroutineScope()

    ComponentPageHeader(
        name = stringResource(Res.string.component_toast_name),
        description = stringResource(Res.string.toast_page_desc),
    )

    // ─── Variants ───────────────────────────────────────────
    DocSection(stringResource(Res.string.section_variants)) {
        var selectedVariant by remember { mutableStateOf("Default") }

        VariantSelector(
            options =
                listOf(
                    "Default",
                    "Success",
                    "Destructive",
                    "Warning",
                ),
            selected = selectedVariant,
            onSelect = { selectedVariant = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val variantNotification =
            stringResource(
                Res.string.toast_demo_variant_notification,
                selectedVariant,
            )
        val showVariantLabel =
            stringResource(
                Res.string.toast_demo_show_variant,
                selectedVariant,
            )

        DemoBox {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
            ) {
                Button(onClick = {
                    val variant =
                        when (selectedVariant) {
                            "Success" -> ToastVariant.Success
                            "Destructive" -> ToastVariant.Destructive
                            "Warning" -> ToastVariant.Warning
                            else -> ToastVariant.Default
                        }
                    scope.launch {
                        toastState.show(
                            message = variantNotification,
                            variant = variant,
                        )
                    }
                }) {
                    Text(showVariantLabel)
                }
            }
        }
    }

    // ─── Animations ─────────────────────────────────────────
    DocSection(stringResource(Res.string.section_animations)) {
        var selectedAnim by remember { mutableStateOf("SlideIn") }

        VariantSelector(
            options = listOf("SlideIn", "Fade", "Scale", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animationMessage =
            stringResource(
                Res.string.toast_demo_animation_message,
                selectedAnim,
            )
        val showAnimatedLabel = stringResource(Res.string.toast_demo_show_animated)

        DemoBox {
            Button(onClick = {
                scope.launch {
                    toastState.show(
                        message = animationMessage,
                        variant = ToastVariant.Success,
                    )
                }
            }) {
                Text(showAnimatedLabel)
            }
        }
    }

    // ─── With Action ────────────────────────────────────────
    DocSection(stringResource(Res.string.toast_section_with_action)) {
        val itemDeletedMessage = stringResource(Res.string.toast_demo_item_deleted)
        val undoLabel = stringResource(Res.string.toast_demo_undo)
        val showWithActionLabel = stringResource(Res.string.toast_demo_show_with_action)

        DemoBox {
            Button(
                onClick = {
                    scope.launch {
                        toastState.show(
                            message = itemDeletedMessage,
                            variant = ToastVariant.Destructive,
                            actionLabel = undoLabel,
                            onAction = {},
                        )
                    }
                },
                variant = ButtonVariant.Outline,
            ) {
                Text(showWithActionLabel)
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
val toastState = rememberToastHostState()

// Pass toastHost to Scaffold — fixed position, never scrolls
Scaffold(
    toastHost = { ToastHost(hostState = toastState) },
) {
    MyScreen()
}

// Or access from anywhere via LocalToastHostState
val toastState = LocalToastHostState.current
val scope = rememberCoroutineScope()

scope.launch {
    toastState.show(
        message = "File saved",
        variant = ToastVariant.Success,
    )
}
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "hostState",
                    "ToastHostState",
                    "required",
                    stringResource(Res.string.toast_prop_host_state_desc),
                ),
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(Res.string.toast_prop_modifier_desc),
                ),
                PropInfo(
                    "position",
                    "ToastPosition",
                    "BottomRight",
                    stringResource(Res.string.toast_prop_position_desc),
                ),
                PropInfo(
                    "animation",
                    "ToastAnimation",
                    "SlideIn",
                    stringResource(Res.string.toast_prop_animation_desc),
                ),
                PropInfo(
                    "maxVisibleToasts",
                    "Int",
                    "5",
                    stringResource(Res.string.toast_prop_max_visible_desc),
                ),
                PropInfo(
                    "swipeToDismiss",
                    "Boolean",
                    "true",
                    stringResource(Res.string.toast_prop_swipe_dismiss_desc),
                ),
                PropInfo(
                    "showProgressBar",
                    "Boolean",
                    "false",
                    stringResource(Res.string.toast_prop_progress_bar_desc),
                ),
            ),
        )
    }
}
