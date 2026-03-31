package zed.rainxch.rikkaui.showcase.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme

@Composable
fun SectionHeader(
    title: String,
    description: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = title,
            variant = TextVariant.H3,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.xs))

        Text(
            text = description,
            variant = TextVariant.Muted,
        )
    }
}
