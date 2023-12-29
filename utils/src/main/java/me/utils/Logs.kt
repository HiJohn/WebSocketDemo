package me.utils

import android.util.Log

/**
 *
 *
 **/
object Logs {
    const val TAG = "barry"

    fun logI(tag:String,log: String) {
        Log.i(tag, log)
    }
    fun logI(log: String) {
        Log.i(TAG, log)
    }

    fun logD(log: String) {
        Log.d(TAG, log)
    }
    fun logD(tag:String,log: String) {
        Log.d(tag, log)
    }

    fun logE(log: String) {
        Log.e(TAG, log)
    }
    fun logE(tag:String,log: String) {
        Log.e(tag, log)
    }

}