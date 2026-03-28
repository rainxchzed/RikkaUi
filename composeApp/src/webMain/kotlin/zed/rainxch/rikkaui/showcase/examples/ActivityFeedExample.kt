package zed.rainxch.rikkaui.showcase.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.avatar.Avatar
import zed.rainxch.rikkaui.components.ui.avatar.AvatarSize
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.card.CardContent
import zed.rainxch.rikkaui.components.ui.card.CardHeader
import zed.rainxch.rikkaui.components.ui.pagination.Pagination
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

/**
 * Recent activity feed with avatars, action descriptions, and pagination.
 *
 * Demonstrates [Card], [CardHeader], [CardContent], [Avatar],
 * [Pagination], [Separator], and [Text] composed together
 * in a team activity timeline layout.
 */
@Composable
fun ActivityFeedExample() {
    var currentPage by remember { mutableStateOf(1) }

    Card {
        // ─── Header ───────────────────────────────────────
        CardHeader {
            Text(text = "Recent Activity", variant = TextVariant.H4)
        }

        // ─── Activity items ───────────────────────────────
        CardContent {
            ActivityItem(
                initials = "AL",
                action = "Alice updated App.kt",
                time = "2 min ago",
            )
            Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))
            ActivityItem(
                initials = "BM",
                action = "Bob merged PR #42",
                time = "15 min ago",
            )
            Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))
            ActivityItem(
                initials = "CS",
                action = "Carol added a comment",
                time = "1 hour ago",
            )
            Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))
            ActivityItem(
                initials = "DK",
                action = "Dave deployed v2.1",
                time = "3 hours ago",
            )
        }

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))
        Separator()
        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        // ─── Pagination ───────────────────────────────────
        Pagination(
            currentPage = currentPage,
            totalPages = 4,
            onPageChange = { currentPage = it },
        )
    }
}

@Composable
private fun ActivityItem(
    initials: String,
    action: String,
    time: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Avatar(fallback = initials, size = AvatarSize.Sm)
        Column(
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
        ) {
            Text(text = action, variant = TextVariant.Small)
            Text(text = time, variant = TextVariant.Muted)
        }
    }
}
