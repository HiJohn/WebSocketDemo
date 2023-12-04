package me.utils

import android.util.Log

/**
 *文件名：Logs
 *创建者：gaozhipeng
 *功能模块：
 *创建时间：2023/11/13 16:01
 *修改时间：
 *描述：
 **/
object Logs {
    const val TAG  = "barry"

    fun logI(log:String){
        Log.i(TAG,log)
    }
    fun logD(log:String){
        Log.d(TAG,log)
    }

    fun logE(log:String){
        Log.e(TAG,log)
    }

}