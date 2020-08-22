package br.com.tsmweb.data.db.inventory.entity

import androidx.room.*
import br.com.tsmweb.data.db.converters.StatusTypeConverter
import br.com.tsmweb.domain.inventory.model.StatusInventory

@Entity(
    tableName = "inventory_item",
    indices = arrayOf(Index(value = ["inventory_id", "patrimony_id"], unique = true)))
@TypeConverters(StatusTypeConverter::class)
data class InventoryItemEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo(name = "inventory_id")
    var inventoryId: Long,
    @ColumnInfo(name = "patrimony_id")
    var patrimonyId: Long,
    var status: StatusInventory,
    var note: String
)