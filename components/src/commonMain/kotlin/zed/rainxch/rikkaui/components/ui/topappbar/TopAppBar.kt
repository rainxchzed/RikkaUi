package zed.rainxch.rikkaui.components.ui.topappbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

// ─── Variant ────────────────────────────────────────────────

/**
 * Top app bar visual variants.
 *
 * - [Default] — Solid background with a subtle bottom border. Standard use case.
 * - [Transparent] — No background, no border. For overlay on hero images or gradients.
 */
enum class TopAppBarVariant {
    Default,
    Transparent,
}

// ─── Size ───────────────────────────────────────────────────

/**
 * Top app bar sizes controlling the bar height and title typography.
 *
 * - [Small] — 56dp height with [TextVariant.Large] title. Compact, default choice.
 * - [Medium] — 64dp height with [TextVariant.H4] title. Spacious, prominent heading.
 */
enum class TopAppBarSize {
    Small,
    Medium,
}

// ─── Component ──────────────────────────────────────────────

/**
 * Top app bar component for the RikkaUi design system.
 *
 * A shadcn/ui-inspired header bar — flat, modern, no elevation.
 * Uses Rikka theme tokens for styling and requires no Material dependency.
 * Works on Android, iOS, Desktop, and Web via Compose Multiplatform.
 *
 * Features:
 * - Two variants: solid background or transparent overlay
 * - Two sizes with appropriate title typography
 * - Navigation icon slot (leading) and actions slot (trailing)
 * - Left-aligned title with heading accessibility semantics
 * - Subtle bottom border on Default variant (shadcn style)
 *
 * Usage:
 * ```
 * TopAppBar(
 *     title = { Text("Dashboard") },
 * )
 *
 * // With navigation and actions
 * TopAppBar(
 *     title = { Text("Settings") },
 *     navigationIcon = {
 *         Button(
 *             onClick = { navigateBack() },
 *             variant = ButtonVariant.Ghost,
 *             size = ButtonSize.Icon,
 *         ) {
 *             Icon(RikkaIcons.ArrowLeft)
 *         }
 *     },
 *     actions = {
 *         Button(
 *             onClick = { },
 *             variant = ButtonVariant.Ghost,
 *             size = ButtonSize.Icon,
 *         ) {
 *             Icon(RikkaIcons.Settings)
 *         }
 *     },
 * )
 *
 * // Transparent overlay on hero
 * TopAppBar(
 *     title = { Text("Gallery", color = Color.White) },
 *     variant = TopAppBarVariant.Transparent,
 *     size = TopAppBarSize.Medium,
 * )
 * ```
 *
 * @param title Title content slot. Rendered with heading semantics.
 * @param modifier Modifier for layout and decoration.
 * @param navigationIcon Leading icon slot — typically a back arrow button.
 *   The slot is given a 48dp minimum touch target.
 * @param actions Trailing actions slot — a [RowScope] for icon buttons.
 * @param variant Visual variant — controls background and border.
 * @param size Controls the bar height and default title typography.
 */
@Composable
fun TopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    variant: TopAppBarVariant = TopAppBarVariant.Default,
    size: TopAppBarSize = TopAppBarSize.Small,
) {
    val colors = resolveColors(variant)
    val sizeValues = resolveSizeValues(size)
    val spacing = RikkaTheme.spacing

    val backgroundModifier =
        if (colors.background != Color.Transparent) {
            Modifier.background(colors.background)
        } else {
            Modifier
        }

    // ─── Bar layout ──────────────────────────────────────
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .then(backgroundModifier),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(sizeValues.height)
                    .padding(horizontal = spacing.md),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // ─── Navigation icon ─────────────────────────
            Box(
                modifier =
                    Modifier
                        .defaultMinSize(
                            minWidth = 48.dp,
                            minHeight = 48.dp,
                        ),
                contentAlignment = Alignment.Center,
            ) {
                navigationIcon()
            }

            // ─── Title ──────────────────────────────────
            Box(
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = spacing.sm)
                        .semantics { heading() },
                contentAlignment = Alignment.CenterStart,
            ) {
                title()
            }

            // ─── Actions ────────────────────────────────
            Row(
                horizontalArrangement = Arrangement.spacedBy(spacing.sm),
                verticalAlignment = Alignment.CenterVertically,
                content = actions,
            )
        }

        // ─── Bottom border (Default variant only) ────────
        if (colors.hasBorder) {
            Separator(
                modifier =
                    Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth(),
            )
        }
    }
}

/**
 * Convenience overload with a text title.
 *
 * The title is rendered using [TextVariant.Large] for [TopAppBarSize.Small]
 * and [TextVariant.H4] for [TopAppBarSize.Medium], with the theme's
 * foreground color (or transparent-safe foreground for overlays).
 *
 * ```
 * TopAppBar(title = "Dashboard")
 *
 * TopAppBar(
 *     title = "Settings",
 *     navigationIcon = { ... },
 *     size = TopAppBarSize.Medium,
 * )
 * ```
 *
 * @param title Title text string.
 * @param modifier Modifier for layout and decoration.
 * @param navigationIcon Leading icon slot — typically a back arrow button.
 * @param actions Trailing actions slot — a [RowScope] for icon buttons.
 * @param variant Visual variant — controls background and border.
 * @param size Controls the bar height and title typography.
 */
@Composable
fun TopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    variant: TopAppBarVariant = TopAppBarVariant.Default,
    size: TopAppBarSize = TopAppBarSize.Small,
) {
    val sizeValues = resolveSizeValues(size)
    val colors = resolveColors(variant)

    TopAppBar(
        title = {
            Text(
                text = title,
                variant = sizeValues.titleVariant,
                color = colors.foreground,
            )
        },
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        variant = variant,
        size = size,
    )
}

// ─── Internal: Color Resolution ─────────────────────────────

private data class TopAppBarColors(
    val background: Color,
    val foreground: Color,
    val hasBorder: Boolean,
)

@Composable
private fun resolveColors(variant: TopAppBarVariant): TopAppBarColors {
    val colors = RikkaTheme.colors

    return when (variant) {
        TopAppBarVariant.Default -> {
            TopAppBarColors(
                background = colors.background,
                foreground = colors.foreground,
                hasBorder = true,
            )
        }

        TopAppBarVariant.Transparent -> {
            TopAppBarColors(
                background = Color.Transparent,
                foreground = colors.foreground,
                hasBorder = false,
            )
        }
    }
}

// ─── Internal: Size Resolution ──────────────────────────────

private data class TopAppBarSizeValues(
    val height: Dp,
    val titleVariant: TextVariant,
)

@Composable
private fun resolveSizeValues(size: TopAppBarSize): TopAppBarSizeValues =
    when (size) {
        TopAppBarSize.Small -> {
            TopAppBarSizeValues(
                height = 56.dp,
                titleVariant = TextVariant.Large,
            )
        }

        TopAppBarSize.Medium -> {
            TopAppBarSizeValues(
                height = 64.dp,
                titleVariant = TextVariant.H4,
            )
        }
    }
