package me.cpm.dbfile.dbs

import androidx.room.Dao
import androidx.room.Query

/**
 * @author gaozp
 * at 2024/1/17
 */
@Dao
interface ProvinceDao  {

    @Query("SELECT * FROM province ORDER BY province_name ASC")
    fun getAll():List<Province>

    @Query("")
    fun getById(id:Int): Province
}