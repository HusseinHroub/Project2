package com.example.project2.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BoradCastRec extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent1 = new Intent(context, MyService.class);
        context.stopService(intent1);
    }
}
