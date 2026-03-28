@file:OptIn(ExperimentalLayoutApi::class)

package zed.rainxch.rikkaui.creator.panel

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
import zed.rainxch.rikkaui.components.theme.RikkaStylePreset
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonAnimation
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.toggle.Toggle
import zed.rainxch.rikkaui.creator.accentNames
import zed.rainxch.rikkaui.creator.accentPreviewColor
import zed.rainxch.rikkaui.creator.fonts.availableFonts
import zed.rainxch.rikkaui.creator.paletteNames

/**
 * Configuration panel for the Design System Creator.
 *
 * Lets users choose: style preset, palette, accent color,
 * font, and preview dark mode. Includes a download button.
 */
@Composable
fun ConfigPanel(
    stylePreset: RikkaStylePreset,
    onStyleChange: (RikkaStylePreset) -> Unit,
    paletteName: String,
    onPaletteChange: (String) -> Unit,
    accentName: String,
    onAccentChange: (String) -> Unit,
    previewDark: Boolean,
    onPreviewDarkChange: (Boolean) -> Unit,
    fontId: String,
    onFontChange: (String) -> Unit,
    onDownload: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Create Your Design System",
        variant = TextVariant.H3,
        modifier = modifier.fillMaxWidth(),
    )

    Spacer(Modifier.height(RikkaTheme.spacing.xs))

    Text(
        text =
            "Configure your theme and preview it live." +
                " When you're happy, download the generated code.",
        variant = TextVariant.Muted,
        modifier = Modifier.fillMaxWidth(),
    )

    Spacer(Modifier.height(RikkaTheme.spacing.xl))

    // ─── Style Preset ─────────────────────────────────
    SectionLabel("Style")
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
    Separator()
    Spacer(Modifier.height(RikkaTheme.spacing.lg))

    // ─── Palette ──────────────────────────────────────
    SectionLabel("Palette")
    Spacer(Modifier.height(RikkaTheme.spacing.xs))
    FlowRow(
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
        verticalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
    ) {
        paletteNames.forEach { name ->
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
    Separator()
    Spacer(Modifier.height(RikkaTheme.spacing.lg))

    // ─── Accent ───────────────────────────────────────
    SectionLabel("Accent")
    Spacer(Modifier.height(RikkaTheme.spacing.xs))
    FlowRow(
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
        verticalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
    ) {
        accentNames.forEach { name ->
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
                val previewColor = accentPreviewColor(name)
                if (previewColor != null) {
                    Box(
                        modifier =
                            Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(previewColor, CircleShape),
                    )
                    Spacer(Modifier.width(RikkaTheme.spacing.xs))
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
    Separator()
    Spacer(Modifier.height(RikkaTheme.spacing.lg))

    // ─── Font ─────────────────────────────────────────
    SectionLabel("Font")
    Spacer(Modifier.height(RikkaTheme.spacing.xs))
    FlowRow(
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
        verticalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
    ) {
        availableFonts.forEach { font ->
            Button(
                text = font.displayName,
                onClick = { onFontChange(font.id) },
                variant =
                    if (fontId == font.id) {
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
    Separator()
    Spacer(Modifier.height(RikkaTheme.spacing.lg))

    // ─── Preview Dark Mode ────────────────────────────
    SectionLabel("Preview Mode")
    Spacer(Modifier.height(RikkaTheme.spacing.xs))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
    ) {
        Toggle(
            checked = previewDark,
            onCheckedChange = onPreviewDarkChange,
            label = "Toggle preview dark mode",
        )
        Text(
            text = if (previewDark) "Dark" else "Light",
            variant = TextVariant.Muted,
        )
    }

    Spacer(Modifier.height(RikkaTheme.spacing.xxl))

    // ─── Download Button ──────────────────────────────
    Button(
        text = "Download Theme",
        onClick = onDownload,
        animation = ButtonAnimation.Bounce,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        variant = TextVariant.Small,
    )
}
