package com.aparshikhin.sensors.helpers

import android.util.Log

class Logger {

    companion object {
        private const val TAG = "SensorTag"

        public fun debug(message:String) {
            Log.d(TAG, message)
        }

        public fun info(message:String) {
            Log.i(TAG, message)
        }
    }
}