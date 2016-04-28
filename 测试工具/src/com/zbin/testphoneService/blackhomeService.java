package com.zbin.testphoneService;

import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;
import com.zbin.dao.BlackNumberDao;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class blackhomeService extends Service {
	private TelephonyManager telephonymanager;
	PhoneStateListener listener;
	private String incomingNumber;
	private BlackNumberDao dao;

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	@Override
	public void onCreate() {
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		telephonymanager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		listener = new MyPhoneListener();
		telephonymanager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		dao = new BlackNumberDao(this);
		super.onCreate();

	}

	private class MyPhoneListener extends PhoneStateListener {

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
				String mode = dao.findMode(incomingNumber);
				if ("1".equals(mode))
					endcall();

				break;

			}
		}

	}

	@Override
	public void onDestroy() {

		super.onDestroy();
		Intent localIntent = new Intent();
		localIntent.setClass(this, blackhomeService.class); // 销毁时重新启动Service
		this.startService(localIntent);
	}

	/**
	 * 挂断电话
	 */
	public void endcall() {
		// ServiceManager.getService(TELEPHONY_SERVICE);
		try {
			Class clazz = callservice.class.getClassLoader().loadClass("android.os.ServiceManager");
			Method method = clazz.getDeclaredMethod("getService", String.class);
			IBinder b = (IBinder) method.invoke(null, TELEPHONY_SERVICE);
			// 获取到了原生未经包装的系统电话的管理服务。
			ITelephony iTelephony = ITelephony.Stub.asInterface(b);
			iTelephony.endCall();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
