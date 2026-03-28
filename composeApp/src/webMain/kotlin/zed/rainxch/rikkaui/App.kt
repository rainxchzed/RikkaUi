package zed.rainxch.rikkaui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeViewport
import rikkaui.composeapp.generated.resources.*
import zed.rainxch.rikkaui.components.theme.RikkaAccent
import zed.rainxch.rikkaui.components.theme.RikkaAccentDark
import zed.rainxch.rikkaui.components.theme.RikkaColors
import zed.rainxch.rikkaui.components.theme.RikkaPalettes
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.theme.rememberRikkaFontFamily
import zed.rainxch.rikkaui.components.theme.rikkaTypography
import zed.rainxch.rikkaui.components.theme.withAccent
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.showcase.FooterSection
import zed.rainxch.rikkaui.showcase.HeroSection
import zed.rainxch.rikkaui.showcase.ThemeSection
import zed.rainxch.rikkaui.showcase.examples.AuthSettingsExample
import zed.rainxch.rikkaui.showcase.examples.ChatInputExample
import zed.rainxch.rikkaui.showcase.examples.ComputeEnvironmentExample
import zed.rainxch.rikkaui.showcase.examples.NavigationExample
import zed.rainxch.rikkaui.showcase.examples.NotificationsExample
import zed.rainxch.rikkaui.showcase.examples.PaymentFormExample
import zed.rainxch.rikkaui.showcase.examples.PriceRangeExample
import zed.rainxch.rikkaui.showcase.examples.SearchExample
import zed.rainxch.rikkaui.showcase.examples.SurveyExample
import zed.rainxch.rikkaui.showcase.examples.TeamMembersExample

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        var isDark by remember { mutableStateOf(true) }
        var paletteName by remember { mutableStateOf("Zinc") }
        var accentName by remember { mutableStateOf("Default") }

        val baseColors = resolvePalette(paletteName, isDark)
        val colors = resolveAccent(baseColors, accentName, isDark)
        val fontFamily =
            rememberRikkaFontFamily(
                light = Res.font.inter_light,
                regular = Res.font.inter_regular,
                medium = Res.font.inter_medium,
                semiBold = Res.font.inter_semi_bold,
                bold = Res.font.inter_bold,
                extraBold = Res.font.inter_black,
            )

        RikkaTheme(
            colors = colors,
            typography = rikkaTypography(fontFamily),
        ) {
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
                    .widthIn(max = 1200.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = RikkaTheme.spacing.lg),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HeroSection()

            Separator()

            ThemeSection(
                isDark = isDark,
                onDarkChange = onDarkChange,
                paletteName = paletteName,
                onPaletteChange = onPaletteChange,
                accentName = accentName,
                onAccentChange = onAccentChange,
            )

            Spacer(Modifier.height(RikkaTheme.spacing.xxxl))

            Separator()

            Spacer(Modifier.height(RikkaTheme.spacing.xl))

            Text(
                text = "Components in Action",
                variant = TextVariant.H2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.xs))

            Text(
                text = "Real-world examples built with RikkaUI components.",
                variant = TextVariant.Muted,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.xxl))

            ExamplesGrid()

            Spacer(Modifier.height(RikkaTheme.spacing.xxxl))

            FooterSection()
        }
    }
}

@Composable
private fun ExamplesGrid() {
    val gap = RikkaTheme.spacing.md

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(gap),
    ) {
        // ── Column 1: Left (wider) ─────────────────────
        Column(
            modifier = Modifier.weight(1.2f),
            verticalArrangement = Arrangement.spacedBy(gap),
        ) {
            PaymentFormExample()
            PriceRangeExample()
        }

        // ── Column 2: Center ───────────────────────────
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(gap),
        ) {
            TeamMembersExample()
            NotificationsExample()
            ChatInputExample()
            SearchExample()
        }

        // ── Column 3: Right ────────────────────────────
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(gap),
        ) {
            AuthSettingsExample()
            ComputeEnvironmentExample()
            SurveyExample()
            NavigationExample()
        }
    }
}

// ─── Theme Resolution ───────────────────────────────────────

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

fun accentPreviewColor(name: String): Color =
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
