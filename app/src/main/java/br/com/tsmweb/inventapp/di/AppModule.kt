package br.com.tsmweb.inventapp.di

import androidx.navigation.NavController
import br.com.tsmweb.data.db.database.AppDataBase
import br.com.tsmweb.data.db.inventory.InventoryItemRoomDataSourceImpl
import br.com.tsmweb.data.db.inventory.InventoryRoomDataSourceImpl
import br.com.tsmweb.data.db.locale.LocaleRoomDataSourceImpl
import br.com.tsmweb.data.db.patrimony.PatrimonyRoomDataSourceImpl
import br.com.tsmweb.data.inventory.repository.InventoryItemRepositoryImpl
import br.com.tsmweb.data.inventory.repository.InventoryRepositoryImpl
import br.com.tsmweb.data.inventory.source.InventoryItemRoomDataSource
import br.com.tsmweb.data.inventory.source.InventoryRoomDataSource
import br.com.tsmweb.data.locale.repository.LocaleRepositoryImpl
import br.com.tsmweb.data.locale.source.LocaleRoomDataSource
import br.com.tsmweb.data.patrimony.repository.PatrimonyRepositoryImpl
import br.com.tsmweb.data.patrimony.source.PatrimonyRoomDataSource
import br.com.tsmweb.domain.inventory.interactor.*
import br.com.tsmweb.domain.inventory.repository.InventoryItemRepository
import br.com.tsmweb.domain.inventory.repository.InventoryRepository
import br.com.tsmweb.domain.locale.interactor.ListLocalesUseCase
import br.com.tsmweb.domain.locale.interactor.RemoveLocaleUseCase
import br.com.tsmweb.domain.locale.interactor.SaveLocaleUseCase
import br.com.tsmweb.domain.locale.repository.LocaleRepository
import br.com.tsmweb.domain.patrimony.interactor.ListPatrimonyUseCase
import br.com.tsmweb.domain.patrimony.interactor.RemovePatrimonyUseCase
import br.com.tsmweb.domain.patrimony.interactor.SavePatrimonyUseCase
import br.com.tsmweb.domain.patrimony.interactor.DetailsPatrimonyUseCase
import br.com.tsmweb.domain.patrimony.repository.PatrimonyRepository
import br.com.tsmweb.inventapp.router.AppRouter
import br.com.tsmweb.inventapp.common.Router
import br.com.tsmweb.inventapp.features.inventory.InventoryItemListViewModel
import br.com.tsmweb.inventapp.features.inventory.InventoryListViewModel
import br.com.tsmweb.inventapp.features.inventory.InventoryNewViewModel
import br.com.tsmweb.inventapp.features.inventory.binding.InventoryBinding
import br.com.tsmweb.inventapp.features.inventory.binding.StatusInventory
import br.com.tsmweb.inventapp.features.locale.LocaleFormViewModel
import br.com.tsmweb.inventapp.features.locale.LocaleListViewModel
import br.com.tsmweb.inventapp.features.locale.binding.LocaleBinding
import br.com.tsmweb.inventapp.features.patrimony.PatrimonyDetailsViewModel
import br.com.tsmweb.inventapp.features.patrimony.PatrimonyFormViewModel
import br.com.tsmweb.inventapp.features.patrimony.PatrimonyListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory { (navController: NavController) ->
        AppRouter(navController) as Router
    }

    /*-- Locale --*/
    single {
        LocaleRoomDataSourceImpl(AppDataBase.getDatabase(context = get())) as LocaleRoomDataSource
    }

    factory {
        LocaleRepositoryImpl(localeRoomDataSource = get()) as LocaleRepository
    }

    factory {
        ListLocalesUseCase(repository = get())
    }

    factory {
        SaveLocaleUseCase(repository = get())
    }

    factory {
        RemoveLocaleUseCase(repository = get())
    }

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

    /*-- Patrimony --*/
    single {
        PatrimonyRoomDataSourceImpl(AppDataBase.getDatabase(context = get())) as PatrimonyRoomDataSource
    }

    factory {
        PatrimonyRepositoryImpl(patrimonyRoomDataSource = get()) as PatrimonyRepository
    }

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

    viewModel { (localeId: String) ->
        PatrimonyListViewModel(
            localeId,
            listPatrimonyUseCase = get(),
            removePatrimonyUseCase = get()
        )
    }

    viewModel {
        PatrimonyFormViewModel(
            savePatrimonyUseCase = get()
        )
    }

    viewModel { (patrimonyId: Long) ->
        PatrimonyDetailsViewModel(
            patrimonyId,
            detailsPatrimonyUseCase = get()
        )
    }

    /*-- Inventory --*/
    single {
        InventoryRoomDataSourceImpl(AppDataBase.getDatabase(context = get())) as InventoryRoomDataSource
    }

    factory {
        InventoryRepositoryImpl(inventoryRoomDataSource = get()) as InventoryRepository
    }

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

    /*-- Inventory Item --*/
    single {
        InventoryItemRoomDataSourceImpl(AppDataBase.getDatabase(context = get())) as InventoryItemRoomDataSource
    }

    factory {
        InventoryItemRepositoryImpl(inventoryItemRoomDataSource = get()) as InventoryItemRepository
    }

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
        RemoveInventoryItemUseCase(repository = get())
    }

    factory {
        CreateInventoryItemsUseCase(
            inventoryItemRepository = get(),
            patrimonyRepository = get()
        )
    }

    viewModel { (inventoryId: Long, statusInventory: StatusInventory) ->
        InventoryItemListViewModel(
            inventoryId,
            statusInventory,
            listInventoryItemUseCase = get(),
            removeInventoryItemUseCase = get()
        )
    }
}