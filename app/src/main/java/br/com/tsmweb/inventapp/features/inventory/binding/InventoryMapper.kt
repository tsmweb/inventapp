package br.com.tsmweb.inventapp.features.inventory.binding

import br.com.tsmweb.domain.inventory.model.Inventory

object InventoryMapper {

    fun fromDomain(domain: Inventory) = InventoryBinding().apply {
        id = domain.id
        localeId = domain.localeId
        dateInventory = domain.dateInventory
        patrimonyChecked = domain.patrimonyChecked
        patrimonyNotFound = domain.patrimonyNotFound
        patrimonyNotChecked = domain.patrimonyUnchecked
    }

    fun toDomain(binding: InventoryBinding) = Inventory(
        id = binding.id,
        localeId = binding.localeId,
        dateInventory = binding.dateInventory,
        patrimonyChecked = binding.patrimonyChecked,
        patrimonyNotFound = binding.patrimonyNotFound,
        patrimonyUnchecked = binding.patrimonyNotChecked
    )

}