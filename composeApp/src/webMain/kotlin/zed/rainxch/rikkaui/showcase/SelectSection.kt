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
import zed.rainxch.rikkaui.components.ui.select.Select
import zed.rainxch.rikkaui.components.ui.select.SelectOption

@Composable
fun SelectSection() {
    SectionHeader(
        title = "Select",
        description = "Dropdown selection with search and keyboard navigation.",
    )

    Spacer(Modifier.height(RikkaTheme.spacing.md))

    Card(modifier = Modifier.fillMaxWidth()) {
        Column {
            var selectedFruit by remember { mutableStateOf("") }

            val fruitOptions =
                listOf(
                    SelectOption(value = "apple", label = "Apple"),
                    SelectOption(value = "banana", label = "Banana"),
                    SelectOption(value = "cherry", label = "Cherry"),
                    SelectOption(value = "date", label = "Date"),
                    SelectOption(value = "elderberry", label = "Elderberry"),
                )

            Select(
                selectedValue = selectedFruit,
                onValueChange = { selectedFruit = it },
                options = fruitOptions,
                placeholder = "Pick a fruit...",
                label = "Fruit selector",
            )
        }
    }
}
