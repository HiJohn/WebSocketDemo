package me.cpm.dbfile

import android.app.Application
import me.cpm.dbfile.dbs.PlaceDatabase

/**
 * @author gapi
 * at 2024/1/17
 */

fun Application.initDataBase() {
    val database: PlaceDatabase by lazy {
        PlaceDatabase.getDatabase(this)
    }
}