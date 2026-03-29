@file:OptIn(ExperimentalLayoutApi::class)

package zed.rainxch.rikkaui.creator.panel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.IconSize
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.popover.Popover
import zed.rainxch.rikkaui.components.ui.popover.PopoverPlacement
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.toggle.Toggle
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.creator.generated.resources.Res
import rikkaui.feature.creator.generated.resources.*
import zed.rainxch.rikkaui.creator.fonts.availableFonts

/**
 * Configuration panel for the Design System Creator.
 *
 * Uses card-style pickers with popovers (shadcn-inspired)
 * for: style preset, palette, accent color, font, and dark mode.
 */
@Composable
fun ConfigPanel(
    stylePreset: RikkaStylePreset,
    onStyleChange: (RikkaStylePreset) -> Unit,
    palette: RikkaPalette,
    onPaletteChange: (RikkaPalette) -> Unit,
    accent: RikkaAccentPreset,
    onAccentChange: (RikkaAccentPreset) -> Unit,
    previewDark: Boolean,
    onPreviewDarkChange: (Boolean) -> Unit,
    fontId: String,
    onFontChange: (String) -> Unit,
    onDownload: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(Res.string.config_title),
        variant = TextVariant.H3,
        modifier = modifier.fillMaxWidth(),
    )

    Spacer(Modifier.height(RikkaTheme.spacing.xs))

    Text(
        text = stringResource(Res.string.config_description),
        variant = TextVariant.Muted,
        modifier = Modifier.fillMaxWidth(),
    )

    Spacer(Modifier.height(RikkaTheme.spacing.xl))

    // ─── Style ──────────────────────────────────────
    PickerCard(
        label = stringResource(Res.string.config_label_style),
        value = stylePreset.label,
    ) { onDismiss ->
        RikkaStylePreset.entries.forEach { preset ->
            PickerItem(
                text = preset.label,
                selected = stylePreset == preset,
                onClick = {
                    onStyleChange(preset)
                    onDismiss()
                },
            )
        }
    }

    Spacer(Modifier.height(RikkaTheme.spacing.sm))

    // ─── Palette ────────────────────────────────────
    PickerCard(
        label = stringResource(Res.string.config_label_base_color),
        value = palette.label,
        trailing = {
            Box(
                modifier =
                    Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(RikkaTheme.colors.primary, CircleShape),
            )
        },
    ) { onDismiss ->
        RikkaPalette.entries.forEach { entry ->
            PickerItem(
                text = entry.label,
                selected = palette == entry,
                onClick = {
                    onPaletteChange(entry)
                    onDismiss()
                },
            )
        }
    }

    Spacer(Modifier.height(RikkaTheme.spacing.sm))

    // ─── Accent ─────────────────────────────────────
    PickerCard(
        label = stringResource(Res.string.config_label_accent),
        value = accent.label,
        trailing = {
            val swatch = accent.previewColor
            Box(
                modifier =
                    Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(
                            swatch ?: RikkaTheme.colors.primary,
                            CircleShape,
                        ),
            )
        },
    ) { onDismiss ->
        RikkaAccentPreset.entries.forEach { entry ->
            PickerItem(
                text = entry.label,
                selected = accent == entry,
                onClick = {
                    onAccentChange(entry)
                    onDismiss()
                },
                leading = {
                    val swatch = entry.previewColor
                    if (swatch != null) {
                        Box(
                            modifier =
                                Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(swatch, CircleShape),
                        )
                    }
                },
            )
        }
    }

    Spacer(Modifier.height(RikkaTheme.spacing.sm))

    // ─── Font ───────────────────────────────────────
    val currentFont = availableFonts.find { it.id == fontId }
    PickerCard(
        label = stringResource(Res.string.config_label_font),
        value = currentFont?.displayName ?: fontId,
        trailing = {
            Text(
                text = stringResource(Res.string.config_font_preview),
                style = RikkaTheme.typography.small,
                color = RikkaTheme.colors.mutedForeground,
            )
        },
    ) { onDismiss ->
        availableFonts.forEach { font ->
            PickerItem(
                text = font.displayName,
                selected = fontId == font.id,
                onClick = {
                    onFontChange(font.id)
                    onDismiss()
                },
            )
        }
    }

    Spacer(Modifier.height(RikkaTheme.spacing.lg))

    // ─── Preview Dark Mode ──────────────────────────
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
    ) {
        Toggle(
            checked = previewDark,
            onCheckedChange = onPreviewDarkChange,
            label = stringResource(Res.string.config_toggle_dark_mode),
        )
        Text(
            text = if (previewDark) {
                stringResource(Res.string.config_preview_dark)
            } else {
                stringResource(Res.string.config_preview_light)
            },
            variant = TextVariant.Muted,
        )
    }

    Spacer(Modifier.height(RikkaTheme.spacing.xxl))

    // ─── Download Button ────────────────────────────
    Button(
        text = stringResource(Res.string.config_download_theme),
        onClick = onDownload,
        animation = ButtonAnimation.Bounce,
        modifier = Modifier.fillMaxWidth(),
    )
}

/**
 * A card-style picker that opens a popover with options.
 * Inspired by shadcn's theme creator sidebar cards.
 */
@Composable
private fun PickerCard(
    label: String,
    value: String,
    trailing: (@Composable () -> Unit)? = null,
    popoverContent: @Composable (onDismiss: () -> Unit) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Popover(
        expanded = expanded,
        onDismiss = { expanded = false },
        minWidth = 160.dp,
        maxWidth = 220.dp,
        placement = PopoverPlacement.BottomStart,
        trigger = {
            Card(
                onClick = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        Text(
                            text = label,
                            style = RikkaTheme.typography.muted,
                            color = RikkaTheme.colors.mutedForeground,
                        )
                        Spacer(Modifier.height(RikkaTheme.spacing.xs))
                        Text(
                            text = value,
                            variant = TextVariant.Large,
                        )
                    }
                    if (trailing != null) {
                        trailing()
                    }
                }
            }
        },
    ) {
        Column(
            verticalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.xs),
        ) {
            popoverContent { expanded = false }
        }
    }
}

/**
 * A single item in a picker popover.
 * Shows a check icon when selected.
 */
@Composable
private fun PickerItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    leading: (@Composable () -> Unit)? = null,
) {
    Button(
        onClick = onClick,
        variant = ButtonVariant.Ghost,
        size = ButtonSize.Sm,
        animation = ButtonAnimation.Scale,
        modifier = Modifier.fillMaxWidth(),
    ) { foreground ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.xs),
            modifier = Modifier.fillMaxWidth(),
        ) {
            if (leading != null) {
                leading()
            }
            Text(
                text = text,
                color = foreground,
                style = RikkaTheme.typography.small,
                modifier = Modifier.weight(1f),
            )
            if (selected) {
                Icon(
                    RikkaIcons.Check,
                    contentDescription = stringResource(Res.string.config_selected),
                    size = IconSize.Sm,
                    tint = foreground,
                )
            }
        }
    }
}
