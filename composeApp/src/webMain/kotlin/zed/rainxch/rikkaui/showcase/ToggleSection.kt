package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import zed.rainxch.rikkaui.components.ui.radio.RadioButton
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.toggle.Toggle
import zed.rainxch.rikkaui.components.ui.toggle.ToggleSize

@Composable
fun ToggleSection() {
    SectionHeader(
        title = "Toggle, Checkbox & Radio",
        description = "Boolean controls with spring animations.",
    )

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.lg)) {
            // ─── Toggle ─────────────────────────────────────
            Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm)) {
                Text(text = "Toggle", variant = TextVariant.Small)

                var defaultToggle by remember { mutableStateOf(true) }
                var smallToggle by remember { mutableStateOf(false) }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Toggle(
                        checked = defaultToggle,
                        onCheckedChange = { defaultToggle = it },
                        label = "Default toggle",
                    )
                    Text(
                        text = if (defaultToggle) "On" else "Off",
                        variant = TextVariant.Small,
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Toggle(
                        checked = smallToggle,
                        onCheckedChange = { smallToggle = it },
                        size = ToggleSize.Sm,
                        label = "Small toggle",
                    )
                    Text(
                        text = if (smallToggle) "On" else "Off",
                        variant = TextVariant.Small,
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Toggle(
                        checked = true,
                        onCheckedChange = {},
                        enabled = false,
                        label = "Disabled on",
                    )
                    Toggle(
                        checked = false,
                        onCheckedChange = {},
                        enabled = false,
                        label = "Disabled off",
                    )
                }
            }

            // ─── Checkbox ───────────────────────────────────
            Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm)) {
                Text(text = "Checkbox", variant = TextVariant.Small)

                var checkA by remember { mutableStateOf(true) }
                var checkB by remember { mutableStateOf(false) }

                Checkbox(
                    checked = checkA,
                    onCheckedChange = { checkA = it },
                    label = "Checked",
                )
                Checkbox(
                    checked = checkB,
                    onCheckedChange = { checkB = it },
                    label = "Unchecked",
                )
                Checkbox(
                    checked = true,
                    onCheckedChange = {},
                    enabled = false,
                    label = "Disabled checked",
                )
                Checkbox(
                    checked = false,
                    onCheckedChange = {},
                    enabled = false,
                    label = "Disabled unchecked",
                )
            }

            // ─── RadioButton ────────────────────────────────
            Column(verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm)) {
                Text(text = "RadioButton", variant = TextVariant.Small)

                var selectedRadio by remember { mutableStateOf(0) }

                RadioButton(
                    selected = selectedRadio == 0,
                    onClick = { selectedRadio = 0 },
                    label = "Option A",
                )
                RadioButton(
                    selected = selectedRadio == 1,
                    onClick = { selectedRadio = 1 },
                    label = "Option B",
                )
                RadioButton(
                    selected = selectedRadio == 2,
                    onClick = { selectedRadio = 2 },
                    label = "Option C",
                )
            }
        }
    }
}
