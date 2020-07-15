package br.com.tsmweb.inventapp.features.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tsmweb.domain.place.interactor.ListPlacesUseCase
import br.com.tsmweb.domain.place.interactor.RemovePlaceUseCase
import br.com.tsmweb.inventapp.common.SingleLiveEvent
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.features.place.binding.PlaceBinding
import br.com.tsmweb.inventapp.features.place.binding.PlaceMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class PlaceListViewModel(
    private val listPlacesUseCase: ListPlacesUseCase,
    private val removePlaceUseCase: RemovePlaceUseCase
): ViewModel() {

    private val loadState = MutableLiveData<ViewState<List<PlaceBinding>>>()
    private val removeState = SingleLiveEvent<ViewState<Unit>>()

    private var lastSearchTerm: String = ""

    fun loadState(): LiveData<ViewState<List<PlaceBinding>>> = loadState
    fun removeState(): LiveData<ViewState<Unit>> = removeState

    fun getLastSearchTerm(): String {
        return lastSearchTerm
    }

    fun search(term: String = "") {
        if ((loadState.value == null) or (lastSearchTerm != term)) {
            lastSearchTerm = term
            loadPlaces(lastSearchTerm)
        }
    }

    private fun loadPlaces(term: String) {
        viewModelScope.launch {
            loadState.postValue(ViewState(ViewState.Status.LOADING))

            try {
                listPlacesUseCase.execute(term)
                    .flowOn(Dispatchers.IO)
                    .collect { places ->
                        val placesBinding = places.map { place ->
                            PlaceMapper.fromDomain(place)
                        }

                        loadState.postValue(
                            ViewState(
                                ViewState.Status.SUCCESS,
                                placesBinding
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

    fun removePlace(place: PlaceBinding) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    removePlaceUseCase.execute(PlaceMapper.toDomain(place))
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