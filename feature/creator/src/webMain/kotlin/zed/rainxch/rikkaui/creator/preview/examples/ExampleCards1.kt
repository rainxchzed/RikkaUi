package zed.rainxch.rikkaui.creator.preview.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.creator.generated.resources.*
import rikkaui.feature.creator.generated.resources.Res
import zed.rainxch.rikkaui.components.ui.alert.Alert
import zed.rainxch.rikkaui.components.ui.alert.AlertDescription
import zed.rainxch.rikkaui.components.ui.alert.AlertVariant
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.button.IconButton
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.checkbox.Checkbox
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.input.Input
import zed.rainxch.rikkaui.components.ui.radio.RadioButton
import zed.rainxch.rikkaui.components.ui.select.Select
import zed.rainxch.rikkaui.components.ui.select.SelectOption
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.textarea.Textarea
import zed.rainxch.rikkaui.components.ui.toggle.Toggle
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── 1. Environment Variables ──────────────────────────────

@Composable
fun ExampleEnvVariables(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(RikkaTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Text(text = stringResource(Res.string.example_env_title), variant = TextVariant.H4)
            Text(
                text = stringResource(Res.string.example_env_subtitle),
                variant = TextVariant.Muted,
            )

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.xs))

            EnvRow(
                key = stringResource(Res.string.example_env_key_database),
                value = stringResource(Res.string.example_env_value_masked),
            )
            EnvRow(
                key = stringResource(Res.string.example_env_key_api),
                value = stringResource(Res.string.example_env_value_api),
            )
            EnvRow(
                key = stringResource(Res.string.example_env_key_stripe),
                value = stringResource(Res.string.example_env_value_masked),
            )

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.xs))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                        Alignment.End,
                    ),
            ) {
                Button(
                    text = stringResource(Res.string.example_env_button_edit),
                    onClick = { },
                    variant = ButtonVariant.Outline,
                )
                Button(
                    text = stringResource(Res.string.example_env_button_deploy),
                    onClick = { },
                    variant = ButtonVariant.Default,
                )
            }
        }
    }
}

@Composable
private fun EnvRow(
    key: String,
    value: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = key,
            variant = TextVariant.Small,
            color = RikkaTheme.colors.onBackground,
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = value,
            variant = TextVariant.Muted,
            maxLines = 1,
        )
    }
}

// ─── 2. Book Appointment ───────────────────────────────────

