package com.snake.device;

import com.snake.render.ScreenView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelerometerController implements SensorEventListener {
	//Sensors for accelerometer
	SensorManager m_sensorManager;
    Sensor m_accelerometer;
    float mf_lastAccel = 0.0f;
	boolean mb_sensorInit = false;
	
	//ScreenView class
	ScreenView m_draw;
	
	public AccelerometerController(ScreenView _draw, SensorManager _sensorManager){
		m_draw = _draw;
		m_sensorManager = _sensorManager;
        m_accelerometer = m_sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        m_sensorManager.registerListener(this, m_accelerometer , SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	public void onResume() {
        m_sensorManager.registerListener(this, m_accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        m_sensorManager.unregisterListener(this);
    }

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// can be safely ignored for this demo
	}

	@Override
	public void onSensorChanged(SensorEvent event) {

	}
}
