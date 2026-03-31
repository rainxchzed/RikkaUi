@file:OptIn(ExperimentalLayoutApi::class)

package zed.rainxch.rikkaui.docs.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.Res
import rikkaui.feature.docs.generated.resources.catalog_subtitle
import rikkaui.feature.docs.generated.resources.catalog_title
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.catalog.ComponentCategory
import zed.rainxch.rikkaui.docs.catalog.ComponentEntry
import zed.rainxch.rikkaui.docs.catalog.ComponentRegistry
import zed.rainxch.rikkaui.foundation.RikkaTheme

/**
 * Full-page component catalog that displays all components
 * grouped by category in a responsive grid layout.
 *
 * Each component is shown as a clickable card with its name,
 * description, and category badge. Clicking navigates to the
 * component's documentation page.
 */
@Composable
fun ComponentsCatalogScreen(
    onComponentClick: (String) -> Unit,
) {
    val grouped = remember { ComponentRegistry.groupedByCategory() }
    val totalCount = remember { ComponentRegistry.entries.size }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier =
                Modifier
                    .widthIn(max = 1120.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        horizontal = RikkaTheme.spacing.xl,
                        vertical = RikkaTheme.spacing.xxl,
                    ),
        ) {
            // ── Header ──
            Text(
                text = stringResource(Res.string.catalog_title),
                variant = TextVariant.H1,
            )
            Spacer(Modifier.height(RikkaTheme.spacing.sm))
            Text(
                text = stringResource(Res.string.catalog_subtitle, totalCount),
                variant = TextVariant.Muted,
            )

            Spacer(Modifier.height(RikkaTheme.spacing.xxl))
            Separator()
            Spacer(Modifier.height(RikkaTheme.spacing.xxl))

            // ── Category Sections ──
            grouped.entries.forEachIndexed { index, (category, entries) ->
                CategorySection(
                    category = category,
                    entries = entries,
                    onComponentClick = onComponentClick,
                )

                if (index < grouped.size - 1) {
                    Spacer(Modifier.height(RikkaTheme.spacing.xxl))
                }
            }

            Spacer(Modifier.height(RikkaTheme.spacing.xxxl))
        }
    }
}

// ────────────────────────────────────────────────────────
// Category Section
// ────────────────────────────────────────────────────────

@Composable
private fun CategorySection(
    category: ComponentCategory,
    entries: List<ComponentEntry>,
    onComponentClick: (String) -> Unit,
) {
    Text(
        text = stringResource(category.labelRes),
        variant = TextVariant.H3,
    )
    Spacer(Modifier.height(RikkaTheme.spacing.xs))
    Text(
        text = "${entries.size} components",
        variant = TextVariant.Muted,
    )
    Spacer(Modifier.height(RikkaTheme.spacing.lg))

    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val columns =
            when {
                maxWidth >= 900.dp -> 3
                maxWidth >= 540.dp -> 2
                else -> 1
            }
        val gap = RikkaTheme.spacing.md

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(gap),
            verticalArrangement = Arrangement.spacedBy(gap),
            maxItemsInEachRow = columns,
            modifier = Modifier.fillMaxWidth(),
        ) {
            entries.forEach { entry ->
                ComponentCard(
                    entry = entry,
                    onClick = { onComponentClick(entry.id) },
                    modifier =
                        Modifier.weight(1f),
                )
            }

            // Fill remaining slots in the last row with empty spacers
            // so cards don't stretch to fill the whole row
            val remainder = entries.size % columns
            if (remainder != 0) {
                repeat(columns - remainder) {
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

// ────────────────────────────────────────────────────────
// Component Card
// ────────────────────────────────────────────────────────

@Composable
private fun ComponentCard(
    entry: ComponentEntry,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val bgColor =
        if (isHovered) {
            RikkaTheme.colors.muted.copy(alpha = 0.4f)
        } else {
            RikkaTheme.colors.card
        }

    val borderColor =
        if (isHovered) {
            RikkaTheme.colors.primary.copy(alpha = 0.4f)
        } else {
            RikkaTheme.colors.border
        }

    Column(
        modifier =
            modifier
                .clip(RikkaTheme.shapes.lg)
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RikkaTheme.shapes.lg,
                ).background(bgColor, RikkaTheme.shapes.lg)
                .hoverable(interactionSource)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    role = Role.Button,
                    onClick = onClick,
                ).padding(RikkaTheme.spacing.lg),
    ) {
        Text(
            text = stringResource(entry.nameRes),
            variant = TextVariant.Large,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.xs))

        Text(
            text = stringResource(entry.descriptionRes),
            variant = TextVariant.Muted,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        Badge(
            text = stringResource(entry.category.labelRes),
            variant = BadgeVariant.Secondary,
        )
    }
}
