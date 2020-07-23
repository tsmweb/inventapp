package br.com.tsmweb.inventapp.features.patrimony

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tsmweb.domain.patrimony.interactor.ListPatrimonyUseCase
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.features.locale.binding.LocaleBinding
import br.com.tsmweb.inventapp.features.locale.binding.LocaleMapper
import br.com.tsmweb.inventapp.features.patrimony.binding.PatrimonyBinding
import br.com.tsmweb.inventapp.features.patrimony.binding.PatrimonyMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.lang.Exception

class PatrimonyListViewModel(
    private val localeId: String,
    private val listPatrimonyUseCase: ListPatrimonyUseCase
) : ViewModel() {

    private val loadState = MutableLiveData<ViewState<List<PatrimonyBinding>>>()

    private var lastSearchTerm: String = ""

    fun loadState(): LiveData<ViewState<List<PatrimonyBinding>>> = loadState

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
}