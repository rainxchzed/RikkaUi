package zed.rainxch.rikkaui.components.ui.popover

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import kotlinx.coroutines.delay
import zed.rainxch.rikkaui.foundation.LocalContentColor
import zed.rainxch.rikkaui.foundation.LocalTextStyle
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Animation ─────────────────────────────────────────────

enum class PopoverAnimation {
    /** Fade + vertical expand (default). */
    FadeExpand,

    /** Opacity fade only. */
    Fade,

    /** No animation. */
    None,
}

// ─── Alignment ─────────────────────────────────────────────

enum class PopoverPlacement {
    BottomStart,
    BottomEnd,
    TopStart,
    TopEnd,
    BottomCenter,
    TopCenter,
}

// ─── Component ──────────────────────────────────────────────

@Composable
fun Popover(
    expanded: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Popover",
    animation: PopoverAnimation = PopoverAnimation.FadeExpand,
    placement: PopoverPlacement = PopoverPlacement.BottomStart,
    minWidth: Dp = 120.dp,
    maxWidth: Dp = 360.dp,
    trigger: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val motion = RikkaTheme.motion
    val focusRequester = remember { FocusRequester() }

    val popupAlignment = resolvePlacement(placement)

    var showPopup by remember { mutableStateOf(false) }
    LaunchedEffect(expanded) {
        if (expanded) showPopup = true
    }

    Box(modifier = modifier) {
        trigger()

        if (showPopup) {
            Popup(
                alignment = popupAlignment,
                onDismissRequest = onDismiss,
            ) {
                when (animation) {
                    PopoverAnimation.FadeExpand -> {
                        val expandFrom = resolveExpandFrom(placement)
                        AnimatedVisibility(
                            visible = expanded,
                            enter =
                                fadeIn(
                                    animationSpec =
                                        tween(
                                            motion.durationFast,
                                        ),
                                ) +
                                    expandVertically(
                                        animationSpec =
                                            tween(
                                                motion.durationDefault,
                                            ),
                                        expandFrom = expandFrom,
                                    ),
                            exit =
                                fadeOut(
                                    animationSpec =
                                        tween(
                                            motion.durationFast,
                                        ),
                                ) +
                                    shrinkVertically(
                                        animationSpec =
                                            tween(
                                                motion.durationDefault,
                                            ),
                                        shrinkTowards = expandFrom,
                                    ),
                        ) {
                            PopoverCard(
                                label = label,
                                onDismiss = onDismiss,
                                focusRequester = focusRequester,
                                minWidth = minWidth,
                                maxWidth = maxWidth,
                                content = content,
                            )
                        }
                    }

                    PopoverAnimation.Fade -> {
                        AnimatedVisibility(
                            visible = expanded,
                            enter =
                                fadeIn(
                                    animationSpec =
                                        tween(
                                            motion.durationDefault,
                                        ),
                                ),
                            exit =
                                fadeOut(
                                    animationSpec =
                                        tween(
                                            motion.durationDefault,
                                        ),
                                ),
                        ) {
                            PopoverCard(
                                label = label,
                                onDismiss = onDismiss,
                                focusRequester = focusRequester,
                                minWidth = minWidth,
                                maxWidth = maxWidth,
                                content = content,
                            )
                        }
                    }

                    PopoverAnimation.None -> {
                        PopoverCard(
                            label = label,
                            onDismiss = onDismiss,
                            focusRequester = focusRequester,
                            minWidth = minWidth,
                            maxWidth = maxWidth,
                            content = content,
                        )
                    }
                }

                if (!expanded) {
                    LaunchedEffect(Unit) {
                        delay(motion.durationDefault.toLong() + 50L)
                        showPopup = false
                    }
                }
            }
        }
    }
}

// ─── Private helpers ────────────────────────────────────────

@Composable
private fun PopoverCard(
    label: String,
    onDismiss: () -> Unit,
    focusRequester: FocusRequester,
    minWidth: Dp,
    maxWidth: Dp,
    content: @Composable () -> Unit,
) {
    val colors = RikkaTheme.colors
    val shapes = RikkaTheme.shapes
    val spacing = RikkaTheme.spacing

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(
        modifier =
            Modifier
                .semantics { paneTitle = label }
                .onKeyEvent { event ->
                    if (event.type == KeyEventType.KeyDown &&
                        event.key == Key.Escape
                    ) {
                        onDismiss()
                        true
                    } else {
                        false
                    }
                }.focusRequester(focusRequester)
                .focusable()
                .widthIn(min = minWidth, max = maxWidth)
                .shadow(RikkaTheme.elevation.high, shapes.md)
                .border(1.dp, colors.border, shapes.md)
                .background(colors.surface, shapes.md)
                .clip(shapes.md)
                .padding(spacing.lg),
    ) {
        CompositionLocalProvider(
            LocalContentColor provides colors.onSurface,
            LocalTextStyle provides RikkaTheme.typography.small,
        ) {
            content()
        }
    }
}

private fun resolvePlacement(placement: PopoverPlacement): Alignment =
    when (placement) {
        PopoverPlacement.BottomStart -> Alignment.BottomStart
        PopoverPlacement.BottomEnd -> Alignment.BottomEnd
        PopoverPlacement.TopStart -> Alignment.TopStart
        PopoverPlacement.TopEnd -> Alignment.TopEnd
        PopoverPlacement.BottomCenter -> Alignment.BottomCenter
        PopoverPlacement.TopCenter -> Alignment.TopCenter
    }

private fun resolveExpandFrom(placement: PopoverPlacement): Alignment.Vertical =
    when (placement) {
        PopoverPlacement.BottomStart,
        PopoverPlacement.BottomEnd,
        PopoverPlacement.BottomCenter,
        -> Alignment.Top

        PopoverPlacement.TopStart,
        PopoverPlacement.TopEnd,
        PopoverPlacement.TopCenter,
        -> Alignment.Bottom
    }
