package com.example.project2.background;

import android.content.Context;

import androidx.core.app.NotificationManagerCompat;

import com.example.project2.common.NotificationManagerr;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ImportantThread extends Thread {


//    private InetAddress localAddress;
//    private DatagramSocket datagramSocket;
//    private DatagramPacket datagramPacket;
    private Context context;
//    private byte[] buf = new byte[1024];
    private int i;
    private static final String CHANNEL_ID = "threadchannel";
    private boolean running;

    public ImportantThread(Context context) {
        //inti
        this.context = context;
        i = -1;
        NotificationManagerr.createNotificaitonChannel("Warning notifcation",
                "A warning notfication about water status",
                CHANNEL_ID,
                context);
        running = true;
        start();

    }

    @Override
    public void run() {

        while (running) {
            try {
                Thread.sleep(5000);
                showNotificaiton(++i);
                System.out.println(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }


    }

    private void showNotificaiton(int i) {

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(3, NotificationManagerr.getNotificationBuilder(context,
                CHANNEL_ID,
                "Thread notfication",
                "i is: " + i,
                true).build());
    }

    public void stopRunning() {
        running = false;
    }


}
