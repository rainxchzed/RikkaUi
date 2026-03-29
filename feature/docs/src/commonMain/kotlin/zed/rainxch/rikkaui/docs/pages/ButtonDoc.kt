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
import rikkaui.feature.docs.generated.resources.button_demo_disabled
import rikkaui.feature.docs.generated.resources.button_demo_enabled
import rikkaui.feature.docs.generated.resources.button_demo_loading
import rikkaui.feature.docs.generated.resources.button_demo_outline
import rikkaui.feature.docs.generated.resources.button_demo_press_me
import rikkaui.feature.docs.generated.resources.button_page_desc
import rikkaui.feature.docs.generated.resources.button_prop_animation_desc
import rikkaui.feature.docs.generated.resources.button_prop_content_desc
import rikkaui.feature.docs.generated.resources.button_prop_enabled_desc
import rikkaui.feature.docs.generated.resources.button_prop_label_desc
import rikkaui.feature.docs.generated.resources.button_prop_loading_desc
import rikkaui.feature.docs.generated.resources.button_prop_onclick_desc
import rikkaui.feature.docs.generated.resources.button_prop_size_desc
import rikkaui.feature.docs.generated.resources.button_prop_variant_desc
import rikkaui.feature.docs.generated.resources.component_button_name
import rikkaui.feature.docs.generated.resources.section_animations
import rikkaui.feature.docs.generated.resources.section_api_reference
import rikkaui.feature.docs.generated.resources.section_sizes
import rikkaui.feature.docs.generated.resources.section_states
import rikkaui.feature.docs.generated.resources.section_usage
import rikkaui.feature.docs.generated.resources.section_variants
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonAnimation
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector

@Composable
fun ButtonDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_button_name),
        description = stringResource(Res.string.button_page_desc),
    )

    DocSection(stringResource(Res.string.section_variants)) {
        var selectedVariant by remember {
            mutableStateOf(ButtonVariant.Default.name)
        }

        VariantSelector(
            options = ButtonVariant.entries.map { it.name },
            selected = selectedVariant,
            onSelect = { selectedVariant = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val variant = ButtonVariant.valueOf(selectedVariant)

        DemoBox {
            Button(
                text = selectedVariant,
                onClick = {},
                variant = variant,
            )
        }
    }

    DocSection(stringResource(Res.string.section_sizes)) {
        var selectedSize by remember {
            mutableStateOf(ButtonSize.Default.name)
        }

        VariantSelector(
            options = ButtonSize.entries.map { it.name },
            selected = selectedSize,
            onSelect = { selectedSize = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val size = ButtonSize.valueOf(selectedSize)

        DemoBox {
            Button(
                text = if (size == ButtonSize.Icon) {
                    "+"
                } else {
                    stringResource(Res.string.component_button_name)
                },
                onClick = {},
                size = size,
            )
        }
    }

    DocSection(stringResource(Res.string.section_animations)) {
        var selectedAnim by remember {
            mutableStateOf(ButtonAnimation.Scale.name)
        }

        VariantSelector(
            options = ButtonAnimation.entries.map { it.name },
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = ButtonAnimation.valueOf(selectedAnim)

        DemoBox {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
            ) {
                Button(
                    text = stringResource(Res.string.button_demo_press_me),
                    onClick = {},
                    animation = animation,
                )
                Button(
                    text = stringResource(Res.string.button_demo_outline),
                    onClick = {},
                    variant = ButtonVariant.Outline,
                    animation = animation,
                )
            }
        }
    }

    DocSection(stringResource(Res.string.section_states)) {
        DemoBox {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
            ) {
                Button(
                    text = stringResource(Res.string.button_demo_enabled),
                    onClick = {},
                )
                Button(
                    text = stringResource(Res.string.button_demo_disabled),
                    onClick = {},
                    enabled = false,
                )
                Button(
                    text = stringResource(Res.string.button_demo_loading),
                    onClick = {},
                    loading = true,
                )
            }
        }
    }

    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
Button("Save", onClick = { save() })

Button(
    text = "Delete",
    onClick = { delete() },
    variant = ButtonVariant.Destructive,
)

// Content lambda receives the foreground color
Button(
    onClick = { submit() },
    size = ButtonSize.Lg,
    animation = ButtonAnimation.Bounce,
) { color ->
    Icon(RikkaIcons.Send, "Send", tint = color)
    Text("Submit", color = color)
}
            """.trimIndent(),
        )
    }

    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "onClick", "() -> Unit", "-",
                    stringResource(Res.string.button_prop_onclick_desc),
                ),
                PropInfo(
                    "variant", "ButtonVariant", "Default",
                    stringResource(Res.string.button_prop_variant_desc),
                ),
                PropInfo(
                    "size", "ButtonSize", "Default",
                    stringResource(Res.string.button_prop_size_desc),
                ),
                PropInfo(
                    "animation", "ButtonAnimation", "Scale",
                    stringResource(Res.string.button_prop_animation_desc),
                ),
                PropInfo(
                    "enabled", "Boolean", "true",
                    stringResource(Res.string.button_prop_enabled_desc),
                ),
                PropInfo(
                    "loading", "Boolean", "false",
                    stringResource(Res.string.button_prop_loading_desc),
                ),
                PropInfo(
                    "label", "String", "\"\"",
                    stringResource(Res.string.button_prop_label_desc),
                ),
                PropInfo(
                    "content", "@Composable (Color) -> Unit", "-",
                    stringResource(Res.string.button_prop_content_desc),
                ),
            ),
        )
    }
}
