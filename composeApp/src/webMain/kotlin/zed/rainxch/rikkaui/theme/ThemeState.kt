package zed.rainxch.rikkaui.theme

/**
 * MVI State for the app-level theme.
 * Only persisted settings live here — dark mode preference.
 */
data class ThemeState(
    val isDark: Boolean = true,
)

/**
 * MVI Intents for the app-level theme.
 */
sealed interface ThemeIntent {
    data class SetDarkMode(
        val isDark: Boolean,
    ) : ThemeIntent

    data object ToggleDarkMode : ThemeIntent
}
