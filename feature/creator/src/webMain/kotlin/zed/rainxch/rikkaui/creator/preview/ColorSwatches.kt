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
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.creator.generated.resources.*
import rikkaui.feature.creator.generated.resources.Res
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme

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
            text = stringResource(Res.string.colors_heading),
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
                stringResource(Res.string.color_background),
                RikkaTheme.colors.background,
                shape,
            )
            ColorSwatch(
                stringResource(Res.string.color_on_background),
                RikkaTheme.colors.onBackground,
                shape,
            )
            ColorSwatch(
                stringResource(Res.string.color_surface),
                RikkaTheme.colors.surface,
                shape,
            )
            ColorSwatch(
                stringResource(Res.string.color_primary),
                RikkaTheme.colors.primary,
                shape,
            )
            ColorSwatch(
                stringResource(Res.string.color_secondary),
                RikkaTheme.colors.secondary,
                shape,
            )
            ColorSwatch(
                stringResource(Res.string.color_muted),
                RikkaTheme.colors.muted,
                shape,
            )
            ColorSwatch(
                stringResource(Res.string.color_destructive),
                RikkaTheme.colors.destructive,
                shape,
            )
            ColorSwatch(
                stringResource(Res.string.color_warning),
                RikkaTheme.colors.warning,
                shape,
            )
            ColorSwatch(
                stringResource(Res.string.color_success),
                RikkaTheme.colors.success,
                shape,
            )
            ColorSwatch(
                stringResource(Res.string.color_border),
                RikkaTheme.colors.border,
                shape,
            )
            ColorSwatch(
                stringResource(Res.string.color_ring),
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
