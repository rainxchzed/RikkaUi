package zed.rainxch.rikkaui.theme

interface AppPreferences {
    fun loadIsDark(): Boolean

    fun saveIsDark(isDark: Boolean)
}
