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
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.*
import rikkaui.feature.docs.generated.resources.Res
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.separator.SeparatorOrientation
import zed.rainxch.rikkaui.components.ui.separator.SeparatorStyle
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

/**
 * Documentation page for the Separator component.
 *
 * Demonstrates line styles (Solid, Dashed, Dotted),
 * orientations (Horizontal, Vertical), and custom thickness.
 */
@Composable
fun SeparatorDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_separator_name),
        description = stringResource(Res.string.separator_page_desc),
    )

    TabbedDocPage(
        overview = { SeparatorOverviewTab() },
        usage = { SeparatorUsageTab() },
        api = { SeparatorApiTab() },
    )
}

// ─── Overview Tab ───────────────────────────────────────────

@Composable
private fun SeparatorOverviewTab() {
    // ─── Line Styles ────────────────────────────────────────
    DocSection(stringResource(Res.string.separator_section_line_styles)) {
        var selectedStyle by remember { mutableStateOf("Solid") }

        VariantSelector(
            options = listOf("Solid", "Dashed", "Dotted"),
            selected = selectedStyle,
            onSelect = { selectedStyle = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val style =
            when (selectedStyle) {
                "Dashed" -> SeparatorStyle.Dashed
                "Dotted" -> SeparatorStyle.Dotted
                else -> SeparatorStyle.Solid
            }

        DemoBox {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.md,
                    ),
            ) {
                Text(stringResource(Res.string.separator_demo_content_above), variant = TextVariant.P)
                Separator(style = style)
                Text(stringResource(Res.string.separator_demo_content_below), variant = TextVariant.P)
            }
        }
    }

    // ─── Vertical Orientation ───────────────────────────────
    DocSection(stringResource(Res.string.separator_section_vertical)) {
        DemoBox {
            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.md,
                    ),
            ) {
                Text(stringResource(Res.string.separator_demo_left), variant = TextVariant.P)
                Separator(
                    orientation = SeparatorOrientation.Vertical,
                    modifier = Modifier.height(40.dp),
                )
                Text(stringResource(Res.string.separator_demo_center), variant = TextVariant.P)
                Separator(
                    orientation = SeparatorOrientation.Vertical,
                    modifier = Modifier.height(40.dp),
                )
                Text(stringResource(Res.string.separator_demo_right), variant = TextVariant.P)
            }
        }
    }

    // ─── Custom Thickness ───────────────────────────────────
    DocSection(stringResource(Res.string.separator_section_thickness)) {
        DemoBox {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.md,
                    ),
            ) {
                Text(stringResource(Res.string.separator_demo_1dp_default), variant = TextVariant.Small)
                Separator(thickness = 1.dp)

                Text(stringResource(Res.string.separator_demo_2dp), variant = TextVariant.Small)
                Separator(thickness = 2.dp)

                Text(stringResource(Res.string.separator_demo_4dp), variant = TextVariant.Small)
                Separator(thickness = 4.dp, style = SeparatorStyle.Dashed)
            }
        }
    }
}

// ─── Usage Tab ──────────────────────────────────────────────

@Composable
private fun SeparatorUsageTab() {
    DocSection(stringResource(Res.string.section_usage)) {
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

    DocSection(stringResource(Res.string.separator_section_dos_donts)) {
        DoAndDont(
            doContent = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                ) {
                    Text("Profile", variant = TextVariant.P)
                    Separator()
                    Text("Settings", variant = TextVariant.P)
                    Separator()
                    Text("Notifications", variant = TextVariant.P)
                }
            },
            doDescription = stringResource(Res.string.separator_do_groups_desc),
            dontContent = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
                ) {
                    Text("Item 1", variant = TextVariant.Small)
                    Separator()
                    Text("Item 2", variant = TextVariant.Small)
                    Separator()
                    Text("Item 3", variant = TextVariant.Small)
                    Separator()
                    Text("Item 4", variant = TextVariant.Small)
                }
            },
            dontDescription = stringResource(Res.string.separator_dont_groups_desc),
        )
    }
}

// ─── API Tab ────────────────────────────────────────────────

@Composable
private fun SeparatorApiTab() {
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(Res.string.separator_prop_modifier_desc),
                ),
                PropInfo(
                    "orientation",
                    "SeparatorOrientation",
                    "Horizontal",
                    stringResource(Res.string.separator_prop_orientation_desc),
                ),
                PropInfo(
                    "color",
                    "Color",
                    "Color.Unspecified",
                    stringResource(Res.string.separator_prop_color_desc),
                ),
                PropInfo(
                    "thickness",
                    "Dp",
                    "1.dp",
                    stringResource(Res.string.separator_prop_thickness_desc),
                ),
                PropInfo(
                    "style",
                    "SeparatorStyle",
                    "Solid",
                    stringResource(Res.string.separator_prop_style_desc),
                ),
            ),
        )
    }
}
