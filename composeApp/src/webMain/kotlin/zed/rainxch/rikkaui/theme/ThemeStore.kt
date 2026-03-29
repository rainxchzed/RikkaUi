package zed.rainxch.rikkaui.theme

import kotlinx.browser.localStorage

object ThemeStore {
    private const val KEY_IS_DARK = "rikkaui_is_dark"

    fun loadIsDark(): Boolean {
        val stored = localStorage.getItem(KEY_IS_DARK)
        return stored?.toBooleanStrictOrNull() ?: true
    }

    fun saveIsDark(isDark: Boolean) {
        localStorage.setItem(KEY_IS_DARK, isDark.toString())
    }
}
