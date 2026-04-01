package zed.rainxch.rikkaui.docs.sources

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.Res
import rikkaui.feature.docs.generated.resources.source_copied
import rikkaui.feature.docs.generated.resources.source_copy_button
import rikkaui.feature.docs.generated.resources.source_dependency
import rikkaui.feature.docs.generated.resources.source_file_label
import rikkaui.feature.docs.generated.resources.source_instruction
import rikkaui.feature.docs.generated.resources.source_no_source
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme

private fun resolveSourceDir(componentId: String): String =
    when (componentId) {
        "dropdown-menu" -> "dropdown"
        else -> componentId.replace("-", "")
    }

@Composable
fun SourceCodeViewer(componentId: String) {
    val dirName = resolveSourceDir(componentId)
    val sourceFiles = ComponentSources.sources[dirName]

    if (sourceFiles.isNullOrEmpty()) {
        BasicText(
            text = stringResource(Res.string.source_no_source),
            style =
                RikkaTheme.typography.p.merge(
                    TextStyle(color = RikkaTheme.colors.onMuted),
                ),
        )
        return
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
    ) {
        InstructionBanner()

        sourceFiles.forEach { file ->
            SourceFileBlock(
                fileName = file.name,
                content = file.content,
            )
        }
    }
}

@Composable
private fun InstructionBanner() {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(RikkaTheme.shapes.md)
                .background(RikkaTheme.colors.muted.copy(alpha = 0.5f))
                .padding(RikkaTheme.spacing.md),
        verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs),
    ) {
        BasicText(
            text = stringResource(Res.string.source_instruction),
            style =
                RikkaTheme.typography.small.merge(
                    TextStyle(color = RikkaTheme.colors.onBackground),
                ),
        )

        Box(
            modifier =
                Modifier
                    .clip(RikkaTheme.shapes.sm)
                    .background(RikkaTheme.colors.background)
                    .padding(
                        horizontal = RikkaTheme.spacing.sm,
                        vertical = RikkaTheme.spacing.xs,
                    ),
        ) {
            BasicText(
                text = stringResource(Res.string.source_dependency),
                style =
                    TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 13.sp,
                        color = RikkaTheme.colors.onBackground,
                    ),
            )
        }
    }
}

@Composable
private fun SourceFileBlock(
    fileName: String,
    content: String,
) {
    val clipboardManager = LocalClipboardManager.current
    val scope = rememberCoroutineScope()
    var copied by remember { mutableStateOf(false) }

    val copyLabel = stringResource(Res.string.source_copy_button)
    val copiedLabel = stringResource(Res.string.source_copied)

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(RikkaTheme.shapes.md)
                .background(RikkaTheme.colors.muted.copy(alpha = 0.3f)),
    ) {
        // Header: file name + copy button
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(RikkaTheme.colors.muted.copy(alpha = 0.5f))
                    .padding(
                        horizontal = RikkaTheme.spacing.md,
                        vertical = RikkaTheme.spacing.xs,
                    ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicText(
                text = stringResource(Res.string.source_file_label, fileName),
                style =
                    TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = RikkaTheme.colors.onMuted,
                    ),
            )

            Button(
                text = if (copied) copiedLabel else copyLabel,
                onClick = {
                    clipboardManager.setText(AnnotatedString(content))
                    copied = true
                    scope.launch {
                        delay(2000)
                        copied = false
                    }
                },
                variant =
                    if (copied) {
                        ButtonVariant.Secondary
                    } else {
                        ButtonVariant.Outline
                    },
                size = ButtonSize.Sm,
            )
        }

        // Source code
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .verticalScroll(rememberScrollState())
                    .horizontalScroll(rememberScrollState())
                    .padding(RikkaTheme.spacing.md),
        ) {
            BasicText(
                text = content,
                style =
                    TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        color = RikkaTheme.colors.onBackground,
                    ),
                modifier = Modifier.widthIn(min = 800.dp),
            )
        }
    }
}
