package br.com.tsmweb.inventapp.features.patrimony.binding

import br.com.tsmweb.domain.locale.model.Locale
import br.com.tsmweb.domain.patrimony.model.Patrimony
import br.com.tsmweb.domain.patrimony.model.StatusType
import br.com.tsmweb.inventapp.features.locale.binding.LocaleBinding
import br.com.tsmweb.inventapp.features.patrimony.binding.StatusType as StatusTypeBinding

object PatrimonyMapper {
    fun fromDomain(domain: Patrimony) = PatrimonyBinding().apply {
        id = domain.id
        locale = LocaleBinding().apply {
            id = domain.locale.id
            code = domain.locale.code
            name = domain.locale.name
        }
        code = domain.code
        name = domain.name
        dependency = domain.dependency
        status = StatusTypeBinding.values()[domain.status.ordinal]
    }

    fun toDomain(binding: PatrimonyBinding) = Patrimony(
        id = binding.id,
        locale = Locale(
            id = binding.locale.id,
            code = binding.locale.code,
            name = binding.locale.name
        ),
        code = binding.code,
        name = binding.name,
        dependency = binding.dependency,
        status = StatusType.values()[binding.status.ordinal]
    )
}