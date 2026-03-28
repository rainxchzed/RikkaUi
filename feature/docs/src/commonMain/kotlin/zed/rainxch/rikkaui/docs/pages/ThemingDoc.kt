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
        name = "Theming",
        description =
            "Customize every aspect of RikkaUI with " +
                "palettes, accents, style presets, and " +
                "individual token overrides.",
    )

    DocSection("Quick Start") {
        Text(
            text =
                "Wrap your app in RikkaTheme. " +
                    "One function call gives you colors, " +
                    "typography, spacing, shapes, and " +
                    "motion tokens for all components.",
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

    DocSection("Color Palettes") {
        Text(
            text =
                "5 base palettes, each with light and " +
                    "dark variants. Each palette defines " +
                    "20 semantic color tokens in " +
                    "background/foreground pairs.",
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
                        "Pure white bg (light), neutral " +
                            "dark bg. Clean, minimal.",
                    ),
                    PropInfo(
                        "Slate",
                        "RikkaPalette",
                        "\u2014",
                        "Cool blue-gray tint. Professional.",
                    ),
                    PropInfo(
                        "Stone",
                        "RikkaPalette",
                        "\u2014",
                        "Warm brown-gray tint. Earthy.",
                    ),
                    PropInfo(
                        "Gray",
                        "RikkaPalette",
                        "\u2014",
                        "True neutral gray. Balanced.",
                    ),
                    PropInfo(
                        "Neutral",
                        "RikkaPalette",
                        "\u2014",
                        "Slightly warm neutral. Soft.",
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

    DocSection("Accent Colors") {
        Text(
            text =
                "7 accent presets override the primary, " +
                    "primaryForeground, and ring tokens. " +
                    "Layer on top of any palette to change " +
                    "the brand color without touching " +
                    "everything else.",
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
                    "Default",
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
                        "Uses the palette\u2019s built-in " +
                            "primary color. No override.",
                    ),
                    PropInfo(
                        "Blue",
                        "RikkaAccentPreset",
                        "\u2014",
                        "Vivid blue accent.",
                    ),
                    PropInfo(
                        "Green",
                        "RikkaAccentPreset",
                        "\u2014",
                        "Fresh green accent.",
                    ),
                    PropInfo(
                        "Orange",
                        "RikkaAccentPreset",
                        "\u2014",
                        "Warm orange accent.",
                    ),
                    PropInfo(
                        "Red",
                        "RikkaAccentPreset",
                        "\u2014",
                        "Bold red accent.",
                    ),
                    PropInfo(
                        "Rose",
                        "RikkaAccentPreset",
                        "\u2014",
                        "Soft pink accent.",
                    ),
                    PropInfo(
                        "Violet",
                        "RikkaAccentPreset",
                        "\u2014",
                        "Rich purple accent.",
                    ),
                    PropInfo(
                        "Yellow",
                        "RikkaAccentPreset",
                        "\u2014",
                        "Bright yellow accent.",
                    ),
                ),
        )
    }

    DocSection("Style Presets") {
        Text(
            text =
                "Style presets bundle shapes, spacing, " +
                    "motion, and type scale into a " +
                    "single choice. Pass a preset to " +
                    "RikkaTheme to change the entire " +
                    "visual feel.",
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
                        "10dp radius, 4dp spacing, " +
                            "balanced motion, scale 1.0.",
                    ),
                    PropInfo(
                        "Nova",
                        "RikkaStylePreset",
                        "\u2014",
                        "4dp radius, 3dp spacing, " +
                            "snappy motion, scale 0.9. " +
                            "Sharp, dense.",
                    ),
                    PropInfo(
                        "Vega",
                        "RikkaStylePreset",
                        "\u2014",
                        "20dp radius, 5dp spacing, " +
                            "playful motion, scale 1.05. " +
                            "Rounded, bouncy.",
                    ),
                    PropInfo(
                        "Aurora",
                        "RikkaStylePreset",
                        "\u2014",
                        "14dp radius, 5dp spacing, " +
                            "default motion, scale 1.1. " +
                            "Spacious, large.",
                    ),
                    PropInfo(
                        "Nebula",
                        "RikkaStylePreset",
                        "\u2014",
                        "0dp radius, 3dp spacing, " +
                            "minimal motion, scale 0.85. " +
                            "Square, tight.",
                    ),
                ),
        )
    }

    DocSection("Individual Token Overrides") {
        Text(
            text =
                "For full control, override individual " +
                    "token factories. The palette + " +
                    "accent overload still works " +
                    "alongside manual overrides.",
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

    DocSection("Color Tokens Reference") {
        Text(
            text =
                "Every palette provides 20 semantic " +
                    "color tokens. Components use these " +
                    "tokens instead of raw colors, so " +
                    "switching palettes automatically " +
                    "updates your entire UI.",
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        Text(
            text = "Surface Colors",
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
                        "The main page/screen background. " +
                            "Used for the root surface of " +
                            "your app.",
                    ),
                    PropInfo(
                        "foreground",
                        "Color",
                        "\u2014",
                        "Default text color on the " +
                            "background. Used for body " +
                            "text, headings, and icons.",
                    ),
                    PropInfo(
                        "card",
                        "Color",
                        "\u2014",
                        "Background for Card, Dialog, and " +
                            "Sheet surfaces. Slightly " +
                            "elevated from background.",
                    ),
                    PropInfo(
                        "cardForeground",
                        "Color",
                        "\u2014",
                        "Text color inside cards, dialogs, " +
                            "and sheets.",
                    ),
                    PropInfo(
                        "popover",
                        "Color",
                        "\u2014",
                        "Background for floating surfaces " +
                            "like Popover, Tooltip, Select " +
                            "dropdown, and DropdownMenu.",
                    ),
                    PropInfo(
                        "popoverForeground",
                        "Color",
                        "\u2014",
                        "Text color inside floating " +
                            "surfaces.",
                    ),
                ),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        Text(
            text = "Interactive Colors",
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
                        "Your brand/accent color. Used " +
                            "for default Button, Toggle " +
                            "checked state, Progress bar, " +
                            "and focused inputs.",
                    ),
                    PropInfo(
                        "primaryForeground",
                        "Color",
                        "\u2014",
                        "Text/icon color on primary " +
                            "surfaces. Must contrast with " +
                            "primary (e.g., white on blue).",
                    ),
                    PropInfo(
                        "secondary",
                        "Color",
                        "\u2014",
                        "Background for less prominent " +
                            "actions. Used by Button " +
                            "Secondary variant and Badge " +
                            "Secondary.",
                    ),
                    PropInfo(
                        "secondaryForeground",
                        "Color",
                        "\u2014",
                        "Text color on secondary surfaces.",
                    ),
                    PropInfo(
                        "destructive",
                        "Color",
                        "\u2014",
                        "Danger/error color. Used by " +
                            "Button Destructive, Alert " +
                            "Destructive, and Badge " +
                            "Destructive.",
                    ),
                    PropInfo(
                        "destructiveForeground",
                        "Color",
                        "\u2014",
                        "Text color on destructive " +
                            "surfaces.",
                    ),
                ),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        Text(
            text = "Subtle & Muted Colors",
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
                        "Subdued background for disabled " +
                            "states, Skeleton placeholders, " +
                            "and inactive tab backgrounds.",
                    ),
                    PropInfo(
                        "mutedForeground",
                        "Color",
                        "\u2014",
                        "Low-emphasis text color. Used " +
                            "for placeholder text, " +
                            "descriptions, and labels.",
                    ),
                    PropInfo(
                        "accent",
                        "Color",
                        "\u2014",
                        "Subtle highlight for hover " +
                            "states. Used by Ghost buttons " +
                            "on hover and dropdown item " +
                            "highlights.",
                    ),
                    PropInfo(
                        "accentForeground",
                        "Color",
                        "\u2014",
                        "Text color on accent-highlighted " +
                            "surfaces.",
                    ),
                ),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        Text(
            text = "Border & Focus Colors",
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
                        "Default border for Card, Table, " +
                            "Separator, and dividers.",
                    ),
                    PropInfo(
                        "input",
                        "Color",
                        "\u2014",
                        "Border color for Input, Textarea, " +
                            "Select, and other form " +
                            "controls in their resting " +
                            "state.",
                    ),
                    PropInfo(
                        "ring",
                        "Color",
                        "\u2014",
                        "Focus ring color shown around " +
                            "interactive elements when " +
                            "focused. Accent overrides " +
                            "change this token too.",
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
