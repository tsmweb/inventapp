package br.com.tsmweb.data.db.patrimony.mapper

import br.com.tsmweb.data.db.patrimony.entity.PatrimonyEntity
import br.com.tsmweb.domain.patrimony.model.Patrimony

object PatrimonyMapper {
    fun fromDomain(domain: Patrimony) = PatrimonyEntity(
        id = domain.id,
        localeId = domain.localeId,
        code = domain.code,
        name = domain.name,
        dependency = domain.dependency,
        status = domain.status
    )

    fun toDomain(entity: PatrimonyEntity) = Patrimony(
        id = entity.id,
        localeId = entity.localeId,
        code = entity.code,
        name = entity.name,
        dependency = entity.dependency,
        status = entity.status
    )
}