package zed.rainxch.rikkaui.docs

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DocsViewModel(
    initialComponentId: String? = null,
) : ViewModel() {
    private val _state =
        MutableStateFlow(
            DocsState(
                selectedId = initialComponentId ?: "introduction",
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
