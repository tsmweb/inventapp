package br.com.tsmweb.domain.inventory.model

import br.com.tsmweb.domain.place.model.Place
import java.util.*

class Inventory (
    var id: Long,
    var place: Place,
    var dateInventory: Date,
    var patrimonyChecked: Int,
    var patrimonyNotFound: Int,
    var patrimonyNotChecked: Int
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Inventory

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}