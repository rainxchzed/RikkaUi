package zed.rainxch.rikkaui.showcase

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import zed.rainxch.rikkaui.app.LocalAppState

@Composable
fun ShowcaseRoute(
    onNavigateToCreator: () -> Unit,
    onNavigateToComponents: () -> Unit,
    viewModel: ShowcaseViewModel = viewModel { ShowcaseViewModel() },
) {
    val state by viewModel.state.collectAsState()
    val appState = LocalAppState.current

    ShowcaseScreen(
        state = state,
        isDark = appState.isDark,
        onAction = viewModel::onAction,
        onNavigateToCreator = onNavigateToCreator,
        onNavigateToComponents = onNavigateToComponents,
    )
}
