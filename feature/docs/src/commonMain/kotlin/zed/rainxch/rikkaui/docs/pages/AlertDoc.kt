package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.alert.Alert
import zed.rainxch.rikkaui.components.ui.alert.AlertAnimation
import zed.rainxch.rikkaui.components.ui.alert.AlertDescription
import zed.rainxch.rikkaui.components.ui.alert.AlertTitle
import zed.rainxch.rikkaui.components.ui.alert.AlertVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector

/**
 * Documentation page for the Alert component.
 *
 * Demonstrates alert variants, animations, and structured
 * sections (AlertTitle, AlertDescription).
 */
@Composable
fun AlertDoc() {
    ComponentPageHeader(
        name = "Alert",
        description = "A status container for displaying important "
            + "messages with optional icon and entrance animation.",
    )

    // ─── Variants ───────────────────────────────────────────
    DocSection("Variants") {
        var selectedVariant by remember { mutableStateOf("Default") }

        VariantSelector(
            options = listOf("Default", "Destructive"),
            selected = selectedVariant,
            onSelect = { selectedVariant = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val variant = when (selectedVariant) {
            "Destructive" -> AlertVariant.Destructive
            else -> AlertVariant.Default
        }

        DemoBox {
            Alert(
                variant = variant,
                modifier = Modifier.fillMaxWidth(),
            ) {
                AlertTitle(
                    text = if (variant == AlertVariant.Destructive) {
                        "Error"
                    } else {
                        "Heads up!"
                    },
                    variant = variant,
                )
                AlertDescription(
                    text = if (variant == AlertVariant.Destructive) {
                        "Your session has expired. Please log in again."
                    } else {
                        "You can add components using the CLI."
                    },
                    variant = variant,
                )
            }
        }
    }

    // ─── Animations ─────────────────────────────────────────
    DocSection("Animations") {
        var selectedAnim by remember { mutableStateOf("None") }

        VariantSelector(
            options = listOf("SlideIn", "Fade", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = when (selectedAnim) {
            "SlideIn" -> AlertAnimation.SlideIn
            "Fade" -> AlertAnimation.Fade
            else -> AlertAnimation.None
        }

        DemoBox {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.md,
                ),
            ) {
                Alert(
                    animation = animation,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    AlertTitle("New update available")
                    AlertDescription(
                        "A new version has been released.",
                    )
                }
                Alert(
                    variant = AlertVariant.Destructive,
                    animation = animation,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    AlertTitle(
                        "Connection lost",
                        variant = AlertVariant.Destructive,
                    )
                    AlertDescription(
                        "Please check your network settings.",
                        variant = AlertVariant.Destructive,
                    )
                }
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
        CodeBlock(
            """
Alert {
    AlertTitle("Heads up!")
    AlertDescription("You can add components using the CLI.")
}

Alert(variant = AlertVariant.Destructive) {
    AlertTitle("Error", variant = AlertVariant.Destructive)
    AlertDescription(
        "Your session has expired.",
        variant = AlertVariant.Destructive,
    )
}

// With animation
Alert(animation = AlertAnimation.SlideIn) {
    AlertTitle("New message")
    AlertDescription("You have 3 unread messages.")
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
                    "Modifier for layout and decoration.",
                ),
                PropInfo(
                    "variant", "AlertVariant", "Default",
                    "Visual variant: Default, Destructive.",
                ),
                PropInfo(
                    "animation", "AlertAnimation", "None",
                    "Entrance animation: SlideIn, Fade, None.",
                ),
                PropInfo(
                    "icon", "(@Composable () -> Unit)?", "null",
                    "Optional leading icon composable (16-20dp).",
                ),
                PropInfo(
                    "label", "String", "\"\"",
                    "Accessibility label for screen readers.",
                ),
                PropInfo(
                    "content", "ColumnScope.() -> Unit", "required",
                    "Content: use AlertTitle + AlertDescription.",
                ),
            ),
        )
    }
}
