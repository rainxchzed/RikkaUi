@file:OptIn(ExperimentalLayoutApi::class)

package zed.rainxch.rikkaui.creator.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaStylePreset
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.theme.rikkaTypography
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.checkbox.Checkbox
import zed.rainxch.rikkaui.components.ui.input.Input
import zed.rainxch.rikkaui.components.ui.progress.Progress
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.toggle.Toggle
import zed.rainxch.rikkaui.creator.fonts.resolvePreviewFontFamily
import zed.rainxch.rikkaui.creator.resolveAccent
import zed.rainxch.rikkaui.creator.resolvePalette

/**
 * Live preview panel that renders real components inside a nested [RikkaTheme].
 *
 * The nested theme applies the user's selected palette, accent, style preset,
 * and dark/light mode so the preview reflects their configuration in real time.
 */
@Composable
fun LivePreview(
    stylePreset: RikkaStylePreset,
    paletteName: String,
    accentName: String,
    isDark: Boolean,
    fontId: String = "inter",
    modifier: Modifier = Modifier,
) {
    val baseColors = resolvePalette(paletteName, isDark)
    val colors = resolveAccent(baseColors, accentName, isDark)
    val rikkaFont = resolvePreviewFontFamily(fontId)

    RikkaTheme(
        colors = colors,
        typography =
            rikkaTypography(
                fontFamily = rikkaFont,
                scale = stylePreset.typeScale,
            ),
        spacing = stylePreset.spacing,
        shapes = stylePreset.shapes,
        motion = stylePreset.motion,
    ) {
        Column(
            modifier =
                modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        width = 1.dp,
                        color = RikkaTheme.colors.border,
                        shape = RoundedCornerShape(12.dp),
                    ).background(RikkaTheme.colors.background)
                    .padding(RikkaTheme.spacing.lg)
                    .verticalScroll(rememberScrollState()),
        ) {
            PreviewHeader()

            Spacer(Modifier.height(RikkaTheme.spacing.lg))

            PreviewButtons()

            Spacer(Modifier.height(RikkaTheme.spacing.lg))
            Separator()
            Spacer(Modifier.height(RikkaTheme.spacing.lg))

            PreviewCard()

            Spacer(Modifier.height(RikkaTheme.spacing.lg))

            PreviewFormElements()

            Spacer(Modifier.height(RikkaTheme.spacing.lg))
            Separator()
            Spacer(Modifier.height(RikkaTheme.spacing.lg))

            PreviewBadges()

            Spacer(Modifier.height(RikkaTheme.spacing.lg))

            PreviewProgress()
        }
    }
}

@Composable
private fun PreviewHeader() {
    Text(
        text = "Preview",
        variant = TextVariant.H3,
    )
    Spacer(Modifier.height(RikkaTheme.spacing.xs))
    Text(
        text = "This is how your design system will look.",
        variant = TextVariant.Muted,
    )
}

@Composable
private fun PreviewButtons() {
    Text(text = "Buttons", variant = TextVariant.Small)
    Spacer(Modifier.height(RikkaTheme.spacing.sm))
    FlowRow(
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
        verticalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
    ) {
        Button(text = "Primary", onClick = {})
        Button(
            text = "Secondary",
            onClick = {},
            variant = ButtonVariant.Secondary,
        )
        Button(
            text = "Outline",
            onClick = {},
            variant = ButtonVariant.Outline,
        )
        Button(
            text = "Ghost",
            onClick = {},
            variant = ButtonVariant.Ghost,
        )
        Button(
            text = "Destructive",
            onClick = {},
            variant = ButtonVariant.Destructive,
        )
    }
}

@Composable
private fun PreviewCard() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Card Title", variant = TextVariant.H4)
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        Text(
            text =
                "This is a sample card showing how content" +
                    " looks inside a card component with your" +
                    " selected theme configuration.",
            variant = TextVariant.Muted,
        )
        Spacer(Modifier.height(RikkaTheme.spacing.md))
        Row(
            horizontalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.sm),
        ) {
            Button(text = "Action", onClick = {})
            Button(
                text = "Cancel",
                onClick = {},
                variant = ButtonVariant.Outline,
            )
        }
    }
}

@Composable
private fun PreviewFormElements() {
    Text(text = "Form Elements", variant = TextVariant.Small)
    Spacer(Modifier.height(RikkaTheme.spacing.sm))

    var inputValue by remember { mutableStateOf("") }
    Input(
        value = inputValue,
        onValueChange = { inputValue = it },
        placeholder = "Type something...",
        label = "Input field",
        modifier = Modifier.fillMaxWidth(),
    )

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    Row(
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        var toggleState by remember { mutableStateOf(true) }
        Toggle(
            checked = toggleState,
            onCheckedChange = { toggleState = it },
            label = "Toggle option",
        )

        var checkState by remember { mutableStateOf(true) }
        Checkbox(
            checked = checkState,
            onCheckedChange = { checkState = it },
            label = "Checkbox",
        )
    }
}

@Composable
private fun PreviewBadges() {
    Text(text = "Badges", variant = TextVariant.Small)
    Spacer(Modifier.height(RikkaTheme.spacing.sm))
    FlowRow(
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
        verticalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
    ) {
        Badge(text = "Default")
        Badge(text = "Secondary", variant = BadgeVariant.Secondary)
        Badge(text = "Outline", variant = BadgeVariant.Outline)
        Badge(text = "Destructive", variant = BadgeVariant.Destructive)
    }
}

@Composable
private fun PreviewProgress() {
    Text(text = "Progress", variant = TextVariant.Small)
    Spacer(Modifier.height(RikkaTheme.spacing.sm))
    Progress(
        progress = 0.65f,
        modifier = Modifier.fillMaxWidth(),
    )
}
