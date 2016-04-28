package com.zbin.testphone;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class incoming extends Activity {
	private TextView tv;
	private Button bb;
	private Button bg;
	private EditText comingTime;
	private ITelephony iTelephony;
	private TelephonyManager manager;
	private String incomingnumber;
	private String IncomingTime;
	private Boolean flag = true;

	// 创建handler 发送消息
	/*
	 * private Handler handler = new Handler() { public void
	 * handleMessage(android.os.Message msg) {
	 * 
	 * }; };
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.incoming);
		tv = (TextView) findViewById(R.id.xianshi);
		bb = (Button) findViewById(R.id.start);
		bg = (Button) findViewById(R.id.over);
		comingTime = (EditText) findViewById(R.id.comingTime);
		IncomingTime = comingTime.getText().toString();
		// 实例化电话服务，监听电话的状态
		manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		bg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flag = false;
				finish();

			}
		});

		bb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				manager.listen(new PhoneStateListener() {

					@Override
					public void onCallStateChanged(int state, final String incomingNumber) {
						super.onCallStateChanged(state, incomingNumber);
						switch (state) {
						// 空闲时
						case TelephonyManager.CALL_STATE_IDLE:
							break;
						case TelephonyManager.CALL_STATE_OFFHOOK:
							break;
						case TelephonyManager.CALL_STATE_RINGING:
							new Thread() {

								public void run() {
									while (flag) {
										flag = true;
										int shijian = Integer.parseInt(IncomingTime);
										int shijian1 = shijian * 1000;
										incomingnumber = incomingNumber;
										// Toast.makeText(getApplicationContext(),
										// "电话：" + incomingnumber, 1);
										// tv.setText(incomingnumber);
										try {
											Thread.sleep(shijian1);
											answer();

										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

									}
								}
							}.start();

							break;

						}

					}

				}, PhoneStateListener.LISTEN_CALL_STATE);

			}
		});

	}

	/**
	 * 接听电话的方法
	 * 
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws RemoteException
	 * 
	 */
	public void answer() throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, RemoteException {
		/*
		 * Method method = Class.forName("android.os.ServiceManager")
		 * 
		 * .getMethod("getService", String.class);
		 * 
		 * IBinder binder = (IBinder) method.invoke(null, new Object[] {
		 * TELEPHONY_SERVICE });
		 * 
		 * ITelephony telephony = ITelephony.Stub.asInterface(binder);
		 * 
		 * telephony.answerRingingCall();
		 */

		try

		{

			Method method = Class.forName("android.os.ServiceManager")

					.getMethod("getService", String.class);

			IBinder binder = (IBinder) method.invoke(null, new Object[] { TELEPHONY_SERVICE });

			ITelephony telephony = ITelephony.Stub.asInterface(binder);

			telephony.answerRingingCall();

		} catch (

		NoSuchMethodException e)

		{

			Log.d("Sandy", "", e);

		} catch (

		ClassNotFoundException e)

		{

			Log.d("Sandy", "", e);

		} catch (

		Exception e)

		{

			Log.d("Sandy", "", e);

			try {

				Log.e("Sandy", "for version 4.1 or larger");

				Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");

				KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK);

				intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);

				sendOrderedBroadcast(intent, "android.permission.CALL_PRIVILEGED");

			} catch (Exception e2) {

				Log.d("Sandy", "", e2);

				Intent meidaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);

				KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK);

				meidaButtonIntent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);

				sendOrderedBroadcast(meidaButtonIntent, null);

			}

		}

	}
	/*
	 * try
	 * 
	 * {
	 * 
	 * Method method = Class.forName("android.os.ServiceManager")
	 * 
	 * .getMethod("getService", String.class);
	 * 
	 * IBinder binder = (IBinder) method.invoke(null, new Object[] {
	 * TELEPHONY_SERVICE });
	 * 
	 * ITelephony telephony = ITelephony.Stub.asInterface(binder);
	 * 
	 * telephony.answerRingingCall();
	 * 
	 * } catch(
	 * 
	 * NoSuchMethodException e)
	 * 
	 * {
	 * 
	 * Log.d("Sandy", "", e);
	 * 
	 * } catch(
	 * 
	 * ClassNotFoundException e)
	 * 
	 * {
	 * 
	 * Log.d("Sandy", "", e);
	 * 
	 * } catch(
	 * 
	 * Exception e)
	 * 
	 * {
	 * 
	 * Log.d("Sandy", "", e);
	 * 
	 * try {
	 * 
	 * Log.e("Sandy", "for version 4.1 or larger");
	 * 
	 * Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
	 * 
	 * KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP,
	 * KeyEvent.KEYCODE_HEADSETHOOK);
	 * 
	 * intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
	 * 
	 * sendOrderedBroadcast(intent, "android.permission.CALL_PRIVILEGED");
	 * 
	 * } catch (Exception e2) {
	 * 
	 * Log.d("Sandy", "", e2);
	 * 
	 * Intent meidaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
	 * 
	 * KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP,
	 * KeyEvent.KEYCODE_HEADSETHOOK);
	 * 
	 * meidaButtonIntent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
	 * 
	 * sendOrderedBroadcast(meidaButtonIntent, null);
	 * 
	 * }
	 * 
	 * }
	 */
}
