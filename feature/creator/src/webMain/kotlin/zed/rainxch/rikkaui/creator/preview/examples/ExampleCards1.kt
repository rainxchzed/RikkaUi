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
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.alert.Alert
import zed.rainxch.rikkaui.components.ui.alert.AlertDescription
import zed.rainxch.rikkaui.components.ui.alert.AlertVariant
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.checkbox.Checkbox
import zed.rainxch.rikkaui.components.ui.icon.Icon
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

// ─── 1. Environment Variables ──────────────────────────────

@Composable
fun ExampleEnvVariables(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(RikkaTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Text(text = "Environment Variables", variant = TextVariant.H4)
            Text(
                text = "Production \u00B7 8 variables",
                variant = TextVariant.Muted,
            )

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.xs))

            EnvRow(key = "DATABASE_URL", value = "\u2022\u2022\u2022\u2022\u2022\u2022\u2022\u2022")
            EnvRow(
                key = "NEXT_PUBLIC_API",
                value = "https://api.example.com/v1",
            )
            EnvRow(key = "STRIPE_SECRET", value = "\u2022\u2022\u2022\u2022\u2022\u2022\u2022\u2022")

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.xs))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                    Alignment.End,
                ),
            ) {
                Button(
                    text = "Edit",
                    onClick = { },
                    variant = ButtonVariant.Outline,
                )
                Button(
                    text = "Deploy",
                    onClick = { },
                    variant = ButtonVariant.Default,
                )
            }
        }
    }
}

