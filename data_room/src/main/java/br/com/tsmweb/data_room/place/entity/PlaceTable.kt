package br.com.tsmweb.data_room.place.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import br.com.tsmweb.data_room.converters.DateConverter
import java.util.*

@Entity(tableName = "place")
@TypeConverters(DateConverter::class)
data class PlaceTable(
    @PrimaryKey
    var code: String,

    var name: String,

    @ColumnInfo(name = "last_inventory")
    var lastInventory: Date?
)