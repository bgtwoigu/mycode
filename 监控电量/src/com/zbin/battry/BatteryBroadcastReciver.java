package com.zbin.battry;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BatteryBroadcastReciver extends BroadcastReceiver {
	fileUtil fileutil = new fileUtil();
	timeUtil timeutil = new timeUtil();
	int time3;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		//time3=intent.getIntExtra("time", time2);
		if (Intent.ACTION_BATTERY_OKAY.equals(intent.getAction())) {
			Toast.makeText(context, "电量已恢复，可以使用!", Toast.LENGTH_LONG).show();
		}
		if (Intent.ACTION_BATTERY_LOW.equals(intent.getAction())) {
			// Toast.makeText(context, "电量过低，请尽快充电！", Toast.LENGTH_LONG).show();
		}
		if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
			// Bundle bundle = intent.getExtras();
			// 得到系统当前电量
			int current = intent.getIntExtra("level", 0);
			// 取得系统总电量
			int total = intent.getIntExtra("scale", 100);
			StringBuffer sb = new StringBuffer();
			sb.append("当前电量为：" + current * 100 / total + "%" + "  ");
			
			Toast.makeText(context, timeutil.currentTime() + sb.toString(),
					0).show();
			fileutil.saveToSDCard("zzhehe.txt",
					timeutil.currentTime() + sb.toString());

		}

	}
}
