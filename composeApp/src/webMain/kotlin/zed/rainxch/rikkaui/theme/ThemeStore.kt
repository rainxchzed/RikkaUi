package zed.rainxch.rikkaui.theme

import kotlinx.browser.localStorage

class ThemeStore : AppPreferences {
    override fun loadIsDark(): Boolean {
        val stored = localStorage.getItem(KEY_IS_DARK)
        return stored?.toBooleanStrictOrNull() ?: true
    }

    override fun saveIsDark(isDark: Boolean) {
        localStorage.setItem(KEY_IS_DARK, isDark.toString())
    }

    private companion object {
        const val KEY_IS_DARK = "rikkaui_is_dark"
    }
}
