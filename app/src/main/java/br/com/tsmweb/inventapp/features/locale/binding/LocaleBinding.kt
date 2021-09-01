package br.com.tsmweb.inventapp.features.locale.binding

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
class LocaleBinding: BaseObservable(), Parcelable {
    @Bindable
    var id: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.id)
        }

    @Bindable
    var code: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.code)
        }

    @Bindable
    var name: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    @Bindable
    var amountPatrimony: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.amountPatrimony)
        }

    @Bindable
    var lastInventory: Date? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastInventory)
        }
}