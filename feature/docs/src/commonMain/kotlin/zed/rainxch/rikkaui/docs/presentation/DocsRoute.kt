package zed.rainxch.rikkaui.docs.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import zed.rainxch.rikkaui.docs.catalog.ComponentRegistry

@Composable
fun DocsRoute(
    initialComponentId: String? = null,
) {
    val viewModel: DocsViewModel =
        viewModel(
            factory =
                viewModelFactory {
                    initializer {
                        DocsViewModel(initialComponentId)
                    }
                },
        )

    val state by viewModel.state.collectAsState()

    LaunchedEffect(initialComponentId) {
        if (initialComponentId != null) {
            viewModel.onAction(
                DocsAction.NavigateToComponent(initialComponentId),
            )
        }
    }

    DocsScreen(
        state = state,
        registry = ComponentRegistry,
        onSelect = { id -> viewModel.onAction(DocsAction.SelectPage(id)) },
    )
}
