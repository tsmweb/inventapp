package br.com.tsmweb.data.room.di

import br.com.tsmweb.data.room.database.AppDataBase
import br.com.tsmweb.data.room.inventory.RoomInventoryRepository
import br.com.tsmweb.data.room.inventory.RoomInventoryItemRepository
import br.com.tsmweb.data.room.locale.RoomLocaleRepository
import br.com.tsmweb.data.room.patrimony.RoomPatrimonyRepository
import br.com.tsmweb.domain.inventory.repository.InventoryRepository
import br.com.tsmweb.domain.inventory.repository.InventoryItemRepository
import br.com.tsmweb.domain.locale.repository.LocaleRepository
import br.com.tsmweb.domain.patrimony.repository.PatrimonyRepository
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object DataRoomModules {

    fun load() {
        loadKoinModules(module {
            single<LocaleRepository> {
                RoomLocaleRepository(AppDataBase.getDatabase(context = get()))
            }

            single<PatrimonyRepository> {
                RoomPatrimonyRepository(AppDataBase.getDatabase(context = get()))
            }

            single<InventoryRepository> {
                RoomInventoryRepository(AppDataBase.getDatabase(context = get()))
            }

            single<InventoryItemRepository> {
                RoomInventoryItemRepository(AppDataBase.getDatabase(context = get()))
            }
        })
    }

}