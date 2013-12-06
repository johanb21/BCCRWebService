package com.localservicetier.bccrwebservice;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class NotificationService extends Service {

	private NotificationReceiver alarm;
	@Override
    public void onCreate() {
		alarm = new NotificationReceiver();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	alarm.setNotificationAlarm(getApplicationContext());
		return START_NOT_STICKY;
    }

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
