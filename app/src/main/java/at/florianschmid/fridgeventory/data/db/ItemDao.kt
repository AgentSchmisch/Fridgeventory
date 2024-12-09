package at.florianschmid.fridgeventory.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert
    suspend fun addItem(itemEntity: ItemEntity)

    @Update
    suspend fun updateItem(itemEntity: ItemEntity)

    @Delete
    suspend fun deleteItem(itemEntity: ItemEntity)

    @Query("SELECT * FROM tasks WHERE _id = :id")
    suspend fun findItemById(id: Int): ItemEntity

    @Query("SELECT * from tasks WHERE status = :status")
    fun getAllItems(status:Boolean): Flow<List<ItemEntity>>

    @Query("SELECT * FROM items WHERE expiry_date BETWEEN date('now') AND date('now', '+2 days')")
    fun getItemsExpiringSoon(): Flow<List<ItemEntity>>

}
