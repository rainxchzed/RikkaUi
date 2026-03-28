package zed.rainxch.rikkaui.components.ui.tooltip

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.window.Popup
import zed.rainxch.rikkaui.components.theme.RikkaTheme

// ─── Component ──────────────────────────────────────────────

/**
 * Tooltip wrapper for the RikkaUi design system.
 *
 * Wraps any composable and shows a small popup label on hover.
 * The tooltip uses inverted theme colors (foreground as background,
 * background as text) for clear contrast. No Material dependency.
 *
 * Features:
 * - Fade-in/out animation using theme motion tokens
 * - Inverted color scheme for contrast on any theme
 * - Positioned above the content by default
 * - Hover-triggered via [MutableInteractionSource]
 *
 * Usage:
 * ```
 * Tooltip(tooltip = "Save changes") {
 *     Button("Save", onClick = { save() })
 * }
 *
 * Tooltip(tooltip = "Copy to clipboard") {
 *     IconButton(onClick = { copy() }) {
 *         Icon(Icons.Copy)
 *     }
 * }
 * ```
 *
 * @param tooltip The text to display inside the tooltip popup.
 * @param modifier Modifier applied to the outer wrapper.
 * @param content The composable content that triggers the tooltip on hover.
 */
@Composable
fun Tooltip(
    tooltip: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val motion = RikkaTheme.motion
    val colors = RikkaTheme.colors
    val shape = RikkaTheme.shapes.sm

    // ─── Fade animation (from theme motion tokens) ──────
    val alpha by animateFloatAsState(
        targetValue = if (isHovered) 1f else 0f,
        animationSpec = tween(motion.durationEnter),
    )

    // ─── Content measurement for popup placement ────────
    var contentSize by remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current

    Box(
        modifier =
            modifier
                .hoverable(interactionSource)
                .onGloballyPositioned { coordinates ->
                    contentSize = coordinates.size
                },
    ) {
        content()

        // ─── Tooltip popup ──────────────────────────────
        if (alpha > 0f) {
            Popup(
                alignment = Alignment.TopCenter,
                offset =
                    IntOffset(
                        x = 0,
                        y = with(density) { -(RikkaTheme.spacing.xs).roundToPx() },
                    ),
            ) {
                Box(
                    modifier =
                        Modifier
                            .graphicsLayer { this.alpha = alpha }
                            .background(colors.foreground, shape)
                            .clip(shape)
                            .padding(
                                horizontal = RikkaTheme.spacing.sm,
                                vertical = RikkaTheme.spacing.xs,
                            ),
                ) {
                    BasicText(
                        text = tooltip,
                        style =
                            RikkaTheme.typography.small.merge(
                                TextStyle(color = colors.background),
                            ),
                    )
                }
            }
        }
    }
}
