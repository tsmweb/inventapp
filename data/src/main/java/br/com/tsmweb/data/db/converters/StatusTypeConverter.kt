package br.com.tsmweb.data.db.converters

import androidx.room.TypeConverter
import br.com.tsmweb.domain.inventory.model.StatusInventory
import br.com.tsmweb.domain.patrimony.model.StatusPatrimony

class StatusTypeConverter {

    @TypeConverter
    fun fromStatusPatrimony(value: StatusPatrimony): Int {
        return value.let { it.ordinal }
    }

    @TypeConverter
    fun toStatusPatrimony(value: Int): StatusPatrimony {
        return StatusPatrimony.values()[value]
    }

    @TypeConverter
    fun fromStatusInventory(value: StatusInventory): Int {
        return value.let { it.ordinal }
    }

    @TypeConverter
    fun toStatusInventory(value: Int): StatusInventory {
        return StatusInventory.values()[value]
    }
}