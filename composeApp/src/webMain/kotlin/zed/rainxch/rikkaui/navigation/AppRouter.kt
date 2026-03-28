package zed.rainxch.rikkaui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import kotlinx.browser.window
import org.w3c.dom.events.Event

/**
 * Syncs the [routeState] with the browser URL hash.
 *
 * - Reads the initial hash on first composition.
 * - Listens for `hashchange` events (back/forward buttons, manual edits)
 *   and updates [routeState].
 *
 * Call this once at the top of your composition.
 */
@Composable
fun BrowserHashSync(routeState: MutableState<AppRoute>) {
    DisposableEffect(Unit) {
        val initialHash = window.location.hash.ifEmpty { "#/" }
        routeState.value = AppRoute.fromHash(initialHash)

        val listener: (Event) -> Unit = {
            val hash = window.location.hash.ifEmpty { "#/" }
            routeState.value = AppRoute.fromHash(hash)
        }

        window.addEventListener("hashchange", listener)

        onDispose {
            window.removeEventListener("hashchange", listener)
        }
    }
}

/**
 * Navigates to a route by updating the browser hash.
 *
 * The [BrowserHashSync] listener will pick up the change
 * and update the route state automatically.
 */
fun navigateTo(route: AppRoute) {
    window.location.hash = route.hash
}
