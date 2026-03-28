package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable

/**
 * Installation / setup documentation page.
 *
 * Covers module structure, Gradle dependencies, and
 * platform-specific setup for both KMP and Android-only.
 */
@Composable
fun InstallationDoc() {
    ComponentPageHeader(
        name = "Installation",
        description =
            "Add RikkaUI to your Compose Multiplatform " +
                "or native Android project.",
    )

    DocSection("Modules") {
        Text(
            text =
                "RikkaUI is split into two modules. " +
                    "You only need to depend on " +
                    ":components \u2014 it transitively " +
                    "includes :foundation.",
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        PropsTable(
            props =
                listOf(
                    PropInfo(
                        name = ":foundation",
                        type = "Theme System",
                        default = "\u2014",
                        description =
                            "Colors, typography, spacing, " +
                                "shapes, motion tokens. " +
                                "RikkaTheme provider.",
                    ),
                    PropInfo(
                        name = ":components",
                        type = "UI Components",
                        default = "\u2014",
                        description =
                            "41 styled components built on " +
                                "compose.foundation. " +
                                "Depends on :foundation.",
                    ),
                ),
        )
    }

    DocSection("Compose Multiplatform (KMP)") {
        Text(
            text =
                "For KMP projects targeting Android, " +
                    "iOS, Desktop, and/or Web:",
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        CodeBlock(
            """
// settings.gradle.kts
include(":rikkaui:components")
include(":rikkaui:foundation")

// Or as a published dependency:
// build.gradle.kts (shared module)
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(
                "zed.rainxch.rikkaui:components:<version>"
            )
        }
    }
}
            """.trimIndent(),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        Text(
            text =
                "The KMP Gradle plugin automatically " +
                    "resolves the correct platform " +
                    "artifact for each target " +
                    "(Android, iOS, JVM, WasmJs).",
            variant = TextVariant.Muted,
        )
    }

    DocSection("Native Android (Jetpack Compose)") {
        Text(
            text =
                "RikkaUI works in standard Android " +
                    "projects that use Jetpack Compose. " +
                    "No KMP setup required \u2014 Gradle " +
                    "resolves the Android artifact " +
                    "automatically.",
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        CodeBlock(
            """
// build.gradle.kts (app module)
dependencies {
    implementation(
        "zed.rainxch.rikkaui:components-android:<version>"
    )
}
            """.trimIndent(),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        Text(
            text =
                "The API is identical across all " +
                    "platforms. Components, theme tokens, " +
                    "and animations work the same way " +
                    "whether you\u2019re targeting Android " +
                    "only or going multiplatform.",
            variant = TextVariant.Muted,
        )
    }

    DocSection("Copy-Paste (No Dependency)") {
        Text(
            text =
                "Like shadcn/ui, you can also copy " +
                    "component source files directly " +
                    "into your project. Each component " +
                    "is self-contained and depends only " +
                    "on compose.foundation and the " +
                    "RikkaUI theme tokens.",
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        Column(
            verticalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.xs),
        ) {
            StepItem(
                step = "1",
                text =
                    "Copy the theme files from " +
                        ":foundation into your project.",
            )
            StepItem(
                step = "2",
                text =
                    "Copy the component files you need " +
                        "from :components.",
            )
            StepItem(
                step = "3",
                text =
                    "Update package names to match " +
                        "your project structure.",
            )
            StepItem(
                step = "4",
                text =
                    "Customize colors, typography, " +
                        "and spacing to your brand.",
            )
        }
    }

    DocSection("Requirements") {
        PropsTable(
            props =
                listOf(
                    PropInfo(
                        name = "Kotlin",
                        type = "2.0+",
                        default = "\u2014",
                        description =
                            "Required for Compose Compiler " +
                                "plugin.",
                    ),
                    PropInfo(
                        name = "Compose",
                        type = "1.7+",
                        default = "\u2014",
                        description =
                            "Compose Multiplatform " +
                                "or Jetpack Compose.",
                    ),
                    PropInfo(
                        name = "Material3",
                        type = "Not needed",
                        default = "\u2014",
                        description =
                            "RikkaUI has zero Material3 " +
                                "dependency. Uses " +
                                "compose.foundation only.",
                    ),
                    PropInfo(
                        name = "minSdk",
                        type = "24 (Android)",
                        default = "\u2014",
                        description =
                            "Android minimum SDK version.",
                    ),
                ),
        )
    }

    DocSection("Verify Installation") {
        Text(
            text =
                "After adding the dependency, verify " +
                    "it works with a minimal example:",
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        CodeBlock(
            """
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.theme.RikkaPalette
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.text.Text

@Composable
fun App() {
    val colors = RikkaPalette.Zinc.resolve(isDark = true)

    RikkaTheme(colors = colors) {
        Button(
            text = "Hello RikkaUI!",
            onClick = { },
        )
    }
}
            """.trimIndent(),
        )
    }
}

@Composable
private fun StepItem(
    step: String,
    text: String,
) {
    Text(
        text = "$step. $text",
        variant = TextVariant.P,
    )
}
