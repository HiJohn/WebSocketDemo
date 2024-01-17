package me.cpm.dbfile.dbs

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author gapi
 * at 2024/1/17
 */
@Entity
data class Province(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "province_name") val provinceName: String
)
