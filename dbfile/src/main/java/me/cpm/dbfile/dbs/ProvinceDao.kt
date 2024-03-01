package me.cpm.dbfile.dbs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * @author gaozp
 * at 2024/1/17
 */
@Dao
interface ProvinceDao  {

    @Query("SELECT * FROM province ORDER BY province_name ASC")
    fun getAll():List<Province>

    @Query("SELECT * FROM province WHERE id=:id")
    fun getById(id:Int): Province

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProvince(province: Province):Long

    @Delete
    fun delete(province: Province?)

    @Query("DELETE FROM province WHERE id = :provinceId")
    fun delete(provinceId: Int): Int
}