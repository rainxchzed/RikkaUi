package zed.rainxch.rikkaui.showcase

import zed.rainxch.rikkaui.foundation.RikkaAccentPreset
import zed.rainxch.rikkaui.foundation.RikkaPalette
import zed.rainxch.rikkaui.foundation.RikkaStylePreset

data class ShowcaseState(
    val palette: RikkaPalette = RikkaPalette.Zinc,
    val accent: RikkaAccentPreset = RikkaAccentPreset.Default,
    val stylePreset: RikkaStylePreset = RikkaStylePreset.Default,
)
