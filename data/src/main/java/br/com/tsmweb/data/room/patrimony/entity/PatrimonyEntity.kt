package br.com.tsmweb.data.room.patrimony.entity

import androidx.room.*
import br.com.tsmweb.data.room.converters.StatusTypeConverter
import br.com.tsmweb.domain.patrimony.model.StatusPatrimony

@Entity(
    tableName = "patrimony",
    indices = arrayOf(Index(value = ["locale_id", "code"], unique = true)))
@TypeConverters(StatusTypeConverter::class)
data class PatrimonyEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @ColumnInfo(name = "locale_id")
    var localeId: String,

    var code: String,

    var name: String,

    var dependency: String,

    var status: StatusPatrimony
)