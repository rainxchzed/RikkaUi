package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.toggle.Toggle
import zed.rainxch.rikkaui.components.ui.toggle.ToggleAnimation
import zed.rainxch.rikkaui.components.ui.toggle.ToggleSize
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector

/**
 * Documentation page for the Toggle component.
 *
 * Demonstrates animation styles, sizes, and disabled states
 * with spring-animated thumb and smooth color transitions.
 */
@Composable
fun ToggleDoc() {
    ComponentPageHeader(
        name = "Toggle",
        description = "A boolean on/off switch with spring-animated thumb " +
            "and smooth color transitions. Two sizes available.",
    )

    // ─── Animations Demo ────────────────────────────────────
    DocSection("Animations") {
        var selectedAnim by remember {
            mutableStateOf(ToggleAnimation.Spring.name)
        }

        VariantSelector(
            options = ToggleAnimation.entries.map { it.name },
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = ToggleAnimation.valueOf(selectedAnim)
        var checked by remember { mutableStateOf(false) }

        DemoBox {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
            ) {
                Text(
                    text = "Dark mode",
                    variant = TextVariant.Small,
                )
                Toggle(
                    checked = checked,
                    onCheckedChange = { checked = it },
                    animation = animation,
                )
            }
        }
    }

    // ─── Sizes Demo ─────────────────────────────────────────
    DocSection("Sizes") {
        var defaultChecked by remember { mutableStateOf(true) }
        var smChecked by remember { mutableStateOf(false) }

        DemoBox {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.md,
                ),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
                ) {
                    Text(
                        text = "Default",
                        variant = TextVariant.Small,
                        modifier = Modifier.width(RikkaTheme.spacing.xxxl),
                    )
                    Toggle(
                        checked = defaultChecked,
                        onCheckedChange = { defaultChecked = it },
                        size = ToggleSize.Default,
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
                ) {
                    Text(
                        text = "Small",
                        variant = TextVariant.Small,
                        modifier = Modifier.width(RikkaTheme.spacing.xxxl),
                    )
                    Toggle(
                        checked = smChecked,
                        onCheckedChange = { smChecked = it },
                        size = ToggleSize.Sm,
                    )
                }
            }
        }
    }

    // ─── Disabled State ─────────────────────────────────────
    DocSection("Disabled") {
        DemoBox {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
            ) {
                Toggle(
                    checked = true,
                    onCheckedChange = {},
                    enabled = false,
                    label = "On & disabled",
                )
                Toggle(
                    checked = false,
                    onCheckedChange = {},
                    enabled = false,
                    label = "Off & disabled",
                )
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
        CodeBlock(
            """
var enabled by remember { mutableStateOf(false) }

Toggle(
    checked = enabled,
    onCheckedChange = { enabled = it },
)

Toggle(
    checked = enabled,
    onCheckedChange = { enabled = it },
    size = ToggleSize.Sm,
    animation = ToggleAnimation.None,
)
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "checked",
                    "Boolean",
                    "-",
                    "Whether the toggle is on.",
                ),
                PropInfo(
                    "onCheckedChange",
                    "(Boolean) -> Unit",
                    "-",
                    "Called when the toggle state changes.",
                ),
                PropInfo(
                    "size",
                    "ToggleSize",
                    "Default",
                    "Size: Default (44x24dp), Sm (36x20dp).",
                ),
                PropInfo(
                    "animation",
                    "ToggleAnimation",
                    "Spring",
                    "Animation: Spring, Tween, None.",
                ),
                PropInfo(
                    "enabled",
                    "Boolean",
                    "true",
                    "Whether the toggle is interactive.",
                ),
                PropInfo(
                    "label",
                    "String",
                    "\"\"",
                    "Accessibility label for screen readers.",
                ),
            ),
        )
    }
}
