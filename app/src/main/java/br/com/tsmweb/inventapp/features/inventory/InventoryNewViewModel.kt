package br.com.tsmweb.inventapp.features.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tsmweb.domain.inventory.interactor.SaveInventoryUseCase
import br.com.tsmweb.inventapp.common.ViewState
import br.com.tsmweb.inventapp.features.inventory.binding.InventoryBinding
import br.com.tsmweb.inventapp.features.inventory.binding.InventoryMapper
import br.com.tsmweb.inventapp.features.locale.binding.LocaleBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class InventoryNewViewModel(
    private val localeBinding: LocaleBinding,
    private val saveInventoryUseCase: SaveInventoryUseCase
) : ViewModel() {

    private val saveState = MutableLiveData<ViewState<Unit>>()

    fun saveState(): LiveData<ViewState<Unit>> = saveState

    fun saveInventory() {
        val inventory = InventoryBinding().apply {
            localeId = localeBinding.id
        }

        saveState.postValue(ViewState(ViewState.Status.LOADING))

        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    saveInventoryUseCase.execute(InventoryMapper.toDomain(inventory))
                }

                saveState.postValue(ViewState(ViewState.Status.SUCCESS))
            } catch (e: Exception) {
                saveState.postValue(ViewState(ViewState.Status.ERROR, error = e))
            }
        }
    }

}