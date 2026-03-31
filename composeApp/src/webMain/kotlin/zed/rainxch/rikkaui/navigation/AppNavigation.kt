package zed.rainxch.rikkaui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import zed.rainxch.rikkaui.creator.DesignSystemCreatorPage
import zed.rainxch.rikkaui.docs.presentation.DocsRoute
import zed.rainxch.rikkaui.navigation.AppNavGraph.ComponentDetailRoute
import zed.rainxch.rikkaui.navigation.AppNavGraph.CreatorRoute
import zed.rainxch.rikkaui.navigation.AppNavGraph.DocsGuideRoute
import zed.rainxch.rikkaui.navigation.AppNavGraph.DocsRoute
import zed.rainxch.rikkaui.navigation.AppNavGraph.HomeRoute
import zed.rainxch.rikkaui.showcase.ShowcaseRoute

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = Modifier.fillMaxSize(),
    ) {
        composable<HomeRoute> {
            ShowcaseRoute(
                onNavigateToCreator = {
                    navController.navigate(CreatorRoute)
                },
                onNavigateToComponents = {
                    navController.navigate(DocsRoute)
                },
            )
        }

        composable<CreatorRoute> {
            DesignSystemCreatorPage()
        }

        composable<DocsRoute> {
            DocsRoute(
                onNavigateToComponent = { id ->
                    navController.navigate(ComponentDetailRoute(id)) {
                        popUpTo<DocsRoute> { inclusive = true }
                    }
                },
                onNavigateToGuide = { guideId ->
                    navController.navigate(DocsGuideRoute(guideId)) {
                        popUpTo<DocsRoute> { inclusive = true }
                    }
                },
            )
        }

        composable<DocsGuideRoute> { backStackEntry ->
            val route: DocsGuideRoute = backStackEntry.toRoute()

            DocsRoute(
                initialComponentId = route.guideId,
                onNavigateToComponent = { id ->
                    navController.navigate(ComponentDetailRoute(id)) {
                        popUpTo<DocsGuideRoute> { inclusive = true }
                    }
                },
                onNavigateToGuide = { guideId ->
                    navController.navigate(DocsGuideRoute(guideId)) {
                        popUpTo<DocsGuideRoute> { inclusive = true }
                    }
                },
            )
        }

        composable<ComponentDetailRoute> { backStackEntry ->
            val route: ComponentDetailRoute = backStackEntry.toRoute()

            DocsRoute(
                initialComponentId = route.componentId,
                onNavigateToComponent = { id ->
                    navController.navigate(ComponentDetailRoute(id)) {
                        popUpTo<ComponentDetailRoute> { inclusive = true }
                    }
                },
                onNavigateToGuide = { guideId ->
                    navController.navigate(DocsGuideRoute(guideId)) {
                        popUpTo<ComponentDetailRoute> { inclusive = true }
                    }
                },
            )
        }
    }
}
