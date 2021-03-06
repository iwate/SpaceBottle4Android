package com.spacebottle.controllers;

import java.net.MalformedURLException;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.spacebottle.models.Positions;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class BackendService extends Service {
	private final long SECOND = 1000;
	private final long MINUTE = 60 * SECOND;
	private final long HOUR = 60 * MINUTE;
	private final String TAG = "BackendService";
	
	protected MobileServiceClient mClient;
	private Positions mPositions;

	protected final IBinder binder = new Binder() {
        @Override
        protected boolean onTransact( int code, Parcel data, Parcel reply, int flags ) throws RemoteException
        {
            return super.onTransact(code, data, reply, flags);
        }
    };

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	public BackendService start(Context context){
		Intent intent = new Intent(context, this.getClass());
        intent.putExtra("type", "start");
        context.startService(intent);

		return this;
	}
	
	@Override
	public void onCreate(){
		//
		/*try {
			mClient = new MobileServiceClient(
					"https://spacebottle.azure-mobile.net/",
					"NIxTTzFiVmtUptmvETPKInerCKgRub76",
					this);
		} catch (MalformedURLException e) {
			Log.d(TAG, e.toString());
		}*/
	}
	
	@Override
    public int onStartCommand(Intent intent,int flags, int startId) {

		updatePosition();
		
		setAlarm(HOUR);

        return START_STICKY;
    }
	
	private void setAlarm(long interval){
		long now = System.currentTimeMillis();

        PendingIntent alarmSender = PendingIntent.getService(
            this,
            0,
            new Intent(this, this.getClass()),
            0
        );
        AlarmManager am = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        am.set(
            AlarmManager.RTC,
            now + interval,
            alarmSender
        );
	}
	
	private void updatePosition(){
		// TODO 位置情報の更新をする。どうやって、ログインするかが課題
	}
}
