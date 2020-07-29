package br.com.tsmweb.data.db.patrimony.mapper

import br.com.tsmweb.data.db.patrimony.entity.PatrimonyAndLocale
import br.com.tsmweb.data.db.patrimony.entity.PatrimonyEntity
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

    fun toDomain(entity: PatrimonyAndLocale) = Patrimony(
        id = entity.patrimony.id,
        locale = Locale(
            id = entity.locale.id,
            code = entity.locale.code,
            name = entity.locale.name
        ),
        code = entity.patrimony.code,
        name = entity.patrimony.name,
        dependency = entity.patrimony.dependency,
        status = entity.patrimony.status
    )
}