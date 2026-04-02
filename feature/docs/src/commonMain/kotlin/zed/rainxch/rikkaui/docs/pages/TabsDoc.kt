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
import rikkaui.feature.docs.generated.resources.*
import rikkaui.feature.docs.generated.resources.Res
import zed.rainxch.rikkaui.components.ui.tabs.Tab
import zed.rainxch.rikkaui.components.ui.tabs.TabAnimation
import zed.rainxch.rikkaui.components.ui.tabs.TabContent
import zed.rainxch.rikkaui.components.ui.tabs.TabList
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

/**
 * Documentation page for the Tabs component.
 *
 * Demonstrates TabList, Tab, and TabContent with animation
 * variants and interactive selection.
 */
@Composable
fun TabsDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_tabs_name),
        description = stringResource(Res.string.tabs_page_desc),
    )

    ComponentFamily(
        related = ComponentFamilies.NAVIGATION,
        currentId = "tabs",
    )

    TabbedDocPage(
        overview = { TabsOverviewTab() },
        usage = { TabsUsageTab() },
        api = { TabsApiTab() },
    )
}

// ─── Overview Tab ───────────────────────────────────────────

@Composable
private fun TabsOverviewTab() {
    // ─── Animation Variants ─────────────────────────────────
    DocSection(stringResource(Res.string.section_animations)) {
        var selectedAnim by remember { mutableStateOf("Spring") }
        var selectedTab by remember { mutableStateOf(0) }

        VariantSelector(
            options = listOf("Spring", "Tween", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation =
            when (selectedAnim) {
                "Tween" -> TabAnimation.Tween
                "None" -> TabAnimation.None
                else -> TabAnimation.Spring
            }

        DemoBox {
            Column {
                TabList {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = stringResource(Res.string.tabs_demo_account),
                        animation = animation,
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = stringResource(Res.string.tabs_demo_password),
                        animation = animation,
                    )
                    Tab(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        text = stringResource(Res.string.tabs_demo_settings),
                        animation = animation,
                    )
                }

                TabContent(selectedIndex = selectedTab) {
                    when (selectedTab) {
                        0 -> {
                            Text(
                                stringResource(Res.string.tabs_demo_account_content),
                                variant = TextVariant.P,
                            )
                        }

                        1 -> {
                            Text(
                                stringResource(Res.string.tabs_demo_password_content),
                                variant = TextVariant.P,
                            )
                        }

                        2 -> {
                            Text(
                                stringResource(Res.string.tabs_demo_settings_content),
                                variant = TextVariant.P,
                            )
                        }
                    }
                }
            }
        }
    }

    // ─── Basic Tabs ─────────────────────────────────────────
    DocSection(stringResource(Res.string.tabs_section_basic)) {
        var selectedTab by remember { mutableStateOf(0) }

        DemoBox {
            Column {
                TabList {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = stringResource(Res.string.tabs_demo_overview),
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = stringResource(Res.string.tabs_demo_analytics),
                    )
                }

                TabContent(selectedIndex = selectedTab) {
                    when (selectedTab) {
                        0 -> {
                            Text(
                                stringResource(Res.string.tabs_demo_overview_content),
                                variant = TextVariant.P,
                            )
                        }

                        1 -> {
                            Text(
                                stringResource(Res.string.tabs_demo_analytics_content),
                                variant = TextVariant.P,
                            )
                        }
                    }
                }
            }
        }
    }
}

// ─── Usage Tab ──────────────────────────────────────────────

@Composable
private fun TabsUsageTab() {
    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
var selectedTab by remember { mutableStateOf(0) }

TabList {
    Tab(
        selected = selectedTab == 0,
        onClick = { selectedTab = 0 },
        text = "Account",
        animation = TabAnimation.Spring,
    )
    Tab(
        selected = selectedTab == 1,
        onClick = { selectedTab = 1 },
        text = "Password",
    )
}

TabContent {
    when (selectedTab) {
        0 -> Text("Account settings")
        1 -> Text("Password settings")
    }
}
            """.trimIndent(),
        )
    }

    DocSection(stringResource(Res.string.tabs_section_dos_donts)) {
        var selectedTab by remember { mutableStateOf(0) }

        DoAndDont(
            doContent = {
                Column {
                    TabList {
                        Tab(
                            selected = selectedTab == 0,
                            onClick = { selectedTab = 0 },
                            text = stringResource(Res.string.tabs_demo_overview),
                        )
                        Tab(
                            selected = selectedTab == 1,
                            onClick = { selectedTab = 1 },
                            text = stringResource(Res.string.tabs_demo_analytics),
                        )
                    }
                    TabContent(selectedIndex = selectedTab) {
                        when (selectedTab) {
                            0 -> Text(
                                stringResource(Res.string.tabs_demo_overview_content),
                                variant = TextVariant.Muted,
                            )
                            1 -> Text(
                                stringResource(Res.string.tabs_demo_analytics_content),
                                variant = TextVariant.Muted,
                            )
                        }
                    }
                }
            },
            doDescription = stringResource(Res.string.tabs_do_related_views_desc),
            dontContent = {
                Column {
                    TabList {
                        Tab(
                            selected = true,
                            onClick = {},
                            text = "Step 1",
                        )
                        Tab(
                            selected = false,
                            onClick = {},
                            text = "Step 2",
                        )
                        Tab(
                            selected = false,
                            onClick = {},
                            text = "Step 3",
                        )
                    }
                    TabContent(selectedIndex = 0) {
                        Text(
                            "Step 1 content",
                            variant = TextVariant.Muted,
                        )
                    }
                }
            },
            dontDescription = stringResource(Res.string.tabs_dont_sequential_desc),
        )
    }
}

// ─── API Tab ────────────────────────────────────────────────

@Composable
private fun TabsApiTab() {
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "selected",
                    "Boolean",
                    "required",
                    stringResource(Res.string.tabs_prop_selected_desc),
                ),
                PropInfo(
                    "onClick",
                    "() -> Unit",
                    "required",
                    stringResource(Res.string.tabs_prop_onclick_desc),
                ),
                PropInfo(
                    "text",
                    "String",
                    "required",
                    stringResource(Res.string.tabs_prop_text_desc),
                ),
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(Res.string.tabs_prop_modifier_desc),
                ),
                PropInfo(
                    "animation",
                    "TabAnimation",
                    "Spring",
                    stringResource(Res.string.tabs_prop_animation_desc),
                ),
                PropInfo(
                    "activeColor",
                    "Color",
                    "Unspecified",
                    stringResource(Res.string.tabs_prop_active_color_desc),
                ),
                PropInfo(
                    "inactiveColor",
                    "Color",
                    "Unspecified",
                    stringResource(Res.string.tabs_prop_inactive_color_desc),
                ),
                PropInfo(
                    "activeBackground",
                    "Color",
                    "background",
                    stringResource(Res.string.tabs_prop_active_bg_desc),
                ),
                PropInfo(
                    "inactiveBackground",
                    "Color",
                    "muted",
                    stringResource(Res.string.tabs_prop_inactive_bg_desc),
                ),
            ),
        )
    }
}
