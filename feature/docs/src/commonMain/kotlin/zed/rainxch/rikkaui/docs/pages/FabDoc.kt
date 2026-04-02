package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import rikkaui.feature.docs.generated.resources.component_fab_name
import rikkaui.feature.docs.generated.resources.fab_demo_add
import rikkaui.feature.docs.generated.resources.fab_demo_create
import rikkaui.feature.docs.generated.resources.fab_demo_delete
import rikkaui.feature.docs.generated.resources.fab_demo_disabled
import rikkaui.feature.docs.generated.resources.fab_demo_enabled
import rikkaui.feature.docs.generated.resources.fab_demo_loading
import rikkaui.feature.docs.generated.resources.fab_demo_new_note
import rikkaui.feature.docs.generated.resources.fab_do_primary_desc
import rikkaui.feature.docs.generated.resources.fab_dont_primary_desc
import rikkaui.feature.docs.generated.resources.fab_page_desc
import rikkaui.feature.docs.generated.resources.fab_prop_animation_desc
import rikkaui.feature.docs.generated.resources.fab_prop_colors_desc
import rikkaui.feature.docs.generated.resources.fab_prop_content_desc
import rikkaui.feature.docs.generated.resources.fab_prop_elevation_desc
import rikkaui.feature.docs.generated.resources.fab_prop_enabled_desc
import rikkaui.feature.docs.generated.resources.fab_prop_expanded_desc
import rikkaui.feature.docs.generated.resources.fab_prop_icon_desc
import rikkaui.feature.docs.generated.resources.fab_prop_interaction_desc
import rikkaui.feature.docs.generated.resources.fab_prop_label_desc
import rikkaui.feature.docs.generated.resources.fab_prop_loading_desc
import rikkaui.feature.docs.generated.resources.fab_prop_onclick_desc
import rikkaui.feature.docs.generated.resources.fab_prop_size_desc
import rikkaui.feature.docs.generated.resources.fab_prop_variant_desc
import rikkaui.feature.docs.generated.resources.fab_section_dos_donts
import rikkaui.feature.docs.generated.resources.fab_section_expanded
import rikkaui.feature.docs.generated.resources.section_animations
import rikkaui.feature.docs.generated.resources.section_api_reference
import rikkaui.feature.docs.generated.resources.section_sizes
import rikkaui.feature.docs.generated.resources.section_states
import rikkaui.feature.docs.generated.resources.section_usage
import rikkaui.feature.docs.generated.resources.section_variants
import zed.rainxch.rikkaui.components.ui.fab.Fab
import zed.rainxch.rikkaui.components.ui.fab.FabAnimation
import zed.rainxch.rikkaui.components.ui.fab.FabSize
import zed.rainxch.rikkaui.components.ui.fab.FabVariant
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.docs.catalog.ComponentFamilies
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentFamily
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DoAndDont
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.TabbedDocPage
import zed.rainxch.rikkaui.docs.components.VariantSelector
import zed.rainxch.rikkaui.foundation.RikkaTheme

@Composable
fun FabDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_fab_name),
        description = stringResource(Res.string.fab_page_desc),
    )

    ComponentFamily(
        related = ComponentFamilies.BUTTONS,
        currentId = "fab",
    )

    TabbedDocPage(
        overview = { FabOverviewTab() },
        usage = { FabUsageTab() },
        api = { FabApiTab() },
    )
}

// ─── Overview Tab ───────────────────────────────────────────

@Composable
private fun FabOverviewTab() {
    DocSection(stringResource(Res.string.section_variants)) {
        var selectedVariant by remember {
            mutableStateOf(FabVariant.Default.name)
        }

        VariantSelector(
            options = FabVariant.entries.map { it.name },
            selected = selectedVariant,
            onSelect = { selectedVariant = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val variant = FabVariant.valueOf(selectedVariant)

        DemoBox {
            Fab(
                icon = RikkaIcons.Plus,
                label = stringResource(Res.string.fab_demo_add),
                onClick = {},
                variant = variant,
            )
        }
    }

    DocSection(stringResource(Res.string.section_sizes)) {
        var selectedSize by remember {
            mutableStateOf(FabSize.Default.name)
        }

        VariantSelector(
            options = FabSize.entries.map { it.name },
            selected = selectedSize,
            onSelect = { selectedSize = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val size = FabSize.valueOf(selectedSize)

        DemoBox {
            Fab(
                icon = RikkaIcons.Plus,
                label = stringResource(Res.string.fab_demo_add),
                onClick = {},
                size = size,
            )
        }
    }

    DocSection(stringResource(Res.string.fab_section_expanded)) {
        DemoBox {
            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.md,
                    ),
            ) {
                Fab(
                    icon = RikkaIcons.Plus,
                    label = stringResource(Res.string.fab_demo_new_note),
                    onClick = {},
                    expanded = true,
                )
                Fab(
                    icon = RikkaIcons.Edit,
                    label = stringResource(Res.string.fab_demo_create),
                    onClick = {},
                    expanded = true,
                    variant = FabVariant.Secondary,
                )
            }
        }
    }

    DocSection(stringResource(Res.string.section_animations)) {
        var selectedAnim by remember {
            mutableStateOf(FabAnimation.Scale.name)
        }

        VariantSelector(
            options = FabAnimation.entries.map { it.name },
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = FabAnimation.valueOf(selectedAnim)

        DemoBox {
            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.md,
                    ),
            ) {
                Fab(
                    icon = RikkaIcons.Plus,
                    label = stringResource(Res.string.fab_demo_add),
                    onClick = {},
                    animation = animation,
                )
                Fab(
                    icon = RikkaIcons.Trash,
                    label = stringResource(Res.string.fab_demo_delete),
                    onClick = {},
                    variant = FabVariant.Destructive,
                    animation = animation,
                )
            }
        }
    }

    DocSection(stringResource(Res.string.section_states)) {
        DemoBox {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
            ) {
                Fab(
                    icon = RikkaIcons.Plus,
                    label = stringResource(Res.string.fab_demo_enabled),
                    onClick = {},
                    expanded = true,
                )
                Fab(
                    icon = RikkaIcons.Plus,
                    label = stringResource(Res.string.fab_demo_disabled),
                    onClick = {},
                    enabled = false,
                    expanded = true,
                )
                Fab(
                    icon = RikkaIcons.Plus,
                    label = stringResource(Res.string.fab_demo_loading),
                    onClick = {},
                    loading = true,
                    expanded = true,
                )
            }
        }
    }
}

