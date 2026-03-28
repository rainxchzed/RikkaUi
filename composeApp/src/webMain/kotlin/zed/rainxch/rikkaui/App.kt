package zed.rainxch.rikkaui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import rikkaui.composeapp.generated.resources.Res
import rikkaui.composeapp.generated.resources.inter_black
import rikkaui.composeapp.generated.resources.inter_bold
import rikkaui.composeapp.generated.resources.inter_light
import rikkaui.composeapp.generated.resources.inter_medium
import rikkaui.composeapp.generated.resources.inter_regular
import rikkaui.composeapp.generated.resources.inter_semi_bold
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.theme.rememberRikkaFontFamily
import zed.rainxch.rikkaui.components.theme.rikkaTypography
import zed.rainxch.rikkaui.showcase.ShowcaseApp
import zed.rainxch.rikkaui.theme.resolveAccent
import zed.rainxch.rikkaui.theme.resolvePalette
import zed.rainxch.rikkaui.theme.resolveStyle

/**
 * Application entry point.
 *
 * Holds top-level theme state (palette, accent, style, dark mode)
 * and wires the [RikkaTheme] around the [ShowcaseApp] layout.
 */
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        var isDark by remember { mutableStateOf(true) }
        var paletteName by remember { mutableStateOf("Zinc") }
        var accentName by remember { mutableStateOf("Default") }
        var styleName by remember { mutableStateOf("Default") }

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
        val style = resolveStyle(styleName)

        RikkaTheme(
            colors = colors,
            typography = rikkaTypography(fontFamily, scale = style.typeScale),
            shapes = style.shapes,
            spacing = style.spacing,
            motion = style.motion,
        ) {
            ShowcaseApp(
                isDark = isDark,
                onDarkChange = { isDark = it },
                paletteName = paletteName,
                onPaletteChange = { paletteName = it },
                accentName = accentName,
                onAccentChange = { accentName = it },
                styleName = styleName,
                onStyleChange = { styleName = it },
            )
        }
    }
}
