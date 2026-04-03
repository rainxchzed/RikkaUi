@file:OptIn(ExperimentalWasmJsInterop::class)

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
import zed.rainxch.rikkaui.docs.presentation.ComponentsCatalogScreen
import zed.rainxch.rikkaui.docs.presentation.DocsRoute
import zed.rainxch.rikkaui.showcase.ShowcaseRoute
import zed.rainxch.rikkaui.whyrikka.WhyRikkaRoute

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
    initialRoute: AppNavGraph = AppNavGraph.HomeRoute,
) {
    val hasNavigated = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (!hasNavigated.value && initialRoute != AppNavGraph.HomeRoute) {
            hasNavigated.value = true
            navController.navigate(initialRoute) {
                popUpTo<AppNavGraph.HomeRoute> { inclusive = false }
                launchSingleTop = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = AppNavGraph.HomeRoute,
        modifier = Modifier.fillMaxSize(),
    ) {
        composable<AppNavGraph.HomeRoute> {
            ShowcaseRoute(
                onNavigateToCreator = {
                    navController.navigate(AppNavGraph.CreatorRoute)
                },
                onNavigateToComponents = {
                    navController.navigate(AppNavGraph.ComponentsCatalogRoute)
                },
            )
        }

        composable<AppNavGraph.ComponentsCatalogRoute> {
            ComponentsCatalogScreen(
                onComponentClick = { componentId ->
                    navController.navigate(
                        AppNavGraph.ComponentDetailRoute(componentId),
                    )
                },
            )
        }

        composable<AppNavGraph.WhyRikkaRoute> {
            WhyRikkaRoute()
        }

        composable<AppNavGraph.CreatorRoute> {
            CreatorRoute()
        }

        composable<AppNavGraph.DocsRoute> {
            DocsRoute(onPageSelected = ::updateDocsHash)
        }

        composable<AppNavGraph.DocsGuideRoute> { backStackEntry ->
            val route: AppNavGraph.DocsGuideRoute = backStackEntry.toRoute()
            DocsRoute(
                initialComponentId = route.guideId,
                onPageSelected = ::updateDocsHash,
            )
        }

        composable<AppNavGraph.ComponentDetailRoute> { backStackEntry ->
            val route: AppNavGraph.ComponentDetailRoute = backStackEntry.toRoute()
            DocsRoute(
                initialComponentId = route.componentId,
                onPageSelected = ::updateDocsHash,
            )
        }
    }
}
