package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.layout.Column
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
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.input.Input
import zed.rainxch.rikkaui.components.ui.label.Label
import zed.rainxch.rikkaui.components.ui.textarea.Textarea

@Composable
fun InputSection() {
    SectionHeader(
        title = "Input & Textarea",
        description = "Text fields, multi-line areas, and form labels.",
    )

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    Card(modifier = Modifier.fillMaxWidth()) {
        Column {
            var name by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var message by remember { mutableStateOf("") }

            // Name field
            Label("Name")
            Spacer(Modifier.height(RikkaTheme.spacing.xs))
            Input(
                value = name,
                onValueChange = { name = it },
                placeholder = "Enter your name...",
            )

            Spacer(Modifier.height(RikkaTheme.spacing.lg))

            // Email field
            Label("Email")
            Spacer(Modifier.height(RikkaTheme.spacing.xs))
            Input(
                value = email,
                onValueChange = { email = it },
                placeholder = "email@example.com",
            )

            Spacer(Modifier.height(RikkaTheme.spacing.lg))

            // Disabled input
            Label("Disabled", disabled = true)
            Spacer(Modifier.height(RikkaTheme.spacing.xs))
            Input(
                value = "",
                onValueChange = {},
                placeholder = "This input is disabled",
                enabled = false,
            )

            Spacer(Modifier.height(RikkaTheme.spacing.lg))

            // Textarea
            Label("Message")
            Spacer(Modifier.height(RikkaTheme.spacing.xs))
            Textarea(
                value = message,
                onValueChange = { message = it },
                placeholder = "Write your message...",
            )
        }
    }
}
