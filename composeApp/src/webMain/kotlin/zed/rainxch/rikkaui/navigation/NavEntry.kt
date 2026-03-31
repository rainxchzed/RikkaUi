package zed.rainxch.rikkaui.navigation

import org.jetbrains.compose.resources.StringResource
import rikkaui.composeapp.generated.resources.Res
import rikkaui.composeapp.generated.resources.nav_create
import rikkaui.composeapp.generated.resources.nav_docs
import rikkaui.composeapp.generated.resources.nav_home
import zed.rainxch.rikkaui.navigation.AppNavGraph.CreatorRoute
import zed.rainxch.rikkaui.navigation.AppNavGraph.DocsRoute
import zed.rainxch.rikkaui.navigation.AppNavGraph.HomeRoute

data class NavEntry(
    val label: StringResource,
    val route: Any,
    val matchPrefix: String,
) {
    companion object {
        fun getNavEntries(): List<NavEntry> =
            listOf(
                NavEntry(
                    label = Res.string.nav_home,
                    route = HomeRoute,
                    matchPrefix = "home",
                ),
                NavEntry(
                    label = Res.string.nav_docs,
                    route = DocsRoute,
                    matchPrefix = "docs",
                ),
                NavEntry(
                    label = Res.string.nav_create,
                    route = CreatorRoute,
                    matchPrefix = "create",
                ),
            )
    }
}
