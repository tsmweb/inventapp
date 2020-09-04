package br.com.tsmweb.inventapp.features.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tsmweb.domain.inventory.interactor.ListInventoryItemUseCase
import br.com.tsmweb.domain.inventory.model.StatusInventory
import br.com.tsmweb.inventapp.common.SingleLiveEvent
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.features.inventory.binding.InventoryItemBinding
import br.com.tsmweb.inventapp.features.inventory.binding.InventoryItemMapper
import br.com.tsmweb.inventapp.features.inventory.binding.StatusInventory as StatusInventoryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.lang.Exception

class InventoryItemListViewModel(
    private val inventoryId: Long,
    private val statusInventory: StatusInventoryBinding,
    private val listInventoryItemUseCase: ListInventoryItemUseCase
) : ViewModel() {

    private val loadState = MutableLiveData<ViewState<List<InventoryItemBinding>>>()
    private val showDetails = SingleLiveEvent<InventoryItemBinding>()

    private var lastSearchTerm: String = ""

    fun loadState(): LiveData<ViewState<List<InventoryItemBinding>>> = loadState

    fun showDetails(): LiveData<InventoryItemBinding> = showDetails

    fun getLastSearchTerm(): String {
        return lastSearchTerm
    }

    fun search(term: String = "") {
        if ((loadState.value == null) or (lastSearchTerm != term)) {
            lastSearchTerm = term
            loadInventory(inventoryId, statusInventory, term)
        }
    }

    private fun loadInventory(inventoryId: Long, statusInventory: StatusInventoryBinding, term: String) {
        viewModelScope.launch {
            loadState.postValue(
                ViewState(
                    ViewState.Status.LOADING
                )
            )

            try {
                val status = StatusInventory.valueOf(statusInventory.name)

                listInventoryItemUseCase.execute(inventoryId, status, term)
                    .flowOn(Dispatchers.IO)
                    .collect { items ->
                        val inventoryItemBinding = items.map { item ->
                            InventoryItemMapper.fromDomain(item)
                        }

                        loadState.postValue(
                            ViewState(
                                ViewState.Status.SUCCESS,
                                inventoryItemBinding
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

    fun selectInventory(inventoryItemBinding: InventoryItemBinding) {
        showDetails.postValue(inventoryItemBinding)
    }

}