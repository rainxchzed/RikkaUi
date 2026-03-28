package zed.rainxch.rikkaui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeViewport
import zed.rainxch.rikkaui.components.theme.RikkaAccent
import zed.rainxch.rikkaui.components.theme.RikkaAccentDark
import zed.rainxch.rikkaui.components.theme.RikkaColors
import zed.rainxch.rikkaui.components.theme.RikkaPalettes
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.theme.withAccent
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonAnimation
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.card.CardContent
import zed.rainxch.rikkaui.components.ui.card.CardFooter
import zed.rainxch.rikkaui.components.ui.card.CardHeader
import zed.rainxch.rikkaui.components.ui.card.CardVariant
import zed.rainxch.rikkaui.components.ui.input.Input
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.toggle.Toggle
import zed.rainxch.rikkaui.components.ui.toggle.ToggleSize

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        var isDark by remember { mutableStateOf(true) }
        var paletteName by remember { mutableStateOf("Zinc") }
        var accentName by remember { mutableStateOf("Default") }

        val baseColors = resolvePalette(paletteName, isDark)
        val colors = resolveAccent(baseColors, accentName, isDark)

        RikkaTheme(colors = colors) {
            ShowcaseApp(
                isDark = isDark,
                onDarkChange = { isDark = it },
                paletteName = paletteName,
                onPaletteChange = { paletteName = it },
                accentName = accentName,
                onAccentChange = { accentName = it },
            )
        }
    }
}

private fun resolvePalette(
    name: String,
    isDark: Boolean,
): RikkaColors =
    when (name) {
        "Neutral" -> if (isDark) RikkaPalettes.NeutralDark else RikkaPalettes.NeutralLight
        "Slate" -> if (isDark) RikkaPalettes.SlateDark else RikkaPalettes.SlateLight
        "Stone" -> if (isDark) RikkaPalettes.StoneDark else RikkaPalettes.StoneLight
        "Gray" -> if (isDark) RikkaPalettes.GrayDark else RikkaPalettes.GrayLight
        else -> if (isDark) RikkaPalettes.ZincDark else RikkaPalettes.ZincLight
    }

private fun resolveAccent(
    base: RikkaColors,
    accentName: String,
    isDark: Boolean,
): RikkaColors {
    if (accentName == "Default") return base
    val accent =
        if (isDark) {
            when (accentName) {
                "Red" -> RikkaAccentDark.Red
                "Rose" -> RikkaAccentDark.Rose
                "Orange" -> RikkaAccentDark.Orange
                "Green" -> RikkaAccentDark.Green
                "Blue" -> RikkaAccentDark.Blue
                "Yellow" -> RikkaAccentDark.Yellow
                "Violet" -> RikkaAccentDark.Violet
                else -> return base
            }
        } else {
            when (accentName) {
                "Red" -> RikkaAccent.Red
                "Rose" -> RikkaAccent.Rose
                "Orange" -> RikkaAccent.Orange
                "Green" -> RikkaAccent.Green
                "Blue" -> RikkaAccent.Blue
                "Yellow" -> RikkaAccent.Yellow
                "Violet" -> RikkaAccent.Violet
                else -> return base
            }
        }
    return base.withAccent(accent)
}

