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
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.skeleton.Skeleton
import zed.rainxch.rikkaui.components.ui.skeleton.SkeletonAnimation
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector

/**
 * Documentation page for the Skeleton component.
 *
 * Demonstrates loading placeholders with different
 * animation styles and shapes.
 */
@Composable
fun SkeletonDoc() {
    ComponentPageHeader(
        name = "Skeleton",
        description = "An animated loading placeholder that indicates "
            + "content is being loaded.",
    )

    // ─── Animation Modes ────────────────────────────────────
    DocSection("Animation Modes") {
        var selectedAnim by remember { mutableStateOf("Pulse") }

        VariantSelector(
            options = listOf("Pulse", "Shimmer", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = when (selectedAnim) {
            "Shimmer" -> SkeletonAnimation.Shimmer
            "None" -> SkeletonAnimation.None
            else -> SkeletonAnimation.Pulse
        }

        DemoBox {
            Column(
                verticalArrangement = Arrangement.spacedBy(
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

    // ─── Card Placeholder ───────────────────────────────────
    DocSection("Card Placeholder") {
        DemoBox {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.md,
                ),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        RikkaTheme.spacing.md,
                    ),
                ) {
                    Skeleton(
                        modifier = Modifier.size(48.dp),
                        shape = RikkaTheme.shapes.full,
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            RikkaTheme.spacing.xs,
                        ),
                    ) {
                        Skeleton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(16.dp),
                        )
                        Skeleton(
                            modifier = Modifier
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

    // ─── Usage ──────────────────────────────────────────────
    DocSection("Usage") {
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

    // ─── API Reference ──────────────────────────────────────
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier for layout. Set size via Modifier.size/height.",
                ),
                PropInfo(
                    "animation", "SkeletonAnimation", "Pulse",
                    "Animation style: Pulse, Shimmer, None.",
                ),
                PropInfo(
                    "shape", "Shape", "RikkaTheme.shapes.md",
                    "Clip shape. Use shapes.full for circular.",
                ),
            ),
        )
    }
}
