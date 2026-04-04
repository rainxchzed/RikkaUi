package zed.rainxch.rikkaui.foundation.modifier

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Adds keyboard scrolling to a scrollable container — zero boilerplate.
 *
 * This `@Composable` overload internally creates its own [CoroutineScope]
 * and [FocusRequester], so the call site only needs to pass the [ScrollState]
 * that is already shared with `verticalScroll()`.
 *
 * Clicking anywhere on the container grabs focus so keyboard
 * scrolling works immediately — no need to Tab first.
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
 */
@Composable
fun Modifier.keyboardScrollable(
    scrollState: ScrollState,
    scrollAmount: Float = 80f,
    pageAmount: Float = 500f,
): Modifier {
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    return keyboardScrollable(scrollState, scope, focusRequester, scrollAmount, pageAmount)
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
 * Clicking anywhere on the container grabs focus so keyboard
 * scrolling works immediately — no need to Tab first.
 *
 * Apply **before** `verticalScroll()` in the modifier chain.
 *
 * ### Usage
 * ```
 * val scrollState = rememberScrollState()
 * val scope = rememberCoroutineScope()
 * val focusRequester = remember { FocusRequester() }
 *
 * Column(
 *     modifier = Modifier
 *         .fillMaxSize()
 *         .keyboardScrollable(scrollState, scope, focusRequester)
 *         .verticalScroll(scrollState),
 * ) { ... }
 * ```
 *
 * @param scrollState The same [ScrollState] passed to `verticalScroll()`.
 * @param scope A [CoroutineScope] for animated scrolling.
 * @param focusRequester A [FocusRequester] to grab focus on click.
 * @param scrollAmount Pixels to scroll per arrow key press.
 * @param pageAmount Pixels to scroll per Page Up/Down/Space press.
 */
fun Modifier.keyboardScrollable(
    scrollState: ScrollState,
    scope: CoroutineScope,
    focusRequester: FocusRequester,
    scrollAmount: Float = 80f,
    pageAmount: Float = 500f,
): Modifier =
    this
        .focusRequester(focusRequester)
        .focusable()
        .pointerInput(Unit) {
            detectTapGestures {
                focusRequester.requestFocus()
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
