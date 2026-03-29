package zed.rainxch.rikkaui.docs.catalog

import zed.rainxch.rikkaui.docs.pages.AccordionDoc
import zed.rainxch.rikkaui.docs.pages.AlertDialogDoc
import zed.rainxch.rikkaui.docs.pages.AlertDoc
import zed.rainxch.rikkaui.docs.pages.AvatarDoc
import zed.rainxch.rikkaui.docs.pages.BadgeDoc
import zed.rainxch.rikkaui.docs.pages.BreadcrumbDoc
import zed.rainxch.rikkaui.docs.pages.ButtonDoc
import zed.rainxch.rikkaui.docs.pages.CardDoc
import zed.rainxch.rikkaui.docs.pages.CheckboxDoc
import zed.rainxch.rikkaui.docs.pages.CollapsibleDoc
import zed.rainxch.rikkaui.docs.pages.ContextMenuDoc
import zed.rainxch.rikkaui.docs.pages.DialogDoc
import zed.rainxch.rikkaui.docs.pages.DropdownMenuDoc
import zed.rainxch.rikkaui.docs.pages.HoverCardDoc
import zed.rainxch.rikkaui.docs.pages.InputDoc
import zed.rainxch.rikkaui.docs.pages.KbdDoc
import zed.rainxch.rikkaui.docs.pages.ListDoc
import zed.rainxch.rikkaui.docs.pages.LabelDoc
import zed.rainxch.rikkaui.docs.pages.NavigationBarDoc
import zed.rainxch.rikkaui.docs.pages.PaginationDoc
import zed.rainxch.rikkaui.docs.pages.PopoverDoc
import zed.rainxch.rikkaui.docs.pages.ProgressDoc
import zed.rainxch.rikkaui.docs.pages.RadioDoc
import zed.rainxch.rikkaui.docs.pages.ScaffoldDoc
import zed.rainxch.rikkaui.docs.pages.ScrollAreaDoc
import zed.rainxch.rikkaui.docs.pages.SelectDoc
import zed.rainxch.rikkaui.docs.pages.SeparatorDoc
import zed.rainxch.rikkaui.docs.pages.SheetDoc
import zed.rainxch.rikkaui.docs.pages.SkeletonDoc
import zed.rainxch.rikkaui.docs.pages.SliderDoc
import zed.rainxch.rikkaui.docs.pages.SpinnerDoc
import zed.rainxch.rikkaui.docs.pages.TableDoc
import zed.rainxch.rikkaui.docs.pages.TabsDoc
import zed.rainxch.rikkaui.docs.pages.TextDoc
import zed.rainxch.rikkaui.docs.pages.TextareaDoc
import zed.rainxch.rikkaui.docs.pages.ToastDoc
import zed.rainxch.rikkaui.docs.pages.ToggleDoc
import zed.rainxch.rikkaui.docs.pages.ToggleGroupDoc
import zed.rainxch.rikkaui.docs.pages.TooltipDoc
import zed.rainxch.rikkaui.docs.pages.TopAppBarDoc

/**
 * Central registry of all RikkaUi components with their
 * documentation pages.
 *
 * Components are sorted alphabetically within each category.
 */
