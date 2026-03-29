package zed.rainxch.rikkaui.docs

/**
 * Immutable state for the documentation page.
 *
 * @param selectedId The ID of the currently selected page (guide or component).
 */
data class DocsState(
    val selectedId: String = "introduction",
)
