package br.com.tsmweb.presentation.place.binding

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class PlaceBinding(
    var code: String = "",
    var name: String = "",
    var lastInventory: Date? = null
) : Parcelable {}