@Composable
fun ExampleBookAppointment(modifier: Modifier = Modifier) {
    val timeSlot1030 = stringResource(Res.string.example_appt_time_1030)
    var selectedSlot by remember { mutableStateOf(timeSlot1030) }
    val timeSlots =
        listOf(
            stringResource(Res.string.example_appt_time_900),
            timeSlot1030,
            stringResource(Res.string.example_appt_time_1100),
            stringResource(Res.string.example_appt_time_130),
        )

    Card(modifier = modifier) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(RikkaTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Text(text = stringResource(Res.string.example_appt_title), variant = TextVariant.H4)
            Text(
                text = stringResource(Res.string.example_appt_doctor),
                variant = TextVariant.Muted,
            )
            Text(
                text = stringResource(Res.string.example_appt_date),
                variant = TextVariant.Small,
            )

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.xs))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
            ) {
                timeSlots.forEach { slot ->
                    Button(
                        text = slot,
                        onClick = { selectedSlot = slot },
                        variant =
                            if (slot == selectedSlot) {
                                ButtonVariant.Default
                            } else {
                                ButtonVariant.Outline
                            },
                        size = ButtonSize.Sm,
                    )
                }
            }

            Alert(variant = AlertVariant.Default) {
                AlertDescription(
                    stringResource(Res.string.example_appt_alert),
                )
            }

            Button(
                text = stringResource(Res.string.example_appt_button),
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

// ─── 3. Invite Team ────────────────────────────────────────

@Composable
fun ExampleInviteTeam(modifier: Modifier = Modifier) {
    var role1 by remember { mutableStateOf("editor") }
    var role2 by remember { mutableStateOf("viewer") }

    val roleOptions =
        listOf(
            SelectOption("admin", stringResource(Res.string.example_invite_role_admin)),
            SelectOption("editor", stringResource(Res.string.example_invite_role_editor)),
            SelectOption("viewer", stringResource(Res.string.example_invite_role_viewer)),
        )

    Card(modifier = modifier) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(RikkaTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Text(text = stringResource(Res.string.example_invite_title), variant = TextVariant.H4)
            Text(
                text = stringResource(Res.string.example_invite_description),
                variant = TextVariant.Muted,
            )

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.xs))

            InviteRow(
                email = stringResource(Res.string.example_invite_email1),
                selectedRole = role1,
                onRoleChange = { role1 = it },
                options = roleOptions,
            )
            InviteRow(
                email = stringResource(Res.string.example_invite_email2),
                selectedRole = role2,
                onRoleChange = { role2 = it },
                options = roleOptions,
            )

            Button(
                text = stringResource(Res.string.example_invite_add_another),
                onClick = { },
                variant = ButtonVariant.Ghost,
                size = ButtonSize.Sm,
            )

            Separator()

            Text(
                text = stringResource(Res.string.example_invite_share_link),
                variant = TextVariant.Small,
                color = RikkaTheme.colors.onMuted,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Input(
                    value = stringResource(Res.string.example_invite_link_value),
                    onValueChange = { },
                    readOnly = true,
                    modifier = Modifier.weight(1f),
                    label = stringResource(Res.string.example_invite_link_label),
                )
                IconButton(
                    icon = RikkaIcons.Copy,
                    contentDescription = stringResource(Res.string.example_invite_copy_link),
                    onClick = { },
                    variant = ButtonVariant.Outline,
                )
            }

            Button(
                text = stringResource(Res.string.example_invite_send_button),
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun InviteRow(
    email: String,
    selectedRole: String,
    onRoleChange: (String) -> Unit,
    options: List<SelectOption>,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.spacedBy(
                RikkaTheme.spacing.sm,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Input(
            value = email,
            onValueChange = { },
            readOnly = true,
            modifier = Modifier.weight(1f),
            label = stringResource(Res.string.example_invite_email_label),
        )
        Select(
            selectedValue = selectedRole,
            onValueChange = onRoleChange,
            options = options,
            modifier = Modifier.width(120.dp),
            label = stringResource(Res.string.example_invite_role_label),
        )
    }
}

// ─── 4. Report Bug ─────────────────────────────────────────

@Composable
fun ExampleReportBug(modifier: Modifier = Modifier) {
    var title by remember { mutableStateOf("") }
    var severity by remember { mutableStateOf("medium") }
    var component by remember { mutableStateOf("dashboard") }
    var steps by remember { mutableStateOf("") }

    val severityOptions =
        listOf(
            SelectOption("low", stringResource(Res.string.example_bug_severity_low)),
            SelectOption("medium", stringResource(Res.string.example_bug_severity_medium)),
            SelectOption("high", stringResource(Res.string.example_bug_severity_high)),
            SelectOption("critical", stringResource(Res.string.example_bug_severity_critical)),
        )
    val componentOptions =
        listOf(
            SelectOption("dashboard", stringResource(Res.string.example_bug_component_dashboard)),
            SelectOption("auth", stringResource(Res.string.example_bug_component_auth)),
            SelectOption("api", stringResource(Res.string.example_bug_component_api)),
            SelectOption("ui", stringResource(Res.string.example_bug_component_ui)),
        )

    Card(modifier = modifier) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(RikkaTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Text(text = stringResource(Res.string.example_bug_title), variant = TextVariant.H4)
            Text(
                text = stringResource(Res.string.example_bug_description),
                variant = TextVariant.Muted,
            )

            Input(
                value = title,
                onValueChange = { title = it },
                placeholder = stringResource(Res.string.example_bug_title_placeholder),
                label = stringResource(Res.string.example_bug_title_label),
                modifier = Modifier.fillMaxWidth(),
            )

            Select(
                selectedValue = severity,
                onValueChange = { severity = it },
                options = severityOptions,
                placeholder = stringResource(Res.string.example_bug_severity_label),
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(Res.string.example_bug_severity_label),
            )
            Select(
                selectedValue = component,
                onValueChange = { component = it },
                options = componentOptions,
                placeholder = stringResource(Res.string.example_bug_component_label),
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(Res.string.example_bug_component_label),
            )

            Textarea(
                value = steps,
                onValueChange = { steps = it },
                placeholder = stringResource(Res.string.example_bug_steps_placeholder),
                label = stringResource(Res.string.example_bug_steps_label),
                modifier = Modifier.fillMaxWidth(),
            )

            Button(
                text = stringResource(Res.string.example_bug_submit),
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

// ─── 5. Profile Settings ───────────────────────────────────

@Composable
fun ExampleProfileSettings(modifier: Modifier = Modifier) {
    val defaultName = stringResource(Res.string.example_profile_name_default)
    val defaultEmail = stringResource(Res.string.example_profile_email_default)
    var name by remember { mutableStateOf(defaultName) }
    var email by remember { mutableStateOf(defaultEmail) }
    var isPrivate by remember { mutableStateOf(false) }

    Card(modifier = modifier) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(RikkaTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Text(text = stringResource(Res.string.example_profile_title), variant = TextVariant.H4)
            Text(
                text = stringResource(Res.string.example_profile_description),
                variant = TextVariant.Muted,
            )

            Input(
                value = name,
                onValueChange = { name = it },
                placeholder = stringResource(Res.string.example_profile_name_placeholder),
                label = stringResource(Res.string.example_profile_name_label),
                modifier = Modifier.fillMaxWidth(),
            )

            Input(
                value = email,
                onValueChange = { email = it },
                placeholder = stringResource(Res.string.example_profile_email_placeholder),
                label = stringResource(Res.string.example_profile_email_label),
                modifier = Modifier.fillMaxWidth(),
            )

            Column(
                verticalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.xs,
                    ),
            ) {
                Checkbox(
                    checked = isPrivate,
                    onCheckedChange = { isPrivate = it },
                    label = stringResource(Res.string.example_profile_private_label),
                )
                Text(
                    text = stringResource(Res.string.example_profile_private_description),
                    variant = TextVariant.Muted,
                )
            }

            Button(
                text = stringResource(Res.string.example_profile_save),
                onClick = { },
            )
        }
    }
}

// ─── 6. Feedback Form ──────────────────────────────────────

@Composable
fun ExampleFeedbackForm(modifier: Modifier = Modifier) {
    var feedback by remember { mutableStateOf("") }

    Card(modifier = modifier) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(RikkaTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Text(text = stringResource(Res.string.example_feedback_title), variant = TextVariant.H4)

            Textarea(
                value = feedback,
                onValueChange = { feedback = it },
                placeholder = stringResource(Res.string.example_feedback_placeholder),
                label = stringResource(Res.string.example_feedback_label),
                modifier = Modifier.fillMaxWidth(),
            )

            Button(
                text = stringResource(Res.string.example_feedback_submit),
                onClick = { },
            )
        }
    }
}

// ─── 7. Notification Settings ──────────────────────────────

@Composable
fun ExampleNotificationSettings(modifier: Modifier = Modifier) {
    var pushEnabled by remember { mutableStateOf(true) }
    var emailDigest by remember { mutableStateOf(true) }
    var marketing by remember { mutableStateOf(false) }
    var quietHours by remember { mutableStateOf(false) }

    Card(modifier = modifier) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(RikkaTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Text(
                text = stringResource(Res.string.example_notif_title),
                variant = TextVariant.H4,
            )
            Text(
                text = stringResource(Res.string.example_notif_description),
                variant = TextVariant.Muted,
            )

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.xs))

            NotificationRow(
                title = stringResource(Res.string.example_notif_push),
                checked = pushEnabled,
                onCheckedChange = { pushEnabled = it },
            )
            NotificationRow(
                title = stringResource(Res.string.example_notif_email_digest),
                checked = emailDigest,
                onCheckedChange = { emailDigest = it },
            )
            NotificationRow(
                title = stringResource(Res.string.example_notif_marketing),
                checked = marketing,
                onCheckedChange = { marketing = it },
            )

            Separator()

            Text(
                text = stringResource(Res.string.example_notif_quiet_hours),
                variant = TextVariant.Large,
            )

            NotificationRow(
                title = stringResource(Res.string.example_notif_quiet_hours_time),
                checked = quietHours,
                onCheckedChange = { quietHours = it },
            )
        }
    }
}

