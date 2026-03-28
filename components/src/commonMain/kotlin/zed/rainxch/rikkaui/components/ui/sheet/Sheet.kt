package zed.rainxch.rikkaui.components.ui.sheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.ui.semantics.dismiss
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import zed.rainxch.rikkaui.components.theme.RikkaMotion
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

// ─── Animation Enum ─────────────────────────────────────────

/**
 * Animation style for [Sheet] enter/exit transitions.
 *
 * Each variant uses [RikkaTheme.motion] tokens for timing, so
 * switching the motion preset (snappy / playful / minimal)
 * automatically adjusts the sheet animation speed.
 *
 * ```
 * Sheet(
 *     open = open,
 *     onDismiss = { open = false },
 *     animation = SheetAnimation.FadeScale,
 * ) { ... }
 * ```
 *
 * - [Slide] — Slide from edge + fade. The default, gives the
 *   sheet a natural "drawer" feel.
 * - [FadeScale] — Fade in combined with a subtle 0.95 -> 1.0
 *   scale. A centered pop-in feel.
 * - [Fade] — Opacity-only transition, no slide or scale.
 * - [None] — Instant appear/disappear with no animation.
 */
enum class SheetAnimation {
    /** Slide from edge + fade. Default. */
    Slide,

    /** Fade + subtle scale up from 0.95. */
    FadeScale,

    /** Opacity-only fade, no slide or scale. */
    Fade,

    /** Instant appear/disappear. */
    None,
}

// ─── Component ──────────────────────────────────────────────

/**
 * Sheet (side/bottom panel) overlay for the RikkaUi design system.
 *
 * Displays a panel that slides in from any edge of the screen
 * on top of a semi-transparent scrim. Clicking the scrim dismisses
 * the sheet. Uses transitions from [RikkaTheme.motion].
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
 * // Bottom sheet with fade animation
 * Sheet(
 *     open = open,
 *     onDismiss = { open = false },
 *     side = SheetSide.Bottom,
 *     animation = SheetAnimation.Fade,
 * ) {
 *     SheetHeader(title = "Options")
 *     SheetContent { Text("Pick an option") }
 * }
 * ```
 *
 * @param open Whether the sheet is visible.
 * @param onDismiss Called when the user dismisses the sheet
 *   (scrim click or back gesture).
 * @param side The edge from which the sheet slides in. Defaults
 *   to [SheetSide.Right].
 * @param modifier Modifier applied to the sheet panel.
 * @param label Accessibility label describing the sheet's purpose.
 * @param animation The enter/exit animation style. Defaults to
 *   [SheetAnimation.Slide].
 * @param scrimColor Color of the backdrop scrim. Defaults to
 *   semi-transparent black (`Color.Black.copy(alpha = 0.5f)`).
 * @param panelWidth Width of the sheet panel for [SheetSide.Left]
 *   and [SheetSide.Right]. Defaults to 320.dp.
 * @param content Sheet content — use [SheetHeader], [SheetContent],
 *   [SheetFooter] for structure.
 */
@Composable
fun Sheet(
    open: Boolean,
    onDismiss: () -> Unit,
    side: SheetSide = SheetSide.Right,
    modifier: Modifier = Modifier,
    label: String = "Sheet",
    animation: SheetAnimation = SheetAnimation.Slide,
    scrimColor: Color = Color.Black.copy(alpha = 0.5f),
    panelWidth: Dp = 320.dp,
    content: @Composable ColumnScope.() -> Unit,
) {
    if (!open) return

    val colors = RikkaTheme.colors
    val spacing = RikkaTheme.spacing
    val motion = RikkaTheme.motion

    val panelShape = resolvePanelShape(side)
    val panelAlignment = resolvePanelAlignment(side)

    val (enterTransition, exitTransition) =
        resolveSheetTransition(animation, side, motion)

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
                enter =
                    fadeIn(
                        animationSpec = tween(motion.durationEnter),
                    ),
                exit =
                    fadeOut(
                        animationSpec = tween(motion.durationEnter),
                    ),
            ) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .background(scrimColor)
                            .clickable(
                                interactionSource =
                                    remember {
                                        MutableInteractionSource()
                                    },
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
                val sizeModifier =
                    when (side) {
                        SheetSide.Left, SheetSide.Right ->
                            Modifier
                                .width(panelWidth)
                                .fillMaxHeight()

                        SheetSide.Top, SheetSide.Bottom ->
                            Modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 200.dp)
                    }

                Column(
                    modifier =
                        modifier
                            .then(sizeModifier)
                            .semantics(mergeDescendants = true) {
                                paneTitle = label
                                dismiss {
                                    onDismiss()
                                    true
                                }
                            }.then(borderModifier)
                            .background(colors.card, panelShape)
                            .clip(panelShape)
                            .padding(spacing.xl),
                    verticalArrangement =
                        Arrangement.spacedBy(
                            spacing.md,
                        ),
                    content = content,
                )
            }
        }
    }
}

// ─── Structured Sections ────────────────────────────────────

/**
 * Header section for a [Sheet]. Contains a title and optional
 * description.
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
        verticalArrangement =
            Arrangement.spacedBy(
                RikkaTheme.spacing.xs,
            ),
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
        horizontalArrangement =
            Arrangement.spacedBy(
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
 * Returns a shape with rounded corners only on the side facing
 * the center.
 */
