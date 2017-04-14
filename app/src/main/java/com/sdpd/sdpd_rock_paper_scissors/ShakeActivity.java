package com.sdpd.sdpd_rock_paper_scissors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class ShakeActivity extends AppCompatActivity implements SensorEventListener {
    TextView result, shakes;
    ImageView image_result;
    int number_of_shakes = 0;
    // variables for shake detection
    private static final float SHAKE_THRESHOLD = 1.5f; // m/S**2
    private static final int MIN_TIME_BETWEEN_SHAKES_MILLISECS = 200;
    private long mLastShakeTime;
    private SensorManager mSensorMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);

        result = (TextView) findViewById(R.id.result);
        shakes = (TextView) findViewById(R.id.shakes);
        image_result=(ImageView)findViewById(R.id.Image_Result);
        // Get a sensor manager to listen for shakes
        mSensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Listen for shakes
        Sensor accelerometer = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            mSensorMgr.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    void shakepp() {
        number_of_shakes += 1;
        shakes.setText(String.valueOf(number_of_shakes));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            if ((curTime - mLastShakeTime) > MIN_TIME_BETWEEN_SHAKES_MILLISECS) {

                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                double acceleration = Math.sqrt(Math.pow(x, 2) +
                        Math.pow(y, 2) +
                        Math.pow(z, 2)) - SensorManager.GRAVITY_EARTH;
//                Log.d(APP_NAME, "Acceleration is " + acceleration + "m/s^2");

                if (acceleration > SHAKE_THRESHOLD) {
                    mLastShakeTime = curTime;
//                    Log.d(APP_NAME, "Shake, Rattle, and Roll");
                    shakeHappened();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Ignore
    }

    void shakeHappened() {
        int i = new Random().nextInt(3);
        switch (i) {
            case 0:{
                result.setText("Rock");
                image_result.setImageResource(R.mipmap.rock);
                break;
            }
            case 1: {
                result.setText("Paper");
                image_result.setImageResource(R.mipmap.paper);
                break;
            }
            case 2: {
                result.setText("Scissors");
                image_result.setImageResource(R.mipmap.scissors);
                break;
            }
        }
        shakepp();
    }

    void stop() {
        mSensorMgr.unregisterListener(this);
    }
}
