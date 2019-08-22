package com.example.project2.background.wthreads;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import com.yuan.waveview.WaveView;

public class UiReceiverThread extends GeneralReceiver {

    private Handler handler;
    private final TextView textView;
    private final WaveView waveView;

    public UiReceiverThread(TextView textView, WaveView waveView) {
        super();
        handler = new Handler(Looper.getMainLooper());
        this.textView = textView;
        this.waveView = waveView;
        start();
        sendWhoIsArduino();
    }

    @Override
    public void run() {
        super.run();
        System.out.println("uiReceiver finished");
    }

    @Override
    public void onResultRec() {
        handler.post(mainRunnable);
    }

    private Runnable mainRunnable = new Runnable() {
        @Override
        public void run() {
            textView.setText("" + value + '%');
            System.out.println("value is: " + value);
            waveView.setSpeed(WaveView.SPEED_NORMAL);
            waveView.setProgress(value + 1);
        }
    };
}
