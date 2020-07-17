package br.com.tsmweb.inventapp.features.patrimony.binding

import br.com.tsmweb.domain.patrimony.model.Patrimony
import br.com.tsmweb.domain.patrimony.model.StatusType
import br.com.tsmweb.inventapp.features.patrimony.binding.StatusType as StatusTypeBinding

object PatrimonyMapper {
    fun fromDomain(domain: Patrimony) = PatrimonyBinding().apply {
        id = domain.id
        localeId = domain.localeId
        code = domain.code
        name = domain.name
        dependency = domain.dependency
        status = StatusTypeBinding.values()[domain.status.ordinal]
    }

    fun toDomain(binding: PatrimonyBinding) = Patrimony(
        id = binding.id,
        localeId = binding.localeId,
        code = binding.code,
        name = binding.name,
        dependency = binding.dependency,
        status = StatusType.values()[binding.status.ordinal]
    )
}