// ─── Usage Tab ──────────────────────────────────────────────

@Composable
private fun FabUsageTab() {
    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
// Icon-only FAB
Fab(
    icon = RikkaIcons.Plus,
    label = "Add item",
    onClick = { addItem() },
)

// Extended FAB with text label
Fab(
    icon = RikkaIcons.Plus,
    label = "New note",
    onClick = { addNote() },
    expanded = true,
)

// Custom content FAB
Fab(
    label = "Create",
    onClick = { create() },
) {
    Icon(RikkaIcons.Plus, null)
    Text("Create")
}

// Destructive variant, large size
Fab(
    icon = RikkaIcons.Trash,
    label = "Delete all",
    onClick = { deleteAll() },
    variant = FabVariant.Destructive,
    size = FabSize.Large,
)
            """.trimIndent(),
        )
    }

    DocSection(stringResource(Res.string.fab_section_dos_donts)) {
        DoAndDont(
            doContent = {
                Fab(
                    icon = RikkaIcons.Plus,
                    label = "Add",
                    onClick = {},
                )
            },
            doDescription = stringResource(Res.string.fab_do_primary_desc),
            dontContent = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                ) {
                    Fab(
                        icon = RikkaIcons.Plus,
                        label = "Add",
                        onClick = {},
                        size = FabSize.Small,
                    )
                    Fab(
                        icon = RikkaIcons.Edit,
                        label = "Edit",
                        onClick = {},
                        size = FabSize.Small,
                    )
                    Fab(
                        icon = RikkaIcons.Trash,
                        label = "Delete",
                        onClick = {},
                        size = FabSize.Small,
                        variant = FabVariant.Destructive,
                    )
                }
            },
            dontDescription = stringResource(Res.string.fab_dont_primary_desc),
        )
    }
}

// ─── API Tab ────────────────────────────────────────────────

@Composable
private fun FabApiTab() {
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "icon",
                    "ImageVector",
                    "-",
                    stringResource(Res.string.fab_prop_icon_desc),
                ),
                PropInfo(
                    "label",
                    "String",
                    "-",
                    stringResource(Res.string.fab_prop_label_desc),
                ),
                PropInfo(
                    "onClick",
                    "() -> Unit",
                    "-",
                    stringResource(Res.string.fab_prop_onclick_desc),
                ),
                PropInfo(
                    "variant",
                    "FabVariant",
                    "Default",
                    stringResource(Res.string.fab_prop_variant_desc),
                ),
                PropInfo(
                    "size",
                    "FabSize",
                    "Default",
                    stringResource(Res.string.fab_prop_size_desc),
                ),
                PropInfo(
                    "animation",
                    "FabAnimation",
                    "Scale",
                    stringResource(Res.string.fab_prop_animation_desc),
                ),
                PropInfo(
                    "expanded",
                    "Boolean",
                    "false",
                    stringResource(Res.string.fab_prop_expanded_desc),
                ),
                PropInfo(
                    "enabled",
                    "Boolean",
                    "true",
                    stringResource(Res.string.fab_prop_enabled_desc),
                ),
                PropInfo(
                    "loading",
                    "Boolean",
                    "false",
                    stringResource(Res.string.fab_prop_loading_desc),
                ),
                PropInfo(
                    "colors",
                    "FabColorValues",
                    "FabDefaults.colors(variant)",
                    stringResource(Res.string.fab_prop_colors_desc),
                ),
                PropInfo(
                    "elevation",
                    "Dp",
                    "RikkaTheme.elevation.high",
                    stringResource(Res.string.fab_prop_elevation_desc),
                ),
                PropInfo(
                    "interactionSource",
                    "MutableInteractionSource?",
                    "null",
                    stringResource(Res.string.fab_prop_interaction_desc),
                ),
                PropInfo(
                    "content",
                    "@Composable () -> Unit",
                    "-",
                    stringResource(Res.string.fab_prop_content_desc),
                ),
            ),
        )
    }
}
