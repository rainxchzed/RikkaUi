package zed.rainxch.rikkaui.creator.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CreatorRoute(viewModel: CreatorViewModel = viewModel { CreatorViewModel() }) {
    val state by viewModel.state.collectAsState()

    CreatorScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}
