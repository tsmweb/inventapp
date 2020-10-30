package br.com.tsmweb.inventapp.features.inventory.binding

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kotlinx.android.parcel.Parcelize
import androidx.databinding.library.baseAdapters.BR
import java.util.*

@Parcelize
class InventoryBinding: BaseObservable(), Parcelable {

    @Bindable
    var id: Long = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.id)
        }

    @Bindable
    var localeId: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.localeId)
        }

    @Bindable
    var dateInventory: Date = Date()
        set(value) {
            field = value
            notifyPropertyChanged(BR.dateInventory)
        }

    @Bindable
    var patrimonyChecked: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.patrimonyChecked)
        }

    @Bindable
    var patrimonyNotFound: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.patrimonyNotFound)
        }

    @Bindable
    var patrimonyNotChecked: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.patrimonyNotChecked)
        }

    @Bindable
    var selected: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.selected)
        }

}