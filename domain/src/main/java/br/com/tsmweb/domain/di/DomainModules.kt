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
                ListLocalesUseCase(repository = get())
            }

            factory {
                SaveLocaleUseCase(repository = get())
            }

            factory {
                RemoveLocaleUseCase(repository = get())
            }

            // Patrimony
            factory {
                ListPatrimonyUseCase(repository = get())
            }

            factory {
                SavePatrimonyUseCase(repository = get())
            }

            factory {
                RemovePatrimonyUseCase(repository = get())
            }

            factory {
                DetailsPatrimonyUseCase(repository = get())
            }

            factory {
                LoadDependencyUseCase(repository = get())
            }

            // Inventory
            factory {
                ListInventoryUseCase(repository = get())
            }

            factory {
                SaveInventoryUseCase(
                    repository = get(),
                    createInventoryItemsUseCase = get())
            }

            factory {
                RemoveInventoryUseCase(repository = get())
            }

            // InventoryItem
            factory {
                ListInventoryItemUseCase(repository = get())
            }

            factory {
                FindInventoryItemByBarcodeUseCase(repository = get())
            }

            factory {
                SaveInventoryItemUseCase(repository = get())
            }

            factory {
                CreateInventoryItemsUseCase(
                    inventoryItemRepository = get(),
                    patrimonyRepository = get())
            }
        })
    }

}