package at.florianschmid.fridgeventory.ui.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import at.florianschmid.fridgeventory.data.Item
import at.florianschmid.fridgeventory.ui.AppViewModelProvider

@Composable
fun TaskEditScreen(
    modifier: Modifier = Modifier,
    viewModel: TaskEditViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onSave: () -> Unit
) {
    val contact = viewModel.editUiState.item
    TaskEditForm(contact, modifier, onValueChange = { contactChanged ->
        viewModel.updateContact(contactChanged)
    }) {
        viewModel.saveContact()
        onSave()
    }
}

@Composable
fun TaskEditForm(
    item: Item,
    modifier: Modifier = Modifier,
    onValueChange: (Item) -> Unit = {},
    onSaveButtonClicked: () -> Unit = {}
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
    )
    {
        Column(Modifier.padding(16.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                OutlinedTextField(
                    value = item.name,
                    label = { Text("Name") },
                    onValueChange = { newText ->
                        onValueChange(item.copy(name = newText))
                    })
            }
            Row {
                OutlinedTextField(
                    value = item.desc,
                    label = { Text("Description") },
                    onValueChange = { newText ->
                        onValueChange(item.copy(desc = newText))
                    })
            }
            Row {
                OutlinedTextField(
                    value = item.dueDate,
                    label = { Text("Due Date") },
                    onValueChange = { newText ->
                        onValueChange(item.copy(dueDate = newText))
                    })
            }

            Button(onClick = { onSaveButtonClicked() }) {
                Text("Save changes")
            }
        }
    }
}

@Preview
@Composable
private fun ContactEditPreview() {
    TaskEditForm(Item(234, "", "asdfasf", "",false)) { }
}