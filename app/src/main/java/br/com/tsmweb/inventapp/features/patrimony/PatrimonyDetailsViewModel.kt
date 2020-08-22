package br.com.tsmweb.inventapp.features.patrimony

import androidx.lifecycle.*
import br.com.tsmweb.domain.patrimony.interactor.DetailsPatrimonyUseCase
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.features.patrimony.binding.PatrimonyBinding
import br.com.tsmweb.inventapp.features.patrimony.binding.PatrimonyMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.lang.Exception

class PatrimonyDetailsViewModel(
    private val patrimonyId: Long,
    private val detailsPatrimonyUseCase: DetailsPatrimonyUseCase
) : ViewModel() {

    private val loadState: MutableLiveData<ViewState<PatrimonyBinding>> = MutableLiveData()
//    val patrimony = Transformations.map(loadState) { it.data }

    fun loadState(): LiveData<ViewState<PatrimonyBinding>> = loadState

    fun loadPatrimony() {
        viewModelScope.launch {
            loadState.postValue(ViewState(ViewState.Status.LOADING))

            try {
                detailsPatrimonyUseCase.execute(patrimonyId)
                    .flowOn(Dispatchers.IO)
                    .collect { patrimony ->
                        if (patrimony != null) {
                            val patrimonyBinding = PatrimonyMapper.fromDomain(patrimony)
                            loadState.postValue(ViewState(ViewState.Status.SUCCESS, patrimonyBinding))
                        } else {
                            loadState.postValue(
                                ViewState(
                                    ViewState.Status.ERROR,
                                    error = RuntimeException("Book not found")
                                )
                            )
                        }
                    }
            } catch (e: Exception) {
                loadState.postValue(ViewState(ViewState.Status.ERROR, error = e))
            }
        }
    }

}