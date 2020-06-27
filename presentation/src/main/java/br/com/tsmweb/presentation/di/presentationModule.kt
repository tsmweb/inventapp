package br.com.tsmweb.presentation.di

import br.com.tsmweb.data.inventory.repository.InventoryRepositoryImpl
import br.com.tsmweb.data.inventory.source.InventoryRoomDataSource
import br.com.tsmweb.data.place.repository.PlaceRepositoryImpl
import br.com.tsmweb.data.place.source.PlaceRoomDataSource
import br.com.tsmweb.data_room.database.AppDataBase
import br.com.tsmweb.data_room.inventory.InventoryRoomDataSourceImpl
import br.com.tsmweb.data_room.place.PlaceRoomDataSourceImpl
import br.com.tsmweb.domain.inventory.interactor.LoadInventoriesUseCase
import br.com.tsmweb.domain.inventory.interactor.RemoveInventoryUseCase
import br.com.tsmweb.domain.inventory.repository.InventoryRepository
import br.com.tsmweb.domain.place.interactor.LoadPlacesUseCase
import br.com.tsmweb.domain.place.repository.PlaceRepository
import br.com.tsmweb.presentation.inventory.InventoryListViewModel
import br.com.tsmweb.presentation.place.PlaceListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    /*-- Place --*/
    single {
        PlaceRoomDataSourceImpl(AppDataBase.getDatabase(context = get())) as PlaceRoomDataSource
    }

    factory {
        PlaceRepositoryImpl(placeRoomDataSource = get()) as PlaceRepository
    }

    factory {
        LoadPlacesUseCase(repository = get())
    }

    viewModel {
        PlaceListViewModel(loadPlacesUseCase = get())
    }

    /*-- Inventory --*/
    single {
        InventoryRoomDataSourceImpl(AppDataBase.getDatabase(context = get())) as InventoryRoomDataSource
    }

    factory {
        InventoryRepositoryImpl(inventoryRoomDataSource = get()) as InventoryRepository
    }

    factory {
        LoadInventoriesUseCase(repository = get())
    }

    factory {
        RemoveInventoryUseCase(repository = get())
    }

    viewModel {
        InventoryListViewModel(loadInventoriesUseCase = get(), removeInventoryUseCase = get())
    }

}