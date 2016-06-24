package com;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.IBinder;

public class UnitApi extends Service{
	
	private WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
	
	public void hotSpot(boolean enable) {
		if (enable) {
			wifiManager.setWifiEnabled(false);
		}
		
//		WifiConfiguration ap = new WifiConfiguration();
		try {
			Method method = wifiManager.getClass().getMethod("setWifiApEnabled", 
					WifiConfiguration.class, Boolean.TYPE);
			method.invoke(wifiManager, enable);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
