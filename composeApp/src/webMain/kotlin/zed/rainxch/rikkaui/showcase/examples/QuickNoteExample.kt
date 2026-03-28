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

/**
 * Quick note-taking card example for the showcase mosaic grid.
 *
 * Demonstrates Card, Kbd, Textarea, ToggleGroup, and Button
 * in a note composer pattern.
 */
@Composable
fun QuickNoteExample() {
    var noteText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Personal") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        label = "Quick note card",
    ) {
        CardHeader {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Quick Note",
                    variant = TextVariant.H4,
                )
                Kbd(text = "\u2318N")
            }
        }

        CardContent {
            Textarea(
                value = noteText,
                onValueChange = { noteText = it },
                placeholder = "Write something...",
                label = "Note content",
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
                        text = "Personal",
                        selected = selectedCategory == "Personal",
                        onClick = { selectedCategory = "Personal" },
                    )
                    ToggleGroupItem(
                        text = "Work",
                        selected = selectedCategory == "Work",
                        onClick = { selectedCategory = "Work" },
                    )
                    ToggleGroupItem(
                        text = "Ideas",
                        selected = selectedCategory == "Ideas",
                        onClick = { selectedCategory = "Ideas" },
                    )
                }
                Button(
                    text = "Save",
                    onClick = { },
                    size = ButtonSize.Sm,
                )
            }
        }
    }
}
