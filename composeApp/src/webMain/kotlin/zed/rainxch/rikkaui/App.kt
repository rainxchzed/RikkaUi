package zed.rainxch.rikkaui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeViewport
import rikkaui.composeapp.generated.resources.Res
import rikkaui.composeapp.generated.resources.inter_black
import rikkaui.composeapp.generated.resources.inter_bold
import rikkaui.composeapp.generated.resources.inter_light
import rikkaui.composeapp.generated.resources.inter_medium
import rikkaui.composeapp.generated.resources.inter_regular
import rikkaui.composeapp.generated.resources.inter_semi_bold
import zed.rainxch.rikkaui.components.theme.RikkaStylePreset
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.theme.rememberRikkaFontFamily
import zed.rainxch.rikkaui.components.theme.rikkaTypography
import zed.rainxch.rikkaui.creator.DesignSystemCreatorPage
import zed.rainxch.rikkaui.navigation.AppRoute
import zed.rainxch.rikkaui.navigation.BrowserHashSync
import zed.rainxch.rikkaui.navigation.navigateTo
import zed.rainxch.rikkaui.shell.TopNavBar
import zed.rainxch.rikkaui.showcase.ShowcaseApp
import zed.rainxch.rikkaui.theme.resolveAccent
import zed.rainxch.rikkaui.theme.resolvePalette

/**
 * Application entry point.
 *
 * Holds top-level theme state and routes between pages:
 * - [AppRoute.Home] — Showcase landing page
 * - [AppRoute.Creator] — "Create Your Design System" page
 */
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        // ─── Global state ────────────────────────────────
        var isDark by remember { mutableStateOf(true) }
        var paletteName by remember { mutableStateOf("Zinc") }
        var accentName by remember { mutableStateOf("Default") }
        var stylePreset by remember { mutableStateOf(RikkaStylePreset.Default) }

        val routeState = remember { mutableStateOf(AppRoute.Home) }
        BrowserHashSync(routeState)
        val currentRoute = routeState.value

        // ─── Theme resolution ────────────────────────────
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
            typography =
                rikkaTypography(
                    fontFamily,
                    scale = stylePreset.typeScale,
                ),
            spacing = stylePreset.spacing,
            shapes = stylePreset.shapes,
            motion = stylePreset.motion,
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(RikkaTheme.colors.background),
            ) {
                TopNavBar(
                    currentRoute = currentRoute,
                    onNavigate = { navigateTo(it) },
                    isDark = isDark,
                    onDarkChange = { isDark = it },
                )

                when (currentRoute) {
                    AppRoute.Home ->
                        ShowcaseApp(
                            isDark = isDark,
                            onDarkChange = { isDark = it },
                            paletteName = paletteName,
                            onPaletteChange = { paletteName = it },
                            accentName = accentName,
                            onAccentChange = { accentName = it },
                            stylePreset = stylePreset,
                            onStyleChange = { stylePreset = it },
                            onNavigateToCreator = {
                                navigateTo(AppRoute.Creator)
                            },
                        )

                    AppRoute.Creator ->
                        DesignSystemCreatorPage()
                }
            }
        }
    }
}
