package zed.rainxch.rikkaui.showcase

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ShowcaseRoute(
    isDark: Boolean,
    onNavigateToCreator: () -> Unit,
    onNavigateToComponents: () -> Unit,
    viewModel: ShowcaseViewModel = viewModel { ShowcaseViewModel() },
) {
    val state by viewModel.state.collectAsState()

    ShowcaseScreen(
        state = state,
        isDark = isDark,
        onAction = viewModel::onAction,
        onNavigateToCreator = onNavigateToCreator,
        onNavigateToComponents = onNavigateToComponents,
    )
}
