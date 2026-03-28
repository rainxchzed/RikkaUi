package zed.rainxch.rikkaui.showcase.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

/**
 * Authentication and verification settings card.
 *
 * Demonstrates a settings-panel layout with [Card], [Button],
 * [Text], and [Separator] — inspired by shadcn's settings panels.
 */
@Composable
fun AuthSettingsExample() {
    Card {
        // ─── Two-factor row ─────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.xs,
                    ),
            ) {
                Text(
                    text = "Two-factor authentication",
                    variant = TextVariant.P,
                )
                Text(
                    text = "Verify via email or phone number.",
                    variant = TextVariant.Muted,
                )
            }
            Button(
                text = "Enable",
                onClick = { },
                variant = ButtonVariant.Outline,
                size = ButtonSize.Sm,
            )
        }

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))
        Separator()
        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        // ─── Verified status row ────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement =
                Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
        ) {
            Text(
                text = "\u2713",
                color = RikkaTheme.colors.primary,
                variant = TextVariant.P,
            )
            Text(
                text = "Your profile has been verified.",
                variant = TextVariant.P,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = ">",
                color = RikkaTheme.colors.mutedForeground,
                variant = TextVariant.P,
            )
        }

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))
        Separator()
        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        // ─── Appearance footer ──────────────────────────
        Text(
            text = "Appearance Settings",
            variant = TextVariant.Muted,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
    }
}
