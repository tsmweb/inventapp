package br.com.tsmweb.data.db.inventory.entity

data class InventoryView (
    var id: Long,
    var localeId: String,
    var dateInventory: String,
    var patrimonyChecked: Int,
    var patrimonyUnchecked: Int,
    var patrimonyNotFound: Int
)