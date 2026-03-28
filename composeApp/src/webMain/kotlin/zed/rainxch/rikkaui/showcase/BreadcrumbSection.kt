package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.breadcrumb.Breadcrumb
import zed.rainxch.rikkaui.components.ui.breadcrumb.BreadcrumbItemData
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.pagination.Pagination
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

@Composable
fun BreadcrumbSection() {
    SectionHeader(
        title = "Breadcrumb & Pagination",
        description = "Navigation aids for complex interfaces.",
    )

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.lg)) {
            // ─── Breadcrumb ──────────────────────────────────
            Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm)) {
                Text(text = "Breadcrumb", variant = TextVariant.Small)

                Breadcrumb(
                    items =
                        listOf(
                            BreadcrumbItemData("Home", onClick = {}),
                            BreadcrumbItemData("Products", onClick = {}),
                            BreadcrumbItemData("Electronics", onClick = {}),
                            BreadcrumbItemData("Headphones"),
                        ),
                )
            }

            // ─── Pagination ──────────────────────────────────
            Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm)) {
                Text(text = "Pagination", variant = TextVariant.Small)

                var currentPage by remember { mutableIntStateOf(1) }

                Pagination(
                    currentPage = currentPage,
                    totalPages = 10,
                    onPageChange = { currentPage = it },
                )
            }
        }
    }
}
