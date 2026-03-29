package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.Res
import rikkaui.feature.docs.generated.resources.component_list_name
import rikkaui.feature.docs.generated.resources.list_demo_accessible
import rikkaui.feature.docs.generated.resources.list_demo_add_dep
import rikkaui.feature.docs.generated.resources.list_demo_all_platforms
import rikkaui.feature.docs.generated.resources.list_demo_clone_repo
import rikkaui.feature.docs.generated.resources.list_demo_composable_dsl
import rikkaui.feature.docs.generated.resources.list_demo_customize_tokens
import rikkaui.feature.docs.generated.resources.list_demo_features_title
import rikkaui.feature.docs.generated.resources.list_demo_full_theme
import rikkaui.feature.docs.generated.resources.list_demo_install_deps
import rikkaui.feature.docs.generated.resources.list_demo_no_material
import rikkaui.feature.docs.generated.resources.list_demo_run_dev
import rikkaui.feature.docs.generated.resources.list_demo_spring_anims
import rikkaui.feature.docs.generated.resources.list_demo_theme_aware
import rikkaui.feature.docs.generated.resources.list_demo_use_component
import rikkaui.feature.docs.generated.resources.list_demo_wrap_theme
import rikkaui.feature.docs.generated.resources.list_page_desc
import rikkaui.feature.docs.generated.resources.list_prop_items_desc
import rikkaui.feature.docs.generated.resources.list_prop_modifier_desc
import rikkaui.feature.docs.generated.resources.list_prop_text_variant_desc
import rikkaui.feature.docs.generated.resources.list_prop_variant_desc
import rikkaui.feature.docs.generated.resources.list_section_custom
import rikkaui.feature.docs.generated.resources.list_section_ordered
import rikkaui.feature.docs.generated.resources.list_section_unordered
import rikkaui.feature.docs.generated.resources.section_api_reference
import rikkaui.feature.docs.generated.resources.section_usage
import rikkaui.feature.docs.generated.resources.section_variants
import zed.rainxch.rikkaui.components.ui.list.ListVariant
import zed.rainxch.rikkaui.components.ui.list.RikkaList
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector
import zed.rainxch.rikkaui.foundation.RikkaTheme

@Composable
fun ListDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_list_name),
        description = stringResource(Res.string.list_page_desc),
    )

    DocSection(stringResource(Res.string.section_variants)) {
        var selected by remember { mutableStateOf("Unordered") }

        VariantSelector(
            options = listOf("Unordered", "Ordered", "None"),
            selected = selected,
            onSelect = { selected = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val variant =
            when (selected) {
                "Ordered" -> ListVariant.Ordered
                "None" -> ListVariant.None
                else -> ListVariant.Unordered
            }

        DemoBox {
            RikkaList(
                items =
                    listOf(
                        stringResource(Res.string.list_demo_clone_repo),
                        stringResource(Res.string.list_demo_install_deps),
                        stringResource(Res.string.list_demo_run_dev),
                    ),
                variant = variant,
            )
        }
    }

    DocSection(
        stringResource(Res.string.list_section_unordered),
    ) {
        DemoBox {
            RikkaList(
                items =
                    listOf(
                        stringResource(Res.string.list_demo_no_material),
                        stringResource(Res.string.list_demo_spring_anims),
                        stringResource(Res.string.list_demo_full_theme),
                        stringResource(Res.string.list_demo_all_platforms),
                    ),
            )
        }
    }

    DocSection(
        stringResource(Res.string.list_section_ordered),
    ) {
        DemoBox {
            RikkaList(
                items =
                    listOf(
                        stringResource(Res.string.list_demo_add_dep),
                        stringResource(Res.string.list_demo_wrap_theme),
                        stringResource(Res.string.list_demo_use_component),
                        stringResource(Res.string.list_demo_customize_tokens),
                    ),
                variant = ListVariant.Ordered,
            )
        }
    }

    DocSection(
        stringResource(Res.string.list_section_custom),
    ) {
        DemoBox {
            Column {
                Text(
                    stringResource(Res.string.list_demo_features_title),
                    variant = TextVariant.H4,
                )
                Spacer(Modifier.height(RikkaTheme.spacing.sm))
                RikkaList {
                    ListItem {
                        Text(
                            stringResource(Res.string.list_demo_composable_dsl),
                            variant = TextVariant.P,
                        )
                    }
                    ListItem {
                        Text(
                            stringResource(Res.string.list_demo_theme_aware),
                            variant = TextVariant.P,
                        )
                    }
                    ListItem {
                        Text(
                            stringResource(Res.string.list_demo_accessible),
                            variant = TextVariant.P,
                        )
                    }
                }
            }
        }
    }

    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
// Bullet list (default)
RikkaList(
    items = listOf("First", "Second", "Third"),
)

// Numbered list
RikkaList(
    items = listOf("Step one", "Step two"),
    variant = ListVariant.Ordered,
)

// Custom content per item
RikkaList(variant = ListVariant.Unordered) {
    ListItem { Text("Custom composable") }
    ListItem { Text("Another item") }
}
            """.trimIndent(),
        )
    }

    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "items",
                    "List<String>",
                    "-",
                    stringResource(Res.string.list_prop_items_desc),
                ),
                PropInfo(
                    "variant",
                    "ListVariant",
                    "Unordered",
                    stringResource(Res.string.list_prop_variant_desc),
                ),
                PropInfo(
                    "textVariant",
                    "TextVariant",
                    "P",
                    stringResource(Res.string.list_prop_text_variant_desc),
                ),
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(Res.string.list_prop_modifier_desc),
                ),
            ),
        )
    }
}
