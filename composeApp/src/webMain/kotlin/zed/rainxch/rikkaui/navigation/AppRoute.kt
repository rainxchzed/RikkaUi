package zed.rainxch.rikkaui.navigation

/**
 * Top-level routes for the RikkaUi showcase website.
 *
 * @param hash URL hash fragment for browser history (e.g. "#/create").
 * @param label Display label for navigation UI.
 */
enum class AppRoute(
    val hash: String,
    val label: String,
) {
    Home("#/", "Home"),
    Creator("#/create", "Create"),
    ;

    companion object {
        /** Resolves a route from the current URL hash. */
        fun fromHash(hash: String): AppRoute = entries.find { it.hash == hash } ?: Home
    }
}
