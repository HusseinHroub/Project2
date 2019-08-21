package com.example.project2.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.project2.R;
import com.example.project2.background.MyService;
import com.example.project2.background.wthreads.UiReceiverThread;
import com.yuan.waveview.WaveView;


public class MainActivity extends AppCompatActivity {

    private WaveView waveView;
    private TextView textView;

    private boolean noBroadCast = false;

    private UiReceiverThread receiverThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.levelTextWave);
        textView.setText("50%");

        waveView = findViewById(R.id.waveview);
        waveView.setbgColor(Color.WHITE);
        waveView.setWaveColor(ContextCompat.getColor(this, R.color.blueC));
        //Shape
        waveView.setMode(WaveView.MODE_CIRCLE);

        //Set default water value
        waveView.setProgress(50);
        waveView.setSpeed(WaveView.SPEED_NORMAL);
        //Set maximum value
        waveView.setMax(102);



    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(MainActivity.this).unregisterReceiver(serviceReciver);

        if (receiverThread != null)
            receiverThread.stopThread();
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
        super.onPause();
    }

    @Override
    protected void onResume() {
        Intent serviceIntent = new Intent(this, MyService.class);
        System.out.println("Called stopService");
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(serviceReciver, new IntentFilter("STATUS"));

        if (!stopService(serviceIntent))
            initUiThreads();


        super.onResume();
    }

    private void initUiThreads() {
        System.out.println("back to onResume to make UI threads");
        receiverThread = new UiReceiverThread(textView, waveView);
    }

    private BroadcastReceiver serviceReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean stopped = intent.getBooleanExtra("stopped", false);  //get the type of message from MyGcmListenerService 1 - lock or 0 -Unlock

            if (stopped)
                initUiThreads();
            System.out.println("stopped:" + stopped);
            noBroadCast = true;
        }
    };

}
