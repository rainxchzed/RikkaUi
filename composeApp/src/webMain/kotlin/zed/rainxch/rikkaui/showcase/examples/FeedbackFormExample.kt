package zed.rainxch.rikkaui.showcase.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.card.CardContent
import zed.rainxch.rikkaui.components.ui.card.CardHeader
import zed.rainxch.rikkaui.components.ui.checkbox.Checkbox
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.label.Label
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.textarea.Textarea

/**
 * Feedback form with star rating, free-text area, and opt-in checkbox.
 *
 * Demonstrates [Card], [CardHeader], [CardContent], [Button] (icon variant
 * for stars), [Icon], [Label], [Textarea], [Checkbox], and [Text]
 * composed together in a feedback collection layout.
 */
@Composable
fun FeedbackFormExample() {
    var rating by remember { mutableStateOf(0) }
    var feedbackText by remember { mutableStateOf("") }
    var contactMe by remember { mutableStateOf(false) }

    Card {
        // ─── Header ───────────────────────────────────────
        CardHeader {
            Text(
                text = "Rate Your Experience",
                variant = TextVariant.H4,
            )
        }

        // ─── Star rating row ──────────────────────────────
        CardContent {
            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.xs,
                    ),
            ) {
                for (star in 1..5) {
                    Button(
                        onClick = { rating = star },
                        variant = ButtonVariant.Ghost,
                        size = ButtonSize.Icon,
                    ) {
                        Icon(
                            imageVector = RikkaIcons.Star,
                            contentDescription = "Rate $star stars",
                            tint =
                                if (star <= rating) {
                                    RikkaTheme.colors.primary
                                } else {
                                    RikkaTheme.colors.mutedForeground
                                },
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

            // ─── Feedback textarea ────────────────────────
            Label(text = "Tell us more")
            Spacer(modifier = Modifier.height(RikkaTheme.spacing.xs))
            Textarea(
                value = feedbackText,
                onValueChange = { feedbackText = it },
                placeholder = "What could we improve?",
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

            // ─── Contact opt-in ───────────────────────────
            Checkbox(
                checked = contactMe,
                onCheckedChange = { contactMe = it },
                label = "Contact me about this feedback",
            )

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.md))

            // ─── Submit button ────────────────────────────
            Button(
                text = "Submit Feedback",
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
