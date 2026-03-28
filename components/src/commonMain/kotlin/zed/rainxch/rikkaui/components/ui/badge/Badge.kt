package zed.rainxch.rikkaui.components.ui.badge

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.text.Text

// ─── Variant ────────────────────────────────────────────────

/**
 * Badge visual variants, matching shadcn/ui's badge variants.
 *
 * - [Default] — Solid primary background. Standard status indicator.
 * - [Secondary] — Muted background. Less prominent.
 * - [Outline] — Bordered, transparent background. Subtle tag.
 * - [Destructive] — Red tint. Error or critical status.
 */
enum class BadgeVariant {
    Default,
    Secondary,
    Outline,
    Destructive,
}

// ─── Component ──────────────────────────────────────────────

/**
 * Badge component for the RikkaUi design system.
 *
 * A small status indicator or label. Replaces Material3 chips/badges
 * with Rikka theme tokens.
 *
 * Usage:
 * ```
 * Badge("New")
 * Badge("Draft", variant = BadgeVariant.Secondary)
 * Badge("Error", variant = BadgeVariant.Destructive)
 * Badge("v1.0", variant = BadgeVariant.Outline)
 * ```
 *
 * @param text The label text.
 * @param modifier Modifier for layout and decoration.
 * @param variant Visual variant — controls colors and border.
 */
@Composable
fun Badge(
    text: String,
    modifier: Modifier = Modifier,
    variant: BadgeVariant = BadgeVariant.Default,
) {
    val resolved = resolveColors(variant)
    val shape = RikkaTheme.shapes.full

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

    Box(
        modifier =
            modifier
                .then(borderModifier)
                .then(backgroundModifier)
                .clip(shape)
                .padding(
                    horizontal = RikkaTheme.spacing.sm,
                    vertical = 2.dp,
                ),
    ) {
        Text(
            text = text,
            color = resolved.foreground,
            style =
                RikkaTheme.typography.small.merge(
                    TextStyle(
                        fontSize = RikkaTheme.typography.small.fontSize,
                        lineHeight = RikkaTheme.typography.small.lineHeight,
                    ),
                ),
        )
    }
}

/**
 * Badge with custom content (icon + text, etc).
 *
 * ```
 * Badge(variant = BadgeVariant.Outline) {
 *     Icon(...)
 *     Text("Tagged")
 * }
 * ```
 */
@Composable
fun Badge(
    modifier: Modifier = Modifier,
    variant: BadgeVariant = BadgeVariant.Default,
    content: @Composable () -> Unit,
) {
    val resolved = resolveColors(variant)
    val shape = RikkaTheme.shapes.full

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

    Box(
        modifier =
            modifier
                .then(borderModifier)
                .then(backgroundModifier)
                .clip(shape)
                .padding(
                    horizontal = RikkaTheme.spacing.sm,
                    vertical = 2.dp,
                ),
    ) {
        content()
    }
}

// ─── Internal: Color Resolution ─────────────────────────────

private data class BadgeColors(
    val background: Color,
    val foreground: Color,
    val border: Color,
)

@Composable
private fun resolveColors(variant: BadgeVariant): BadgeColors {
    val colors = RikkaTheme.colors

    return when (variant) {
        BadgeVariant.Default -> {
            BadgeColors(
                background = colors.primary,
                foreground = colors.primaryForeground,
                border = Color.Transparent,
            )
        }

        BadgeVariant.Secondary -> {
            BadgeColors(
                background = colors.secondary,
                foreground = colors.secondaryForeground,
                border = Color.Transparent,
            )
        }

        BadgeVariant.Outline -> {
            BadgeColors(
                background = Color.Transparent,
                foreground = colors.foreground,
                border = colors.border,
            )
        }

        BadgeVariant.Destructive -> {
            BadgeColors(
                background = colors.destructive,
                foreground = colors.destructiveForeground,
                border = Color.Transparent,
            )
        }
    }
}
