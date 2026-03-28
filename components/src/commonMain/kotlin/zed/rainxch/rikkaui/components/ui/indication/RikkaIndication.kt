package zed.rainxch.rikkaui.components.ui.indication

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.invalidateDraw
import kotlinx.coroutines.launch

// ─── Factory ────────────────────────────────────────────────

/**
 * RikkaIndication — design-system-wide visual feedback for interactive components.
 *
 * Unlike Material's ripple (which creates expanding circles), Rikka uses
 * a **subtle overlay + scale** approach that feels modern and lightweight.
 *
 * This replaces per-component hover/press color logic with a single, consistent
 * feedback system that works across all platforms (Android, iOS, Desktop, Web).
 *
 * Features:
 * - Hover: subtle background overlay (alpha 0.04)
 * - Press: slightly stronger overlay (alpha 0.08)
 * - Focus: visible ring overlay for keyboard navigation
 * - Spring physics for smooth, interruptible transitions
 * - Runs in draw phase only — zero composition/layout overhead
 *
 * Usage:
 * ```
 * // Applied automatically when you use RikkaIndication as the indication:
 * Box(
 *     modifier = Modifier.clickable(
 *         interactionSource = remember { MutableInteractionSource() },
 *         indication = RikkaIndication,
 *         onClick = { },
 *     )
 * )
 *
 * // Or use the themed variant that picks up the current foreground color:
 * Box(
 *     modifier = Modifier.clickable(
 *         interactionSource = remember { MutableInteractionSource() },
 *         indication = RikkaIndication(overlayColor = Color.White),
 *         onClick = { },
 *     )
 * )
 * ```
 */
object RikkaIndication : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode =
        RikkaIndicationNode(interactionSource, Color.Unspecified)

    override fun hashCode(): Int = -1

    override fun equals(other: Any?): Boolean = other === this
}

/**
 * Creates a [RikkaIndication] with a custom overlay color.
 *
 * @param overlayColor The color used for hover/press/focus overlays.
 *   If [Color.Unspecified], uses white (suitable for dark surfaces) or
 *   black (suitable for light surfaces) based on luminance.
 * @param cornerRadius Corner radius for the overlay. Defaults to 0 (fills the shape of the parent clip).
 */
fun RikkaIndication(
    overlayColor: Color = Color.Unspecified,
    cornerRadius: CornerRadius = CornerRadius.Zero,
): IndicationNodeFactory =
    RikkaIndicationFactory(overlayColor, cornerRadius)

private class RikkaIndicationFactory(
    private val overlayColor: Color,
    private val cornerRadius: CornerRadius,
) : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode =
        RikkaIndicationNode(interactionSource, overlayColor, cornerRadius)

    override fun hashCode(): Int = overlayColor.hashCode() * 31 + cornerRadius.hashCode()

    override fun equals(other: Any?): Boolean =
        other is RikkaIndicationFactory &&
            other.overlayColor == overlayColor &&
            other.cornerRadius == cornerRadius
}

// ─── Node ───────────────────────────────────────────────────

private class RikkaIndicationNode(
    private val interactionSource: InteractionSource,
    private val overlayColor: Color,
    private val cornerRadius: CornerRadius = CornerRadius.Zero,
) : Modifier.Node(),
    DrawModifierNode {
    private val hoverAlpha = Animatable(0f)
    private val pressAlpha = Animatable(0f)
    private val focusAlpha = Animatable(0f)

    override fun onAttach() {
        coroutineScope.launch {
            interactionSource.interactions.collect { interaction ->
                when (interaction) {
                    is HoverInteraction.Enter -> {
                        launch { hoverAlpha.animateTo(1f, tween(150)) }
                    }

                    is HoverInteraction.Exit -> {
                        launch { hoverAlpha.animateTo(0f, tween(150)) }
                    }

                    is PressInteraction.Press -> {
                        launch {
                            pressAlpha.animateTo(
                                1f,
                                spring(
                                    dampingRatio = Spring.DampingRatioNoBouncy,
                                    stiffness = Spring.StiffnessMediumLow,
                                ),
                            )
                        }
                    }

                    is PressInteraction.Release,
                    is PressInteraction.Cancel,
                    -> {
                        launch { pressAlpha.animateTo(0f, tween(200)) }
                    }

                    is FocusInteraction.Focus -> {
                        launch { focusAlpha.animateTo(1f, tween(150)) }
                    }

                    is FocusInteraction.Unfocus -> {
                        launch { focusAlpha.animateTo(0f, tween(150)) }
                    }
                }
            }
        }
    }

    override fun ContentDrawScope.draw() {
        drawContent()

        val color =
            if (overlayColor != Color.Unspecified) {
                overlayColor
            } else {
                Color.White
            }

        // Hover overlay
        val currentHoverAlpha = hoverAlpha.value
        if (currentHoverAlpha > 0f) {
            drawRoundRect(
                color = color.copy(alpha = 0.04f * currentHoverAlpha),
                cornerRadius = cornerRadius,
            )
        }

        // Press overlay
        val currentPressAlpha = pressAlpha.value
        if (currentPressAlpha > 0f) {
            drawRoundRect(
                color = color.copy(alpha = 0.08f * currentPressAlpha),
                cornerRadius = cornerRadius,
            )
        }

        // Focus ring (stronger for accessibility)
        val currentFocusAlpha = focusAlpha.value
        if (currentFocusAlpha > 0f) {
            drawRoundRect(
                color = color.copy(alpha = 0.06f * currentFocusAlpha),
                cornerRadius = cornerRadius,
            )
        }

        // Trigger invalidation when animating
        if (currentHoverAlpha > 0f || currentPressAlpha > 0f || currentFocusAlpha > 0f) {
            invalidateDraw()
        }
    }
}
