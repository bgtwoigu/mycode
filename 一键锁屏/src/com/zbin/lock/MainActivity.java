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
		if (policyManager.isAdminActive(componentName)) {// �ж��Ƿ���Ȩ��(�������豸������)
			Log.e("zbin###", "oncreate+22222222222222222222222222222222");
			policyManager.lockNow();// ֱ������
			cleanmemory();
			finish();
			android.os.Process.killProcess(android.os.Process.myPid());

		} else {
			activeManager();// �����豸��������ȡȨ��
		}

		setContentView(R.layout.activity_main);

	}

	/*
	 * public void LockScreen(View v){
	 * 
	 * }
	 */

	// �����
	public void Bind(View v) {
		Log.e("zbin###", "Bind+111111111111111111111111111111");
		if (componentName != null) {
			policyManager.removeActiveAdmin(componentName);
			activeManager();
		}
	}

	// ��д�˷��������ڵ�һ�μ����豸������֮��������Ļ
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
		// ʹ����ʽ��ͼ����ϵͳ����������ָ�����豸������
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "һ������");
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
			String[] pkNameList = Runninginfo.pkgList;// �����µ����а���

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
