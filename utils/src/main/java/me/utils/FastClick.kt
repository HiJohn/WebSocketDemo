package me.utils

import kotlin.math.abs

/**
 */
object FastClick {
    private const val INTERVAL: Long = 500L

    private var listTime: Long = 0

    fun canClick(): Boolean {
        val now = System.currentTimeMillis()
        if (abs(now - listTime) > INTERVAL) {
            listTime = now
            return true
        }
        return false
    }

    fun fastClick():Boolean{
        val now = System.currentTimeMillis()
        if (abs(now - listTime) < INTERVAL) {
            return false
        }
        listTime = now
        return true
    }


}