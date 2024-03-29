package br.com.tsmweb.data.room.inventory.entity

import androidx.room.*
import br.com.tsmweb.data.room.converters.StatusTypeConverter
import br.com.tsmweb.domain.inventory.model.StatusInventory
import br.com.tsmweb.domain.patrimony.model.StatusPatrimony

@Entity(
    tableName = "inventory_item",
    indices = arrayOf(Index(value = ["inventory_id", "patrimony_code"], unique = true)))
@TypeConverters(StatusTypeConverter::class)
data class InventoryItemEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @ColumnInfo(name = "inventory_id")
    var inventoryId: Long,

    @ColumnInfo(name = "patrimony_code")
    var patrimonyCode: String,

    @ColumnInfo(name = "patrimony_name")
    var patrimonyName: String,

    @ColumnInfo(name = "patrimony_dependency")
    var patrimonyDependency: String,

    @ColumnInfo(name = "patrimony_status")
    var patrimonyStatus: StatusPatrimony,

    var status: StatusInventory,

    var note: String
)