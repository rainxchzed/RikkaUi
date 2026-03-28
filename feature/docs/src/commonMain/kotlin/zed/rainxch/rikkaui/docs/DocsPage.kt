package zed.rainxch.rikkaui.docs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.catalog.ComponentCategory
import zed.rainxch.rikkaui.docs.catalog.ComponentEntry
import zed.rainxch.rikkaui.docs.catalog.ComponentRegistry
import zed.rainxch.rikkaui.docs.pages.InstallationDoc
import zed.rainxch.rikkaui.docs.pages.IntroductionDoc
import zed.rainxch.rikkaui.docs.pages.ThemingDoc

// ─── Getting Started Pages ───────────────────────────────────

/**
 * Sidebar entry for non-component pages (Getting Started section).
 */
private data class GuidePage(
    val id: String,
    val name: String,
    val content: @Composable () -> Unit,
)

private val guidePages =
    listOf(
        GuidePage(
            id = "introduction",
            name = "Introduction",
            content = { IntroductionDoc() },
        ),
        GuidePage(
            id = "installation",
            name = "Installation",
            content = { InstallationDoc() },
        ),
        GuidePage(
            id = "theming",
            name = "Theming",
            content = { ThemingDoc() },
        ),
    )

// ─── DocsPage ────────────────────────────────────────────────

/**
 * Docs page with sidebar navigation and content area.
 *
 * Includes two sidebar sections:
 * 1. **Getting Started** — Introduction, Installation, Theming
 * 2. **Components** — 41 components grouped by category
 *
 * @param navController App-level NavController.
 * @param initialComponentId Component to show on load, or
 *   a guide page id ("introduction", "installation", "theming").
 *   When null, defaults to Introduction.
 */
@Composable
fun DocsPage(
    navController: Any? = null,
    initialComponentId: String? = null,
    modifier: Modifier = Modifier,
) {
    val registry = ComponentRegistry
    var selectedId by remember {
        mutableStateOf(
            initialComponentId ?: "introduction",
        )
    }

    // Sync if route changes externally
    if (initialComponentId != null &&
        initialComponentId != selectedId
    ) {
        selectedId = initialComponentId
    }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isWide = maxWidth >= 800.dp

        if (isWide) {
            Row(Modifier.fillMaxSize()) {
                // ─── Sidebar ─────────────────────────
                DocsSidebar(
                    guidePages = guidePages,
                    grouped = registry.groupedByCategory(),
                    selectedId = selectedId,
                    onSelect = { selectedId = it },
                    modifier =
                        Modifier
                            .width(240.dp)
                            .fillMaxHeight()
                            .verticalScroll(
                                rememberScrollState(),
                            )
                            .padding(
                                start = RikkaTheme.spacing.lg,
                                top = RikkaTheme.spacing.lg,
                                bottom = RikkaTheme.spacing.lg,
                                end = RikkaTheme.spacing.md,
                            ),
                )

                // Vertical separator
                Box(
                    Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .background(RikkaTheme.colors.border),
                )

                // ─── Content ─────────────────────────
                Column(
                    modifier =
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .verticalScroll(
                                rememberScrollState(),
                            )
                            .padding(RikkaTheme.spacing.xl),
                ) {
                    PageContent(
                        selectedId = selectedId,
                        registry = registry,
                    )
                }
            }
        } else {
            // Narrow: stacked layout
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(RikkaTheme.spacing.md),
            ) {
                CompactSelector(
                    guidePages = guidePages,
                    entries = registry.entries,
                    selectedId = selectedId,
                    onSelect = { selectedId = it },
                )

                Spacer(Modifier.height(RikkaTheme.spacing.lg))

                PageContent(
                    selectedId = selectedId,
                    registry = registry,
                )
            }
        }
    }
}

// ─── Content Router ──────────────────────────────────────────

@Composable
private fun PageContent(
    selectedId: String,
    registry: ComponentRegistry,
) {
    Column(modifier = Modifier.widthIn(max = 900.dp)) {
        // Check guide pages first
        val guide = guidePages.find { it.id == selectedId }
        if (guide != null) {
            guide.content()
            return@Column
        }

        // Then check component pages
        val entry = registry.findById(selectedId)
        if (entry != null) {
            entry.content()
        }
    }
}

// ─── Sidebar ─────────────────────────────────────────────────

