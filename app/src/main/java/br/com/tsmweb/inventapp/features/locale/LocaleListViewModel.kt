package br.com.tsmweb.inventapp.features.locale

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tsmweb.domain.locale.interactor.ListLocalesUseCase
import br.com.tsmweb.domain.locale.interactor.RemoveLocaleUseCase
import br.com.tsmweb.inventapp.common.SingleLiveEvent
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.features.locale.binding.LocaleBinding
import br.com.tsmweb.inventapp.features.locale.binding.LocaleMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LocaleListViewModel(
    private val listLocalesUseCase: ListLocalesUseCase,
    private val removeLocaleUseCase: RemoveLocaleUseCase
): ViewModel() {

    private val loadState = MutableLiveData<ViewState<List<LocaleBinding>>>()
    private val removeState = SingleLiveEvent<ViewState<Unit>>()

    private var lastSearchTerm: String = ""

    fun loadState(): LiveData<ViewState<List<LocaleBinding>>> = loadState
    fun removeState(): LiveData<ViewState<Unit>> = removeState

    fun getLastSearchTerm(): String {
        return lastSearchTerm
    }

    fun search(term: String = "") {
        if ((loadState.value == null) or (lastSearchTerm != term)) {
            lastSearchTerm = term
            loadLocales(lastSearchTerm)
        }
    }

    private fun loadLocales(term: String) {
        viewModelScope.launch {
            loadState.postValue(ViewState(ViewState.Status.LOADING))

            try {
                listLocalesUseCase.execute(term)
                    .flowOn(Dispatchers.IO)
                    .collect { locales ->
                        val localesBinding = locales.map { locale ->
                            LocaleMapper.fromDomain(locale)
                        }

                        loadState.postValue(
                            ViewState(
                                ViewState.Status.SUCCESS,
                                localesBinding
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

    fun removePlace(locale: LocaleBinding) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    removeLocaleUseCase.execute(LocaleMapper.toDomain(locale))
                }

                removeState.postValue(ViewState(ViewState.Status.SUCCESS))
            } catch (e: Exception) {
                removeState.postValue(
                    ViewState(
                        ViewState.Status.ERROR,
                        error = e
                    )
                )
            }
        }
    }

}