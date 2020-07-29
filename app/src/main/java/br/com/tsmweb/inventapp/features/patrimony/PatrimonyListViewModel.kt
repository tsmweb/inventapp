package br.com.tsmweb.inventapp.features.patrimony

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tsmweb.domain.patrimony.interactor.ListPatrimonyUseCase
import br.com.tsmweb.domain.patrimony.interactor.RemovePatrimonyUseCase
import br.com.tsmweb.inventapp.common.SingleLiveEvent
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.features.patrimony.binding.PatrimonyBinding
import br.com.tsmweb.inventapp.features.patrimony.binding.PatrimonyMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class PatrimonyListViewModel(
    private val localeId: String,
    private val listPatrimonyUseCase: ListPatrimonyUseCase,
    private val removePatrimonyUseCase: RemovePatrimonyUseCase
) : ViewModel() {

    private val loadState = MutableLiveData<ViewState<List<PatrimonyBinding>>>()
    private val deleteState = SingleLiveEvent<ViewState<Int>>()
    private val showDetails = SingleLiveEvent<PatrimonyBinding>()

    private val inDeleteMode = MutableLiveData<Boolean>().apply {
        value = false
    }
    private val selectedItems = mutableListOf<PatrimonyBinding>()
    private val selectionCount = MutableLiveData<Int>()

    private var lastSearchTerm: String = ""

    fun loadState(): LiveData<ViewState<List<PatrimonyBinding>>> = loadState
    fun deleteState(): LiveData<ViewState<Int>> = deleteState
    fun showDetails(): LiveData<PatrimonyBinding> = showDetails
    fun selectionCount(): LiveData<Int> = selectionCount
    fun isInDeleteModel(): LiveData<Boolean> = inDeleteMode

    fun getLastSearchTerm(): String {
        return lastSearchTerm
    }

    fun search(term: String = "") {
        if ((loadState.value == null) or (lastSearchTerm != term)) {
            lastSearchTerm = term
            loadPatrimonies(localeId, lastSearchTerm)
        }
    }

    private fun loadPatrimonies(localeId: String, term: String) {
        viewModelScope.launch {
            loadState.postValue(ViewState(ViewState.Status.LOADING))

            try {
                listPatrimonyUseCase.execute(localeId, term)
                    .flowOn(Dispatchers.IO)
                    .collect { patrimonies ->
                        val patrimoniesBinding = patrimonies.map { patrimony ->
                            PatrimonyMapper.fromDomain(patrimony)
                        }

                        loadState.postValue(
                            ViewState(
                                ViewState.Status.SUCCESS,
                                patrimoniesBinding
                            )
                        )
                    }
            } catch (e: Exception) {
                loadState.postValue(
                    ViewState(
                        ViewState.Status.ERROR,
                        error = e
                    )
                )
            }
        }
    }

    fun selectPatrimony(patrimonyBinding: PatrimonyBinding) {
        if (inDeleteMode.value == true) {
            toggleInventorySelected(patrimonyBinding)

            if (selectedItems.size == 0) {
                inDeleteMode.postValue(false)
            } else {
                selectionCount.postValue(selectedItems.size)
            }
        } else {
            showDetails.postValue(patrimonyBinding)
        }
    }

    private fun toggleInventorySelected(patrimonyBinding: PatrimonyBinding) {
        val existing = selectedItems.find { it.id == patrimonyBinding.id }

        if (existing == null) {
            patrimonyBinding.selected = true
            selectedItems.add(patrimonyBinding)
        } else {
            patrimonyBinding.selected = false
            selectedItems.removeAll { it.id == patrimonyBinding.id }
        }
    }

    fun setInDeleteMode(deleteMode: Boolean) {
        if (!deleteMode) {
            selectionCount.postValue(0)
            selectedItems.forEach {
                it.selected = false
            }
            selectedItems.clear()
        }

        inDeleteMode.value = deleteMode
    }

    fun deleteSelected() {
        val patrimonies = selectedItems.map { patrimonyBinding ->
            PatrimonyMapper.toDomain(patrimonyBinding)
        }

        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    removePatrimonyUseCase.execute(patrimonies)
                }

                deleteState.postValue(
                    ViewState(
                        ViewState.Status.SUCCESS,
                        selectedItems.size
                    )
                )
                setInDeleteMode(false)
            } catch (e: Exception) {
                deleteState.postValue(
                    ViewState(
                        ViewState.Status.ERROR,
                        error = e
                    )
                )
            }
        }
    }
}