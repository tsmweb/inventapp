package br.com.tsmweb.inventapp.features.inventory.binding

import br.com.tsmweb.domain.inventory.model.InventoryItem
import br.com.tsmweb.domain.inventory.model.StatusInventory
import br.com.tsmweb.domain.patrimony.model.StatusPatrimony
import br.com.tsmweb.inventapp.features.inventory.binding.StatusInventory as StatusInventoryBinding
import br.com.tsmweb.inventapp.features.patrimony.binding.StatusPatrimony as StatusPatrimonyBinding

object InventoryItemMapper {

    fun fromDomain(domain: InventoryItem) = InventoryItemBinding().apply {
        id = domain.id
        inventoryId = domain.inventoryId
        patrimonyCode = domain.patrimonyCode
        patrimonyName = domain.patrimonyName
        patrimonyDependency = domain.patrimonyDependency
        patrimonyStatus = StatusPatrimonyBinding.valueOf(domain.patrimonyStatus.name)
        status = StatusInventoryBinding.valueOf(domain.status.name)
        note = domain.note
    }

    fun toDomain(binding: InventoryItemBinding) = InventoryItem(
        id = binding.id,
        inventoryId = binding.inventoryId,
        patrimonyCode = binding.patrimonyCode,
        patrimonyName = binding.patrimonyName,
        patrimonyDependency = binding.patrimonyDependency,
        patrimonyStatus = StatusPatrimony.valueOf(binding.patrimonyStatus.name),
        status = StatusInventory.valueOf(binding.status.name),
        note = binding.note
    )
}