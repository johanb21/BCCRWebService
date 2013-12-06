package com.localservicetier.bccrwebservice;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoStartReceiver extends BroadcastReceiver {

    private NotificationReceiver alarm;

    public AutoStartReceiver() {
    	alarm = new NotificationReceiver();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
		    alarm.setNotificationAlarm(context);
		}
    }
}
