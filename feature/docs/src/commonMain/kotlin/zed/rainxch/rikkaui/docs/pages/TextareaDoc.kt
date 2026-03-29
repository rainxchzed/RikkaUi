package zed.rainxch.rikkaui.docs.pages

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
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.Res
import rikkaui.feature.docs.generated.resources.*
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.textarea.Textarea
import zed.rainxch.rikkaui.components.ui.textarea.TextareaAnimation
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector

/**
 * Documentation page for the Textarea component.
 *
 * Demonstrates focus animations, character count with max length,
 * configurable line counts, and disabled/read-only states.
 */
@Composable
fun TextareaDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_textarea_name),
        description = stringResource(Res.string.textarea_page_desc),
    )

    // ─── Focus Animations Demo ──────────────────────────────
    DocSection(stringResource(Res.string.textarea_section_focus)) {
        var selectedAnim by remember {
            mutableStateOf(TextareaAnimation.Glow.name)
        }

        VariantSelector(
            options = TextareaAnimation.entries.map { it.name },
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = TextareaAnimation.valueOf(selectedAnim)
        var text by remember { mutableStateOf("") }

        DemoBox {
            Textarea(
                value = text,
                onValueChange = { text = it },
                placeholder = stringResource(Res.string.textarea_demo_write_placeholder),
                animation = animation,
                modifier = Modifier.width(400.dp),
            )
        }
    }

    // ─── Character Count Demo ───────────────────────────────
    DocSection(stringResource(Res.string.textarea_section_char_count)) {
        var bio by remember { mutableStateOf("") }

        DemoBox {
            Textarea(
                value = bio,
                onValueChange = { bio = it },
                placeholder = stringResource(Res.string.textarea_demo_bio_placeholder),
                maxLength = 280,
                showCharCount = true,
                minLines = 4,
                modifier = Modifier.width(400.dp),
            )
        }
    }

    // ─── Disabled State ─────────────────────────────────────
    DocSection(stringResource(Res.string.section_disabled)) {
        DemoBox {
            Textarea(
                value = stringResource(Res.string.textarea_demo_disabled_value),
                onValueChange = {},
                enabled = false,
                modifier = Modifier.width(400.dp),
            )
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
var text by remember { mutableStateOf("") }

Textarea(
    value = text,
    onValueChange = { text = it },
    placeholder = "Write your message...",
)

Textarea(
    value = text,
    onValueChange = { text = it },
    placeholder = "Bio (max 280 chars)...",
    maxLength = 280,
    showCharCount = true,
    minLines = 4,
    maxLines = 8,
)
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "value",
                    "String",
                    "-",
                    stringResource(Res.string.textarea_prop_value_desc),
                ),
                PropInfo(
                    "onValueChange",
                    "(String) -> Unit",
                    "-",
                    stringResource(Res.string.textarea_prop_on_value_change_desc),
                ),
                PropInfo(
                    "placeholder",
                    "String",
                    "\"\"",
                    stringResource(Res.string.textarea_prop_placeholder_desc),
                ),
                PropInfo(
                    "animation",
                    "TextareaAnimation",
                    "Glow",
                    stringResource(Res.string.textarea_prop_animation_desc),
                ),
                PropInfo(
                    "minLines",
                    "Int",
                    "3",
                    stringResource(Res.string.textarea_prop_min_lines_desc),
                ),
                PropInfo(
                    "maxLines",
                    "Int",
                    "5",
                    stringResource(Res.string.textarea_prop_max_lines_desc),
                ),
                PropInfo(
                    "maxLength",
                    "Int?",
                    "null",
                    stringResource(Res.string.textarea_prop_max_length_desc),
                ),
                PropInfo(
                    "showCharCount",
                    "Boolean",
                    "false",
                    stringResource(Res.string.textarea_prop_show_char_count_desc),
                ),
                PropInfo(
                    "enabled",
                    "Boolean",
                    "true",
                    stringResource(Res.string.textarea_prop_enabled_desc),
                ),
                PropInfo(
                    "readOnly",
                    "Boolean",
                    "false",
                    stringResource(Res.string.textarea_prop_read_only_desc),
                ),
            ),
        )
    }
}