@Composable
private fun NotificationRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = title, variant = TextVariant.P)
        Toggle(
            checked = checked,
            onCheckedChange = onCheckedChange,
            label = stringResource(Res.string.example_notif_toggle_suffix, title),
        )
    }
}

// ─── 8. Shipping Address ───────────────────────────────────

@Composable
fun ExampleShippingAddress(modifier: Modifier = Modifier) {
    var street by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var zip by remember { mutableStateOf("") }

    Card(modifier = modifier) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(RikkaTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Text(text = stringResource(Res.string.example_shipping_title), variant = TextVariant.H4)
            Text(
                text = stringResource(Res.string.example_shipping_description),
                variant = TextVariant.Muted,
            )

            Input(
                value = street,
                onValueChange = { street = it },
                placeholder = stringResource(Res.string.example_shipping_street_placeholder),
                label = stringResource(Res.string.example_shipping_street_label),
                modifier = Modifier.fillMaxWidth(),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(
                        RikkaTheme.spacing.sm,
                    ),
            ) {
                Input(
                    value = city,
                    onValueChange = { city = it },
                    placeholder = stringResource(Res.string.example_shipping_city_placeholder),
                    label = stringResource(Res.string.example_shipping_city_label),
                    modifier = Modifier.weight(1f),
                )
                Input(
                    value = state,
                    onValueChange = { state = it },
                    placeholder = stringResource(Res.string.example_shipping_state_placeholder),
                    label = stringResource(Res.string.example_shipping_state_label),
                    modifier = Modifier.weight(1f),
                )
            }

            Input(
                value = zip,
                onValueChange = { zip = it },
                placeholder = stringResource(Res.string.example_shipping_zip_placeholder),
                label = stringResource(Res.string.example_shipping_zip_label),
                modifier = Modifier.fillMaxWidth(),
            )

            Button(
                text = stringResource(Res.string.example_shipping_save),
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

// ─── 9. Cookie Settings ────────────────────────────────────

@Composable
fun ExampleCookieSettings(modifier: Modifier = Modifier) {
    var essential by remember { mutableStateOf(true) }
    var analytics by remember { mutableStateOf(false) }
    var marketingCookies by remember { mutableStateOf(false) }

    Card(modifier = modifier) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(RikkaTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Text(text = stringResource(Res.string.example_cookie_title), variant = TextVariant.H4)
            Text(
                text = stringResource(Res.string.example_cookie_description),
                variant = TextVariant.Muted,
            )

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.xs))

            CookieRow(
                title = stringResource(Res.string.example_cookie_essential),
                description = stringResource(Res.string.example_cookie_essential_desc),
                checked = essential,
                onCheckedChange = { essential = it },
            )
            CookieRow(
                title = stringResource(Res.string.example_cookie_analytics),
                description = stringResource(Res.string.example_cookie_analytics_desc),
                checked = analytics,
                onCheckedChange = { analytics = it },
            )
            CookieRow(
                title = stringResource(Res.string.example_cookie_marketing),
                description = stringResource(Res.string.example_cookie_marketing_desc),
                checked = marketingCookies,
                onCheckedChange = { marketingCookies = it },
            )

            Separator()

            Button(
                text = stringResource(Res.string.example_cookie_save),
                onClick = { },
            )
        }
    }
}

