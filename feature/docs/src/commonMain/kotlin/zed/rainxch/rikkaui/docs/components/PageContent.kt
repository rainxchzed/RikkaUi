package zed.rainxch.rikkaui.docs.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.Res
import rikkaui.feature.docs.generated.resources.source_tab_code
import rikkaui.feature.docs.generated.resources.source_tab_docs
import zed.rainxch.rikkaui.docs.catalog.ComponentRegistry
import zed.rainxch.rikkaui.docs.catalog.guidePages
import zed.rainxch.rikkaui.docs.sources.SourceCodeViewer
import zed.rainxch.rikkaui.foundation.RikkaTheme

@Composable
fun PageContent(
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
            var showCode by remember(selectedId) {
                mutableStateOf(false)
            }

            Row(
                modifier =
                    Modifier.padding(
                        bottom = RikkaTheme.spacing.lg,
                    ),
                horizontalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.xs,
                    ),
            ) {
                SourceTab(
                    text = stringResource(Res.string.source_tab_docs),
                    isActive = !showCode,
                    onClick = { showCode = false },
                )
                SourceTab(
                    text = stringResource(Res.string.source_tab_code),
                    isActive = showCode,
                    onClick = { showCode = true },
                )
            }

            if (showCode) {
                SourceCodeViewer(componentId = entry.id)
            } else {
                entry.content()
            }
        }
    }
}

@Composable
private fun SourceTab(
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
                    horizontal = RikkaTheme.spacing.md,
                    vertical = RikkaTheme.spacing.xs,
                ),
    ) {
        BasicText(
            text = text,
            style =
                RikkaTheme.typography.small.merge(
                    TextStyle(
                        color = fg,
                        fontWeight = FontWeight.Medium,
                    ),
                ),
        )
    }
}
