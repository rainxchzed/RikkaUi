package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.icon.Icon
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

/**
 * Documentation page for the Scaffold component.
 *
 * Demonstrates slot-based page layout with top bar, bottom bar,
 * FAB, and content window insets.
 */
@Composable
fun ScaffoldDoc() {
    ComponentPageHeader(
        name = "Scaffold",
        description = "Page-level slot-based layout structure with top bar, "
            + "bottom bar, FAB, and snackbar host.",
    )

    // ─── Basic Scaffold ─────────────────────────────────────
    DocSection("Basic Layout") {
        DemoBox {
            Box(
                modifier = Modifier
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(RikkaTheme.colors.card)
                                .padding(RikkaTheme.spacing.md),
                        ) {
                            Text("Top Bar", variant = TextVariant.H4)
                        }
                    },
                    bottomBar = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(RikkaTheme.colors.card)
                                .padding(RikkaTheme.spacing.md),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                "Bottom Bar",
                                variant = TextVariant.Small,
                            )
                        }
                    },
                ) { paddingValues ->
                    Column(
                        modifier = Modifier.padding(paddingValues)
                            .padding(RikkaTheme.spacing.md),
                    ) {
                        Text(
                            "Content area receives PaddingValues "
                                + "that account for bar heights.",
                            variant = TextVariant.P,
                        )
                    }
                }
            }
        }
    }

    // ─── With FAB ───────────────────────────────────────────
    DocSection("With Floating Action Button") {
        DemoBox {
            Box(
                modifier = Modifier
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(RikkaTheme.colors.card)
                                .padding(RikkaTheme.spacing.md),
                        ) {
                            Text("My App", variant = TextVariant.H4)
                        }
                    },
                    floatingActionButton = {
                        Button(
                            onClick = {},
                            size = ButtonSize.Icon,
                        ) {
                            Icon(RikkaIcons.Plus, "Add")
                        }
                    },
                ) { paddingValues ->
                    Column(
                        modifier = Modifier.padding(paddingValues)
                            .padding(RikkaTheme.spacing.md),
                    ) {
                        Text(
                            "The FAB is positioned at the bottom-end "
                                + "with theme-based padding.",
                            variant = TextVariant.P,
                        )
                    }
                }
            }
        }
    }

    // ─── With Window Insets ─────────────────────────────────
    DocSection("Content Window Insets") {
        DemoBox {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .border(
                        1.dp,
                        RikkaTheme.colors.border,
                        RikkaTheme.shapes.md,
                    ),
            ) {
                Scaffold(
                    contentWindowInsets = ScaffoldWindowInsets(
                        left = 16.dp,
                        right = 16.dp,
                        bottom = 24.dp,
                    ),
                ) { paddingValues ->
                    Column(
                        modifier = Modifier.padding(paddingValues),
                    ) {
                        Text(
                            "Content respects custom insets "
                                + "(16dp left/right, 24dp bottom).",
                            variant = TextVariant.P,
                        )
                    }
                }
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
        CodeBlock(
            """
Scaffold(
    topBar = {
        Box(
            Modifier
                .fillMaxWidth()
                .background(RikkaTheme.colors.card)
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
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier applied to the outer container.",
                ),
                PropInfo(
                    "topBar", "() -> Unit", "{}",
                    "Slot rendered at the top, spanning full width.",
                ),
                PropInfo(
                    "bottomBar", "() -> Unit", "{}",
                    "Slot rendered at the bottom, spanning full width.",
                ),
                PropInfo(
                    "floatingActionButton", "() -> Unit", "{}",
                    "FAB slot at bottom-end above the bottom bar.",
                ),
                PropInfo(
                    "snackbarHost", "() -> Unit", "{}",
                    "Snackbar/toast host above bottom bar and FAB.",
                ),
                PropInfo(
                    "containerColor", "Color", "background",
                    "Background color for the scaffold.",
                ),
                PropInfo(
                    "contentColor", "Color", "foreground",
                    "Foreground color hint for content.",
                ),
                PropInfo(
                    "contentWindowInsets", "ScaffoldWindowInsets",
                    "ScaffoldWindowInsets()",
                    "Insets added to content PaddingValues.",
                ),
                PropInfo(
                    "content", "(PaddingValues) -> Unit", "required",
                    "Main content. Receives PaddingValues for bars + insets.",
                ),
            ),
        )
    }
}
