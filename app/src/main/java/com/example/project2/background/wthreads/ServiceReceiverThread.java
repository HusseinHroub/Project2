package com.example.project2.background.wthreads;

import android.content.Context;

import androidx.core.app.NotificationManagerCompat;

import com.example.project2.common.NotificationManagerr;

public class ServiceReceiverThread extends GeneralReceiver {


    private Context context;
    private static final String CHANNEL_ID = "threadchannel";

    private boolean isNotified10 = true;
    private boolean isNotNotified2 = true;
    private boolean isNotified5 = true;
    private boolean isNotNotified96 = true;


    public ServiceReceiverThread(Context context) {
        //inti
        this.context = context;
        NotificationManagerr.createNotificaitonChannel("Warning notifcation",
                "A warning notfication about water status",
                CHANNEL_ID,
                context);
        start();

    }

    @Override
    public void run() {
        super.run();
        System.out.println("service thread finished");
    }

    @Override
    public void onResultRec() {

        if (value > 96) {
            if (isNotNotified96) {
                isNotNotified96 = false;
                showNotificaiton("Tank about to be filled", "You have more than 96% of water");
            }
        } else if (value < 10 && value >= 5) {
            //test
            if (isNotified10) {
                isNotified10 = false;
                showNotificaiton("Water Warning", "You have less than 10% of water");
            }


            isNotified5 = true;
            //test
        } else if (value < 5 && value >= 2) {
            if (isNotified5) {
                isNotified5 = false;
                showNotificaiton("Water Warning", "You have less than 5% of water");
            }


            isNotNotified2 = true;

        } else if (value < 2) {
            if (isNotNotified2) {
                isNotNotified2 = false;
                showNotificaiton("Water Warning", "You have less than 2% of water");
            }
        } else {
            setAllNotified(true);
        }

        if (value < 90)
            isNotNotified96 = true;

    }

    private void showNotificaiton(String title, String warning) {

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(3, NotificationManagerr.getNotificationBuilder(context,
                CHANNEL_ID,
                title,
                warning,
                true).build());
    }


    private void setAllNotified(boolean b) {

        isNotified5 = b;
        isNotified10 = b;
        isNotNotified2 = b;
    }

}
