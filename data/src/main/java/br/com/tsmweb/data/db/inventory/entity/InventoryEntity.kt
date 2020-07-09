package br.com.tsmweb.data.db.inventory.entity

import androidx.room.*
import br.com.tsmweb.data.db.converters.DateConverter
import br.com.tsmweb.data.db.place.entity.PlaceEntity
import java.util.*

@Entity(tableName = "inventory")
@TypeConverters(DateConverter::class)
data class InventoryEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @Embedded(prefix = "place_")
    var place: PlaceEntity,

    @ColumnInfo(name = "date_inventory")
    var dateInventory: Date,

    @ColumnInfo(name = "patrimony_checked")
    var patrimonyChecked: Int,

    @ColumnInfo(name = "patrimony_not_found")
    var patrimonyNotFound: Int,

    @ColumnInfo(name = "patrimony_not_checked")
    var patrimonyNotChecked: Int
)