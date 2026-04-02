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
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.*
import rikkaui.feature.docs.generated.resources.Res
import zed.rainxch.rikkaui.components.ui.kbd.Kbd
import zed.rainxch.rikkaui.components.ui.kbd.KbdCombo
import zed.rainxch.rikkaui.components.ui.kbd.KbdSize
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DoAndDont
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.TabbedDocPage
import zed.rainxch.rikkaui.docs.components.VariantSelector
import zed.rainxch.rikkaui.foundation.RikkaTheme

@Composable
fun KbdDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_kbd_name),
        description = stringResource(Res.string.kbd_page_desc),
    )

    TabbedDocPage(
        overview = { KbdOverviewTab() },
        usage = { KbdUsageTab() },
        api = { KbdApiTab() },
    )
}

// ─── Overview Tab ───────────────────────────────────────────

@Composable
private fun KbdOverviewTab() {
    DocSection(stringResource(Res.string.section_sizes)) {
        var selectedSize by remember { mutableStateOf("Default") }

        VariantSelector(
            options = listOf("Sm", "Default", "Lg"),
            selected = selectedSize,
            onSelect = { selectedSize = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val size =
            when (selectedSize) {
                "Sm" -> KbdSize.Sm
                "Lg" -> KbdSize.Lg
                else -> KbdSize.Default
            }

        DemoBox {
            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
            ) {
                Kbd(text = "Esc", size = size)
                Kbd(text = "Enter", size = size)
                Kbd(text = "\u2318K", size = size)
            }
        }
    }

    DocSection(stringResource(Res.string.kbd_section_combos)) {
        DemoBox {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(
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
}

// ─── Usage Tab ──────────────────────────────────────────────

@Composable
private fun KbdUsageTab() {
    DocSection(stringResource(Res.string.section_usage)) {
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

    DocSection(stringResource(Res.string.kbd_section_dos_donts)) {
        DoAndDont(
            doContent = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                ) {
                    KbdCombo(keys = listOf("Ctrl", "S"))
                }
            },
            doDescription = stringResource(Res.string.kbd_do_shortcuts_desc),
            dontContent = {
                Kbd(text = "myVariable")
            },
            dontDescription = stringResource(Res.string.kbd_dont_inline_code_desc),
        )
    }
}

// ─── API Tab ────────────────────────────────────────────────

@Composable
private fun KbdApiTab() {
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "text",
                    "String",
                    "required",
                    stringResource(Res.string.kbd_prop_text_desc),
                ),
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(Res.string.kbd_prop_modifier_desc),
                ),
                PropInfo(
                    "size",
                    "KbdSize",
                    "Default",
                    stringResource(Res.string.kbd_prop_size_desc),
                ),
            ),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        PropsTable(
            listOf(
                PropInfo(
                    "keys",
                    "List<String>",
                    "required",
                    stringResource(Res.string.kbd_prop_keys_desc),
                ),
                PropInfo(
                    "separator",
                    "String",
                    "\"+\"",
                    stringResource(Res.string.kbd_prop_separator_desc),
                ),
                PropInfo(
                    "size",
                    "KbdSize",
                    "Default",
                    stringResource(Res.string.kbd_prop_combo_size_desc),
                ),
            ),
        )
    }
}
