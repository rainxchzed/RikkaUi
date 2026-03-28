package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.scrollarea.HorizontalScrollArea
import zed.rainxch.rikkaui.components.ui.scrollarea.ScrollArea
import zed.rainxch.rikkaui.components.ui.scrollarea.ScrollbarAnimation
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
 * Documentation page for the ScrollArea component.
 *
 * Demonstrates vertical and horizontal scroll areas with
 * different scrollbar animation modes.
 */
@Composable
fun ScrollAreaDoc() {
    ComponentPageHeader(
        name = "ScrollArea",
        description = "Scrollable container with a custom scrollbar indicator.",
    )

    // ─── Scrollbar Animations ───────────────────────────────
    DocSection("Scrollbar Animations") {
        var selectedAnim by remember { mutableStateOf("Fade") }

        VariantSelector(
            options = listOf("Fade", "Always", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = when (selectedAnim) {
            "Always" -> ScrollbarAnimation.Always
            "None" -> ScrollbarAnimation.None
            else -> ScrollbarAnimation.Fade
        }

        DemoBox {
            ScrollArea(
                modifier = Modifier.fillMaxWidth().height(200.dp),
                scrollbarAnimation = animation,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
                    modifier = Modifier.padding(RikkaTheme.spacing.sm),
                ) {
                    repeat(20) { index ->
                        Text(
                            "Item ${index + 1}",
                            variant = TextVariant.P,
                        )
                    }
                }
            }
        }
    }

    // ─── Horizontal ScrollArea ──────────────────────────────
    DocSection("Horizontal ScrollArea") {
        DemoBox {
            HorizontalScrollArea(
                modifier = Modifier.fillMaxWidth().height(80.dp),
                scrollbarAnimation = ScrollbarAnimation.Always,
            ) {
                repeat(15) { index ->
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .padding(RikkaTheme.spacing.xs)
                            .background(
                                RikkaTheme.colors.muted,
                                RikkaTheme.shapes.md,
                            )
                            .padding(RikkaTheme.spacing.sm),
                    ) {
                        Text(
                            "${index + 1}",
                            variant = TextVariant.Small,
                        )
                    }
                }
            }
        }
    }

    // ─── Custom Scrollbar ───────────────────────────────────
    DocSection("Custom Scrollbar") {
        DemoBox {
            ScrollArea(
                modifier = Modifier.fillMaxWidth().height(160.dp),
                scrollbarAnimation = ScrollbarAnimation.Always,
                scrollbarWidth = 6.dp,
                scrollbarColor = RikkaTheme.colors.primary,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
                    modifier = Modifier.padding(RikkaTheme.spacing.sm),
                ) {
                    repeat(15) { index ->
                        Text(
                            "Row ${index + 1} with primary-colored scrollbar",
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
// Vertical scroll area (default fade scrollbar)
ScrollArea(modifier = Modifier.fillMaxSize()) {
    repeat(50) { index ->
        Text("Item ${'$'}index")
    }
}

// Always-visible wider scrollbar with custom color
ScrollArea(
    scrollbarAnimation = ScrollbarAnimation.Always,
    scrollbarWidth = 6.dp,
    scrollbarColor = RikkaTheme.colors.primary,
) { /* content */ }

// Horizontal scroll area
HorizontalScrollArea(
    modifier = Modifier.fillMaxWidth(),
) {
    repeat(20) { index ->
        Box(modifier = Modifier.size(100.dp))
    }
}
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection("API Reference") {
        Text(
            "ScrollArea (Vertical)",
            variant = TextVariant.H4,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        PropsTable(
            listOf(
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier for layout and decoration.",
                ),
                PropInfo(
                    "scrollbarAnimation", "ScrollbarAnimation",
                    "Fade",
                    "Scrollbar visibility: Fade, Always, None.",
                ),
                PropInfo(
                    "scrollbarWidth", "Dp", "4.dp",
                    "Thickness of the scrollbar track and thumb.",
                ),
                PropInfo(
                    "scrollbarColor", "Color?", "null",
                    "Optional thumb color override. Defaults to mutedForeground.",
                ),
                PropInfo(
                    "content", "ColumnScope.() -> Unit", "required",
                    "Scrollable column content.",
                ),
            ),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        Text(
            "HorizontalScrollArea",
            variant = TextVariant.H4,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        PropsTable(
            listOf(
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier for layout and decoration.",
                ),
                PropInfo(
                    "scrollbarAnimation", "ScrollbarAnimation",
                    "Fade",
                    "Scrollbar visibility: Fade, Always, None.",
                ),
                PropInfo(
                    "scrollbarWidth", "Dp", "4.dp",
                    "Thickness of the scrollbar track and thumb.",
                ),
                PropInfo(
                    "scrollbarColor", "Color?", "null",
                    "Optional thumb color override. Defaults to mutedForeground.",
                ),
                PropInfo(
                    "content", "RowScope.() -> Unit", "required",
                    "Scrollable row content.",
                ),
            ),
        )
    }
}
