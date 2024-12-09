package at.florianschmid.fridgeventory.data

data class Item(
    val id: Int,
    val name: String,
    val desc: String,
    val dueDate: String,
    val status: Boolean
)
