package com.zk.trackshows.common

import android.util.Log

object InfoLogger {

    private const val TAG = "InfoLogger"

    fun logMessage(message: String) {
        Log.i(TAG, message)
    }
}