package zed.rainxch.rikkaui.showcase

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

@Composable
fun FooterSection() {
    Separator()

    Spacer(Modifier.height(RikkaTheme.spacing.xl))

    Text(
        text = "Built with Compose Multiplatform",
        variant = TextVariant.Muted,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )

    Spacer(Modifier.height(RikkaTheme.spacing.xs))

    Text(
        text = "RikkaUI — Zero Material. Pure Kotlin.",
        variant = TextVariant.Small,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
        color = RikkaTheme.colors.primary,
    )

    Spacer(Modifier.height(RikkaTheme.spacing.xxxl))
}
