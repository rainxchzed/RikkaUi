package zed.rainxch.rikkaui.docs

/**
 * User intents for the documentation page.
 */
sealed interface DocsAction {
    /** User selected a page from the sidebar or compact selector. */
    data class SelectPage(val id: String) : DocsAction

    /**
     * Navigation layer requested a specific component.
     * Triggered by deep-link or route change.
     */
    data class NavigateToComponent(val componentId: String) : DocsAction
}
