package br.com.tsmweb.inventapp.features.inventory

import androidx.lifecycle.*
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
    private val shareState = SingleLiveEvent<ViewState<List<InventoryItemBinding>>>()

    private var lastSearchTerm: String = ""

    private val inSelectionMode = MutableLiveData<Boolean>().apply {
        value = false
    }
    private val selectedItems = mutableListOf<InventoryItemBinding>()
    private val selectionCount = MutableLiveData<Int>()

    fun loadState(): LiveData<ViewState<List<InventoryItemBinding>>> = loadState

    fun showDetails(): LiveData<InventoryItemBinding> = showDetails

    fun shareState() : LiveData<ViewState<List<InventoryItemBinding>>> = shareState

    fun selectionCount(): LiveData<Int> = selectionCount

    fun getLastSearchTerm(): String {
        return lastSearchTerm
    }

    fun isInSelectionModel(): LiveData<Boolean> = inSelectionMode

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

    fun selectInventoryItem(inventoryItemBinding: InventoryItemBinding) {
        if (inSelectionMode.value == true) {
            toggleInventoryItemSelected(inventoryItemBinding)

            if (selectedItems.size == 0) {
                inSelectionMode.postValue(false)
            } else {
                selectionCount.postValue(selectedItems.size)
            }
        } else {
            showDetails.postValue(inventoryItemBinding)
        }
    }

    private fun toggleInventoryItemSelected(inventoryItemBinding: InventoryItemBinding) {
        val existing = selectedItems.find { it.id == inventoryItemBinding.id }

        if (existing == null) {
            inventoryItemBinding.selected = true
            selectedItems.add(inventoryItemBinding)
        } else {
            inventoryItemBinding.selected = false
            selectedItems.removeAll { it.id == inventoryItemBinding.id }
        }
    }

    fun setInSelectionMode(selectionMode: Boolean) {
        if (!selectionMode) {
            selectionCount.postValue(0)
            selectedItems.forEach {
                it.selected = false
            }
            selectedItems.clear()
        }

        inSelectionMode.value = selectionMode
    }

    fun shareSelected() {
        if (selectedItems.size > 0) {
            shareState.postValue(ViewState(ViewState.Status.SUCCESS, selectedItems))
        }
    }

}