package com.zbin.lock;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class Cleanmermory extends Service {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> RunninginfoList = am
				.getRunningAppProcesses();
		List<ActivityManager.RunningServiceInfo> serviceInfos = am
				.getRunningServices(100);
		
		for (RunningAppProcessInfo Runninginfo : RunninginfoList) {

			if (Runninginfo.processName.contains("com.android.system")
					|| Runninginfo.pid == android.os.Process.myPid())
				continue;
			String[] pkNameList = Runninginfo.pkgList;// 进程下的所有包名

			for (int i = 0; i < pkNameList.length; i++) {

				String packageName = pkNameList[i];
				am.killBackgroundProcesses(packageName);
			}

		}

	}

	@Override
	public void onDestroy() {

		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}