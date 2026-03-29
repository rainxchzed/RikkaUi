package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.Res
import rikkaui.feature.docs.generated.resources.accordion_demo_animation_body
import rikkaui.feature.docs.generated.resources.accordion_demo_animation_title
import rikkaui.feature.docs.generated.resources.accordion_demo_appearance
import rikkaui.feature.docs.generated.resources.accordion_demo_appearance_desc
import rikkaui.feature.docs.generated.resources.accordion_demo_general
import rikkaui.feature.docs.generated.resources.accordion_demo_general_desc
import rikkaui.feature.docs.generated.resources.accordion_demo_notifications
import rikkaui.feature.docs.generated.resources.accordion_demo_notifications_desc
import rikkaui.feature.docs.generated.resources.accordion_demo_privacy
import rikkaui.feature.docs.generated.resources.accordion_demo_privacy_desc
import rikkaui.feature.docs.generated.resources.accordion_demo_returns
import rikkaui.feature.docs.generated.resources.accordion_demo_returns_desc
import rikkaui.feature.docs.generated.resources.accordion_demo_shipping
import rikkaui.feature.docs.generated.resources.accordion_demo_shipping_desc
import rikkaui.feature.docs.generated.resources.accordion_page_desc
import rikkaui.feature.docs.generated.resources.accordion_prop_animation_desc
import rikkaui.feature.docs.generated.resources.accordion_prop_chevron_desc
import rikkaui.feature.docs.generated.resources.accordion_prop_content_desc
import rikkaui.feature.docs.generated.resources.accordion_prop_expanded_desc
import rikkaui.feature.docs.generated.resources.accordion_prop_modifier_desc
import rikkaui.feature.docs.generated.resources.accordion_prop_on_expanded_change_desc
import rikkaui.feature.docs.generated.resources.accordion_prop_title_desc
import rikkaui.feature.docs.generated.resources.accordion_section_animation_styles
import rikkaui.feature.docs.generated.resources.accordion_section_basic
import rikkaui.feature.docs.generated.resources.accordion_section_multiple_open
import rikkaui.feature.docs.generated.resources.component_accordion_name
import rikkaui.feature.docs.generated.resources.section_api_reference
import rikkaui.feature.docs.generated.resources.section_usage
import zed.rainxch.rikkaui.components.ui.accordion.AccordionAnimation
import zed.rainxch.rikkaui.components.ui.accordion.AccordionItem
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector
import zed.rainxch.rikkaui.foundation.RikkaTheme

/**
 * Documentation page for the Accordion component.
 *
 * Demonstrates expandable sections with practical examples,
 * animation styles, and multi-open patterns.
 */
