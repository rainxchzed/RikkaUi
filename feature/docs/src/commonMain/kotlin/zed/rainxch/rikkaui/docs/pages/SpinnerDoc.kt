package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
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
import zed.rainxch.rikkaui.components.ui.spinner.Spinner
import zed.rainxch.rikkaui.components.ui.spinner.SpinnerAnimation
import zed.rainxch.rikkaui.components.ui.spinner.SpinnerSize
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector
import zed.rainxch.rikkaui.foundation.RikkaTheme

/**
 * Documentation page for the Spinner component.
 *
 * Demonstrates spinner sizes, animations, and
 * customization options.
 */
@Composable
fun SpinnerDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_spinner_name),
        description = stringResource(Res.string.spinner_page_desc),
    )

    // ─── Sizes ──────────────────────────────────────────────
    DocSection(stringResource(Res.string.section_sizes)) {
        DemoBox {
            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.lg,
                    ),
            ) {
                Spinner(size = SpinnerSize.Sm)
                Spinner(size = SpinnerSize.Default)
                Spinner(size = SpinnerSize.Lg)
            }
        }
    }

    // ─── Animations ─────────────────────────────────────────
    DocSection(stringResource(Res.string.section_animations)) {
        var selectedAnim by remember { mutableStateOf("Spin") }

        VariantSelector(
            options = listOf("Spin", "Pulse", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation =
            when (selectedAnim) {
                "Pulse" -> SpinnerAnimation.Pulse
                "None" -> SpinnerAnimation.None
                else -> SpinnerAnimation.Spin
            }

        DemoBox {
            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.lg,
                    ),
            ) {
                Spinner(animation = animation)
                Spinner(
                    animation = animation,
                    size = SpinnerSize.Lg,
                    trackColor = RikkaTheme.colors.muted,
                )
            }
        }
    }

    // ─── Custom Sweep Angle ─────────────────────────────────
    DocSection(stringResource(Res.string.spinner_section_custom_sweep)) {
        DemoBox {
            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.lg,
                    ),
            ) {
                Spinner(sweepAngle = 90f, size = SpinnerSize.Lg)
                Spinner(sweepAngle = 180f, size = SpinnerSize.Lg)
                Spinner(sweepAngle = 270f, size = SpinnerSize.Lg)
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
Spinner()
Spinner(size = SpinnerSize.Lg)
Spinner(animation = SpinnerAnimation.Pulse)

// With track ring and custom color
Spinner(
    size = SpinnerSize.Lg,
    color = RikkaTheme.colors.destructive,
    trackColor = RikkaTheme.colors.muted,
)

// Custom sweep angle
Spinner(sweepAngle = 180f)
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(Res.string.spinner_prop_modifier_desc),
                ),
                PropInfo(
                    "size",
                    "SpinnerSize",
                    "Default",
                    stringResource(Res.string.spinner_prop_size_desc),
                ),
                PropInfo(
                    "animation",
                    "SpinnerAnimation",
                    "Spin",
                    stringResource(Res.string.spinner_prop_animation_desc),
                ),
                PropInfo(
                    "color",
                    "Color",
                    "Unspecified",
                    stringResource(Res.string.spinner_prop_color_desc),
                ),
                PropInfo(
                    "trackColor",
                    "Color?",
                    "colors.muted",
                    stringResource(Res.string.spinner_prop_track_color_desc),
                ),
                PropInfo(
                    "sweepAngle",
                    "Float",
                    "240f",
                    stringResource(Res.string.spinner_prop_sweep_angle_desc),
                ),
                PropInfo(
                    "label",
                    "String",
                    "\"Loading\"",
                    stringResource(Res.string.spinner_prop_label_desc),
                ),
            ),
        )
    }
}
