package com.zbin.battry;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class BatteryMonitorService2 extends Service {
	private String TAG = "zbinservice";
	BatteryBroadcastReciver batteryreciver;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.e(TAG, "onCreate");
		// batteryreciver = new BatteryBroadcastReciver();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e(TAG, "Service2 onStartCommand");
		if (!isServiceWork(getBaseContext(), "com.zbin.battry.BatteryMonitorService")) {
			Intent intent2 = new Intent(BatteryMonitorService2.this, BatteryMonitorService.class);
			startService(intent2);
			Log.e("zbinService2", "service2222222222222222");
		}

		return super.onStartCommand(intent, flags, startId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.e(TAG, "onDestroy");
		// unregisterReceiver(batteryreciver);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onUnbind(android.content.Intent)
	 */
	@Override
	public boolean onUnbind(Intent intent) {
		Log.e(TAG, "onUnbind");
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.e(TAG, "onBind");
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 判断某个服务是否正在运行的方法
	 * 
	 * @param mContext
	 * @param serviceName
	 *            是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
	 * @return true代表正在运行，false代表服务没有正在运行
	 */
	public boolean isServiceWork(Context mContext, String serviceName) {
		boolean isWork = false;
		ActivityManager myAM = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> myList = myAM.getRunningServices(100);
		if (myList.size() <= 0) {
			return false;
		}
		for (int i = 0; i < myList.size(); i++) {
			String mName = myList.get(i).service.getClassName().toString();
			if (mName.equals(serviceName)) {
				isWork = true;
				break;
			}
		}
		return isWork;
	}

}
