package zed.rainxch.rikkaui.docs.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.Res
import rikkaui.feature.docs.generated.resources.docs_components
import rikkaui.feature.docs.generated.resources.docs_getting_started
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.catalog.ComponentCategory
import zed.rainxch.rikkaui.docs.catalog.ComponentEntry
import zed.rainxch.rikkaui.docs.catalog.guidePages
import zed.rainxch.rikkaui.foundation.RikkaTheme

@Composable
fun DocsSidebar(
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
                                    .onMuted,
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
            RikkaTheme.colors.onBackground
        } else {
            RikkaTheme.colors.onMuted
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
