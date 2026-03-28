package zed.rainxch.rikkaui.showcase.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.card.CardContent
import zed.rainxch.rikkaui.components.ui.card.CardFooter
import zed.rainxch.rikkaui.components.ui.card.CardHeader
import zed.rainxch.rikkaui.components.ui.checkbox.Checkbox
import zed.rainxch.rikkaui.components.ui.input.Input
import zed.rainxch.rikkaui.components.ui.label.Label
import zed.rainxch.rikkaui.components.ui.select.Select
import zed.rainxch.rikkaui.components.ui.select.SelectOption
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.textarea.Textarea

/**
 * A realistic payment method form card demonstrating Card, Input, Select,
 * Checkbox, Textarea, Separator, Label, and Button components together.
 *
 * Inspired by shadcn/ui's Payment Method homepage example.
 */
@Composable
fun PaymentFormExample() {
    var cardName by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var selectedMonth by remember { mutableStateOf("") }
    var selectedYear by remember { mutableStateOf("") }
    var sameAsShipping by remember { mutableStateOf(true) }
    var comments by remember { mutableStateOf("") }

    val monthOptions =
        listOf(
            SelectOption("01", "January"),
            SelectOption("02", "February"),
            SelectOption("03", "March"),
            SelectOption("04", "April"),
            SelectOption("05", "May"),
            SelectOption("06", "June"),
            SelectOption("07", "July"),
            SelectOption("08", "August"),
            SelectOption("09", "September"),
            SelectOption("10", "October"),
            SelectOption("11", "November"),
            SelectOption("12", "December"),
        )

    val yearOptions =
        (2024..2028).map { year ->
            SelectOption(year.toString(), year.toString())
        }

    Card(
        label = "Payment method form",
    ) {
        // ─── Header ──────────────────────────────────────
        CardHeader {
            Text(
                text = "Payment Method",
                variant = TextVariant.H4,
            )
            Text(
                text = "All transactions are secure and encrypted.",
                variant = TextVariant.Muted,
            )
        }

        // ─── Content ─────────────────────────────────────
        CardContent {
            Column(
                verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
            ) {
                // Name on Card
                Column(
                    verticalArrangement =
                        Arrangement.spacedBy(
                            RikkaTheme.spacing.xs,
                        ),
                ) {
                    Label("Name on Card")
                    Input(
                        value = cardName,
                        onValueChange = { cardName = it },
                        placeholder = "John Doe",
                        label = "Name on card",
                    )
                }

                // Card Number + CVV
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(
                            RikkaTheme.spacing.md,
                        ),
                ) {
                    Column(
                        modifier = Modifier.weight(2f),
                        verticalArrangement =
                            Arrangement.spacedBy(
                                RikkaTheme.spacing.xs,
                            ),
                    ) {
                        Label("Card Number")
                        Input(
                            value = cardNumber,
                            onValueChange = { cardNumber = it },
                            placeholder = "1234 5678 9012 3456",
                            label = "Card number",
                        )
                    }
                    Column(
                        modifier = Modifier.weight(0.5f),
                        verticalArrangement =
                            Arrangement.spacedBy(
                                RikkaTheme.spacing.xs,
                            ),
                    ) {
                        Label("CVV")
                        Input(
                            value = cvv,
                            onValueChange = { cvv = it },
                            placeholder = "123",
                            label = "CVV",
                        )
                    }
                }

                Text(
                    text = "Enter your 16-digit card number.",
                    variant = TextVariant.Muted,
                )

                // Month + Year
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(
                            RikkaTheme.spacing.md,
                        ),
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement =
                            Arrangement.spacedBy(
                                RikkaTheme.spacing.xs,
                            ),
                    ) {
                        Label("Month")
                        Select(
                            selectedValue = selectedMonth,
                            onValueChange = { selectedMonth = it },
                            options = monthOptions,
                            placeholder = "Month...",
                            label = "Expiration month",
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement =
                            Arrangement.spacedBy(
                                RikkaTheme.spacing.xs,
                            ),
                    ) {
                        Label("Year")
                        Select(
                            selectedValue = selectedYear,
                            onValueChange = { selectedYear = it },
                            options = yearOptions,
                            placeholder = "Year...",
                            label = "Expiration year",
                        )
                    }
                }

                Separator()

                // Billing Address
                Column(
                    verticalArrangement =
                        Arrangement.spacedBy(
                            RikkaTheme.spacing.xs,
                        ),
                ) {
                    Label("Billing Address")
                    Text(
                        text = "Select the address that matches your card.",
                        variant = TextVariant.Muted,
                    )
                }

                Checkbox(
                    checked = sameAsShipping,
                    onCheckedChange = { sameAsShipping = it },
                    label = "Same as shipping address",
                )

                Separator()

                // Comments
                Column(
                    verticalArrangement =
                        Arrangement.spacedBy(
                            RikkaTheme.spacing.xs,
                        ),
                ) {
                    Label("Comments")
                    Textarea(
                        value = comments,
                        onValueChange = { comments = it },
                        placeholder = "Add any additional comments...",
                        label = "Additional comments",
                        minLines = 3,
                        maxLines = 5,
                    )
                }
            }
        }

        // ─── Footer ─────────────────────────────────────
        CardFooter {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
            ) {
                Button(
                    text = "Submit",
                    onClick = { /* handle submit */ },
                )
                Button(
                    text = "Cancel",
                    onClick = { /* handle cancel */ },
                    variant = ButtonVariant.Outline,
                )
            }
        }
    }
}
