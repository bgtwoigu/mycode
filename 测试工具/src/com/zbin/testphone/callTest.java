package com.zbin.testphone;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;
import com.zbin.dao.BlackNumberDao;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class callTest extends Activity {
	private Boolean b = true;
	private EditText et_phonenum;
	private EditText et_times;
	private EditText et_timelong;
	private Message msg;
	private String num;
	private String times;
	private String longtime;
	private Button bb;
	private Button fin;
	private Button ok;
	private Button cancel;
	private ITelephony iTelephony;
	private TelephonyManager manager;
	private Thread newThread;
	private AlertDialog dialog;
	private int nowCount = 0;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			super.handleMessage(msg);
			if (msg.what == 1) {

				// int i = msg.getData().getInt("times");
				Toast.makeText(getApplicationContext(), "这是第" + nowCount + "次拨打电话", Toast.LENGTH_SHORT).show();
			} else if (msg.what == 2) {

				Toast.makeText(getApplicationContext(), "finish", Toast.LENGTH_SHORT).show();
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new BlackNumberDao(null);
		et_phonenum = (EditText) findViewById(R.id.et_phonenum);
		et_times = (EditText) findViewById(R.id.et_times);
		et_timelong = (EditText) findViewById(R.id.et_timelong);
		bb = (Button) findViewById(R.id.call);
		fin = (Button) findViewById(R.id.over);
		cancel = (Button) findViewById(R.id.cancel);
		ok = (Button) findViewById(R.id.ok);
		fin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				b = false;
				finish();
				android.os.Process.killProcess(android.os.Process.myPid());

				Log.i("结束服务", "电话自动停止、。。。。。。。。。。。。");

			}

		});
		bb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				b = true;
				num = et_phonenum.getText().toString().trim();
				times = et_times.getText().toString();

				longtime = et_timelong.getText().toString();

				if (num.equals("") || times.equals("") || longtime.equals("")) {
					Toast.makeText(getApplicationContext(), "请输入", 1).show();

				} else {
					final int time = Integer.parseInt(times);
					final int loll = Integer.parseInt(longtime);
					System.out.println(time + "#######");
					System.out.println(loll + "#######");

					// msg = Message.obtain();
					msg = new Message();
					new Thread() {
						public void run() {
							// for (int i = 1; i <= time; i++) {
							// int i = 0;

							// Bundle bundle = new Bundle();
							for (int i = 1; i <= time; i++) {
								nowCount = i;
								Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + num));
								startActivity(intent);

								try {
									Log.i("通话测试。。。。。", "开始睡眠。。。。。。。。。。。。。。。");
									Thread.sleep(loll * 1000);
									encall();
									Thread.sleep(5000);
									Log.i("通话测试。。。。。", "电话挂断了。。。。。。。。。。。。。。。");
									Log.i("WO ZAI  CE  SHI ......", "THIS IS TIMES " + nowCount);
								} catch (Exception e) {
									e.printStackTrace();
								}
								/*
								 * if (i > time) { b = false; break; }
								 */

								// i++;
								// bundle.putInt("times", i);
								// msg.setData(bundle);
								// handler.sendMessage(msg);
								handler.sendEmptyMessage(1);
							}
							// }
							handler.sendEmptyMessage(2);
						}

					}.start();
				}

			}
		});

	}

	@Override
	protected void onStop() {

		super.onStop();

		Log.i("结束服务", "电话自动停止、。。。。。。。。。。。。");

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i("结束服务", "页面被销毁、。。。。。。。。。。。。");

	}

	/**
	 * 挂断电话的方法
	 * 
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws RemoteException
	 */
	public void encall() throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, RemoteException {

		Method method = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);

		IBinder binder = (IBinder) method.invoke(null, new Object[] { "phone" });
		ITelephony telephony = ITelephony.Stub.asInterface(binder);
		telephony.endCall();

	}

}