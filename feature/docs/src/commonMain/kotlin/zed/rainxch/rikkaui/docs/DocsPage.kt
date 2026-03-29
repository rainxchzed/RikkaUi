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
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.Res
import rikkaui.feature.docs.generated.resources.docs_components
import rikkaui.feature.docs.generated.resources.docs_getting_started
import rikkaui.feature.docs.generated.resources.guide_installation
import rikkaui.feature.docs.generated.resources.guide_introduction
import rikkaui.feature.docs.generated.resources.guide_theming
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.catalog.ComponentCategory
import zed.rainxch.rikkaui.docs.catalog.ComponentEntry
import zed.rainxch.rikkaui.docs.catalog.ComponentRegistry
import zed.rainxch.rikkaui.docs.pages.InstallationDoc
import zed.rainxch.rikkaui.docs.pages.IntroductionDoc
import zed.rainxch.rikkaui.docs.pages.ThemingDoc
import zed.rainxch.rikkaui.foundation.RikkaTheme

private data class GuidePage(
    val id: String,
    val nameRes: StringResource,
    val content: @Composable () -> Unit,
)

private val guidePages =
    listOf(
        GuidePage(
            id = "introduction",
            nameRes = Res.string.guide_introduction,
            content = { IntroductionDoc() },
        ),
        GuidePage(
            id = "installation",
            nameRes = Res.string.guide_installation,
            content = { InstallationDoc() },
        ),
        GuidePage(
            id = "theming",
            nameRes = Res.string.guide_theming,
            content = { ThemingDoc() },
        ),
    )

@Composable
fun DocsPage(
    initialComponentId: String? = null,
    onNavigateToComponent: (String) -> Unit = {},
    onNavigateToGuide: (String) -> Unit = {},
) {
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
    val guidePageIds = remember { guidePages.map { it.id }.toSet() }

    val onSelect: (String) -> Unit = { id ->
        viewModel.onAction(DocsAction.SelectPage(id))
        if (id in guidePageIds) {
            onNavigateToGuide(id)
        } else {
            onNavigateToComponent(id)
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isWide = maxWidth >= 800.dp

        if (isWide) {
            Row(Modifier.fillMaxSize()) {
                DocsSidebar(
                    guidePages = guidePages,
                    grouped = registry.groupedByCategory(),
                    selectedId = state.selectedId,
                    onSelect = onSelect,
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
                    onSelect = onSelect,
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
            text = stringResource(Res.string.docs_getting_started),
            variant = TextVariant.H4,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.xs))

        guidePages.forEach { page ->
            SidebarItem(
                name = stringResource(page.nameRes),
                isSelected = page.id == selectedId,
                onClick = { onSelect(page.id) },
            )
        }

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        Text(
            text = stringResource(Res.string.docs_components),
            variant = TextVariant.H4,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.xs))

        grouped.forEach { (category, entries) ->
            Spacer(Modifier.height(RikkaTheme.spacing.sm))

            BasicText(
                text = stringResource(category.labelRes),
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
                    name = stringResource(entry.nameRes),
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
                text = stringResource(page.nameRes),
                isActive = page.id == selectedId,
                onClick = { onSelect(page.id) },
            )
        }

        entries.forEach { entry ->
            CompactChip(
                text = stringResource(entry.nameRes),
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
