package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.alert.Alert
import zed.rainxch.rikkaui.components.ui.alert.AlertDescription
import zed.rainxch.rikkaui.components.ui.alert.AlertTitle
import zed.rainxch.rikkaui.components.ui.alert.AlertVariant
import zed.rainxch.rikkaui.components.ui.card.Card

@Composable
fun AlertSection() {
    SectionHeader(
        title = "Alert",
        description = "Status messages for important information.",
    )

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    Card(modifier = Modifier.fillMaxWidth()) {
        Alert {
            AlertTitle("Heads up!")
            AlertDescription("You can add components to your app using the CLI.")
        }

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        Alert(variant = AlertVariant.Destructive) {
            AlertTitle("Error")
            AlertDescription(
                text = "Your session has expired. Please log in again.",
                variant = AlertVariant.Destructive,
            )
        }
    }
}
