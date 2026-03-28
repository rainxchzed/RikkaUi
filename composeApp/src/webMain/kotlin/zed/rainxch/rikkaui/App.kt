package zed.rainxch.rikkaui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeViewport
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import rikkaui.composeapp.generated.resources.Res
import rikkaui.composeapp.generated.resources.inter_black
import rikkaui.composeapp.generated.resources.inter_bold
import rikkaui.composeapp.generated.resources.inter_light
import rikkaui.composeapp.generated.resources.inter_medium
import rikkaui.composeapp.generated.resources.inter_regular
import rikkaui.composeapp.generated.resources.inter_semi_bold
import zed.rainxch.rikkaui.components.theme.RikkaAccentPreset
import zed.rainxch.rikkaui.components.theme.RikkaPalette
import zed.rainxch.rikkaui.components.theme.RikkaStylePreset
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.theme.rememberRikkaFontFamily
import zed.rainxch.rikkaui.components.theme.rikkaTypography
import zed.rainxch.rikkaui.creator.DesignSystemCreatorPage
import zed.rainxch.rikkaui.docs.DocsPage
import zed.rainxch.rikkaui.navigation.ComponentDetailRoute
import zed.rainxch.rikkaui.navigation.ComponentsRoute
import zed.rainxch.rikkaui.navigation.CreatorRoute
import zed.rainxch.rikkaui.navigation.DocsRoute
import zed.rainxch.rikkaui.navigation.HomeRoute
import zed.rainxch.rikkaui.shell.TopNavBar
import zed.rainxch.rikkaui.showcase.ShowcaseApp

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        App()
    }
}

@Composable
private fun App() {
    var isDark by remember { mutableStateOf(true) }
    var palette by remember { mutableStateOf(RikkaPalette.Zinc) }
    var accent by remember { mutableStateOf(RikkaAccentPreset.Default) }
    var stylePreset by remember {
        mutableStateOf(RikkaStylePreset.Default)
    }

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
        palette = palette,
        accent = accent,
        isDark = isDark,
        preset = stylePreset,
        typography =
            rikkaTypography(
                fontFamily = fontFamily,
                scale = stylePreset.typeScale,
            ),
    ) {
        val navController = rememberNavController()

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(RikkaTheme.colors.background),
        ) {
            TopNavBar(
                navController = navController,
                isDark = isDark,
                onDarkChange = { isDark = it },
            )

            NavHost(
                navController = navController,
                startDestination = HomeRoute,
                modifier = Modifier.weight(1f),
            ) {
                composable<HomeRoute> {
                    ShowcaseApp(
                        isDark = isDark,
                        onDarkChange = { isDark = it },
                        palette = palette,
                        onPaletteChange = { palette = it },
                        accent = accent,
                        onAccentChange = { accent = it },
                        stylePreset = stylePreset,
                        onStyleChange = { stylePreset = it },
                        onNavigateToCreator = {
                            navController.navigate(DocsRoute)
                        },
                    )
                }

                composable<CreatorRoute> {
                    DesignSystemCreatorPage()
                }

                composable<DocsRoute> {
                    DocsPage(navController = navController)
                }

                composable<ComponentsRoute> {
                    DocsPage(
                        navController = navController,
                        initialComponentId = null,
                    )
                }

                composable<ComponentDetailRoute> { backStackEntry ->
                    val route: ComponentDetailRoute =
                        backStackEntry.toRoute()
                    DocsPage(
                        navController = navController,
                        initialComponentId = route.componentId,
                    )
                }
            }
        }
    }
}
