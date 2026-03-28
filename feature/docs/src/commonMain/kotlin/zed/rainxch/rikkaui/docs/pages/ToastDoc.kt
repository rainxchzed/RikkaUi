package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.toast.ToastAnimation
import zed.rainxch.rikkaui.components.ui.toast.ToastHost
import zed.rainxch.rikkaui.components.ui.toast.ToastPosition
import zed.rainxch.rikkaui.components.ui.toast.ToastVariant
import zed.rainxch.rikkaui.components.ui.toast.rememberToastHostState
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector

/**
 * Documentation page for the Toast component.
 *
 * Demonstrates toast variants, animations, positions,
 * and the ToastHostState API.
 */
@Composable
fun ToastDoc() {
    val toastState = rememberToastHostState()
    val scope = rememberCoroutineScope()

    ComponentPageHeader(
        name = "Toast",
        description = "A non-intrusive notification system with "
            + "auto-dismiss, swipe gestures, and variant accents.",
    )

    // ─── Variants ───────────────────────────────────────────
    DocSection("Variants") {
        var selectedVariant by remember { mutableStateOf("Default") }

        VariantSelector(
            options = listOf(
                "Default", "Success", "Destructive", "Warning",
            ),
            selected = selectedVariant,
            onSelect = { selectedVariant = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        DemoBox {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
            ) {
                Button(onClick = {
                    val variant = when (selectedVariant) {
                        "Success" -> ToastVariant.Success
                        "Destructive" -> ToastVariant.Destructive
                        "Warning" -> ToastVariant.Warning
                        else -> ToastVariant.Default
                    }
                    scope.launch {
                        toastState.show(
                            message = "$selectedVariant toast notification",
                            variant = variant,
                        )
                    }
                }) {
                    Text("Show $selectedVariant Toast")
                }
            }
        }
    }

    // ─── Animations ─────────────────────────────────────────
    DocSection("Animations") {
        var selectedAnim by remember { mutableStateOf("SlideIn") }

        VariantSelector(
            options = listOf("SlideIn", "Fade", "Scale", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        DemoBox {
            Button(onClick = {
                scope.launch {
                    toastState.show(
                        message = "Animation: $selectedAnim",
                        variant = ToastVariant.Success,
                    )
                }
            }) {
                Text("Show Animated Toast")
            }
        }
    }

    // ─── With Action ────────────────────────────────────────
    DocSection("With Action Button") {
        DemoBox {
            Button(
                onClick = {
                    scope.launch {
                        toastState.show(
                            message = "Item deleted",
                            variant = ToastVariant.Destructive,
                            actionLabel = "Undo",
                            onAction = {},
                        )
                    }
                },
                variant = ButtonVariant.Outline,
            ) {
                Text("Show Toast with Action")
            }
        }
    }

    // ─── Toast Host (renders toasts) ────────────────────────
    Box(Modifier.fillMaxWidth().height(120.dp)) {
        ToastHost(
            hostState = toastState,
            position = ToastPosition.BottomCenter,
            animation = ToastAnimation.SlideIn,
            showProgressBar = true,
        )
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
        CodeBlock(
            """
val toastState = rememberToastHostState()
val scope = rememberCoroutineScope()

// Show a toast
scope.launch {
    toastState.show(
        message = "File saved",
        variant = ToastVariant.Success,
    )
}

// With action button
scope.launch {
    toastState.show(
        message = "Item deleted",
        variant = ToastVariant.Destructive,
        actionLabel = "Undo",
        onAction = { undoDelete() },
    )
}

// Place ToastHost in your layout
Box(Modifier.fillMaxSize()) {
    MyScreen()
    ToastHost(
        hostState = toastState,
        position = ToastPosition.BottomRight,
        animation = ToastAnimation.SlideIn,
        showProgressBar = true,
    )
}
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "hostState", "ToastHostState", "required",
                    "State holder managing the toast queue.",
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier applied to the host container.",
                ),
                PropInfo(
                    "position", "ToastPosition", "BottomRight",
                    "Anchor position: TopCenter, TopRight, "
                        + "BottomCenter, BottomRight.",
                ),
                PropInfo(
                    "animation", "ToastAnimation", "SlideIn",
                    "Enter/exit animation: SlideIn, Fade, Scale, None.",
                ),
                PropInfo(
                    "maxVisibleToasts", "Int", "5",
                    "Max simultaneously visible toasts.",
                ),
                PropInfo(
                    "swipeToDismiss", "Boolean", "true",
                    "Enable horizontal swipe to dismiss.",
                ),
                PropInfo(
                    "showProgressBar", "Boolean", "false",
                    "Show countdown progress bar on each toast.",
                ),
            ),
        )
    }
}
