package com.zbin.testphone;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class monkeyTest extends Activity {
	private Runtime runtime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monkey);
	}

	public void click(View v) throws Exception {
		runtime = Runtime.getRuntime();
		runtime.exec("monkey -s 10 -p com.android.dialer --throttle 1000  -v 1000");
		Log.i("Monkey", "厘壓峇佩。。。。。。。。。。。。。。。。。。");
	}
}
