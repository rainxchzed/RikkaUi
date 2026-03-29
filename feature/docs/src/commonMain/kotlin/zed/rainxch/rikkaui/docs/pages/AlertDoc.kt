package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
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
import rikkaui.feature.docs.generated.resources.alert_demo_check_network
import rikkaui.feature.docs.generated.resources.alert_demo_cli_hint
import rikkaui.feature.docs.generated.resources.alert_demo_connection_lost
import rikkaui.feature.docs.generated.resources.alert_demo_error
import rikkaui.feature.docs.generated.resources.alert_demo_heads_up
import rikkaui.feature.docs.generated.resources.alert_demo_new_update
import rikkaui.feature.docs.generated.resources.alert_demo_new_version
import rikkaui.feature.docs.generated.resources.alert_demo_session_expired
import rikkaui.feature.docs.generated.resources.alert_page_desc
import rikkaui.feature.docs.generated.resources.alert_prop_animation_desc
import rikkaui.feature.docs.generated.resources.alert_prop_content_desc
import rikkaui.feature.docs.generated.resources.alert_prop_icon_desc
import rikkaui.feature.docs.generated.resources.alert_prop_label_desc
import rikkaui.feature.docs.generated.resources.alert_prop_modifier_desc
import rikkaui.feature.docs.generated.resources.alert_prop_variant_desc
import rikkaui.feature.docs.generated.resources.component_alert_name
import rikkaui.feature.docs.generated.resources.section_animations
import rikkaui.feature.docs.generated.resources.section_api_reference
import rikkaui.feature.docs.generated.resources.section_usage
import rikkaui.feature.docs.generated.resources.section_variants
import zed.rainxch.rikkaui.components.ui.alert.Alert
import zed.rainxch.rikkaui.components.ui.alert.AlertAnimation
import zed.rainxch.rikkaui.components.ui.alert.AlertDescription
import zed.rainxch.rikkaui.components.ui.alert.AlertTitle
import zed.rainxch.rikkaui.components.ui.alert.AlertVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector
import zed.rainxch.rikkaui.foundation.RikkaTheme

/**
 * Documentation page for the Alert component.
 *
 * Demonstrates alert variants, animations, and structured
 * sections (AlertTitle, AlertDescription).
 */
@Composable
fun AlertDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_alert_name),
        description =
            stringResource(
                Res.string.alert_page_desc,
            ),
    )

    // ─── Variants ───────────────────────────────────────────
    DocSection(stringResource(Res.string.section_variants)) {
        var selectedVariant by remember { mutableStateOf("Default") }

        VariantSelector(
            options = listOf("Default", "Destructive"),
            selected = selectedVariant,
            onSelect = { selectedVariant = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val variant =
            when (selectedVariant) {
                "Destructive" -> AlertVariant.Destructive
                else -> AlertVariant.Default
            }

        DemoBox {
            Alert(
                variant = variant,
                modifier = Modifier.fillMaxWidth(),
            ) {
                AlertTitle(
                    text =
                        if (variant == AlertVariant.Destructive) {
                            stringResource(Res.string.alert_demo_error)
                        } else {
                            stringResource(Res.string.alert_demo_heads_up)
                        },
                    variant = variant,
                )
                AlertDescription(
                    text =
                        if (variant == AlertVariant.Destructive) {
                            stringResource(
                                Res.string.alert_demo_session_expired,
                            )
                        } else {
                            stringResource(
                                Res.string.alert_demo_cli_hint,
                            )
                        },
                    variant = variant,
                )
            }
        }
    }

    // ─── Animations ─────────────────────────────────────────
    DocSection(stringResource(Res.string.section_animations)) {
        var selectedAnim by remember { mutableStateOf("None") }

        VariantSelector(
            options = listOf("SlideIn", "Fade", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation =
            when (selectedAnim) {
                "SlideIn" -> AlertAnimation.SlideIn
                "Fade" -> AlertAnimation.Fade
                else -> AlertAnimation.None
            }

        DemoBox {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.md,
                    ),
            ) {
                Alert(
                    animation = animation,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    AlertTitle(
                        stringResource(
                            Res.string.alert_demo_new_update,
                        ),
                    )
                    AlertDescription(
                        stringResource(
                            Res.string.alert_demo_new_version,
                        ),
                    )
                }
                Alert(
                    variant = AlertVariant.Destructive,
                    animation = animation,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    AlertTitle(
                        stringResource(
                            Res.string.alert_demo_connection_lost,
                        ),
                        variant = AlertVariant.Destructive,
                    )
                    AlertDescription(
                        stringResource(
                            Res.string.alert_demo_check_network,
                        ),
                        variant = AlertVariant.Destructive,
                    )
                }
            }
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
Alert {
    AlertTitle("Heads up!")
    AlertDescription("You can add components using the CLI.")
}

Alert(variant = AlertVariant.Destructive) {
    AlertTitle("Error", variant = AlertVariant.Destructive)
    AlertDescription(
        "Your session has expired.",
        variant = AlertVariant.Destructive,
    )
}

// With animation
Alert(animation = AlertAnimation.SlideIn) {
    AlertTitle("New message")
    AlertDescription("You have 3 unread messages.")
}
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection(
        stringResource(Res.string.section_api_reference),
    ) {
        PropsTable(
            listOf(
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(
                        Res.string.alert_prop_modifier_desc,
                    ),
                ),
                PropInfo(
                    "variant",
                    "AlertVariant",
                    "Default",
                    stringResource(
                        Res.string.alert_prop_variant_desc,
                    ),
                ),
                PropInfo(
                    "animation",
                    "AlertAnimation",
                    "None",
                    stringResource(
                        Res.string.alert_prop_animation_desc,
                    ),
                ),
                PropInfo(
                    "icon",
                    "(@Composable () -> Unit)?",
                    "null",
                    stringResource(
                        Res.string.alert_prop_icon_desc,
                    ),
                ),
                PropInfo(
                    "label",
                    "String",
                    "\"\"",
                    stringResource(
                        Res.string.alert_prop_label_desc,
                    ),
                ),
                PropInfo(
                    "content",
                    "ColumnScope.() -> Unit",
                    "required",
                    stringResource(
                        Res.string.alert_prop_content_desc,
                    ),
                ),
            ),
        )
    }
}
