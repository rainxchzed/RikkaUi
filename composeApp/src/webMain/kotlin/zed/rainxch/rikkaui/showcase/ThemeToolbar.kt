@file:OptIn(ExperimentalLayoutApi::class)

package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaAccentPreset
import zed.rainxch.rikkaui.components.theme.RikkaPalette
import zed.rainxch.rikkaui.components.theme.RikkaStylePreset
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonAnimation
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.IconSize
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.popover.Popover
import zed.rainxch.rikkaui.components.ui.text.Text

/**
 * Compact theme customization toolbar displayed above the examples grid.
 * Each option opens a Popover with choices — inspired by shadcn's theme switcher.
 */
@Composable
fun ThemeToolbar(
    palette: RikkaPalette,
    onPaletteChange: (RikkaPalette) -> Unit,
    accent: RikkaAccentPreset,
    onAccentChange: (RikkaAccentPreset) -> Unit,
    stylePreset: RikkaStylePreset,
    onStyleChange: (RikkaStylePreset) -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
        verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
    ) {
        // Style picker
        ToolbarPopover(
            label = "Style: ${stylePreset.label}",
            icon = { Icon(RikkaIcons.Settings, "Style", size = IconSize.Sm) },
        ) { onDismiss ->
            Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs)) {
                Text(text = "Style", style = RikkaTheme.typography.small, color = RikkaTheme.colors.mutedForeground)
                Spacer(Modifier.height(RikkaTheme.spacing.xs))
                RikkaStylePreset.entries.forEach { preset ->
                    Button(
                        text = preset.label,
                        onClick = {
                            onStyleChange(preset)
                            onDismiss()
                        },
                        variant = if (stylePreset == preset) ButtonVariant.Default else ButtonVariant.Ghost,
                        size = ButtonSize.Sm,
                        animation = ButtonAnimation.Scale,
                    )
                }
            }
        }

        // Palette picker
        ToolbarPopover(
            label = palette.label,
            icon = {
                Box(
                    modifier =
                        Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(RikkaTheme.colors.primary, CircleShape),
                )
            },
        ) { onDismiss ->
            Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs)) {
                Text(text = "Palette", style = RikkaTheme.typography.small, color = RikkaTheme.colors.mutedForeground)
                Spacer(Modifier.height(RikkaTheme.spacing.xs))
                RikkaPalette.entries.forEach { entry ->
                    Button(
                        text = entry.label,
                        onClick = {
                            onPaletteChange(entry)
                            onDismiss()
                        },
                        variant = if (palette == entry) ButtonVariant.Default else ButtonVariant.Ghost,
                        size = ButtonSize.Sm,
                        animation = ButtonAnimation.Scale,
                    )
                }
            }
        }

        // Accent picker
        ToolbarPopover(
            label = accent.label,
            icon = {
                val swatch = accent.previewColor
                if (swatch != null) {
                    Box(
                        modifier =
                            Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(swatch, CircleShape),
                    )
                } else {
                    Box(
                        modifier =
                            Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(RikkaTheme.colors.primary, CircleShape),
                    )
                }
            },
        ) { onDismiss ->
            Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs)) {
                Text(text = "Accent", style = RikkaTheme.typography.small, color = RikkaTheme.colors.mutedForeground)
                Spacer(Modifier.height(RikkaTheme.spacing.xs))
                RikkaAccentPreset.entries.forEach { entry ->
                    Button(
                        onClick = {
                            onAccentChange(entry)
                            onDismiss()
                        },
                        variant = if (accent == entry) ButtonVariant.Default else ButtonVariant.Ghost,
                        size = ButtonSize.Sm,
                        animation = ButtonAnimation.Scale,
                    ) { foreground ->
                        val swatch = entry.previewColor
                        if (swatch != null) {
                            Box(
                                modifier =
                                    Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(swatch, CircleShape),
                            )
                            Spacer(Modifier.width(RikkaTheme.spacing.xs))
                        }
                        Text(
                            text = entry.label,
                            color = foreground,
                            style = RikkaTheme.typography.small,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ToolbarPopover(
    label: String,
    icon: @Composable () -> Unit,
    content: @Composable (onDismiss: () -> Unit) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Popover(
        expanded = expanded,
        onDismiss = { expanded = false },
        trigger = {
            Button(
                onClick = { expanded = !expanded },
                variant = ButtonVariant.Outline,
                size = ButtonSize.Sm,
            ) { foreground ->
                icon()
                Spacer(Modifier.width(RikkaTheme.spacing.xs))
                Text(
                    text = label,
                    color = foreground,
                    style = RikkaTheme.typography.small,
                )
                Spacer(Modifier.width(RikkaTheme.spacing.xs))
                Icon(
                    RikkaIcons.ChevronDown,
                    contentDescription = "Expand",
                    size = IconSize.Sm,
                    tint = foreground,
                )
            }
        },
    ) {
        content { expanded = false }
    }
}
