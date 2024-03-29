package br.com.tsmweb.domain.inventory.model

import br.com.tsmweb.domain.patrimony.model.StatusPatrimony

class InventoryItem(
    var id: Long,
    var inventoryId: Long,
    var patrimonyCode: String,
    var patrimonyName: String,
    var patrimonyDependency: String,
    var patrimonyStatus: StatusPatrimony,
    var status: StatusInventory,
    var note: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InventoryItem

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}