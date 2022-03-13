package br.com.tsmweb.data.room.locale.mapper

import br.com.tsmweb.data.room.common.Constants.DATE_FORMAT_DB
import br.com.tsmweb.data.room.locale.entity.LocaleEntity
import br.com.tsmweb.data.room.locale.entity.LocaleView
import br.com.tsmweb.domain.locale.model.Locale
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

internal object LocaleMapper {
    fun fromDomain(domain: Locale) = LocaleEntity(
        id = domain.id,
        code = domain.code,
        name = domain.name
    )

    fun toDomain(entity: LocaleView) = Locale(
        id = entity.id,
        code = entity.code,
        name = entity.name,
        amountPatrimony = entity.amountPatrimony,
        lastInventory = SimpleDateFormat(DATE_FORMAT_DB).parse(entity.lastInventory)
//        lastInventory = DateFormat.getDateInstance().parse(entity.lastInventory)
    )

    fun toDomain(entity: LocaleEntity) = Locale(
        id = entity.id,
        code = entity.code,
        name = entity.name,
        amountPatrimony = 0,
        lastInventory = Date()
    )
}