package zed.rainxch.rikkaui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
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
            "${RoutePaths.DOCS_GUIDE}/${RoutePaths.GUIDE_ID_PARAM}=$pageId"
        } else {
            "${RoutePaths.DOCS_COMPONENTS}/${RoutePaths.COMPONENT_ID_PARAM}=$pageId"
        }
    window.history.replaceState(null, "", "#$path")
}

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
