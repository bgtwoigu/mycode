package com.zbin.testphone;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class autoSMS extends Activity {
	private Button start;
	private EditText counts, body, number;
	private TextView tt;
	private String numbers, bodys, countss;
	private ProgressBar smsBar;
	private SmsManager sms;
	private String defaultSmsPkg, mypackage;
	private SharedPreferences sp;
	private String msgs = "", spmsg;
	private int tiao;

	private SimpleDateFormat sfd;
	private Date date;

	public static final Uri CONTENT_URI = Uri.parse("content://sms");

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// msgs=sp.getString("msgdebug", msgs);
			if (msg.what == 1) {

				Toast.makeText(getApplicationContext(), msgs, 1).show();

				Log.e("msgdebug", "package:" + "handler发送消息。。。。。。。。。。。。。。。");

			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms);
		// 获得sp对象，获得编辑器
		sp = getSharedPreferences("sp", MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("msgdebug", msgs);
		editor.commit();
		start = (Button) findViewById(R.id.add_sms);
		counts = (EditText) findViewById(R.id.sms_counts);
		number = (EditText) findViewById(R.id.number);
		body = (EditText) findViewById(R.id.body);
		tt = (TextView) findViewById(R.id.tt);
		smsBar = (ProgressBar) findViewById(R.id.sms_progressBar);
		sms = SmsManager.getDefault();
		// 得到默认的短信包
		defaultSmsPkg = Telephony.Sms.getDefaultSmsPackage(this);
		// 得到自己的短信包
		mypackage = this.getPackageName();

		// 判断是否是默认的短信
		if (!defaultSmsPkg.equals(mypackage)) {

			Log.e("msgdebug", "package:" + mypackage);
			Log.e("msgdebug", "package:" + defaultSmsPkg);
			Intent localIntent = new Intent(/*Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT*/"android.provider.Telephony.ACTION_CHANGE_DEFAULT");
			localIntent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, mypackage);
			startActivity(localIntent);
		}

		start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				numbers = number.getText().toString().trim();
				bodys = body.getText().toString().trim();
				countss = counts.getText().toString().trim();
				if (TextUtils.isEmpty(numbers) || TextUtils.isEmpty(bodys) || TextUtils.isEmpty(countss))
					Toast.makeText(getApplicationContext(), "请输入", 1).show();
				else {
					new Thread() {
						public void run() {
							writeSms(bodys, numbers, countss);
							Log.e("msgdebug", bodys + numbers + countss);
							query();
							// showProgressBar();
						}

					}.start();
				}
			}
		});
	}

	// 显示进度条
	private void showProgressBar() {
		smsBar.setVisibility(View.VISIBLE);
		countss = counts.getText().toString();
		tiao = Integer.parseInt(countss);
		smsBar.setMax(tiao);
		smsBar.setProgress(0);
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < tiao; i++) {

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		}).start();
		;

	}

	// 写入信息
	private void writeSms(String numbers, String bodys, String countss) {
		ContentResolver resolver = getContentResolver();
		ContentValues contentValues = new ContentValues();
		contentValues.put(Telephony.Sms.DATE, System.currentTimeMillis());
		contentValues.put(Telephony.Sms.TYPE, Telephony.Sms.MESSAGE_TYPE_INBOX);
		contentValues.put(Telephony.Sms.READ, false);
		contentValues.put(Telephony.Sms.SEEN, false);
		contentValues.put(Telephony.Sms.THREAD_ID, countss);
		contentValues.put(Telephony.Sms.ADDRESS, numbers);
		contentValues.put(Telephony.Sms.BODY, bodys);

		resolver.insert(CONTENT_URI, contentValues);

	}

	// 查询
	private void query() {
		Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);
		// 把从短信中获取的时间戳换成一定格式的时间

		while ((cursor.moveToNext()) && (cursor.getPosition() < 5)) {
			String dateColumn = cursor.getString(cursor.getColumnIndex("date"));
			String phoneColumn = cursor.getString(cursor.getColumnIndex("address"));
			String smsColumn = cursor.getString(cursor.getColumnIndex("body"));
			sfd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			date = new Date(Long.parseLong(dateColumn));
			String time = sfd.format(date);

			System.out.println("count " + cursor.getCount() + " position " + cursor.getPosition());
			System.out.println("phoneColumn " + phoneColumn + " smsColumn " + smsColumn + "dateColumn" + time);
			/*
			 * Date date = new
			 * Date(Long.parseLong(cursor.getString(dateColumn))); String time =
			 * sfd.format(date);
			 */
			msgs = "phoneColumn " + phoneColumn + " smsColumn " + smsColumn + "dateColumn" + time + "\n";
			/* msgs=sp.getString("msgdebug", msgs); */
			spmsg = sp.getString("msgdebug", msgs);
			Log.e("msgdebug", spmsg + "*********************************");

		}
		handler.sendEmptyMessage(1);
		cursor.close();

	}

}
