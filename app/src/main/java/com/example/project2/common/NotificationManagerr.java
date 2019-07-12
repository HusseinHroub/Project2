package com.example.project2.common;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.project2.activities.MainActivity;
import com.example.project2.R;

public class NotificationManagerr {


    private NotificationManagerr() {

    }

    public static void createNotificaitonChannel(CharSequence name, String description, String channelId, Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static NotificationCompat.Builder getNotificationBuilder(Context context, String CHANNEL_ID, String title, String contentText, boolean clickable) {
        NotificationCompat.Builder notficationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
        notficationBuilder.setSmallIcon(R.drawable.wave_icon);
        notficationBuilder.setContentTitle(title);
        notficationBuilder.setContentText(contentText);
        notficationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);

        if(clickable)
        {
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent mainActivityIntent = PendingIntent.getActivity(context, 0 , intent, 0);
            notficationBuilder.setContentIntent(mainActivityIntent);
        }
        return notficationBuilder;
    }
}