@Composable
private fun resolvePanelShape(side: SheetSide): Shape {
    val radius = 10.dp
    return when (side) {
        SheetSide.Left ->
            RoundedCornerShape(
                topStart = 0.dp,
                topEnd = radius,
                bottomEnd = radius,
                bottomStart = 0.dp,
            )

        SheetSide.Right ->
            RoundedCornerShape(
                topStart = radius,
                topEnd = 0.dp,
                bottomEnd = 0.dp,
                bottomStart = radius,
            )

        SheetSide.Top ->
            RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 0.dp,
                bottomEnd = radius,
                bottomStart = radius,
            )

        SheetSide.Bottom ->
            RoundedCornerShape(
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
private fun resolvePanelAlignment(side: SheetSide): Alignment =
    when (side) {
        SheetSide.Left -> Alignment.CenterStart
        SheetSide.Right -> Alignment.CenterEnd
        SheetSide.Top -> Alignment.TopCenter
        SheetSide.Bottom -> Alignment.BottomCenter
    }

/**
 * Returns a border modifier that places a 1dp border on the edge
 * facing the center.
 */
@Composable
private fun resolveBorderModifier(
    side: SheetSide,
    borderColor: Color,
): Modifier {
    val width = 1.dp
    return when (side) {
        SheetSide.Left ->
            Modifier.border(
                width = width,
                color = borderColor,
                shape =
                    RoundedCornerShape(
                        topEnd = 10.dp,
                        bottomEnd = 10.dp,
                    ),
            )

        SheetSide.Right ->
            Modifier.border(
                width = width,
                color = borderColor,
                shape =
                    RoundedCornerShape(
                        topStart = 10.dp,
                        bottomStart = 10.dp,
                    ),
            )

        SheetSide.Top ->
            Modifier.border(
                width = width,
                color = borderColor,
                shape =
                    RoundedCornerShape(
                        bottomStart = 10.dp,
                        bottomEnd = 10.dp,
                    ),
            )

        SheetSide.Bottom ->
            Modifier.border(
                width = width,
                color = borderColor,
                shape =
                    RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp,
                    ),
            )
    }
}

// ─── Internal: Transition Resolution ────────────────────────

/**
 * Resolves enter/exit transitions for the sheet panel based on the
 * chosen [SheetAnimation], [SheetSide], and current [RikkaMotion]
 * tokens.
 */
private fun resolveSheetTransition(
    animation: SheetAnimation,
    side: SheetSide,
    motion: RikkaMotion,
): Pair<EnterTransition, ExitTransition> =
    when (animation) {
        SheetAnimation.Slide -> resolveSlideTransition(side, motion)

        SheetAnimation.FadeScale -> {
            val enter =
                fadeIn(
                    animationSpec = tween(motion.durationEnter),
                ) +
                    scaleIn(
                        initialScale = 0.95f,
                        animationSpec = tween(motion.durationEnter),
                    )
            val exit =
                fadeOut(
                    animationSpec = tween(motion.durationEnter),
                ) +
                    scaleOut(
                        targetScale = 0.95f,
                        animationSpec = tween(motion.durationEnter),
                    )
            enter to exit
        }

        SheetAnimation.Fade -> {
            val enter =
                fadeIn(
                    animationSpec = tween(motion.durationEnter),
                )
            val exit =
                fadeOut(
                    animationSpec = tween(motion.durationEnter),
                )
            enter to exit
        }

        SheetAnimation.None -> {
            val enter = fadeIn(animationSpec = tween(0))
            val exit = fadeOut(animationSpec = tween(0))
            enter to exit
        }
    }

/**
 * Resolves slide + fade enter/exit transitions based on the sheet
 * [side] and [motion] tokens.
 */
private fun resolveSlideTransition(
    side: SheetSide,
    motion: RikkaMotion,
): Pair<EnterTransition, ExitTransition> {
    val durationMs = motion.durationEnter
    val enter =
        when (side) {
            SheetSide.Left ->
                slideInHorizontally(
                    animationSpec = tween(durationMs),
                    initialOffsetX = { -it },
                ) + fadeIn(animationSpec = tween(durationMs))

            SheetSide.Right ->
                slideInHorizontally(
                    animationSpec = tween(durationMs),
                    initialOffsetX = { it },
                ) + fadeIn(animationSpec = tween(durationMs))

            SheetSide.Top ->
                slideInVertically(
                    animationSpec = tween(durationMs),
                    initialOffsetY = { -it },
                ) + fadeIn(animationSpec = tween(durationMs))

            SheetSide.Bottom ->
                slideInVertically(
                    animationSpec = tween(durationMs),
                    initialOffsetY = { it },
                ) + fadeIn(animationSpec = tween(durationMs))
        }
    val exit =
        when (side) {
            SheetSide.Left ->
                slideOutHorizontally(
                    animationSpec = tween(durationMs),
                    targetOffsetX = { -it },
                ) + fadeOut(animationSpec = tween(durationMs))

            SheetSide.Right ->
                slideOutHorizontally(
                    animationSpec = tween(durationMs),
                    targetOffsetX = { it },
                ) + fadeOut(animationSpec = tween(durationMs))

            SheetSide.Top ->
                slideOutVertically(
                    animationSpec = tween(durationMs),
                    targetOffsetY = { -it },
                ) + fadeOut(animationSpec = tween(durationMs))

            SheetSide.Bottom ->
                slideOutVertically(
                    animationSpec = tween(durationMs),
                    targetOffsetY = { it },
                ) + fadeOut(animationSpec = tween(durationMs))
        }
    return enter to exit
}
