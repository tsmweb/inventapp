package br.com.tsmweb.inventapp.features.patrimony

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tsmweb.domain.patrimony.interactor.LoadDependencyUseCase
import br.com.tsmweb.domain.patrimony.interactor.SavePatrimonyUseCase
import br.com.tsmweb.inventapp.common.SingleLiveEvent
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.features.patrimony.binding.PatrimonyBinding
import br.com.tsmweb.inventapp.features.patrimony.binding.PatrimonyMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.Exception

class PatrimonyFormViewModel(
    private val savePatrimonyUseCase: SavePatrimonyUseCase,
    private val loadDependencyUseCase: LoadDependencyUseCase
) : ViewModel() {

    private val patrimony = MutableLiveData(PatrimonyBinding())
    private val saveState = SingleLiveEvent<ViewState<Unit>>()
    private val loadDependencyState = MutableLiveData<ViewState<List<String>>>()

    fun patrimony(): LiveData<PatrimonyBinding> = patrimony
    fun saveState(): LiveData<ViewState<Unit>> = saveState
    fun loadDependencyState(): LiveData<ViewState<List<String>>> = loadDependencyState

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

    fun loadDependency(localeId: String) {
        viewModelScope.launch {
            loadDependencyState.postValue(ViewState(ViewState.Status.LOADING))

            try {
                loadDependencyUseCase.execute(localeId)
                    .flowOn(Dispatchers.IO)
                    .collect { dependencies ->
                        loadDependencyState.postValue(ViewState(ViewState.Status.SUCCESS, dependencies))
                    }
            } catch (e: Exception) {
                loadDependencyState.postValue(ViewState(ViewState.Status.ERROR, error = e))
            }
        }
    }
}