@Composable
private fun CookieRow(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement =
                Arrangement.spacedBy(
                    RikkaTheme.spacing.xs,
                ),
        ) {
            Text(text = title, variant = TextVariant.P)
            Text(text = description, variant = TextVariant.Muted)
        }
        Toggle(
            checked = checked,
            onCheckedChange = onCheckedChange,
            label = stringResource(Res.string.example_cookie_toggle_suffix, title),
        )
    }
}

// ─── 10. Payment Method ────────────────────────────────────

@Composable
fun ExamplePaymentMethod(modifier: Modifier = Modifier) {
    var selectedMethod by remember { mutableStateOf(0) }
    var cardNumber by remember { mutableStateOf("") }
    var expiry by remember { mutableStateOf("") }

    Card(modifier = modifier) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(RikkaTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Text(text = stringResource(Res.string.example_payment_title), variant = TextVariant.H4)
            Text(
                text = stringResource(Res.string.example_payment_description),
                variant = TextVariant.Muted,
            )

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.xs))

            RadioButton(
                selected = selectedMethod == 0,
                onClick = { selectedMethod = 0 },
                label = stringResource(Res.string.example_payment_credit_card),
            )
            RadioButton(
                selected = selectedMethod == 1,
                onClick = { selectedMethod = 1 },
                label = stringResource(Res.string.example_payment_paypal),
            )
            RadioButton(
                selected = selectedMethod == 2,
                onClick = { selectedMethod = 2 },
                label = stringResource(Res.string.example_payment_bank_transfer),
            )

            if (selectedMethod == 0) {
                Separator()

                Input(
                    value = cardNumber,
                    onValueChange = { cardNumber = it },
                    placeholder = stringResource(Res.string.example_payment_card_placeholder),
                    label = stringResource(Res.string.example_payment_card_label),
                    modifier = Modifier.fillMaxWidth(),
                )
                Input(
                    value = expiry,
                    onValueChange = { expiry = it },
                    placeholder = stringResource(Res.string.example_payment_expiry_placeholder),
                    label = stringResource(Res.string.example_payment_expiry_label),
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Button(
                text = stringResource(Res.string.example_payment_continue),
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
