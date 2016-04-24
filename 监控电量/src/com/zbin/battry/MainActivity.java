package com.zbin.battry;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	timeUtil timeutil = new timeUtil();
	BatteryBroadcastReciver reciver;
	TextView displaybattry, time;
	private String TAG = "zbinmain";
	String currenttime, time1;
	Button begin, stop;
	int time2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		//time1 = time.getText().toString();
		//time2 = Integer.parseInt(time1);
		currenttime = timeutil.currentTime();
		Log.e(TAG, "onCreate");
		begin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				/*
				 * Bundle bundle=new Bundle(); bundle.putInt("time", time2);
				 
				Intent intent1 = new Intent();
				intent1.putExtra("time", time2);
				sendBroadcast(intent1);*/
				Intent intent = new Intent(MainActivity.this,
						BatteryMonitorService.class);
				// Intent intent=new Intent(MainActivity.class,
				// BatteryMonitorService.class);
				startService(intent);

			}
		});
		stop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopService(new Intent(MainActivity.this,
						BatteryMonitorService.class));
				
			}
		});
		displaybattry = (TextView) findViewById(R.id.displaybattry);
		displaybattry.setText(currenttime);
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
		Log.e(TAG, "onDestroy");
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
