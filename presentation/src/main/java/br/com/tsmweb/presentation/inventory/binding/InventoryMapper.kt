package br.com.tsmweb.presentation.inventory.binding

import br.com.tsmweb.domain.inventory.model.Inventory
import br.com.tsmweb.domain.place.model.Place
import br.com.tsmweb.presentation.place.binding.PlaceBinding

object InventoryMapper {

    fun fromDomain(domain: Inventory): InventoryBinding {
        return InventoryBinding(
            id = domain.id,
            place = PlaceBinding(
                code = domain.place?.code ?: "",
                name = domain.place?.name ?: "",
                lastInventory = domain.dateInventory),
            dateInventory = domain.dateInventory,
            patrimonyChecked = domain.patrimonyChecked,
            patrimonyNotFound = domain.patrimonyNotFound,
            patrimonyNotChecked = domain.patrimonyNotChecked
        )
    }

    fun toDomain(binding: InventoryBinding): Inventory {
        return Inventory(
            id = binding.id,
            place = Place(
                code = binding.place?.code ?: "",
                name = binding.place?.name ?: "",
                lastInventory = binding.dateInventory),
            dateInventory = binding.dateInventory,
            patrimonyChecked = binding.patrimonyChecked,
            patrimonyNotFound = binding.patrimonyNotFound,
            patrimonyNotChecked = binding.patrimonyNotChecked
        )
    }

}