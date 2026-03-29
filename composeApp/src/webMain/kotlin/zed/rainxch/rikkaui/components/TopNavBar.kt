package zed.rainxch.rikkaui.components

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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.jetbrains.compose.resources.stringResource
import rikkaui.composeapp.generated.resources.Res
import rikkaui.composeapp.generated.resources.app_name
import rikkaui.composeapp.generated.resources.github
import rikkaui.composeapp.generated.resources.toggle_dark_mode
import zed.rainxch.rikkaui.components.model.NavEntry
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.IconSize
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.navigation.AppNavGraph.CreatorRoute
import zed.rainxch.rikkaui.navigation.AppNavGraph.DocsRoute
import zed.rainxch.rikkaui.navigation.AppNavGraph.HomeRoute

@Composable
fun TopNavBar(
    navController: NavController,
    isDark: Boolean,
    onDarkChange: (Boolean) -> Unit,
) {
    val uriHandler = LocalUriHandler.current

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: ""

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

            NavEntry.getNavEntries().forEach { link ->
                val isActive =
                    currentRoute == link.matchPrefix ||
                        currentRoute.startsWith(
                            link.matchPrefix + "/",
                        )
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

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Button(
                text = stringResource(Res.string.github),
                onClick = {
                    uriHandler.openUri("https://github.com/rainxchzed/RikkaUi")
                },
                variant = ButtonVariant.Outline,
            )

            Button(
                onClick = {
                    onDarkChange(!isDark)
                },
                variant = ButtonVariant.Link,
                size = ButtonSize.Icon,
            ) {
                Icon(
                    imageVector =
                        if (isDark) {
                            RikkaIcons.Sun
                        } else {
                            RikkaIcons.Moon
                        },
                    contentDescription = stringResource(Res.string.toggle_dark_mode),
                    size = IconSize.Sm,
                )
            }
        }
    }
}
