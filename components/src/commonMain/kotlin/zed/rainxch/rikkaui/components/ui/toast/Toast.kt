package zed.rainxch.rikkaui.components.ui.toast

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.dismiss
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

// ─── Variant ────────────────────────────────────────────────

/**
 * Toast visual variants controlling the accent color and icon treatment.
 *
 * - [Default] — Neutral popover card. General-purpose notifications.
 * - [Success] — Green accent on the left border. Confirmations and positive outcomes.
 * - [Destructive] — Destructive-red accent. Errors and critical failures.
 * - [Warning] — Yellow/orange accent. Caution alerts and recoverable issues.
 */
enum class ToastVariant {
    Default,
    Success,
    Destructive,
    Warning,
}

// ─── Position ───────────────────────────────────────────────

/**
 * Screen position where toasts are anchored.
 *
 * - [TopCenter] — Top edge, horizontally centered.
 * - [TopRight] — Top-right corner.
 * - [BottomCenter] — Bottom edge, horizontally centered.
 * - [BottomRight] — Bottom-right corner.
 */
enum class ToastPosition {
    TopCenter,
    TopRight,
    BottomCenter,
    BottomRight,
}

// ─── Toast Data ─────────────────────────────────────────────

/**
 * Immutable data describing a single toast notification.
 *
 * @param id Unique identifier for this toast instance.
 * @param message The primary text content displayed in the toast.
 * @param variant Visual variant controlling accent color.
 * @param duration Auto-dismiss delay in milliseconds.
 * @param actionLabel Optional action button text (e.g. "Undo", "Retry").
 * @param onAction Callback invoked when the action button is clicked.
 */
@Immutable
data class ToastData(
    val id: Long,
    val message: String,
    val variant: ToastVariant = ToastVariant.Default,
    val duration: Long = DEFAULT_TOAST_DURATION,
    val actionLabel: String? = null,
    val onAction: (() -> Unit)? = null,
)

private const val DEFAULT_TOAST_DURATION = 4000L
private const val MAX_VISIBLE_TOASTS = 5

// ─── Host State ─────────────────────────────────────────────

/**
 * State holder that manages a queue of toast notifications.
 *
 * Acts as the bridge between business logic (triggering a toast)
 * and the UI layer ([ToastHost]) that renders them. Conceptually
 * similar to `SnackbarHostState` but supports multiple concurrent
 * toasts with independent lifecycles.
 *
 * Usage:
 * ```
 * val toastState = rememberToastHostState()
 *
 * // In a coroutine (e.g. LaunchedEffect, viewModelScope):
 * toastState.show("File saved", variant = ToastVariant.Success)
 *
 * // With action:
 * toastState.show(
 *     message = "Item deleted",
 *     variant = ToastVariant.Destructive,
 *     actionLabel = "Undo",
 *     onAction = { undoDelete() },
 * )
 *
 * // In the composition tree:
 * ToastHost(hostState = toastState, position = ToastPosition.BottomRight)
 * ```
 */
class ToastHostState {
    private var nextId = 0L
    private val mutex = Mutex()

    /**
     * Currently visible toast items. Observed by [ToastHost].
     */
    internal val toasts = mutableStateListOf<ToastData>()

    /**
     * Shows a toast notification.
     *
     * This is a suspend function that returns after the toast
     * has been queued. The toast auto-dismisses after [duration] ms.
     *
     * @param message The text to display.
     * @param variant Visual variant (Default, Success, Destructive, Warning).
     * @param duration Auto-dismiss delay in milliseconds. Default is 4000ms.
     * @param actionLabel Optional action button label.
     * @param onAction Callback when the action button is tapped.
     */
    suspend fun show(
        message: String,
        variant: ToastVariant = ToastVariant.Default,
        duration: Long = DEFAULT_TOAST_DURATION,
        actionLabel: String? = null,
        onAction: (() -> Unit)? = null,
    ) {
        mutex.withLock {
            val toast = ToastData(
                id = nextId++,
                message = message,
                variant = variant,
                duration = duration,
                actionLabel = actionLabel,
                onAction = onAction,
            )
            toasts.add(toast)

            // Trim oldest if we exceed max visible
            while (toasts.size > MAX_VISIBLE_TOASTS) {
                toasts.removeAt(0)
            }
        }
    }

