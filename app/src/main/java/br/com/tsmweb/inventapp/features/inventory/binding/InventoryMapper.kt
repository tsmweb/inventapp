package br.com.tsmweb.inventapp.features.inventory.binding

import br.com.tsmweb.domain.inventory.model.Inventory
import br.com.tsmweb.inventapp.features.locale.binding.LocaleMapper

object InventoryMapper {

    fun fromDomain(domain: Inventory) = InventoryBinding().apply {
        id = domain.id
        locale = LocaleMapper.fromDomain(domain.locale)
        dateInventory = domain.dateInventory
        patrimonyChecked = domain.patrimonyChecked
        patrimonyNotFound = domain.patrimonyNotFound
        patrimonyNotChecked = domain.patrimonyUnchecked
    }

    fun toDomain(binding: InventoryBinding) = Inventory(
        id = binding.id,
        locale = LocaleMapper.toDomain(binding.locale),
        dateInventory = binding.dateInventory,
        patrimonyChecked = binding.patrimonyChecked,
        patrimonyNotFound = binding.patrimonyNotFound,
        patrimonyUnchecked = binding.patrimonyNotChecked
    )

}