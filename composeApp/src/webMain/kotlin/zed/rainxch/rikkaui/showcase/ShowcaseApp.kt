package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import rikkaui.composeapp.generated.resources.Res
import rikkaui.composeapp.generated.resources.components_in_action
import rikkaui.composeapp.generated.resources.components_in_action_desc
import rikkaui.composeapp.generated.resources.make_it_yours
import rikkaui.composeapp.generated.resources.make_it_yours_desc
import zed.rainxch.rikkaui.components.theme.RikkaStylePreset
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

/**
 * Root layout composable for the showcase website.
 *
 * Orchestrates the page flow:
 * 1. Hero section
 * 2. "Components in Action" — responsive example grid
 * 3. "Make It Yours" — interactive theme controls
 * 4. Footer
 */
@Composable
fun ShowcaseApp(
    isDark: Boolean,
    onDarkChange: (Boolean) -> Unit,
    paletteName: String,
    onPaletteChange: (String) -> Unit,
    accentName: String,
    onAccentChange: (String) -> Unit,
    stylePreset: RikkaStylePreset,
    onStyleChange: (RikkaStylePreset) -> Unit,
    onNavigateToCreator: () -> Unit = {},
) {
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

            Text(
                text = stringResource(Res.string.components_in_action),
                variant = TextVariant.H2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.xs))

            Text(
                text =
                    stringResource(
                        Res.string.components_in_action_desc,
                    ),
                variant = TextVariant.Muted,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.xxl))

            ExamplesGrid(sizeClass)

            Spacer(Modifier.height(RikkaTheme.spacing.xxxl))

            Separator()

            Spacer(Modifier.height(RikkaTheme.spacing.xl))

            Text(
                text = stringResource(Res.string.make_it_yours),
                variant = TextVariant.H2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.xs))

            Text(
                text = stringResource(Res.string.make_it_yours_desc),
                variant = TextVariant.Muted,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.xxl))

            ThemeSection(
                isDark = isDark,
                onDarkChange = onDarkChange,
                paletteName = paletteName,
                onPaletteChange = onPaletteChange,
                accentName = accentName,
                onAccentChange = onAccentChange,
                stylePreset = stylePreset,
                onStyleChange = onStyleChange,
            )

            Spacer(Modifier.height(RikkaTheme.spacing.xxxl))

            FooterSection()
        }
    }
}
