package com.localservicetier.bccrwebservice;

import com.activitytier.bccrwebservice.R;
import com.logicaltier.bccrwebservice.TipoCambioWebService;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		PowerManager powerManager = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, NotificationReceiver.class.getSimpleName());
		try {
			wakeLock.acquire();
			notify(context);
		} catch(Exception e) {
			Log.e("onReceive e", e.toString());
		} finally {
			wakeLock.release();
		}
	}

	public void setNotificationAlarm(Context context) {
		int minutos = (1000 * 60) * 1;
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, NotificationReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + minutos, minutos, pendingIntent);
	}

	public void notify(Context context) {
		String[] tipoCambios = null;
		try {
			tipoCambios = new ConsumeWebServiceTask().execute().get();
		} catch (Exception e) {
			Log.e("notify e", e.toString());
		}
		Notification notification = new NotificationCompat.Builder(context)
        .setContentTitle("Tipo de cambio BCCR")
        .setContentText("Compra: " + tipoCambios[0] + " Venta: " + tipoCambios[1])
        .setSmallIcon(R.drawable.ic_launcher)
        .build();
	    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	    notification.flags |= Notification.FLAG_AUTO_CANCEL;
	    notification.defaults |= Notification.DEFAULT_SOUND;
	    notificationManager.notify(0, notification);
		
	}

	private class ConsumeWebServiceTask extends AsyncTask<Void, Void, String[]> {
	
		@Override
		protected String[] doInBackground(Void... params) {
			String[] result = {TipoCambioWebService.getInstance().obtainTipoCambioVenta(), TipoCambioWebService.getInstance().obtainTipoCambioCompra()};
			return result;
		}
	}
}
