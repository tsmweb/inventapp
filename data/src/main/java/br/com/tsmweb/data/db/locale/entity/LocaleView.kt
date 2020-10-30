package br.com.tsmweb.data.db.locale.entity

data class LocaleView(
    var id: String,
    var code: String,
    var name: String,
    var amountPatrimony: Int,
    var lastInventory: String
)