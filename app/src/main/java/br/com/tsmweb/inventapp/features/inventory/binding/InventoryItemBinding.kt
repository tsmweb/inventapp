package br.com.tsmweb.inventapp.features.inventory.binding

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import br.com.tsmweb.inventapp.features.patrimony.binding.StatusPatrimony
import kotlinx.android.parcel.Parcelize

@Parcelize
class InventoryItemBinding: BaseObservable(), Parcelable {

    @Bindable
    var id: Long = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.id)
        }

    @Bindable
    var inventoryId: Long = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.inventoryId)
        }

    @Bindable
    var patrimonyId: Long = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.patrimonyId)
        }

    @Bindable
    var patrimonyCode: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.patrimonyCode)
        }

    @Bindable
    var patrimonyName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.patrimonyName)
        }

    @Bindable
    var patrimonyDependency: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.patrimonyDependency)
        }

    @Bindable
    var patrimonyStatus: StatusPatrimony = StatusPatrimony.ACTIVE
        set(value) {
            field = value
            notifyPropertyChanged(BR.patrimonyStatus)
        }

    @Bindable
    var status: StatusInventory = StatusInventory.UNCHECKED
        set(value) {
            field = value
            notifyPropertyChanged(BR.status)
        }

    @Bindable
    var note: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.note)
        }

    @Bindable
    var selected: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.selected)
        }

}