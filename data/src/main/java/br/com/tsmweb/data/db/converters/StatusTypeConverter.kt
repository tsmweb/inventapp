package br.com.tsmweb.data.db.converters

import androidx.room.TypeConverter
import br.com.tsmweb.domain.patrimony.model.StatusType

class StatusTypeConverter {
    @TypeConverter
    fun fromStatusType(value: StatusType): Int {
        return value.let { value.ordinal }
    }

    @TypeConverter
    fun toStatusType(value: Int): StatusType {
        return StatusType.values()[value]
    }
}