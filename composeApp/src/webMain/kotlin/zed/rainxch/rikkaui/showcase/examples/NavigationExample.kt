package zed.rainxch.rikkaui.showcase.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.breadcrumb.Breadcrumb
import zed.rainxch.rikkaui.components.ui.breadcrumb.BreadcrumbItem
import zed.rainxch.rikkaui.components.ui.breadcrumb.BreadcrumbSeparator
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.pagination.Pagination
import zed.rainxch.rikkaui.components.ui.progress.Progress
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.spinner.Spinner
import zed.rainxch.rikkaui.components.ui.spinner.SpinnerSize
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.toggle.Toggle
import zed.rainxch.rikkaui.components.ui.toggle.ToggleSize

/**
 * Navigation elements showcase card.
 *
 * Demonstrates [Pagination], [Breadcrumb], [Spinner], [Progress],
 * and [Toggle] composed together in a realistic navigation layout.
 */
@Composable
fun NavigationExample() {
    var currentPage by remember { mutableStateOf(2) }
    var copilotEnabled by remember { mutableStateOf(true) }

    Card {
        Pagination(
            currentPage = currentPage,
            totalPages = 5,
            onPageChange = { currentPage = it },
        )

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))
        Separator()
        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Breadcrumb {
                BreadcrumbItem("Home", onClick = { })
                BreadcrumbSeparator()
                BreadcrumbItem("Settings")
            }
        }

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))
        Separator()
        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spinner(size = SpinnerSize.Sm)
            Spacer(modifier = Modifier.width(RikkaTheme.spacing.sm))
            Text(
                text = "Processing your request",
                variant = TextVariant.Muted,
            )
        }

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        Progress(progress = 0.7f)

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement =
                Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
        ) {
            Toggle(
                checked = copilotEnabled,
                onCheckedChange = { copilotEnabled = it },
                size = ToggleSize.Sm,
                label = "Copilot toggle",
            )
            Text(
                text = "Copilot",
                variant = TextVariant.Small,
            )
        }
    }
}
