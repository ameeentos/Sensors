package com.aparshikhin.sensors.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import com.aparshikhin.sensors.helpers.Logger

class LinearAccelerationSensorEventListener : SensorEventListener {
    override fun onSensorChanged(sensorEvent: SensorEvent) {
        val x = sensorEvent.values[0]
        if (x > 5 || x < -5) {
            Logger.debug("onSensorChanged: $x")
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        Logger.debug("onAccuracyChanged")
    }
}