@Composable
private fun EnvRow(key: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.spacedBy(RikkaTheme.spacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = key,
            variant = TextVariant.Small,
            color = RikkaTheme.colors.foreground,
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
    var selectedSlot by remember { mutableStateOf("10:30 AM") }
    val timeSlots = listOf("9:00 AM", "10:30 AM", "11:00 AM", "1:30 PM")

    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(RikkaTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Text(text = "Book Appointment", variant = TextVariant.H4)
            Text(
                text = "Dr. Sarah Chen \u00B7 Cardiology",
                variant = TextVariant.Muted,
            )
            Text(
                text = "Available on March 28, 2026",
                variant = TextVariant.Small,
            )

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.xs))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
            ) {
                timeSlots.forEach { slot ->
                    Button(
                        text = slot,
                        onClick = { selectedSlot = slot },
                        variant = if (slot == selectedSlot) {
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
                    "New patient? Please arrive 15 minutes early.",
                )
            }

            Button(
                text = "Book Appointment",
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

    val roleOptions = listOf(
        SelectOption("admin", "Admin"),
        SelectOption("editor", "Editor"),
        SelectOption("viewer", "Viewer"),
    )

    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(RikkaTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Text(text = "Invite Team", variant = TextVariant.H4)
            Text(
                text = "Add members to your workspace",
                variant = TextVariant.Muted,
            )

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.xs))

            InviteRow(
                email = "alex@example.com",
                selectedRole = role1,
                onRoleChange = { role1 = it },
                options = roleOptions,
            )
            InviteRow(
                email = "sam@example.com",
                selectedRole = role2,
                onRoleChange = { role2 = it },
                options = roleOptions,
            )

            Button(
                text = "+ Add another",
                onClick = { },
                variant = ButtonVariant.Ghost,
                size = ButtonSize.Sm,
            )

            Separator()

            Text(
                text = "Or share invite link",
                variant = TextVariant.Small,
                color = RikkaTheme.colors.mutedForeground,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Input(
                    value = "https://app.co/invite/x8f2k",
                    onValueChange = { },
                    readOnly = true,
                    modifier = Modifier.weight(1f),
                    label = "Invite link",
                )
                Button(
                    onClick = { },
                    variant = ButtonVariant.Outline,
                    size = ButtonSize.Icon,
                    label = "Copy invite link",
                ) {
                    Icon(
                        imageVector = RikkaIcons.Copy,
                        contentDescription = "Copy",
                    )
                }
            }

            Button(
                text = "Send Invites",
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
        horizontalArrangement = Arrangement.spacedBy(
            RikkaTheme.spacing.sm,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Input(
            value = email,
            onValueChange = { },
            readOnly = true,
            modifier = Modifier.weight(1f),
            label = "Email address",
        )
        Select(
            selectedValue = selectedRole,
            onValueChange = onRoleChange,
            options = options,
            modifier = Modifier.width(120.dp),
            label = "Role",
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

    val severityOptions = listOf(
        SelectOption("low", "Low"),
        SelectOption("medium", "Medium"),
        SelectOption("high", "High"),
        SelectOption("critical", "Critical"),
    )
    val componentOptions = listOf(
        SelectOption("dashboard", "Dashboard"),
        SelectOption("auth", "Authentication"),
        SelectOption("api", "API"),
        SelectOption("ui", "UI Components"),
    )

    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(RikkaTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Text(text = "Report Bug", variant = TextVariant.H4)
            Text(
                text = "Help us fix issues faster.",
                variant = TextVariant.Muted,
            )

            Input(
                value = title,
                onValueChange = { title = it },
                placeholder = "Brief description of the issue",
                label = "Title",
                modifier = Modifier.fillMaxWidth(),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
            ) {
                Select(
                    selectedValue = severity,
                    onValueChange = { severity = it },
                    options = severityOptions,
                    placeholder = "Severity",
                    modifier = Modifier.weight(1f),
                    label = "Severity",
                )
                Select(
                    selectedValue = component,
                    onValueChange = { component = it },
                    options = componentOptions,
                    placeholder = "Component",
                    modifier = Modifier.weight(1f),
                    label = "Component",
                )
            }

            Textarea(
                value = steps,
                onValueChange = { steps = it },
                placeholder = "1. Go to\n2. Click on\n3. Observe...",
                label = "Steps to reproduce",
                modifier = Modifier.fillMaxWidth(),
            )

            Button(
                text = "Submit Bug Report",
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

// ─── 5. Profile Settings ───────────────────────────────────

@Composable
fun ExampleProfileSettings(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("shadcn") }
    var email by remember { mutableStateOf("m@example.com") }
    var isPrivate by remember { mutableStateOf(false) }

    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(RikkaTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Text(text = "Profile", variant = TextVariant.H4)
            Text(
                text = "Manage your profile information.",
                variant = TextVariant.Muted,
            )

            Input(
                value = name,
                onValueChange = { name = it },
                placeholder = "Your name",
                label = "Name",
                modifier = Modifier.fillMaxWidth(),
            )

            Input(
                value = email,
                onValueChange = { email = it },
                placeholder = "Email address",
                label = "Email",
                modifier = Modifier.fillMaxWidth(),
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.xs,
                ),
            ) {
                Checkbox(
                    checked = isPrivate,
                    onCheckedChange = { isPrivate = it },
                    label = "Make profile private and hide activity",
                )
                Text(
                    text = "When enabled, your profile and activity "
                        + "will be hidden from other users.",
                    variant = TextVariant.Muted,
                )
            }

            Button(
                text = "Save Changes",
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(RikkaTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Text(text = "Feedback", variant = TextVariant.H4)

            Textarea(
                value = feedback,
                onValueChange = { feedback = it },
                placeholder = "Your feedback helps us improve...",
                label = "Feedback",
                modifier = Modifier.fillMaxWidth(),
            )

            Button(
                text = "Submit",
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(RikkaTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Text(
                text = "Notification Settings",
                variant = TextVariant.H4,
            )
            Text(
                text = "Manage how you receive notifications.",
                variant = TextVariant.Muted,
            )

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.xs))

            NotificationRow(
                title = "Push notifications",
                checked = pushEnabled,
                onCheckedChange = { pushEnabled = it },
            )
            NotificationRow(
                title = "Email digest",
                checked = emailDigest,
                onCheckedChange = { emailDigest = it },
            )
            NotificationRow(
                title = "Marketing emails",
                checked = marketing,
                onCheckedChange = { marketing = it },
            )

            Separator()

            Text(
                text = "Quiet hours",
                variant = TextVariant.Large,
            )

            NotificationRow(
                title = "From 10:00 PM to 7:00 AM",
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
            label = "$title toggle",
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(RikkaTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Text(text = "Shipping Address", variant = TextVariant.H4)
            Text(
                text = "Where should we deliver?",
                variant = TextVariant.Muted,
            )

            Input(
                value = street,
                onValueChange = { street = it },
                placeholder = "123 Main Street",
                label = "Street address",
                modifier = Modifier.fillMaxWidth(),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    RikkaTheme.spacing.sm,
                ),
            ) {
                Input(
                    value = city,
                    onValueChange = { city = it },
                    placeholder = "City",
                    label = "City",
                    modifier = Modifier.weight(1f),
                )
                Input(
                    value = state,
                    onValueChange = { state = it },
                    placeholder = "State",
                    label = "State",
                    modifier = Modifier.weight(1f),
                )
            }

            Input(
                value = zip,
                onValueChange = { zip = it },
                placeholder = "ZIP code",
                label = "ZIP code",
                modifier = Modifier.fillMaxWidth(),
            )

            Button(
                text = "Save Address",
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(RikkaTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Text(text = "Cookie Settings", variant = TextVariant.H4)
            Text(
                text = "Manage your cookie preferences.",
                variant = TextVariant.Muted,
            )

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.xs))

            CookieRow(
                title = "Essential",
                description = "Required for the website to function",
                checked = essential,
                onCheckedChange = { essential = it },
            )
            CookieRow(
                title = "Analytics",
                description = "Help us understand usage patterns",
                checked = analytics,
                onCheckedChange = { analytics = it },
            )
            CookieRow(
                title = "Marketing",
                description = "Personalized ads and recommendations",
                checked = marketingCookies,
                onCheckedChange = { marketingCookies = it },
            )

            Separator()

            Button(
                text = "Save Preferences",
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
            verticalArrangement = Arrangement.spacedBy(
                RikkaTheme.spacing.xs,
            ),
        ) {
            Text(text = title, variant = TextVariant.P)
            Text(text = description, variant = TextVariant.Muted)
        }
        Toggle(
            checked = checked,
            onCheckedChange = onCheckedChange,
            label = "$title cookies toggle",
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(RikkaTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        ) {
            Text(text = "Payment Method", variant = TextVariant.H4)
            Text(
                text = "Choose your preferred payment method.",
                variant = TextVariant.Muted,
            )

            Spacer(modifier = Modifier.height(RikkaTheme.spacing.xs))

            RadioButton(
                selected = selectedMethod == 0,
                onClick = { selectedMethod = 0 },
                label = "Credit Card",
            )
            RadioButton(
                selected = selectedMethod == 1,
                onClick = { selectedMethod = 1 },
                label = "PayPal",
            )
            RadioButton(
                selected = selectedMethod == 2,
                onClick = { selectedMethod = 2 },
                label = "Bank Transfer",
            )

            if (selectedMethod == 0) {
                Separator()

                Input(
                    value = cardNumber,
                    onValueChange = { cardNumber = it },
                    placeholder = "4242 4242 4242 4242",
                    label = "Card number",
                    modifier = Modifier.fillMaxWidth(),
                )
                Input(
                    value = expiry,
                    onValueChange = { expiry = it },
                    placeholder = "MM / YY",
                    label = "Expiry date",
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Button(
                text = "Continue",
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
