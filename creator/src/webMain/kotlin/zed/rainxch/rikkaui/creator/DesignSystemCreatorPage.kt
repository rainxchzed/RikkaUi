package zed.rainxch.rikkaui.creator

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaStylePreset
import zed.rainxch.rikkaui.components.theme.RikkaTheme

/**
 * Root composable for the "Create Your Design System" page.
 *
 * Two-panel layout:
 * - **Left:** Configuration panel (style, palette, accent, font, dark mode)
 * - **Right:** Live preview showing real components with the configured theme
 *
 * On narrow screens the panels stack vertically.
 */
@Composable
fun DesignSystemCreatorPage() {
    var stylePreset by remember { mutableStateOf(RikkaStylePreset.Default) }
    var paletteName by remember { mutableStateOf("Zinc") }
    var accentName by remember { mutableStateOf("Default") }
    var previewDark by remember { mutableStateOf(true) }
    var fontId by remember { mutableStateOf("inter") }

    val selectedFont = availableFonts.find { it.id == fontId }
        ?: availableFonts.first()

    val onDownload: () -> Unit = {
        val themeCode = generateThemeCode(
            paletteName = paletteName,
            accentName = accentName,
            stylePreset = stylePreset,
            fontId = fontId,
            fontDisplayName = selectedFont.displayName,
        )
        val readme = generateReadme(
            paletteName = paletteName,
            accentName = accentName,
            stylePreset = stylePreset,
            fontDisplayName = selectedFont.displayName,
        )
        downloadDesignSystemZip(
            zipName = "rikka-theme.zip",
            themeCode = themeCode,
            readmeContent = readme,
            fontId = fontId,
        )
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        val isWide = maxWidth >= 900.dp

        if (isWide) {
            Row(
                modifier =
                    Modifier
                        .widthIn(max = 1400.dp)
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
                            .weight(0.38f)
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState()),
                ) {
                    ConfigPanel(
                        stylePreset = stylePreset,
                        onStyleChange = { stylePreset = it },
                        paletteName = paletteName,
                        onPaletteChange = { paletteName = it },
                        accentName = accentName,
                        onAccentChange = { accentName = it },
                        previewDark = previewDark,
                        onPreviewDarkChange = { previewDark = it },
                        fontId = fontId,
                        onFontChange = { fontId = it },
                        onDownload = onDownload,
                    )
                }

                Column(
                    modifier =
                        Modifier
                            .weight(0.62f)
                            .fillMaxHeight(),
                ) {
                    LivePreview(
                        stylePreset = stylePreset,
                        paletteName = paletteName,
                        accentName = accentName,
                        isDark = previewDark,
                        fontId = fontId,
                    )
                }
            }
        } else {
            Column(
                modifier =
                    Modifier
                        .widthIn(max = 700.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(
                            horizontal = RikkaTheme.spacing.md,
                            vertical = RikkaTheme.spacing.lg,
                        ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ConfigPanel(
                    stylePreset = stylePreset,
                    onStyleChange = { stylePreset = it },
                    paletteName = paletteName,
                    onPaletteChange = { paletteName = it },
                    accentName = accentName,
                    onAccentChange = { accentName = it },
                    previewDark = previewDark,
                    onPreviewDarkChange = { previewDark = it },
                    fontId = fontId,
                    onFontChange = { fontId = it },
                    onDownload = onDownload,
                )

                Spacer(Modifier.height(RikkaTheme.spacing.xl))

                LivePreview(
                    stylePreset = stylePreset,
                    paletteName = paletteName,
                    accentName = accentName,
                    isDark = previewDark,
                    fontId = fontId,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
