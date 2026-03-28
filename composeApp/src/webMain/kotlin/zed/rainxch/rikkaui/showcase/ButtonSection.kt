@file:OptIn(ExperimentalLayoutApi::class)

package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonAnimation
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

@Composable
fun ButtonSection() {
    SectionHeader(
        title = "Button",
        description = "Interactive button with 6 variants, 4 sizes, and spring-physics animations.",
    )

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    Card(modifier = Modifier.fillMaxWidth()) {
        // Variants
        Text(text = "Variants", variant = TextVariant.Small)
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        FlowRow(
            horizontalArrangement =
                androidx.compose.foundation.layout.Arrangement
                    .spacedBy(RikkaTheme.spacing.sm),
            verticalArrangement =
                androidx.compose.foundation.layout.Arrangement
                    .spacedBy(RikkaTheme.spacing.sm),
        ) {
            Button(text = "Default", onClick = {}, variant = ButtonVariant.Default)
            Button(text = "Outline", onClick = {}, variant = ButtonVariant.Outline)
            Button(text = "Secondary", onClick = {}, variant = ButtonVariant.Secondary)
            Button(text = "Ghost", onClick = {}, variant = ButtonVariant.Ghost)
            Button(text = "Destructive", onClick = {}, variant = ButtonVariant.Destructive)
            Button(text = "Link", onClick = {}, variant = ButtonVariant.Link)
        }

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        // Sizes
        Text(text = "Sizes", variant = TextVariant.Small)
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        FlowRow(
            horizontalArrangement =
                androidx.compose.foundation.layout.Arrangement
                    .spacedBy(RikkaTheme.spacing.sm),
            verticalArrangement =
                androidx.compose.foundation.layout.Arrangement
                    .spacedBy(RikkaTheme.spacing.sm),
        ) {
            Button(text = "Large", onClick = {}, size = ButtonSize.Lg)
            Button(text = "Default", onClick = {}, size = ButtonSize.Default)
            Button(text = "Small", onClick = {}, size = ButtonSize.Sm)
            Button(onClick = {}, size = ButtonSize.Icon) {
                Text(text = "+", color = RikkaTheme.colors.primaryForeground)
            }
        }

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        // Animations
        Text(text = "Animations", variant = TextVariant.Small)
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        FlowRow(
            horizontalArrangement =
                androidx.compose.foundation.layout.Arrangement
                    .spacedBy(RikkaTheme.spacing.sm),
            verticalArrangement =
                androidx.compose.foundation.layout.Arrangement
                    .spacedBy(RikkaTheme.spacing.sm),
        ) {
            Button(text = "None", onClick = {}, animation = ButtonAnimation.None)
            Button(text = "Scale", onClick = {}, animation = ButtonAnimation.Scale)
            Button(text = "Bounce", onClick = {}, animation = ButtonAnimation.Bounce)
        }

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        // States
        Text(text = "States", variant = TextVariant.Small)
        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        FlowRow(
            horizontalArrangement =
                androidx.compose.foundation.layout.Arrangement
                    .spacedBy(RikkaTheme.spacing.sm),
            verticalArrangement =
                androidx.compose.foundation.layout.Arrangement
                    .spacedBy(RikkaTheme.spacing.sm),
        ) {
            Button(text = "Enabled", onClick = {}, enabled = true)
            Button(text = "Disabled", onClick = {}, enabled = false)
        }
    }
}
