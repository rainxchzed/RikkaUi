package zed.rainxch.rikkaui.showcase

import zed.rainxch.rikkaui.foundation.RikkaAccentPreset
import zed.rainxch.rikkaui.foundation.RikkaPalette
import zed.rainxch.rikkaui.foundation.RikkaStylePreset

sealed interface ShowcaseAction {
    data class ChangePalette(val palette: RikkaPalette) : ShowcaseAction

    data class ChangeAccent(val accent: RikkaAccentPreset) : ShowcaseAction

    data class ChangeStylePreset(val preset: RikkaStylePreset) : ShowcaseAction
}
