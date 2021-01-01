package com.example.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int timeToCountdown = 0;
    boolean isStarted = false;
    CountDownTimer countDownTimer = null;
    TextView textView;
    Button button;
    SeekBar seekBar;

    public void resetTimer() {
        isStarted = false;
        System.out.println(countDownTimer);
        countDownTimer.cancel();
        seekBar.setProgress(30);
        seekBar.setEnabled(true);
        button.setText("GO!");
    }

    public void startTimer(View view) {
        button = findViewById(R.id.button);
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.sound);
        if (isStarted) {
            resetTimer();
        } else {
            isStarted = true;
            button.setText("STOP");
            countDownTimer = new CountDownTimer(timeToCountdown * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    Log.i("timer", String.valueOf(l / 1000));
                    textView.setText(String.valueOf(l / 1000));
                    seekBar.setEnabled(false);

                }

                @Override
                public void onFinish() {
                    Log.i("end", "count down finished");
                    mediaPlayer.start();

                    resetTimer();
                }
            }.start();
        }
    }

    public void updateTimer(int i) {
        String message;
        int minutes = i / 60;
        int seconds = i - (minutes * 60);
        if (seconds == 0) {
            message = minutes + ":" + seconds + "0";

        } else if (seconds >= 1 && seconds < 10) {
            message = minutes + ":0" + seconds;

        } else {
            message = minutes + ":" + seconds;

        }
        textView.setText(message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        seekBar = findViewById(R.id.seekBar);

        seekBar.setMax(500);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                timeToCountdown = i;
//                Log.i("seekbar", Integer.toString(timeToCountdown));
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}