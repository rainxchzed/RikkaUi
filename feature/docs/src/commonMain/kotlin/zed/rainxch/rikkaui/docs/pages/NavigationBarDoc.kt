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
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.navigationbar.NavigationBar
import zed.rainxch.rikkaui.components.ui.navigationbar.NavigationBarAnimation
import zed.rainxch.rikkaui.components.ui.navigationbar.NavigationBarItem
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

/**
 * Documentation page for the NavigationBar component.
 *
 * Demonstrates bottom navigation with icon items, animation
 * variants, and the convenience ImageVector overload.
 */
@Composable
fun NavigationBarDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_navigation_bar_name),
        description = stringResource(Res.string.nav_bar_page_desc),
    )

    ComponentFamily(
        related = ComponentFamilies.NAVIGATION,
        currentId = "navigation-bar",
    )

    TabbedDocPage(
        overview = { NavigationBarOverviewTab() },
        usage = { NavigationBarUsageTab() },
        api = { NavigationBarApiTab() },
    )
}

// ─── Overview Tab ───────────────────────────────────────────

@Composable
private fun NavigationBarOverviewTab() {
    // ─── Animation Variants ─────────────────────────────────
    DocSection(stringResource(Res.string.section_animations)) {
        var selectedAnim by remember { mutableStateOf("Spring") }
        var selectedIndex by remember { mutableStateOf(0) }

        VariantSelector(
            options = listOf("Spring", "Tween", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation =
            when (selectedAnim) {
                "Tween" -> NavigationBarAnimation.Tween
                "None" -> NavigationBarAnimation.None
                else -> NavigationBarAnimation.Spring
            }

        DemoBox {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedIndex == 0,
                    onClick = { selectedIndex = 0 },
                    icon = RikkaIcons.User,
                    label = stringResource(Res.string.nav_bar_demo_home),
                    animation = animation,
                )
                NavigationBarItem(
                    selected = selectedIndex == 1,
                    onClick = { selectedIndex = 1 },
                    icon = RikkaIcons.Search,
                    label = stringResource(Res.string.nav_bar_demo_search),
                    animation = animation,
                )
                NavigationBarItem(
                    selected = selectedIndex == 2,
                    onClick = { selectedIndex = 2 },
                    icon = RikkaIcons.Settings,
                    label = stringResource(Res.string.nav_bar_demo_settings),
                    animation = animation,
                )
            }
        }
    }

    // ─── Four Items ─────────────────────────────────────────
    DocSection(stringResource(Res.string.nav_bar_section_four_items)) {
        var selectedIndex by remember { mutableStateOf(0) }

        DemoBox {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedIndex == 0,
                    onClick = { selectedIndex = 0 },
                    icon = RikkaIcons.User,
                    label = stringResource(Res.string.nav_bar_demo_home),
                )
                NavigationBarItem(
                    selected = selectedIndex == 1,
                    onClick = { selectedIndex = 1 },
                    icon = RikkaIcons.Search,
                    label = stringResource(Res.string.nav_bar_demo_search),
                )
                NavigationBarItem(
                    selected = selectedIndex == 2,
                    onClick = { selectedIndex = 2 },
                    icon = RikkaIcons.Heart,
                    label = stringResource(Res.string.nav_bar_demo_favorites),
                )
                NavigationBarItem(
                    selected = selectedIndex == 3,
                    onClick = { selectedIndex = 3 },
                    icon = RikkaIcons.Settings,
                    label = stringResource(Res.string.nav_bar_demo_settings),
                )
            }
        }
    }
}

// ─── Usage Tab ──────────────────────────────────────────────

