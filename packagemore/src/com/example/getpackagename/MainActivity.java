package com.example.getpackagename;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * 张斌 2016.05.03
 *
 */
public class MainActivity extends Activity {
	private Context mContext;

	private ListView mListView;
	private List<AppInfo> mAppInfoList;
	private AppInfo appInfo;
	private FrameLayout mRootFrameLayout;
	public final int GET_APK_FINISH = 9527;
	public final int change = 9528;
	public ProgressBar mProgressBar;
	private PackageManager mPackageManager;
	private ListViewAdapter mListViewAdapter;
	ItemClickListenerImpl mItemClickListenerImpl;
	private ItemLongClickListenerImpl ItemLongClickListenerImpl;
	private EditText search;
	private ImageButton searchbutton;

	private ClearEditText mClearEditText;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == GET_APK_FINISH) {
				// dismissProgressBar();
				mListViewAdapter.setList(mAppInfoList);

				mListView.setAdapter(mListViewAdapter);
				mListViewAdapter.notifyDataSetChanged();
			}
			if (msg.what == 1) {
				mListViewAdapter.setList(mAppInfoList);

				mListView.setAdapter(mListViewAdapter);
				mListViewAdapter.notifyDataSetChanged();

			}
			if (msg.what == change) {
				mListViewAdapter.setList(mAppInfoList);

				mListView.setAdapter(mListViewAdapter);
				mListViewAdapter.notifyDataSetChanged();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		// searchbutton = (ImageButton) findViewById(R.id.search);
		/*
		 * searchbutton.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { search = (EditText)
		 * findViewById(R.id.shuru); search.setVisibility(View.VISIBLE);
		 * 
		 * } });
		 */
		mContext = this;
		mPackageManager = getApplicationContext().getPackageManager();
		mListView = (ListView) findViewById(R.id.listView);
		// 进度条初始化
		mProgressBar = new ProgressBar(mContext);

		mItemClickListenerImpl = new ItemClickListenerImpl();

		mAppInfoList = new ArrayList<AppInfo>();

		mListViewAdapter = new ListViewAdapter(mContext);
		mListViewAdapter.setList(mAppInfoList);

		mListView.setAdapter(mListViewAdapter);
		// mListView.setAdapter(new ListViewAdapter(mContext));

		// mListView.setAdapter(new ListViewAdapter(getBaseContext()));

		mListView.setOnItemClickListener(mItemClickListenerImpl);
		mListView.setOnItemLongClickListener(new ItemLongClickListenerImpl());
		// showProgressBar();

		filldata();
		set_Search_TextChanged();

	}

	// 长按进去

	private class ItemLongClickListenerImpl implements AdapterView.OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
			String packname = mAppInfoList.get(position).getPackagename();
			Intent intent = new Intent();
			PackageManager pm = getPackageManager();
			PackageInfo packinfo;

			try {
				packinfo = pm.getPackageInfo(packname, PackageManager.GET_ACTIVITIES);

				ActivityInfo[] activityInfos = packinfo.activities;
				if (activityInfos != null && activityInfos.length > 0) {
					ActivityInfo activityinfo = activityInfos[0];

					intent.setClassName(packname, activityinfo.name);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					Log.e("zbinmain", "wo zai changan " + packname + "++++++++" + activityinfo.name);
					startActivity(intent);
					Toast.makeText(getApplicationContext(), activityinfo.name + " ", 500).show();
				} else {
					Toast.makeText(getApplicationContext(), " 没有界面", 500).show();

				}
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), "有问题程序 ", 500).show();
			}

			Log.e("zbinmain", "wo zai changan ");

			return true;
		}
	}

	// 点击分享

	private class ItemClickListenerImpl implements AdapterView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			File apkFile = mAppInfoList.get(position).getApkFile();
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_SEND);
			intent.setType("*/*");
			intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(apkFile));
			startActivity(intent);
		}

	}

	/**
	 * 获取搜索出来的应用
	 */
	private List<AppInfo> getSearchAllAppInfo() {
		File apkFile;
		AppInfo appInfo;
		mAppInfoList = new ArrayList<AppInfo>();
		// 获取已安装所有应用对应的PackageInfo
		List<PackageInfo> packageInfoList = mPackageManager.getInstalledPackages(0);

		for (int i = 0; i < packageInfoList.size(); i++) {

			appInfo = new AppInfo();
			PackageInfo packageInfo = packageInfoList.get(i);
			if (getApplicationName(packageInfo.packageName, mPackageManager).contains(search.getText().toString())) {
				Log.e("zbin", search.getText().toString() + packageInfoList.size());

				// 获取应用名称
				appInfo.setAppName(getApplicationName(packageInfo.packageName, mPackageManager));
				// 获取应用ICON
				appInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(mPackageManager));
				// 获取应用的apk文件
				apkFile = new File(packageInfo.applicationInfo.sourceDir);
				appInfo.setApkFile(apkFile);
				// 获取到应用大�?
				appInfo.setAppSize(((float) apkFile.length() / 1024 / 1024) + "MB");

				// 获取已经安装应用的包名
				appInfo.setPackagename(packageInfo.applicationInfo.packageName);
				// 获取应用的更新时�?
				appInfo.setAppTime(getDate(packageInfo.lastUpdateTime));

				mAppInfoList.add(appInfo);
			}
		}
		return mAppInfoList;
	}

	/**
	 * 获取已经安装的应用
	 */
	private List<AppInfo> getAllAppInfo() {
		File apkFile;
		AppInfo appInfo;
		mAppInfoList = new ArrayList<AppInfo>();
		// 获取已安装所有应用对应的PackageInfo
		List<PackageInfo> packageInfoList = mPackageManager.getInstalledPackages(0);
		for (int i = 0; i < packageInfoList.size(); i++) {
			appInfo = new AppInfo();
			PackageInfo packageInfo = packageInfoList.get(i);
			// 获取应用名称
			appInfo.setAppName(getApplicationName(packageInfo.packageName, mPackageManager));
			// 获取应用ICON
			appInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(mPackageManager));
			// 获取应用的apk文件
			apkFile = new File(packageInfo.applicationInfo.sourceDir);
			appInfo.setApkFile(apkFile);
			// 获取到应用大小
			appInfo.setAppSize(((float) apkFile.length() / 1024 / 1024) + "MB");

			// 获取已经安装应用的包名
			appInfo.setPackagename(packageInfo.applicationInfo.packageName);
			// 获取应用的更新时间
			appInfo.setAppTime(getDate(packageInfo.lastUpdateTime));
			mAppInfoList.add(appInfo);
		}
		return mAppInfoList;
	}

	/**
	 * 获取应用的Icon
	 *//*
		 * public Drawable getAppliactionIcon(PackageInfo packageInfo,
		 * PackageManager packageManager) { Drawable appliactionIcon =
		 * packageInfo.applicationInfo.loadIcon(packageManager); return
		 * appliactionIcon; }
		 */

	/**
	 * 获取应用的名�?
	 */
	public String getApplicationName(String packageName, PackageManager packageManager) {
		String applicationName = null;
		try {
			ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
			applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
		} catch (PackageManager.NameNotFoundException e) {

		}
		return applicationName;
	}

	/**
	 * 生成时间
	 */
	public static String getDate(long time) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(time);
		String formatedDate = simpleDateFormat.format(date);
		return formatedDate;
	}

	/**
	 * 在屏幕中间显示风火轮
	 */
	private void showProgressBar() {
		mRootFrameLayout = (FrameLayout) findViewById(android.R.id.content);
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = Gravity.CENTER;
		mProgressBar = new ProgressBar(mContext);
		mProgressBar.setLayoutParams(layoutParams);
		mProgressBar.setVisibility(View.VISIBLE);
		mRootFrameLayout.addView(mProgressBar);
	}

	/**
	 * 隐藏转动条
	 */
	private void dismissProgressBar() {
		if (null != mProgressBar && null != mRootFrameLayout) {
			mRootFrameLayout.removeView(mProgressBar);
		}
	}

	/**
	 * 填充数据
	 * 
	 */
	public void filldata() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				mAppInfoList.clear();
				mAppInfoList.addAll(getAllAppInfo());
				mHandler.sendEmptyMessage(GET_APK_FINISH);
			}
		}).start();
	}

	/**
	 * 开启应用
	 */
	private void startApplication() {
		Intent intent = new Intent();
		String packname = appInfo.getPackagename();
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packinfo = pm.getPackageInfo(packname, PackageManager.GET_ACTIVITIES);
			ActivityInfo[] activityInfos = packinfo.activities;
			if (activityInfos != null && activityInfos.length > 0) {
				ActivityInfo activityinfo = activityInfos[0];
				intent.setClassName(packname, activityinfo.name);
				startActivity(intent);
			} else {
				Toast.makeText(this, "哎呀，这个应用程序没界面", 0).show();
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(this, "没法开这个应用。", 0).show();
		}

	}

	private void set_Search_TextChanged() {

		search = (EditText) findViewById(R.id.shuru);
		search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				Log.e("zbinmain", "文字正在发生变化");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				Log.e("zbinmain", "文字变化之前");
			}

			@Override
			public void afterTextChanged(Editable s) {

				Message msg = new Message();

				Log.e("zbinmain", "文字变化之后");
				if (s.length() == 0) {
					msg.what = 1;
					// Toast.makeText(getApplicationContext(), "什么都没有",
					// 0).show();

					filldata();
					mClearEditText = new ClearEditText(mContext);
					mClearEditText.setShakeAnimation(5);

					// 加入震动功能
					Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
					vib.vibrate(1000);
					// mHandler.post(restore);

				} else {

					// Toast.makeText(getApplicationContext(), "hehe已经输入",
					// 0).show();
					changeUi();

				}

			}
		});

	}

	Runnable restore = new Runnable() {
		@Override
		public void run() {
			mAppInfoList.clear();
			mAppInfoList.addAll(getAllAppInfo());
			mHandler.sendEmptyMessage(GET_APK_FINISH);
		}
	};

	public void changeUi() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				mAppInfoList.clear();
				mAppInfoList.addAll(getSearchAllAppInfo());
				mHandler.sendEmptyMessage(change);

			}
		}).start();

	}
}
