package at.florianschmid.fridgeventory.ui.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.florianschmid.fridgeventory.data.Item
import at.florianschmid.fridgeventory.data.ItemRepository
import kotlinx.coroutines.launch

data class TaskAddUi(
    val item: Item = Item(0, "", "", "",false)
)

class TaskAddViewModel(private val itemRepository: ItemRepository) : ViewModel() {

    var addUiState by mutableStateOf(TaskAddUi())
        private set

    init {
        viewModelScope.launch {
            val item = Item(0, "", "","",false)
            addUiState = TaskAddUi(item)
        }
    }

    fun updateTask(item: Item) {
        addUiState = addUiState.copy(item=item)
    }

    fun saveTask() {
        viewModelScope.launch {
            itemRepository.addItem(addUiState.item)
        }
    }

}