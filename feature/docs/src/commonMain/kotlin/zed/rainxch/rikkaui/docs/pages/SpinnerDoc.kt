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
import zed.rainxch.rikkaui.components.theme.RikkaTheme
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

/**
 * Documentation page for the Spinner component.
 *
 * Demonstrates spinner sizes, animations, and
 * customization options.
 */
@Composable
fun SpinnerDoc() {
    ComponentPageHeader(
        name = "Spinner",
        description = "A circular loading indicator with configurable "
            + "animation, size, and color.",
    )

    // ─── Sizes ──────────────────────────────────────────────
    DocSection("Sizes") {
        DemoBox {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
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
    DocSection("Animations") {
        var selectedAnim by remember { mutableStateOf("Spin") }

        VariantSelector(
            options = listOf("Spin", "Pulse", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = when (selectedAnim) {
            "Pulse" -> SpinnerAnimation.Pulse
            "None" -> SpinnerAnimation.None
            else -> SpinnerAnimation.Spin
        }

        DemoBox {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
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
    DocSection("Custom Sweep Angle") {
        DemoBox {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
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
    DocSection("Usage") {
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
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier for layout and decoration.",
                ),
                PropInfo(
                    "size", "SpinnerSize", "Default",
                    "Size variant: Sm (16dp), Default (24dp), Lg (32dp).",
                ),
                PropInfo(
                    "animation", "SpinnerAnimation", "Spin",
                    "Animation style: Spin, Pulse, None.",
                ),
                PropInfo(
                    "color", "Color", "primary",
                    "Arc color. Defaults to theme primary.",
                ),
                PropInfo(
                    "trackColor", "Color?", "null",
                    "Background track ring color. Null for no track.",
                ),
                PropInfo(
                    "sweepAngle", "Float", "270f",
                    "Arc sweep angle in degrees.",
                ),
                PropInfo(
                    "label", "String", "\"Loading\"",
                    "Accessibility content description.",
                ),
            ),
        )
    }
}
