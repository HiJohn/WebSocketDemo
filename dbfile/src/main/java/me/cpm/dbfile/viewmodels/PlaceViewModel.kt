package me.cpm.dbfile.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import me.cpm.dbfile.dbs.City
import me.cpm.dbfile.dbs.PlaceDatabase
import me.cpm.dbfile.dbs.Province

/**
 * @author gaozp
 * at 2024/1/17
 */
class PlaceViewModel : ViewModel() {

    fun addCity(context: Context, city: City) {
        val insertSuccess =
            PlaceDatabase.getInstance(context.applicationContext).cityDao().insert(city)
        if (insertSuccess > 0) {
            //
        }
    }

    fun addProvince(context: Context, province: Province) {
        val insertId = PlaceDatabase.getDatabase(context.applicationContext).provinceDao()
            .addProvince(province)

        if (insertId > 0) {

        }
    }
}