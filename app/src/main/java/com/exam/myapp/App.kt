package com.exam.myapp

import android.app.Application
import com.exam.netstatelib.NetWorkMonitorManager

class App : Application() {

    var app :App by lazy {
        app = this
    }

    override fun onCreate() {
        super.onCreate()
        NetWorkMonitorManager.getInstance().init(this)
    }


}