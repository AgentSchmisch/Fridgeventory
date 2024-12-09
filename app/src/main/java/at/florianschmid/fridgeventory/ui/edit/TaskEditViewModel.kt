package at.florianschmid.fridgeventory.ui.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.florianschmid.fridgeventory.data.Item
import at.florianschmid.fridgeventory.data.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class ContactEditUi(
    val item: Item = Item(0, "", "", "",false)
)

class TaskEditViewModel(private val savedStateHandle: SavedStateHandle,
                        private val itemRepository: ItemRepository) : ViewModel() {

    private val contactId: Int = checkNotNull(savedStateHandle["contactId"])

    var editUiState by mutableStateOf(ContactEditUi())
        private set

    init {
        viewModelScope.launch {
            val contact = withContext(Dispatchers.IO) {
                itemRepository.findItemById(contactId)
            }
            editUiState = ContactEditUi(contact)
        }
    }

    fun updateContact(item: Item) {
        editUiState = editUiState.copy(item=item)
    }

    fun saveContact() {
        viewModelScope.launch {
            itemRepository.updateItem(editUiState.item)
        }
    }

}