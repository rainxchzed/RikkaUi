@file:OptIn(ExperimentalLayoutApi::class)

package zed.rainxch.rikkaui.showcase.components

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
import org.jetbrains.compose.resources.stringResource
import rikkaui.composeapp.generated.resources.Res
import rikkaui.composeapp.generated.resources.toolbar_accent
import rikkaui.composeapp.generated.resources.toolbar_accent_label
import rikkaui.composeapp.generated.resources.toolbar_expand
import rikkaui.composeapp.generated.resources.toolbar_palette
import rikkaui.composeapp.generated.resources.toolbar_palette_label
import rikkaui.composeapp.generated.resources.toolbar_style
import rikkaui.composeapp.generated.resources.toolbar_style_label
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonAnimation
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.IconSize
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.popover.Popover
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.foundation.RikkaAccentPreset
import zed.rainxch.rikkaui.foundation.RikkaPalette
import zed.rainxch.rikkaui.foundation.RikkaStylePreset
import zed.rainxch.rikkaui.foundation.RikkaTheme

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
        ToolbarPopover(
            label = stringResource(Res.string.toolbar_style_label, stylePreset.label),
            icon = { Icon(RikkaIcons.Settings, stringResource(Res.string.toolbar_style), size = IconSize.Sm) },
        ) { onDismiss ->
            Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs)) {
                Text(
                    text = stringResource(Res.string.toolbar_style),
                    style = RikkaTheme.typography.small,
                    color = RikkaTheme.colors.onMuted,
                )
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

        ToolbarPopover(
            label = stringResource(Res.string.toolbar_palette_label, palette.label),
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
                Text(
                    text = stringResource(Res.string.toolbar_palette),
                    style = RikkaTheme.typography.small,
                    color = RikkaTheme.colors.onMuted,
                )
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

        ToolbarPopover(
            label = stringResource(Res.string.toolbar_accent_label, accent.label),
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
                Text(
                    text = stringResource(Res.string.toolbar_accent),
                    style = RikkaTheme.typography.small,
                    color = RikkaTheme.colors.onMuted,
                )
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
                    ) {
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
        maxWidth = 160.dp,
        trigger = {
            Button(
                onClick = { expanded = !expanded },
                variant = ButtonVariant.Outline,
                size = ButtonSize.Sm,
            ) {
                icon()
                Spacer(Modifier.width(RikkaTheme.spacing.xs))
                Text(
                    text = label,
                    style = RikkaTheme.typography.small,
                )
                Spacer(Modifier.width(RikkaTheme.spacing.xs))
                Icon(
                    RikkaIcons.ChevronDown,
                    contentDescription = stringResource(Res.string.toolbar_expand),
                    size = IconSize.Sm,
                )
            }
        },
    ) {
        content { expanded = false }
    }
}
