package br.com.tsmweb.inventapp.features.inventory.binding

import br.com.tsmweb.domain.inventory.model.InventoryItem
import br.com.tsmweb.inventapp.features.patrimony.binding.PatrimonyMapper
import br.com.tsmweb.domain.inventory.model.StatusInventory
import br.com.tsmweb.inventapp.features.inventory.binding.StatusInventory as StatusInventoryBinding

object InventoryItemMapper {

    fun fromDomain(domain: InventoryItem) = InventoryItemBinding().apply {
        id = domain.id
        inventoryId = domain.inventoryId
        patrimony = PatrimonyMapper.fromDomain(domain.patrimony)
        status = StatusInventoryBinding.valueOf(domain.status.name)
        note = domain.note
    }

    fun toDomain(binding: InventoryItemBinding) = InventoryItem(
        id = binding.id,
        inventoryId = binding.inventoryId,
        patrimony = PatrimonyMapper.toDomain(binding.patrimony),
        status = StatusInventory.valueOf(binding.status.name),
        note = binding.note
    )
}