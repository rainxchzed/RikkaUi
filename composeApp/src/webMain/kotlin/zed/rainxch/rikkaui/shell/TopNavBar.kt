package zed.rainxch.rikkaui.shell

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.jetbrains.compose.resources.stringResource
import rikkaui.composeapp.generated.resources.Res
import rikkaui.composeapp.generated.resources.app_name
import rikkaui.composeapp.generated.resources.toggle_dark_mode
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.toggle.Toggle
import zed.rainxch.rikkaui.navigation.ComponentsRoute
import zed.rainxch.rikkaui.navigation.CreatorRoute
import zed.rainxch.rikkaui.navigation.HomeRoute

/**
 * Top-level navigation links for the nav bar.
 */
private data class NavLink(
    val label: String,
    val route: Any,
    val matchPrefix: String,
)

/**
 * Persistent top navigation bar shown across all pages.
 *
 * Uses NavController for type-safe navigation.
 */
@Composable
fun TopNavBar(
    navController: NavController,
    isDark: Boolean,
    onDarkChange: (Boolean) -> Unit,
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: ""

    val navLinks =
        listOf(
            NavLink("Home", HomeRoute, "home"),
            NavLink("Components", ComponentsRoute, "docs"),
            NavLink("Create", CreatorRoute, "create"),
        )

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
        // Left: logo + nav links
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

            Spacer(Modifier.width(RikkaTheme.spacing.md))

            navLinks.forEach { link ->
                val isActive =
                    currentRoute.contains(link.matchPrefix)
                Button(
                    text = link.label,
                    onClick = {
                        navController.navigate(link.route) {
                            popUpTo(HomeRoute) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
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

        // Right: dark mode toggle
        Toggle(
            checked = isDark,
            onCheckedChange = onDarkChange,
            label = stringResource(Res.string.toggle_dark_mode),
        )
    }
}
