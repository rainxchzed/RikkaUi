package zed.rainxch.rikkaui.components.ui.sheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.dismiss
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

// ─── Side ───────────────────────────────────────────────────

/**
 * The side from which the sheet slides in.
 *
 * - [Top] — Slides down from the top edge.
 * - [Bottom] — Slides up from the bottom edge.
 * - [Left] — Slides in from the left edge.
 * - [Right] — Slides in from the right edge (default).
 */
enum class SheetSide {
    Top,
    Bottom,
    Left,
    Right,
}

// ─── Component ──────────────────────────────────────────────

/**
 * Sheet (side/bottom panel) overlay for the RikkaUi design system.
 *
 * Displays a panel that slides in from any edge of the screen
 * on top of a semi-transparent scrim. Clicking the scrim dismisses
 * the sheet. Uses slide + fade animations from [RikkaTheme.motion].
 *
 * Usage:
 * ```
 * var open by remember { mutableStateOf(false) }
 *
 * Button("Open Sheet", onClick = { open = true })
 *
 * Sheet(open = open, onDismiss = { open = false }) {
 *     SheetHeader(
 *         title = "Settings",
 *         description = "Adjust your preferences.",
 *     )
 *     SheetContent {
 *         Text("Sheet body content here.")
 *     }
 *     SheetFooter {
 *         Button("Close", onClick = { open = false })
 *     }
 * }
 *
 * // Bottom sheet
 * Sheet(
 *     open = open,
 *     onDismiss = { open = false },
 *     side = SheetSide.Bottom,
 * ) {
 *     SheetHeader(title = "Options")
 *     SheetContent { Text("Pick an option") }
 * }
 * ```
 *
 * @param open Whether the sheet is visible.
 * @param onDismiss Called when the user dismisses the sheet (scrim click or back gesture).
 * @param side The edge from which the sheet slides in. Defaults to [SheetSide.Right].
 * @param modifier Modifier applied to the sheet panel.
 * @param label Accessibility label describing the sheet's purpose.
 * @param content Sheet content — use [SheetHeader], [SheetContent], [SheetFooter] for structure.
 */
@Composable
fun Sheet(
    open: Boolean,
    onDismiss: () -> Unit,
    side: SheetSide = SheetSide.Right,
    modifier: Modifier = Modifier,
    label: String = "Sheet",
    content: @Composable ColumnScope.() -> Unit,
) {
    if (!open) return

    val colors = RikkaTheme.colors
    val spacing = RikkaTheme.spacing
    val motion = RikkaTheme.motion

    val panelShape = resolvePanelShape(side)
    val panelAlignment = resolvePanelAlignment(side)
    val enterTransition = resolveEnterTransition(side, motion.durationEnter)
    val exitTransition = resolveExitTransition(side, motion.durationEnter)

    val borderModifier = resolveBorderModifier(side, colors.border)

    Popup(
        onDismissRequest = onDismiss,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = panelAlignment,
        ) {
            // ─── Scrim ───────────────────────────────────
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(motion.durationEnter)),
                exit = fadeOut(animationSpec = tween(motion.durationEnter)),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = onDismiss,
                        ),
                )
            }

            // ─── Sheet panel ─────────────────────────────
            AnimatedVisibility(
                visible = true,
                enter = enterTransition,
                exit = exitTransition,
            ) {
                val sizeModifier = when (side) {
                    SheetSide.Left, SheetSide.Right ->
                        Modifier
                            .width(320.dp)
                            .fillMaxHeight()

                    SheetSide.Top, SheetSide.Bottom ->
                        Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 200.dp)
                }

                Column(
                    modifier = modifier
                        .then(sizeModifier)
                        .semantics(mergeDescendants = true) {
                            paneTitle = label
                            dismiss { onDismiss(); true }
                        }
                        .then(borderModifier)
                        .background(colors.card, panelShape)
                        .clip(panelShape)
                        .padding(spacing.xl),
                    verticalArrangement = Arrangement.spacedBy(spacing.md),
                    content = content,
                )
            }
        }
    }
}

// ─── Structured Sections ────────────────────────────────────

/**
 * Header section for a [Sheet]. Contains a title and optional description.
 *
 * ```
 * SheetHeader(
 *     title = "Navigation",
 *     description = "Browse sections of the app.",
 * )
 * ```
 *
 * @param title The sheet title, rendered as H3.
 * @param description Optional description text, rendered as Muted.
 * @param modifier Modifier for the header column.
 */
@Composable
fun SheetHeader(
    title: String,
    modifier: Modifier = Modifier,
    description: String = "",
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
    ) {
        Text(
            text = title,
            variant = TextVariant.H3,
        )
        if (description.isNotEmpty()) {
            Text(
                text = description,
                variant = TextVariant.Muted,
            )
        }
    }
}

/**
 * Main content section for a [Sheet].
 *
 * ```
 * SheetContent {
 *     Text("Your content here")
 * }
 * ```
 *
 * @param modifier Modifier for the content column.
 * @param content The sheet body content.
 */
