package com.theartofbrian.missionelapsedtimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView timerTxt;
    Button startBtn;
    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;

    boolean timerStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTxt = (TextView) findViewById(R.id.timerTxt);
        startBtn = (Button) findViewById(R.id.startBtn);
        timer = new Timer();

    }

    public void resetActivated(View view) {
        AlertDialog.Builder resetAlert = new AlertDialog.Builder(this);
        resetAlert.setTitle(R.string.rstTimer);
        resetAlert.setMessage(R.string.rstTimerMsg);
        resetAlert.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(timerTask != null) {
                    timerTask.cancel();
                    setButtonUI(R.color.green, R.string.start);
                    time = 0.0;
                    timerStarted = false;
                    timerTxt.setText(formatTime(0,0,0));
                }
            }
        });

        resetAlert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        resetAlert.show();
    }

    public void startActivated(View view) {
        if (!timerStarted){
            timerStarted = true;
            setButtonUI(R.color.red, R.string.stop);

            startTimer();
        } else {
            timerStarted = false;
            setButtonUI(R.color.green, R.string.start);
            timerTask.cancel();
        }
    }

    private void setButtonUI(int p, int color) {
        startBtn.setTextColor(ContextCompat.getColor(this, p));
        startBtn.setText(color);
    }

    private void startTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        timerTxt.setText(getTimerText());
                    }
                });
            }
        };

        timer.scheduleAtFixedRate(timerTask, 0 , 1000);
    }

    private String getTimerText() {
        int rounded = (int) Math.round(time);
        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) % 3600);

        return formatTime(seconds, minutes, hours);
    }

    private String formatTime(int seconds, int minutes, int hours) {
        return String.format("%02d",hours) + " : " + String.format("%02d",minutes) + " : " + String.format("%02d",seconds);
    }
}