@file:OptIn(ExperimentalLayoutApi::class)

package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import rikkaui.composeapp.generated.resources.Res
import rikkaui.composeapp.generated.resources.accent
import rikkaui.composeapp.generated.resources.dark
import rikkaui.composeapp.generated.resources.dark_mode_label
import rikkaui.composeapp.generated.resources.light
import rikkaui.composeapp.generated.resources.palette
import rikkaui.composeapp.generated.resources.style_label
import rikkaui.composeapp.generated.resources.theme_description
import rikkaui.composeapp.generated.resources.theme_title
import rikkaui.composeapp.generated.resources.toggle_dark_mode
import zed.rainxch.rikkaui.components.theme.RikkaAccentPreset
import zed.rainxch.rikkaui.components.theme.RikkaPalette
import zed.rainxch.rikkaui.components.theme.RikkaStylePreset
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonAnimation
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.toggle.Toggle

@Composable
fun ThemeSection(
    isDark: Boolean,
    onDarkChange: (Boolean) -> Unit,
    palette: RikkaPalette,
    onPaletteChange: (RikkaPalette) -> Unit,
    accent: RikkaAccentPreset,
    onAccentChange: (RikkaAccentPreset) -> Unit,
    stylePreset: RikkaStylePreset,
    onStyleChange: (RikkaStylePreset) -> Unit,
) {
    Spacer(Modifier.height(RikkaTheme.spacing.xl))

    SectionHeader(
        title = stringResource(Res.string.theme_title),
        description = stringResource(Res.string.theme_description),
    )

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    Card(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(Res.string.style_label),
            variant = TextVariant.Small,
        )
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        FlowRow(
            horizontalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.sm),
            verticalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.sm),
        ) {
            RikkaStylePreset.entries.forEach { preset ->
                Button(
                    text = preset.label,
                    onClick = { onStyleChange(preset) },
                    variant =
                        if (stylePreset == preset) {
                            ButtonVariant.Default
                        } else {
                            ButtonVariant.Outline
                        },
                    size = ButtonSize.Sm,
                    animation = ButtonAnimation.Scale,
                )
            }
        }

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        Text(
            text = stringResource(Res.string.palette),
            variant = TextVariant.Small,
        )
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        FlowRow(
            horizontalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.sm),
            verticalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.sm),
        ) {
            RikkaPalette.entries.forEach { entry ->
                Button(
                    text = entry.label,
                    onClick = { onPaletteChange(entry) },
                    variant =
                        if (palette == entry) {
                            ButtonVariant.Default
                        } else {
                            ButtonVariant.Outline
                        },
                    size = ButtonSize.Sm,
                    animation = ButtonAnimation.Scale,
                )
            }
        }

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        Text(
            text = stringResource(Res.string.accent),
            variant = TextVariant.Small,
        )
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        FlowRow(
            horizontalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.sm),
            verticalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.sm),
        ) {
            RikkaAccentPreset.entries.forEach { entry ->
                Button(
                    onClick = { onAccentChange(entry) },
                    variant =
                        if (accent == entry) {
                            ButtonVariant.Default
                        } else {
                            ButtonVariant.Outline
                        },
                    size = ButtonSize.Sm,
                    animation = ButtonAnimation.Scale,
                ) {
                    val swatch = entry.previewColor
                    if (swatch != null) {
                        Box(
                            modifier =
                                Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(
                                        swatch,
                                        CircleShape,
                                    ),
                        )
                        Spacer(
                            Modifier.width(RikkaTheme.spacing.xs),
                        )
                    }
                    Text(
                        text = entry.label,
                        color =
                            if (accent == entry) {
                                RikkaTheme.colors.primaryForeground
                            } else {
                                RikkaTheme.colors.foreground
                            },
                        style = RikkaTheme.typography.small,
                    )
                }
            }
        }
    }

    Spacer(Modifier.height(RikkaTheme.spacing.xl))
}
