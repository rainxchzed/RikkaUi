package zed.rainxch.rikkaui.components.ui.topappbar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Variant ────────────────────────────────────────────────

/**
 * - [Default] — Solid background with bottom border.
 * - [Transparent] — No background, no border. For overlays.
 */
enum class TopAppBarVariant {
    Default,
    Transparent,
}

// ─── Size ───────────────────────────────────────────────────

/**
 * - [Small] — 56dp, compact default.
 * - [Medium] — 64dp, prominent heading.
 */
enum class TopAppBarSize {
    Small,
    Medium,
}

// ─── Color Transition Animation ─────────────────────────────

/**
 * - [Smooth] — Animated crossfade at default duration.
 * - [Snap] — Fast tween transition.
 * - [None] — Instant color change.
 */
enum class TopAppBarColorTransition {
    Smooth,
    Snap,
    None,
}

// ─── Component ──────────────────────────────────────────────

/**
 * A top app bar with navigation icon, title, and trailing action slots.
 *
 * This is the content-lambda overload that accepts a composable [title] lambda.
 * Renders a bottom border separator for the [TopAppBarVariant.Default] variant.
 * The title slot has heading semantics for accessibility.
 *
 * @param title Composable content for the title area.
 * @param modifier [Modifier] applied to the app bar container.
 * @param navigationIcon Composable slot for the leading navigation icon (e.g., back arrow). Defaults to empty.
 * @param actions Composable row slot for trailing action icons. Defaults to empty.
 * @param variant [TopAppBarVariant] controlling background and border styling. Defaults to [TopAppBarVariant.Default].
 * @param size [TopAppBarSize] controlling the bar height and title text variant. Defaults to [TopAppBarSize.Small].
 * @param centerTitle Whether to center the title. Defaults to false (start-aligned).
 * @param elevation Shadow elevation of the app bar. Defaults to 0.dp.
 * @param colorTransition [TopAppBarColorTransition] controlling how background color changes animate. Defaults to [TopAppBarColorTransition.None].
 */
@Composable
fun TopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    variant: TopAppBarVariant = TopAppBarVariant.Default,
    size: TopAppBarSize = TopAppBarSize.Small,
    centerTitle: Boolean = false,
    elevation: Dp = 0.dp,
    colorTransition: TopAppBarColorTransition = TopAppBarColorTransition.None,
) {
    val colors = resolveColors(variant)
    val sizeValues = resolveSizeValues(size)
    val spacing = RikkaTheme.spacing
    val motion = RikkaTheme.motion

    val resolvedBackground =
        resolveAnimatedBackground(
            targetColor = colors.background,
            colorTransition = colorTransition,
            durationDefault = motion.durationDefault,
            durationFast = motion.durationFast,
        )

    val backgroundModifier =
        if (colors.background != Color.Transparent) {
            Modifier.background(resolvedBackground)
        } else {
            Modifier
        }

    val shadowModifier =
        if (elevation > 0.dp) {
            Modifier.shadow(elevation)
        } else {
            Modifier
        }

    // ─── Bar layout ──────────────────────────────────────
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .semantics { isTraversalGroup = true }
                .then(shadowModifier)
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
                contentAlignment =
                    if (centerTitle) {
                        Alignment.Center
                    } else {
                        Alignment.CenterStart
                    },
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
 * Convenience top app bar that accepts a [String] title directly.
 *
 * Renders the title as a [Text] composable with size-appropriate variant and foreground color.
 * Delegates to the content-lambda overload of [TopAppBar].
 *
 * @param title String title text displayed in the app bar.
 * @param modifier [Modifier] applied to the app bar container.
 * @param navigationIcon Composable slot for the leading navigation icon. Defaults to empty.
 * @param actions Composable row slot for trailing action icons. Defaults to empty.
 * @param variant [TopAppBarVariant] controlling background and border styling. Defaults to [TopAppBarVariant.Default].
 * @param size [TopAppBarSize] controlling the bar height and title text variant. Defaults to [TopAppBarSize.Small].
 * @param centerTitle Whether to center the title. Defaults to false (start-aligned).
 * @param elevation Shadow elevation of the app bar. Defaults to 0.dp.
 * @param colorTransition [TopAppBarColorTransition] controlling how background color changes animate. Defaults to [TopAppBarColorTransition.None].
 */
@Composable
fun TopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    variant: TopAppBarVariant = TopAppBarVariant.Default,
    size: TopAppBarSize = TopAppBarSize.Small,
    centerTitle: Boolean = false,
    elevation: Dp = 0.dp,
    colorTransition: TopAppBarColorTransition = TopAppBarColorTransition.None,
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
        centerTitle = centerTitle,
        elevation = elevation,
        colorTransition = colorTransition,
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
                foreground = colors.onBackground,
                hasBorder = true,
            )
        }

        TopAppBarVariant.Transparent -> {
            TopAppBarColors(
                background = Color.Transparent,
                foreground = colors.onBackground,
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

// ─── Internal: Animated Background Resolution ───────────────

@Composable
private fun resolveAnimatedBackground(
    targetColor: Color,
    colorTransition: TopAppBarColorTransition,
    durationDefault: Int,
    durationFast: Int,
): Color =
    when (colorTransition) {
        TopAppBarColorTransition.None -> {
            targetColor
        }

        TopAppBarColorTransition.Smooth -> {
            val animated by animateColorAsState(
                targetValue = targetColor,
                animationSpec = tween(durationDefault),
            )
            animated
        }

        TopAppBarColorTransition.Snap -> {
            val animated by animateColorAsState(
                targetValue = targetColor,
                animationSpec = tween(durationFast),
            )
            animated
        }
    }
