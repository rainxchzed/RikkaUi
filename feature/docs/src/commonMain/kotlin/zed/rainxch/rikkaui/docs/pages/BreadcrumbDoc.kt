package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.*
import rikkaui.feature.docs.generated.resources.Res
import zed.rainxch.rikkaui.components.ui.breadcrumb.Breadcrumb
import zed.rainxch.rikkaui.components.ui.breadcrumb.BreadcrumbAnimation
import zed.rainxch.rikkaui.components.ui.breadcrumb.BreadcrumbItem
import zed.rainxch.rikkaui.components.ui.breadcrumb.BreadcrumbItemData
import zed.rainxch.rikkaui.components.ui.breadcrumb.BreadcrumbSeparator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.catalog.ComponentFamilies
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentFamily
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.DoAndDont
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.TabbedDocPage
import zed.rainxch.rikkaui.docs.components.VariantSelector
import zed.rainxch.rikkaui.foundation.RikkaTheme

@Composable
fun BreadcrumbDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_breadcrumb_name),
        description = stringResource(Res.string.breadcrumb_page_desc),
    )

    ComponentFamily(
        related = ComponentFamilies.NAVIGATION,
        currentId = "breadcrumb",
    )

    TabbedDocPage(
        overview = { BreadcrumbOverviewTab() },
        usage = { BreadcrumbUsageTab() },
        api = { BreadcrumbApiTab() },
    )
}

// ─── Overview Tab ───────────────────────────────────────────

@Composable
private fun BreadcrumbOverviewTab() {
    DocSection(
        stringResource(Res.string.breadcrumb_section_basic),
    ) {
        DemoBox {
            Breadcrumb {
                BreadcrumbItem(
                    stringResource(Res.string.breadcrumb_demo_home),
                    onClick = {},
                )
                BreadcrumbSeparator()
                BreadcrumbItem(
                    stringResource(Res.string.breadcrumb_demo_products),
                    onClick = {},
                )
                BreadcrumbSeparator()
                BreadcrumbItem(
                    stringResource(Res.string.breadcrumb_demo_widgets),
                )
            }
        }
    }

    DocSection(
        stringResource(Res.string.breadcrumb_section_data_driven),
    ) {
        var selectedAnim by remember { mutableStateOf("None") }

        VariantSelector(
            options = listOf("None", "Fade", "Slide"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation =
            when (selectedAnim) {
                "Fade" -> BreadcrumbAnimation.Fade
                "Slide" -> BreadcrumbAnimation.Slide
                else -> BreadcrumbAnimation.None
            }

        DemoBox {
            Breadcrumb(
                items =
                    listOf(
                        BreadcrumbItemData(
                            stringResource(Res.string.breadcrumb_demo_home),
                            onClick = {},
                        ),
                        BreadcrumbItemData(
                            stringResource(Res.string.breadcrumb_demo_products),
                            onClick = {},
                        ),
                        BreadcrumbItemData(
                            stringResource(Res.string.breadcrumb_demo_widgets),
                            onClick = {},
                        ),
                        BreadcrumbItemData(
                            stringResource(Res.string.breadcrumb_demo_current_page),
                        ),
                    ),
                animation = animation,
            )
        }
    }

    DocSection(
        stringResource(Res.string.breadcrumb_section_ellipsis),
    ) {
        DemoBox {
            Breadcrumb(
                items =
                    listOf(
                        BreadcrumbItemData(
                            stringResource(Res.string.breadcrumb_demo_home),
                            onClick = {},
                        ),
                        BreadcrumbItemData(
                            stringResource(Res.string.breadcrumb_demo_documents),
                            onClick = {},
                        ),
                        BreadcrumbItemData(
                            stringResource(Res.string.breadcrumb_demo_projects),
                            onClick = {},
                        ),
                        BreadcrumbItemData(
                            stringResource(Res.string.breadcrumb_demo_design),
                            onClick = {},
                        ),
                        BreadcrumbItemData(
                            stringResource(Res.string.breadcrumb_demo_components),
                        ),
                    ),
                maxVisibleItems = 3,
            )
        }
    }
}

// ─── Usage Tab ──────────────────────────────────────────────

@Composable
private fun BreadcrumbUsageTab() {
    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
// Manual composition — full control
Breadcrumb {
    BreadcrumbItem("Home", onClick = { nav() })
    BreadcrumbSeparator()
    BreadcrumbItem("Products", onClick = { nav() })
    BreadcrumbSeparator()
    BreadcrumbItem("Current Page")
}

// Data-driven — pass a list, get separators for free
Breadcrumb(
    items = listOf(
        BreadcrumbItemData("Home", onClick = { nav() }),
        BreadcrumbItemData("Products", onClick = { nav() }),
        BreadcrumbItemData("Current Page"),
    ),
    animation = BreadcrumbAnimation.Fade,
    maxVisibleItems = 3,
)
            """.trimIndent(),
        )
    }

    DocSection(stringResource(Res.string.breadcrumb_section_dos_donts)) {
        DoAndDont(
            doContent = {
                Breadcrumb(
                    items = listOf(
                        BreadcrumbItemData(
                            stringResource(Res.string.breadcrumb_demo_home),
                            onClick = {},
                        ),
                        BreadcrumbItemData(
                            stringResource(Res.string.breadcrumb_demo_products),
                            onClick = {},
                        ),
                        BreadcrumbItemData(
                            stringResource(Res.string.breadcrumb_demo_widgets),
                        ),
                    ),
                )
            },
            doDescription = stringResource(Res.string.breadcrumb_do_full_path_desc),
            dontContent = {
                Breadcrumb {
                    BreadcrumbItem(
                        stringResource(Res.string.breadcrumb_demo_widgets),
                    )
                }
            },
            dontDescription = stringResource(Res.string.breadcrumb_dont_flat_desc),
        )
    }
}

// ─── API Tab ────────────────────────────────────────────────

@Composable
private fun BreadcrumbApiTab() {
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "items",
                    "List<BreadcrumbItemData>",
                    "required",
                    stringResource(Res.string.breadcrumb_prop_items_desc),
                ),
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(Res.string.breadcrumb_prop_modifier_desc),
                ),
                PropInfo(
                    "animation",
                    "BreadcrumbAnimation",
                    "None",
                    stringResource(Res.string.breadcrumb_prop_animation_desc),
                ),
                PropInfo(
                    "separator",
                    "(@Composable () -> Unit)?",
                    "null",
                    stringResource(Res.string.breadcrumb_prop_separator_desc),
                ),
                PropInfo(
                    "maxVisibleItems",
                    "Int",
                    "0",
                    stringResource(Res.string.breadcrumb_prop_max_visible_desc),
                ),
                PropInfo(
                    "onEllipsisClick",
                    "(() -> Unit)?",
                    "null",
                    stringResource(Res.string.breadcrumb_prop_ellipsis_click_desc),
                ),
            ),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        Text(
            stringResource(Res.string.breadcrumb_subsection_item_data),
            variant = TextVariant.Large,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        PropsTable(
            listOf(
                PropInfo(
                    "label",
                    "String",
                    "required",
                    stringResource(Res.string.breadcrumb_prop_label_desc),
                ),
                PropInfo(
                    "onClick",
                    "(() -> Unit)?",
                    "null",
                    stringResource(Res.string.breadcrumb_prop_onclick_desc),
                ),
            ),
        )
    }
}
