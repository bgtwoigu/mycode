package com.oneplus.camera.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.usb.UsbManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	public Button start, stop;
	EditText editCount, editRecorder,phoneNumberText,smsContentText;

	public int cishu, totalTime;
	public boolean isStop = false;
	public TextView show;

	MonkeyTouch touch;

	Thread mThread = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		start = (Button) findViewById(R.id.start);
		stop = (Button) findViewById(R.id.stop);

		editCount = (EditText) findViewById(R.id.editText1);
		editRecorder = (EditText) findViewById(R.id.editText2);
		show = (TextView) findViewById(R.id.show);
		

		start.setOnClickListener(this);
		stop.setOnClickListener(this);
		touch=new MonkeyTouch();

		
			
	}

	class Test extends Thread implements Runnable {
		public void run() {

			for(int i=0;i<cishu;i++){
				
				startTestActivity();

    			SystemClock.sleep(5000);
    	   			
    			touch.drag(531, 1679,531, 1679,2);
    			SystemClock.sleep(2000);
    			gobackCount(1);
				SystemClock.sleep(totalTime*1000);
				
			}

		}
	};
	
	public void gobackCount(int count){
		touch.sendKey(KeyEvent.KEYCODE_BACK);
		SystemClock.sleep(500);
	}
	
	
	public void startTestActivity(){
		
        Intent mIntent=new Intent();	   
	    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

	    mIntent.setClassName("com.oneplus.camera",
				"com.oneplus.camera.OPCameraActivity");

		startActivity(mIntent);
	}
	

	
	
	public boolean isCheckInput() {
		String cishu = editCount.getText().toString();
		String times = editRecorder.getText().toString();
	
		if (cishu.equals("") || times.equals("")) {
			Toast.makeText(getApplicationContext(), "请重新设置运行参数!",
					Toast.LENGTH_SHORT).show();
			return false;
		} else
			return true;

	}


	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		int id = view.getId();
		switch (id) {
		case R.id.start:
			boolean op = isCheckInput();
			isStop = false;
			
			show.setText("");// 

			if (op == true) {
				if(mThread!=null){
					mThread.interrupted();
					mThread=null;
				}
				start.setEnabled(false);
				stop.setEnabled(true);
				cishu = Integer.parseInt(editCount.getText().toString());
				totalTime = Integer.parseInt(editRecorder.getText().toString());
			
				mThread = new Thread(new Test());
				mThread.start();
		
				start.setEnabled(false);
			}
			break;
		case R.id.stop:
			isStop = true;
			start.setEnabled(true);
			stop.setEnabled(false);

			android.os.Process.killProcess(android.os.Process.myPid());
			break;

		}

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			final AlertDialog isExit = new AlertDialog.Builder(this).create();
			isExit.setTitle("退出");
			isExit.setMessage("确定退出应用吗?");
			DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case AlertDialog.BUTTON1:
						NotificationManager notificationManager = (NotificationManager) MainActivity.this
								.getSystemService(NOTIFICATION_SERVICE);
						notificationManager.cancel(0);

						String packagename = getPackageName();
						ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

						finish();
						manager.killBackgroundProcesses(packagename);
						break;
					case AlertDialog.BUTTON2:
						isExit.cancel();
						break;
					default:
						break;
					}
				}
			};
			isExit.setButton("确认", listener);
			isExit.setButton2("取消", listener);
			isExit.show();

		}
		return false;
	}


}
