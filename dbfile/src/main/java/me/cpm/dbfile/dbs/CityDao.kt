package me.cpm.dbfile.dbs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * @author gapi
 * at 2024/1/17
 */
@Dao
interface CityDao {


    @Query("SELECT * FROM city ORDER BY city_name ASC")
    fun getAll():List<City>

    @Insert
    fun insert(city: City)
}