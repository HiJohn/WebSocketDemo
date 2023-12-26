package com.exam.myapp

import android.app.Application
import com.exam.netstatelib.NetWorkMonitorManager

class App : Application() {

    val app :App by lazy {
        this@App
    }

    override fun onCreate() {
        super.onCreate()
        NetWorkMonitorManager.getInstance().init(this)
    }


}