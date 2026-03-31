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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.delay
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.LocalContentColor
import zed.rainxch.rikkaui.foundation.LocalTextStyle
import zed.rainxch.rikkaui.foundation.RikkaMotion
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Side ───────────────────────────────────────────────────

/** Side from which the sheet slides in. */
enum class SheetSide {
    /** Slides down from the top edge. */
    Top,

    /** Slides up from the bottom edge. */
    Bottom,

    /** Slides in from the left edge. */
    Left,

    /** Slides in from the right edge (default). */
    Right,
}

// ─── Animation Enum ─────────────────────────────────────────

/** Animation style for sheet enter/exit transitions. */
enum class SheetAnimation {
    /** Slide from edge + fade. */
    Slide,

    /** Fade + subtle scale from 0.95. */
    FadeScale,

    /** Opacity-only fade. */
    Fade,

    /** Instant appear/disappear. */
    None,
}

// ─── Component ──────────────────────────────────────────────

@Composable
fun Sheet(
    open: Boolean,
    onDismiss: () -> Unit,
    side: SheetSide = SheetSide.Right,
    modifier: Modifier = Modifier,
    label: String = "Sheet",
    animation: SheetAnimation = SheetAnimation.Slide,
    scrimColor: Color = RikkaTheme.colors.scrim,
    panelWidth: Dp = 320.dp,
    content: @Composable ColumnScope.() -> Unit,
) {
    var showPopup by remember { mutableStateOf(false) }
    LaunchedEffect(open) {
        if (open) showPopup = true
    }
    if (!showPopup) return

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
                visible = open,
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
                visible = open,
                enter = enterTransition,
                exit = exitTransition,
            ) {
                val sizeModifier =
                    when (side) {
                        SheetSide.Left, SheetSide.Right -> {
                            Modifier
                                .width(panelWidth)
                                .fillMaxHeight()
                        }

                        SheetSide.Top, SheetSide.Bottom -> {
                            Modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 200.dp)
                        }
                    }

                CompositionLocalProvider(
                    LocalContentColor provides colors.cardForeground,
                    LocalTextStyle provides RikkaTheme.typography.p,
                ) {
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

            if (!open) {
                LaunchedEffect(Unit) {
                    delay(motion.durationEnter.toLong() + 50L)
                    showPopup = false
                }
            }
        }
    }
}

// ─── Structured Sections ────────────────────────────────────

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

@Composable
private fun resolvePanelShape(side: SheetSide): Shape {
    // Use the theme's lg shape as reference, extracting its corner radius.
    val themeShape = RikkaTheme.shapes.lg
    val radius =
        (themeShape as? RoundedCornerShape)
            ?.topStart
            ?.let { corner ->
                try {
                    val size =
                        androidx.compose.ui.geometry
                            .Size(100f, 100f)
                    val density =
                        androidx.compose.ui.unit
                            .Density(1f)
                    corner.toPx(size, density).dp
                } catch (_: Exception) {
                    10.dp
                }
            } ?: 10.dp

    return when (side) {
        SheetSide.Left -> {
            RoundedCornerShape(
                topStart = 0.dp,
                topEnd = radius,
                bottomEnd = radius,
                bottomStart = 0.dp,
            )
        }

        SheetSide.Right -> {
            RoundedCornerShape(
                topStart = radius,
                topEnd = 0.dp,
                bottomEnd = 0.dp,
                bottomStart = radius,
            )
        }

        SheetSide.Top -> {
            RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 0.dp,
                bottomEnd = radius,
                bottomStart = radius,
            )
        }

        SheetSide.Bottom -> {
            RoundedCornerShape(
                topStart = radius,
                topEnd = radius,
                bottomEnd = 0.dp,
                bottomStart = 0.dp,
            )
        }
    }
}

private fun resolvePanelAlignment(side: SheetSide): Alignment =
    when (side) {
        SheetSide.Left -> Alignment.CenterStart
        SheetSide.Right -> Alignment.CenterEnd
        SheetSide.Top -> Alignment.TopCenter
        SheetSide.Bottom -> Alignment.BottomCenter
    }

@Composable
private fun resolveBorderModifier(
    side: SheetSide,
    borderColor: Color,
): Modifier {
    val panelShape = resolvePanelShape(side)
    val width = 1.dp
    return Modifier.border(
        width = width,
        color = borderColor,
        shape = panelShape,
    )
}

// ─── Internal: Transition Resolution ────────────────────────

private fun resolveSheetTransition(
    animation: SheetAnimation,
    side: SheetSide,
    motion: RikkaMotion,
): Pair<EnterTransition, ExitTransition> =
    when (animation) {
        SheetAnimation.Slide -> {
            resolveSlideTransition(side, motion)
        }

        SheetAnimation.FadeScale -> {
            val enter =
                fadeIn(
                    animationSpec = tween(motion.durationEnter),
                ) +
                    scaleIn(
                        initialScale = motion.overlayScaleIn,
                        animationSpec = tween(motion.durationEnter),
                    )
            val exit =
                fadeOut(
                    animationSpec = tween(motion.durationEnter),
                ) +
                    scaleOut(
                        targetScale = motion.overlayScaleIn,
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
            val enter = fadeIn(animationSpec = tween(motion.durationInstant))
            val exit = fadeOut(animationSpec = tween(motion.durationInstant))
            enter to exit
        }
    }

private fun resolveSlideTransition(
    side: SheetSide,
    motion: RikkaMotion,
): Pair<EnterTransition, ExitTransition> {
    val durationMs = motion.durationEnter
    val enter =
        when (side) {
            SheetSide.Left -> {
                slideInHorizontally(
                    animationSpec = tween(durationMs),
                    initialOffsetX = { -it },
                ) + fadeIn(animationSpec = tween(durationMs))
            }

            SheetSide.Right -> {
                slideInHorizontally(
                    animationSpec = tween(durationMs),
                    initialOffsetX = { it },
                ) + fadeIn(animationSpec = tween(durationMs))
            }

            SheetSide.Top -> {
                slideInVertically(
                    animationSpec = tween(durationMs),
                    initialOffsetY = { -it },
                ) + fadeIn(animationSpec = tween(durationMs))
            }

            SheetSide.Bottom -> {
                slideInVertically(
                    animationSpec = tween(durationMs),
                    initialOffsetY = { it },
                ) + fadeIn(animationSpec = tween(durationMs))
            }
        }
    val exit =
        when (side) {
            SheetSide.Left -> {
                slideOutHorizontally(
                    animationSpec = tween(durationMs),
                    targetOffsetX = { -it },
                ) + fadeOut(animationSpec = tween(durationMs))
            }

            SheetSide.Right -> {
                slideOutHorizontally(
                    animationSpec = tween(durationMs),
                    targetOffsetX = { it },
                ) + fadeOut(animationSpec = tween(durationMs))
            }

            SheetSide.Top -> {
                slideOutVertically(
                    animationSpec = tween(durationMs),
                    targetOffsetY = { -it },
                ) + fadeOut(animationSpec = tween(durationMs))
            }

            SheetSide.Bottom -> {
                slideOutVertically(
                    animationSpec = tween(durationMs),
                    targetOffsetY = { it },
                ) + fadeOut(animationSpec = tween(durationMs))
            }
        }
    return enter to exit
}
