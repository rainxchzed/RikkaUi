package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.Res
import rikkaui.feature.docs.generated.resources.theming_accent_blue_desc
import rikkaui.feature.docs.generated.resources.theming_accent_default_desc
import rikkaui.feature.docs.generated.resources.theming_accent_green_desc
import rikkaui.feature.docs.generated.resources.theming_accent_orange_desc
import rikkaui.feature.docs.generated.resources.theming_accent_red_desc
import rikkaui.feature.docs.generated.resources.theming_accent_rose_desc
import rikkaui.feature.docs.generated.resources.theming_accent_violet_desc
import rikkaui.feature.docs.generated.resources.theming_accent_yellow_desc
import rikkaui.feature.docs.generated.resources.theming_accents_body
import rikkaui.feature.docs.generated.resources.theming_color_tokens_body
import rikkaui.feature.docs.generated.resources.theming_desc
import rikkaui.feature.docs.generated.resources.theming_palette_gray_desc
import rikkaui.feature.docs.generated.resources.theming_palette_neutral_desc
import rikkaui.feature.docs.generated.resources.theming_palette_slate_desc
import rikkaui.feature.docs.generated.resources.theming_palette_stone_desc
import rikkaui.feature.docs.generated.resources.theming_palette_zinc_desc
import rikkaui.feature.docs.generated.resources.theming_palettes_body
import rikkaui.feature.docs.generated.resources.theming_preset_aurora_desc
import rikkaui.feature.docs.generated.resources.theming_preset_default_desc
import rikkaui.feature.docs.generated.resources.theming_preset_nebula_desc
import rikkaui.feature.docs.generated.resources.theming_preset_nova_desc
import rikkaui.feature.docs.generated.resources.theming_preset_vega_desc
import rikkaui.feature.docs.generated.resources.theming_quick_start_body
import rikkaui.feature.docs.generated.resources.theming_section_accent_colors
import rikkaui.feature.docs.generated.resources.theming_section_color_palettes
import rikkaui.feature.docs.generated.resources.theming_section_color_tokens
import rikkaui.feature.docs.generated.resources.theming_section_quick_start
import rikkaui.feature.docs.generated.resources.theming_section_style_presets
import rikkaui.feature.docs.generated.resources.theming_section_token_overrides
import rikkaui.feature.docs.generated.resources.theming_style_presets_body
import rikkaui.feature.docs.generated.resources.theming_subsection_border_colors
import rikkaui.feature.docs.generated.resources.theming_subsection_interactive_colors
import rikkaui.feature.docs.generated.resources.theming_subsection_subtle_colors
import rikkaui.feature.docs.generated.resources.theming_subsection_surface_colors
import rikkaui.feature.docs.generated.resources.theming_title
import rikkaui.feature.docs.generated.resources.theming_token_accent_desc
import rikkaui.feature.docs.generated.resources.theming_token_accent_foreground_desc
import rikkaui.feature.docs.generated.resources.theming_token_background_desc
import rikkaui.feature.docs.generated.resources.theming_token_border_desc
import rikkaui.feature.docs.generated.resources.theming_token_card_desc
import rikkaui.feature.docs.generated.resources.theming_token_card_foreground_desc
import rikkaui.feature.docs.generated.resources.theming_token_destructive_desc
import rikkaui.feature.docs.generated.resources.theming_token_destructive_foreground_desc
import rikkaui.feature.docs.generated.resources.theming_token_foreground_desc
import rikkaui.feature.docs.generated.resources.theming_token_input_desc
import rikkaui.feature.docs.generated.resources.theming_token_muted_desc
import rikkaui.feature.docs.generated.resources.theming_token_muted_foreground_desc
import rikkaui.feature.docs.generated.resources.theming_token_overrides_body
import rikkaui.feature.docs.generated.resources.theming_token_popover_desc
import rikkaui.feature.docs.generated.resources.theming_token_popover_foreground_desc
import rikkaui.feature.docs.generated.resources.theming_token_primary_desc
import rikkaui.feature.docs.generated.resources.theming_token_primary_foreground_desc
import rikkaui.feature.docs.generated.resources.theming_token_ring_desc
import rikkaui.feature.docs.generated.resources.theming_token_secondary_desc
import rikkaui.feature.docs.generated.resources.theming_token_secondary_foreground_desc
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable

