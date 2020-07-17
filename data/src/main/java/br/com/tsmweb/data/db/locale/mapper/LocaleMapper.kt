package br.com.tsmweb.data.db.locale.mapper

import br.com.tsmweb.data.db.locale.entity.LocaleEntity
import br.com.tsmweb.domain.locale.model.Locale

object LocaleMapper {
    fun fromDomain(domain: Locale) = LocaleEntity(
        id = domain.id,
        code = domain.code,
        name = domain.name
    ).apply {
        amountPatrimony = domain.amountPatrimony
        lastInventory = domain.lastInventory
    }

    fun toDomain(entity: LocaleEntity) = Locale(
        id = entity.id,
        code = entity.code,
        name = entity.name,
        amountPatrimony = entity.amountPatrimony,
        lastInventory = entity.lastInventory
    )
}