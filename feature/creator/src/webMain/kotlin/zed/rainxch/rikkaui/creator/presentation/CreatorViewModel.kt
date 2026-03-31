package zed.rainxch.rikkaui.creator.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import zed.rainxch.rikkaui.creator.codegen.generateReadme
import zed.rainxch.rikkaui.creator.codegen.generateThemeCode
import zed.rainxch.rikkaui.creator.download.downloadDesignSystemZip
import zed.rainxch.rikkaui.creator.fonts.availableFonts

class CreatorViewModel : ViewModel() {
    private val _state = MutableStateFlow(CreatorState())
    val state: StateFlow<CreatorState> = _state.asStateFlow()

    fun onAction(action: CreatorAction) {
        when (action) {
            is CreatorAction.ChangeStylePreset -> reduce { copy(stylePreset = action.preset) }
            is CreatorAction.ChangePalette -> reduce { copy(palette = action.palette) }
            is CreatorAction.ChangeAccent -> reduce { copy(accent = action.accent) }
            is CreatorAction.ChangePreviewDark -> reduce { copy(previewDark = action.isDark) }
            is CreatorAction.ChangeFont -> reduce { copy(fontId = action.fontId) }
            is CreatorAction.Download -> handleDownload()
        }
    }

    private fun handleDownload() {
        val current = _state.value
        val selectedFont =
            availableFonts.find { it.id == current.fontId }
                ?: availableFonts.first()

        val themeCode =
            generateThemeCode(
                palette = current.palette,
                accent = current.accent,
                stylePreset = current.stylePreset,
                fontId = current.fontId,
                fontDisplayName = selectedFont.displayName,
            )
        val readme =
            generateReadme(
                palette = current.palette,
                accent = current.accent,
                stylePreset = current.stylePreset,
                fontDisplayName = selectedFont.displayName,
            )
        downloadDesignSystemZip(
            zipName = "rikka-theme.zip",
            themeCode = themeCode,
            readmeContent = readme,
            fontId = current.fontId,
            fontWeights = selectedFont.weights,
        )
    }

    private fun reduce(block: CreatorState.() -> CreatorState) {
        _state.update { it.block() }
    }
}
