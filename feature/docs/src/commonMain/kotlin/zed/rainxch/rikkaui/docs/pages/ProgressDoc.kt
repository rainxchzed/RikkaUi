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
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.*
import rikkaui.feature.docs.generated.resources.Res
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
import zed.rainxch.rikkaui.foundation.RikkaTheme

/**
 * Documentation page for the Progress component.
 *
 * Demonstrates animated progress bars with different
 * animation styles and customization options.
 */
@Composable
fun ProgressDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_progress_name),
        description = stringResource(Res.string.progress_page_desc),
    )

    // ─── Animation Modes ────────────────────────────────────
    DocSection(stringResource(Res.string.progress_section_animation_modes)) {
        var selectedAnim by remember { mutableStateOf("Spring") }
        var progress by remember { mutableStateOf(0.6f) }

        VariantSelector(
            options = listOf("Spring", "Tween", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation =
            when (selectedAnim) {
                "Tween" -> ProgressAnimation.Tween
                "None" -> ProgressAnimation.None
                else -> ProgressAnimation.Spring
            }

        DemoBox {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(
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
                    Text(
                        stringResource(Res.string.progress_demo_advance),
                        color = color,
                    )
                }
            }
        }
    }

    // ─── Custom Height ──────────────────────────────────────
    DocSection(stringResource(Res.string.progress_section_custom_height)) {
        DemoBox {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(
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
    DocSection(stringResource(Res.string.section_usage)) {
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
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "progress",
                    "Float",
                    "required",
                    stringResource(Res.string.progress_prop_progress_desc),
                ),
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(Res.string.progress_prop_modifier_desc),
                ),
                PropInfo(
                    "trackColor",
                    "Color",
                    "Unspecified",
                    stringResource(Res.string.progress_prop_track_color_desc),
                ),
                PropInfo(
                    "fillColor",
                    "Color",
                    "Unspecified",
                    stringResource(Res.string.progress_prop_fill_color_desc),
                ),
                PropInfo(
                    "height",
                    "Dp",
                    "8.dp",
                    stringResource(Res.string.progress_prop_height_desc),
                ),
                PropInfo(
                    "animation",
                    "ProgressAnimation",
                    "Spring",
                    stringResource(Res.string.progress_prop_animation_desc),
                ),
                PropInfo(
                    "label",
                    "String",
                    "\"\"",
                    stringResource(Res.string.progress_prop_label_desc),
                ),
            ),
        )
    }
}
