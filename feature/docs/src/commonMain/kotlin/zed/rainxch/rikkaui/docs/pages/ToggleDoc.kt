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
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.*
import rikkaui.feature.docs.generated.resources.Res
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
import zed.rainxch.rikkaui.foundation.RikkaTheme

/**
 * Documentation page for the Toggle component.
 *
 * Demonstrates animation styles, sizes, and disabled states
 * with spring-animated thumb and smooth color transitions.
 */
@Composable
fun ToggleDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_toggle_name),
        description = stringResource(Res.string.toggle_page_desc),
    )

    // ─── Animations Demo ────────────────────────────────────
    DocSection(stringResource(Res.string.section_animations)) {
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
                horizontalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
            ) {
                Text(
                    text = stringResource(Res.string.toggle_demo_dark_mode),
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
    DocSection(stringResource(Res.string.section_sizes)) {
        var defaultChecked by remember { mutableStateOf(true) }
        var smChecked by remember { mutableStateOf(false) }

        DemoBox {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.md,
                    ),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement =
                        Arrangement.spacedBy(
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
                    horizontalArrangement =
                        Arrangement.spacedBy(
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
    DocSection(stringResource(Res.string.section_disabled)) {
        DemoBox {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
            ) {
                Toggle(
                    checked = true,
                    onCheckedChange = {},
                    enabled = false,
                    label = stringResource(Res.string.toggle_demo_on_disabled),
                )
                Toggle(
                    checked = false,
                    onCheckedChange = {},
                    enabled = false,
                    label = stringResource(Res.string.toggle_demo_off_disabled),
                )
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection(stringResource(Res.string.section_usage)) {
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
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "checked",
                    "Boolean",
                    "-",
                    stringResource(Res.string.toggle_prop_checked_desc),
                ),
                PropInfo(
                    "onCheckedChange",
                    "(Boolean) -> Unit",
                    "-",
                    stringResource(Res.string.toggle_prop_on_checked_change_desc),
                ),
                PropInfo(
                    "size",
                    "ToggleSize",
                    "Default",
                    stringResource(Res.string.toggle_prop_size_desc),
                ),
                PropInfo(
                    "animation",
                    "ToggleAnimation",
                    "Spring",
                    stringResource(Res.string.toggle_prop_animation_desc),
                ),
                PropInfo(
                    "enabled",
                    "Boolean",
                    "true",
                    stringResource(Res.string.toggle_prop_enabled_desc),
                ),
                PropInfo(
                    "label",
                    "String",
                    "\"\"",
                    stringResource(Res.string.toggle_prop_label_desc),
                ),
            ),
        )
    }
}
