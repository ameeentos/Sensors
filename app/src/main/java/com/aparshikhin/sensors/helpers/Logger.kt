package com.aparshikhin.sensors.helpers

import android.util.Log
import java.lang.Exception

class Logger {

    companion object {
        private const val TAG = "SensorTag"

        fun debug(message: String) {
            Log.d(TAG, message)
        }

        fun info(message: String) {
            Log.i(TAG, message)
        }

        fun error(exception: Exception, message: String) {
            Log.e(TAG, "$message with exception: $exception.message")
        }
    }
}