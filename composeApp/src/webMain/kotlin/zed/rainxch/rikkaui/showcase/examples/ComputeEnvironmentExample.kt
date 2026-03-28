package zed.rainxch.rikkaui.showcase.examples

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

/**
 * Compute environment selection card with radio options and GPU counter.
 *
 * Demonstrates nested [Card] layouts, custom radio indicators,
 * and increment/decrement controls — inspired by shadcn's
 * compute environment selector.
 */
@Composable
fun ComputeEnvironmentExample() {
    var selectedOption by remember { mutableStateOf(0) }
    var gpuCount by remember { mutableStateOf(8) }

    Card {
        Text(
            text = "Compute Environment",
            variant = TextVariant.H4,
        )
        Text(
            text = "Select the compute environment for your cluster.",
            variant = TextVariant.Muted,
        )

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        // ─── Option 1: Kubernetes (selected) ────────────
        OptionCard(
            title = "Kubernetes",
            description =
                "Run GPU workloads on a K8s configured" +
                    " cluster. This is the default.",
            selected = selectedOption == 0,
            enabled = true,
            onClick = { selectedOption = 0 },
        )

        // ─── Option 2: Virtual Machine (disabled) ───────
        OptionCard(
            title = "Virtual Machine",
            description =
                "Access a VM configured cluster to run" +
                    " workloads. (Coming soon)",
            selected = selectedOption == 1,
            enabled = false,
            onClick = { },
        )

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        // ─── GPU counter row ────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Number of GPUs",
                variant = TextVariant.P,
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
            ) {
                Box(
                    modifier =
                        Modifier
                            .border(
                                width = 1.dp,
                                color = RikkaTheme.colors.border,
                                shape = RikkaTheme.shapes.md,
                            ).padding(
                                horizontal = RikkaTheme.spacing.sm,
                                vertical = RikkaTheme.spacing.xs,
                            ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = gpuCount.toString(),
                        variant = TextVariant.Small,
                    )
                }
                Button(
                    onClick = {
                        if (gpuCount > 1) gpuCount--
                    },
                    variant = ButtonVariant.Outline,
                    size = ButtonSize.Icon,
                ) {
                    Text(text = "\u2212", variant = TextVariant.P)
                }
                Button(
                    onClick = { gpuCount++ },
                    variant = ButtonVariant.Outline,
                    size = ButtonSize.Icon,
                ) {
                    Text(text = "+", variant = TextVariant.P)
                }
            }
        }

        Text(
            text = "You can add more later.",
            variant = TextVariant.Muted,
        )
    }
}

// ─── Private: Option Card with Radio ────────────────────────

@Composable
private fun OptionCard(
    title: String,
    description: String,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val colors = RikkaTheme.colors
    val borderColor = if (selected) colors.primary else colors.border
    val alpha = if (enabled) 1f else 0.5f

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .border(
                    width = if (selected) 2.dp else 1.dp,
                    color = borderColor.copy(alpha = alpha),
                    shape = RikkaTheme.shapes.lg,
                ).clip(RikkaTheme.shapes.lg)
                .background(colors.card.copy(alpha = alpha))
                .then(
                    if (enabled) {
                        Modifier.clickable(onClick = onClick)
                    } else {
                        Modifier
                    },
                ).padding(RikkaTheme.spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement =
            Arrangement.spacedBy(
                RikkaTheme.spacing.md,
            ),
    ) {
        // Radio indicator
        Box(
            modifier =
                Modifier
                    .size(20.dp)
                    .border(
                        width = if (selected) 6.dp else 2.dp,
                        color =
                            if (selected) {
                                colors.primary.copy(alpha = alpha)
                            } else {
                                colors.border.copy(alpha = alpha)
                            },
                        shape = CircleShape,
                    ).clip(CircleShape)
                    .background(colors.background),
        )

        Column(
            verticalArrangement =
                Arrangement.spacedBy(
                    RikkaTheme.spacing.xs,
                ),
        ) {
            Text(
                text = title,
                variant = TextVariant.P,
            )
            Text(
                text = description,
                variant = TextVariant.Muted,
            )
        }
    }
}
