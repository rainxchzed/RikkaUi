package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.*
import rikkaui.feature.docs.generated.resources.Res
import zed.rainxch.rikkaui.components.ui.button.IconButton
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.scaffold.Scaffold
import zed.rainxch.rikkaui.components.ui.scaffold.ScaffoldWindowInsets
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.foundation.RikkaTheme

/**
 * Documentation page for the Scaffold component.
 *
 * Demonstrates slot-based page layout with top bar, bottom bar,
 * FAB, and content window insets.
 */
@Composable
fun ScaffoldDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_scaffold_name),
        description = stringResource(Res.string.scaffold_page_desc),
    )

    // ─── Basic Scaffold ─────────────────────────────────────
    DocSection(stringResource(Res.string.scaffold_section_basic)) {
        DemoBox {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .border(
                            1.dp,
                            RikkaTheme.colors.border,
                            RikkaTheme.shapes.md,
                        ),
            ) {
                Scaffold(
                    topBar = {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .background(RikkaTheme.colors.surface)
                                    .padding(RikkaTheme.spacing.md),
                        ) {
                            Text(
                                stringResource(Res.string.scaffold_demo_top_bar),
                                variant = TextVariant.H4,
                            )
                        }
                    },
                    bottomBar = {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .background(RikkaTheme.colors.surface)
                                    .padding(RikkaTheme.spacing.md),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                stringResource(Res.string.scaffold_demo_bottom_bar),
                                variant = TextVariant.Small,
                            )
                        }
                    },
                ) { paddingValues ->
                    Column(
                        modifier =
                            Modifier
                                .padding(paddingValues)
                                .padding(RikkaTheme.spacing.md),
                    ) {
                        Text(
                            stringResource(Res.string.scaffold_demo_content_padding),
                            variant = TextVariant.P,
                        )
                    }
                }
            }
        }
    }

    // ─── With FAB ───────────────────────────────────────────
    DocSection(stringResource(Res.string.scaffold_section_fab)) {
        DemoBox {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .border(
                            1.dp,
                            RikkaTheme.colors.border,
                            RikkaTheme.shapes.md,
                        ),
            ) {
                Scaffold(
                    topBar = {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .background(RikkaTheme.colors.surface)
                                    .padding(RikkaTheme.spacing.md),
                        ) {
                            Text(
                                stringResource(Res.string.scaffold_demo_my_app),
                                variant = TextVariant.H4,
                            )
                        }
                    },
                    floatingActionButton = {
                        IconButton(
                            icon = RikkaIcons.Plus,
                            contentDescription = stringResource(Res.string.scaffold_demo_add),
                            onClick = {},
                        )
                    },
                ) { paddingValues ->
                    Column(
                        modifier =
                            Modifier
                                .padding(paddingValues)
                                .padding(RikkaTheme.spacing.md),
                    ) {
                        Text(
                            stringResource(Res.string.scaffold_demo_fab_position),
                            variant = TextVariant.P,
                        )
                    }
                }
            }
        }
    }

    // ─── With Window Insets ─────────────────────────────────
    DocSection(stringResource(Res.string.scaffold_section_insets)) {
        DemoBox {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .border(
                            1.dp,
                            RikkaTheme.colors.border,
                            RikkaTheme.shapes.md,
                        ),
            ) {
                Scaffold(
                    contentWindowInsets =
                        ScaffoldWindowInsets(
                            left = 16.dp,
                            right = 16.dp,
                            bottom = 24.dp,
                        ),
                ) { paddingValues ->
                    Column(
                        modifier = Modifier.padding(paddingValues),
                    ) {
                        Text(
                            stringResource(Res.string.scaffold_demo_custom_insets),
                            variant = TextVariant.P,
                        )
                    }
                }
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
Scaffold(
    topBar = {
        Box(
            Modifier
                .fillMaxWidth()
                .background(RikkaTheme.colors.surface)
                .padding(RikkaTheme.spacing.md),
        ) {
            Text("My App", variant = TextVariant.H4)
        }
    },
    bottomBar = { /* footer content */ },
    floatingActionButton = {
        Button(onClick = {}, size = ButtonSize.Icon) {
            Icon(RikkaIcons.Plus, "Add")
        }
    },
    contentWindowInsets = ScaffoldWindowInsets(
        left = 16.dp, right = 16.dp,
    ),
) { paddingValues ->
    Column(Modifier.padding(paddingValues)) {
        Text("Page content goes here")
    }
}
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
                    stringResource(Res.string.scaffold_prop_modifier_desc),
                ),
                PropInfo(
                    "topBar",
                    "() -> Unit",
                    "{}",
                    stringResource(Res.string.scaffold_prop_top_bar_desc),
                ),
                PropInfo(
                    "bottomBar",
                    "() -> Unit",
                    "{}",
                    stringResource(Res.string.scaffold_prop_bottom_bar_desc),
                ),
                PropInfo(
                    "floatingActionButton",
                    "() -> Unit",
                    "{}",
                    stringResource(Res.string.scaffold_prop_fab_desc),
                ),
                PropInfo(
                    "snackbarHost",
                    "() -> Unit",
                    "{}",
                    stringResource(Res.string.scaffold_prop_snackbar_desc),
                ),
                PropInfo(
                    "containerColor",
                    "Color",
                    "background",
                    stringResource(Res.string.scaffold_prop_container_color_desc),
                ),
                PropInfo(
                    "contentColor",
                    "Color",
                    "foreground",
                    stringResource(Res.string.scaffold_prop_content_color_desc),
                ),
                PropInfo(
                    "contentWindowInsets",
                    "ScaffoldWindowInsets",
                    "ScaffoldWindowInsets()",
                    stringResource(Res.string.scaffold_prop_insets_desc),
                ),
                PropInfo(
                    "content",
                    "(PaddingValues) -> Unit",
                    "required",
                    stringResource(Res.string.scaffold_prop_content_desc),
                ),
            ),
        )
    }
}
