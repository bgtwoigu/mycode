package com.zbin.Util;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class NotifictionCustomer extends Activity {
	// 打开文件
	private void openFile(File file) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);

		String type = getMIMEType(file);
		intent.setDataAndType(Uri.fromFile(file), type);
		startActivity(intent);
	}

	// 获取文件mimetype
	private String getMIMEType(File file) {
		String type = "";
		String name = file.getName();
		// 文件扩展名
		String end = name.substring(name.lastIndexOf(".") + 1, name.length()).toLowerCase();
		if (end.equals("m4a") || end.equals("mp3") || end.equals("wav")) {
			type = "audio";
		} else if (end.equals("mp4") || end.equals("3gp")) {
			type = "video";
		} else if (end.equals("jpg") || end.equals("png") || end.equals("jpeg") || end.equals("bmp")
				|| end.equals("gif")) {
			type = "image";
		} else {
			// 如果无法直接打开，跳出列表由用户选择
			type = "*";
		}
		type += "/*";
		return type;
	}

	private void displayToast(String message, int time) {
		Toast.makeText(NotifictionCustomer.this, message, time).show();
	}
}
