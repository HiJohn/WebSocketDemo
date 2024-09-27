package me.cpm.dbfile.dbs

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * @author gapi
 * at 2024/1/17
 */
@Database(entities = [City::class, Province::class], version = 1, exportSchema = false)
abstract class PlaceDatabase : RoomDatabase() {

    companion object {
        private var INSTANCE: PlaceDatabase? = null

        const val DB_NAME = "place_db"

        fun getInstance(context: Context): PlaceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context, PlaceDatabase::class.java, DB_NAME)
                    .allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }

        fun getDatabase(context: Context): PlaceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    PlaceDatabase::class.java,
                    DB_NAME
                )
                    .createFromAsset("database/places.db")
                    .build()
                INSTANCE = instance

                instance
            }

        }
    }

    abstract fun cityDao(): CityDao
    abstract fun provinceDao(): ProvinceDao


}