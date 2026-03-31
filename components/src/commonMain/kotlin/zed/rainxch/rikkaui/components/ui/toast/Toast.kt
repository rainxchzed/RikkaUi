package zed.rainxch.rikkaui.components.ui.toast

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.dismiss
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

// ─── Variant ────────────────────────────────────────────────

enum class ToastVariant {
    /** Neutral popover card. */
    Default,

    /** Green accent. Confirmations and positive outcomes. */
    Success,

    /** Red accent. Errors and critical failures. */
    Destructive,

    /** Yellow/orange accent. Caution alerts. */
    Warning,
}

// ─── Animation ──────────────────────────────────────────────

enum class ToastAnimation {
    /** Slide from nearest edge with fade. */
    SlideIn,

    /** Simple fade in/out. */
    Fade,

    /** Scale up from 0.8 with fade. */
    Scale,

    /** Instant appear/disappear. */
    None,
}

// ─── Position ───────────────────────────────────────────────

enum class ToastPosition {
    TopCenter,
    TopRight,
    BottomCenter,
    BottomRight,
}

// ─── Toast Data ─────────────────────────────────────────────

@Immutable
data class ToastData(
    val id: Long,
    val message: String,
    val variant: ToastVariant = ToastVariant.Default,
    val duration: Long = DEFAULT_TOAST_DURATION,
    val actionLabel: String? = null,
    val onAction: (() -> Unit)? = null,
)

const val DEFAULT_TOAST_DURATION = 4000L
const val TOAST_DURATION_SHORT = 2000L
const val TOAST_DURATION_LONG = 7000L
const val TOAST_DURATION_INFINITE = Long.MAX_VALUE

private const val DEFAULT_MAX_VISIBLE_TOASTS = 5

private const val SWIPE_DISMISS_THRESHOLD = 150f
private const val SCALE_ANIMATION_INITIAL = 0.8f

// ─── Host State ─────────────────────────────────────────────

class ToastHostState {
    private var nextId = 0L
    private val mutex = Mutex()

    internal val toasts = mutableStateListOf<ToastData>()

    suspend fun show(
        message: String,
        variant: ToastVariant = ToastVariant.Default,
        duration: Long = DEFAULT_TOAST_DURATION,
        actionLabel: String? = null,
        onAction: (() -> Unit)? = null,
    ) {
        mutex.withLock {
            val toast =
                ToastData(
                    id = nextId++,
                    message = message,
                    variant = variant,
                    duration = duration,
                    actionLabel = actionLabel,
                    onAction = onAction,
                )
            toasts.add(toast)
        }
    }

    internal fun dismiss(id: Long) {
        toasts.removeAll { it.id == id }
    }

    fun dismissAll() {
        toasts.clear()
    }
}

@Composable
fun rememberToastHostState(): ToastHostState = remember { ToastHostState() }

val LocalToastHostState =
    staticCompositionLocalOf<ToastHostState> {
        error("No ToastHostState provided. Place ToastHost at your app root.")
    }

// ─── Toast Host ─────────────────────────────────────────────

