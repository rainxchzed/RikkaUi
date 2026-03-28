package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.kbd.Kbd
import zed.rainxch.rikkaui.components.ui.kbd.KbdCombo
import zed.rainxch.rikkaui.components.ui.kbd.KbdSize
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector

/**
 * Documentation page for the Kbd component.
 *
 * Demonstrates keyboard shortcut indicators in various
 * sizes and the KbdCombo helper.
 */
@Composable
fun KbdDoc() {
    ComponentPageHeader(
        name = "Kbd",
        description = "A styled inline indicator for keyboard "
            + "shortcuts and key combinations.",
    )

    // ─── Sizes ──────────────────────────────────────────────
    DocSection("Sizes") {
        var selectedSize by remember { mutableStateOf("Default") }

        VariantSelector(
            options = listOf("Sm", "Default", "Lg"),
            selected = selectedSize,
            onSelect = { selectedSize = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val size = when (selectedSize) {
            "Sm" -> KbdSize.Sm
            "Lg" -> KbdSize.Lg
            else -> KbdSize.Default
        }

        DemoBox {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
            ) {
                Kbd(text = "Esc", size = size)
                Kbd(text = "Enter", size = size)
                Kbd(text = "\u2318K", size = size)
            }
        }
    }

    // ─── Kbd Combo ──────────────────────────────────────────
    DocSection("Key Combinations") {
        DemoBox {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.md,
                ),
            ) {
                KbdCombo(keys = listOf("Ctrl", "Shift", "P"))
                KbdCombo(keys = listOf("\u2318", "K"))
                KbdCombo(
                    keys = listOf("Alt", "F4"),
                    size = KbdSize.Lg,
                )
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
        CodeBlock(
            """
Kbd("\u2318K")
Kbd("Enter", size = KbdSize.Lg)

KbdCombo(keys = listOf("Ctrl", "Shift", "P"))
KbdCombo(
    keys = listOf("\u2318", "K"),
    separator = " ",
)
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "text", "String", "required",
                    "The key label to display (e.g. \"\u2318K\").",
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier for layout and decoration.",
                ),
                PropInfo(
                    "size", "KbdSize", "Default",
                    "Size variant: Sm, Default, Lg.",
                ),
            ),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        PropsTable(
            listOf(
                PropInfo(
                    "keys", "List<String>", "required",
                    "List of individual key labels for KbdCombo.",
                ),
                PropInfo(
                    "separator", "String", "\"+\"",
                    "Text displayed between keys.",
                ),
                PropInfo(
                    "size", "KbdSize", "Default",
                    "Size variant applied to each key badge.",
                ),
            ),
        )
    }
}
