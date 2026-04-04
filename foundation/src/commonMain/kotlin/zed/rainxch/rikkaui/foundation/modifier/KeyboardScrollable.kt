package zed.rainxch.rikkaui.foundation.modifier

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
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