object ComponentRegistry {
    val entries: List<ComponentEntry> =
        listOf(
            // ─── Layout ──────────────────────────────
            ComponentEntry(
                id = "card",
                name = "Card",
                description =
                    "A container with border, shadow, " +
                        "and structured sections.",
                category = ComponentCategory.Layout,
                content = { CardDoc() },
            ),
            ComponentEntry(
                id = "separator",
                name = "Separator",
                description = "A horizontal or vertical divider.",
                category = ComponentCategory.Layout,
                content = { SeparatorDoc() },
            ),
            ComponentEntry(
                id = "scaffold",
                name = "Scaffold",
                description =
                    "Page layout with top bar, bottom bar, " +
                        "and FAB slots.",
                category = ComponentCategory.Layout,
                content = { ScaffoldDoc() },
            ),
            ComponentEntry(
                id = "scroll-area",
                name = "Scroll Area",
                description =
                    "Custom scrollbar container for " +
                        "scrollable content.",
                category = ComponentCategory.Layout,
                content = { ScrollAreaDoc() },
            ),
            ComponentEntry(
                id = "accordion",
                name = "Accordion",
                description = "Expandable content sections.",
                category = ComponentCategory.Layout,
                content = { AccordionDoc() },
            ),
            ComponentEntry(
                id = "collapsible",
                name = "Collapsible",
                description =
                    "Toggle content visibility with a trigger.",
                category = ComponentCategory.Layout,
                content = { CollapsibleDoc() },
            ),
            // ─── Forms ───────────────────────────────
            ComponentEntry(
                id = "button",
                name = "Button",
                description =
                    "Interactive button with 6 variants, " +
                        "4 sizes, and 3 animations.",
                category = ComponentCategory.Forms,
                content = { ButtonDoc() },
            ),
            ComponentEntry(
                id = "input",
                name = "Input",
                description =
                    "Single-line text input with focus " +
                        "animations.",
                category = ComponentCategory.Forms,
                content = { InputDoc() },
            ),
            ComponentEntry(
                id = "textarea",
                name = "Textarea",
                description =
                    "Multi-line text input with char count.",
                category = ComponentCategory.Forms,
                content = { TextareaDoc() },
            ),
            ComponentEntry(
                id = "select",
                name = "Select",
                description =
                    "Dropdown select with search " +
                        "and animations.",
                category = ComponentCategory.Forms,
                content = { SelectDoc() },
            ),
            ComponentEntry(
                id = "checkbox",
                name = "Checkbox",
                description =
                    "Animated checkmark with label.",
                category = ComponentCategory.Forms,
                content = { CheckboxDoc() },
            ),
            ComponentEntry(
                id = "radio",
                name = "Radio",
                description = "Radio selection control.",
                category = ComponentCategory.Forms,
                content = { RadioDoc() },
            ),
            ComponentEntry(
                id = "toggle",
                name = "Toggle",
                description =
                    "Spring-animated switch with 2 sizes.",
                category = ComponentCategory.Forms,
                content = { ToggleDoc() },
            ),
            ComponentEntry(
                id = "slider",
                name = "Slider",
                description =
                    "Draggable range input with " +
                        "customizable track.",
                category = ComponentCategory.Forms,
                content = { SliderDoc() },
            ),
            ComponentEntry(
                id = "label",
                name = "Label",
                description =
                    "Form label with required indicator.",
                category = ComponentCategory.Forms,
                content = { LabelDoc() },
            ),
            // ─── Data Display ────────────────────────
            ComponentEntry(
                id = "text",
                name = "Text",
                description =
                    "Typography component with 9 variants.",
                category = ComponentCategory.DataDisplay,
                content = { TextDoc() },
            ),
            ComponentEntry(
                id = "badge",
                name = "Badge",
                description =
                    "Small status indicator with " +
                        "4 variants and 3 sizes.",
                category = ComponentCategory.DataDisplay,
                content = { BadgeDoc() },
            ),
            ComponentEntry(
                id = "avatar",
                name = "Avatar",
                description =
                    "Fallback initials with status indicator.",
                category = ComponentCategory.DataDisplay,
                content = { AvatarDoc() },
            ),
            ComponentEntry(
                id = "table",
                name = "Table",
                description =
                    "Data table with hover, stripe, " +
                        "and border styles.",
                category = ComponentCategory.DataDisplay,
                content = { TableDoc() },
            ),
            ComponentEntry(
                id = "kbd",
                name = "Kbd",
                description =
                    "Keyboard shortcut indicator.",
                category = ComponentCategory.DataDisplay,
                content = { KbdDoc() },
            ),
            ComponentEntry(
                id = "list",
                name = "List",
                description =
                    "Styled lists with bullets, numbers, "
                        + "or custom content.",
                category = ComponentCategory.DataDisplay,
                content = { ListDoc() },
            ),
            // ─── Feedback ────────────────────────────
            ComponentEntry(
                id = "alert",
                name = "Alert",
                description =
                    "Callout for important messages.",
                category = ComponentCategory.Feedback,
                content = { AlertDoc() },
            ),
            ComponentEntry(
                id = "toast",
                name = "Toast",
                description =
                    "Temporary notification with " +
                        "swipe-to-dismiss.",
                category = ComponentCategory.Feedback,
                content = { ToastDoc() },
            ),
            ComponentEntry(
                id = "progress",
                name = "Progress",
                description = "Animated progress bar.",
                category = ComponentCategory.Feedback,
                content = { ProgressDoc() },
            ),
            ComponentEntry(
                id = "skeleton",
                name = "Skeleton",
                description = "Pulsing loading placeholder.",
                category = ComponentCategory.Feedback,
                content = { SkeletonDoc() },
            ),
            ComponentEntry(
                id = "spinner",
                name = "Spinner",
                description = "Rotating loading indicator.",
                category = ComponentCategory.Feedback,
                content = { SpinnerDoc() },
            ),
            // ─── Overlays ────────────────────────────
            ComponentEntry(
                id = "dialog",
                name = "Dialog",
                description =
                    "Modal dialog with header and footer.",
                category = ComponentCategory.Overlays,
                content = { DialogDoc() },
            ),
            ComponentEntry(
                id = "alert-dialog",
                name = "Alert Dialog",
                description =
                    "Non-dismissable confirmation dialog.",
                category = ComponentCategory.Overlays,
                content = { AlertDialogDoc() },
            ),
            ComponentEntry(
                id = "sheet",
                name = "Sheet",
                description =
                    "Slide-in panel from any edge.",
                category = ComponentCategory.Overlays,
                content = { SheetDoc() },
            ),
            ComponentEntry(
                id = "popover",
                name = "Popover",
                description =
                    "Click-triggered floating content.",
                category = ComponentCategory.Overlays,
                content = { PopoverDoc() },
            ),
            ComponentEntry(
                id = "tooltip",
                name = "Tooltip",
                description =
                    "Hover-triggered informational popup.",
                category = ComponentCategory.Overlays,
                content = { TooltipDoc() },
            ),
            ComponentEntry(
                id = "hover-card",
                name = "Hover Card",
                description =
                    "Rich hover-triggered preview card.",
                category = ComponentCategory.Overlays,
                content = { HoverCardDoc() },
            ),
            ComponentEntry(
                id = "dropdown-menu",
                name = "Dropdown Menu",
                description =
                    "Action menu with items and labels.",
                category = ComponentCategory.Overlays,
                content = { DropdownMenuDoc() },
            ),
            ComponentEntry(
                id = "context-menu",
                name = "Context Menu",
                description =
                    "Long-press/right-click action menu.",
                category = ComponentCategory.Overlays,
                content = { ContextMenuDoc() },
            ),
            // ─── Navigation ──────────────────────────
            ComponentEntry(
                id = "tabs",
                name = "Tabs",
                description =
                    "Tab-based content switching.",
                category = ComponentCategory.Navigation,
                content = { TabsDoc() },
            ),
            ComponentEntry(
                id = "breadcrumb",
                name = "Breadcrumb",
                description =
                    "Navigation trail with auto-separators.",
                category = ComponentCategory.Navigation,
                content = { BreadcrumbDoc() },
            ),
            ComponentEntry(
                id = "pagination",
                name = "Pagination",
                description =
                    "Page navigation with smart ranges.",
                category = ComponentCategory.Navigation,
                content = { PaginationDoc() },
            ),
            ComponentEntry(
                id = "navigation-bar",
                name = "Navigation Bar",
                description =
                    "Bottom navigation with animated " +
                        "pill indicator.",
                category = ComponentCategory.Navigation,
                content = { NavigationBarDoc() },
            ),
            ComponentEntry(
                id = "top-app-bar",
                name = "Top App Bar",
                description =
                    "Header bar with title and actions.",
                category = ComponentCategory.Navigation,
                content = { TopAppBarDoc() },
            ),
            ComponentEntry(
                id = "toggle-group",
                name = "Toggle Group",
                description =
                    "Grouped toggle buttons.",
                category = ComponentCategory.Navigation,
                content = { ToggleGroupDoc() },
            ),
        )

    /** Look up a component by its URL id. */
    fun findById(id: String): ComponentEntry? =
        entries.find { it.id == id }

    /** Group entries by category, preserving category order. */
    fun groupedByCategory():
        Map<ComponentCategory, List<ComponentEntry>> =
        ComponentCategory.entries.associateWith { cat ->
            entries.filter { it.category == cat }
        }.filterValues { it.isNotEmpty() }
}
