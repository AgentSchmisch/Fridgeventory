package at.florianschmid.fridgeventory.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.florianschmid.fridgeventory.data.Item
import at.florianschmid.fridgeventory.data.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class ContactDetailUi(
    val item: Item = Item(0, "", "", "", false)
)

class TaskUpdateViewModel(savedStateHandle: SavedStateHandle, private val itemRepository: ItemRepository): ViewModel() {

    private val contactId: Int = checkNotNull(savedStateHandle["contactId"])

    private val _detailUiState = MutableStateFlow(ContactDetailUi())
    val detailUiState = _detailUiState.asStateFlow()

    init {
        viewModelScope.launch {
            val contact = withContext(Dispatchers.IO) {
                itemRepository.findItemById(contactId)
            }
            _detailUiState.update {
                ContactDetailUi(contact)
            }
        }
    }

}