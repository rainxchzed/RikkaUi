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
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.*
import rikkaui.feature.docs.generated.resources.Res
import zed.rainxch.rikkaui.components.ui.scrollarea.HorizontalScrollArea
import zed.rainxch.rikkaui.components.ui.scrollarea.ScrollArea
import zed.rainxch.rikkaui.components.ui.scrollarea.ScrollbarAnimation
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
 * Documentation page for the ScrollArea component.
 *
 * Demonstrates vertical and horizontal scroll areas with
 * different scrollbar animation modes.
 */
@Composable
fun ScrollAreaDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_scroll_area_name),
        description = stringResource(Res.string.scroll_area_page_desc),
    )

    TabbedDocPage(
        overview = { ScrollAreaOverviewTab() },
        usage = { ScrollAreaUsageTab() },
        api = { ScrollAreaApiTab() },
    )
}

// ─── Overview Tab ───────────────────────────────────────────

@Composable
private fun ScrollAreaOverviewTab() {
    // ─── Scrollbar Animations ───────────────────────────────
    DocSection(stringResource(Res.string.scroll_area_section_scrollbar_anims)) {
        var selectedAnim by remember { mutableStateOf("Fade") }

        VariantSelector(
            options = listOf("Fade", "Always", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation =
            when (selectedAnim) {
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
                    verticalArrangement =
                        Arrangement.spacedBy(
                            RikkaTheme.spacing.sm,
                        ),
                    modifier = Modifier.padding(RikkaTheme.spacing.sm),
                ) {
                    repeat(20) { index ->
                        Text(
                            stringResource(Res.string.scroll_area_demo_item, index + 1),
                            variant = TextVariant.P,
                        )
                    }
                }
            }
        }
    }

    // ─── Horizontal ScrollArea ──────────────────────────────
    DocSection(stringResource(Res.string.scroll_area_section_horizontal)) {
        DemoBox {
            HorizontalScrollArea(
                modifier = Modifier.fillMaxWidth().height(80.dp),
                scrollbarAnimation = ScrollbarAnimation.Always,
            ) {
                repeat(15) { index ->
                    Box(
                        modifier =
                            Modifier
                                .size(80.dp)
                                .padding(RikkaTheme.spacing.xs)
                                .background(
                                    RikkaTheme.colors.muted,
                                    RikkaTheme.shapes.md,
                                ).padding(RikkaTheme.spacing.sm),
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
    DocSection(stringResource(Res.string.scroll_area_section_custom)) {
        DemoBox {
            ScrollArea(
                modifier = Modifier.fillMaxWidth().height(160.dp),
                scrollbarAnimation = ScrollbarAnimation.Always,
                scrollbarWidth = 6.dp,
                scrollbarColor = RikkaTheme.colors.primary,
            ) {
                Column(
                    verticalArrangement =
                        Arrangement.spacedBy(
                            RikkaTheme.spacing.sm,
                        ),
                    modifier = Modifier.padding(RikkaTheme.spacing.sm),
                ) {
                    repeat(15) { index ->
                        Text(
                            stringResource(Res.string.scroll_area_demo_row_scrollbar, index + 1),
                            variant = TextVariant.P,
                        )
                    }
                }
            }
        }
    }
}

// ─── Usage Tab ──────────────────────────────────────────────

@Composable
private fun ScrollAreaUsageTab() {
    DocSection(stringResource(Res.string.section_usage)) {
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

    DocSection(stringResource(Res.string.scroll_area_section_dos_donts)) {
        DoAndDont(
            doContent = {
                ScrollArea(
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    scrollbarAnimation = ScrollbarAnimation.Always,
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
                        modifier = Modifier.padding(RikkaTheme.spacing.xs),
                    ) {
                        repeat(8) { index ->
                            Text("Item ${index + 1}", variant = TextVariant.Small)
                        }
                    }
                }
            },
            doDescription = stringResource(Res.string.scroll_area_do_overflow_desc),
            dontContent = {
                ScrollArea(
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
                        modifier = Modifier.padding(RikkaTheme.spacing.xs),
                    ) {
                        ScrollArea(modifier = Modifier.fillMaxWidth().height(60.dp)) {
                            repeat(5) { index ->
                                Text("Nested ${index + 1}", variant = TextVariant.Small)
                            }
                        }
                    }
                }
            },
            dontDescription = stringResource(Res.string.scroll_area_dont_overflow_desc),
        )
    }
}

// ─── API Tab ────────────────────────────────────────────────

@Composable
private fun ScrollAreaApiTab() {
    DocSection(stringResource(Res.string.section_api_reference)) {
        Text(
            stringResource(Res.string.scroll_area_subsection_vertical),
            variant = TextVariant.H4,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        PropsTable(
            listOf(
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(Res.string.scroll_area_prop_modifier_desc),
                ),
                PropInfo(
                    "scrollbarAnimation",
                    "ScrollbarAnimation",
                    "Fade",
                    stringResource(Res.string.scroll_area_prop_animation_desc),
                ),
                PropInfo(
                    "scrollbarWidth",
                    "Dp",
                    "4.dp",
                    stringResource(Res.string.scroll_area_prop_width_desc),
                ),
                PropInfo(
                    "scrollbarColor",
                    "Color?",
                    "null",
                    stringResource(Res.string.scroll_area_prop_color_desc),
                ),
                PropInfo(
                    "keyboardScrolling",
                    "Boolean",
                    "true",
                    "Enables Arrow/Space/Page/Home/End keyboard scrolling.",
                ),
                PropInfo(
                    "scrollFocusMode",
                    "ScrollFocusMode",
                    "RequestFocus",
                    "How the area acquires focus: RequestFocus (auto), Hover, or Click.",
                ),
                PropInfo(
                    "content",
                    "ColumnScope.() -> Unit",
                    "required",
                    stringResource(Res.string.scroll_area_prop_content_vertical_desc),
                ),
            ),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        Text(
            stringResource(Res.string.scroll_area_subsection_horizontal),
            variant = TextVariant.H4,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        PropsTable(
            listOf(
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(Res.string.scroll_area_prop_modifier_desc),
                ),
                PropInfo(
                    "scrollbarAnimation",
                    "ScrollbarAnimation",
                    "Fade",
                    stringResource(Res.string.scroll_area_prop_animation_desc),
                ),
                PropInfo(
                    "scrollbarWidth",
                    "Dp",
                    "4.dp",
                    stringResource(Res.string.scroll_area_prop_width_desc),
                ),
                PropInfo(
                    "scrollbarColor",
                    "Color?",
                    "null",
                    stringResource(Res.string.scroll_area_prop_color_desc),
                ),
                PropInfo(
                    "keyboardScrolling",
                    "Boolean",
                    "true",
                    "Enables Arrow/Space/Page/Home/End keyboard scrolling.",
                ),
                PropInfo(
                    "scrollFocusMode",
                    "ScrollFocusMode",
                    "RequestFocus",
                    "How the area acquires focus: RequestFocus (auto), Hover, or Click.",
                ),
                PropInfo(
                    "content",
                    "RowScope.() -> Unit",
                    "required",
                    stringResource(Res.string.scroll_area_prop_content_horizontal_desc),
                ),
            ),
        )
    }
}
