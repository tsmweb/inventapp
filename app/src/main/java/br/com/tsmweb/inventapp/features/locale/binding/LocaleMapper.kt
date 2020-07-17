package br.com.tsmweb.inventapp.features.locale.binding

import br.com.tsmweb.domain.locale.model.Locale

object LocaleMapper {

    fun fromDomain(domain: Locale) = LocaleBinding().apply {
        id = domain.id
        code = domain.code
        name = domain.name
        amountPatrimony = domain.amountPatrimony
        lastInventory = domain.lastInventory
    }

    fun toDomain(binding: LocaleBinding) = Locale(
        id = binding.id,
        code = binding.code,
        name = binding.name,
        amountPatrimony = binding.amountPatrimony,
        lastInventory = binding.lastInventory
    )

}