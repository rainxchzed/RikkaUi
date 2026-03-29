package zed.rainxch.rikkaui.components.model

import zed.rainxch.rikkaui.navigation.AppNavGraph.CreatorRoute
import zed.rainxch.rikkaui.navigation.AppNavGraph.DocsRoute
import zed.rainxch.rikkaui.navigation.AppNavGraph.HomeRoute

data class NavEntry(
    val label: String,
    val route: Any,
    val matchPrefix: String,
) {
    companion object {
        fun getNavEntries(): List<NavEntry> =
            listOf(
                NavEntry(
                    label = "Home",
                    route = HomeRoute,
                    matchPrefix = "home",
                ),
                NavEntry(
                    label = "Docs",
                    route = DocsRoute,
                    matchPrefix = "docs",
                ),
                NavEntry(
                    label = "Create",
                    route = CreatorRoute,
                    matchPrefix = "create",
                ),
            )
    }
}
