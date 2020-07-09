package br.com.tsmweb.data.db.place.entity

import androidx.room.*
import br.com.tsmweb.data.db.converters.DateConverter
import java.util.*

@Entity(
    tableName = "place",
    indices = arrayOf(Index(value = ["code"], unique = true)))
@TypeConverters(DateConverter::class)
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var code: String,
    var name: String,
    @ColumnInfo(name = "last_inventory")
    var lastInventory: Date?
)