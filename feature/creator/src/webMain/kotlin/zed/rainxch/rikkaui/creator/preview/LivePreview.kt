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
import androidx.compose.foundation.layout.width
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
import zed.rainxch.rikkaui.components.ui.slider.Slider
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.textarea.Textarea
import zed.rainxch.rikkaui.components.ui.toggle.Toggle
import zed.rainxch.rikkaui.creator.fonts.availableFonts
import zed.rainxch.rikkaui.creator.fonts.resolvePreviewFontFamily
import zed.rainxch.rikkaui.creator.preview.examples.ExampleActivityLog
import zed.rainxch.rikkaui.creator.preview.examples.ExampleApiKeyManager
import zed.rainxch.rikkaui.creator.preview.examples.ExampleBookAppointment
import zed.rainxch.rikkaui.creator.preview.examples.ExampleChatComposer
import zed.rainxch.rikkaui.creator.preview.examples.ExampleCookieSettings
import zed.rainxch.rikkaui.creator.preview.examples.ExampleEnvVariables
import zed.rainxch.rikkaui.creator.preview.examples.ExampleFeedbackForm
import zed.rainxch.rikkaui.creator.preview.examples.ExampleFileManager
import zed.rainxch.rikkaui.creator.preview.examples.ExampleInviteTeam
import zed.rainxch.rikkaui.creator.preview.examples.ExampleNotificationSettings
import zed.rainxch.rikkaui.creator.preview.examples.ExampleOnboarding
import zed.rainxch.rikkaui.creator.preview.examples.ExamplePaymentMethod
import zed.rainxch.rikkaui.creator.preview.examples.ExamplePricingCard
import zed.rainxch.rikkaui.creator.preview.examples.ExampleProfileSettings
import zed.rainxch.rikkaui.creator.preview.examples.ExampleQuickNote
import zed.rainxch.rikkaui.creator.preview.examples.ExampleReportBug
import zed.rainxch.rikkaui.creator.preview.examples.ExampleSearchCommand
import zed.rainxch.rikkaui.creator.preview.examples.ExampleShippingAddress
import zed.rainxch.rikkaui.creator.preview.examples.ExampleTaskList
import zed.rainxch.rikkaui.creator.preview.examples.ExampleUserDirectory
import zed.rainxch.rikkaui.creator.resolveAccent
import zed.rainxch.rikkaui.creator.resolvePalette

