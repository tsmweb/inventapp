package br.com.tsmweb.data.db.locale.entity

import androidx.room.*
import br.com.tsmweb.data.db.converters.DateConverter
import java.util.*

@Entity(
    tableName = "locale",
    indices = arrayOf(Index(value = ["code"], unique = true)))
@TypeConverters(DateConverter::class)
data class LocaleEntity(
    @PrimaryKey
    var id: String,
    var code: String,
    var name: String
) {
    @Ignore
    var amountPatrimony: Int = 0
    @Ignore
    var lastInventory: Date? = null
}