@Composable
fun ToastHost(
    hostState: ToastHostState,
    modifier: Modifier = Modifier,
    position: ToastPosition = ToastPosition.BottomRight,
    animation: ToastAnimation = ToastAnimation.SlideIn,
    maxVisibleToasts: Int = DEFAULT_MAX_VISIBLE_TOASTS,
    swipeToDismiss: Boolean = true,
    showProgressBar: Boolean = false,
) {
    val spacing = RikkaTheme.spacing

    val isTop =
        position == ToastPosition.TopCenter ||
            position == ToastPosition.TopRight

    // Trim oldest when exceeding max visible
    LaunchedEffect(hostState.toasts.size, maxVisibleToasts) {
        while (hostState.toasts.size > maxVisibleToasts) {
            hostState.dismiss(hostState.toasts.first().id)
        }
    }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(spacing.lg),
        verticalArrangement =
            Arrangement.spacedBy(
                spacing.sm,
                if (isTop) Alignment.Top else Alignment.Bottom,
            ),
        horizontalAlignment =
            when (position) {
                ToastPosition.TopCenter,
                ToastPosition.BottomCenter,
                -> Alignment.CenterHorizontally

                ToastPosition.TopRight,
                ToastPosition.BottomRight,
                -> Alignment.End
            },
    ) {
        val items =
            if (isTop) {
                hostState.toasts.toList()
            } else {
                hostState.toasts.toList().reversed()
            }

        items.forEach { toast ->
            key(toast.id) {
                ToastItem(
                    data = toast,
                    isTop = isTop,
                    animation = animation,
                    swipeToDismiss = swipeToDismiss,
                    showProgressBar = showProgressBar,
                    onDismiss = { hostState.dismiss(toast.id) },
                )
            }
        }
    }
}

// ─── Toast Item (animated wrapper) ──────────────────────────

@Composable
private fun ToastItem(
    data: ToastData,
    isTop: Boolean,
    animation: ToastAnimation,
    swipeToDismiss: Boolean,
    showProgressBar: Boolean,
    onDismiss: () -> Unit,
) {
    val motion = RikkaTheme.motion

    // Track whether the timer is paused (e.g. during hover)
    var isPaused by remember { mutableStateOf(false) }

    // Elapsed time tracking for progress bar
    var elapsed by remember { mutableStateOf(0L) }
    val isFiniteDuration = data.duration != TOAST_DURATION_INFINITE

    // Track visibility for exit animation
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    // Auto-dismiss timer with pause support
    LaunchedEffect(data.id) {
        if (!isFiniteDuration) return@LaunchedEffect
        val tickInterval = 50L
        while (elapsed < data.duration) {
            delay(tickInterval)
            if (!isPaused) {
                elapsed += tickInterval
            }
        }
        // Trigger exit animation, then actually remove
        visible = false
        delay(motion.durationDefault.toLong() + 50L)
        onDismiss()
    }

    // Progress fraction (1.0 = full, 0.0 = expired)
    val progressFraction =
        if (isFiniteDuration) {
            (1f - (elapsed.toFloat() / data.duration)).coerceIn(0f, 1f)
        } else {
            1f
        }
    val animatedProgress by animateFloatAsState(
        targetValue = progressFraction,
        animationSpec = tween(durationMillis = 100),
    )

    // Swipe-to-dismiss offset
    val swipeOffset = remember { Animatable(0f) }
    val swipeAlpha =
        1f - (
            swipeOffset.value.absoluteValue /
                SWIPE_DISMISS_THRESHOLD
        ).coerceIn(0f, 1f) * 0.5f
    val swipeScope = rememberCoroutineScope()

    val enterDirection = if (isTop) -1 else 1
    val (enter, exit) =
        resolveTransitions(
            animation = animation,
            enterDirection = enterDirection,
            durationEnter = motion.durationEnter,
            durationExit = motion.durationDefault,
        )

    AnimatedVisibility(
        visible = visible,
        enter = enter,
        exit = exit,
    ) {
        val swipeModifier =
            if (swipeToDismiss) {
                Modifier
                    .offset { IntOffset(swipeOffset.value.roundToInt(), 0) }
                    .alpha(swipeAlpha)
                    .pointerInput(data.id) {
                        detectHorizontalDragGestures(
                            onDragEnd = {
                                if (swipeOffset.value.absoluteValue >
                                    SWIPE_DISMISS_THRESHOLD
                                ) {
                                    visible = false
                                    swipeScope.launch {
                                        delay(
                                            motion.durationDefault
                                                .toLong() + 50L,
                                        )
                                        onDismiss()
                                    }
                                } else {
                                    swipeScope.launch {
                                        swipeOffset.animateTo(
                                            0f,
                                            tween(motion.durationDefault),
                                        )
                                    }
                                }
                            },
                            onHorizontalDrag = { _, dragAmount ->
                                swipeScope.launch {
                                    swipeOffset.snapTo(
                                        swipeOffset.value + dragAmount,
                                    )
                                }
                            },
                        )
                    }
            } else {
                Modifier
            }

        Toast(
            message = data.message,
            variant = data.variant,
            onDismiss = {
                visible = false
                swipeScope.launch {
                    delay(motion.durationDefault.toLong() + 50L)
                    onDismiss()
                }
            },
            actionLabel = data.actionLabel,
            onAction = data.onAction,
            showProgressBar = showProgressBar && isFiniteDuration,
            progressFraction = animatedProgress,
            modifier = swipeModifier,
            onHoverChange = { hovered -> isPaused = hovered },
        )
    }
}

