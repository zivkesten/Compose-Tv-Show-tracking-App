package com.zk.trackshows.common

import android.util.Log

object InfoLogger {

    private const val TAG = "InfoLogger"
    private const val DEBUG = 0
    private const val VERBOSE = 1
    private const val ERROR = 2
    private const val INFO = 3
    private const val WARN = 4

    fun logMessage(message: String, logType: Int? = 0) {
        when (logType) {
            DEBUG -> Log.d(TAG, message)
            VERBOSE -> Log.v(TAG, message)
            ERROR -> Log.e(TAG, message)
            INFO -> Log.i(TAG, message)
            WARN -> Log.w(TAG, message)
        }
    }
}