    /**
     * Dismisses a specific toast by its [id].
     */
    internal fun dismiss(id: Long) {
        toasts.removeAll { it.id == id }
    }
}

/**
 * Creates and remembers a [ToastHostState] across recompositions.
 *
 * ```
 * val toastState = rememberToastHostState()
 * ```
 */
@Composable
fun rememberToastHostState(): ToastHostState = remember { ToastHostState() }

// ─── Toast Host ─────────────────────────────────────────────

/**
 * Container that renders active toasts from a [ToastHostState].
 *
 * Place this at the root of your layout (typically inside a [Box]
 * with [Modifier.fillMaxSize]) so toasts overlay your content.
 *
 * Usage:
 * ```
 * val toastState = rememberToastHostState()
 *
 * Box(Modifier.fillMaxSize()) {
 *     // Your app content
 *     MyScreen()
 *
 *     // Toast overlay
 *     ToastHost(
 *         hostState = toastState,
 *         position = ToastPosition.BottomRight,
 *     )
 * }
 * ```
 *
 * @param hostState The [ToastHostState] managing the toast queue.
 * @param modifier Modifier applied to the host container.
 * @param position Screen position where toasts are anchored.
 */
@Composable
fun ToastHost(
    hostState: ToastHostState,
    modifier: Modifier = Modifier,
    position: ToastPosition = ToastPosition.BottomRight,
) {
    val spacing = RikkaTheme.spacing

    val alignment = resolveAlignment(position)
    val isTop = position == ToastPosition.TopCenter ||
        position == ToastPosition.TopRight

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = alignment,
    ) {
        Column(
            modifier = Modifier.padding(spacing.lg),
            verticalArrangement = Arrangement.spacedBy(
                spacing.sm,
                if (isTop) Alignment.Top else Alignment.Bottom,
            ),
            horizontalAlignment = when (position) {
                ToastPosition.TopCenter,
                ToastPosition.BottomCenter,
                -> Alignment.CenterHorizontally

                ToastPosition.TopRight,
                ToastPosition.BottomRight,
                -> Alignment.End
            },
        ) {
            val items = if (isTop) {
                hostState.toasts.toList()
            } else {
                hostState.toasts.toList().reversed()
            }

            items.forEach { toast ->
                key(toast.id) {
                    ToastItem(
                        data = toast,
                        isTop = isTop,
                        onDismiss = { hostState.dismiss(toast.id) },
                    )
                }
            }
        }
    }
}

// ─── Toast Item (animated wrapper) ──────────────────────────

@Composable
private fun ToastItem(
    data: ToastData,
    isTop: Boolean,
    onDismiss: () -> Unit,
) {
    val motion = RikkaTheme.motion

    // Auto-dismiss timer
    LaunchedEffect(data.id) {
        delay(data.duration)
        onDismiss()
    }

    val enterDirection = if (isTop) -1 else 1

    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(
            animationSpec = tween(motion.durationEnter),
            initialOffsetY = { fullHeight -> enterDirection * fullHeight },
        ) + fadeIn(
            animationSpec = tween(motion.durationEnter),
        ),
        exit = slideOutVertically(
            animationSpec = tween(motion.durationDefault),
            targetOffsetY = { fullHeight -> enterDirection * fullHeight },
        ) + fadeOut(
            animationSpec = tween(motion.durationDefault),
        ),
    ) {
        Toast(
            message = data.message,
            variant = data.variant,
            onDismiss = onDismiss,
            actionLabel = data.actionLabel,
            onAction = data.onAction,
        )
    }
}

// ─── Toast (visual card) ────────────────────────────────────

