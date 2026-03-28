package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.collapsible.Collapsible
import zed.rainxch.rikkaui.components.ui.collapsible.CollapsibleAnimation
import zed.rainxch.rikkaui.components.ui.collapsible.CollapsibleContent
import zed.rainxch.rikkaui.components.ui.collapsible.CollapsibleTrigger
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
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
 * Documentation page for the Collapsible component.
 *
 * Demonstrates controlled usage, DSL builder syntax, and
 * animation styles for the expand/collapse primitive.
 */
@Composable
fun CollapsibleDoc() {
    ComponentPageHeader(
        name = "Collapsible",
        description = "A primitive expand/collapse container. Unlike "
            + "Accordion, it has no built-in header \u2014 you compose "
            + "the trigger and content yourself.",
    )

    // ─── Controlled Usage ───────────────────────────────────
    DocSection("Controlled Usage") {
        DemoBox {
            var open by remember { mutableStateOf(false) }

            Collapsible(
                open = open,
                onOpenChange = { open = it },
                modifier = Modifier.fillMaxWidth(),
            ) {
                CollapsibleTrigger(
                    onClick = { open = !open },
                    expanded = open,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(
                                vertical = RikkaTheme.spacing.sm,
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            "3 starred repositories",
                            variant = TextVariant.P,
                        )
                        Icon(RikkaIcons.ChevronDown, "Toggle")
                    }
                }

                CollapsibleContent(open = open) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            RikkaTheme.spacing.xs,
                        ),
                        modifier = Modifier.padding(
                            bottom = RikkaTheme.spacing.sm,
                        ),
                    ) {
                        Text(
                            "@rikka/components",
                            variant = TextVariant.Muted,
                        )
                        Text(
                            "@rikka/theme",
                            variant = TextVariant.Muted,
                        )
                        Text(
                            "@rikka/icons",
                            variant = TextVariant.Muted,
                        )
                    }
                }
            }
        }
    }

    // ─── DSL Builder ────────────────────────────────────────
    DocSection("DSL Builder") {
        DemoBox {
            Collapsible(
                modifier = Modifier.fillMaxWidth(),
                initialOpen = false,
                animation = CollapsibleAnimation.Spring,
            ) {
                trigger {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(
                                vertical = RikkaTheme.spacing.sm,
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            "Click to expand (DSL)",
                            variant = TextVariant.P,
                        )
                        Icon(RikkaIcons.ChevronDown, "Toggle")
                    }
                }
                content {
                    Column(
                        modifier = Modifier.padding(
                            bottom = RikkaTheme.spacing.sm,
                        ),
                    ) {
                        Text(
                            "This collapsible uses the DSL builder "
                                + "with internally-managed state.",
                            variant = TextVariant.Muted,
                        )
                    }
                }
            }
        }
    }

    // ─── Animation Styles ───────────────────────────────────
    DocSection("Animation Styles") {
        var selectedAnim by remember { mutableStateOf("Spring") }

        VariantSelector(
            options = listOf("Spring", "Tween", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = when (selectedAnim) {
            "Tween" -> CollapsibleAnimation.Tween
            "None" -> CollapsibleAnimation.None
            else -> CollapsibleAnimation.Spring
        }

        DemoBox {
            var open by remember { mutableStateOf(false) }

            Collapsible(
                open = open,
                onOpenChange = { open = it },
                modifier = Modifier.fillMaxWidth(),
            ) {
                CollapsibleTrigger(
                    onClick = { open = !open },
                    expanded = open,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(
                                vertical = RikkaTheme.spacing.sm,
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            "Animation: $selectedAnim",
                            variant = TextVariant.P,
                        )
                    }
                }

                CollapsibleContent(
                    open = open,
                    animation = animation,
                ) {
                    Column(
                        modifier = Modifier.padding(
                            bottom = RikkaTheme.spacing.sm,
                        ),
                    ) {
                        Text(
                            "Toggle to see the "
                                + "$selectedAnim animation.",
                            variant = TextVariant.Muted,
                        )
                    }
                }
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
        CodeBlock(
            """
// Controlled usage
var open by remember { mutableStateOf(false) }

Collapsible(
    open = open,
    onOpenChange = { open = it },
) {
    CollapsibleTrigger(
        onClick = { open = !open },
        expanded = open,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("3 starred repositories")
            Icon(RikkaIcons.ChevronDown, "Toggle")
        }
    }
    CollapsibleContent(open = open) {
        Text("@rikka/components")
        Text("@rikka/theme")
        Text("@rikka/icons")
    }
}

// DSL builder (internally-managed state)
Collapsible(
    initialOpen = false,
    animation = CollapsibleAnimation.Tween,
) {
    trigger { Text("Click me") }
    content { Text("Expandable body") }
}
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection("API Reference") {
        Text("Collapsible (controlled)", variant = TextVariant.H4)

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        PropsTable(
            listOf(
                PropInfo(
                    "open", "Boolean", "required",
                    "Whether the content is expanded.",
                ),
                PropInfo(
                    "onOpenChange", "(Boolean) -> Unit",
                    "required",
                    "Called when expanded state changes.",
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier applied to the container.",
                ),
                PropInfo(
                    "content", "() -> Unit", "required",
                    "Builder with CollapsibleTrigger "
                        + "+ CollapsibleContent.",
                ),
            ),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        Text("CollapsibleTrigger", variant = TextVariant.H4)

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        PropsTable(
            listOf(
                PropInfo(
                    "onClick", "() -> Unit", "required",
                    "Called when the trigger is clicked.",
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier applied to the trigger.",
                ),
                PropInfo(
                    "label", "String",
                    "\"Toggle section\"",
                    "Accessibility content description.",
                ),
                PropInfo(
                    "expanded", "Boolean?", "null",
                    "Current state for a11y "
                        + "stateDescription.",
                ),
                PropInfo(
                    "content", "() -> Unit", "required",
                    "Composable inside the trigger.",
                ),
            ),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        Text("CollapsibleContent", variant = TextVariant.H4)

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        PropsTable(
            listOf(
                PropInfo(
                    "open", "Boolean", "required",
                    "Whether the content is visible.",
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier applied to the wrapper.",
                ),
                PropInfo(
                    "animation", "CollapsibleAnimation",
                    "Spring",
                    "Animation style: Spring, Tween, None.",
                ),
                PropInfo(
                    "content", "() -> Unit", "required",
                    "Composable content to show/hide.",
                ),
            ),
        )
    }
}
