package zed.rainxch.rikkaui.creator.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.foundation.modifier.keyboardScrollable
import zed.rainxch.rikkaui.creator.fonts.preloadAllCreatorFonts
import zed.rainxch.rikkaui.creator.panel.ConfigPanel
import zed.rainxch.rikkaui.creator.preview.LivePreview
import zed.rainxch.rikkaui.foundation.RikkaTheme

@Composable
fun CreatorScreen(
    state: CreatorState,
    onAction: (CreatorAction) -> Unit,
) {
    preloadAllCreatorFonts()

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        val isWide = maxWidth >= 900.dp

        if (isWide) {
            WideLayout(state = state, onAction = onAction)
        } else {
            CompactLayout(state = state, onAction = onAction)
        }
    }
}

@Composable
private fun WideLayout(
    state: CreatorState,
    onAction: (CreatorAction) -> Unit,
) {
    val configScroll = rememberScrollState()
    val previewScroll = rememberScrollState()

    Row(
        modifier =
            Modifier
                .widthIn(max = 1800.dp)
                .fillMaxSize()
                .padding(
                    horizontal = RikkaTheme.spacing.lg,
                    vertical = RikkaTheme.spacing.lg,
                ),
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.lg),
    ) {
        Column(
            modifier =
                Modifier
                    .weight(0.28f)
                    .fillMaxHeight()
                    .keyboardScrollable(configScroll)
                    .verticalScroll(configScroll),
        ) {
            CreatorConfigPanel(state = state, onAction = onAction)
        }

        Column(
            modifier =
                Modifier
                    .weight(0.72f)
                    .fillMaxHeight()
                    .keyboardScrollable(previewScroll)
                    .verticalScroll(previewScroll),
        ) {
            LivePreview(
                stylePreset = state.stylePreset,
                palette = state.palette,
                accent = state.accent,
                isDark = state.previewDark,
                fontId = state.fontId,
            )
        }
    }
}

@Composable
private fun CompactLayout(
    state: CreatorState,
    onAction: (CreatorAction) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier =
            Modifier
                .widthIn(max = 700.dp)
                .fillMaxSize()
                .keyboardScrollable(scrollState)
                .verticalScroll(scrollState)
                .padding(
                    horizontal = RikkaTheme.spacing.md,
                    vertical = RikkaTheme.spacing.lg,
                ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CreatorConfigPanel(state = state, onAction = onAction)

        Spacer(Modifier.height(RikkaTheme.spacing.xl))

        LivePreview(
            stylePreset = state.stylePreset,
            palette = state.palette,
            accent = state.accent,
            isDark = state.previewDark,
            fontId = state.fontId,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun CreatorConfigPanel(
    state: CreatorState,
    onAction: (CreatorAction) -> Unit,
) {
    ConfigPanel(
        stylePreset = state.stylePreset,
        onStyleChange = { onAction(CreatorAction.ChangeStylePreset(it)) },
        palette = state.palette,
        onPaletteChange = { onAction(CreatorAction.ChangePalette(it)) },
        accent = state.accent,
        onAccentChange = { onAction(CreatorAction.ChangeAccent(it)) },
        previewDark = state.previewDark,
        onPreviewDarkChange = { onAction(CreatorAction.ChangePreviewDark(it)) },
        fontId = state.fontId,
        onFontChange = { onAction(CreatorAction.ChangeFont(it)) },
        onDownload = { onAction(CreatorAction.Download) },
    )
}
