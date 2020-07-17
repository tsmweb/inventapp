package br.com.tsmweb.domain.locale.model

import java.util.*

class Locale(
    var id: String,
    var code: String,
    var name: String,
    var amountPatrimony: Int,
    var lastInventory: Date?
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Locale

        if (id != other.id) return false
        if (code != other.code) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + code.hashCode()
        return result
    }
}