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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.catalog.ComponentCategory
import zed.rainxch.rikkaui.docs.catalog.ComponentEntry
import zed.rainxch.rikkaui.docs.catalog.ComponentRegistry
import zed.rainxch.rikkaui.docs.pages.InstallationDoc
import zed.rainxch.rikkaui.docs.pages.IntroductionDoc
import zed.rainxch.rikkaui.docs.pages.ThemingDoc

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

/**
 * Documentation page with MVI state management.
 *
 * Uses [DocsViewModel] for robust, unidirectional state management.
 * Supports deep-linking via [initialComponentId] from navigation routes.
 *
 * @param initialComponentId Optional component ID from navigation deep-link.
 *   When null, defaults to the "introduction" guide page.
 */
@Composable
fun DocsPage(initialComponentId: String? = null) {
    val viewModel: DocsViewModel =
        viewModel(
            factory =
                viewModelFactory {
                    initializer {
                        DocsViewModel(initialComponentId)
                    }
                },
        )

    val state by viewModel.state.collectAsState()

    LaunchedEffect(initialComponentId) {
        if (initialComponentId != null) {
            viewModel.onAction(
                DocsAction.NavigateToComponent(initialComponentId),
            )
        }
    }

    val registry = ComponentRegistry

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isWide = maxWidth >= 800.dp

        if (isWide) {
            Row(Modifier.fillMaxSize()) {
                DocsSidebar(
                    guidePages = guidePages,
                    grouped = registry.groupedByCategory(),
                    selectedId = state.selectedId,
                    onSelect = {
                        viewModel.onAction(DocsAction.SelectPage(it))
                    },
                    modifier =
                        Modifier
                            .width(240.dp)
                            .fillMaxHeight()
                            .verticalScroll(
                                rememberScrollState(),
                            ).padding(
                                start = RikkaTheme.spacing.lg,
                                top = RikkaTheme.spacing.lg,
                                bottom = RikkaTheme.spacing.lg,
                                end = RikkaTheme.spacing.md,
                            ),
                )

                Box(
                    Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .background(RikkaTheme.colors.border),
                )

                Column(
                    modifier =
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .verticalScroll(
                                rememberScrollState(),
                            ).padding(RikkaTheme.spacing.xl),
                ) {
                    PageContent(
                        selectedId = state.selectedId,
                        registry = registry,
                    )
                }
            }
        } else {
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
                    selectedId = state.selectedId,
                    onSelect = {
                        viewModel.onAction(DocsAction.SelectPage(it))
                    },
                )

                Spacer(Modifier.height(RikkaTheme.spacing.lg))

                PageContent(
                    selectedId = state.selectedId,
                    registry = registry,
                )
            }
        }
    }
}

@Composable
private fun PageContent(
    selectedId: String,
    registry: ComponentRegistry,
) {
    Column(modifier = Modifier.widthIn(max = 900.dp)) {
        val guide = guidePages.find { it.id == selectedId }
        if (guide != null) {
            guide.content()
            return@Column
        }

        val entry = registry.findById(selectedId)
        if (entry != null) {
            entry.content()
        }
    }
}

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

        Text(
            text = "Components",
            variant = TextVariant.H4,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.xs))

        grouped.forEach { (category, entries) ->
            Spacer(Modifier.height(RikkaTheme.spacing.sm))

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
            isSelected -> {
                RikkaTheme.colors.muted
            }

            isHovered -> {
                RikkaTheme.colors.muted.copy(alpha = 0.5f)
            }

            else -> {
                RikkaTheme.colors.background
            }
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
                ).background(bg, RikkaTheme.shapes.md)
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
        guidePages.forEach { page ->
            CompactChip(
                text = page.name,
                isActive = page.id == selectedId,
                onClick = { onSelect(page.id) },
            )
        }

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
                ).background(bg, RikkaTheme.shapes.md)
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
