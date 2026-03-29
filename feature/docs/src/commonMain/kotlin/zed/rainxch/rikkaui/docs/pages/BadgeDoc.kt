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
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.Res
import rikkaui.feature.docs.generated.resources.badge_demo_default
import rikkaui.feature.docs.generated.resources.badge_demo_large
import rikkaui.feature.docs.generated.resources.badge_demo_new
import rikkaui.feature.docs.generated.resources.badge_demo_small
import rikkaui.feature.docs.generated.resources.badge_page_desc
import rikkaui.feature.docs.generated.resources.badge_prop_animation_desc
import rikkaui.feature.docs.generated.resources.badge_prop_modifier_desc
import rikkaui.feature.docs.generated.resources.badge_prop_size_desc
import rikkaui.feature.docs.generated.resources.badge_prop_text_desc
import rikkaui.feature.docs.generated.resources.badge_prop_variant_desc
import rikkaui.feature.docs.generated.resources.component_badge_name
import rikkaui.feature.docs.generated.resources.section_animations
import rikkaui.feature.docs.generated.resources.section_api_reference
import rikkaui.feature.docs.generated.resources.section_sizes
import rikkaui.feature.docs.generated.resources.section_usage
import rikkaui.feature.docs.generated.resources.section_variants
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
import zed.rainxch.rikkaui.foundation.RikkaTheme

@Composable
fun BadgeDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_badge_name),
        description = stringResource(Res.string.badge_page_desc),
    )

    DocSection(stringResource(Res.string.section_variants)) {
        var selectedVariant by remember { mutableStateOf("Default") }

        VariantSelector(
            options =
                listOf(
                    "Default",
                    "Secondary",
                    "Destructive",
                    "Outline",
                ),
            selected = selectedVariant,
            onSelect = { selectedVariant = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val variant =
            when (selectedVariant) {
                "Secondary" -> BadgeVariant.Secondary
                "Destructive" -> BadgeVariant.Destructive
                "Outline" -> BadgeVariant.Outline
                else -> BadgeVariant.Default
            }

        DemoBox {
            Badge(text = selectedVariant, variant = variant)
        }
    }

    DocSection(stringResource(Res.string.section_sizes)) {
        DemoBox {
            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
            ) {
                Badge(
                    text = stringResource(Res.string.badge_demo_small),
                    size = BadgeSize.Sm,
                )
                Badge(
                    text = stringResource(Res.string.badge_demo_default),
                    size = BadgeSize.Default,
                )
                Badge(
                    text = stringResource(Res.string.badge_demo_large),
                    size = BadgeSize.Lg,
                )
            }
        }
    }

    DocSection(stringResource(Res.string.section_animations)) {
        var selectedAnim by remember { mutableStateOf("None") }

        VariantSelector(
            options = listOf("Pulse", "Scale", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation =
            when (selectedAnim) {
                "Pulse" -> BadgeAnimation.Pulse
                "Scale" -> BadgeAnimation.Scale
                else -> BadgeAnimation.None
            }

        DemoBox {
            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
            ) {
                Badge(
                    text = stringResource(Res.string.badge_demo_new),
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

    DocSection(stringResource(Res.string.section_usage)) {
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

    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "text",
                    "String",
                    "required",
                    stringResource(Res.string.badge_prop_text_desc),
                ),
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(Res.string.badge_prop_modifier_desc),
                ),
                PropInfo(
                    "variant",
                    "BadgeVariant",
                    "Default",
                    stringResource(Res.string.badge_prop_variant_desc),
                ),
                PropInfo(
                    "animation",
                    "BadgeAnimation",
                    "None",
                    stringResource(Res.string.badge_prop_animation_desc),
                ),
                PropInfo(
                    "size",
                    "BadgeSize",
                    "Default",
                    stringResource(Res.string.badge_prop_size_desc),
                ),
            ),
        )
    }
}
