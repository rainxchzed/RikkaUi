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
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.Res
import rikkaui.feature.docs.generated.resources.*
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
        name = stringResource(Res.string.component_collapsible_name),
        description = stringResource(Res.string.collapsible_page_desc),
    )

    // ─── Controlled Usage ───────────────────────────────────
    DocSection(stringResource(Res.string.collapsible_section_controlled)) {
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
                            stringResource(Res.string.collapsible_demo_starred_repos),
                            variant = TextVariant.P,
                        )
                        Icon(
                            RikkaIcons.ChevronDown,
                            stringResource(Res.string.collapsible_demo_toggle),
                        )
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
    DocSection(stringResource(Res.string.collapsible_section_dsl)) {
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
                            stringResource(Res.string.collapsible_demo_expand_dsl),
                            variant = TextVariant.P,
                        )
                        Icon(
                            RikkaIcons.ChevronDown,
                            stringResource(Res.string.collapsible_demo_toggle),
                        )
                    }
                }
                content {
                    Column(
                        modifier = Modifier.padding(
                            bottom = RikkaTheme.spacing.sm,
                        ),
                    ) {
                        Text(
                            stringResource(Res.string.collapsible_demo_dsl_body),
                            variant = TextVariant.Muted,
                        )
                    }
                }
            }
        }
    }

    // ─── Animation Styles ───────────────────────────────────
    DocSection(stringResource(Res.string.collapsible_section_animation_styles)) {
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
                            stringResource(
                                Res.string.collapsible_demo_animation_title,
                                selectedAnim,
                            ),
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
                            stringResource(
                                Res.string.collapsible_demo_animation_body,
                                selectedAnim,
                            ),
                            variant = TextVariant.Muted,
                        )
                    }
                }
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection(stringResource(Res.string.section_usage)) {
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
    DocSection(stringResource(Res.string.section_api_reference)) {
        Text(
            stringResource(Res.string.collapsible_subsection_controlled),
            variant = TextVariant.H4,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        PropsTable(
            listOf(
                PropInfo(
                    "open", "Boolean", "required",
                    stringResource(Res.string.collapsible_prop_open_desc),
                ),
                PropInfo(
                    "onOpenChange", "(Boolean) -> Unit",
                    "required",
                    stringResource(Res.string.collapsible_prop_on_open_change_desc),
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    stringResource(Res.string.collapsible_prop_modifier_desc),
                ),
                PropInfo(
                    "content", "() -> Unit", "required",
                    stringResource(Res.string.collapsible_prop_content_desc),
                ),
            ),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        Text(
            stringResource(Res.string.collapsible_subsection_trigger),
            variant = TextVariant.H4,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        PropsTable(
            listOf(
                PropInfo(
                    "onClick", "() -> Unit", "required",
                    stringResource(Res.string.collapsible_prop_trigger_onclick_desc),
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    stringResource(Res.string.collapsible_prop_trigger_modifier_desc),
                ),
                PropInfo(
                    "label", "String",
                    "\"Toggle section\"",
                    stringResource(Res.string.collapsible_prop_trigger_label_desc),
                ),
                PropInfo(
                    "expanded", "Boolean?", "null",
                    stringResource(Res.string.collapsible_prop_trigger_expanded_desc),
                ),
                PropInfo(
                    "content", "() -> Unit", "required",
                    stringResource(Res.string.collapsible_prop_trigger_content_desc),
                ),
            ),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        Text(
            stringResource(Res.string.collapsible_subsection_content),
            variant = TextVariant.H4,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        PropsTable(
            listOf(
                PropInfo(
                    "open", "Boolean", "required",
                    stringResource(Res.string.collapsible_prop_content_open_desc),
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    stringResource(Res.string.collapsible_prop_content_modifier_desc),
                ),
                PropInfo(
                    "animation", "CollapsibleAnimation",
                    "Spring",
                    stringResource(Res.string.collapsible_prop_content_animation_desc),
                ),
                PropInfo(
                    "content", "() -> Unit", "required",
                    stringResource(Res.string.collapsible_prop_content_content_desc),
                ),
            ),
        )
    }
}
