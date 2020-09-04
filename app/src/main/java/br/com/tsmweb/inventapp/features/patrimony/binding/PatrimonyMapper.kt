package br.com.tsmweb.inventapp.features.patrimony.binding

import br.com.tsmweb.domain.patrimony.model.Patrimony
import br.com.tsmweb.domain.patrimony.model.StatusPatrimony
import br.com.tsmweb.inventapp.features.locale.binding.LocaleMapper
import br.com.tsmweb.inventapp.features.patrimony.binding.StatusType as StatusTypeBinding

object PatrimonyMapper {
    fun fromDomain(domain: Patrimony) = PatrimonyBinding().apply {
        id = domain.id
        locale = LocaleMapper.fromDomain(domain.locale)
        code = domain.code
        name = domain.name
        dependency = domain.dependency
        status = StatusTypeBinding.valueOf(domain.status.name)
    }

    fun toDomain(binding: PatrimonyBinding) = Patrimony(
        id = binding.id,
        locale = LocaleMapper.toDomain(binding.locale),
        code = binding.code,
        name = binding.name,
        dependency = binding.dependency,
        status = StatusPatrimony.valueOf(binding.status.name)
    )
}