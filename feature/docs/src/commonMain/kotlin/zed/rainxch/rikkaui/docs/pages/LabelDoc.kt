package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.*
import rikkaui.feature.docs.generated.resources.Res
import zed.rainxch.rikkaui.components.ui.input.Input
import zed.rainxch.rikkaui.components.ui.label.Label
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.foundation.RikkaTheme

/**
 * Documentation page for the Label component.
 *
 * Demonstrates standard labels, required indicators,
 * disabled state, and pairing with form inputs.
 */
@Composable
fun LabelDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_label_name),
        description = stringResource(Res.string.label_page_desc),
    )

    // ─── Basic Labels Demo ──────────────────────────────────
    DocSection(stringResource(Res.string.label_section_examples)) {
        DemoBox {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.md,
                    ),
            ) {
                Label(text = stringResource(Res.string.label_demo_email))
                Label(
                    text = stringResource(Res.string.label_demo_password),
                    required = true,
                )
                Label(
                    text = stringResource(Res.string.label_demo_username),
                    disabled = true,
                )
            }
        }
    }

    // ─── With Input Demo ────────────────────────────────────
    DocSection(stringResource(Res.string.label_section_with_input)) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        DemoBox {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.md,
                    ),
                modifier = Modifier.width(320.dp),
            ) {
                Column(
                    verticalArrangement =
                        Arrangement.spacedBy(
                            RikkaTheme.spacing.xs,
                        ),
                ) {
                    Label(text = stringResource(Res.string.label_demo_email))
                    Input(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = stringResource(Res.string.label_demo_email_placeholder),
                    )
                }
                Column(
                    verticalArrangement =
                        Arrangement.spacedBy(
                            RikkaTheme.spacing.xs,
                        ),
                ) {
                    Label(
                        text = stringResource(Res.string.label_demo_password),
                        required = true,
                    )
                    Input(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = stringResource(Res.string.label_demo_password_placeholder),
                    )
                }
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection(stringResource(Res.string.section_usage)) {
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
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "text",
                    "String",
                    "-",
                    stringResource(Res.string.label_prop_text_desc),
                ),
                PropInfo(
                    "disabled",
                    "Boolean",
                    "false",
                    stringResource(Res.string.label_prop_disabled_desc),
                ),
                PropInfo(
                    "required",
                    "Boolean",
                    "false",
                    stringResource(Res.string.label_prop_required_desc),
                ),
            ),
        )
    }
}
