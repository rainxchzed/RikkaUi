@file:OptIn(ExperimentalLayoutApi::class)

package zed.rainxch.rikkaui.creator.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

/**
 * Displays a visual grid of all semantic color tokens.
 *
 * Two rows of rounded swatches with truncated labels underneath,
 * matching the style seen in shadcn's "Create" page.
 */
@Composable
fun ColorSwatches(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Colors",
            variant = TextVariant.Small,
        )
        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        val shape = RikkaTheme.shapes.md

        FlowRow(
            horizontalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.sm),
            verticalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.sm),
        ) {
            ColorSwatch(
                "background",
                RikkaTheme.colors.background,
                shape,
            )
            ColorSwatch(
                "foreground",
                RikkaTheme.colors.foreground,
                shape,
            )
            ColorSwatch(
                "primary",
                RikkaTheme.colors.primary,
                shape,
            )
            ColorSwatch(
                "secondary",
                RikkaTheme.colors.secondary,
                shape,
            )
            ColorSwatch(
                "muted",
                RikkaTheme.colors.muted,
                shape,
            )
            ColorSwatch(
                "accent",
                RikkaTheme.colors.accent,
                shape,
            )
            ColorSwatch(
                "card",
                RikkaTheme.colors.card,
                shape,
            )
            ColorSwatch(
                "border",
                RikkaTheme.colors.border,
                shape,
            )
            ColorSwatch(
                "destructive",
                RikkaTheme.colors.destructive,
                shape,
            )
            ColorSwatch(
                "input",
                RikkaTheme.colors.input,
                shape,
            )
            ColorSwatch(
                "ring",
                RikkaTheme.colors.ring,
                shape,
            )
        }
    }
}

@Composable
private fun ColorSwatch(
    label: String,
    color: Color,
    shape: Shape,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier =
                Modifier
                    .size(48.dp)
                    .clip(shape)
                    .background(color)
                    .border(
                        width = 1.dp,
                        color = RikkaTheme.colors.border,
                        shape = shape,
                    ),
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = label,
            variant = TextVariant.Muted,
            style = RikkaTheme.typography.small,
        )
    }
}