/** Accent swatch preview color (light mode values for visibility). */
private fun accentPreviewColor(name: String): Color =
    when (name) {
        "Red" -> Color(0xFFDC2626)
        "Rose" -> Color(0xFFE11D48)
        "Orange" -> Color(0xFFF97316)
        "Green" -> Color(0xFF16A34A)
        "Blue" -> Color(0xFF2563EB)
        "Yellow" -> Color(0xFFFACC15)
        "Violet" -> Color(0xFF7C3AED)
        else -> Color.Transparent
    }

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ShowcaseApp(
    isDark: Boolean,
    onDarkChange: (Boolean) -> Unit,
    paletteName: String,
    onPaletteChange: (String) -> Unit,
    accentName: String,
    onAccentChange: (String) -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(RikkaTheme.colors.background),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier =
                Modifier
                    .widthIn(max = 800.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = RikkaTheme.spacing.lg),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // ─── Hero Section ────────────────────────────────
            Spacer(Modifier.height(RikkaTheme.spacing.xxxl))

            Badge("Preview", variant = BadgeVariant.Outline)

            Spacer(Modifier.height(RikkaTheme.spacing.md))

            Text(
                text = "RikkaUI",
                variant = TextVariant.H1,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.sm))

            Text(
                text = "Beautiful components for Compose Multiplatform",
                variant = TextVariant.Lead,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.sm))

            Text(
                text =
                    "A shadcn/ui-inspired design system built entirely in Kotlin. " +
                        "Copy-paste components with theme tokens, spring animations, " +
                        "and zero Material dependency. Ships to Android, iOS, Desktop, and Web.",
                variant = TextVariant.Muted,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.xl))

            Row(
                horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
            ) {
                Button(
                    text = "Get Started",
                    onClick = { },
                    animation = ButtonAnimation.Bounce,
                )
                Button(
                    text = "GitHub",
                    onClick = { },
                    variant = ButtonVariant.Outline,
                )
            }

            Spacer(Modifier.height(RikkaTheme.spacing.xxxl))

            Separator()

            // ─── Theme Switcher ──────────────────────────────
            Spacer(Modifier.height(RikkaTheme.spacing.xl))

            SectionHeader(
                title = "Theme",
                description = "Switch palettes and toggle dark mode in real time.",
            )

            Spacer(Modifier.height(RikkaTheme.spacing.md))

            Card(modifier = Modifier.fillMaxWidth()) {
                Text("Palette", variant = TextVariant.Small)

                Spacer(Modifier.height(RikkaTheme.spacing.xs))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                    verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                ) {
                    listOf("Zinc", "Slate", "Stone", "Gray", "Neutral").forEach { name ->
                        val isSelected = paletteName == name
                        Button(
                            text = name,
                            onClick = { onPaletteChange(name) },
                            variant =
                                if (isSelected) {
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

                Text("Accent", variant = TextVariant.Small)

                Spacer(Modifier.height(RikkaTheme.spacing.xs))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                    verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                ) {
                    listOf(
                        "Default",
                        "Blue",
                        "Green",
                        "Orange",
                        "Red",
                        "Rose",
                        "Violet",
                        "Yellow",
                    ).forEach { name ->
                        val isSelected = accentName == name
                        Button(
                            onClick = { onAccentChange(name) },
                            variant = if (isSelected) ButtonVariant.Default else ButtonVariant.Outline,
                            size = ButtonSize.Sm,
                            animation = ButtonAnimation.Scale,
                        ) {
                            if (name != "Default") {
                                Box(
                                    modifier =
                                        Modifier
                                            .size(12.dp)
                                            .clip(CircleShape)
                                            .background(accentPreviewColor(name), CircleShape),
                                )
                                Spacer(Modifier.padding(start = RikkaTheme.spacing.xs))
                            }
                            Text(
                                name,
                                color =
                                    if (isSelected) {
                                        RikkaTheme.colors.primaryForeground
                                    } else {
                                        RikkaTheme.colors.foreground
                                    },
                                variant = TextVariant.Small,
                            )
                        }
                    }
                }

                Spacer(Modifier.height(RikkaTheme.spacing.lg))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                ) {
                    Text("Dark mode", variant = TextVariant.Small)
                    Toggle(
                        checked = isDark,
                        onCheckedChange = onDarkChange,
                    )
                }
            }

            Spacer(Modifier.height(RikkaTheme.spacing.xxxl))

            Separator()

            // ─── Components Showcase ─────────────────────────
            Spacer(Modifier.height(RikkaTheme.spacing.xl))

            Text(
                text = "Components",
                variant = TextVariant.H2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.xs))

            Text(
                text = "Every component, every variant. Built with Rikka theme tokens.",
                variant = TextVariant.Muted,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.xxl))

            // ── Text ──────────────────────────────────────────
            SectionHeader(
                title = "Text",
                description = "Typography variants from H1 to Muted.",
            )

            Spacer(Modifier.height(RikkaTheme.spacing.md))

            Card(modifier = Modifier.fillMaxWidth()) {
                Text("Heading 1", variant = TextVariant.H1)
                Text("Heading 2", variant = TextVariant.H2)
                Text("Heading 3", variant = TextVariant.H3)
                Text("Heading 4", variant = TextVariant.H4)
                Text(
                    "Paragraph - The quick brown fox jumps over the lazy dog.",
                    variant = TextVariant.P,
                )
                Text(
                    "Lead text with a slightly larger and lighter style.",
                    variant = TextVariant.Lead,
                )
                Text("Large semi-bold text", variant = TextVariant.Large)
                Text("Small medium-weight text", variant = TextVariant.Small)
                Text(
                    "Muted text for secondary information.",
                    variant = TextVariant.Muted,
                )
            }

            Spacer(Modifier.height(RikkaTheme.spacing.xxl))

            Separator()

            Spacer(Modifier.height(RikkaTheme.spacing.xxl))

            // ── Button ────────────────────────────────────────
            SectionHeader(
                title = "Button",
                description = "Six variants, three sizes, and spring-physics animations.",
            )

            Spacer(Modifier.height(RikkaTheme.spacing.md))

            Card(modifier = Modifier.fillMaxWidth()) {
                Text("Variants", variant = TextVariant.Small)

                Spacer(Modifier.height(RikkaTheme.spacing.xs))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                    verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                ) {
                    Button(text = "Default", onClick = { })
                    Button(
                        text = "Outline",
                        onClick = { },
                        variant = ButtonVariant.Outline,
                    )
                    Button(
                        text = "Secondary",
                        onClick = { },
                        variant = ButtonVariant.Secondary,
                    )
                    Button(
                        text = "Ghost",
                        onClick = { },
                        variant = ButtonVariant.Ghost,
                    )
                    Button(
                        text = "Destructive",
                        onClick = { },
                        variant = ButtonVariant.Destructive,
                    )
                    Button(
                        text = "Link",
                        onClick = { },
                        variant = ButtonVariant.Link,
                    )
                }

                Spacer(Modifier.height(RikkaTheme.spacing.lg))

                Text("Sizes", variant = TextVariant.Small)

                Spacer(Modifier.height(RikkaTheme.spacing.xs))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                    verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Button(text = "Large", onClick = { }, size = ButtonSize.Lg)
                    Button(text = "Default", onClick = { }, size = ButtonSize.Default)
                    Button(text = "Small", onClick = { }, size = ButtonSize.Sm)
                    Button(onClick = { }, size = ButtonSize.Icon) {
                        Text(
                            "+",
                            color = RikkaTheme.colors.primaryForeground,
                        )
                    }
                }

                Spacer(Modifier.height(RikkaTheme.spacing.lg))

                Text("Animations", variant = TextVariant.Small)

                Spacer(Modifier.height(RikkaTheme.spacing.xs))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                    verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                ) {
                    Button(
                        text = "None",
                        onClick = { },
                        animation = ButtonAnimation.None,
                    )
                    Button(
                        text = "Scale",
                        onClick = { },
                        animation = ButtonAnimation.Scale,
                    )
                    Button(
                        text = "Bounce",
                        onClick = { },
                        animation = ButtonAnimation.Bounce,
                    )
                }

                Spacer(Modifier.height(RikkaTheme.spacing.lg))

                Text("States", variant = TextVariant.Small)

                Spacer(Modifier.height(RikkaTheme.spacing.xs))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                    verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                ) {
                    Button(text = "Enabled", onClick = { })
                    Button(text = "Disabled", onClick = { }, enabled = false)
                }
            }

            Spacer(Modifier.height(RikkaTheme.spacing.xxl))

            Separator()

            Spacer(Modifier.height(RikkaTheme.spacing.xxl))

            // ── Card ──────────────────────────────────────────
            SectionHeader(
                title = "Card",
                description = "Container variants with structured header, content, and footer sections.",
            )

            Spacer(Modifier.height(RikkaTheme.spacing.md))

            Card(modifier = Modifier.fillMaxWidth(), variant = CardVariant.Ghost) {
                Text("Default", variant = TextVariant.Small)

                Spacer(Modifier.height(RikkaTheme.spacing.xs))

                Card(modifier = Modifier.fillMaxWidth()) {
                    CardHeader {
                        Text("Card Title", variant = TextVariant.H4)
                        Text(
                            "A default card with a border and background.",
                            variant = TextVariant.Muted,
                        )
                    }
                    CardContent {
                        Text(
                            "This is the card content area. Put anything here.",
                            variant = TextVariant.P,
                        )
                    }
                    CardFooter {
                        Button(
                            text = "Action",
                            onClick = { },
                            size = ButtonSize.Sm,
                        )
                    }
                }

                Spacer(Modifier.height(RikkaTheme.spacing.lg))

                Text("Elevated", variant = TextVariant.Small)

                Spacer(Modifier.height(RikkaTheme.spacing.xs))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    variant = CardVariant.Elevated,
                ) {
                    CardHeader {
                        Text("Elevated Card", variant = TextVariant.H4)
                        Text(
                            "Subtle shadow, no border. Adds depth to the layout.",
                            variant = TextVariant.Muted,
                        )
                    }
                    CardContent {
                        Text(
                            "Elevated cards create visual hierarchy through shadow.",
                            variant = TextVariant.P,
                        )
                    }
                }

                Spacer(Modifier.height(RikkaTheme.spacing.lg))

                Text("Ghost", variant = TextVariant.Small)

                Spacer(Modifier.height(RikkaTheme.spacing.xs))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    variant = CardVariant.Ghost,
                ) {
                    CardHeader {
                        Text("Ghost Card", variant = TextVariant.H4)
                        Text(
                            "No border, no shadow, no background. Just structure.",
                            variant = TextVariant.Muted,
                        )
                    }
                }
            }

            Spacer(Modifier.height(RikkaTheme.spacing.xxl))

            Separator()

            Spacer(Modifier.height(RikkaTheme.spacing.xxl))

            // ── Badge ─────────────────────────────────────────
            SectionHeader(
                title = "Badge",
                description = "Small status indicators in four variants.",
            )

            Spacer(Modifier.height(RikkaTheme.spacing.md))

            Card(modifier = Modifier.fillMaxWidth()) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                    verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                ) {
                    Badge("Default")
                    Badge("Secondary", variant = BadgeVariant.Secondary)
                    Badge("Outline", variant = BadgeVariant.Outline)
                    Badge("Destructive", variant = BadgeVariant.Destructive)
                }
            }

            Spacer(Modifier.height(RikkaTheme.spacing.xxl))

            Separator()

            Spacer(Modifier.height(RikkaTheme.spacing.xxl))

            // ── Separator ─────────────────────────────────────
            SectionHeader(
                title = "Separator",
                description = "Horizontal and vertical dividers for visual separation.",
            )

            Spacer(Modifier.height(RikkaTheme.spacing.md))

            Card(modifier = Modifier.fillMaxWidth()) {
                Text("Horizontal (default)", variant = TextVariant.Small)

                Spacer(Modifier.height(RikkaTheme.spacing.sm))

                Text("Content above", variant = TextVariant.P)
                Separator(
                    modifier = Modifier.padding(vertical = RikkaTheme.spacing.sm),
                )
                Text("Content below", variant = TextVariant.P)

                Spacer(Modifier.height(RikkaTheme.spacing.lg))

                Text("Custom thickness and color", variant = TextVariant.Small)

                Spacer(Modifier.height(RikkaTheme.spacing.sm))

                Separator(
                    modifier = Modifier.padding(vertical = RikkaTheme.spacing.sm),
                    color = RikkaTheme.colors.primary,
                    thickness = 2.dp,
                )
            }

            Spacer(Modifier.height(RikkaTheme.spacing.xxl))

            Separator()

            Spacer(Modifier.height(RikkaTheme.spacing.xxl))

            // ── Input ─────────────────────────────────────────
            SectionHeader(
                title = "Input",
                description = "Text fields with animated focus borders and placeholder support.",
            )

            Spacer(Modifier.height(RikkaTheme.spacing.md))

            Card(modifier = Modifier.fillMaxWidth()) {
                var nameValue by remember { mutableStateOf("") }
                var emailValue by remember { mutableStateOf("") }

                Text("Default", variant = TextVariant.Small)

                Spacer(Modifier.height(RikkaTheme.spacing.xs))

                Input(
                    value = nameValue,
                    onValueChange = { nameValue = it },
                    placeholder = "Enter your name...",
                )

                Spacer(Modifier.height(RikkaTheme.spacing.md))

                Text("With placeholder", variant = TextVariant.Small)

                Spacer(Modifier.height(RikkaTheme.spacing.xs))

                Input(
                    value = emailValue,
                    onValueChange = { emailValue = it },
                    placeholder = "email@example.com",
                )

                Spacer(Modifier.height(RikkaTheme.spacing.md))

                Text("Disabled", variant = TextVariant.Small)

                Spacer(Modifier.height(RikkaTheme.spacing.xs))

                Input(
                    value = "Cannot edit this",
                    onValueChange = { },
                    enabled = false,
                )

                Spacer(Modifier.height(RikkaTheme.spacing.md))

                Text("Read-only", variant = TextVariant.Small)

                Spacer(Modifier.height(RikkaTheme.spacing.xs))

                Input(
                    value = "Read-only content",
                    onValueChange = { },
                    readOnly = true,
                )
            }

            Spacer(Modifier.height(RikkaTheme.spacing.xxl))

            Separator()

            Spacer(Modifier.height(RikkaTheme.spacing.xxl))

            // ── Toggle ────────────────────────────────────────
            SectionHeader(
                title = "Toggle",
                description = "Boolean switch with spring-animated thumb and smooth color transitions.",
            )

            Spacer(Modifier.height(RikkaTheme.spacing.md))

            Card(modifier = Modifier.fillMaxWidth()) {
                var toggle1 by remember { mutableStateOf(false) }
                var toggle2 by remember { mutableStateOf(true) }
                var toggle3 by remember { mutableStateOf(false) }
                var toggle4 by remember { mutableStateOf(true) }

                Text("Default size", variant = TextVariant.Small)

                Spacer(Modifier.height(RikkaTheme.spacing.xs))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement =
                        Arrangement.spacedBy(
                            RikkaTheme.spacing.sm,
                        ),
                ) {
                    Toggle(
                        checked = toggle1,
                        onCheckedChange = { toggle1 = it },
                    )
                    Text(
                        text = if (toggle1) "On" else "Off",
                        variant = TextVariant.Small,
                    )
                }

                Spacer(Modifier.height(RikkaTheme.spacing.md))

                Text("Small size", variant = TextVariant.Small)

                Spacer(Modifier.height(RikkaTheme.spacing.xs))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement =
                        Arrangement.spacedBy(
                            RikkaTheme.spacing.sm,
                        ),
                ) {
                    Toggle(
                        checked = toggle2,
                        onCheckedChange = { toggle2 = it },
                        size = ToggleSize.Sm,
                    )
                    Text(
                        text = if (toggle2) "On" else "Off",
                        variant = TextVariant.Small,
                    )
                }

                Spacer(Modifier.height(RikkaTheme.spacing.md))

                Text("Disabled (off)", variant = TextVariant.Small)

                Spacer(Modifier.height(RikkaTheme.spacing.xs))

                Toggle(
                    checked = toggle3,
                    onCheckedChange = { toggle3 = it },
                    enabled = false,
                )

                Spacer(Modifier.height(RikkaTheme.spacing.md))

                Text("Disabled (on)", variant = TextVariant.Small)

                Spacer(Modifier.height(RikkaTheme.spacing.xs))

                Toggle(
                    checked = toggle4,
                    onCheckedChange = { toggle4 = it },
                    enabled = false,
                )
            }

            Spacer(Modifier.height(RikkaTheme.spacing.xxxl))

            Separator()

            // ─── Footer ──────────────────────────────────────
            Spacer(Modifier.height(RikkaTheme.spacing.xl))

            Text(
                text = "Built with Compose Multiplatform",
                variant = TextVariant.Muted,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.xs))

            Text(
                text = "RikkaUI - Zero Material. Pure Kotlin.",
                variant = TextVariant.Small,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = RikkaTheme.colors.primary,
            )

            Spacer(Modifier.height(RikkaTheme.spacing.xxxl))
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    description: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(text = title, variant = TextVariant.H3)
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        Text(text = description, variant = TextVariant.Muted)
    }
}
