package zed.rainxch.rikkaui.app

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import zed.rainxch.rikkaui.theme.AppPreferences

class AppViewModel(
    private val preferences: AppPreferences,
) : ViewModel() {
    private val _state =
        MutableStateFlow(
            AppState(isDark = preferences.loadIsDark()),
        )
    val state: StateFlow<AppState> = _state.asStateFlow()

    fun onAction(action: AppAction) {
        when (action) {
            is AppAction.SetDarkMode -> reduce { copy(isDark = action.isDark) }
            is AppAction.ToggleDarkMode -> reduce { copy(isDark = !isDark) }
        }
    }

    private fun reduce(block: AppState.() -> AppState) {
        _state.update { state ->
            val newState = state.block()
            preferences.saveIsDark(newState.isDark)
            newState
        }
    }
}
