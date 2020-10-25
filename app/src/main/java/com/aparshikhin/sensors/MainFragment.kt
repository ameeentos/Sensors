package com.aparshikhin.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aparshikhin.sensors.helpers.Logger
import com.aparshikhin.sensors.state.PhoneState
import com.aparshikhin.sensors.state.State
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {

    private lateinit var mSensorManager: SensorManager
    private lateinit var mLinearAccelerationSensor: Sensor

    private val mAccelerationListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(sensorEvent: SensorEvent) {
            val x = sensorEvent.values[0]
            val y = sensorEvent.values[1]
            val z = sensorEvent.values[2]

            // todo: remove?
            coordinatesTextView.text = getString(R.string.coordinates, x, y ,z)

            if (y > 2 && x > 5.5 && x < 110) {
                PhoneState.getPhoneState().notifyStateChange(State.TiltLeft)
            } else if (y > 2 && x > -10 && x < -5.5) {
                PhoneState.getPhoneState().notifyStateChange(State.TiltRight)
            } else if (x > 8.8 || x < -8.8 || y < 2) {
                PhoneState.getPhoneState().notifyStateChange(State.Downwards)
            } else {
                PhoneState.getPhoneState().notifyStateChange(State.Straight)
            }
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            Logger.debug("onAccuracyChanged")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeSensorDependencies()
    }

    override fun onStart() {
        super.onStart()
        registerLinearAccelerationListener()
    }

    override fun onStop() {
        super.onStop()
        unregisterLinearAccelerationListener()
    }

    private fun initializeSensorDependencies() {
        try {
            mSensorManager =
                requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
            mLinearAccelerationSensor =
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            Logger.debug("SensorManager was created")
        } catch (exception: Exception) {
            Logger.error(exception, "SensorManager didn't created")
        }
    }

    private fun registerLinearAccelerationListener() {
        mSensorManager.registerListener(
            mAccelerationListener,
            mLinearAccelerationSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        Logger.debug("Listener was register")
    }

    private fun unregisterLinearAccelerationListener() {
        mSensorManager.unregisterListener(mAccelerationListener, mLinearAccelerationSensor)
        Logger.debug("Listener was unregister")
    }
}