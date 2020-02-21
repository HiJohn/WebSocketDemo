package com.exam.myapp;

import android.app.Application;

import com.exam.netstatelib.NetWorkMonitorManager;


public class App extends Application {


    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        NetWorkMonitorManager.getInstance().init(this);
    }


    public static App getApp() {
        return app;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
