package br.com.tsmweb.data.db.inventory.entity

import androidx.room.*

@Entity(tableName = "inventory",
    indices = arrayOf(Index(value = ["locale_id", "date_inventory"])))
data class InventoryEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo(name = "locale_id")
    var localeId: String,
    @ColumnInfo(name = "date_inventory")
    var dateInventory: String,
    @ColumnInfo(name = "patrimony_checked")
    var patrimonyChecked: Int,
    @ColumnInfo(name = "patrimony_not_found")
    var patrimonyNotFound: Int,
    @ColumnInfo(name = "patrimony_unchecked")
    var patrimonyUnchecked: Int
)