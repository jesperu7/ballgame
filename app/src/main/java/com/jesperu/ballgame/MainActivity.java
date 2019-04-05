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
    private int first = 0;
    private int second = 0;
    private int third = 0;
    private int score;

    private TextView p1, p2, p3;

    // What to do when the sensor catches movement
    @Override
    public void onSensorChanged(SensorEvent event) {
        TextView yourScore;
        yourScore = findViewById(R.id.yourScore);

        if (event.values[0] >= MIN_ACC || event.values[1] >= MIN_ACC || event.values[2] >= MIN_ACC){
            // start game
        }

        // score = length of the throw
        yourScore.setText("You scored: " + Integer.toString(score));
        checkHighScore();
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
        SensorManager sensorManager;
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Accelerometer sensor
        Sensor mySensor;
        mySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register sensor listener
        sensorManager.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_GAME);

        // Outputs high scores
        p1 = findViewById(R.id.firstPlace);
        p2 = findViewById(R.id.secondPlace);
        p3 = findViewById(R.id.thirdPlace);

        p1.setText(Integer.toString(first));
        p2.setText(Integer.toString(second));
        p3.setText(Integer.toString(third));


        // initiate button to settings
        Button settingsButton;
        settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });

    }

    // Assigns the set MIN_ACC chosen in settings
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

    // Opens SettingsActivity
    public void openSettings() {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivityForResult(i, RESULT_CODE);
    }

    // checks if the score goes on the scoreboard
    public void checkHighScore() {
        if (score >= first) {
            third = second;
            second = first;
            first = score;
        } else if (score >= second){
            third = second;
            second = score;
        } else if (score >= third) {
            third = score;
        }

        // outputs new high scores
        p1.setText("1. " + Integer.toString(first));
        p2.setText("2. " + Integer.toString(second));
        p3.setText("3. " + Integer.toString(third));
    }

}
