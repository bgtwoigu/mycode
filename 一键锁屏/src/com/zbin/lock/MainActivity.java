package com.zbin.lock;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	private DevicePolicyManager policyManager;

	private ComponentName componentName;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Log.e("zbin###", "oncreate+111111111111111111111111111111");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		componentName = new ComponentName(this, LockReceiver.class);
		if (policyManager.isAdminActive(componentName)) {// 判断是否有权限(激活了设备管理器)
			Log.e("zbin###", "oncreate+22222222222222222222222222222222");
			policyManager.lockNow();// 直接锁屏
			cleanmemory();
			finish();
			android.os.Process.killProcess(android.os.Process.myPid());

		} else {
			activeManager();// 激活设备管理器获取权限
		}

		setContentView(R.layout.activity_main);

	}

	/*
	 * public void LockScreen(View v){
	 * 
	 * }
	 */

	// 解除绑定
	public void Bind(View v) {
		Log.e("zbin###", "Bind+111111111111111111111111111111");
		if (componentName != null) {
			policyManager.removeActiveAdmin(componentName);
			activeManager();
		}
	}

	// 重写此方法用来在第一次激活设备管理器之后锁定屏幕
	@Override
	protected void onResume() {
		if (policyManager != null && policyManager.isAdminActive(componentName)) {
			policyManager.lockNow();
			Log.e("zbin###", "onResume+111111111111111111111111111111");
			android.os.Process.killProcess(android.os.Process.myPid());
		}
		else {
			finish();
			android.os.Process.killProcess(android.os.Process.myPid());
			Log.e("zbin###", "onResume+222222222222222222222222222222");
		}
		super.onResume();
	}

	private void activeManager() {
		// 使用隐式意图调用系统方法来激活指定的设备管理器
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "一键锁屏");
		startActivity(intent);

	}

	public void cleanmemory() {
		ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> RunninginfoList = am
				.getRunningAppProcesses();
		/*
		 * List<ActivityManager.RunningServiceInfo> serviceInfos = am
		 * .getRunningServices(100);
		 */

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
	protected void onPause() {
		Log.e("zbin###", "onPause+111111111111111111111111111111");
		super.onPause();
	}
	@Override
	protected void onStop() {
		Log.e("zbin###", "onStop+111111111111111111111111111111");
		super.onStop();
	}
	@Override
	protected void onDestroy() {

		super.onDestroy();

	}

}
