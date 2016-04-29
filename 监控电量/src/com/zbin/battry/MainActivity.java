package com.zbin.battry;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Message msg;
	timeUtil timeutil;
	BatteryBroadcastReciver reciver;
	TextView displaybattry, time;
	private String TAG = "zbinmain";
	String currenttime, time1;
	Button begin, stop;
	int time2;
	// <!-- android:sharedUserId="android.uid.system" -->
	// 在主线程里面处理消息并更新UI界面
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				timeutil = new timeUtil();
				currenttime = timeutil.currentTime();

				displaybattry.setText(currenttime);
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.e(TAG, "onCreate");
		init();
		msg = new Message();
		// time1 = time.getText().toString();
		// time2 = Integer.parseInt(time1);

		displaybattry = (TextView) findViewById(R.id.displaybattry);
		new TimeThread().start(); // 启动新的线程
		// displaybattry.setText(currenttime);
		begin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				/*
				 * Bundle bundle=new Bundle(); bundle.putInt("time", time2);
				 * 
				 * Intent intent1 = new Intent(); intent1.putExtra("time",
				 * time2); sendBroadcast(intent1);
				 */
				Intent intent = new Intent(MainActivity.this, BatteryMonitorService.class);
				// Intent intent=new Intent(MainActivity.class,
				// BatteryMonitorService.class);

				startService(intent);

				Toast.makeText(getApplicationContext(), "监控开始", 0).show();
				begin.setClickable(false);
				stop.setClickable(true);
				begin.setEnabled(false);
				stop.setEnabled(true);

			}
		});
		stop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				stopService(new Intent(MainActivity.this, BatteryMonitorService.class));
				Toast.makeText(getApplicationContext(), "结束监控", 0).show();
				stop.setClickable(false);
				begin.setClickable(true);
				stop.setEnabled(false);
				begin.setEnabled(true);

			}
		});

	}

	public void init() {
		time = (TextView) findViewById(R.id.time);
		begin = (Button) findViewById(R.id.begin);
		stop = (Button) findViewById(R.id.stop);
		/*
		 * begin.setOnClickListener((OnClickListener) this);
		 * stop.setOnClickListener((OnClickListener) this);
		 */
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.e(TAG, "onResume");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.e(TAG, "onPause");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.e(TAG, "onStop");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// unregisterReceiver(reciver);
		Intent intent2 = new Intent(MainActivity.this, BatteryMonitorService.class);

		startService(intent2);
		Log.e(TAG, "onDestroy");
	}

	// 创建线程
	class TimeThread extends Thread {
		@Override
		public void run() {
			do {
				try {
					Thread.sleep(1000);
					msg = new Message();
					msg.what = 1; // 消息(一个整型值)
					mHandler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
