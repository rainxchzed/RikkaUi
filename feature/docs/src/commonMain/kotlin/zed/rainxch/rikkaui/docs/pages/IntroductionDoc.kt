package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.Res
import rikkaui.feature.docs.generated.resources.intro_badge_android
import rikkaui.feature.docs.generated.resources.intro_badge_desktop
import rikkaui.feature.docs.generated.resources.intro_badge_ios
import rikkaui.feature.docs.generated.resources.intro_badge_web
import rikkaui.feature.docs.generated.resources.intro_desc
import rikkaui.feature.docs.generated.resources.intro_feature_copy_paste_desc
import rikkaui.feature.docs.generated.resources.intro_feature_copy_paste_title
import rikkaui.feature.docs.generated.resources.intro_feature_cross_platform_desc
import rikkaui.feature.docs.generated.resources.intro_feature_cross_platform_title
import rikkaui.feature.docs.generated.resources.intro_feature_no_material_desc
import rikkaui.feature.docs.generated.resources.intro_feature_no_material_title
import rikkaui.feature.docs.generated.resources.intro_feature_spring_desc
import rikkaui.feature.docs.generated.resources.intro_feature_spring_title
import rikkaui.feature.docs.generated.resources.intro_feature_theme_desc
import rikkaui.feature.docs.generated.resources.intro_feature_theme_title
import rikkaui.feature.docs.generated.resources.intro_name_meaning
import rikkaui.feature.docs.generated.resources.intro_platform_body
import rikkaui.feature.docs.generated.resources.intro_section_component_count
import rikkaui.feature.docs.generated.resources.intro_section_platform_support
import rikkaui.feature.docs.generated.resources.intro_section_quick_example
import rikkaui.feature.docs.generated.resources.intro_section_what_is
import rikkaui.feature.docs.generated.resources.intro_section_why
import rikkaui.feature.docs.generated.resources.intro_stat_accent_colors
import rikkaui.feature.docs.generated.resources.intro_stat_accent_colors_value
import rikkaui.feature.docs.generated.resources.intro_stat_animation_enums
import rikkaui.feature.docs.generated.resources.intro_stat_animation_enums_value
import rikkaui.feature.docs.generated.resources.intro_stat_color_palettes
import rikkaui.feature.docs.generated.resources.intro_stat_color_palettes_value
import rikkaui.feature.docs.generated.resources.intro_stat_style_presets
import rikkaui.feature.docs.generated.resources.intro_stat_style_presets_value
import rikkaui.feature.docs.generated.resources.intro_stat_total_components
import rikkaui.feature.docs.generated.resources.intro_stat_total_components_value
import rikkaui.feature.docs.generated.resources.intro_title
import rikkaui.feature.docs.generated.resources.intro_what_is_body
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.foundation.RikkaTheme

/**
 * Introduction / "What is RikkaUI?" documentation page.
 */
@Composable
fun IntroductionDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.intro_title),
        description = stringResource(Res.string.intro_desc),
    )

    DocSection(stringResource(Res.string.intro_section_what_is)) {
        Text(
            text = stringResource(Res.string.intro_what_is_body),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        Text(
            text = stringResource(Res.string.intro_name_meaning),
            variant = TextVariant.Muted,
        )
    }

    DocSection(stringResource(Res.string.intro_section_why)) {
        Column(
            verticalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            FeatureItem(
                title =
                    stringResource(
                        Res.string.intro_feature_no_material_title,
                    ),
                description =
                    stringResource(
                        Res.string.intro_feature_no_material_desc,
                    ),
            )
            FeatureItem(
                title =
                    stringResource(
                        Res.string.intro_feature_copy_paste_title,
                    ),
                description =
                    stringResource(
                        Res.string.intro_feature_copy_paste_desc,
                    ),
            )
            FeatureItem(
                title =
                    stringResource(
                        Res.string.intro_feature_spring_title,
                    ),
                description =
                    stringResource(
                        Res.string.intro_feature_spring_desc,
                    ),
            )
            FeatureItem(
                title =
                    stringResource(
                        Res.string.intro_feature_cross_platform_title,
                    ),
                description =
                    stringResource(
                        Res.string.intro_feature_cross_platform_desc,
                    ),
            )
            FeatureItem(
                title =
                    stringResource(
                        Res.string.intro_feature_theme_title,
                    ),
                description =
                    stringResource(
                        Res.string.intro_feature_theme_desc,
                    ),
            )
        }
    }

    DocSection(
        stringResource(Res.string.intro_section_platform_support),
    ) {
        DemoBox {
            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
            ) {
                Badge(
                    text =
                        stringResource(
                            Res.string.intro_badge_android,
                        ),
                )
                Badge(
                    text =
                        stringResource(
                            Res.string.intro_badge_ios,
                        ),
                )
                Badge(
                    text =
                        stringResource(
                            Res.string.intro_badge_desktop,
                        ),
                )
                Badge(
                    text =
                        stringResource(
                            Res.string.intro_badge_web,
                        ),
                )
            }
        }

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        Text(
            text = stringResource(Res.string.intro_platform_body),
            variant = TextVariant.P,
        )
    }

    DocSection(
        stringResource(Res.string.intro_section_component_count),
    ) {
        DemoBox {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
            ) {
                StatRow(
                    stringResource(
                        Res.string.intro_stat_total_components,
                    ),
                    stringResource(
                        Res.string.intro_stat_total_components_value,
                    ),
                )
                StatRow(
                    stringResource(
                        Res.string.intro_stat_animation_enums,
                    ),
                    stringResource(
                        Res.string.intro_stat_animation_enums_value,
                    ),
                )
                StatRow(
                    stringResource(
                        Res.string.intro_stat_style_presets,
                    ),
                    stringResource(
                        Res.string.intro_stat_style_presets_value,
                    ),
                )
                StatRow(
                    stringResource(
                        Res.string.intro_stat_color_palettes,
                    ),
                    stringResource(
                        Res.string.intro_stat_color_palettes_value,
                    ),
                )
                StatRow(
                    stringResource(
                        Res.string.intro_stat_accent_colors,
                    ),
                    stringResource(
                        Res.string.intro_stat_accent_colors_value,
                    ),
                )
            }
        }
    }

    DocSection(
        stringResource(Res.string.intro_section_quick_example),
    ) {
        CodeBlock(
            """
RikkaTheme(
    colors = RikkaPalette.Zinc.resolve(isDark = true),
) {
    Button(
        text = "Click me",
        onClick = { /* ... */ },
        variant = ButtonVariant.Default,
        animation = ButtonAnimation.Scale,
    )
}
            """.trimIndent(),
        )
    }
}

@Composable
private fun FeatureItem(
    title: String,
    description: String,
) {
    Column {
        Text(
            text = title,
            variant = TextVariant.P,
        )
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        Text(
            text = description,
            variant = TextVariant.Muted,
        )
    }
}

@Composable
private fun StatRow(
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = label, variant = TextVariant.P)
        Badge(
            text = value,
            variant = BadgeVariant.Secondary,
        )
    }
}
