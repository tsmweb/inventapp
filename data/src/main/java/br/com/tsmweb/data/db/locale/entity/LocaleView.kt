package br.com.tsmweb.data.db.locale.entity

import androidx.room.TypeConverters
import br.com.tsmweb.data.db.converters.DateConverter
import java.util.*

@TypeConverters(DateConverter::class)
data class LocaleView(
    var id: String,
    var code: String,
    var name: String,
    var amountPatrimony: Int,
    var lastInventory: Date?
)