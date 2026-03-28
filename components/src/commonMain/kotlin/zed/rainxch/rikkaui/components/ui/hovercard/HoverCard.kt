package zed.rainxch.rikkaui.components.ui.hovercard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import kotlinx.coroutines.delay
import zed.rainxch.rikkaui.components.theme.RikkaTheme

// ─── Constants ──────────────────────────────────────────────

/** Delay before showing the hover card after the trigger is hovered. */
private const val SHOW_DELAY_MS = 300L

/** Delay before hiding the hover card after the cursor leaves. */
private const val HIDE_DELAY_MS = 200L

// ─── Component ──────────────────────────────────────────────

/**
 * Hover card component for the RikkaUi design system.
 *
 * Shows a floating card when the user hovers over the trigger element.
 * The card appears after a short delay and stays visible while the
 * cursor is over the trigger or the card itself. Maps to shadcn/ui's
 * Hover Card.
 *
 * Features:
 * - Hover-triggered with show/hide delays for smooth UX
 * - Card stays visible while cursor moves from trigger to card
 * - Theme-aware styling with border and shadow
 * - No Material3 dependency
 *
 * Usage:
 * ```
 * HoverCard(
 *     trigger = {
 *         Text("@rikka")
 *     },
 * ) {
 *     Column {
 *         Text("RikkaUi", variant = TextVariant.Large)
 *         Text("A shadcn-inspired component library for Compose.")
 *     }
 * }
 * ```
 *
 * @param modifier Modifier applied to the outer wrapper.
 * @param trigger The composable that triggers the hover card on hover.
 * @param content The composable content displayed inside the floating card.
 */
@Composable
fun HoverCard(
    modifier: Modifier = Modifier,
    trigger: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val triggerInteraction = remember { MutableInteractionSource() }
    val isTriggerHovered by triggerInteraction.collectIsHoveredAsState()

    val cardInteraction = remember { MutableInteractionSource() }
    val isCardHovered by cardInteraction.collectIsHoveredAsState()

    var isVisible by remember { mutableStateOf(false) }

    val colors = RikkaTheme.colors
    val shapes = RikkaTheme.shapes
    val spacing = RikkaTheme.spacing

    // ─── Show/hide with delays ───────────────────────────
    val isAnyHovered = isTriggerHovered || isCardHovered

    LaunchedEffect(isAnyHovered) {
        if (isAnyHovered) {
            delay(SHOW_DELAY_MS)
            isVisible = true
        } else {
            delay(HIDE_DELAY_MS)
            isVisible = false
        }
    }

    Box(modifier = modifier) {
        Box(modifier = Modifier.hoverable(triggerInteraction)) {
            trigger()
        }

        if (isVisible) {
            Popup(
                alignment = Alignment.BottomStart,
            ) {
                Box(
                    modifier =
                        Modifier
                            .hoverable(cardInteraction)
                            .defaultMinSize(minWidth = 250.dp)
                            .widthIn(max = 360.dp)
                            .shadow(8.dp, shapes.md)
                            .border(
                                1.dp,
                                colors.border,
                                shapes.md,
                            )
                            .background(
                                colors.popover,
                                shapes.md,
                            )
                            .clip(shapes.md)
                            .padding(spacing.lg),
                ) {
                    content()
                }
            }
        }
    }
}
