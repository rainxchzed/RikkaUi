package zed.rainxch.rikkaui.showcase.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import rikkaui.composeapp.generated.resources.Res
import rikkaui.composeapp.generated.resources.ideas
import rikkaui.composeapp.generated.resources.note_content
import rikkaui.composeapp.generated.resources.personal
import rikkaui.composeapp.generated.resources.quick_note
import rikkaui.composeapp.generated.resources.quick_note_label
import rikkaui.composeapp.generated.resources.save
import rikkaui.composeapp.generated.resources.work
import rikkaui.composeapp.generated.resources.write_something
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonSize
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.card.CardContent
import zed.rainxch.rikkaui.components.ui.card.CardFooter
import zed.rainxch.rikkaui.components.ui.card.CardHeader
import zed.rainxch.rikkaui.components.ui.kbd.Kbd
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.textarea.Textarea
import zed.rainxch.rikkaui.components.ui.togglegroup.ToggleGroup
import zed.rainxch.rikkaui.components.ui.togglegroup.ToggleGroupItem

@Composable
fun QuickNoteExample() {
    var noteText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Personal") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        label = stringResource(Res.string.quick_note_label),
    ) {
        CardHeader {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = stringResource(Res.string.quick_note), variant = TextVariant.H4)
                Kbd(text = "\u2318N")
            }
        }

        CardContent {
            Textarea(
                value = noteText,
                onValueChange = { noteText = it },
                placeholder = stringResource(Res.string.write_something),
                label = stringResource(Res.string.note_content),
                modifier = Modifier.fillMaxWidth(),
            )
        }

        CardFooter {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ToggleGroup {
                    ToggleGroupItem(
                        text = stringResource(Res.string.personal),
                        selected = selectedCategory == "Personal",
                        onClick = { selectedCategory = "Personal" },
                    )
                    ToggleGroupItem(
                        text = stringResource(Res.string.work),
                        selected = selectedCategory == "Work",
                        onClick = { selectedCategory = "Work" },
                    )
                    ToggleGroupItem(
                        text = stringResource(Res.string.ideas),
                        selected = selectedCategory == "Ideas",
                        onClick = { selectedCategory = "Ideas" },
                    )
                }
                Button(
                    text = stringResource(Res.string.save),
                    onClick = { },
                    size = ButtonSize.Sm,
                )
            }
        }
    }
}
