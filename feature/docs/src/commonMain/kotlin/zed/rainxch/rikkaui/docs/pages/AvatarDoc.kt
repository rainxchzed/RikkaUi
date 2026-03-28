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

/**
 * Documentation page for the Avatar component.
 *
 * Demonstrates sizes, animations, and status indicators.
 */
@Composable
fun AvatarDoc() {
    ComponentPageHeader(
        name = "Avatar",
        description = "A circular container displaying user initials "
            + "with optional status indicator and entrance animation.",
    )

    // ─── Sizes ──────────────────────────────────────────────
    DocSection("Sizes") {
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

    // ─── Status Indicators ──────────────────────────────────
    DocSection("Status Indicators") {
        DemoBox {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.md,
                ),
            ) {
                Avatar(
                    fallback = "ON",
                    status = AvatarStatus.Online,
                )
                Avatar(
                    fallback = "OF",
                    status = AvatarStatus.Offline,
                )
                Avatar(
                    fallback = "BY",
                    status = AvatarStatus.Busy,
                )
                Avatar(fallback = "NS")
            }
        }
    }

    // ─── Animations ─────────────────────────────────────────
    DocSection("Animations") {
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

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
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

    // ─── API Reference ──────────────────────────────────────
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "fallback", "String", "required",
                    "1-2 character initials shown as avatar content.",
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier for layout and decoration.",
                ),
                PropInfo(
                    "size", "AvatarSize", "Default",
                    "Size variant: Sm (32dp), Default (40dp), Lg (48dp).",
                ),
                PropInfo(
                    "animation", "AvatarAnimation", "FadeIn",
                    "Entrance animation: FadeIn, Scale, None.",
                ),
                PropInfo(
                    "status", "AvatarStatus?", "null",
                    "Status dot: Online, Offline, Busy, or null.",
                ),
            ),
        )
    }
}
