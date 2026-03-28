package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.slider.Slider
import zed.rainxch.rikkaui.components.ui.slider.SliderAnimation
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector

/**
 * Documentation page for the Slider component.
 *
 * Demonstrates animation styles, custom sizes,
 * and disabled state with a draggable range input.
 */
@Composable
fun SliderDoc() {
    ComponentPageHeader(
        name = "Slider",
        description = "A draggable range input for selecting a value between " +
            "0 and 1. Supports tap-to-seek and configurable animations.",
    )

    // ─── Animations Demo ────────────────────────────────────
    DocSection("Animations") {
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
                verticalArrangement = Arrangement.spacedBy(
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
                    color = RikkaTheme.colors.mutedForeground,
                )
            }
        }
    }

    // ─── Custom Sizes Demo ──────────────────────────────────
    DocSection("Custom Sizes") {
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
    DocSection("Disabled") {
        DemoBox {
            Slider(
                value = 0.3f,
                onValueChange = {},
                enabled = false,
                modifier = Modifier.width(320.dp),
            )
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
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

    // ─── API Reference ──────────────────────────────────────
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "value",
                    "Float",
                    "-",
                    "Current value, clamped to 0f..1f.",
                ),
                PropInfo(
                    "onValueChange",
                    "(Float) -> Unit",
                    "-",
                    "Called when the user drags or taps to change the value.",
                ),
                PropInfo(
                    "animation",
                    "SliderAnimation",
                    "Spring",
                    "Animation: Spring, Tween, None.",
                ),
                PropInfo(
                    "thumbSize",
                    "Dp",
                    "20.dp",
                    "Diameter of the thumb circle.",
                ),
                PropInfo(
                    "trackHeight",
                    "Dp",
                    "6.dp",
                    "Height of the track bar.",
                ),
                PropInfo(
                    "trackColor",
                    "Color",
                    "Unspecified",
                    "Background track color. Defaults to theme muted.",
                ),
                PropInfo(
                    "fillColor",
                    "Color",
                    "Unspecified",
                    "Filled track color. Defaults to theme primary.",
                ),
                PropInfo(
                    "thumbColor",
                    "Color",
                    "Unspecified",
                    "Thumb fill color. Defaults to theme background.",
                ),
                PropInfo(
                    "enabled",
                    "Boolean",
                    "true",
                    "Whether the slider is interactive.",
                ),
            ),
        )
    }
}