/**
 * Live preview panel that renders real components inside
 * a nested [RikkaTheme].
 *
 * Layout (top to bottom, scrollable):
 * 1. Theme header (style + font name)
 * 2. Color swatches (semantic color tokens)
 * 3. Component strip (buttons, form elements, badges)
 * 4. Typography preview (heading + body text)
 * 5. Example cards grid (~20 real-world app cards)
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
    val fontDisplayName =
        availableFonts.find { it.id == fontId }
            ?.displayName ?: "Inter"

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
            // ── 1. Theme Header ──
            ThemeHeader(
                styleName = stylePreset.label,
                fontName = fontDisplayName,
            )

            Spacer(Modifier.height(RikkaTheme.spacing.xl))

            // ── 2. Color Swatches ──
            ColorSwatches()

            Spacer(Modifier.height(RikkaTheme.spacing.xl))
            Separator()
            Spacer(Modifier.height(RikkaTheme.spacing.xl))

            // ── 3. Component Strip ──
            ComponentStrip()

            Spacer(Modifier.height(RikkaTheme.spacing.xl))
            Separator()
            Spacer(Modifier.height(RikkaTheme.spacing.xl))

            // ── 4. Typography Preview ──
            TypographyPreview(fontDisplayName = fontDisplayName)

            Spacer(Modifier.height(RikkaTheme.spacing.xxl))
            Separator()
            Spacer(Modifier.height(RikkaTheme.spacing.xxl))

            // ── 5. Example Cards Grid ──
            Text(
                text = "Examples",
                variant = TextVariant.H3,
            )
            Spacer(Modifier.height(RikkaTheme.spacing.xs))
            Text(
                text = "Real-world UI patterns built with" +
                    " your theme configuration.",
                variant = TextVariant.Muted,
            )
            Spacer(Modifier.height(RikkaTheme.spacing.lg))

            ExampleCardsGrid()
        }
    }
}

// ────────────────────────────────────────────────────────
// Theme Header
// ────────────────────────────────────────────────────────

@Composable
private fun ThemeHeader(
    styleName: String,
    fontName: String,
) {
    Text(
        text = "$styleName \u00B7 $fontName",
        variant = TextVariant.H2,
    )
    Spacer(Modifier.height(RikkaTheme.spacing.xs))
    Text(
        text = "This is a preview of your design system" +
            " configuration. Every component below uses" +
            " your selected theme.",
        variant = TextVariant.Muted,
    )
}

// ────────────────────────────────────────────────────────
// Component Strip
// ────────────────────────────────────────────────────────

@Composable
private fun ComponentStrip() {
    // Buttons
    Text(text = "Buttons", variant = TextVariant.Small)
    Spacer(Modifier.height(RikkaTheme.spacing.sm))
    FlowRow(
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
        verticalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
    ) {
        Button(text = "Button", onClick = {})
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
    }

    Spacer(Modifier.height(RikkaTheme.spacing.lg))

    // Form elements
    var sliderVal by remember { mutableStateOf(0.5f) }
    Slider(
        value = sliderVal,
        onValueChange = { sliderVal = it },
        modifier = Modifier.fillMaxWidth(),
    )

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    var inputVal by remember { mutableStateOf("") }
    Input(
        value = inputVal,
        onValueChange = { inputVal = it },
        placeholder = "Name",
        label = "Name",
        modifier = Modifier.fillMaxWidth(),
    )

    Spacer(Modifier.height(RikkaTheme.spacing.sm))

    var textareaVal by remember { mutableStateOf("") }
    Textarea(
        value = textareaVal,
        onValueChange = { textareaVal = it },
        placeholder = "Message",
        label = "Message",
        modifier = Modifier.fillMaxWidth(),
    )

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    // Badges + form controls row
    FlowRow(
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
        verticalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.sm),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Badge(text = "Badge")
        Badge(
            text = "Secondary",
            variant = BadgeVariant.Secondary,
        )
        Badge(
            text = "Outline",
            variant = BadgeVariant.Outline,
        )
    }

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    Row(
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        var checkState by remember { mutableStateOf(true) }
        Checkbox(
            checked = checkState,
            onCheckedChange = { checkState = it },
            label = "",
        )

        var toggleState by remember { mutableStateOf(false) }
        Toggle(
            checked = toggleState,
            onCheckedChange = { toggleState = it },
            label = "Toggle",
        )
    }

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    Progress(
        progress = 0.65f,
        modifier = Modifier.fillMaxWidth(),
    )
}

// ────────────────────────────────────────────────────────
// Example Cards Grid
// ────────────────────────────────────────────────────────

/**
 * Responsive grid of ~20 real-world example cards.
 *
 * Uses [FlowRow] so cards wrap naturally. Each card has
 * a fixed preferred width so they form columns on wide
 * screens and stack on narrow ones.
 */
@Composable
private fun ExampleCardsGrid() {
    val cardModifier = Modifier.width(360.dp)

    FlowRow(
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.md),
        verticalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.md),
        modifier = Modifier.fillMaxWidth(),
    ) {
        // Row 1 — varied card types
        ExampleEnvVariables(modifier = cardModifier)
        ExampleBookAppointment(modifier = cardModifier)
        ExampleFeedbackForm(modifier = cardModifier)

        // Row 2
        ExampleTaskList(modifier = cardModifier)
        ExampleProfileSettings(modifier = cardModifier)
        ExampleSearchCommand(modifier = cardModifier)

        // Row 3
        ExampleInviteTeam(modifier = cardModifier)
        ExamplePricingCard(modifier = cardModifier)
        ExampleNotificationSettings(modifier = cardModifier)

        // Row 4
        ExampleReportBug(modifier = cardModifier)
        ExampleChatComposer(modifier = cardModifier)
        ExampleApiKeyManager(modifier = cardModifier)

        // Row 5
        ExamplePaymentMethod(modifier = cardModifier)
        ExampleFileManager(modifier = cardModifier)
        ExampleUserDirectory(modifier = cardModifier)

        // Row 6
        ExampleShippingAddress(modifier = cardModifier)
        ExampleCookieSettings(modifier = cardModifier)
        ExampleOnboarding(modifier = cardModifier)

        // Row 7
        ExampleActivityLog(modifier = cardModifier)
        ExampleQuickNote(modifier = cardModifier)
    }
}
