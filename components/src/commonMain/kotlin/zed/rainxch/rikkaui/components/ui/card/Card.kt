package zed.rainxch.rikkaui.components.ui.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme

// ─── Variant ────────────────────────────────────────────────

/**
 * Card visual variants.
 *
 * - [Default] — Bordered card with card surface background. Most common.
 * - [Elevated] — Subtle shadow, no border. Layered feel.
 * - [Ghost] — No border, no shadow. Transparent background. Groups content without visual weight.
 */
enum class CardVariant {
    Default,
    Elevated,
    Ghost,
}

// ─── Component ──────────────────────────────────────────────

/**
 * Card component for the RikkaUi design system.
 *
 * A container that groups related content with visual separation.
 * Replaces Material3's Card with Rikka theme tokens.
 *
 * Usage:
 * ```
 * Card {
 *     Text("Card title", variant = TextVariant.H4)
 *     Text("Card description", variant = TextVariant.Muted)
 * }
 *
 * Card(variant = CardVariant.Elevated) {
 *     Text("Elevated card")
 * }
 * ```
 *
 * @param modifier Modifier for layout and decoration.
 * @param variant Visual variant — controls background, border, and shadow.
 * @param content Card content — use [CardHeader], [CardContent], [CardFooter] for structured layout.
 */
@Composable
fun Card(
    modifier: Modifier = Modifier,
    variant: CardVariant = CardVariant.Default,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = RikkaTheme.colors
    val shape = RikkaTheme.shapes.lg

    val resolved = resolveCardStyle(variant)

    val backgroundModifier =
        if (resolved.background != Color.Transparent) {
            Modifier.background(resolved.background, shape)
        } else {
            Modifier
        }

    val borderModifier =
        if (resolved.border != Color.Transparent) {
            Modifier.border(1.dp, resolved.border, shape)
        } else {
            Modifier
        }

    val shadowModifier =
        if (resolved.elevation > 0.dp) {
            Modifier.shadow(resolved.elevation, shape)
        } else {
            Modifier
        }

    Column(
        modifier =
            modifier
                .then(shadowModifier)
                .then(borderModifier)
                .then(backgroundModifier)
                .clip(shape)
                .padding(RikkaTheme.spacing.lg),
        verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
        content = content,
    )
}

// ─── Structured Sections ────────────────────────────────────

/**
 * Header section of a Card. Typically contains title and description.
 *
 * ```
 * Card {
 *     CardHeader {
 *         Text("Title", variant = TextVariant.H4)
 *         Text("Description", variant = TextVariant.Muted)
 *     }
 *     CardContent { ... }
 * }
 * ```
 */
@Composable
fun CardHeader(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
        content = content,
    )
}

/**
 * Main content section of a Card.
 */
@Composable
fun CardContent(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.padding(top = RikkaTheme.spacing.sm),
        content = content,
    )
}

/**
 * Footer section of a Card. Typically contains action buttons.
 */
@Composable
fun CardFooter(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.padding(top = RikkaTheme.spacing.sm),
        content = content,
    )
}

// ─── Internal: Style Resolution ─────────────────────────────

private data class CardStyle(
    val background: Color,
    val border: Color,
    val elevation: androidx.compose.ui.unit.Dp,
)

@Composable
private fun resolveCardStyle(variant: CardVariant): CardStyle {
    val colors = RikkaTheme.colors

    return when (variant) {
        CardVariant.Default -> {
            CardStyle(
                background = colors.card,
                border = colors.border,
                elevation = 0.dp,
            )
        }

        CardVariant.Elevated -> {
            CardStyle(
                background = colors.card,
                border = Color.Transparent,
                elevation = 4.dp,
            )
        }

        CardVariant.Ghost -> {
            CardStyle(
                background = Color.Transparent,
                border = Color.Transparent,
                elevation = 0.dp,
            )
        }
    }
}