// ─── Transition Resolution ──────────────────────────────────

private fun resolveTransitions(
    animation: ToastAnimation,
    enterDirection: Int,
    durationEnter: Int,
    durationExit: Int,
): Pair<EnterTransition, ExitTransition> =
    when (animation) {
        ToastAnimation.SlideIn -> {
            val enter =
                slideInVertically(
                    animationSpec = tween(durationEnter),
                    initialOffsetY = { fullHeight -> enterDirection * fullHeight },
                ) + fadeIn(animationSpec = tween(durationEnter))

            val exit =
                slideOutVertically(
                    animationSpec = tween(durationExit),
                    targetOffsetY = { fullHeight -> enterDirection * fullHeight },
                ) + fadeOut(animationSpec = tween(durationExit))

            enter to exit
        }

        ToastAnimation.Fade -> {
            val enter = fadeIn(animationSpec = tween(durationEnter))
            val exit = fadeOut(animationSpec = tween(durationExit))
            enter to exit
        }

        ToastAnimation.Scale -> {
            val enter =
                scaleIn(
                    animationSpec = tween(durationEnter),
                    initialScale = SCALE_ANIMATION_INITIAL,
                ) + fadeIn(animationSpec = tween(durationEnter))

            val exit =
                scaleOut(
                    animationSpec = tween(durationExit),
                    targetScale = SCALE_ANIMATION_INITIAL,
                ) + fadeOut(animationSpec = tween(durationExit))

            enter to exit
        }

        ToastAnimation.None -> {
            val enter = fadeIn(animationSpec = tween(0))
            val exit = fadeOut(animationSpec = tween(0))
            enter to exit
        }
    }

// ─── Toast (visual card) ────────────────────────────────────

