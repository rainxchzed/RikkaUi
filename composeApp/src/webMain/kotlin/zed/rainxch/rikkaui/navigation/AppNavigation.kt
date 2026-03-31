package zed.rainxch.rikkaui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.browser.window
import zed.rainxch.rikkaui.creator.presentation.CreatorRoute
import zed.rainxch.rikkaui.docs.catalog.guidePageIds
import zed.rainxch.rikkaui.docs.presentation.DocsRoute
import zed.rainxch.rikkaui.navigation.AppNavGraph.ComponentDetailRoute
import zed.rainxch.rikkaui.navigation.AppNavGraph.CreatorRoute
import zed.rainxch.rikkaui.navigation.AppNavGraph.DocsGuideRoute
import zed.rainxch.rikkaui.navigation.AppNavGraph.DocsRoute
import zed.rainxch.rikkaui.navigation.AppNavGraph.HomeRoute
import zed.rainxch.rikkaui.showcase.ShowcaseRoute

private fun updateDocsHash(pageId: String) {
    val path =
        if (pageId in guidePageIds) {
            "${RoutePaths.DOCS_GUIDE}?${RoutePaths.GUIDE_ID_PARAM}=$pageId"
        } else {
            "${RoutePaths.DOCS_COMPONENTS}?${RoutePaths.COMPONENT_ID_PARAM}=$pageId"
        }
    window.history.replaceState(null, "", "#$path")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    initialRoute: Any = HomeRoute,
) {
    // Navigate to the deep-linked route on first composition.
    // startDestination is always HomeRoute (required for the graph root),
    // but if the URL hash pointed elsewhere we immediately navigate there.
    val hasNavigated = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (!hasNavigated.value && initialRoute != HomeRoute) {
            hasNavigated.value = true
            navController.navigate(initialRoute) {
                popUpTo<HomeRoute> { inclusive = true }
                launchSingleTop = true
            }
        }
    }

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
            CreatorRoute()
        }

        composable<DocsRoute> {
            DocsRoute(onPageSelected = ::updateDocsHash)
        }

        composable<DocsGuideRoute> { backStackEntry ->
            val route: DocsGuideRoute = backStackEntry.toRoute()
            DocsRoute(
                initialComponentId = route.guideId,
                onPageSelected = ::updateDocsHash,
            )
        }

        composable<ComponentDetailRoute> { backStackEntry ->
            val route: ComponentDetailRoute = backStackEntry.toRoute()
            DocsRoute(
                initialComponentId = route.componentId,
                onPageSelected = ::updateDocsHash,
            )
        }
    }
}
