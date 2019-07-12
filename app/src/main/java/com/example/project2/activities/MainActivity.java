package com.example.project2.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.project2.R;
import com.example.project2.background.MyService1;
import com.example.project2.common.NotificationManagerr;
import com.yuan.waveview.WaveView;


public class MainActivity extends AppCompatActivity {

    private WaveView waveView;
    private TextView textView;
    private static final String CHANNEL_ID = "mainchannel";
    private boolean isNotified10 = true;
    private boolean isNotNotified2 = true;
    private boolean isNotified5 = true;
    private boolean isNotNotified96 = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotificationManagerr.createNotificaitonChannel("Testing notfication",
                "A tempororay notifcaiton for testing purposes, will be removed in full release.",
                CHANNEL_ID,
                getApplicationContext());
//        waveLoadingView = findViewById(R.id.waveView);
//        waveLoadingView.setAnimation();
//        waveLoadingView.setProgressValue(1);
        //waveLoadingView.setAnimDuration(3000);
        textView = findViewById(R.id.textView);
        waveView = findViewById(R.id.waveview);
        waveView.setbgColor(Color.WHITE);
        waveView.setWaveColor(ContextCompat.getColor(this, R.color.blueC));

        //Shape
        waveView.setMode(WaveView.MODE_CIRCLE);

        //Set default water value
        waveView.setProgress(1);
        //Set maximum value
        waveView.setMax(101);


        ((SeekBar) findViewById(R.id.seekBar)).
                setOnSeekBarChangeListener(new SeekBarChangeHandling());


//        Intent intent = new Intent(this, MyService.class);
//        startService(intent);


        System.out.println("starting service");
        Intent serviceIntent = new Intent(MainActivity.this, MyService1.class);
        ContextCompat.startForegroundService(MainActivity.this, serviceIntent);
//        startService(serviceIntent);


    }


    private class SeekBarChangeHandling implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//            waveLoadingView.setProgressValue(progress);

            waveView.setProgress(progress);

            textView.setText(progress + "%");


            if (progress > 96) {
                if (isNotNotified96) {
                    isNotNotified96 = false;
                    showNotfication("Tank about to be filled", "You have more than 96% of water");
                }
            } else if (progress < 10 && progress >= 5) {
                //test
                if (isNotified10) {
                    isNotified10 = false;
                    showNotfication("Water Warning", "You have less than 10% of water");
                }


                isNotified5 = true;
                //test
            } else if (progress < 5 && progress >= 2) {
                if (isNotified5) {
                    isNotified5 = false;
                    showNotfication("Water Warning", "You have less than 5% of water");
                }


                isNotNotified2 = true;

            } else if (progress < 2) {
                if (isNotNotified2) {
                    isNotNotified2 = false;
                    showNotfication("Water Warning", "You have less than 2% of water");
                }
            } else {
                setAllNotified(true);
            }

            if (progress < 90)
                isNotNotified96 = true;

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // waveLoadingView.setAnimDuration(1000);
            System.out.println("?");

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

            System.out.println("?? :/");

        }
    }

    private void setAllNotified(boolean b) {
        System.out.println("CALLLED");
        isNotified5 = b;
        isNotified10 = b;
        isNotNotified2 = b;
    }


    private void showNotfication(String title, String message) {

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
        notificationManager.notify(2, NotificationManagerr.getNotificationBuilder(this,
                CHANNEL_ID,
                title,
                message,
                false).build());
    }

    public void stopService(View v) {
        Intent serviceIntent = new Intent(this, MyService1.class);
        stopService(serviceIntent);
    }
}
