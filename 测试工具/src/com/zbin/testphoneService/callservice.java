package com.zbin.testphoneService;

import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

public class callservice extends Service {

	@Override
	public void onCreate() {
		
		super.onCreate();
		endcall();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 清除呼叫记录
	 * @param incomingNumber
	 */
	public void deleteCalllog(String incomingNumber) {
		ContentResolver resolver = getContentResolver();
		Uri url = Uri.parse("content://call_log/calls");
		resolver.delete(url, "number=?", new String[]{incomingNumber});
	}

	/**
	 * 挂断电话
	 */
	public void endcall() {
		//ServiceManager.getService(TELEPHONY_SERVICE);
		try {
			Class clazz = 	callservice.class.getClassLoader().loadClass("android.os.ServiceManager");
			Method method = clazz.getDeclaredMethod("getService", String.class);
			IBinder b  = (IBinder) method.invoke(null, TELEPHONY_SERVICE); 
			//获取到了原生未经包装的系统电话的管理服务。
			ITelephony iTelephony = ITelephony.Stub.asInterface(b);
			iTelephony.endCall();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	

}