/**
 * Theming documentation page.
 *
 * Covers RikkaTheme setup, palettes, accents, style presets,
 * and individual token customization.
 */
@Composable
fun ThemingDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.theming_title),
        description = stringResource(Res.string.theming_desc),
    )

    DocSection(
        stringResource(Res.string.theming_section_quick_start),
    ) {
        Text(
            text = stringResource(
                Res.string.theming_quick_start_body,
            ),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        CodeBlock(
            """
// Simplest setup — just pick a palette
RikkaTheme(
    palette = RikkaPalette.Zinc,
    isDark = true,
) {
    // All components now use Zinc dark colors
}

// Full customization in one call
RikkaTheme(
    palette = RikkaPalette.Slate,
    accent = RikkaAccentPreset.Blue,
    isDark = true,
    preset = RikkaStylePreset.Vega,
) {
    // Slate palette + blue accent + rounded style
}
            """.trimIndent(),
        )
    }

    DocSection(
        stringResource(Res.string.theming_section_color_palettes),
    ) {
        Text(
            text = stringResource(
                Res.string.theming_palettes_body,
            ),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        PropsTable(
            props =
                listOf(
                    PropInfo(
                        "Zinc",
                        "RikkaPalette",
                        "Default",
                        stringResource(
                            Res.string.theming_palette_zinc_desc,
                        ),
                    ),
                    PropInfo(
                        "Slate",
                        "RikkaPalette",
                        "\u2014",
                        stringResource(
                            Res.string.theming_palette_slate_desc,
                        ),
                    ),
                    PropInfo(
                        "Stone",
                        "RikkaPalette",
                        "\u2014",
                        stringResource(
                            Res.string.theming_palette_stone_desc,
                        ),
                    ),
                    PropInfo(
                        "Gray",
                        "RikkaPalette",
                        "\u2014",
                        stringResource(
                            Res.string.theming_palette_gray_desc,
                        ),
                    ),
                    PropInfo(
                        "Neutral",
                        "RikkaPalette",
                        "\u2014",
                        stringResource(
                            Res.string.theming_palette_neutral_desc,
                        ),
                    ),
                ),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        CodeBlock(
            """
// Switch palettes
RikkaTheme(palette = RikkaPalette.Slate, isDark = true) {
    // Cool blue-gray UI
}
            """.trimIndent(),
        )
    }

    DocSection(
        stringResource(Res.string.theming_section_accent_colors),
    ) {
        Text(
            text = stringResource(
                Res.string.theming_accents_body,
            ),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        DemoBox {
            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
            ) {
                AccentSwatch(
                    stringResource(
                        Res.string.theming_accent_default_desc,
                    ).substringBefore("."),
                    RikkaTheme.colors.primary,
                )
            }
        }

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        CodeBlock(
            """
// Apply a blue accent on any palette
RikkaTheme(
    palette = RikkaPalette.Zinc,
    accent = RikkaAccentPreset.Blue,
    isDark = true,
) { /* Zinc colors + blue primary */ }
            """.trimIndent(),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        PropsTable(
            props =
                listOf(
                    PropInfo(
                        "Default",
                        "RikkaAccentPreset",
                        "Yes",
                        stringResource(
                            Res.string.theming_accent_default_desc,
                        ),
                    ),
                    PropInfo(
                        "Blue",
                        "RikkaAccentPreset",
                        "\u2014",
                        stringResource(
                            Res.string.theming_accent_blue_desc,
                        ),
                    ),
                    PropInfo(
                        "Green",
                        "RikkaAccentPreset",
                        "\u2014",
                        stringResource(
                            Res.string.theming_accent_green_desc,
                        ),
                    ),
                    PropInfo(
                        "Orange",
                        "RikkaAccentPreset",
                        "\u2014",
                        stringResource(
                            Res.string.theming_accent_orange_desc,
                        ),
                    ),
                    PropInfo(
                        "Red",
                        "RikkaAccentPreset",
                        "\u2014",
                        stringResource(
                            Res.string.theming_accent_red_desc,
                        ),
                    ),
                    PropInfo(
                        "Rose",
                        "RikkaAccentPreset",
                        "\u2014",
                        stringResource(
                            Res.string.theming_accent_rose_desc,
                        ),
                    ),
                    PropInfo(
                        "Violet",
                        "RikkaAccentPreset",
                        "\u2014",
                        stringResource(
                            Res.string.theming_accent_violet_desc,
                        ),
                    ),
                    PropInfo(
                        "Yellow",
                        "RikkaAccentPreset",
                        "\u2014",
                        stringResource(
                            Res.string.theming_accent_yellow_desc,
                        ),
                    ),
                ),
        )
    }

    DocSection(
        stringResource(Res.string.theming_section_style_presets),
    ) {
        Text(
            text = stringResource(
                Res.string.theming_style_presets_body,
            ),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        CodeBlock(
            """
// One-line style switch
RikkaTheme(
    palette = RikkaPalette.Zinc,
    isDark = true,
    preset = RikkaStylePreset.Vega,
) { /* rounded, bouncy, spacious */ }
            """.trimIndent(),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        PropsTable(
            props =
                listOf(
                    PropInfo(
                        "Default",
                        "RikkaStylePreset",
                        "Yes",
                        stringResource(
                            Res.string.theming_preset_default_desc,
                        ),
                    ),
                    PropInfo(
                        "Nova",
                        "RikkaStylePreset",
                        "\u2014",
                        stringResource(
                            Res.string.theming_preset_nova_desc,
                        ),
                    ),
                    PropInfo(
                        "Vega",
                        "RikkaStylePreset",
                        "\u2014",
                        stringResource(
                            Res.string.theming_preset_vega_desc,
                        ),
                    ),
                    PropInfo(
                        "Aurora",
                        "RikkaStylePreset",
                        "\u2014",
                        stringResource(
                            Res.string.theming_preset_aurora_desc,
                        ),
                    ),
                    PropInfo(
                        "Nebula",
                        "RikkaStylePreset",
                        "\u2014",
                        stringResource(
                            Res.string.theming_preset_nebula_desc,
                        ),
                    ),
                ),
        )
    }

    DocSection(
        stringResource(Res.string.theming_section_token_overrides),
    ) {
        Text(
            text = stringResource(
                Res.string.theming_token_overrides_body,
            ),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        CodeBlock(
            """
// Custom typography
val typography = rikkaTypography(
    fontFamily = myFont,
    scale = 1.1f,       // 10% larger
    h1Size = 48.sp,     // override specific sizes
)

// Custom spacing
val spacing = rikkaSpacing(base = 5.dp)
// Generates: xs=5, sm=10, md=15, lg=20, xl=30...

// Custom shapes
val shapes = rikkaShapes(radius = 16.dp)
// Generates: sm=8, md=16, lg=24, xl=32, full=999

// Custom motion
val motion = RikkaMotionPresets.snappy()
// Or: .playful(), .minimal()

// Mix palette convenience with manual tokens
val colors = RikkaPalette.Zinc.resolve(isDark = true)
RikkaTheme(
    colors = colors,
    typography = typography,
    spacing = spacing,
    shapes = shapes,
    motion = motion,
) { /* ... */ }
            """.trimIndent(),
        )
    }

    DocSection(
        stringResource(Res.string.theming_section_color_tokens),
    ) {
        Text(
            text = stringResource(
                Res.string.theming_color_tokens_body,
            ),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        Text(
            text = stringResource(
                Res.string.theming_subsection_surface_colors,
            ),
            variant = TextVariant.H4,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        PropsTable(
            props =
                listOf(
                    PropInfo(
                        "background",
                        "Color",
                        "\u2014",
                        stringResource(
                            Res.string.theming_token_background_desc,
                        ),
                    ),
                    PropInfo(
                        "foreground",
                        "Color",
                        "\u2014",
                        stringResource(
                            Res.string.theming_token_foreground_desc,
                        ),
                    ),
                    PropInfo(
                        "card",
                        "Color",
                        "\u2014",
                        stringResource(
                            Res.string.theming_token_card_desc,
                        ),
                    ),
                    PropInfo(
                        "cardForeground",
                        "Color",
                        "\u2014",
                        stringResource(
                            Res.string.theming_token_card_foreground_desc,
                        ),
                    ),
                    PropInfo(
                        "popover",
                        "Color",
                        "\u2014",
                        stringResource(
                            Res.string.theming_token_popover_desc,
                        ),
                    ),
                    PropInfo(
                        "popoverForeground",
                        "Color",
                        "\u2014",
                        stringResource(
                            Res.string.theming_token_popover_foreground_desc,
                        ),
                    ),
                ),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        Text(
            text = stringResource(
                Res.string.theming_subsection_interactive_colors,
            ),
            variant = TextVariant.H4,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        PropsTable(
            props =
                listOf(
                    PropInfo(
                        "primary",
                        "Color",
                        "\u2014",
                        stringResource(
                            Res.string.theming_token_primary_desc,
                        ),
                    ),
                    PropInfo(
                        "primaryForeground",
                        "Color",
                        "\u2014",
                        stringResource(
                            Res.string.theming_token_primary_foreground_desc,
                        ),
                    ),
                    PropInfo(
                        "secondary",
                        "Color",
                        "\u2014",
                        stringResource(
                            Res.string.theming_token_secondary_desc,
                        ),
                    ),
                    PropInfo(
                        "secondaryForeground",
                        "Color",
                        "\u2014",
                        stringResource(
                            Res.string.theming_token_secondary_foreground_desc,
                        ),
                    ),
                    PropInfo(
                        "destructive",
                        "Color",
                        "\u2014",
                        stringResource(
                            Res.string.theming_token_destructive_desc,
                        ),
                    ),
                    PropInfo(
                        "destructiveForeground",
                        "Color",
                        "\u2014",
                        stringResource(
                            Res.string.theming_token_destructive_foreground_desc,
                        ),
                    ),
                ),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        Text(
            text = stringResource(
                Res.string.theming_subsection_subtle_colors,
            ),
            variant = TextVariant.H4,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        PropsTable(
            props =
                listOf(
                    PropInfo(
                        "muted",
                        "Color",
                        "\u2014",
                        stringResource(
                            Res.string.theming_token_muted_desc,
                        ),
                    ),
                    PropInfo(
                        "mutedForeground",
                        "Color",
                        "\u2014",
                        stringResource(
                            Res.string.theming_token_muted_foreground_desc,
                        ),
                    ),
                    PropInfo(
                        "accent",
                        "Color",
                        "\u2014",
                        stringResource(
                            Res.string.theming_token_accent_desc,
                        ),
                    ),
                    PropInfo(
                        "accentForeground",
                        "Color",
                        "\u2014",
                        stringResource(
                            Res.string.theming_token_accent_foreground_desc,
                        ),
                    ),
                ),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        Text(
            text = stringResource(
                Res.string.theming_subsection_border_colors,
            ),
            variant = TextVariant.H4,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        PropsTable(
            props =
                listOf(
                    PropInfo(
                        "border",
                        "Color",
                        "\u2014",
                        stringResource(
                            Res.string.theming_token_border_desc,
                        ),
                    ),
                    PropInfo(
                        "input",
                        "Color",
                        "\u2014",
                        stringResource(
                            Res.string.theming_token_input_desc,
                        ),
                    ),
                    PropInfo(
                        "ring",
                        "Color",
                        "\u2014",
                        stringResource(
                            Res.string.theming_token_ring_desc,
                        ),
                    ),
                ),
        )
    }
}

@Composable
private fun AccentSwatch(
    name: String,
    color: androidx.compose.ui.graphics.Color,
) {
    Column(
        horizontalAlignment =
            androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.xs),
    ) {
        Box(
            modifier =
                Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(color),
        )
        Text(text = name, variant = TextVariant.Small)
    }
}
