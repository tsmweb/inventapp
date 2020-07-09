package br.com.tsmweb.data.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.tsmweb.data.db.place.dao.PlaceDao
import br.com.tsmweb.data.db.place.entity.PlaceEntity

@Database(entities = [PlaceEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {

    abstract fun placeDao(): PlaceDao

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