package zed.rainxch.rikkaui.navigation

import kotlinx.browser.window

/**
 * Reads the browser hash on startup and resolves the initial route
 * for the NavHost. This must run BEFORE NavHost composes so that
 * [bindToBrowserNavigation] doesn't overwrite the hash with the
 * default start destination.
 */
fun resolveInitialRoute(): Any {
    val hash = window.location.hash.removePrefix("#").trimStart('/')

    return when {
        hash.isEmpty() || hash == RoutePaths.HOME -> AppNavGraph.HomeRoute
        hash == RoutePaths.CREATE -> AppNavGraph.CreatorRoute
        hash == RoutePaths.DOCS -> AppNavGraph.DocsRoute
        hash.startsWith(RoutePaths.DOCS_GUIDE) -> {
            val guideId = extractParam(hash, RoutePaths.GUIDE_ID_PARAM)
            if (guideId != null) {
                AppNavGraph.DocsGuideRoute(guideId)
            } else {
                AppNavGraph.DocsRoute
            }
        }
        hash.startsWith(RoutePaths.DOCS_COMPONENTS) -> {
            val componentId = extractParam(hash, RoutePaths.COMPONENT_ID_PARAM)
            if (componentId != null) {
                AppNavGraph.ComponentDetailRoute(componentId)
            } else {
                AppNavGraph.DocsRoute
            }
        }
        else -> AppNavGraph.HomeRoute
    }
}

private fun extractParam(hash: String, paramName: String): String? {
    // Handles both "route?param=value" and "route/param=value" formats
    val query = hash.substringAfter("?", "").ifEmpty {
        hash.substringAfter("/", "")
    }
    return query
        .split("&")
        .mapNotNull { part ->
            val (key, value) = part.split("=", limit = 2).let {
                if (it.size == 2) it[0] to it[1] else return@mapNotNull null
            }
            if (key == paramName) value else null
        }
        .firstOrNull()
}
