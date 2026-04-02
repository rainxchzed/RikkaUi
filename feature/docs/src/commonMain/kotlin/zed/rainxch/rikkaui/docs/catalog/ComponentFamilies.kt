package zed.rainxch.rikkaui.docs.catalog

/**
 * Pre-defined component families for the "See also" chip row.
 *
 * Each list contains (componentId, displayName) pairs.
 * Components in the same family share related functionality
 * and are displayed together in [ComponentFamily].
 */
object ComponentFamilies {
    /** Button, Icon Button (inside Button doc), FAB */
    val BUTTONS: List<Pair<String, String>> =
        listOf(
            "button" to "Button",
            "fab" to "FAB",
        )

    /** Dialog, Alert Dialog, Sheet */
    val DIALOGS: List<Pair<String, String>> =
        listOf(
            "dialog" to "Dialog",
            "alert-dialog" to "Alert Dialog",
            "sheet" to "Sheet",
        )

    /** Popover, Tooltip, Hover Card, Dropdown Menu, Context Menu */
    val POPUPS: List<Pair<String, String>> =
        listOf(
            "popover" to "Popover",
            "tooltip" to "Tooltip",
            "hover-card" to "Hover Card",
            "dropdown-menu" to "Dropdown Menu",
            "context-menu" to "Context Menu",
        )

    /** Input, Textarea, Select */
    val TEXT_INPUTS: List<Pair<String, String>> =
        listOf(
            "input" to "Input",
            "textarea" to "Textarea",
            "select" to "Select",
        )

    /** Checkbox, Radio, Toggle, Toggle Group */
    val SELECTION: List<Pair<String, String>> =
        listOf(
            "checkbox" to "Checkbox",
            "radio" to "Radio",
            "toggle" to "Toggle",
            "toggle-group" to "Toggle Group",
        )

    /** Tabs, Navigation Bar, Top App Bar, Breadcrumb, Pagination */
    val NAVIGATION: List<Pair<String, String>> =
        listOf(
            "tabs" to "Tabs",
            "navigation-bar" to "Navigation Bar",
            "top-app-bar" to "Top App Bar",
            "breadcrumb" to "Breadcrumb",
            "pagination" to "Pagination",
        )

    /** Progress, Spinner, Skeleton */
    val LOADING: List<Pair<String, String>> =
        listOf(
            "progress" to "Progress",
            "spinner" to "Spinner",
            "skeleton" to "Skeleton",
        )

    /** Alert, Toast */
    val MESSAGING: List<Pair<String, String>> =
        listOf(
            "alert" to "Alert",
            "toast" to "Toast",
        )

    /** Accordion, Collapsible */
    val EXPANDABLE: List<Pair<String, String>> =
        listOf(
            "accordion" to "Accordion",
            "collapsible" to "Collapsible",
        )
}
