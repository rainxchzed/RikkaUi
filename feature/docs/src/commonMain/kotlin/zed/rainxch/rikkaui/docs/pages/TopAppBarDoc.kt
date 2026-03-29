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
import rikkaui.feature.docs.generated.resources.Res
import rikkaui.feature.docs.generated.resources.*
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.button.IconButton
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.topappbar.TopAppBar
import zed.rainxch.rikkaui.components.ui.topappbar.TopAppBarSize
import zed.rainxch.rikkaui.components.ui.topappbar.TopAppBarVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector

/**
 * Documentation page for the TopAppBar component.
 *
 * Demonstrates variants, sizes, navigation icons, action
 * slots, centered titles, and color transitions.
 */
@Composable
fun TopAppBarDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_top_app_bar_name),
        description = stringResource(Res.string.top_app_bar_page_desc),
    )

    // ─── Variants ───────────────────────────────────────────
    DocSection(stringResource(Res.string.section_variants)) {
        var selectedVariant by remember { mutableStateOf("Default") }

        VariantSelector(
            options = listOf("Default", "Transparent"),
            selected = selectedVariant,
            onSelect = { selectedVariant = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val variant =
            when (selectedVariant) {
                "Transparent" -> TopAppBarVariant.Transparent
                else -> TopAppBarVariant.Default
            }

        DemoBox {
            TopAppBar(
                title = stringResource(Res.string.top_app_bar_demo_dashboard),
                variant = variant,
            )
        }
    }

    // ─── Sizes ──────────────────────────────────────────────
    DocSection(stringResource(Res.string.section_sizes)) {
        var selectedSize by remember { mutableStateOf("Small") }

        VariantSelector(
            options = listOf("Small", "Medium"),
            selected = selectedSize,
            onSelect = { selectedSize = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val size =
            when (selectedSize) {
                "Medium" -> TopAppBarSize.Medium
                else -> TopAppBarSize.Small
            }

        DemoBox {
            TopAppBar(
                title = stringResource(Res.string.top_app_bar_demo_settings),
                size = size,
            )
        }
    }

    // ─── With Navigation & Actions ──────────────────────────
    DocSection(stringResource(Res.string.top_app_bar_section_nav_actions)) {
        DemoBox {
            TopAppBar(
                title = stringResource(Res.string.top_app_bar_demo_profile),
                navigationIcon = {
                    IconButton(
                        icon = RikkaIcons.ArrowLeft,
                        contentDescription = stringResource(
                            Res.string.top_app_bar_demo_back,
                        ),
                        onClick = {},
                    )
                },
                actions = {
                    IconButton(
                        icon = RikkaIcons.Settings,
                        contentDescription = stringResource(
                            Res.string.top_app_bar_demo_settings,
                        ),
                        onClick = {},
                    )
                },
                centerTitle = true,
            )
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
TopAppBar(title = "Dashboard")

// With navigation and actions
TopAppBar(
    title = "Settings",
    navigationIcon = {
        Button(
            onClick = { navigateBack() },
            variant = ButtonVariant.Ghost,
            size = ButtonSize.Icon,
        ) { Icon(RikkaIcons.ArrowLeft) }
    },
    actions = {
        Button(
            onClick = {},
            variant = ButtonVariant.Ghost,
            size = ButtonSize.Icon,
        ) { Icon(RikkaIcons.Settings) }
    },
    size = TopAppBarSize.Medium,
    centerTitle = true,
    elevation = 2.dp,
)
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "title",
                    "String / () -> Unit",
                    "required",
                    stringResource(Res.string.top_app_bar_prop_title_desc),
                ),
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(Res.string.top_app_bar_prop_modifier_desc),
                ),
                PropInfo(
                    "navigationIcon",
                    "() -> Unit",
                    "{}",
                    stringResource(Res.string.top_app_bar_prop_nav_icon_desc),
                ),
                PropInfo(
                    "actions",
                    "RowScope.() -> Unit",
                    "{}",
                    stringResource(Res.string.top_app_bar_prop_actions_desc),
                ),
                PropInfo(
                    "variant",
                    "TopAppBarVariant",
                    "Default",
                    stringResource(Res.string.top_app_bar_prop_variant_desc),
                ),
                PropInfo(
                    "size",
                    "TopAppBarSize",
                    "Small",
                    stringResource(Res.string.top_app_bar_prop_size_desc),
                ),
                PropInfo(
                    "centerTitle",
                    "Boolean",
                    "false",
                    stringResource(Res.string.top_app_bar_prop_center_title_desc),
                ),
                PropInfo(
                    "elevation",
                    "Dp",
                    "0.dp",
                    stringResource(Res.string.top_app_bar_prop_elevation_desc),
                ),
                PropInfo(
                    "colorTransition",
                    "TopAppBarColorTransition",
                    "None",
                    stringResource(Res.string.top_app_bar_prop_color_transition_desc),
                ),
            ),
        )
    }
}
