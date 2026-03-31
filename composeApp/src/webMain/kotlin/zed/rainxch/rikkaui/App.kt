package zed.rainxch.rikkaui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import zed.rainxch.rikkaui.components.ui.separator.Separator
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
import zed.rainxch.rikkaui.navigation.resolveInitialRoute
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
    val initialRoute = remember { resolveInitialRoute() }
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
                    initialRoute = initialRoute,
                )
            }
        }
    }
}

// ────────────────────────────────────────────────────────
// TopNavBar — responsive
// ────────────────────────────────────────────────────────

@Composable
private fun TopNavBar(
    navController: NavController,
    isDark: Boolean,
    onDarkChange: (Boolean) -> Unit,
) {
    val uriHandler = LocalUriHandler.current
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: ""
    var menuExpanded by remember { mutableStateOf(false) }

    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val isCompact = maxWidth < 640.dp
        val isMedium = maxWidth in 640.dp..899.dp

        Column {
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
                // ── Left: Logo + nav links (or just logo on compact) ──
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

                    if (!isCompact) {
                        Spacer(Modifier.width(RikkaTheme.spacing.md))

                        LazyRow {
                            items(NavEntry.getNavEntries()) { link ->
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
                    }
                }

                // ── Right: GitHub (expanded only) + dark toggle + hamburger (compact) ──
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement =
                        Arrangement.spacedBy(RikkaTheme.spacing.xs),
                ) {
                    if (!isCompact && !isMedium) {
                        Button(
                            text = stringResource(Res.string.github),
                            onClick = {
                                uriHandler.openUri(
                                    "https://github.com/rainxchzed/RikkaUi",
                                )
                            },
                            variant = ButtonVariant.Outline,
                        )
                    }

                    Button(
                        onClick = { onDarkChange(!isDark) },
                        variant = ButtonVariant.Link,
                        size = ButtonSize.Icon,
                    ) {
                        Icon(
                            imageVector =
                                if (isDark) RikkaIcons.Sun else RikkaIcons.Moon,
                            contentDescription =
                                stringResource(Res.string.toggle_dark_mode),
                            size = IconSize.Sm,
                        )
                    }

                    if (isCompact) {
                        Button(
                            onClick = { menuExpanded = !menuExpanded },
                            variant = ButtonVariant.Ghost,
                            size = ButtonSize.Icon,
                        ) {
                            Icon(
                                imageVector =
                                    if (menuExpanded) {
                                        RikkaIcons.X
                                    } else {
                                        RikkaIcons.Menu
                                    },
                                contentDescription = "Menu",
                                size = IconSize.Sm,
                            )
                        }
                    }
                }
            }

            // ── Mobile menu drawer ──
            if (isCompact && menuExpanded) {
                MobileMenu(
                    navController = navController,
                    currentRoute = currentRoute,
                    onNavigate = { menuExpanded = false },
                )
            }
        }
    }
}

// ────────────────────────────────────────────────────────
// Mobile Menu
// ────────────────────────────────────────────────────────

@Composable
private fun MobileMenu(
    navController: NavController,
    currentRoute: String,
    onNavigate: () -> Unit,
) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(RikkaTheme.colors.card)
                .padding(
                    horizontal = RikkaTheme.spacing.lg,
                    vertical = RikkaTheme.spacing.sm,
                ),
        verticalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.xs),
    ) {
        NavEntry.getNavEntries().forEach { link ->
            val isActive =
                currentRoute == link.matchPrefix ||
                    currentRoute.startsWith(link.matchPrefix + "/")

            MobileMenuItem(
                label = stringResource(link.label),
                isActive = isActive,
                onClick = {
                    navController.navigate(link.route) {
                        popUpTo<HomeRoute> { inclusive = false }
                        launchSingleTop = true
                    }
                    onNavigate()
                },
            )
        }

        Separator()

        MobileMenuItem(
            label = stringResource(Res.string.github),
            isActive = false,
            onClick = {
                uriHandler.openUri("https://github.com/rainxchzed/RikkaUi")
                onNavigate()
            },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.xs))
    }
}

@Composable
private fun MobileMenuItem(
    label: String,
    isActive: Boolean,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val bg =
        when {
            isActive -> RikkaTheme.colors.muted
            isHovered -> RikkaTheme.colors.muted.copy(alpha = 0.5f)
            else -> RikkaTheme.colors.card
        }

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(RikkaTheme.shapes.md)
                .background(bg, RikkaTheme.shapes.md)
                .hoverable(interactionSource)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick,
                ).padding(
                    horizontal = RikkaTheme.spacing.md,
                    vertical = RikkaTheme.spacing.sm,
                ),
    ) {
        Text(
            text = label,
            variant =
                if (isActive) TextVariant.Large else TextVariant.P,
            color =
                if (isActive) {
                    RikkaTheme.colors.foreground
                } else {
                    RikkaTheme.colors.mutedForeground
                },
        )
    }
}
