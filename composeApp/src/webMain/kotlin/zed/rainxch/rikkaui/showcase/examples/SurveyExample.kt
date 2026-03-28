package zed.rainxch.rikkaui.showcase.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.checkbox.Checkbox
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.togglegroup.ToggleGroup
import zed.rainxch.rikkaui.components.ui.togglegroup.ToggleGroupItem
import zed.rainxch.rikkaui.components.ui.togglegroup.ToggleGroupVariant

/**
 * Survey card example with multi-select toggle options and terms checkbox.
 *
 * Demonstrates [Card], [ToggleGroup], [ToggleGroupItem], [Checkbox],
 * [Separator], and [Text] composed together in a realistic survey form.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SurveyExample() {
    var socialMedia by remember { mutableStateOf(true) }
    var searchEngine by remember { mutableStateOf(true) }
    var referral by remember { mutableStateOf(false) }
    var other by remember { mutableStateOf(false) }
    var termsAccepted by remember { mutableStateOf(false) }

    Card {
        Text(
            text = "How did you hear about us?",
            variant = TextVariant.H4,
        )
        Text(
            text = "Select the option that best describes how you found us.",
            variant = TextVariant.Muted,
        )

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        FlowRow(
            horizontalArrangement =
                Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
            verticalArrangement =
                Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
        ) {
            ToggleGroupItem(
                text = "Social Media",
                selected = socialMedia,
                onClick = { socialMedia = !socialMedia },
                variant = ToggleGroupVariant.Outline,
            )
            ToggleGroupItem(
                text = "Search Engine",
                selected = searchEngine,
                onClick = { searchEngine = !searchEngine },
                variant = ToggleGroupVariant.Outline,
            )
            ToggleGroupItem(
                text = "Referral",
                selected = referral,
                onClick = { referral = !referral },
                variant = ToggleGroupVariant.Outline,
            )
            ToggleGroupItem(
                text = "Other",
                selected = other,
                onClick = { other = !other },
                variant = ToggleGroupVariant.Outline,
            )
        }

        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))
        Separator()
        Spacer(modifier = Modifier.height(RikkaTheme.spacing.sm))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = termsAccepted,
                onCheckedChange = { termsAccepted = it },
                label = "I agree to the terms and conditions",
            )
        }
    }
}
