package com.hodinv.music.screens.player

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.util.Log
import android.view.Surface

/**
 * Created by vasily on 23.07.17.
 */
class SensorLogic(val updateRotation: (Int) -> Unit) : SensorEventListener {
    private var value_0 = -10000f
    private var value_1 = -10000f
    private var orientation = -1
    override fun onSensorChanged(event: SensorEvent) {
        var value: Int
        if (value_0 == event.values[0] && value_1 == event.values[1]) {
            return
        }
        //            Log.d("values:", "values:" + event.values[0]+", "+event.values[1]);
        if (event.values[1] > 0 && Math.abs(event.values[0]) <= 0.1f) {
            value = Surface.ROTATION_0//portrait
            if (orientation != value) {
                updateRotation(value)
                Log.d("orientation", "portrait  + update")
            }
            orientation = value
            Log.d("orientation", "portrait ")
        }


        if (event.values[1] < 0 && Math.abs(event.values[0]) <= 0.1f) {
            value = Surface.ROTATION_180//portrait reverse
            if (orientation != value) {
                updateRotation(value)
                Log.d("orientation", "portrait reverse + update")
            }
            orientation = value
            Log.d("orientation", "portrait reverse")
        }

        if (event.values[0] > 0 && Math.abs(event.values[1]) <= 0.1f) {
            value = Surface.ROTATION_90//portrait reverse
            if (orientation != value) {
                updateRotation(value)
                Log.d("orientation", "landscape  + update")
            }
            orientation = value
            Log.d("orientation", "landscape")
        }

        if (event.values[0] < 0 && Math.abs(event.values[1]) <= 0.1f) {
            value = Surface.ROTATION_270//portrait reverse
            if (orientation != value) {
                updateRotation(value)
                Log.d("orientation", "landscape  + update")
            }
            orientation = value
            Log.d("orientation", "landscape reverse")
        }

        value_0 = event.values[0]
        value_1 = event.values[1]
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }

}