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
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.navigationbar.NavigationBar
import zed.rainxch.rikkaui.components.ui.navigationbar.NavigationBarAnimation
import zed.rainxch.rikkaui.components.ui.navigationbar.NavigationBarItem
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector

/**
 * Documentation page for the NavigationBar component.
 *
 * Demonstrates bottom navigation with icon items, animation
 * variants, and the convenience ImageVector overload.
 */
@Composable
fun NavigationBarDoc() {
    ComponentPageHeader(
        name = "NavigationBar",
        description = "A bottom navigation bar for app-level "
            + "navigation with icon + label items and an "
            + "animated indicator pill.",
    )

    // ─── Animation Variants ─────────────────────────────────
    DocSection("Animations") {
        var selectedAnim by remember { mutableStateOf("Spring") }
        var selectedIndex by remember { mutableStateOf(0) }

        VariantSelector(
            options = listOf("Spring", "Tween", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = when (selectedAnim) {
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
                    label = "Home",
                    animation = animation,
                )
                NavigationBarItem(
                    selected = selectedIndex == 1,
                    onClick = { selectedIndex = 1 },
                    icon = RikkaIcons.Search,
                    label = "Search",
                    animation = animation,
                )
                NavigationBarItem(
                    selected = selectedIndex == 2,
                    onClick = { selectedIndex = 2 },
                    icon = RikkaIcons.Settings,
                    label = "Settings",
                    animation = animation,
                )
            }
        }
    }

    // ─── Four Items ─────────────────────────────────────────
    DocSection("Four Items") {
        var selectedIndex by remember { mutableStateOf(0) }

        DemoBox {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedIndex == 0,
                    onClick = { selectedIndex = 0 },
                    icon = RikkaIcons.User,
                    label = "Home",
                )
                NavigationBarItem(
                    selected = selectedIndex == 1,
                    onClick = { selectedIndex = 1 },
                    icon = RikkaIcons.Search,
                    label = "Search",
                )
                NavigationBarItem(
                    selected = selectedIndex == 2,
                    onClick = { selectedIndex = 2 },
                    icon = RikkaIcons.Heart,
                    label = "Favorites",
                )
                NavigationBarItem(
                    selected = selectedIndex == 3,
                    onClick = { selectedIndex = 3 },
                    icon = RikkaIcons.Settings,
                    label = "Settings",
                )
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
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

    // ─── API Reference ──────────────────────────────────────
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "selected", "Boolean", "required",
                    "Whether this item is currently active.",
                ),
                PropInfo(
                    "onClick", "() -> Unit", "required",
                    "Called when the item is tapped.",
                ),
                PropInfo(
                    "icon", "ImageVector", "required",
                    "The icon to display.",
                ),
                PropInfo(
                    "label", "String", "required",
                    "Text label displayed below the icon.",
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier for layout and decoration.",
                ),
                PropInfo(
                    "selectedIcon", "ImageVector?", "null",
                    "Different icon for selected state.",
                ),
                PropInfo(
                    "enabled", "Boolean", "true",
                    "Whether the item is interactive.",
                ),
                PropInfo(
                    "alwaysShowLabel", "Boolean", "true",
                    "When false, label fades when not selected.",
                ),
                PropInfo(
                    "animation", "NavigationBarAnimation", "Spring",
                    "Animation strategy: Spring, Tween, None.",
                ),
                PropInfo(
                    "indicatorColor", "Color", "accent",
                    "Override for the pill indicator color.",
                ),
            ),
        )
    }
}
