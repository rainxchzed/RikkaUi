package zed.rainxch.rikkaui.docs.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import zed.rainxch.rikkaui.docs.catalog.ComponentRegistry
import zed.rainxch.rikkaui.docs.catalog.guidePageIds

@Composable
fun DocsRoute(
    initialComponentId: String? = null,
    onNavigateToComponent: (String) -> Unit = {},
    onNavigateToGuide: (String) -> Unit = {},
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

    val onSelect: (String) -> Unit = remember(viewModel) {
        { id: String ->
            viewModel.onAction(DocsAction.SelectPage(id))
            if (id in guidePageIds) {
                onNavigateToGuide(id)
            } else {
                onNavigateToComponent(id)
            }
        }
    }

    DocsScreen(
        state = state,
        registry = ComponentRegistry,
        onSelect = onSelect,
    )
}
