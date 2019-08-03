package com.example.project2.background;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.project2.R;
import com.example.project2.background.wthreads.ServiceReceiverThread;
import com.example.project2.common.NotificationManagerr;

public class MyService extends Service {
    private static final String CHANNEL_ID = "servicechannel";
    private final int FOR_GROUND_ID = 1;
    private ServiceReceiverThread serviceReceiverThread;


    // Handler that receives messages from the thread


    @Override
    public void onCreate() {

        setInForGround();
        System.out.println("onCreate()");


        if (serviceReceiverThread == null) {
            serviceReceiverThread = new ServiceReceiverThread(getApplicationContext());
            System.out.println("starting thread");
        }


    }

    private void setInForGround() {
        NotificationManagerr.createNotificaitonChannel("Listening notifcation",
                "if this notifcaiton is on, this means that a listening background service is working",
                CHANNEL_ID,
                getApplicationContext());
        NotificationCompat.Builder notificationBuilder = NotificationManagerr.getNotificationBuilder(getApplicationContext(),
                CHANNEL_ID,
                "Listenintg",
                "ok I am listening",
                true);


        //intent to handle the close button
        Intent endService = new Intent(getApplicationContext(), BoradCastRec.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                1,
                endService,
                PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.addAction(R.drawable.wave_icon, "Cancle test", pendingIntent);
        startForeground(FOR_GROUND_ID, notificationBuilder.build());


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return START_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }


    @Override
    public void onDestroy() {

        Toast.makeText(this, "service stopped", Toast.LENGTH_SHORT).show();
        System.out.println("called onServiceDestroy()");
        serviceReceiverThread.stopThread();


//        stopSelf();
        System.out.println("finished on destroy");
        sendBroadcastForActivity();
    }

    private void sendBroadcastForActivity() {

        Intent in = new Intent();
        in.putExtra("stopped", true);
        in.setAction("STATUS");
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(in);
    }


    //V@1
//    private void setInForGround() {
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent =
//                PendingIntent.getActivity(this, 0, notificationIntent, 0);
//
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            String Channel_Id = createNotificationChannel("gps_updater", "gps_service");
//            Notification notification =
//                    new Notification.Builder(this, Channel_Id)
//                            .setContentTitle("Gps App")
//                            .setContentText("Updating Gps")
//                            .setSmallIcon(android.R.drawable.sym_def_app_icon)
//                            .setContentIntent(pendingIntent)
////                            .setTicker("test")
//                            .build();
//
//            startForeground(FOR_GROUND_ID, notification);
//        }
//
//
//    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private String createNotificationChannel(String channelId, String channelName) {
//        NotificationChannel channel = new NotificationChannel(channelId,
//                channelName, NotificationManager.IMPORTANCE_HIGH);
//
////        channel.setLightColor(Color.BLUE);
////        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
//        NotificationManager service = getSystemService(NotificationManager.class);
//        service.createNotificationChannel(channel);
//
//        return channelId;
//    }


}