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
import zed.rainxch.rikkaui.components.theme.RikkaTheme
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

/**
 * Documentation page for the Accordion component.
 *
 * Demonstrates expandable sections with practical examples,
 * animation styles, and multi-open patterns.
 */
@Composable
fun AccordionDoc() {
    ComponentPageHeader(
        name = "Accordion",
        description = "Vertically stacked sections that expand"
            + " and collapse. Use it for FAQs, settings"
            + " panels, or any grouped content.",
    )

    // ─── Basic Accordion ─────────────────────────────
    DocSection("Basic Accordion") {
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
                    title = "Notifications",
                    expanded = notificationsOpen,
                    onExpandedChange = {
                        notificationsOpen = it
                    },
                ) {
                    Text(
                        "Control which alerts you receive."
                            + " Enable push notifications for"
                            + " new messages, mentions, and"
                            + " activity updates.",
                        variant = TextVariant.Muted,
                    )
                }

                AccordionItem(
                    title = "Privacy",
                    expanded = privacyOpen,
                    onExpandedChange = {
                        privacyOpen = it
                    },
                ) {
                    Text(
                        "Manage who can see your profile"
                            + " and activity. Choose between"
                            + " public, friends-only, or"
                            + " private visibility.",
                        variant = TextVariant.Muted,
                    )
                }

                AccordionItem(
                    title = "Appearance",
                    expanded = appearanceOpen,
                    onExpandedChange = {
                        appearanceOpen = it
                    },
                ) {
                    Text(
                        "Customize your theme, font size,"
                            + " and color accent. Changes"
                            + " apply across all devices.",
                        variant = TextVariant.Muted,
                    )
                }
            }
        }
    }

    // ─── Animation Styles ────────────────────────────
    DocSection("Animation Styles") {
        var selectedAnim by remember { mutableStateOf("Spring") }

        VariantSelector(
            options = listOf("Spring", "Tween", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = when (selectedAnim) {
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
                    title = "Animation: $selectedAnim",
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    animation = animation,
                ) {
                    Text(
                        "Toggle this section to see the"
                            + " $selectedAnim animation in"
                            + " action. Try switching between"
                            + " styles to compare.",
                        variant = TextVariant.Muted,
                    )
                }
            }
        }
    }

    // ─── Multiple Open Items ─────────────────────────
    DocSection("Multiple Open Items") {
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
                    title = "General",
                    expanded = generalOpen,
                    onExpandedChange = {
                        generalOpen = it
                    },
                ) {
                    Text(
                        "Each AccordionItem manages its"
                            + " own state independently, so"
                            + " multiple sections can stay"
                            + " open at the same time.",
                        variant = TextVariant.Muted,
                    )
                }

                AccordionItem(
                    title = "Shipping",
                    expanded = shippingOpen,
                    onExpandedChange = {
                        shippingOpen = it
                    },
                ) {
                    Text(
                        "Free standard shipping on orders"
                            + " over \$50. Express delivery"
                            + " available at checkout.",
                        variant = TextVariant.Muted,
                    )
                }

                AccordionItem(
                    title = "Returns",
                    expanded = returnsOpen,
                    onExpandedChange = {
                        returnsOpen = it
                    },
                ) {
                    Text(
                        "30-day return policy for unused"
                            + " items. Contact support to"
                            + " start a return request.",
                        variant = TextVariant.Muted,
                    )
                }
            }
        }
    }

    // ─── Usage ───────────────────────────────────────
    DocSection("Usage") {
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
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "title", "String", "required",
                    "Header text in the clickable"
                        + " title row.",
                ),
                PropInfo(
                    "expanded", "Boolean", "required",
                    "Whether the content section is"
                        + " visible.",
                ),
                PropInfo(
                    "onExpandedChange",
                    "(Boolean) -> Unit", "required",
                    "Called when the user toggles the"
                        + " section.",
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier for layout and"
                        + " decoration.",
                ),
                PropInfo(
                    "animation", "AccordionAnimation",
                    "Spring",
                    "Animation style: Spring, Tween,"
                        + " None.",
                ),
                PropInfo(
                    "chevronIcon", "ImageVector",
                    "RikkaIcons.ChevronRight",
                    "Icon for the expand indicator."
                        + " Rotates 90 degrees when"
                        + " expanded.",
                ),
                PropInfo(
                    "content", "() -> Unit", "required",
                    "The expandable content slot.",
                ),
            ),
        )
    }
}
