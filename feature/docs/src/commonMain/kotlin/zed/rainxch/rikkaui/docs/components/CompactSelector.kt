package zed.rainxch.rikkaui.docs.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import org.jetbrains.compose.resources.stringResource
import zed.rainxch.rikkaui.docs.catalog.ComponentEntry
import zed.rainxch.rikkaui.docs.catalog.guidePages
import zed.rainxch.rikkaui.foundation.RikkaTheme

@Composable
fun CompactSelector(
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
            RikkaTheme.colors.onPrimary
        } else {
            RikkaTheme.colors.onBackground
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