@Composable
fun AccordionDoc() {
    ComponentPageHeader(
        name =
            stringResource(
                Res.string.component_accordion_name,
            ),
        description =
            stringResource(
                Res.string.accordion_page_desc,
            ),
    )

    // ─── Basic Accordion ─────────────────────────────
    DocSection(
        stringResource(Res.string.accordion_section_basic),
    ) {
        DemoBox {
            Column(modifier = Modifier.fillMaxWidth()) {
                var notificationsOpen by remember {
                    mutableStateOf(false)
                }
                var privacyOpen by remember {
                    mutableStateOf(false)
                }
                var appearanceOpen by remember {
                    mutableStateOf(false)
                }

                AccordionItem(
                    title =
                        stringResource(
                            Res.string.accordion_demo_notifications,
                        ),
                    expanded = notificationsOpen,
                    onExpandedChange = {
                        notificationsOpen = it
                    },
                ) {
                    Text(
                        stringResource(
                            Res.string.accordion_demo_notifications_desc,
                        ),
                        variant = TextVariant.Muted,
                    )
                }

                AccordionItem(
                    title =
                        stringResource(
                            Res.string.accordion_demo_privacy,
                        ),
                    expanded = privacyOpen,
                    onExpandedChange = {
                        privacyOpen = it
                    },
                ) {
                    Text(
                        stringResource(
                            Res.string.accordion_demo_privacy_desc,
                        ),
                        variant = TextVariant.Muted,
                    )
                }

                AccordionItem(
                    title =
                        stringResource(
                            Res.string.accordion_demo_appearance,
                        ),
                    expanded = appearanceOpen,
                    onExpandedChange = {
                        appearanceOpen = it
                    },
                ) {
                    Text(
                        stringResource(
                            Res.string.accordion_demo_appearance_desc,
                        ),
                        variant = TextVariant.Muted,
                    )
                }
            }
        }
    }

    // ─── Animation Styles ────────────────────────────
    DocSection(
        stringResource(
            Res.string.accordion_section_animation_styles,
        ),
    ) {
        var selectedAnim by remember { mutableStateOf("Spring") }

        VariantSelector(
            options = listOf("Spring", "Tween", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation =
            when (selectedAnim) {
                "Tween" -> AccordionAnimation.Tween
                "None" -> AccordionAnimation.None
                else -> AccordionAnimation.Spring
            }

        DemoBox {
            Column(modifier = Modifier.fillMaxWidth()) {
                var expanded by remember {
                    mutableStateOf(false)
                }

                AccordionItem(
                    title =
                        stringResource(
                            Res.string.accordion_demo_animation_title,
                            selectedAnim,
                        ),
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    animation = animation,
                ) {
                    Text(
                        stringResource(
                            Res.string.accordion_demo_animation_body,
                            selectedAnim,
                        ),
                        variant = TextVariant.Muted,
                    )
                }
            }
        }
    }

    // ─── Multiple Open Items ─────────────────────────
    DocSection(
        stringResource(
            Res.string.accordion_section_multiple_open,
        ),
    ) {
        DemoBox {
            Column(modifier = Modifier.fillMaxWidth()) {
                var generalOpen by remember {
                    mutableStateOf(true)
                }
                var shippingOpen by remember {
                    mutableStateOf(true)
                }
                var returnsOpen by remember {
                    mutableStateOf(false)
                }

                AccordionItem(
                    title =
                        stringResource(
                            Res.string.accordion_demo_general,
                        ),
                    expanded = generalOpen,
                    onExpandedChange = {
                        generalOpen = it
                    },
                ) {
                    Text(
                        stringResource(
                            Res.string.accordion_demo_general_desc,
                        ),
                        variant = TextVariant.Muted,
                    )
                }

                AccordionItem(
                    title =
                        stringResource(
                            Res.string.accordion_demo_shipping,
                        ),
                    expanded = shippingOpen,
                    onExpandedChange = {
                        shippingOpen = it
                    },
                ) {
                    Text(
                        stringResource(
                            Res.string.accordion_demo_shipping_desc,
                        ),
                        variant = TextVariant.Muted,
                    )
                }

                AccordionItem(
                    title =
                        stringResource(
                            Res.string.accordion_demo_returns,
                        ),
                    expanded = returnsOpen,
                    onExpandedChange = {
                        returnsOpen = it
                    },
                ) {
                    Text(
                        stringResource(
                            Res.string.accordion_demo_returns_desc,
                        ),
                        variant = TextVariant.Muted,
                    )
                }
            }
        }
    }

    // ─── Usage ───────────────────────────────────────
    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
var expanded by remember { mutableStateOf(false) }

AccordionItem(
    title = "Section title",
    expanded = expanded,
    onExpandedChange = { expanded = it },
) {
    Text("Expandable content goes here.")
}
            """.trimIndent(),
        )
    }

    // ─── API Reference ───────────────────────────────
    DocSection(
        stringResource(Res.string.section_api_reference),
    ) {
        PropsTable(
            listOf(
                PropInfo(
                    "title",
                    "String",
                    "required",
                    stringResource(
                        Res.string.accordion_prop_title_desc,
                    ),
                ),
                PropInfo(
                    "expanded",
                    "Boolean",
                    "required",
                    stringResource(
                        Res.string.accordion_prop_expanded_desc,
                    ),
                ),
                PropInfo(
                    "onExpandedChange",
                    "(Boolean) -> Unit",
                    "required",
                    stringResource(
                        Res.string.accordion_prop_on_expanded_change_desc,
                    ),
                ),
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(
                        Res.string.accordion_prop_modifier_desc,
                    ),
                ),
                PropInfo(
                    "animation",
                    "AccordionAnimation",
                    "Spring",
                    stringResource(
                        Res.string.accordion_prop_animation_desc,
                    ),
                ),
                PropInfo(
                    "chevronIcon",
                    "ImageVector",
                    "RikkaIcons.ChevronRight",
                    stringResource(
                        Res.string.accordion_prop_chevron_desc,
                    ),
                ),
                PropInfo(
                    "content",
                    "() -> Unit",
                    "required",
                    stringResource(
                        Res.string.accordion_prop_content_desc,
                    ),
                ),
            ),
        )
    }
}