@Composable
private fun DocsSidebar(
    guidePages: List<GuidePage>,
    grouped: Map<ComponentCategory, List<ComponentEntry>>,
    selectedId: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.xs),
    ) {
        // ─── Getting Started section ─────────────
        Text(
            text = "Getting Started",
            variant = TextVariant.H4,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.xs))

        guidePages.forEach { page ->
            SidebarItem(
                name = page.name,
                isSelected = page.id == selectedId,
                onClick = { onSelect(page.id) },
            )
        }

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        // ─── Components section ──────────────────
        Text(
            text = "Components",
            variant = TextVariant.H4,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.xs))

        grouped.forEach { (category, entries) ->
            Spacer(Modifier.height(RikkaTheme.spacing.sm))

            // Category header
            BasicText(
                text = category.label,
                style =
                    RikkaTheme.typography.small.merge(
                        TextStyle(
                            color =
                                RikkaTheme.colors
                                    .mutedForeground,
                            fontWeight = FontWeight.SemiBold,
                        ),
                    ),
                modifier =
                    Modifier.padding(
                        vertical = RikkaTheme.spacing.xs,
                    ),
            )

            entries.forEach { entry ->
                SidebarItem(
                    name = entry.name,
                    isSelected = entry.id == selectedId,
                    onClick = { onSelect(entry.id) },
                )
            }
        }
    }
}

@Composable
private fun SidebarItem(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val interactionSource =
        remember { MutableInteractionSource() }
    val isHovered by
        interactionSource.collectIsHoveredAsState()

    val bg =
        when {
            isSelected -> RikkaTheme.colors.muted
            isHovered ->
                RikkaTheme.colors.muted.copy(alpha = 0.5f)
            else -> RikkaTheme.colors.background
        }

    val fg =
        if (isSelected) {
            RikkaTheme.colors.foreground
        } else {
            RikkaTheme.colors.mutedForeground
        }

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .hoverable(interactionSource)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick,
                )
                .background(bg, RikkaTheme.shapes.md)
                .clip(RikkaTheme.shapes.md)
                .padding(
                    horizontal = RikkaTheme.spacing.sm,
                    vertical = RikkaTheme.spacing.xs,
                ),
    ) {
        BasicText(
            text = name,
            style =
                RikkaTheme.typography.small.merge(
                    TextStyle(
                        color = fg,
                        fontWeight =
                            if (isSelected) {
                                FontWeight.Medium
                            } else {
                                FontWeight.Normal
                            },
                    ),
                ),
        )
    }
}

// ─── Compact Selector (narrow screens) ───────────────────────

@Composable
private fun CompactSelector(
    guidePages: List<GuidePage>,
    entries: List<ComponentEntry>,
    selectedId: String,
    onSelect: (String) -> Unit,
) {
    val scrollState = rememberScrollState()

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState),
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.xs),
    ) {
        // Guide pages first
        guidePages.forEach { page ->
            CompactChip(
                text = page.name,
                isActive = page.id == selectedId,
                onClick = { onSelect(page.id) },
            )
        }

        // Then component entries
        entries.forEach { entry ->
            CompactChip(
                text = entry.name,
                isActive = entry.id == selectedId,
                onClick = { onSelect(entry.id) },
            )
        }
    }
}

@Composable
private fun CompactChip(
    text: String,
    isActive: Boolean,
    onClick: () -> Unit,
) {
    val interactionSource =
        remember { MutableInteractionSource() }
    val isHovered by
        interactionSource.collectIsHoveredAsState()

    val bg =
        when {
            isActive -> RikkaTheme.colors.primary
            isHovered -> RikkaTheme.colors.muted
            else -> RikkaTheme.colors.background
        }
    val fg =
        if (isActive) {
            RikkaTheme.colors.primaryForeground
        } else {
            RikkaTheme.colors.foreground
        }

    Box(
        modifier =
            Modifier
                .hoverable(interactionSource)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick,
                )
                .background(bg, RikkaTheme.shapes.md)
                .padding(
                    horizontal = RikkaTheme.spacing.sm,
                    vertical = RikkaTheme.spacing.xs,
                ),
    ) {
        BasicText(
            text = text,
            style =
                RikkaTheme.typography.small.merge(
                    TextStyle(color = fg),
                ),
        )
    }
}
