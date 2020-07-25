package br.com.tsmweb.data.db.patrimony.entity

import androidx.room.*
import br.com.tsmweb.data.db.converters.StatusTypeConverter
import br.com.tsmweb.data.db.locale.entity.LocaleEntity
import br.com.tsmweb.domain.patrimony.model.StatusType

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
    var status: StatusType
)