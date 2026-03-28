package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.tabs.Tab
import zed.rainxch.rikkaui.components.ui.tabs.TabAnimation
import zed.rainxch.rikkaui.components.ui.tabs.TabContent
import zed.rainxch.rikkaui.components.ui.tabs.TabList
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector

/**
 * Documentation page for the Tabs component.
 *
 * Demonstrates TabList, Tab, and TabContent with animation
 * variants and interactive selection.
 */
@Composable
fun TabsDoc() {
    ComponentPageHeader(
        name = "Tabs",
        description = "A set of layered sections of content that "
            + "display one panel at a time.",
    )

    // ─── Animation Variants ─────────────────────────────────
    DocSection("Animations") {
        var selectedAnim by remember { mutableStateOf("Spring") }
        var selectedTab by remember { mutableStateOf(0) }

        VariantSelector(
            options = listOf("Spring", "Tween", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = when (selectedAnim) {
            "Tween" -> TabAnimation.Tween
            "None" -> TabAnimation.None
            else -> TabAnimation.Spring
        }

        DemoBox {
            TabList {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = "Account",
                    animation = animation,
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = "Password",
                    animation = animation,
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    text = "Settings",
                    animation = animation,
                )
            }

            TabContent {
                when (selectedTab) {
                    0 -> Text(
                        "Manage your account details.",
                        variant = TextVariant.P,
                    )
                    1 -> Text(
                        "Change your password here.",
                        variant = TextVariant.P,
                    )
                    2 -> Text(
                        "Configure your preferences.",
                        variant = TextVariant.P,
                    )
                }
            }
        }
    }

    // ─── Basic Tabs ─────────────────────────────────────────
    DocSection("Basic Tabs") {
        var selectedTab by remember { mutableStateOf(0) }

        DemoBox {
            TabList {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = "Overview",
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = "Analytics",
                )
            }

            TabContent {
                when (selectedTab) {
                    0 -> Text(
                        "Overview content panel.",
                        variant = TextVariant.P,
                    )
                    1 -> Text(
                        "Analytics content panel.",
                        variant = TextVariant.P,
                    )
                }
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
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

    // ─── API Reference ──────────────────────────────────────
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "selected", "Boolean", "required",
                    "Whether this tab is currently active.",
                ),
                PropInfo(
                    "onClick", "() -> Unit", "required",
                    "Called when the tab is clicked.",
                ),
                PropInfo(
                    "text", "String", "required",
                    "Label text displayed in the tab.",
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier for layout and decoration.",
                ),
                PropInfo(
                    "animation", "TabAnimation", "Spring",
                    "Animation strategy: Spring, Tween, None.",
                ),
                PropInfo(
                    "activeColor", "Color", "foreground",
                    "Override for selected text color.",
                ),
                PropInfo(
                    "inactiveColor", "Color", "mutedForeground",
                    "Override for unselected text color.",
                ),
                PropInfo(
                    "activeBackground", "Color", "background",
                    "Override for selected background.",
                ),
                PropInfo(
                    "inactiveBackground", "Color", "muted",
                    "Override for unselected background.",
                ),
            ),
        )
    }
}
