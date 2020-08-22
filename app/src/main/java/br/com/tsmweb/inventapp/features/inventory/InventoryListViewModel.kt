package br.com.tsmweb.inventapp.features.inventory

import androidx.lifecycle.*
import br.com.tsmweb.domain.inventory.interactor.ListInventoryUseCase
import br.com.tsmweb.domain.inventory.interactor.RemoveInventoryUseCase
import br.com.tsmweb.inventapp.common.SingleLiveEvent
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.features.inventory.binding.InventoryBinding
import br.com.tsmweb.inventapp.features.inventory.binding.InventoryMapper
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class InventoryListViewModel(
    private val localeId: String,
    private val listInventoryUseCase: ListInventoryUseCase,
    private val removeInventoryUseCase: RemoveInventoryUseCase
): ViewModel() {

    private val loadState = MutableLiveData<ViewState<List<InventoryBinding>>>()
    private val deleteState = SingleLiveEvent<ViewState<Int>>()
    private val showDetails = SingleLiveEvent<InventoryBinding>()

    private var lastSearchTerm: String = ""

    private val inDeleteMode = MutableLiveData<Boolean>().apply {
        value = false
    }
    private val selectedItems = mutableListOf<InventoryBinding>()
    private val selectionCount = MutableLiveData<Int>()

    fun loadState(): LiveData<ViewState<List<InventoryBinding>>> = loadState

    fun deleteState(): LiveData<ViewState<Int>> = deleteState

    fun showDetails(): LiveData<InventoryBinding> = showDetails

    fun selectionCount(): LiveData<Int> = selectionCount

    fun getLastSearchTerm(): String {
        return lastSearchTerm
    }

    fun isInDeleteModel(): LiveData<Boolean> = inDeleteMode

    fun search(term: String = "") {
        if ((loadState.value == null) or (lastSearchTerm != term)) {
            lastSearchTerm = term
            loadInventory(localeId, term)
        }
    }

    private fun loadInventory(localeId: String, term: String) {
        viewModelScope.launch {
            loadState.postValue(
                ViewState(
                    ViewState.Status.LOADING
                )
            )

            try {
                listInventoryUseCase.execute(localeId, term)
                    .flowOn(Dispatchers.IO)
                    .collect { inventories ->
                        val inventoriesBinding = inventories.map { inventory ->
                            InventoryMapper.fromDomain(inventory)
                        }

                        loadState.postValue(
                            ViewState(
                                ViewState.Status.SUCCESS,
                                inventoriesBinding
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

    fun selectInventory(inventoryBinding: InventoryBinding) {
        if (inDeleteMode.value == true) {
            toggleInventorySelected(inventoryBinding)

            if (selectedItems.size == 0) {
                inDeleteMode.postValue(false)
            } else {
                selectionCount.postValue(selectedItems.size)
            }
        } else {
            showDetails.postValue(inventoryBinding)
        }
    }

    private fun toggleInventorySelected(inventoryBinding: InventoryBinding) {
        val existing = selectedItems.find { it.id == inventoryBinding.id }

        if (existing == null) {
            inventoryBinding.selected = true
            selectedItems.add(inventoryBinding)
        } else {
            inventoryBinding.selected = false
            selectedItems.removeAll { it.id == inventoryBinding.id }
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
        val inventories = selectedItems.map { inventoryBinding ->
            InventoryMapper.toDomain(inventoryBinding)
        }

        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    removeInventoryUseCase.execute(inventories)
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