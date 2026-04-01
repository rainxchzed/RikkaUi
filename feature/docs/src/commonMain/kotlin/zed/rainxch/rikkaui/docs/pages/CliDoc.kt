package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.Res
import rikkaui.feature.docs.generated.resources.cli_add_body
import rikkaui.feature.docs.generated.resources.cli_add_deps
import rikkaui.feature.docs.generated.resources.cli_add_flags_title
import rikkaui.feature.docs.generated.resources.cli_config_body
import rikkaui.feature.docs.generated.resources.cli_desc
import rikkaui.feature.docs.generated.resources.cli_how_note
import rikkaui.feature.docs.generated.resources.cli_how_step_1
import rikkaui.feature.docs.generated.resources.cli_how_step_2
import rikkaui.feature.docs.generated.resources.cli_how_step_3
import rikkaui.feature.docs.generated.resources.cli_how_step_4
import rikkaui.feature.docs.generated.resources.cli_init_body
import rikkaui.feature.docs.generated.resources.cli_init_creates
import rikkaui.feature.docs.generated.resources.cli_install_body
import rikkaui.feature.docs.generated.resources.cli_install_manual
import rikkaui.feature.docs.generated.resources.cli_list_body
import rikkaui.feature.docs.generated.resources.cli_section_add
import rikkaui.feature.docs.generated.resources.cli_section_config
import rikkaui.feature.docs.generated.resources.cli_section_how
import rikkaui.feature.docs.generated.resources.cli_section_init
import rikkaui.feature.docs.generated.resources.cli_section_install
import rikkaui.feature.docs.generated.resources.cli_section_list
import rikkaui.feature.docs.generated.resources.cli_title
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.foundation.RikkaTheme
import zed.rainxch.rikkaui.foundation.RikkaVersion

@Composable
fun CliDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.cli_title),
        description = stringResource(Res.string.cli_desc),
    )

    // ─── Install ────────────────────────────────────────────
    DocSection(stringResource(Res.string.cli_section_install)) {
        Text(
            text = stringResource(Res.string.cli_install_body),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        CodeBlock(
            """
curl -fsSL https://rikkaui.dev/install.sh | bash
            """.trimIndent(),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        Text(
            text = stringResource(Res.string.cli_install_manual),
            variant = TextVariant.Muted,
        )
    }

    // ─── Init ───────────────────────────────────────────────
    DocSection(stringResource(Res.string.cli_section_init)) {
        Text(
            text = stringResource(Res.string.cli_init_body),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        CodeBlock(
            """
rikkaui init

# Interactive prompts:
# Package name (e.g. com.example.ui): com.myapp.ui
# Source set [commonMain]:
#
# Created rikka.json
            """.trimIndent(),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        Text(
            text = stringResource(Res.string.cli_init_creates),
            variant = TextVariant.Muted,
        )
    }

    // ─── Add ────────────────────────────────────────────────
    DocSection(stringResource(Res.string.cli_section_add)) {
        Text(
            text = stringResource(Res.string.cli_add_body),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        CodeBlock(
            """
# Add a single component
rikkaui add button

# Add multiple components
rikkaui add button dialog card

# Add all components
rikkaui add --all

# Preview without writing files
rikkaui add button --dry-run

# Specify path and package inline (no config needed)
rikkaui add button --path ./ui --package com.myapp.ui
            """.trimIndent(),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        Text(
            text = stringResource(Res.string.cli_add_deps),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        CodeBlock(
            """
$ rikkaui add button

Resolving dependencies...

  + Added button
  + Added icon (dependency)
  + Added spinner (dependency)
  + Added text (dependency)

  Ensure these dependencies are in your build.gradle.kts:
  implementation("${RikkaVersion.FOUNDATION}")
            """.trimIndent(),
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        Text(
            text = stringResource(Res.string.cli_add_flags_title),
            variant = TextVariant.H4,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        PropsTable(
            props =
                listOf(
                    PropInfo(
                        name = "--path",
                        type = "String",
                        default = "from config",
                        description = "Target directory for component files",
                    ),
                    PropInfo(
                        name = "--package",
                        type = "String",
                        default = "from config",
                        description = "Target package name for import rewriting",
                    ),
                    PropInfo(
                        name = "--dry-run",
                        type = "Flag",
                        default = "false",
                        description = "Preview file operations without writing",
                    ),
                    PropInfo(
                        name = "--overwrite",
                        type = "Flag",
                        default = "false",
                        description = "Overwrite existing files",
                    ),
                    PropInfo(
                        name = "--all",
                        type = "Flag",
                        default = "false",
                        description = "Add all components from the registry",
                    ),
                    PropInfo(
                        name = "--registry",
                        type = "String",
                        default = "rikkaui.dev/r",
                        description = "Custom registry URL",
                    ),
                ),
        )
    }

    // ─── List ───────────────────────────────────────────────
    DocSection(stringResource(Res.string.cli_section_list)) {
        Text(
            text = stringResource(Res.string.cli_list_body),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        CodeBlock(
            """
$ rikkaui list

  RikkaUI v${RikkaVersion.NAME} — 40 components

  Layout:
    card                 A container with border and shadow.
    separator            A horizontal or vertical divider.
    accordion            Expandable content sections. [deps: icon, text]
    ...

  Forms:
    button               Interactive button with 6 variants. [deps: icon, spinner, text]
    input                Single-line text input.
    select               Dropdown select. [deps: icon, text]
    ...
            """.trimIndent(),
        )
    }

    // ─── Configuration ──────────────────────────────────────
    DocSection(stringResource(Res.string.cli_section_config)) {
        Text(
            text = stringResource(Res.string.cli_config_body),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        CodeBlock(
            """
// rikka.json
{
  "foundation": "${RikkaVersion.FOUNDATION}",
  "registry": "https://rikkaui.dev/r",
  "packageName": "com.myapp.ui",
  "componentsDir": "shared/src/commonMain/kotlin/com/myapp/ui"
}
            """.trimIndent(),
        )
    }

    // ─── How It Works ───────────────────────────────────────
    DocSection(stringResource(Res.string.cli_section_how)) {
        Column(
            verticalArrangement =
                Arrangement.spacedBy(RikkaTheme.spacing.xs),
        ) {
            StepItem("1", stringResource(Res.string.cli_how_step_1))
            StepItem("2", stringResource(Res.string.cli_how_step_2))
            StepItem("3", stringResource(Res.string.cli_how_step_3))
            StepItem("4", stringResource(Res.string.cli_how_step_4))
        }

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        Text(
            text = stringResource(Res.string.cli_how_note),
            variant = TextVariant.Muted,
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
