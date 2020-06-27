package br.com.tsmweb.domain.place.model

import java.util.*

class Place(
    var code: String,
    var name: String,
    var lastInventory: Date?
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Place

        if (code != other.code) return false

        return true
    }

    override fun hashCode(): Int {
        return code.hashCode()
    }
}