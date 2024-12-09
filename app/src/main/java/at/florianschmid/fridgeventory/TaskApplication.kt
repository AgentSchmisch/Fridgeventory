package at.florianschmid.fridgeventory

import android.app.Application
import at.florianschmid.fridgeventory.data.ItemRepository
import at.florianschmid.fridgeventory.data.db.ItemDatabase

class TaskApplication : Application() {

    val itemRepository by lazy {
        ItemRepository(

            ItemDatabase.getDatabase(this).itemDao()
        )
    }
}