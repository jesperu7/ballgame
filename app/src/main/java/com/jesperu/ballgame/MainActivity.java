package com.jesperu.ballgame;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;


public class MainActivity extends AppCompatActivity implements SensorEventListener{
    public static final int RESULT_CODE = 0;
    private static int MIN_ACC;

    private Sensor mySensor;
    private SensorManager sensorManager;
    private TextView xText, yText, zText;
    private Button settingsButton;


    @Override
    public void onSensorChanged(SensorEvent event) {
        xText.setText("X: " + event.values[0]);
        yText.setText("Y: " + event.values[1]);
        zText.setText("Z: " + event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // usen't
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sensor Manager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Accelerometer sensor
        mySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register sensorlistener
        sensorManager.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_GAME);

        // Assign textviews
        xText = (TextView) findViewById(R.id.xText);
        yText = (TextView) findViewById(R.id.yText);
        zText = (TextView) findViewById(R.id.zText);


        settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == RESULT_CODE){
            if (resultCode == Activity.RESULT_OK){
                Bundle b = data.getExtras();
                String temp = b.get(SettingsActivity.SENSITIVITY).toString();
                MIN_ACC = Integer.parseInt(temp);

            }
        }
    }

    public void openSettings() {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivityForResult(i, RESULT_CODE);
    }


}
