package zed.rainxch.rikkaui.components.ui.card

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Variant ────────────────────────────────────────────────

/**
 * - [Default] — Bordered card with card surface background.
 * - [Elevated] — Shadow, no border.
 * - [Ghost] — Transparent, no border or shadow.
 */
enum class CardVariant {
    Default,
    Elevated,
    Ghost,
}

// ─── Animation ──────────────────────────────────────────────

/**
 * - [Hover] — Lift and scale on hover.
 * - [Press] — Scale down on click.
 * - [None] — No animation.
 */
enum class CardAnimation {
    Hover,
    Press,
    None,
}

// ─── Component ──────────────────────────────────────────────

@Composable
fun Card(
    modifier: Modifier = Modifier,
    variant: CardVariant = CardVariant.Default,
    onClick: (() -> Unit)? = null,
    animation: CardAnimation = CardAnimation.Hover,
    elevation: Dp? = null,
    label: String = "",
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = RikkaTheme.colors
    val shape = RikkaTheme.shapes.lg
    val motion = RikkaTheme.motion

    val resolved = resolveCardStyle(variant)
    val effectiveElevation = elevation ?: resolved.elevation

    // ─── Interaction tracking ────────────────────────────
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()
    val isClickable = onClick != null
    val hasAnimation = isClickable && animation != CardAnimation.None

    // ─── Scale animation ─────────────────────────────────
    val targetScale =
        when {
            !hasAnimation -> 1f
            animation == CardAnimation.Hover && isHovered && !isPressed -> 1.02f
            animation == CardAnimation.Hover && isPressed -> motion.pressScaleSubtle
            animation == CardAnimation.Press && isPressed -> motion.pressScaleSubtle
            else -> 1f
        }

    val animSpec =
        when (animation) {
            CardAnimation.Hover -> motion.springDefault
            CardAnimation.Press -> motion.springSnap
            CardAnimation.None -> motion.springDefault
        }

    val scale by animateFloatAsState(
        targetValue = targetScale,
        animationSpec = animSpec,
    )

    // ─── Hover elevation lift ────────────────────────────
    val hoverElevationBoost =
        if (
            hasAnimation &&
            animation == CardAnimation.Hover &&
            isHovered
        ) {
            4.dp
        } else {
            0.dp
        }

    // ─── Modifiers ───────────────────────────────────────
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

    val totalElevation = effectiveElevation + hoverElevationBoost
    val shadowModifier =
        if (totalElevation > 0.dp) {
            Modifier.shadow(totalElevation, shape)
        } else {
            Modifier
        }

    val animationModifier =
        if (hasAnimation) {
            Modifier.graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
        } else {
            Modifier
        }

    // Cards group related content — merge descendants so screen readers
    // treat the card as a single navigable unit with all its content.
    val semanticsModifier =
        if (label.isNotEmpty()) {
            Modifier.semantics(mergeDescendants = true) {
                contentDescription = label
            }
        } else {
            Modifier.semantics(mergeDescendants = true) {}
        }

    val clickModifier =
        if (isClickable) {
            Modifier
                .hoverable(interactionSource)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    role = Role.Button,
                    onClick = onClick,
                )
        } else {
            Modifier
        }

    Column(
        modifier =
            modifier
                .then(semanticsModifier)
                .then(animationModifier)
                .then(shadowModifier)
                .then(borderModifier)
                .then(backgroundModifier)
                .clip(shape)
                .then(clickModifier)
                .padding(RikkaTheme.spacing.lg),
        verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
        content = content,
    )
}

// ─── Structured Sections ────────────────────────────────────

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
    val elevation: Dp,
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
