package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.*
import rikkaui.feature.docs.generated.resources.Res
import zed.rainxch.rikkaui.components.ui.input.Input
import zed.rainxch.rikkaui.components.ui.slider.Slider
import zed.rainxch.rikkaui.components.ui.slider.SliderAnimation
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.DoAndDont
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.TabbedDocPage
import zed.rainxch.rikkaui.docs.components.VariantSelector
import zed.rainxch.rikkaui.foundation.RikkaTheme

/**
 * Documentation page for the Slider component.
 *
 * Demonstrates animation styles, custom sizes,
 * and disabled state with a draggable range input.
 */
@Composable
fun SliderDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_slider_name),
        description = stringResource(Res.string.slider_page_desc),
    )

    TabbedDocPage(
        overview = { SliderOverviewTab() },
        usage = { SliderUsageTab() },
        api = { SliderApiTab() },
    )
}

@Composable
private fun SliderOverviewTab() {
    // ─── Animations Demo ────────────────────────────────────
    DocSection(stringResource(Res.string.section_animations)) {
        var selectedAnim by remember {
            mutableStateOf(SliderAnimation.Spring.name)
        }

        VariantSelector(
            options = SliderAnimation.entries.map { it.name },
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = SliderAnimation.valueOf(selectedAnim)
        var value by remember { mutableFloatStateOf(0.5f) }

        DemoBox {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
            ) {
                Slider(
                    value = value,
                    onValueChange = { value = it },
                    animation = animation,
                    modifier = Modifier.width(320.dp),
                )
                Text(
                    text = "${(value * 100).toInt()}%",
                    variant = TextVariant.Small,
                    color = RikkaTheme.colors.onMuted,
                )
            }
        }
    }

    // ─── Custom Sizes Demo ──────────────────────────────────
    DocSection(stringResource(Res.string.slider_section_custom_sizes)) {
        var value by remember { mutableFloatStateOf(0.7f) }

        DemoBox {
            Slider(
                value = value,
                onValueChange = { value = it },
                thumbSize = 24.dp,
                trackHeight = 8.dp,
                modifier = Modifier.width(320.dp),
            )
        }
    }

    // ─── Disabled State ─────────────────────────────────────
    DocSection(stringResource(Res.string.section_disabled)) {
        DemoBox {
            Slider(
                value = 0.3f,
                onValueChange = {},
                enabled = false,
                modifier = Modifier.width(320.dp),
            )
        }
    }
}

@Composable
private fun SliderUsageTab() {
    // ─── Code Example ───────────────────────────────────────
    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
var volume by remember { mutableFloatStateOf(0.5f) }

Slider(
    value = volume,
    onValueChange = { volume = it },
)

Slider(
    value = volume,
    onValueChange = { volume = it },
    thumbSize = 24.dp,
    trackHeight = 8.dp,
    animation = SliderAnimation.Tween,
)
            """.trimIndent(),
        )
    }

    // ─── Do / Don't ─────────────────────────────────────────
    Spacer(Modifier.height(RikkaTheme.spacing.xl))

    var doValue by remember { mutableFloatStateOf(0.6f) }
    var dontText by remember { mutableStateOf("60") }

    DoAndDont(
        doContent = {
            Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm)) {
                Slider(
                    value = doValue,
                    onValueChange = { doValue = it },
                    modifier = Modifier.width(200.dp),
                )
                Text(
                    text = "${(doValue * 100).toInt()}%",
                    variant = TextVariant.Small,
                    color = RikkaTheme.colors.onMuted,
                )
            }
        },
        doDescription = stringResource(Res.string.slider_do_range_desc),
        dontContent = {
            Input(
                value = dontText,
                onValueChange = { dontText = it },
                placeholder = "0",
                modifier = Modifier.width(120.dp),
            )
        },
        dontDescription = stringResource(Res.string.slider_dont_precise_desc),
    )
}

@Composable
private fun SliderApiTab() {
    // ─── API Reference ──────────────────────────────────────
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "value",
                    "Float",
                    "-",
                    stringResource(Res.string.slider_prop_value_desc),
                ),
                PropInfo(
                    "onValueChange",
                    "(Float) -> Unit",
                    "-",
                    stringResource(Res.string.slider_prop_on_value_change_desc),
                ),
                PropInfo(
                    "animation",
                    "SliderAnimation",
                    "Spring",
                    stringResource(Res.string.slider_prop_animation_desc),
                ),
                PropInfo(
                    "thumbSize",
                    "Dp",
                    "20.dp",
                    stringResource(Res.string.slider_prop_thumb_size_desc),
                ),
                PropInfo(
                    "trackHeight",
                    "Dp",
                    "6.dp",
                    stringResource(Res.string.slider_prop_track_height_desc),
                ),
                PropInfo(
                    "trackColor",
                    "Color",
                    "Unspecified",
                    stringResource(Res.string.slider_prop_track_color_desc),
                ),
                PropInfo(
                    "fillColor",
                    "Color",
                    "Unspecified",
                    stringResource(Res.string.slider_prop_fill_color_desc),
                ),
                PropInfo(
                    "thumbColor",
                    "Color",
                    "Unspecified",
                    stringResource(Res.string.slider_prop_thumb_color_desc),
                ),
                PropInfo(
                    "thumbBorderColor",
                    "Color",
                    "Unspecified",
                    stringResource(Res.string.slider_prop_thumb_border_color_desc),
                ),
                PropInfo(
                    "enabled",
                    "Boolean",
                    "true",
                    stringResource(Res.string.slider_prop_enabled_desc),
                ),
                PropInfo(
                    "label",
                    "String",
                    "\"\"",
                    stringResource(Res.string.slider_prop_label_desc),
                ),
            ),
        )
    }
}
