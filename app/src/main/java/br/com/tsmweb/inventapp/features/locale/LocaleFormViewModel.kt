package br.com.tsmweb.inventapp.features.locale

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tsmweb.domain.locale.interactor.SaveLocaleUseCase
import br.com.tsmweb.inventapp.common.SingleLiveEvent
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.features.locale.binding.LocaleBinding
import br.com.tsmweb.inventapp.features.locale.binding.LocaleMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LocaleFormViewModel(
    private val saveLocaleUseCase: SaveLocaleUseCase
): ViewModel() {

    private val locale = MutableLiveData(LocaleBinding())
    private val saveState = SingleLiveEvent<ViewState<Unit>>()

    fun locale(): LiveData<LocaleBinding> = locale
    fun saveState(): LiveData<ViewState<Unit>> = saveState

    fun setLocale(locale: LocaleBinding) {
        this.locale.value = locale
    }

    fun saveLocale() {
        locale.value?.let {
            saveState.postValue(ViewState(ViewState.Status.LOADING))

            viewModelScope.launch {
                try {
                    withContext(Dispatchers.IO) {
                        saveLocaleUseCase.execute(LocaleMapper.toDomain(it))
                    }

                    saveState.postValue(ViewState(ViewState.Status.SUCCESS))
                } catch (e: Exception) {
                    saveState.postValue(ViewState(ViewState.Status.ERROR, error = e))
                }
            }
        } ?: saveState.postValue(ViewState(ViewState.Status.ERROR,
            error = IllegalArgumentException("Locale is null")))
    }

}