@Composable
fun SheetContent(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.padding(top = RikkaTheme.spacing.sm),
        content = content,
    )
}

/**
 * Footer section for a [Sheet]. Arranges action buttons at the end.
 *
 * ```
 * SheetFooter {
 *     Button("Cancel", onClick = { dismiss() }, variant = ButtonVariant.Outline)
 *     Button("Apply", onClick = { apply() })
 * }
 * ```
 *
 * @param modifier Modifier for the footer row.
 * @param content Footer content — typically buttons.
 */
@Composable
fun SheetFooter(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier.padding(top = RikkaTheme.spacing.sm),
        horizontalArrangement = Arrangement.spacedBy(
            RikkaTheme.spacing.sm,
            Alignment.End,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        content()
    }
}

// ─── Internal: Shape Resolution ─────────────────────────────

/**
 * Returns a shape with rounded corners only on the side facing the center.
 */
@Composable
private fun resolvePanelShape(side: SheetSide): Shape {
    val radius = 10.dp // matches default base radius from RikkaShapes
    return when (side) {
        SheetSide.Left -> RoundedCornerShape(
            topStart = 0.dp,
            topEnd = radius,
            bottomEnd = radius,
            bottomStart = 0.dp,
        )

        SheetSide.Right -> RoundedCornerShape(
            topStart = radius,
            topEnd = 0.dp,
            bottomEnd = 0.dp,
            bottomStart = radius,
        )

        SheetSide.Top -> RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomEnd = radius,
            bottomStart = radius,
        )

        SheetSide.Bottom -> RoundedCornerShape(
            topStart = radius,
            topEnd = radius,
            bottomEnd = 0.dp,
            bottomStart = 0.dp,
        )
    }
}

/**
 * Returns the alignment for the panel within the full-screen box.
 */
private fun resolvePanelAlignment(side: SheetSide): Alignment = when (side) {
    SheetSide.Left -> Alignment.CenterStart
    SheetSide.Right -> Alignment.CenterEnd
    SheetSide.Top -> Alignment.TopCenter
    SheetSide.Bottom -> Alignment.BottomCenter
}

/**
 * Returns a border modifier that places a 1dp border on the edge facing the center.
 */
@Composable
private fun resolveBorderModifier(
    side: SheetSide,
    borderColor: Color,
): Modifier {
    val width = 1.dp
    return when (side) {
        SheetSide.Left -> Modifier.border(
            width = width,
            color = borderColor,
            shape = RoundedCornerShape(
                topEnd = 10.dp,
                bottomEnd = 10.dp,
            ),
        )

        SheetSide.Right -> Modifier.border(
            width = width,
            color = borderColor,
            shape = RoundedCornerShape(
                topStart = 10.dp,
                bottomStart = 10.dp,
            ),
        )

        SheetSide.Top -> Modifier.border(
            width = width,
            color = borderColor,
            shape = RoundedCornerShape(
                bottomStart = 10.dp,
                bottomEnd = 10.dp,
            ),
        )

        SheetSide.Bottom -> Modifier.border(
            width = width,
            color = borderColor,
            shape = RoundedCornerShape(
                topStart = 10.dp,
                topEnd = 10.dp,
            ),
        )
    }
}

/**
 * Returns the enter transition for the sheet panel based on the side.
 */
private fun resolveEnterTransition(
    side: SheetSide,
    durationMs: Int,
) = when (side) {
    SheetSide.Left -> slideInHorizontally(
        animationSpec = tween(durationMs),
        initialOffsetX = { -it },
    ) + fadeIn(animationSpec = tween(durationMs))

    SheetSide.Right -> slideInHorizontally(
        animationSpec = tween(durationMs),
        initialOffsetX = { it },
    ) + fadeIn(animationSpec = tween(durationMs))

    SheetSide.Top -> slideInVertically(
        animationSpec = tween(durationMs),
        initialOffsetY = { -it },
    ) + fadeIn(animationSpec = tween(durationMs))

    SheetSide.Bottom -> slideInVertically(
        animationSpec = tween(durationMs),
        initialOffsetY = { it },
    ) + fadeIn(animationSpec = tween(durationMs))
}

/**
 * Returns the exit transition for the sheet panel based on the side.
 */
private fun resolveExitTransition(
    side: SheetSide,
    durationMs: Int,
) = when (side) {
    SheetSide.Left -> slideOutHorizontally(
        animationSpec = tween(durationMs),
        targetOffsetX = { -it },
    ) + fadeOut(animationSpec = tween(durationMs))

    SheetSide.Right -> slideOutHorizontally(
        animationSpec = tween(durationMs),
        targetOffsetX = { it },
    ) + fadeOut(animationSpec = tween(durationMs))

    SheetSide.Top -> slideOutVertically(
        animationSpec = tween(durationMs),
        targetOffsetY = { -it },
    ) + fadeOut(animationSpec = tween(durationMs))

    SheetSide.Bottom -> slideOutVertically(
        animationSpec = tween(durationMs),
        targetOffsetY = { it },
    ) + fadeOut(animationSpec = tween(durationMs))
}
