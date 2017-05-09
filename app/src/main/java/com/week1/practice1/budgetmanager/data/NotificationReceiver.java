package com.week1.practice1.budgetmanager.data;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by dev7 on 9.5.17..
 */

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getStringExtra("msg").equals("CANCEL")) {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancel(0);
        }

        if(intent.getStringExtra("msg").equals("ADD")) {

        }
    }
}
