package zed.rainxch.rikkaui.docs

sealed interface DocsAction {
    data class SelectPage(
        val id: String,
    ) : DocsAction

    data class NavigateToComponent(
        val componentId: String,
    ) : DocsAction
}
