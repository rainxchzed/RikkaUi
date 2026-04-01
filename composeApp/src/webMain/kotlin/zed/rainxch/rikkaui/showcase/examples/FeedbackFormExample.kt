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
import org.jetbrains.compose.resources.stringResource
import rikkaui.composeapp.generated.resources.Res
import rikkaui.composeapp.generated.resources.contact_me_about_feedback
import rikkaui.composeapp.generated.resources.rate_stars
import rikkaui.composeapp.generated.resources.rate_your_experience
import rikkaui.composeapp.generated.resources.submit_feedback
import rikkaui.composeapp.generated.resources.tell_us_more
import rikkaui.composeapp.generated.resources.what_could_we_improve
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
import zed.rainxch.rikkaui.foundation.RikkaTheme

@Composable
fun FeedbackFormExample() {
    var rating by remember { mutableStateOf(0) }
    var feedbackText by remember { mutableStateOf("") }
    var contactMe by remember { mutableStateOf(false) }

    Card {
        CardHeader {
            Text(text = stringResource(Res.string.rate_your_experience), variant = TextVariant.H4)
        }

        CardContent {
            Row(horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xs)) {
                for (star in 1..5) {
                    Button(
                        onClick = { rating = star },
                        variant = ButtonVariant.Ghost,
                        size = ButtonSize.Icon,
                    ) {
                        Icon(
                            imageVector = RikkaIcons.Star,
                            contentDescription = stringResource(Res.string.rate_stars, star),
                            tint =
                                if (star <= rating) {
                                    RikkaTheme.colors.primary
                                } else {
                                    RikkaTheme.colors.onMuted
                                },
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

            Label(text = stringResource(Res.string.tell_us_more))
            Spacer(modifier = Modifier.height(RikkaTheme.spacing.xs))
            Textarea(
                value = feedbackText,
                onValueChange = { feedbackText = it },
                placeholder = stringResource(Res.string.what_could_we_improve),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

            Checkbox(
                checked = contactMe,
                onCheckedChange = { contactMe = it },
                label = stringResource(Res.string.contact_me_about_feedback),
            )

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.md))

            Button(
                text = stringResource(Res.string.submit_feedback),
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
