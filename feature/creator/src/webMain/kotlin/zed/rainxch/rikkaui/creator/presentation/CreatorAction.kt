package zed.rainxch.rikkaui.creator.presentation

import zed.rainxch.rikkaui.foundation.RikkaAccentPreset
import zed.rainxch.rikkaui.foundation.RikkaPalette
import zed.rainxch.rikkaui.foundation.RikkaStylePreset

sealed interface CreatorAction {
    data class ChangeStylePreset(val preset: RikkaStylePreset) : CreatorAction

    data class ChangePalette(val palette: RikkaPalette) : CreatorAction

    data class ChangeAccent(val accent: RikkaAccentPreset) : CreatorAction

    data class ChangePreviewDark(val isDark: Boolean) : CreatorAction

    data class ChangeFont(val fontId: String) : CreatorAction

    data object Download : CreatorAction
}
