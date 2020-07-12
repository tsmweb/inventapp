package br.com.tsmweb.inventapp.features.place.binding

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class PlaceBinding(
    var id: Long = 0,
    var code: String = "",
    var name: String = "",
    var amountPatrimony: Int = 0,
    var lastInventory: Date? = null
) : Parcelable {}