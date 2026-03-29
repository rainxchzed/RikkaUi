package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import zed.rainxch.rikkaui.foundation.RikkaTheme
import zed.rainxch.rikkaui.showcase.examples.ActivityFeedExample
import zed.rainxch.rikkaui.showcase.examples.ApiKeyManagerExample
import zed.rainxch.rikkaui.showcase.examples.FeedbackFormExample
import zed.rainxch.rikkaui.showcase.examples.FileExplorerExample
import zed.rainxch.rikkaui.showcase.examples.MusicPlayerExample
import zed.rainxch.rikkaui.showcase.examples.QuickNoteExample
import zed.rainxch.rikkaui.showcase.examples.SystemStatusExample
import zed.rainxch.rikkaui.showcase.examples.TaskBoardExample
import zed.rainxch.rikkaui.showcase.examples.UserProfileExample
import zed.rainxch.rikkaui.showcase.examples.WeatherDashboardExample
import zed.rainxch.rikkaui.utils.WindowSizeClass

@Composable
fun ExamplesGrid(sizeClass: WindowSizeClass) {
    val gap = RikkaTheme.spacing.md

    when (sizeClass) {
        WindowSizeClass.Compact -> CompactGrid(gap)
        WindowSizeClass.Medium -> MediumGrid(gap)
        WindowSizeClass.Expanded -> ExpandedGrid(gap)
    }
}

@Composable
private fun CompactGrid(gap: Dp) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(gap),
    ) {
        MusicPlayerExample()
        TaskBoardExample()
        UserProfileExample()
        WeatherDashboardExample()
        FileExplorerExample()
        ApiKeyManagerExample()
        QuickNoteExample()
        FeedbackFormExample()
        SystemStatusExample()
        ActivityFeedExample()
    }
}

@Composable
private fun MediumGrid(gap: Dp) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(gap),
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(gap),
        ) {
            MusicPlayerExample()
            WeatherDashboardExample()
            QuickNoteExample()
            SystemStatusExample()
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(gap),
        ) {
            TaskBoardExample()
            FileExplorerExample()
            UserProfileExample()
            ApiKeyManagerExample()
            FeedbackFormExample()
            ActivityFeedExample()
        }
    }
}

@Composable
private fun ExpandedGrid(gap: Dp) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(gap),
    ) {
        Column(
            modifier = Modifier.weight(1.2f),
            verticalArrangement = Arrangement.spacedBy(gap),
        ) {
            MusicPlayerExample()
            WeatherDashboardExample()
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(gap),
        ) {
            TaskBoardExample()
            FileExplorerExample()
            QuickNoteExample()
            SystemStatusExample()
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(gap),
        ) {
            UserProfileExample()
            ApiKeyManagerExample()
            FeedbackFormExample()
            ActivityFeedExample()
        }
    }
}
