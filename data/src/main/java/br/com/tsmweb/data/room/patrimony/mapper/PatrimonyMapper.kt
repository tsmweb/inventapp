package br.com.tsmweb.data.room.patrimony.mapper

import br.com.tsmweb.data.room.locale.mapper.LocaleMapper
import br.com.tsmweb.data.room.patrimony.entity.PatrimonyAndLocale
import br.com.tsmweb.data.room.patrimony.entity.PatrimonyEntity
import br.com.tsmweb.domain.locale.model.Locale
import br.com.tsmweb.domain.patrimony.model.Patrimony

object PatrimonyMapper {
    fun fromDomain(domain: Patrimony) = PatrimonyEntity(
        id = domain.id,
        localeId = domain.locale.id,
        code = domain.code,
        name = domain.name,
        dependency = domain.dependency,
        status = domain.status
    )

    fun toDomain(entity: PatrimonyEntity) = Patrimony(
        id = entity.id,
        locale = Locale(
            id = entity.localeId,
            code = "",
            name = ""
        ),
        code = entity.code,
        name = entity.name,
        dependency = entity.dependency,
        status = entity.status
    )

    fun toDomain(entity: PatrimonyAndLocale) = Patrimony(
        id = entity.patrimony.id,
        locale = LocaleMapper.toDomain(entity.locale),
        code = entity.patrimony.code,
        name = entity.patrimony.name,
        dependency = entity.patrimony.dependency,
        status = entity.patrimony.status
    )
}