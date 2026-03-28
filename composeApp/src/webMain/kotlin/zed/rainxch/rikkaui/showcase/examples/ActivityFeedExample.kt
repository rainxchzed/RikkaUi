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
import org.jetbrains.compose.resources.stringResource
import rikkaui.composeapp.generated.resources.Res
import rikkaui.composeapp.generated.resources.alice_action
import rikkaui.composeapp.generated.resources.alice_time
import rikkaui.composeapp.generated.resources.bob_action
import rikkaui.composeapp.generated.resources.bob_time
import rikkaui.composeapp.generated.resources.carol_action
import rikkaui.composeapp.generated.resources.carol_time
import rikkaui.composeapp.generated.resources.dave_action
import rikkaui.composeapp.generated.resources.dave_time
import rikkaui.composeapp.generated.resources.recent_activity
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

@Composable
fun ActivityFeedExample() {
    var currentPage by remember { mutableStateOf(1) }

    Card {
        CardHeader {
            Text(text = stringResource(Res.string.recent_activity), variant = TextVariant.H4)
        }

        CardContent {
            ActivityItem(
                initials = "AL",
                action = stringResource(Res.string.alice_action),
                time = stringResource(Res.string.alice_time),
            )
            Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))
            ActivityItem(
                initials = "BM",
                action = stringResource(Res.string.bob_action),
                time = stringResource(Res.string.bob_time),
            )
            Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))
            ActivityItem(
                initials = "CS",
                action = stringResource(Res.string.carol_action),
                time = stringResource(Res.string.carol_time),
            )
            Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))
            ActivityItem(
                initials = "DK",
                action = stringResource(Res.string.dave_action),
                time = stringResource(Res.string.dave_time),
            )
        }

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))
        Separator()
        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        Pagination(
            currentPage = currentPage,
            totalPages = 4,
            onPageChange = { currentPage = it },
        )
    }
}

@Composable
private fun ActivityItem(initials: String, action: String, time: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Avatar(fallback = initials, size = AvatarSize.Sm)
        Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs)) {
            Text(text = action, variant = TextVariant.Small)
            Text(text = time, variant = TextVariant.Muted)
        }
    }
}
