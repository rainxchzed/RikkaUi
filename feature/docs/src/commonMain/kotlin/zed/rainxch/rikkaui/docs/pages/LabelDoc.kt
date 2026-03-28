package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.input.Input
import zed.rainxch.rikkaui.components.ui.label.Label
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable

/**
 * Documentation page for the Label component.
 *
 * Demonstrates standard labels, required indicators,
 * disabled state, and pairing with form inputs.
 */
@Composable
fun LabelDoc() {
    ComponentPageHeader(
        name = "Label",
        description = "A text label for form elements with optional required " +
            "indicator and disabled state.",
    )

    // ─── Basic Labels Demo ──────────────────────────────────
    DocSection("Examples") {
        DemoBox {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.md,
                ),
            ) {
                Label(text = "Email")
                Label(text = "Password", required = true)
                Label(text = "Username", disabled = true)
            }
        }
    }

    // ─── With Input Demo ────────────────────────────────────
    DocSection("With Input") {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        DemoBox {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.md,
                ),
                modifier = Modifier.width(320.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        RikkaTheme.spacing.xs,
                    ),
                ) {
                    Label(text = "Email")
                    Input(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "you@example.com",
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        RikkaTheme.spacing.xs,
                    ),
                ) {
                    Label(text = "Password", required = true)
                    Input(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = "Enter password...",
                    )
                }
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
        CodeBlock(
            """
Label("Email")
Label("Username", disabled = true)
Label("Password", required = true)

// Paired with an Input
Column {
    Label("Email")
    Input(
        value = email,
        onValueChange = { email = it },
        placeholder = "you@example.com",
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
                    "text",
                    "String",
                    "-",
                    "The label text to display.",
                ),
                PropInfo(
                    "disabled",
                    "Boolean",
                    "false",
                    "When true, the label is dimmed with mutedForeground color.",
                ),
                PropInfo(
                    "required",
                    "Boolean",
                    "false",
                    "When true, a red asterisk (*) is appended.",
                ),
            ),
        )
    }
}