@Composable
private fun NavigationBarUsageTab() {
    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
var selectedIndex by remember { mutableStateOf(0) }

NavigationBar {
    NavigationBarItem(
        selected = selectedIndex == 0,
        onClick = { selectedIndex = 0 },
        icon = RikkaIcons.Home,
        label = "Home",
        animation = NavigationBarAnimation.Spring,
    )
    NavigationBarItem(
        selected = selectedIndex == 1,
        onClick = { selectedIndex = 1 },
        icon = RikkaIcons.Search,
        label = "Search",
    )
}
            """.trimIndent(),
        )
    }

    DocSection(stringResource(Res.string.nav_bar_section_dos_donts)) {
        var selectedIndex by remember { mutableStateOf(0) }

        DoAndDont(
            doContent = {
                NavigationBar {
                    NavigationBarItem(
                        selected = selectedIndex == 0,
                        onClick = { selectedIndex = 0 },
                        icon = RikkaIcons.User,
                        label = stringResource(Res.string.nav_bar_demo_home),
                    )
                    NavigationBarItem(
                        selected = selectedIndex == 1,
                        onClick = { selectedIndex = 1 },
                        icon = RikkaIcons.Search,
                        label = stringResource(Res.string.nav_bar_demo_search),
                    )
                    NavigationBarItem(
                        selected = selectedIndex == 2,
                        onClick = { selectedIndex = 2 },
                        icon = RikkaIcons.Settings,
                        label = stringResource(Res.string.nav_bar_demo_settings),
                    )
                }
            },
            doDescription = stringResource(Res.string.nav_bar_do_primary_nav_desc),
            dontContent = {
                NavigationBar {
                    NavigationBarItem(
                        selected = selectedIndex == 0,
                        onClick = { selectedIndex = 0 },
                        icon = RikkaIcons.User,
                        label = stringResource(Res.string.nav_bar_demo_home),
                    )
                    NavigationBarItem(
                        selected = selectedIndex == 1,
                        onClick = { selectedIndex = 1 },
                        icon = RikkaIcons.Search,
                        label = stringResource(Res.string.nav_bar_demo_search),
                    )
                    NavigationBarItem(
                        selected = selectedIndex == 2,
                        onClick = { selectedIndex = 2 },
                        icon = RikkaIcons.Heart,
                        label = stringResource(Res.string.nav_bar_demo_favorites),
                    )
                    NavigationBarItem(
                        selected = selectedIndex == 3,
                        onClick = { selectedIndex = 3 },
                        icon = RikkaIcons.Settings,
                        label = stringResource(Res.string.nav_bar_demo_settings),
                    )
                    NavigationBarItem(
                        selected = selectedIndex == 4,
                        onClick = { selectedIndex = 4 },
                        icon = RikkaIcons.Star,
                        label = "More",
                    )
                    NavigationBarItem(
                        selected = selectedIndex == 5,
                        onClick = { selectedIndex = 5 },
                        icon = RikkaIcons.Menu,
                        label = "Extra",
                    )
                }
            },
            dontDescription = stringResource(Res.string.nav_bar_dont_too_many_items_desc),
        )
    }
}

// ─── API Tab ────────────────────────────────────────────────

@Composable
private fun NavigationBarApiTab() {
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "selected",
                    "Boolean",
                    "required",
                    stringResource(Res.string.nav_bar_prop_selected_desc),
                ),
                PropInfo(
                    "onClick",
                    "() -> Unit",
                    "required",
                    stringResource(Res.string.nav_bar_prop_onclick_desc),
                ),
                PropInfo(
                    "icon",
                    "ImageVector",
                    "required",
                    stringResource(Res.string.nav_bar_prop_icon_desc),
                ),
                PropInfo(
                    "label",
                    "String",
                    "required",
                    stringResource(Res.string.nav_bar_prop_label_desc),
                ),
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(Res.string.nav_bar_prop_modifier_desc),
                ),
                PropInfo(
                    "selectedIcon",
                    "ImageVector?",
                    "null",
                    stringResource(Res.string.nav_bar_prop_selected_icon_desc),
                ),
                PropInfo(
                    "enabled",
                    "Boolean",
                    "true",
                    stringResource(Res.string.nav_bar_prop_enabled_desc),
                ),
                PropInfo(
                    "alwaysShowLabel",
                    "Boolean",
                    "true",
                    stringResource(Res.string.nav_bar_prop_always_show_label_desc),
                ),
                PropInfo(
                    "animation",
                    "NavigationBarAnimation",
                    "Spring",
                    stringResource(Res.string.nav_bar_prop_animation_desc),
                ),
                PropInfo(
                    "indicatorColor",
                    "Color",
                    "Unspecified",
                    stringResource(Res.string.nav_bar_prop_indicator_color_desc),
                ),
                PropInfo(
                    "activeColor",
                    "Color",
                    "Unspecified",
                    stringResource(Res.string.nav_bar_prop_active_color_desc),
                ),
                PropInfo(
                    "inactiveColor",
                    "Color",
                    "Unspecified",
                    stringResource(Res.string.nav_bar_prop_inactive_color_desc),
                ),
            ),
        )
    }
}
