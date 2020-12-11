package br.com.tsmweb.domain.di

import br.com.tsmweb.domain.inventory.interactor.*
import br.com.tsmweb.domain.locale.interactor.ListLocalesUseCase
import br.com.tsmweb.domain.locale.interactor.RemoveLocaleUseCase
import br.com.tsmweb.domain.locale.interactor.SaveLocaleUseCase
import br.com.tsmweb.domain.patrimony.interactor.*
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object DomainModules {

    fun load() {
        loadKoinModules(module {
            // Locale
            factory {
                ListLocalesUseCase(localeDataStore = get())
            }

            factory {
                SaveLocaleUseCase(localeDataStore = get())
            }

            factory {
                RemoveLocaleUseCase(localeDataStore = get())
            }

            // Patrimony
            factory {
                ListPatrimonyUseCase(patrimonyDataStore = get())
            }

            factory {
                SavePatrimonyUseCase(patrimonyDataStore = get())
            }

            factory {
                RemovePatrimonyUseCase(patrimonyDataStore = get())
            }

            factory {
                DetailsPatrimonyUseCase(patrimonyDataStore = get())
            }

            factory {
                LoadDependencyUseCase(patrimonyDataStore = get())
            }

            // Inventory
            factory {
                ListInventoryUseCase(inventoryDataStore = get())
            }

            factory {
                SaveInventoryUseCase(
                    inventoryDataStore = get(),
                    createInventoryItemsUseCase = get())
            }

            factory {
                RemoveInventoryUseCase(inventoryDataStore = get())
            }

            // InventoryItem
            factory {
                ListInventoryItemUseCase(inventoryItemDataStore = get())
            }

            factory {
                FindInventoryItemByBarcodeUseCase(inventoryItemDataStore = get())
            }

            factory {
                SaveInventoryItemUseCase(inventoryItemDataStore = get())
            }

            factory {
                CreateInventoryItemsUseCase(
                    inventoryItemDataStore = get(),
                    patrimonyDataStore = get())
            }
        })
    }

}