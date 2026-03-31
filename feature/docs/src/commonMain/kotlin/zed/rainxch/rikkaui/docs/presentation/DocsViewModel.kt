package zed.rainxch.rikkaui.docs.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import zed.rainxch.rikkaui.docs.catalog.GuideIds

class DocsViewModel(
    initialComponentId: String? = null,
) : ViewModel() {
    private val _state =
        MutableStateFlow(
            DocsState(
                selectedId = initialComponentId ?: GuideIds.INTRODUCTION,
            ),
        )
    val state: StateFlow<DocsState> = _state.asStateFlow()

    fun onAction(action: DocsAction) {
        when (action) {
            is DocsAction.SelectPage -> {
                reduce { copy(selectedId = action.id) }
            }

            is DocsAction.NavigateToComponent -> {
                reduce { copy(selectedId = action.componentId) }
            }
        }
    }

    private fun reduce(block: DocsState.() -> DocsState) {
        _state.update { state -> state.block() }
    }
}
