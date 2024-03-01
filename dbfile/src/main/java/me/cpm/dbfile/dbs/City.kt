package me.cpm.dbfile.dbs

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author gapi
 * at 2024/1/17
 */
@Entity
data class City(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "city_name") val cityName: String,
    @ColumnInfo(name = "province_id") val provinceId: Int,
    @ColumnInfo(name = "province") val province: String?,

)
