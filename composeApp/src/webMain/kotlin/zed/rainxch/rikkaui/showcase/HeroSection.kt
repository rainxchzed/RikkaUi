@file:OptIn(ExperimentalLayoutApi::class)

package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonAnimation
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

@Composable
fun HeroSection() {
    Spacer(Modifier.height(RikkaTheme.spacing.xxxl))

    Badge(
        text = "Preview",
        variant = BadgeVariant.Outline,
    )

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    Text(
        text = "RikkaUI",
        variant = TextVariant.H1,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )

    Spacer(Modifier.height(RikkaTheme.spacing.sm))

    Text(
        text = "Beautiful components for Compose Multiplatform",
        variant = TextVariant.Lead,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )

    Spacer(Modifier.height(RikkaTheme.spacing.sm))

    Text(
        text =
            "A shadcn-inspired component library for Kotlin. " +
                "Copy-paste components you own, spring-physics animations, " +
                "and zero Material dependency.",
        variant = TextVariant.Muted,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )

    Spacer(Modifier.height(RikkaTheme.spacing.xl))

    FlowRow(
        horizontalArrangement =
            Arrangement.spacedBy(
                RikkaTheme.spacing.sm,
                Alignment.CenterHorizontally,
            ),
        verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Button(
            text = "Get Started",
            onClick = {},
            animation = ButtonAnimation.Bounce,
        )
        Button(
            text = "GitHub",
            onClick = {},
            variant = ButtonVariant.Outline,
        )
    }

    Spacer(Modifier.height(RikkaTheme.spacing.xxxl))
}
