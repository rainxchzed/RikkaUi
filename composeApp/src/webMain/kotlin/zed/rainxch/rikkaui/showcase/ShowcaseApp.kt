package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import rikkaui.composeapp.generated.resources.Res
import rikkaui.composeapp.generated.resources.components_in_action
import rikkaui.composeapp.generated.resources.components_in_action_desc
import zed.rainxch.rikkaui.components.theme.RikkaAccentPreset
import zed.rainxch.rikkaui.components.theme.RikkaFontFamily
import zed.rainxch.rikkaui.components.theme.RikkaPalette
import zed.rainxch.rikkaui.components.theme.RikkaStylePreset
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.theme.rikkaTypography
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

/**
 * Showcase landing page with its own local preview state.
 *
 * Theme changes here (palette, accent, style) only affect the
 * example cards — they do NOT bleed into other screens.
 */
@Composable
fun ShowcaseApp(
    isDark: Boolean,
    fontFamily: RikkaFontFamily,
    onNavigateToCreator: () -> Unit = {},
) {
    // ─── Local preview state (screen-scoped, not persisted) ───
    var palette by remember { mutableStateOf(RikkaPalette.Zinc) }
    var accent by remember { mutableStateOf(RikkaAccentPreset.Default) }
    var stylePreset by remember { mutableStateOf(RikkaStylePreset.Default) }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        val sizeClass = WindowSizeClass.fromWidth(maxWidth)
        val horizontalPadding =
            when (sizeClass) {
                WindowSizeClass.Compact -> RikkaTheme.spacing.md
                WindowSizeClass.Medium -> RikkaTheme.spacing.lg
                WindowSizeClass.Expanded -> RikkaTheme.spacing.lg
            }

        Column(
            modifier =
                Modifier
                    .widthIn(max = 1200.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = horizontalPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HeroSection(onGetStarted = onNavigateToCreator)

            Spacer(Modifier.height(RikkaTheme.spacing.xl))

            // Section title
            Text(
                text = stringResource(Res.string.components_in_action),
                variant = TextVariant.H2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.xs))

            Text(
                text = stringResource(Res.string.components_in_action_desc),
                variant = TextVariant.Muted,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.lg))

            // Theme toolbar — compact popovers above examples
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                ThemeToolbar(
                    palette = palette,
                    onPaletteChange = { palette = it },
                    accent = accent,
                    onAccentChange = { accent = it },
                    stylePreset = stylePreset,
                    onStyleChange = { stylePreset = it },
                )
            }

            Spacer(Modifier.height(RikkaTheme.spacing.xxl))

            // Nested RikkaTheme — preview changes only affect examples
            RikkaTheme(
                palette = palette,
                accent = accent,
                isDark = isDark,
                preset = stylePreset,
                typography =
                    rikkaTypography(
                        fontFamily = fontFamily,
                        scale = stylePreset.typeScale,
                    ),
            ) {
                ExamplesGrid(sizeClass)
            }

            Spacer(Modifier.height(RikkaTheme.spacing.xxxl))

            FooterSection()
        }
    }
}
