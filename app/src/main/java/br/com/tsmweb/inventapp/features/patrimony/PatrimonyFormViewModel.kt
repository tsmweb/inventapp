package br.com.tsmweb.inventapp.features.patrimony

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tsmweb.domain.patrimony.interactor.SavePatrimonyUseCase
import br.com.tsmweb.inventapp.common.SingleLiveEvent
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.features.patrimony.binding.PatrimonyBinding
import br.com.tsmweb.inventapp.features.patrimony.binding.PatrimonyMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class PatrimonyFormViewModel(
    private val savePatrimonyUseCase: SavePatrimonyUseCase
) : ViewModel() {

    private val patrimony = MutableLiveData<PatrimonyBinding>(PatrimonyBinding())
    private val saveState = SingleLiveEvent<ViewState<Unit>>()

    fun patrimony(): LiveData<PatrimonyBinding> = patrimony
    fun saveState(): LiveData<ViewState<Unit>> = saveState

    fun setPatrimony(patrimony: PatrimonyBinding) {
        this.patrimony.value = patrimony
    }

    fun savePatrimony() {
        patrimony.value?.let {
            saveState.postValue(ViewState(ViewState.Status.LOADING))

            viewModelScope.launch {
                try {
                    withContext(Dispatchers.IO) {
                        savePatrimonyUseCase.execute(PatrimonyMapper.toDomain(it))
                    }

                    saveState.postValue(ViewState(ViewState.Status.SUCCESS))
                } catch (e: Exception) {
                    saveState.postValue(ViewState(ViewState.Status.ERROR, error = e))
                }
            }
        } ?: saveState.postValue(ViewState(ViewState.Status.ERROR,
            error = IllegalArgumentException("Patrimony is null")))
    }
}