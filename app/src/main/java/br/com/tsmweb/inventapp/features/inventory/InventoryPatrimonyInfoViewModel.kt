package br.com.tsmweb.inventapp.features.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tsmweb.domain.inventory.interactor.FindInventoryItemByBarcodeUseCase
import br.com.tsmweb.domain.inventory.interactor.SaveInventoryItemUseCase
import br.com.tsmweb.inventapp.common.SingleLiveEvent
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.features.inventory.binding.InventoryItemBinding
import br.com.tsmweb.inventapp.features.inventory.binding.InventoryItemMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class InventoryPatrimonyInfoViewModel(
    private val findInventoryItemByBarcodeUseCase: FindInventoryItemByBarcodeUseCase,
    private val saveInventoryItemUseCase: SaveInventoryItemUseCase
) : ViewModel() {

    private val findState = SingleLiveEvent<ViewState<InventoryItemBinding>>()
    private val saveState = SingleLiveEvent<ViewState<Unit>>()

    fun findState(): LiveData<ViewState<InventoryItemBinding>> = findState

    fun saveState(): LiveData<ViewState<Unit>> = saveState

    fun findInventoryItem(inventoryId: Long, code: String) {
        viewModelScope.launch {
            findState.postValue(
                ViewState(ViewState.Status.LOADING)
            )

            try {
                var inventoryItem: InventoryItemBinding

                withContext(Dispatchers.IO) {
                    inventoryItem = InventoryItemMapper.fromDomain(
                        findInventoryItemByBarcodeUseCase.execute(inventoryId, code)
                    )
                }

                findState.postValue(
                    ViewState(ViewState.Status.SUCCESS, data = inventoryItem)
                )
            } catch (e: Exception) {
                findState.postValue(
                    ViewState(ViewState.Status.ERROR, error = e)
                )
            }
        }
    }

    fun saveInventoryItem(editMode: Boolean) {
        findState.value?.data?.let {
            viewModelScope.launch {
                saveState.postValue(
                    ViewState(ViewState.Status.LOADING)
                )

                try {
                    withContext(Dispatchers.IO) {
                        saveInventoryItemUseCase.execute(InventoryItemMapper.toDomain(it), editMode)
                    }

                    saveState.postValue(ViewState(ViewState.Status.SUCCESS))
                } catch (e: Exception) {
                    saveState.postValue(ViewState(ViewState.Status.ERROR, error = e))
                }
            }
        }
    }

}