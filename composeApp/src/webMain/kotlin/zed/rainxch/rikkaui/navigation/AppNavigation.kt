package zed.rainxch.rikkaui.navigation

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import zed.rainxch.rikkaui.creator.DesignSystemCreatorPage
import zed.rainxch.rikkaui.docs.DocsPage
import zed.rainxch.rikkaui.navigation.AppNavGraph.ComponentDetailRoute
import zed.rainxch.rikkaui.navigation.AppNavGraph.ComponentsRoute
import zed.rainxch.rikkaui.navigation.AppNavGraph.CreatorRoute
import zed.rainxch.rikkaui.navigation.AppNavGraph.DocsRoute
import zed.rainxch.rikkaui.navigation.AppNavGraph.HomeRoute
import zed.rainxch.rikkaui.showcase.ShowcaseApp
import zed.rainxch.rikkaui.theme.ThemeState

@Composable
fun ColumnScope.AppNavigation(
    navController: NavHostController,
    themeState: ThemeState,
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = Modifier.weight(1f),
    ) {
        composable<HomeRoute> {
            ShowcaseApp(
                isDark = themeState.isDark,
                onNavigateToCreator = {
                    navController.navigate(CreatorRoute)
                },
                onNavigateToComponents = {
                    navController.navigate(ComponentsRoute)
                },
            )
        }

        composable<CreatorRoute> {
            DesignSystemCreatorPage()
        }

        composable<DocsRoute> {
            DocsPage()
        }

        composable<ComponentsRoute> {
            DocsPage(
                initialComponentId = null,
            )
        }

        composable<ComponentDetailRoute> { backStackEntry ->
            val route: ComponentDetailRoute = backStackEntry.toRoute()

            DocsPage(
                initialComponentId = route.componentId,
            )
        }
    }
}
