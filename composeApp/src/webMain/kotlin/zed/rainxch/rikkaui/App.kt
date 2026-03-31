package zed.rainxch.rikkaui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeViewport
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.ExperimentalBrowserHistoryApi
import androidx.navigation.NavController
import androidx.navigation.bindToBrowserNavigation
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.stringResource
import rikkaui.composeapp.generated.resources.Res
import rikkaui.composeapp.generated.resources.app_name
import rikkaui.composeapp.generated.resources.github
import rikkaui.composeapp.generated.resources.toggle_dark_mode
import zed.rainxch.rikkaui.app.AppAction
import zed.rainxch.rikkaui.app.AppViewModel
import zed.rainxch.rikkaui.app.LocalAppState
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.IconSize
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.scaffold.Scaffold
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.toast.LocalToastHostState
import zed.rainxch.rikkaui.components.ui.toast.ToastHost
import zed.rainxch.rikkaui.components.ui.toast.rememberToastHostState
import zed.rainxch.rikkaui.foundation.RikkaPalette
import zed.rainxch.rikkaui.foundation.RikkaStylePreset
import zed.rainxch.rikkaui.foundation.RikkaTheme
import zed.rainxch.rikkaui.foundation.rikkaTypography
import zed.rainxch.rikkaui.navigation.AppNavGraph.HomeRoute
import zed.rainxch.rikkaui.navigation.AppNavigation
import zed.rainxch.rikkaui.navigation.NavEntry
import zed.rainxch.rikkaui.theme.ThemeStore
import zed.rainxch.rikkaui.utils.ThemeUtils

@OptIn(ExperimentalComposeUiApi::class, ExperimentalBrowserHistoryApi::class)
fun main() =
    ComposeViewport {
        App(
            onNavHostReady = {
                it.bindToBrowserNavigation()
            },
        )
    }

@Composable
private fun App(onNavHostReady: suspend (NavController) -> Unit = {}) {
    val navController = rememberNavController()
    val viewModel: AppViewModel =
        viewModel(
            factory =
                viewModelFactory {
                    initializer {
                        AppViewModel(preferences = ThemeStore())
                    }
                },
        )

    val appState by viewModel.state.collectAsState()

    LaunchedEffect(navController) {
        onNavHostReady(navController)
    }

    val toastHostState = rememberToastHostState()

    RikkaTheme(
        palette = RikkaPalette.Zinc,
        isDark = appState.isDark,
        preset = RikkaStylePreset.Default,
        typography =
            rikkaTypography(
                fontFamily = ThemeUtils.getFontFamily(),
            ),
    ) {
        CompositionLocalProvider(
            LocalToastHostState provides toastHostState,
            LocalAppState provides appState,
        ) {
            Scaffold(
                topBar = {
                    TopNavBar(
                        navController = navController,
                        isDark = appState.isDark,
                        onDarkChange = {
                            viewModel.onAction(AppAction.SetDarkMode(it))
                        },
                    )
                },
                toastHost = {
                    ToastHost(hostState = toastHostState)
                },
                containerColor = RikkaTheme.colors.background,
            ) { _ ->
                AppNavigation(
                    navController = navController,
                )
            }
        }
    }
}

@Composable
private fun TopNavBar(
    navController: NavController,
    isDark: Boolean,
    onDarkChange: (Boolean) -> Unit,
) {
    val uriHandler = LocalUriHandler.current

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: ""

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = RikkaTheme.spacing.lg,
                    vertical = RikkaTheme.spacing.sm,
                ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.sm),
        ) {
            Text(
                text = stringResource(Res.string.app_name),
                variant = TextVariant.Large,
                color = RikkaTheme.colors.foreground,
            )

            Spacer(Modifier.width(RikkaTheme.spacing.md))

            NavEntry.getNavEntries().forEach { link ->
                val isActive =
                    currentRoute == link.matchPrefix ||
                        currentRoute.startsWith(
                            link.matchPrefix + "/",
                        )
                Button(
                    text = stringResource(link.label),
                    onClick = {
                        navController.navigate(link.route) {
                            popUpTo<HomeRoute> {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    },
                    variant =
                        if (isActive) {
                            ButtonVariant.Secondary
                        } else {
                            ButtonVariant.Ghost
                        },
                    size = ButtonSize.Sm,
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Button(
                text = stringResource(Res.string.github),
                onClick = {
                    uriHandler.openUri("https://github.com/rainxchzed/RikkaUi")
                },
                variant = ButtonVariant.Outline,
            )

            Button(
                onClick = {
                    onDarkChange(!isDark)
                },
                variant = ButtonVariant.Link,
                size = ButtonSize.Icon,
            ) {
                Icon(
                    imageVector =
                        if (isDark) {
                            RikkaIcons.Sun
                        } else {
                            RikkaIcons.Moon
                        },
                    contentDescription = stringResource(Res.string.toggle_dark_mode),
                    size = IconSize.Sm,
                )
            }
        }
    }
}
