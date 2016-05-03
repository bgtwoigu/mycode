package com.example.getpackagename;

import java.io.File;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * 原创作者: 谷哥的小弟 http://blog.csdn.net/lfdfhl
 *
 * QQ交流群: 183899857
 *
 */
public class AppInfo {
	private String packagename;

	public String getPackagename() {
		return packagename;
	}

	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}

	public Intent getIntent() {
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}

	private Intent intent;
	private String appName;
	private String appSize;
	private String appTime;
	private File apkFile;
	private Drawable appIcon;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppSize() {
		return appSize;
	}

	public void setAppSize(String appSize) {
		this.appSize = appSize;
	}

	public String getAppTime() {
		return appTime;
	}

	public void setAppTime(String appTime) {
		this.appTime = appTime;
	}

	public Drawable getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}

	public File getApkFile() {
		return apkFile;
	}

	public void setApkFile(File apkFile) {
		this.apkFile = apkFile;
	}

	@Override
	public String toString() {
		return "AppInfo{" + "appName='" + appName + '\'' + ", appSize='" + appSize + '\'' + ", appTime='" + appTime
				+ '\'' + ", appIcon='" + appIcon + '\'' + '}';
	}
}
