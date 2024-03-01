package me.cpm.dbfile.dbs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * @author gapi
 * at 2024/1/17
 */
@Dao
interface CityDao {


    @Query("SELECT * FROM city ORDER BY city_name ASC")
    fun getAll():List<City>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city: City):Long

    @Delete
    fun delete(city: City?)

    @Query("DELETE FROM city WHERE id = :cityId")
    fun delete(cityId: Int): Int
}