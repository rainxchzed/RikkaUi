package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.Res
import rikkaui.feature.docs.generated.resources.install_android_body
import rikkaui.feature.docs.generated.resources.install_android_note
import rikkaui.feature.docs.generated.resources.install_copy_paste_body
import rikkaui.feature.docs.generated.resources.install_desc
import rikkaui.feature.docs.generated.resources.install_kmp_body
import rikkaui.feature.docs.generated.resources.install_kmp_note
import rikkaui.feature.docs.generated.resources.install_module_components_desc
import rikkaui.feature.docs.generated.resources.install_module_foundation_desc
import rikkaui.feature.docs.generated.resources.install_modules_body
import rikkaui.feature.docs.generated.resources.install_req_compose_desc
import rikkaui.feature.docs.generated.resources.install_req_kotlin_desc
import rikkaui.feature.docs.generated.resources.install_req_material3_desc
import rikkaui.feature.docs.generated.resources.install_req_minsdk_desc
import rikkaui.feature.docs.generated.resources.install_section_android
import rikkaui.feature.docs.generated.resources.install_section_copy_paste
import rikkaui.feature.docs.generated.resources.install_section_kmp
import rikkaui.feature.docs.generated.resources.install_section_modules
import rikkaui.feature.docs.generated.resources.install_section_requirements
import rikkaui.feature.docs.generated.resources.install_section_verify
import rikkaui.feature.docs.generated.resources.install_step_1
import rikkaui.feature.docs.generated.resources.install_step_2
import rikkaui.feature.docs.generated.resources.install_step_3
import rikkaui.feature.docs.generated.resources.install_step_4
import rikkaui.feature.docs.generated.resources.install_title
import rikkaui.feature.docs.generated.resources.install_verify_body
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.foundation.RikkaTheme

/**
 * Installation / setup documentation page.
 *
 * Covers module structure, Gradle dependencies, and
 * platform-specific setup for both KMP and Android-only.
 */
@Composable
fun InstallationDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.install_title),
        description = stringResource(Res.string.install_desc),
    )

    DocSection(
        stringResource(Res.string.install_section_modules),
    ) {
        Text(
            text =
                stringResource(
                    Res.string.install_modules_body,
                ),
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
                            stringResource(
                                Res.string.install_module_foundation_desc,
                            ),
                    ),
                    PropInfo(
                        name = ":components",
                        type = "UI Components",
                        default = "\u2014",
                        description =
                            stringResource(
                                Res.string.install_module_components_desc,
                            ),
                    ),
                ),
        )
    }

    DocSection(
        stringResource(Res.string.install_section_kmp),
    ) {
        Text(
            text = stringResource(Res.string.install_kmp_body),
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
            text = stringResource(Res.string.install_kmp_note),
            variant = TextVariant.Muted,
        )
    }

    DocSection(
        stringResource(Res.string.install_section_android),
    ) {
        Text(
            text =
                stringResource(
                    Res.string.install_android_body,
                ),
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
                stringResource(
                    Res.string.install_android_note,
                ),
            variant = TextVariant.Muted,
        )
    }

    DocSection(
        stringResource(Res.string.install_section_copy_paste),
    ) {
        Text(
            text =
                stringResource(
                    Res.string.install_copy_paste_body,
                ),
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
                    stringResource(
                        Res.string.install_step_1,
                    ),
            )
            StepItem(
                step = "2",
                text =
                    stringResource(
                        Res.string.install_step_2,
                    ),
            )
            StepItem(
                step = "3",
                text =
                    stringResource(
                        Res.string.install_step_3,
                    ),
            )
            StepItem(
                step = "4",
                text =
                    stringResource(
                        Res.string.install_step_4,
                    ),
            )
        }
    }

    DocSection(
        stringResource(Res.string.install_section_requirements),
    ) {
        PropsTable(
            props =
                listOf(
                    PropInfo(
                        name = "Kotlin",
                        type = "2.0+",
                        default = "\u2014",
                        description =
                            stringResource(
                                Res.string.install_req_kotlin_desc,
                            ),
                    ),
                    PropInfo(
                        name = "Compose",
                        type = "1.7+",
                        default = "\u2014",
                        description =
                            stringResource(
                                Res.string.install_req_compose_desc,
                            ),
                    ),
                    PropInfo(
                        name = "Material3",
                        type = "Not needed",
                        default = "\u2014",
                        description =
                            stringResource(
                                Res.string.install_req_material3_desc,
                            ),
                    ),
                    PropInfo(
                        name = "minSdk",
                        type = "24 (Android)",
                        default = "\u2014",
                        description =
                            stringResource(
                                Res.string.install_req_minsdk_desc,
                            ),
                    ),
                ),
        )
    }

    DocSection(
        stringResource(Res.string.install_section_verify),
    ) {
        Text(
            text =
                stringResource(
                    Res.string.install_verify_body,
                ),
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
