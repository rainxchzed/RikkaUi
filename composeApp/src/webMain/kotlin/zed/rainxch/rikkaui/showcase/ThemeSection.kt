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
import zed.rainxch.rikkaui.theme.accentPreviewColor

@Composable
fun ThemeSection(
    isDark: Boolean,
    onDarkChange: (Boolean) -> Unit,
    paletteName: String,
    onPaletteChange: (String) -> Unit,
    accentName: String,
    onAccentChange: (String) -> Unit,
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
        // ─── Style ─────────────────────────────────────────
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

        // ─── Palette ───────────────────────────────────────
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
            val palettes = listOf(
                "Zinc", "Slate", "Stone", "Gray", "Neutral",
            )
            palettes.forEach { name ->
                Button(
                    text = name,
                    onClick = { onPaletteChange(name) },
                    variant =
                        if (paletteName == name) {
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

        // ─── Accent ────────────────────────────────────────
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
            val accents = listOf(
                "Default", "Blue", "Green", "Orange",
                "Red", "Rose", "Violet", "Yellow",
            )
            accents.forEach { name ->
                Button(
                    onClick = { onAccentChange(name) },
                    variant =
                        if (accentName == name) {
                            ButtonVariant.Default
                        } else {
                            ButtonVariant.Outline
                        },
                    size = ButtonSize.Sm,
                    animation = ButtonAnimation.Scale,
                ) {
                    if (name != "Default") {
                        Box(
                            modifier =
                                Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(
                                        accentPreviewColor(name),
                                        CircleShape,
                                    ),
                        )
                        Spacer(
                            Modifier.width(RikkaTheme.spacing.xs),
                        )
                    }
                    Text(
                        text = name,
                        color =
                            if (accentName == name) {
                                RikkaTheme.colors.primaryForeground
                            } else {
                                RikkaTheme.colors.foreground
                            },
                        style = RikkaTheme.typography.small,
                    )
                }
            }
        }

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        // ─── Dark Mode ─────────────────────────────────────
        Text(
            text = stringResource(Res.string.dark_mode_label),
            variant = TextVariant.Small,
        )
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.sm),
        ) {
            Toggle(
                checked = isDark,
                onCheckedChange = onDarkChange,
                label = stringResource(Res.string.toggle_dark_mode),
            )
            Text(
                text = if (isDark) {
                    stringResource(Res.string.dark)
                } else {
                    stringResource(Res.string.light)
                },
                variant = TextVariant.Muted,
            )
        }
    }

    Spacer(Modifier.height(RikkaTheme.spacing.xl))
}
