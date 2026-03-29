package zed.rainxch.rikkaui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeViewport
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.ExperimentalBrowserHistoryApi
import androidx.navigation.NavController
import androidx.navigation.bindToBrowserNavigation
import androidx.navigation.compose.rememberNavController
import zed.rainxch.rikkaui.components.TopNavBar
import zed.rainxch.rikkaui.components.theme.RikkaPalette
import zed.rainxch.rikkaui.components.theme.RikkaStylePreset
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.theme.rikkaTypography
import zed.rainxch.rikkaui.navigation.AppNavigation
import zed.rainxch.rikkaui.theme.ThemeAction
import zed.rainxch.rikkaui.theme.ThemeViewModel
import zed.rainxch.rikkaui.utils.ThemeUtils

@OptIn(ExperimentalComposeUiApi::class, ExperimentalBrowserHistoryApi::class)
fun main() {
    ComposeViewport {
        App(
            onNavHostReady = { it.bindToBrowserNavigation() },
        )
    }
}

@Composable
private fun App(onNavHostReady: suspend (NavController) -> Unit = {}) {
    val navController = rememberNavController()
    val viewModel: ThemeViewModel =
        viewModel(
            factory =
                viewModelFactory {
                    initializer {
                        ThemeViewModel()
                    }
                },
        )

    val themeState by viewModel.state.collectAsState()

    LaunchedEffect(navController) {
        onNavHostReady(navController)
    }

    RikkaTheme(
        palette = RikkaPalette.Zinc,
        isDark = themeState.isDark,
        preset = RikkaStylePreset.Default,
        typography =
            rikkaTypography(
                fontFamily = ThemeUtils.getFontFamily(),
            ),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(RikkaTheme.colors.background),
        ) {
            TopNavBar(
                navController = navController,
                isDark = themeState.isDark,
                onDarkChange = {
                    viewModel.onAction(ThemeAction.SetDarkMode(it))
                },
            )

            AppNavigation(
                navController = navController,
                themeState = themeState,
            )
        }
    }
}
