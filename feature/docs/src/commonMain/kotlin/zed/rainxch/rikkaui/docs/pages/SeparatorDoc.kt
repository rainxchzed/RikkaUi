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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.separator.SeparatorOrientation
import zed.rainxch.rikkaui.components.ui.separator.SeparatorStyle
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
 * Documentation page for the Separator component.
 *
 * Demonstrates line styles (Solid, Dashed, Dotted),
 * orientations (Horizontal, Vertical), and custom thickness.
 */
@Composable
fun SeparatorDoc() {
    ComponentPageHeader(
        name = "Separator",
        description = "A thin line that visually divides content sections.",
    )

    // ─── Line Styles ────────────────────────────────────────
    DocSection("Line Styles") {
        var selectedStyle by remember { mutableStateOf("Solid") }

        VariantSelector(
            options = listOf("Solid", "Dashed", "Dotted"),
            selected = selectedStyle,
            onSelect = { selectedStyle = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val style = when (selectedStyle) {
            "Dashed" -> SeparatorStyle.Dashed
            "Dotted" -> SeparatorStyle.Dotted
            else -> SeparatorStyle.Solid
        }

        DemoBox {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.md,
                ),
            ) {
                Text("Content above", variant = TextVariant.P)
                Separator(style = style)
                Text("Content below", variant = TextVariant.P)
            }
        }
    }

    // ─── Vertical Orientation ───────────────────────────────
    DocSection("Vertical Orientation") {
        DemoBox {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.md,
                ),
            ) {
                Text("Left", variant = TextVariant.P)
                Separator(
                    orientation = SeparatorOrientation.Vertical,
                    modifier = Modifier.height(40.dp),
                )
                Text("Center", variant = TextVariant.P)
                Separator(
                    orientation = SeparatorOrientation.Vertical,
                    modifier = Modifier.height(40.dp),
                )
                Text("Right", variant = TextVariant.P)
            }
        }
    }

    // ─── Custom Thickness ───────────────────────────────────
    DocSection("Custom Thickness") {
        DemoBox {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.md,
                ),
            ) {
                Text("1dp (default)", variant = TextVariant.Small)
                Separator(thickness = 1.dp)

                Text("2dp", variant = TextVariant.Small)
                Separator(thickness = 2.dp)

                Text("4dp", variant = TextVariant.Small)
                Separator(thickness = 4.dp, style = SeparatorStyle.Dashed)
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
        CodeBlock(
            """
// Horizontal separator (default)
Column {
    Text("Section 1")
    Separator()
    Text("Section 2")
}

// Vertical separator in a Row
Row {
    Text("Left")
    Separator(
        orientation = SeparatorOrientation.Vertical,
    )
    Text("Right")
}

// Dashed style with custom thickness
Separator(
    style = SeparatorStyle.Dashed,
    thickness = 2.dp,
)
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
                    "orientation", "SeparatorOrientation",
                    "Horizontal",
                    "Horizontal (full-width) or Vertical (full-height).",
                ),
                PropInfo(
                    "color", "Color", "Color.Unspecified",
                    "Override color. Defaults to theme border color.",
                ),
                PropInfo(
                    "thickness", "Dp", "1.dp",
                    "Line thickness in dp.",
                ),
                PropInfo(
                    "style", "SeparatorStyle", "Solid",
                    "Line style: Solid, Dashed, or Dotted.",
                ),
            ),
        )
    }
}
