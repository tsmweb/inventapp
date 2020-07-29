package br.com.tsmweb.inventapp.features.patrimony.binding

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import br.com.tsmweb.inventapp.features.locale.binding.LocaleBinding
import kotlinx.android.parcel.Parcelize

@Parcelize
class PatrimonyBinding: BaseObservable(), Parcelable {

    @Bindable
    var id: Long = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.id)
        }

    @Bindable
    var locale: LocaleBinding = LocaleBinding()
        set(value) {
            field = value
            notifyPropertyChanged(BR.locale)
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
    var dependency: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.dependency)
        }

    @Bindable
    var status: StatusType = StatusType.ACTIVE
        set(value) {
            field = value
            notifyPropertyChanged(BR.status)
        }

    @Bindable
    var selected: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.selected)
        }
}
