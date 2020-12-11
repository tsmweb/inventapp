package br.com.tsmweb.inventapp.di

import androidx.navigation.NavController
import br.com.tsmweb.inventapp.router.AppRouter
import br.com.tsmweb.inventapp.common.Router
import br.com.tsmweb.inventapp.features.inventory.InventoryItemListViewModel
import br.com.tsmweb.inventapp.features.inventory.InventoryListViewModel
import br.com.tsmweb.inventapp.features.inventory.InventoryNewViewModel
import br.com.tsmweb.inventapp.features.inventory.InventoryPatrimonyInfoViewModel
import br.com.tsmweb.inventapp.features.inventory.binding.StatusInventory
import br.com.tsmweb.inventapp.features.locale.LocaleFormViewModel
import br.com.tsmweb.inventapp.features.locale.LocaleListViewModel
import br.com.tsmweb.inventapp.features.locale.binding.LocaleBinding
import br.com.tsmweb.inventapp.features.patrimony.PatrimonyDetailsViewModel
import br.com.tsmweb.inventapp.features.patrimony.PatrimonyFormViewModel
import br.com.tsmweb.inventapp.features.patrimony.PatrimonyListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object AppModule {

    fun load() {
        loadKoinModules(module {
            // App
            factory<Router> { (navController: NavController) ->
                AppRouter(navController)
            }

            // Locales
            viewModel {
                LocaleListViewModel(
                    listLocalesUseCase = get(),
                    removeLocaleUseCase = get()
                )
            }

            viewModel {
                LocaleFormViewModel(
                    saveLocaleUseCase = get()
                )
            }

            // Patrimony
            viewModel { (localeId: String) ->
                PatrimonyListViewModel(
                    localeId,
                    listPatrimonyUseCase = get(),
                    removePatrimonyUseCase = get()
                )
            }

            viewModel {
                PatrimonyFormViewModel(
                    savePatrimonyUseCase = get(),
                    loadDependencyUseCase = get()
                )
            }

            viewModel { (patrimonyId: Long) ->
                PatrimonyDetailsViewModel(
                    patrimonyId,
                    detailsPatrimonyUseCase = get()
                )
            }

            // Inventory
            viewModel { (locale: LocaleBinding) ->
                InventoryNewViewModel(
                    locale,
                    saveInventoryUseCase = get()
                )
            }

            viewModel { (localeId: String) ->
                InventoryListViewModel(
                    localeId,
                    listInventoryUseCase = get(),
                    removeInventoryUseCase = get()
                )
            }

            // InventoryItem
            viewModel { (inventoryId: Long, statusInventory: StatusInventory) ->
                InventoryItemListViewModel(
                    inventoryId,
                    statusInventory,
                    listInventoryItemUseCase = get()
                )
            }

            viewModel {
                InventoryPatrimonyInfoViewModel(
                    findInventoryItemByBarcodeUseCase = get(),
                    saveInventoryItemUseCase = get()
                )
            }
        })
    }
}
