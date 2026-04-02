package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.*
import rikkaui.feature.docs.generated.resources.Res
import zed.rainxch.rikkaui.components.ui.skeleton.Skeleton
import zed.rainxch.rikkaui.components.ui.skeleton.SkeletonAnimation
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.catalog.ComponentFamilies
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentFamily
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DoAndDont
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.TabbedDocPage
import zed.rainxch.rikkaui.docs.components.VariantSelector
import zed.rainxch.rikkaui.foundation.RikkaTheme

@Composable
fun SkeletonDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_skeleton_name),
        description = stringResource(Res.string.skeleton_page_desc),
    )

    ComponentFamily(
        related = ComponentFamilies.LOADING,
        currentId = "skeleton",
    )

    TabbedDocPage(
        overview = { SkeletonOverviewTab() },
        usage = { SkeletonUsageTab() },
        api = { SkeletonApiTab() },
    )
}

// ─── Overview Tab ───────────────────────────────────────────

@Composable
private fun SkeletonOverviewTab() {
    DocSection(stringResource(Res.string.skeleton_section_animation_modes)) {
        var selectedAnim by remember { mutableStateOf("Pulse") }

        VariantSelector(
            options = listOf("Pulse", "Shimmer", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation =
            when (selectedAnim) {
                "Shimmer" -> SkeletonAnimation.Shimmer
                "None" -> SkeletonAnimation.None
                else -> SkeletonAnimation.Pulse
            }

        DemoBox {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
            ) {
                Skeleton(
                    modifier = Modifier.fillMaxWidth().height(16.dp),
                    animation = animation,
                )
                Skeleton(
                    modifier = Modifier.fillMaxWidth(0.75f).height(16.dp),
                    animation = animation,
                )
                Skeleton(
                    modifier = Modifier.fillMaxWidth(0.5f).height(16.dp),
                    animation = animation,
                )
            }
        }
    }

    DocSection(stringResource(Res.string.skeleton_section_card_placeholder)) {
        DemoBox {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.md,
                    ),
            ) {
                Row(
                    horizontalArrangement =
                        Arrangement.spacedBy(
                            RikkaTheme.spacing.md,
                        ),
                ) {
                    Skeleton(
                        modifier = Modifier.size(48.dp),
                        shape = RikkaTheme.shapes.full,
                    )
                    Column(
                        verticalArrangement =
                            Arrangement.spacedBy(
                                RikkaTheme.spacing.xs,
                            ),
                    ) {
                        Skeleton(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(16.dp),
                        )
                        Skeleton(
                            modifier =
                                Modifier
                                    .fillMaxWidth(0.6f)
                                    .height(12.dp),
                        )
                    }
                }
                Skeleton(
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    animation = SkeletonAnimation.Shimmer,
                )
            }
        }
    }
}

// ─── Usage Tab ──────────────────────────────────────────────

@Composable
private fun SkeletonUsageTab() {
    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
// Text line placeholder
Skeleton(
    modifier = Modifier.fillMaxWidth().height(16.dp),
)

// Avatar placeholder (circular)
Skeleton(
    modifier = Modifier.size(40.dp),
    shape = RikkaTheme.shapes.full,
)

// Shimmer card placeholder
Skeleton(
    modifier = Modifier.fillMaxWidth().height(120.dp),
    animation = SkeletonAnimation.Shimmer,
)
            """.trimIndent(),
        )
    }

    DocSection(stringResource(Res.string.skeleton_section_dos_donts)) {
        DoAndDont(
            doContent = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                ) {
                    Skeleton(modifier = Modifier.fillMaxWidth().height(16.dp))
                    Skeleton(modifier = Modifier.fillMaxWidth(0.7f).height(16.dp))
                }
            },
            doDescription = stringResource(Res.string.skeleton_do_layout_desc),
            dontContent = {
                Text("Instantly loaded content", variant = TextVariant.Muted)
            },
            dontDescription = stringResource(Res.string.skeleton_dont_instant_desc),
        )
    }
}

// ─── API Tab ────────────────────────────────────────────────

@Composable
private fun SkeletonApiTab() {
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(Res.string.skeleton_prop_modifier_desc),
                ),
                PropInfo(
                    "animation",
                    "SkeletonAnimation",
                    "Pulse",
                    stringResource(Res.string.skeleton_prop_animation_desc),
                ),
                PropInfo(
                    "shape",
                    "Shape",
                    "RikkaTheme.shapes.md",
                    stringResource(Res.string.skeleton_prop_shape_desc),
                ),
            ),
        )
    }
}
