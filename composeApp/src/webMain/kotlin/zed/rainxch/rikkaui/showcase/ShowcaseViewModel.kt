package zed.rainxch.rikkaui.showcase

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ShowcaseViewModel : ViewModel() {
    private val _state = MutableStateFlow(ShowcaseState())
    val state: StateFlow<ShowcaseState> = _state.asStateFlow()

    fun onAction(action: ShowcaseAction) {
        when (action) {
            is ShowcaseAction.ChangePalette -> reduce { copy(palette = action.palette) }
            is ShowcaseAction.ChangeAccent -> reduce { copy(accent = action.accent) }
            is ShowcaseAction.ChangeStylePreset -> reduce { copy(stylePreset = action.preset) }
        }
    }

    private fun reduce(block: ShowcaseState.() -> ShowcaseState) {
        _state.update { it.block() }
    }
}
