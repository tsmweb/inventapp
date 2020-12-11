package br.com.tsmweb.data.room.di

import br.com.tsmweb.data.room.database.AppDataBase
import br.com.tsmweb.data.room.inventory.RoomInventoryDataStore
import br.com.tsmweb.data.room.inventory.RoomInventoryItemDataStore
import br.com.tsmweb.data.room.locale.RoomLocaleDataStore
import br.com.tsmweb.data.room.patrimony.RoomPatrimonyDataStore
import br.com.tsmweb.domain.inventory.gateway.InventoryDataStore
import br.com.tsmweb.domain.inventory.gateway.InventoryItemDataStore
import br.com.tsmweb.domain.locale.gateway.LocaleDataStore
import br.com.tsmweb.domain.patrimony.gateway.PatrimonyDataStore
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object DataRoomModules {

    fun load() {
        loadKoinModules(module {
            single<LocaleDataStore> {
                RoomLocaleDataStore(AppDataBase.getDatabase(context = get()))
            }

            single<PatrimonyDataStore> {
                RoomPatrimonyDataStore(AppDataBase.getDatabase(context = get()))
            }

            single<InventoryDataStore> {
                RoomInventoryDataStore(AppDataBase.getDatabase(context = get()))
            }

            single<InventoryItemDataStore> {
                RoomInventoryItemDataStore(AppDataBase.getDatabase(context = get()))
            }
        })
    }

}