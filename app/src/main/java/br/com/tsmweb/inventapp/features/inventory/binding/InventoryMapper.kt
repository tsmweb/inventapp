package br.com.tsmweb.inventapp.features.inventory.binding

import br.com.tsmweb.domain.inventory.model.Inventory
import br.com.tsmweb.domain.place.model.Place
import br.com.tsmweb.inventapp.features.place.binding.PlaceBinding

object InventoryMapper {

    fun fromDomain(domain: Inventory) = InventoryBinding(
        id = domain.id,
        place = PlaceBinding(
            id = domain.place?.id ?: 0,
            code = domain.place?.code ?: "",
            name = domain.place?.name ?: "",
            lastInventory = domain.dateInventory
        ),
        dateInventory = domain.dateInventory,
        patrimonyChecked = domain.patrimonyChecked,
        patrimonyNotFound = domain.patrimonyNotFound,
        patrimonyNotChecked = domain.patrimonyNotChecked
    )

    fun toDomain(binding: InventoryBinding) = Inventory(
        id = binding.id,
        place = Place(
            id = binding.place?.id ?: 0,
            code = binding.place?.code ?: "",
            name = binding.place?.name ?: "",
            lastInventory = binding.dateInventory
        ),
        dateInventory = binding.dateInventory,
        patrimonyChecked = binding.patrimonyChecked,
        patrimonyNotFound = binding.patrimonyNotFound,
        patrimonyNotChecked = binding.patrimonyNotChecked
    )

}