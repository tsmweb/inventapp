package br.com.tsmweb.data.room.inventory.entity

import androidx.room.*

@Entity(tableName = "inventory",
    indices = arrayOf(Index(value = ["locale_id", "date_inventory"])))
data class InventoryEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @ColumnInfo(name = "locale_id")
    var localeId: String,

    @ColumnInfo(name = "date_inventory")
    var dateInventory: String
)