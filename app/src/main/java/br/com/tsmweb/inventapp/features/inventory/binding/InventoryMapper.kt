package br.com.tsmweb.inventapp.features.inventory.binding

import br.com.tsmweb.domain.inventory.model.Inventory
import br.com.tsmweb.inventapp.features.locale.binding.LocaleBinding

object InventoryMapper {

    fun fromDomain(domain: Inventory) = InventoryBinding(
        id = domain.id,
        locale = LocaleBinding(),
        dateInventory = domain.dateInventory,
        patrimonyChecked = domain.patrimonyChecked,
        patrimonyNotFound = domain.patrimonyNotFound,
        patrimonyNotChecked = domain.patrimonyNotChecked
    )

    fun toDomain(binding: InventoryBinding) = Inventory(
        id = binding.id,
        localeId = "",
        dateInventory = binding.dateInventory,
        patrimonyChecked = binding.patrimonyChecked,
        patrimonyNotFound = binding.patrimonyNotFound,
        patrimonyNotChecked = binding.patrimonyNotChecked
    )

}