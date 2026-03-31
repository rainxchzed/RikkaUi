package zed.rainxch.rikkaui.creator.presentation

import zed.rainxch.rikkaui.foundation.RikkaAccentPreset
import zed.rainxch.rikkaui.foundation.RikkaPalette
import zed.rainxch.rikkaui.foundation.RikkaStylePreset

data class CreatorState(
    val stylePreset: RikkaStylePreset = RikkaStylePreset.Default,
    val palette: RikkaPalette = RikkaPalette.Zinc,
    val accent: RikkaAccentPreset = RikkaAccentPreset.Default,
    val previewDark: Boolean = true,
    val fontId: String = FontIds.INTER,
)

object FontIds {
    const val INTER = "inter"
}
