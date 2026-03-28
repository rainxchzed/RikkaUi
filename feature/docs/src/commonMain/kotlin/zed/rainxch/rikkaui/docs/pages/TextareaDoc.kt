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
        name = "Textarea",
        description = "A multi-line text input with animated focus states " +
            "and optional character count.",
    )

    // ─── Focus Animations Demo ──────────────────────────────
    DocSection("Focus Animations") {
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
                placeholder = "Write something...",
                animation = animation,
                modifier = Modifier.width(400.dp),
            )
        }
    }

    // ─── Character Count Demo ───────────────────────────────
    DocSection("Character Count") {
        var bio by remember { mutableStateOf("") }

        DemoBox {
            Textarea(
                value = bio,
                onValueChange = { bio = it },
                placeholder = "Bio (max 280 chars)...",
                maxLength = 280,
                showCharCount = true,
                minLines = 4,
                modifier = Modifier.width(400.dp),
            )
        }
    }

    // ─── Disabled State ─────────────────────────────────────
    DocSection("Disabled") {
        DemoBox {
            Textarea(
                value = "This textarea is disabled.",
                onValueChange = {},
                enabled = false,
                modifier = Modifier.width(400.dp),
            )
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
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
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "value",
                    "String",
                    "-",
                    "Current text value.",
                ),
                PropInfo(
                    "onValueChange",
                    "(String) -> Unit",
                    "-",
                    "Called when the text changes.",
                ),
                PropInfo(
                    "placeholder",
                    "String",
                    "\"\"",
                    "Placeholder text shown when empty.",
                ),
                PropInfo(
                    "animation",
                    "TextareaAnimation",
                    "Glow",
                    "Focus animation: Glow, Color, None.",
                ),
                PropInfo(
                    "minLines",
                    "Int",
                    "3",
                    "Minimum number of visible lines.",
                ),
                PropInfo(
                    "maxLines",
                    "Int",
                    "5",
                    "Maximum number of visible lines.",
                ),
                PropInfo(
                    "maxLength",
                    "Int?",
                    "null",
                    "Maximum character limit.",
                ),
                PropInfo(
                    "showCharCount",
                    "Boolean",
                    "false",
                    "Displays character count when maxLength is set.",
                ),
                PropInfo(
                    "enabled",
                    "Boolean",
                    "true",
                    "Whether the textarea is interactive.",
                ),
                PropInfo(
                    "readOnly",
                    "Boolean",
                    "false",
                    "Whether the textarea is read-only.",
                ),
            ),
        )
    }
}
