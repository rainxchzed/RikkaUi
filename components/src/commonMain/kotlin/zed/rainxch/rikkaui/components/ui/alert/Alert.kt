package zed.rainxch.rikkaui.components.ui.alert

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

// ─── Variant ────────────────────────────────────────────────

/**
 * Alert visual variants, matching shadcn/ui's alert variants.
 *
 * - [Default] — Bordered card background with foreground text. Informational alerts.
 * - [Destructive] — Destructive border tint with destructive foreground. Errors or critical warnings.
 */
enum class AlertVariant {
    Default,
    Destructive,
}

// ─── Component ──────────────────────────────────────────────

/**
 * Alert component for the RikkaUi design system.
 *
 * A status container that displays important messages to the user.
 * Replaces Material3's snackbar/alert patterns with Rikka theme tokens.
 *
 * Usage:
 * ```
 * Alert {
 *     AlertTitle("Heads up!")
 *     AlertDescription("You can add components to your app using the CLI.")
 * }
 *
 * Alert(variant = AlertVariant.Destructive) {
 *     AlertTitle("Error")
 *     AlertDescription("Your session has expired. Please log in again.")
 * }
 * ```
 *
 * @param modifier Modifier for layout and decoration.
 * @param variant Visual variant — controls border color and text styling.
 * @param label Accessibility label for screen readers. Describes the alert's purpose.
 * @param content Alert content — use [AlertTitle] and [AlertDescription] for structured layout.
 */
@Composable
fun Alert(
    modifier: Modifier = Modifier,
    variant: AlertVariant = AlertVariant.Default,
    label: String = "",
    content: @Composable ColumnScope.() -> Unit,
) {
    val resolved = resolveColors(variant)
    val shape = RikkaTheme.shapes.md

    val semanticsModifier =
        if (label.isNotEmpty()) {
            Modifier.semantics(mergeDescendants = true) {
                contentDescription = label
            }
        } else {
            Modifier.semantics(mergeDescendants = true) {}
        }

    Column(
        modifier =
            modifier
                .then(semanticsModifier)
                .border(1.dp, resolved.border, shape)
                .background(resolved.background, shape)
                .clip(shape)
                .padding(RikkaTheme.spacing.lg),
        content = content,
    )
}

// ─── Structured Sections ────────────────────────────────────

/**
 * Title section for an [Alert]. Renders as an H4 heading.
 *
 * ```
 * Alert {
 *     AlertTitle("Heads up!")
 *     AlertDescription("Description text here.")
 * }
 * ```
 *
 * @param text The title text.
 * @param modifier Modifier for layout and decoration.
 */
@Composable
fun AlertTitle(
    text: String,
    modifier: Modifier = Modifier,
    variant: AlertVariant = AlertVariant.Default,
) {
    val color = when (variant) {
        AlertVariant.Default -> RikkaTheme.colors.foreground
        AlertVariant.Destructive -> RikkaTheme.colors.destructive
    }
    Text(
        text = text,
        modifier = modifier,
        variant = TextVariant.H4,
        color = color,
    )
}

/**
 * Description section for an [Alert]. Renders as body text with color
 * matching the alert variant.
 *
 * - In [AlertVariant.Default], text is muted foreground.
 * - In [AlertVariant.Destructive], text is destructive foreground.
 *
 * ```
 * Alert(variant = AlertVariant.Destructive) {
 *     AlertTitle("Error")
 *     AlertDescription("Something went wrong.")
 * }
 * ```
 *
 * @param text The description text.
 * @param modifier Modifier for layout and decoration.
 * @param variant The parent alert variant — controls text color.
 */
@Composable
fun AlertDescription(
    text: String,
    modifier: Modifier = Modifier,
    variant: AlertVariant = AlertVariant.Default,
) {
    val color = resolveDescriptionColor(variant)

    Text(
        text = text,
        modifier = modifier,
        variant = TextVariant.P,
        color = color,
    )
}

// ─── Internal: Color Resolution ─────────────────────────────

private data class AlertColors(
    val background: Color,
    val border: Color,
)

@Composable
private fun resolveColors(variant: AlertVariant): AlertColors {
    val colors = RikkaTheme.colors

    return when (variant) {
        AlertVariant.Default -> {
            AlertColors(
                background = colors.card,
                border = colors.border,
            )
        }

        AlertVariant.Destructive -> {
            AlertColors(
                background = colors.destructive.copy(alpha = 0.1f),
                border = colors.destructive.copy(alpha = 0.3f),
            )
        }
    }
}

@Composable
private fun resolveDescriptionColor(variant: AlertVariant): Color {
    val colors = RikkaTheme.colors

    return when (variant) {
        AlertVariant.Default -> colors.mutedForeground
        AlertVariant.Destructive -> colors.destructive
    }
}
