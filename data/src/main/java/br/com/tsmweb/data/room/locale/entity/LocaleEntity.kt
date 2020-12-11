package br.com.tsmweb.data.room.locale.entity

import androidx.room.*
import br.com.tsmweb.data.room.converters.DateConverter

@Entity(
    tableName = "locale",
    indices = arrayOf(Index(value = ["code"], unique = true)))
@TypeConverters(DateConverter::class)
data class LocaleEntity(

    @PrimaryKey
    var id: String,

    var code: String,

    var name: String
)