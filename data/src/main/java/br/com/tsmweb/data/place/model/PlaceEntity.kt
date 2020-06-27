package br.com.tsmweb.data.place.model

import java.util.*

data class PlaceEntity(
    var code: String,
    var name: String,
    var lastInventory: Date?
)