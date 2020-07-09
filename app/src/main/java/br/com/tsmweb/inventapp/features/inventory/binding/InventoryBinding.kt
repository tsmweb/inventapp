package br.com.tsmweb.inventapp.features.inventory.binding

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class InventoryBinding(
    var id: Long = 0,
    var place: br.com.tsmweb.inventapp.features.place.binding.PlaceBinding? = null,
    var dateInventory: Date = Date(),
    var patrimonyChecked: Int = 0,
    var patrimonyNotFound: Int = 0,
    var patrimonyNotChecked: Int = 0,
    var selected: Boolean = false
) : Parcelable {}