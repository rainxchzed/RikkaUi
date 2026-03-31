package zed.rainxch.rikkaui.app

import androidx.compose.runtime.staticCompositionLocalOf

data class AppState(
    val isDark: Boolean = true,
)

val LocalAppState =
    staticCompositionLocalOf {
        AppState()
    }
