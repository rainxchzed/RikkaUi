package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeAnimation
import zed.rainxch.rikkaui.components.ui.badge.BadgeSize
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector

/**
 * Documentation page for the Badge component.
 *
 * Demonstrates all badge variants, sizes, and animations.
 */
@Composable
fun BadgeDoc() {
    ComponentPageHeader(
        name = "Badge",
        description = "A small status indicator or label for tags, "
            + "counts, and notifications.",
    )

    // ─── Variants ───────────────────────────────────────────
    DocSection("Variants") {
        var selectedVariant by remember { mutableStateOf("Default") }

        VariantSelector(
            options = listOf(
                "Default", "Secondary", "Destructive", "Outline",
            ),
            selected = selectedVariant,
            onSelect = { selectedVariant = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val variant = when (selectedVariant) {
            "Secondary" -> BadgeVariant.Secondary
            "Destructive" -> BadgeVariant.Destructive
            "Outline" -> BadgeVariant.Outline
            else -> BadgeVariant.Default
        }

        DemoBox {
            Badge(text = selectedVariant, variant = variant)
        }
    }

    // ─── Sizes ──────────────────────────────────────────────
    DocSection("Sizes") {
        DemoBox {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
            ) {
                Badge(text = "Small", size = BadgeSize.Sm)
                Badge(text = "Default", size = BadgeSize.Default)
                Badge(text = "Large", size = BadgeSize.Lg)
            }
        }
    }

    // ─── Animations ─────────────────────────────────────────
    DocSection("Animations") {
        var selectedAnim by remember { mutableStateOf("None") }

        VariantSelector(
            options = listOf("Pulse", "Scale", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = when (selectedAnim) {
            "Pulse" -> BadgeAnimation.Pulse
            "Scale" -> BadgeAnimation.Scale
            else -> BadgeAnimation.None
        }

        DemoBox {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
            ) {
                Badge(
                    text = "New",
                    variant = BadgeVariant.Default,
                    animation = animation,
                )
                Badge(
                    text = "3",
                    variant = BadgeVariant.Destructive,
                    animation = animation,
                )
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
        CodeBlock(
            """
Badge("New")
Badge("Draft", variant = BadgeVariant.Secondary)
Badge("Error", variant = BadgeVariant.Destructive)
Badge("v1.0", variant = BadgeVariant.Outline)

// With size and animation
Badge(
    text = "3",
    size = BadgeSize.Sm,
    animation = BadgeAnimation.Scale,
)
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "text", "String", "required",
                    "The label text displayed in the badge.",
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier for layout and decoration.",
                ),
                PropInfo(
                    "variant", "BadgeVariant", "Default",
                    "Visual variant: Default, Secondary, "
                        + "Destructive, Outline.",
                ),
                PropInfo(
                    "animation", "BadgeAnimation", "None",
                    "Entrance animation: Pulse, Scale, None.",
                ),
                PropInfo(
                    "size", "BadgeSize", "Default",
                    "Size preset: Sm, Default, Lg.",
                ),
            ),
        )
    }
}
