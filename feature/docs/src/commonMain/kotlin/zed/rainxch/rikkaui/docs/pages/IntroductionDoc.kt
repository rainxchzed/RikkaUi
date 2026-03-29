package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection

/**
 * Introduction / "What is RikkaUI?" documentation page.
 */
@Composable
fun IntroductionDoc() {
    ComponentPageHeader(
        name = "Introduction",
        description =
            "A shadcn/ui-inspired component library " +
                "and design system for Compose " +
                "Multiplatform.",
    )

    DocSection("What is RikkaUI?") {
        Text(
            text =
                "RikkaUI provides beautifully styled, " +
                    "copy-paste components for Jetpack " +
                    "Compose and Compose Multiplatform. " +
                    "Built entirely on compose.foundation " +
                    "with zero Material3 dependency.",
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        Text(
            text =
                "The name means \"snowflake\" (\u516D\u82B1) " +
                    "or \"composing elements into harmony\" " +
                    "(\u7ACB\u82B1).",
            variant = TextVariant.Muted,
        )
    }

    DocSection("Why RikkaUI?") {
        Column(
            verticalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            FeatureItem(
                title = "No Material3 Lock-in",
                description =
                    "Every component is built on " +
                        "compose.foundation only. You get " +
                        "full control over styling without " +
                        "fighting Material3 defaults.",
            )
            FeatureItem(
                title = "Copy-Paste Ownership",
                description =
                    "Components are designed to be copied " +
                        "into your project. No dependency " +
                        "lock-in \u2014 you own the code.",
            )
            FeatureItem(
                title = "Spring-Physics Animations",
                description =
                    "Every interactive component has " +
                        "configurable animation enums " +
                        "(Spring, Tween, None). This is " +
                        "something shadcn/ui can\u2019t do.",
            )
            FeatureItem(
                title = "Cross-Platform",
                description =
                    "Works on Android, iOS, Desktop " +
                        "(JVM), and Web (Wasm). Same " +
                        "components, same API, everywhere.",
            )
            FeatureItem(
                title = "Fully Customizable Theme",
                description =
                    "20 semantic color tokens, 5 palettes, " +
                        "7 accent colors, 5 style presets. " +
                        "Every token is overridable.",
            )
        }
    }

    DocSection("Platform Support") {
        DemoBox {
            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
            ) {
                Badge(text = "Android")
                Badge(text = "iOS")
                Badge(text = "Desktop (JVM)")
                Badge(text = "Web (Wasm)")
            }
        }

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        Text(
            text =
                "RikkaUI works in both Compose " +
                    "Multiplatform (KMP) projects and " +
                    "native Android projects that use " +
                    "Jetpack Compose. No KMP setup " +
                    "required for Android-only projects " +
                    "\u2014 just add the dependency and go.",
            variant = TextVariant.P,
        )
    }

    DocSection("Component Count") {
        DemoBox {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
            ) {
                StatRow("Total Components", "41")
                StatRow("Animation Enums", "20+")
                StatRow("Style Presets", "5")
                StatRow("Color Palettes", "5")
                StatRow("Accent Colors", "7")
            }
        }
    }

    DocSection("Quick Example") {
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
