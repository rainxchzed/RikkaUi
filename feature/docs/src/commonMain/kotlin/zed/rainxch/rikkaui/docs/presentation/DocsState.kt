package zed.rainxch.rikkaui.docs.presentation

import zed.rainxch.rikkaui.docs.catalog.GuideIds

/**
 * Immutable state for the documentation page.
 *
 * @param selectedId The ID of the currently selected page (guide or component).
 */
data class DocsState(
    val selectedId: String = GuideIds.INTRODUCTION,
)
