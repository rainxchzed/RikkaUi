package zed.rainxch.rikkaui.theme

sealed interface ThemeAction {
    data class SetDarkMode(
        val isDark: Boolean,
    ) : ThemeAction

    data object ToggleDarkMode : ThemeAction
}
