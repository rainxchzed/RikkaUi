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
import rikkaui.feature.docs.generated.resources.avatar_page_desc
import rikkaui.feature.docs.generated.resources.avatar_prop_animation_desc
import rikkaui.feature.docs.generated.resources.avatar_prop_fallback_desc
import rikkaui.feature.docs.generated.resources.avatar_prop_modifier_desc
import rikkaui.feature.docs.generated.resources.avatar_prop_size_desc
import rikkaui.feature.docs.generated.resources.avatar_prop_status_desc
import rikkaui.feature.docs.generated.resources.avatar_section_status
import rikkaui.feature.docs.generated.resources.component_avatar_name
import rikkaui.feature.docs.generated.resources.section_animations
import rikkaui.feature.docs.generated.resources.section_api_reference
import rikkaui.feature.docs.generated.resources.section_sizes
import rikkaui.feature.docs.generated.resources.section_usage
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.avatar.Avatar
import zed.rainxch.rikkaui.components.ui.avatar.AvatarAnimation
import zed.rainxch.rikkaui.components.ui.avatar.AvatarSize
import zed.rainxch.rikkaui.components.ui.avatar.AvatarStatus
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector

@Composable
fun AvatarDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_avatar_name),
        description = stringResource(Res.string.avatar_page_desc),
    )

    DocSection(stringResource(Res.string.section_sizes)) {
        DemoBox {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.md,
                ),
            ) {
                Avatar(fallback = "S", size = AvatarSize.Sm)
                Avatar(fallback = "JD", size = AvatarSize.Default)
                Avatar(fallback = "RX", size = AvatarSize.Lg)
            }
        }
    }

    DocSection(
        stringResource(Res.string.avatar_section_status),
    ) {
        DemoBox {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.md,
                ),
            ) {
                Avatar(fallback = "ON", status = AvatarStatus.Online)
                Avatar(fallback = "OF", status = AvatarStatus.Offline)
                Avatar(fallback = "BY", status = AvatarStatus.Busy)
                Avatar(fallback = "NS")
            }
        }
    }

    DocSection(stringResource(Res.string.section_animations)) {
        var selectedAnim by remember { mutableStateOf("FadeIn") }

        VariantSelector(
            options = listOf("FadeIn", "Scale", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = when (selectedAnim) {
            "Scale" -> AvatarAnimation.Scale
            "None" -> AvatarAnimation.None
            else -> AvatarAnimation.FadeIn
        }

        DemoBox {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.md,
                ),
            ) {
                Avatar(
                    fallback = "A",
                    animation = animation,
                    status = AvatarStatus.Online,
                )
                Avatar(
                    fallback = "B",
                    size = AvatarSize.Lg,
                    animation = animation,
                )
            }
        }
    }

    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
Avatar(fallback = "JD")
Avatar(fallback = "A", size = AvatarSize.Sm)
Avatar(fallback = "RX", size = AvatarSize.Lg)
Avatar(
    fallback = "JD",
    animation = AvatarAnimation.Scale,
    status = AvatarStatus.Online,
)
            """.trimIndent(),
        )
    }

    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "fallback", "String", "required",
                    stringResource(Res.string.avatar_prop_fallback_desc),
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    stringResource(Res.string.avatar_prop_modifier_desc),
                ),
                PropInfo(
                    "size", "AvatarSize", "Default",
                    stringResource(Res.string.avatar_prop_size_desc),
                ),
                PropInfo(
                    "animation", "AvatarAnimation", "FadeIn",
                    stringResource(Res.string.avatar_prop_animation_desc),
                ),
                PropInfo(
                    "status", "AvatarStatus?", "null",
                    stringResource(Res.string.avatar_prop_status_desc),
                ),
            ),
        )
    }
}
