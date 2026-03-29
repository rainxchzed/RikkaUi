package zed.rainxch.rikkaui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.toRoute
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.w3c.dom.events.Event

/**
 * Maps a browser hash fragment back to a navigation route object.
 *
 * Examples:
 * - "#/" or "" → HomeRoute
 * - "#/docs" → DocsRoute
 * - "#/create" → CreatorRoute
 * - "#/docs/components/button" → ComponentDetailRoute("button")
 */
private fun hashToRoute(hash: String): Any {
    val path = hash.removePrefix("#/").removePrefix("#").trimEnd('/')
    return when {
        path.isEmpty() || path == "home" -> HomeRoute
        path == "docs" -> DocsRoute
        path == "create" -> CreatorRoute
        path == "docs/components" -> ComponentsRoute
        path.startsWith("docs/components/") -> {
            val componentId = path.removePrefix("docs/components/")
            ComponentDetailRoute(componentId)
        }
        else -> HomeRoute
    }
}

/**
 * Maps a Compose Navigation destination route to a browser hash.
 */
private fun destinationToHash(route: String?): String {
    if (route == null || route == "home") return "#/"
    return "#/$route"
}

/**
 * Syncs the browser URL hash with Compose Navigation state.
 *
 * - When the user navigates in-app, the URL hash updates.
 * - When the user changes the URL hash (back/forward, manual edit), the app navigates.
 * - On first load, navigates to the route matching the current hash.
 */
@Composable
fun HashRouterEffect(navController: NavController) {
    val scope = rememberCoroutineScope()

    DisposableEffect(navController) {
        var isUpdatingHash = false
        var isNavigatingFromHash = false

        // Nav changes → update browser hash
        val listener =
            NavController.OnDestinationChangedListener { controller, _, _ ->
                if (isNavigatingFromHash) return@OnDestinationChangedListener
                isUpdatingHash = true
                // Use the current entry's route string which includes resolved args
                val entry = controller.currentBackStackEntry
                val id = entry?.destination?.route ?: "home"
                // For ComponentDetailRoute, extract the componentId from the entry
                val newHash =
                    if (
                        id.contains("{componentId}") || id == "docs/components/{componentId}"
                    ) {
                        val componentId = entry?.toRoute<ComponentDetailRoute>()?.componentId
                        if (componentId != null) "#/docs/components/$componentId" else "#/docs/components"
                    } else {
                        destinationToHash(id)
                    }
                if (window.location.hash != newHash) {
                    window.location.hash = newHash
                }
                isUpdatingHash = false
            }
        navController.addOnDestinationChangedListener(listener)

        // Browser hash changes → navigate in app
        val hashChangeCallback: (Event) -> Unit = { _ ->
            if (!isUpdatingHash) {
                isNavigatingFromHash = true
                scope.launch {
                    val route = hashToRoute(window.location.hash)
                    navController.navigate(route) {
                        popUpTo(HomeRoute) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                    isNavigatingFromHash = false
                }
            }
        }
        window.addEventListener("hashchange", hashChangeCallback)

        // On initial load, navigate to hash route if not home
        val initialHash = window.location.hash
        if (initialHash.isNotEmpty() && initialHash != "#/" && initialHash != "#") {
            isNavigatingFromHash = true
            scope.launch {
                val route = hashToRoute(initialHash)
                navController.navigate(route) {
                    popUpTo(HomeRoute) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
                isNavigatingFromHash = false
            }
        }

        onDispose {
            navController.removeOnDestinationChangedListener(listener)
            window.removeEventListener("hashchange", hashChangeCallback)
        }
    }
}
