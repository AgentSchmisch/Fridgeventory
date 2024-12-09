package at.florianschmid.fridgeventory.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.florianschmid.fridgeventory.data.Item
import at.florianschmid.fridgeventory.data.ItemRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(val repository: ItemRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.loadInitialTasks()
        }
    }

    val tasksUiState = repository.getAllItems()
        .map { TasksUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TasksUiState(emptyList())
        )

    fun onTaskCheckedChanged(item: Item, isChecked: Boolean) {
        viewModelScope.launch {
            repository.updateItem(item.copy(status = isChecked))
        }
    }

    fun onAddButtonClicked() {
        viewModelScope.launch {
            repository.addItem(Item(0,"","","", false))
        }
    }
}
