package zed.rainxch.rikkaui.foundation.modifier

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Controls how a [keyboardScrollable] container acquires focus.
 */
enum class ScrollFocusMode {
    /** Automatically requests focus when the container is first composed. */
    RequestFocus,

    /** Requests focus when the pointer enters the container boundary. */
    Hover,

    /** Requests focus only when the user clicks inside the container. */
    Click,
}

/**
 * Adds keyboard scrolling to a scrollable container — zero boilerplate.
 *
 * This `@Composable` overload internally creates its own [CoroutineScope]
 * and [FocusRequester], so the call site only needs to pass the [ScrollState]
 * that is already shared with `verticalScroll()`.
 *
 * Apply **before** `verticalScroll()` in the modifier chain.
 *
 * ### Usage
 * ```
 * val scrollState = rememberScrollState()
 *
 * Column(
 *     modifier = Modifier
 *         .fillMaxSize()
 *         .keyboardScrollable(scrollState)
 *         .verticalScroll(scrollState),
 * ) { ... }
 * ```
 *
 * @param scrollState The same [ScrollState] passed to `verticalScroll()`.
 * @param focusMode How the container acquires focus for keyboard input.
 * @param scrollAmount Pixels to scroll per arrow key press.
 * @param pageAmount Pixels to scroll per Page Up/Down/Space press.
 */
@Composable
fun Modifier.keyboardScrollable(
    scrollState: ScrollState,
    focusMode: ScrollFocusMode = ScrollFocusMode.RequestFocus,
    scrollAmount: Float = 80f,
    pageAmount: Float = 500f,
): Modifier {
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }

    if (focusMode == ScrollFocusMode.RequestFocus) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }

    return keyboardScrollable(scrollState, scope, focusRequester, focusMode, scrollAmount, pageAmount)
}

/**
 * Adds keyboard scrolling to a container using [ScrollState].
 *
 * Compose's `verticalScroll` only handles wheel and touch input.
 * This modifier bridges the gap for keyboard users by handling:
 * - **Arrow Up/Down** — scroll by [scrollAmount] pixels
 * - **Space / Page Down** — scroll down by [pageAmount] pixels
 * - **Page Up** — scroll up by [pageAmount] pixels
 * - **Home** — scroll to top
 * - **End** — scroll to bottom
 *
 * Apply **before** `verticalScroll()` in the modifier chain.
 *
 * @param scrollState The same [ScrollState] passed to `verticalScroll()`.
 * @param scope A [CoroutineScope] for animated scrolling.
 * @param focusRequester A [FocusRequester] to grab focus.
 * @param focusMode How the container acquires focus for keyboard input.
 * @param scrollAmount Pixels to scroll per arrow key press.
 * @param pageAmount Pixels to scroll per Page Up/Down/Space press.
 */
fun Modifier.keyboardScrollable(
    scrollState: ScrollState,
    scope: CoroutineScope,
    focusRequester: FocusRequester,
    focusMode: ScrollFocusMode = ScrollFocusMode.RequestFocus,
    scrollAmount: Float = 80f,
    pageAmount: Float = 500f,
): Modifier =
    this
        .focusRequester(focusRequester)
        .focusable()
        .pointerInput(focusMode) {
            when (focusMode) {
                ScrollFocusMode.Hover ->
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            if (event.type == PointerEventType.Enter) {
                                focusRequester.requestFocus()
                            }
                        }
                    }

                ScrollFocusMode.Click, ScrollFocusMode.RequestFocus ->
                    detectTapGestures {
                        focusRequester.requestFocus()
                    }
            }
        }.onKeyEvent { event ->
            if (event.type != KeyEventType.KeyDown) return@onKeyEvent false

            val delta =
                when (event.key) {
                    Key.DirectionDown -> scrollAmount
                    Key.DirectionUp -> -scrollAmount
                    Key.Spacebar -> pageAmount
                    Key.PageDown -> pageAmount
                    Key.PageUp -> -pageAmount
                    Key.MoveHome -> -scrollState.value.toFloat()
                    Key.MoveEnd ->
                        (scrollState.maxValue - scrollState.value).toFloat()
                    else -> return@onKeyEvent false
                }

            scope.launch {
                scrollState.animateScrollTo(
                    (scrollState.value + delta.toInt())
                        .coerceIn(0, scrollState.maxValue),
                )
            }
            true
        }
