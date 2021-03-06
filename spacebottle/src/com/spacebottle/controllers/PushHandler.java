package com.spacebottle.controllers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.spacebottle.R;
import com.microsoft.windowsazure.notifications.NotificationsHandler;

public class PushHandler extends NotificationsHandler {
	private static final int NOTIFICATION_ID = 2014;
	@com.google.gson.annotations.SerializedName("handle")
	private static String mHandle;

	public static String getHandle() {
	    return mHandle;
	}

	public static final void setHandle(String handle) {
	    mHandle = handle;
	}
	
	@Override
	public void onRegistered(Context context, String gcmRegistrationId) {
	    super.onRegistered(context, gcmRegistrationId);
	    setHandle(gcmRegistrationId);
	}
	
	@Override
	public void onReceive(Context context, Bundle bundle) {
		super.onReceive(context, bundle);
	    NotificationManager mNotificationManager;
	    mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
	    
	    PendingIntent mPendingIntent = PendingIntent.getActivity(context, 0,
	    		new Intent(context, HomeActivity.class),
	    		PendingIntent.FLAG_UPDATE_CURRENT);
	    Notification mNotification;
	    mNotification = new Notification.BigTextStyle(
	    	    new Notification.Builder(context)
	            .setContentTitle("SpaceBottle")
	            .setSmallIcon(R.drawable.ic_launcher)
	            .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher))
	            .addAction(android.R.drawable.ic_menu_send, "Send message", mPendingIntent)
	    		)
	        .bigText("BigText")
	    //  .setBigContentTitle("BigContentTitle") // Notification.Builder#setContentTitle() ���㏑��
	        .setSummaryText(bundle.getString("message"))
	        .build();
	    
	    mNotification.defaults |= Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS;
	    mNotificationManager.notify(NOTIFICATION_ID, mNotification);
    }
}