/**
 * Individual toast notification card.
 *
 * A card-like container with popover styling, variant-specific left
 * border accent, dismiss button, and optional action button.
 *
 * Prefer using [ToastHost] + [ToastHostState] for managed toasts.
 * Use this directly only for custom toast layouts.
 *
 * Usage:
 * ```
 * Toast(
 *     message = "Changes saved successfully",
 *     variant = ToastVariant.Success,
 *     onDismiss = { /* handle dismiss */ },
 * )
 *
 * Toast(
 *     message = "Failed to upload file",
 *     variant = ToastVariant.Destructive,
 *     onDismiss = { /* handle dismiss */ },
 *     actionLabel = "Retry",
 *     onAction = { retryUpload() },
 * )
 * ```
 *
 * @param message The notification text.
 * @param variant Visual variant controlling accent color.
 * @param onDismiss Called when the dismiss button is clicked.
 * @param modifier Modifier applied to the toast card.
 * @param actionLabel Optional action button text.
 * @param onAction Callback invoked when the action button is clicked.
 * @param label Accessibility label for screen readers.
 */
@Composable
fun Toast(
    message: String,
    variant: ToastVariant = ToastVariant.Default,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    label: String = "",
) {
    val colors = RikkaTheme.colors
    val shapes = RikkaTheme.shapes
    val spacing = RikkaTheme.spacing
    val resolved = resolveColors(variant)

    val accessibilityLabel = label.ifEmpty {
        when (variant) {
            ToastVariant.Default -> "Notification: $message"
            ToastVariant.Success -> "Success: $message"
            ToastVariant.Destructive -> "Error: $message"
            ToastVariant.Warning -> "Warning: $message"
        }
    }

    Row(
        modifier = modifier
            .widthIn(max = 420.dp)
            .semantics(mergeDescendants = true) {
                contentDescription = accessibilityLabel
                liveRegion = LiveRegionMode.Polite
                dismiss { onDismiss(); true }
            }
            .shadow(8.dp, shapes.lg)
            .border(1.dp, colors.border, shapes.lg)
            .background(colors.popover, shapes.lg)
            .clip(shapes.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // ─── Left accent border ──────────────────────
        if (resolved.accent != Color.Transparent) {
            Box(
                modifier = Modifier
                    .size(width = 4.dp, height = 48.dp)
                    .background(resolved.accent),
            )
        }

        // ─── Content ─────────────────────────────────
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = if (resolved.accent != Color.Transparent) {
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
                val actionInteraction = remember {
                    MutableInteractionSource()
                }
                val actionHovered by actionInteraction
                    .collectIsHoveredAsState()

                Text(
                    text = actionLabel,
                    variant = TextVariant.Small,
                    color = if (actionHovered) {
                        colors.primary.copy(alpha = 0.8f)
                    } else {
                        colors.primary
                    },
                    modifier = Modifier
                        .hoverable(actionInteraction)
                        .clickable(
                            interactionSource = actionInteraction,
                            indication = null,
                            role = Role.Button,
                            onClick = {
                                onAction()
                                onDismiss()
                            },
                        )
                        .semantics {
                            contentDescription = actionLabel
                        }
                        .padding(
                            horizontal = spacing.sm,
                            vertical = spacing.xs,
                        ),
                )
            }
        }

        // ─── Dismiss button ──────────────────────────
        val dismissInteraction = remember {
            MutableInteractionSource()
        }
        val dismissHovered by dismissInteraction
            .collectIsHoveredAsState()

        Box(
            modifier = Modifier
                .hoverable(dismissInteraction)
                .clickable(
                    interactionSource = dismissInteraction,
                    indication = null,
                    role = Role.Button,
                    onClick = onDismiss,
                )
                .semantics {
                    contentDescription = "Dismiss notification"
                }
                .padding(spacing.md),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = RikkaIcons.X,
                contentDescription = null,
                tint = if (dismissHovered) {
                    colors.foreground
                } else {
                    colors.mutedForeground
                },
            )
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
        ToastVariant.Default -> ToastColors(
            accent = Color.Transparent,
        )

        ToastVariant.Success -> ToastColors(
            accent = Color(0xFF22C55E),
        )

        ToastVariant.Destructive -> ToastColors(
            accent = colors.destructive,
        )

        ToastVariant.Warning -> ToastColors(
            accent = Color(0xFFF59E0B),
        )
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
