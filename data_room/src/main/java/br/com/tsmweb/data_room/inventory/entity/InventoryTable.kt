package br.com.tsmweb.data_room.inventory.entity

import androidx.room.*
import br.com.tsmweb.data_room.converters.DateConverter
import br.com.tsmweb.data_room.place.entity.PlaceTable
import java.util.*

@Entity(tableName = "inventory")
@TypeConverters(DateConverter::class)
data class InventoryTable(
    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @Embedded(prefix = "place_")
    var place: PlaceTable,

    @ColumnInfo(name = "date_inventory")
    var dateInventory: Date,

    @ColumnInfo(name = "patrimony_checked")
    var patrimonyChecked: Int,

    @ColumnInfo(name = "patrimony_not_found")
    var patrimonyNotFound: Int,

    @ColumnInfo(name = "patrimony_not_checked")
    var patrimonyNotChecked: Int
)