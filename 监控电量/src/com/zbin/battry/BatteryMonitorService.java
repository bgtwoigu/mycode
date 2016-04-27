package com.zbin.battry;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class BatteryMonitorService extends Service {
	private String TAG = "zbinservice";
	BatteryBroadcastReciver batteryreciver;
	Notification notification;

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
		batteryreciver = new BatteryBroadcastReciver();
		notification=new Notification();
		notification.tickerText = "开始监控电量！！！！";
		notification.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e(TAG, "service1 onStartCommand");
		if (!isServiceWork(getBaseContext(), "com.zbin.battry.BatteryMonitorService2")) {
			Log.e("song", "service1 to start service2");
			Intent intent1 = new Intent(BatteryMonitorService.this, BatteryMonitorService2.class);
			startForeground(100, notification);
			startService(intent1);

		}
		// 创建一个过滤器
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(batteryreciver, intentFilter);
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
		unregisterReceiver(batteryreciver);
		Log.e("zbinservice", "解除广播");
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
