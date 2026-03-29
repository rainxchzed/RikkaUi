package zed.rainxch.rikkaui.docs.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.Res
import rikkaui.feature.docs.generated.resources.prop_header_default
import rikkaui.feature.docs.generated.resources.prop_header_description
import rikkaui.feature.docs.generated.resources.prop_header_prop
import rikkaui.feature.docs.generated.resources.prop_header_type
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

// ─── Page Header ─────────────────────────────────────────────

/**
 * Component page header with title and description.
 */
@Composable
fun ComponentPageHeader(
    name: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = name,
            variant = TextVariant.H2,
        )
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        Text(
            text = description,
            variant = TextVariant.Muted,
        )
        Spacer(Modifier.height(RikkaTheme.spacing.lg))
        Separator()
    }
}

// ─── Section Header ──────────────────────────────────────────

/**
 * Section header within a component doc page.
 */
@Composable
fun DocSection(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Spacer(Modifier.height(RikkaTheme.spacing.xl))
        Text(
            text = title,
            variant = TextVariant.H4,
        )
        Spacer(Modifier.height(RikkaTheme.spacing.md))
        content()
    }
}

// ─── Demo Box ────────────────────────────────────────────────

/**
 * A bordered container for live component demos.
 *
 * Shows the component centered with a subtle border
 * and the theme's card background.
 */
@Composable
fun DemoBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    RikkaTheme.colors.border,
                    RikkaTheme.shapes.lg,
                )
                .background(
                    RikkaTheme.colors.card,
                    RikkaTheme.shapes.lg,
                )
                .clip(RikkaTheme.shapes.lg)
                .padding(RikkaTheme.spacing.xl),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

// ─── Variant Selector ────────────────────────────────────────

/**
 * A row of selectable chips for switching demo variants.
 *
 * @param options List of option labels.
 * @param selected Currently selected option label.
 * @param onSelect Called when an option is tapped.
 */
@Composable
fun VariantSelector(
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.xs),
    ) {
        options.forEach { option ->
            val isActive = option == selected
            val interactionSource =
                remember { MutableInteractionSource() }
            val isHovered by
                interactionSource.collectIsHoveredAsState()

            val bg =
                when {
                    isActive -> RikkaTheme.colors.primary
                    isHovered -> RikkaTheme.colors.muted
                    else -> RikkaTheme.colors.background
                }
            val fg =
                if (isActive) {
                    RikkaTheme.colors.primaryForeground
                } else {
                    RikkaTheme.colors.foreground
                }

            Box(
                modifier =
                    Modifier
                        .hoverable(interactionSource)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                        ) { onSelect(option) }
                        .border(
                            if (isActive) 0.dp else 1.dp,
                            if (isActive) {
                                RikkaTheme.colors.primary
                            } else {
                                RikkaTheme.colors.border
                            },
                            RikkaTheme.shapes.md,
                        )
                        .background(bg, RikkaTheme.shapes.md)
                        .padding(
                            horizontal = RikkaTheme.spacing.sm,
                            vertical = RikkaTheme.spacing.xs,
                        ),
            ) {
                BasicText(
                    text = option,
                    style =
                        RikkaTheme.typography.small.merge(
                            TextStyle(color = fg),
                        ),
                )
            }
        }
    }
}

// ─── Code Block ──────────────────────────────────────────────

/**
 * A styled code block with monospace font and dark background.
 *
 * Displays Kotlin source code for copy-paste.
 */
@Composable
fun CodeBlock(
    code: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .background(
                    RikkaTheme.colors.muted,
                    RikkaTheme.shapes.md,
                )
                .clip(RikkaTheme.shapes.md)
                .horizontalScroll(rememberScrollState())
                .padding(RikkaTheme.spacing.md),
    ) {
        BasicText(
            text = code,
            style =
                RikkaTheme.typography.small.merge(
                    TextStyle(
                        color = RikkaTheme.colors.foreground,
                        fontFamily = FontFamily.Monospace,
                    ),
                ),
        )
    }
}

// ─── Props Table ─────────────────────────────────────────────

/**
 * A single prop/parameter row.
 */
data class PropInfo(
    val name: String,
    val type: String,
    val default: String,
    val description: String,
)

/**
 * Renders a property reference table for a component's API.
 */
@Composable
fun PropsTable(
    props: List<PropInfo>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    RikkaTheme.colors.border,
                    RikkaTheme.shapes.md,
                )
                .clip(RikkaTheme.shapes.md),
    ) {
        // Header row
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(RikkaTheme.colors.muted)
                    .padding(
                        horizontal = RikkaTheme.spacing.md,
                        vertical = RikkaTheme.spacing.sm,
                    ),
        ) {
            PropHeaderCell(
                stringResource(Res.string.prop_header_prop),
                Modifier.weight(0.2f),
            )
            PropHeaderCell(
                stringResource(Res.string.prop_header_type),
                Modifier.weight(0.2f),
            )
            PropHeaderCell(
                stringResource(Res.string.prop_header_default),
                Modifier.weight(0.15f),
            )
            PropHeaderCell(
                stringResource(Res.string.prop_header_description),
                Modifier.weight(0.45f),
            )
        }

        // Data rows
        props.forEachIndexed { index, prop ->
            if (index > 0) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(RikkaTheme.colors.border),
                )
            }
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = RikkaTheme.spacing.md,
                            vertical = RikkaTheme.spacing.sm,
                        ),
            ) {
                PropCell(
                    prop.name,
                    Modifier.weight(0.2f),
                    isMono = true,
                )
                PropCell(
                    prop.type,
                    Modifier.weight(0.2f),
                    isMono = true,
                )
                PropCell(
                    prop.default,
                    Modifier.weight(0.15f),
                    isMono = true,
                )
                PropCell(
                    prop.description,
                    Modifier.weight(0.45f),
                )
            }
        }
    }
}

@Composable
private fun PropHeaderCell(
    text: String,
    modifier: Modifier = Modifier,
) {
    BasicText(
        text = text,
        modifier = modifier,
        style =
            RikkaTheme.typography.small.merge(
                TextStyle(
                    color = RikkaTheme.colors.foreground,
                    fontWeight = FontWeight.SemiBold,
                ),
            ),
    )
}

@Composable
private fun PropCell(
    text: String,
    modifier: Modifier = Modifier,
    isMono: Boolean = false,
) {
    BasicText(
        text = text,
        modifier = modifier,
        style =
            RikkaTheme.typography.small.merge(
                TextStyle(
                    color = RikkaTheme.colors.mutedForeground,
                    fontFamily =
                        if (isMono) {
                            FontFamily.Monospace
                        } else {
                            FontFamily.Default
                        },
                ),
            ),
    )
}
