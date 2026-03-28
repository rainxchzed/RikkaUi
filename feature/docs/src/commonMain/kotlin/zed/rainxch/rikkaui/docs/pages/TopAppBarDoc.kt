package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.topappbar.TopAppBar
import zed.rainxch.rikkaui.components.ui.topappbar.TopAppBarColorTransition
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
        name = "TopAppBar",
        description = "A header bar with title, navigation icon, "
            + "and action slots. Supports solid and transparent "
            + "variants.",
    )

    // ─── Variants ───────────────────────────────────────────
    DocSection("Variants") {
        var selectedVariant by remember { mutableStateOf("Default") }

        VariantSelector(
            options = listOf("Default", "Transparent"),
            selected = selectedVariant,
            onSelect = { selectedVariant = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val variant = when (selectedVariant) {
            "Transparent" -> TopAppBarVariant.Transparent
            else -> TopAppBarVariant.Default
        }

        DemoBox {
            TopAppBar(
                title = "Dashboard",
                variant = variant,
            )
        }
    }

    // ─── Sizes ──────────────────────────────────────────────
    DocSection("Sizes") {
        var selectedSize by remember { mutableStateOf("Small") }

        VariantSelector(
            options = listOf("Small", "Medium"),
            selected = selectedSize,
            onSelect = { selectedSize = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val size = when (selectedSize) {
            "Medium" -> TopAppBarSize.Medium
            else -> TopAppBarSize.Small
        }

        DemoBox {
            TopAppBar(
                title = "Settings",
                size = size,
            )
        }
    }

    // ─── With Navigation & Actions ──────────────────────────
    DocSection("Navigation Icon & Actions") {
        DemoBox {
            TopAppBar(
                title = "Profile",
                navigationIcon = {
                    Button(
                        onClick = {},
                        variant = ButtonVariant.Ghost,
                        size = ButtonSize.Icon,
                    ) {
                        Icon(
                            RikkaIcons.ArrowLeft,
                            contentDescription = "Back",
                            modifier = Modifier.size(20.dp),
                        )
                    }
                },
                actions = {
                    Button(
                        onClick = {},
                        variant = ButtonVariant.Ghost,
                        size = ButtonSize.Icon,
                    ) {
                        Icon(
                            RikkaIcons.Settings,
                            contentDescription = "Settings",
                            modifier = Modifier.size(20.dp),
                        )
                    }
                },
                centerTitle = true,
            )
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
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
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "title", "String / () -> Unit", "required",
                    "Title text or composable slot.",
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier for layout and decoration.",
                ),
                PropInfo(
                    "navigationIcon", "() -> Unit", "{}",
                    "Leading icon slot (e.g. back arrow).",
                ),
                PropInfo(
                    "actions", "RowScope.() -> Unit", "{}",
                    "Trailing actions slot.",
                ),
                PropInfo(
                    "variant", "TopAppBarVariant", "Default",
                    "Visual variant: Default, Transparent.",
                ),
                PropInfo(
                    "size", "TopAppBarSize", "Small",
                    "Bar size: Small (56dp), Medium (64dp).",
                ),
                PropInfo(
                    "centerTitle", "Boolean", "false",
                    "When true, title is centered horizontally.",
                ),
                PropInfo(
                    "elevation", "Dp", "0.dp",
                    "Shadow elevation beneath the bar.",
                ),
                PropInfo(
                    "colorTransition", "TopAppBarColorTransition", "None",
                    "Background animation: None, Smooth, Snap.",
                ),
            ),
        )
    }
}
