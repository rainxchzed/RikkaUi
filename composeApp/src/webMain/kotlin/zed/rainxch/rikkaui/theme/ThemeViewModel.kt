package zed.rainxch.rikkaui.theme

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ThemeViewModel : ViewModel() {
    private val _state =
        MutableStateFlow(
            ThemeState(isDark = ThemeStore.loadIsDark()),
        )
    val state: StateFlow<ThemeState> = _state.asStateFlow()

    fun onAction(action: ThemeAction) {
        when (action) {
            is ThemeAction.SetDarkMode -> reduce { copy(isDark = action.isDark) }
            is ThemeAction.ToggleDarkMode -> reduce { copy(isDark = !isDark) }
        }
    }

    private fun reduce(block: ThemeState.() -> ThemeState) {
        _state.update { state ->
            val newState = state.block()

            ThemeStore.saveIsDark(newState.isDark)
            newState
        }
    }
}
