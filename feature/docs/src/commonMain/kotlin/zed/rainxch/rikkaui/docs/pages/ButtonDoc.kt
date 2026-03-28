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

/**
 * Documentation page for the Button component.
 *
 * Showcases all variants, sizes, and animation styles with
 * live interactive demos, code snippets, and an API reference table.
 */
@Composable
fun ButtonDoc() {
    ComponentPageHeader(
        name = "Button",
        description = "Triggers an action or event. Supports 6 visual variants, " +
            "4 sizes, and 3 press animation styles.",
    )

    // ─── Variants Demo ──────────────────────────────────────
    DocSection("Variants") {
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

    // ─── Sizes Demo ─────────────────────────────────────────
    DocSection("Sizes") {
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
                text = if (size == ButtonSize.Icon) "+" else "Button",
                onClick = {},
                size = size,
            )
        }
    }

    // ─── Animations Demo ────────────────────────────────────
    DocSection("Animations") {
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
                    text = "Press me",
                    onClick = {},
                    animation = animation,
                )
                Button(
                    text = "Outline",
                    onClick = {},
                    variant = ButtonVariant.Outline,
                    animation = animation,
                )
            }
        }
    }

    // ─── States Demo ────────────────────────────────────────
    DocSection("States") {
        DemoBox {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
            ) {
                Button(text = "Enabled", onClick = {})
                Button(
                    text = "Disabled",
                    onClick = {},
                    enabled = false,
                )
                Button(
                    text = "Loading...",
                    onClick = {},
                    loading = true,
                )
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
        CodeBlock(
            """
Button("Save", onClick = { save() })

Button(
    text = "Delete",
    onClick = { delete() },
    variant = ButtonVariant.Destructive,
)

Button(
    onClick = { submit() },
    size = ButtonSize.Lg,
    animation = ButtonAnimation.Bounce,
) {
    Text("Submit")
}
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "onClick",
                    "() -> Unit",
                    "-",
                    "Called when the button is clicked.",
                ),
                PropInfo(
                    "variant",
                    "ButtonVariant",
                    "Default",
                    "Visual variant: Default, Outline, Secondary, Ghost, Destructive, Link.",
                ),
                PropInfo(
                    "size",
                    "ButtonSize",
                    "Default",
                    "Size: Default, Sm, Lg, Icon.",
                ),
                PropInfo(
                    "animation",
                    "ButtonAnimation",
                    "Scale",
                    "Press animation: None, Scale, Bounce.",
                ),
                PropInfo(
                    "enabled",
                    "Boolean",
                    "true",
                    "Whether the button is interactive.",
                ),
                PropInfo(
                    "loading",
                    "Boolean",
                    "false",
                    "Shows a Spinner and disables interaction.",
                ),
                PropInfo(
                    "label",
                    "String",
                    "\"\"",
                    "Accessibility label for screen readers.",
                ),
                PropInfo(
                    "content",
                    "@Composable",
                    "-",
                    "Button content, typically Text and/or icons.",
                ),
            ),
        )
    }
}
