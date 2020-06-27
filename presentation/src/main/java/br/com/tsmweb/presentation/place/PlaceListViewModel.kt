package br.com.tsmweb.presentation.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tsmweb.domain.place.interactor.LoadPlacesUseCase
import br.com.tsmweb.presentation.ViewState
import br.com.tsmweb.presentation.place.binding.PlaceBinding
import br.com.tsmweb.presentation.place.binding.PlaceMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.lang.Exception

class PlaceListViewModel(
    private val loadPlacesUseCase: LoadPlacesUseCase
): ViewModel() {

    private val loadState = MutableLiveData<ViewState<List<PlaceBinding>>>()

    private var lastSearchTerm: String = ""

    fun loadState(): LiveData<ViewState<List<PlaceBinding>>> = loadState

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
            loadState.postValue(
                ViewState(
                    ViewState.Status.LOADING
                )
            )

            try {
                loadPlacesUseCase.execute(term)
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

}