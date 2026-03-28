package zed.rainxch.rikkaui.shell

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import zed.rainxch.rikkaui.navigation.AppRoute

/**
 * Persistent top navigation bar shown across all pages.
 *
 * Contains the logo/app name, page navigation links,
 * and the dark/light mode toggle.
 */
@Composable
fun TopNavBar(
    currentRoute: AppRoute,
    onNavigate: (AppRoute) -> Unit,
    isDark: Boolean,
    onDarkChange: (Boolean) -> Unit,
) {
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

            AppRoute.entries.forEach { route ->
                Button(
                    text = route.label,
                    onClick = { onNavigate(route) },
                    variant =
                        if (currentRoute == route) {
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
