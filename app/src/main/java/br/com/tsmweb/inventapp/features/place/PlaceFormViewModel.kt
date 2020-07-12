package br.com.tsmweb.inventapp.features.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tsmweb.domain.place.interactor.SavePlaceUseCase
import br.com.tsmweb.domain.place.interactor.ViewPlaceDetailsUseCase
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

class PlaceFormViewModel(
    private val savePlaceUseCase: SavePlaceUseCase
): ViewModel() {

    private val place = MutableLiveData<PlaceBinding>(PlaceBinding())
    private val saveState = SingleLiveEvent<ViewState<Unit>>()

    fun place(): LiveData<PlaceBinding> = place
    fun saveState(): LiveData<ViewState<Unit>> = saveState

    fun setPlace(place: PlaceBinding) {
        this.place.value = place
    }

    fun savePlace() {
        place.value?.let {
            saveState.postValue(ViewState(ViewState.Status.LOADING))

            viewModelScope.launch {
                try {
                    withContext(Dispatchers.IO) {
                        savePlaceUseCase.execute(PlaceMapper.toDomain(it))
                    }

                    saveState.postValue(ViewState(ViewState.Status.SUCCESS))
                } catch (e: Exception) {
                    saveState.postValue(ViewState(ViewState.Status.ERROR, error = e))
                }
            }
        } ?: saveState.postValue(ViewState(ViewState.Status.ERROR,
            error = IllegalArgumentException("Place is null")))
    }

}