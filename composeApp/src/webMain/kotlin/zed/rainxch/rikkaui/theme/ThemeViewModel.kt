package zed.rainxch.rikkaui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * MVI ViewModel for app-level theme state.
 *
 * Manages only persisted preferences (dark mode).
 * Screen-local preview state (palette, accent, style) stays
 * in each screen's own composable state.
 */
class ThemeViewModel {
    private val _state =
        MutableStateFlow(
            ThemeState(isDark = ThemeStore.loadIsDark()),
        )
    val state: StateFlow<ThemeState> = _state.asStateFlow()

    fun onIntent(intent: ThemeIntent) {
        when (intent) {
            is ThemeIntent.SetDarkMode -> reduce { copy(isDark = intent.isDark) }
            is ThemeIntent.ToggleDarkMode -> reduce { copy(isDark = !isDark) }
        }
    }

    private fun reduce(block: ThemeState.() -> ThemeState) {
        _state.update { state ->
            val newState = state.block()
            // Persist on every state change
            ThemeStore.saveIsDark(newState.isDark)
            newState
        }
    }
}

/**
 * Remember a single ThemeViewModel instance for the app lifetime.
 */
@Composable
fun rememberThemeViewModel(): ThemeViewModel = remember { ThemeViewModel() }
