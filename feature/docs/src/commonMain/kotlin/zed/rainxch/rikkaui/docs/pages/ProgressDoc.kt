package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.progress.Progress
import zed.rainxch.rikkaui.components.ui.progress.ProgressAnimation
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector

/**
 * Documentation page for the Progress component.
 *
 * Demonstrates animated progress bars with different
 * animation styles and customization options.
 */
@Composable
fun ProgressDoc() {
    ComponentPageHeader(
        name = "Progress",
        description = "An animated horizontal progress bar that fills "
            + "from left to right to indicate completion.",
    )

    // ─── Animation Modes ────────────────────────────────────
    DocSection("Animation Modes") {
        var selectedAnim by remember { mutableStateOf("Spring") }
        var progress by remember { mutableStateOf(0.6f) }

        VariantSelector(
            options = listOf("Spring", "Tween", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = when (selectedAnim) {
            "Tween" -> ProgressAnimation.Tween
            "None" -> ProgressAnimation.None
            else -> ProgressAnimation.Spring
        }

        DemoBox {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.md,
                ),
            ) {
                Progress(
                    progress = progress,
                    animation = animation,
                    modifier = Modifier.fillMaxWidth(),
                )
                Button(onClick = {
                    progress = if (progress >= 1f) 0.1f else progress + 0.2f
                }) { color ->
                    Text("Advance", color = color)
                }
            }
        }
    }

    // ─── Custom Height ──────────────────────────────────────
    DocSection("Custom Height") {
        DemoBox {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.md,
                ),
            ) {
                Progress(
                    progress = 0.4f,
                    height = 4.dp,
                    modifier = Modifier.fillMaxWidth(),
                )
                Progress(
                    progress = 0.6f,
                    height = 8.dp,
                    modifier = Modifier.fillMaxWidth(),
                )
                Progress(
                    progress = 0.8f,
                    height = 12.dp,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
        CodeBlock(
            """
Progress(progress = 0.6f)

// With animation style
Progress(
    progress = 0.8f,
    animation = ProgressAnimation.Tween,
)

// Custom colors and height
Progress(
    progress = 0.5f,
    trackColor = RikkaTheme.colors.secondary,
    fillColor = RikkaTheme.colors.destructive,
    height = 12.dp,
)
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "progress", "Float", "required",
                    "Current progress value, clamped to 0f..1f.",
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier for layout and decoration.",
                ),
                PropInfo(
                    "trackColor", "Color", "Unspecified",
                    "Background track color. Defaults to theme muted.",
                ),
                PropInfo(
                    "fillColor", "Color", "Unspecified",
                    "Fill bar color. Defaults to theme primary.",
                ),
                PropInfo(
                    "height", "Dp", "8.dp",
                    "Height of the progress bar.",
                ),
                PropInfo(
                    "animation", "ProgressAnimation", "Spring",
                    "Animation style: Spring, Tween, None.",
                ),
                PropInfo(
                    "label", "String", "\"\"",
                    "Accessibility label for screen readers.",
                ),
            ),
        )
    }
}
