package br.com.tsmweb.data.inventory.model

import br.com.tsmweb.data.place.model.PlaceEntity
import java.util.*

data class InventoryEntity(
    var id: Long,
    var place: PlaceEntity,
    var dateInventory: Date,
    var patrimonyChecked: Int,
    var patrimonyNotFound: Int,
    var patrimonyNotChecked: Int
)