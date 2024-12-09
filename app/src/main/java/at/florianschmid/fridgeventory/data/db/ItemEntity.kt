package at.florianschmid.fridgeventory.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "tasks")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val _id: Int = 0,
    val name: String,
    val expiry_date:  LocalDate,
    val quantity: Int,
    val additional: String,
)