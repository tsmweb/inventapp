package br.com.tsmweb.data_room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.tsmweb.data_room.inventory.dao.InventoryDao
import br.com.tsmweb.data_room.inventory.entity.InventoryTable
import br.com.tsmweb.data_room.place.dao.PlaceDao
import br.com.tsmweb.data_room.place.entity.PlaceTable

@Database(entities = [PlaceTable::class, InventoryTable::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {
    abstract fun placeDao(): PlaceDao
    abstract fun inventoryDao(): InventoryDao

    companion object {
        private var instance: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "inventAppDB")
                    .build()
            }

            return instance as AppDataBase
        }

        fun destroyInstance() {
            instance = null
        }
    }
}