@Composable
fun Toast(
    message: String,
    variant: ToastVariant = ToastVariant.Default,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    label: String = "",
    showProgressBar: Boolean = false,
    progressFraction: Float = 1f,
    onHoverChange: ((Boolean) -> Unit)? = null,
) {
    val colors = RikkaTheme.colors
    val shapes = RikkaTheme.shapes
    val spacing = RikkaTheme.spacing
    val resolved = resolveColors(variant)

    val accessibilityLabel =
        label.ifEmpty {
            when (variant) {
                ToastVariant.Default -> "Notification: $message"
                ToastVariant.Success -> "Success: $message"
                ToastVariant.Destructive -> "Error: $message"
                ToastVariant.Warning -> "Warning: $message"
            }
        }

    // Hover interaction for pause-on-hover
    val hoverInteraction = remember { MutableInteractionSource() }
    val isHovered by hoverInteraction.collectIsHoveredAsState()

    LaunchedEffect(isHovered) {
        onHoverChange?.invoke(isHovered)
    }

    Column(
        modifier =
            modifier
                .widthIn(max = 420.dp)
                .hoverable(hoverInteraction)
                .semantics(mergeDescendants = true) {
                    contentDescription = accessibilityLabel
                    liveRegion = LiveRegionMode.Polite
                    dismiss {
                        onDismiss()
                        true
                    }
                }.shadow(8.dp, shapes.lg)
                .border(1.dp, colors.border, shapes.lg)
                .background(colors.popover, shapes.lg)
                .clip(shapes.lg),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // ─── Left accent border ──────────────────────
            if (resolved.accent != Color.Transparent) {
                Box(
                    modifier =
                        Modifier
                            .size(width = 4.dp, height = 48.dp)
                            .background(resolved.accent),
                )
            }

            // ─── Content ─────────────────────────────────
            Row(
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(
                            start =
                                if (resolved.accent != Color.Transparent) {
                                    spacing.md
                                } else {
                                    spacing.lg
                                },
                            top = spacing.md,
                            bottom = spacing.md,
                            end = spacing.sm,
                        ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing.sm),
            ) {
                Text(
                    text = message,
                    variant = TextVariant.Small,
                    color = colors.popoverForeground,
                    modifier = Modifier.weight(1f),
                )

                // ─── Action button ───────────────────────
                if (actionLabel != null && onAction != null) {
                    val actionInteraction =
                        remember {
                            MutableInteractionSource()
                        }
                    val actionHovered by actionInteraction
                        .collectIsHoveredAsState()

                    Text(
                        text = actionLabel,
                        variant = TextVariant.Small,
                        color =
                            if (actionHovered) {
                                colors.primary.copy(alpha = 0.8f)
                            } else {
                                colors.primary
                            },
                        modifier =
                            Modifier
                                .hoverable(actionInteraction)
                                .clickable(
                                    interactionSource = actionInteraction,
                                    indication = null,
                                    role = Role.Button,
                                    onClick = {
                                        onAction()
                                        onDismiss()
                                    },
                                ).semantics {
                                    contentDescription = actionLabel
                                }.padding(
                                    horizontal = spacing.sm,
                                    vertical = spacing.xs,
                                ),
                    )
                }
            }

            // ─── Dismiss button ──────────────────────────
            val dismissInteraction =
                remember {
                    MutableInteractionSource()
                }
            val dismissHovered by dismissInteraction
                .collectIsHoveredAsState()

            Box(
                modifier =
                    Modifier
                        .hoverable(dismissInteraction)
                        .clickable(
                            interactionSource = dismissInteraction,
                            indication = null,
                            role = Role.Button,
                            onClick = onDismiss,
                        ).semantics {
                            contentDescription = "Dismiss notification"
                        }.padding(spacing.md),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = RikkaIcons.X,
                    contentDescription = null,
                    tint =
                        if (dismissHovered) {
                            colors.foreground
                        } else {
                            colors.mutedForeground
                        },
                )
            }
        }

        // ─── Progress bar ────────────────────────────────
        if (showProgressBar) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .background(colors.muted),
            ) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth(progressFraction)
                            .height(3.dp)
                            .background(resolved.accent.takeIf { it != Color.Transparent } ?: colors.primary),
                )
            }
        }
    }
}

// ─── Internal: Color Resolution ─────────────────────────────

@Immutable
private data class ToastColors(
    val accent: Color,
)

@Composable
private fun resolveColors(variant: ToastVariant): ToastColors {
    val colors = RikkaTheme.colors

    return when (variant) {
        ToastVariant.Default -> {
            ToastColors(accent = Color.Transparent)
        }

        ToastVariant.Success -> {
            ToastColors(accent = colors.primary)
        }

        ToastVariant.Destructive -> {
            ToastColors(accent = colors.destructive)
        }

        ToastVariant.Warning -> {
            ToastColors(
                accent = Color(0xFFF59E0B),
            )
        }
    }
}

// ─── Internal: Position Resolution ──────────────────────────

private fun resolveAlignment(position: ToastPosition): Alignment =
    when (position) {
        ToastPosition.TopCenter -> Alignment.TopCenter
        ToastPosition.TopRight -> Alignment.TopEnd
        ToastPosition.BottomCenter -> Alignment.BottomCenter
        ToastPosition.BottomRight -> Alignment.BottomEnd
    }
